package com.code;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

/**
 * <p>Description:工具软件</p>
 * @version 1.0
 */

public class CreateSpringBaseCode {
	// 需修改数据
	private String basepath = "E:/工程/wkmk-zyb1/src/com/wkmk/tk/";
	private String basepackage = "com.wkmk.tk.";
	private boolean createfile = true;

	private String url = "jdbc:mysql://192.168.0.2:3306/java2zyb1?useUnicode=true&characterEncoding=utf-8";
	private String user = "javadev";
	private String password = "java2soft";

	private String tablename = "tk_textbook_buy";
	private String objname = "TkTextBookBuy";
	private String objnamecn = "教材基本信息表";

	// ////////////////////////////////////////////////

	// 基本不变数据
	String n[] = new String[100];// 列名
	String t[] = new String[100];// 列类型
	int isize = 0;

	private static PrintWriter pw;
	private String title = objnamecn;
	private String hbmxmlpath = basepath + "bo/mappings/";
	private String bopath = basepath + "bo/";
	private String managerpath = basepath + "service/";
	private String managerimplpath = basepath + "service/impl/";
	private String actionformpath = basepath + "web/form/";
	private String actionpath = basepath + "web/action/";

	public CreateSpringBaseCode() {
	}

	public void getTableField(String tablename) {

		try {
			String sql = "SELECT * FROM " + tablename;
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection(url, user, password); // 连接数据库
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			int numberOfColumns = rsmd.getColumnCount(); // 获取字段数
			isize = numberOfColumns;
			for (int i = 1; i <= numberOfColumns; i++) {
				n[i - 1] = rsmd.getColumnName(i);// 列名
				t[i - 1] = getJavaObjectType(rsmd.getColumnTypeName(i)
						.toLowerCase());
				System.out.println(rsmd.getColumnTypeName(i));
			}
			rs.close(); // 关闭结果集
			stmt.close(); // 关闭statement
			conn.close(); // 关闭连接
		} catch (Exception e) {
		}
	}

	/**
	 * 字段对象
	 * @param columntype
	 * @return
	 */
	private String getJavaObjectType(String columntype) {
		String javaObjectType = "String";
		if ("varchar".equals(columntype) || "char".equals(columntype)
				|| "text".equals(columntype)) {
			javaObjectType = "String";
		} else if ("integer".equals(columntype) || "int".equals(columntype)) {
			javaObjectType = "Integer";
		} else if ("folat".equals(columntype)) {
			javaObjectType = "Float";
		} else if ("double".equals(columntype)) {
			javaObjectType = "Double";
		}
		return javaObjectType;
	}

	/**
	 * 创建文件
	 * @param filepath
	 * @param filename
	 * @param code
	 */
	private static void creatCodeFile(String filepath, String filename,
			String code) {
		try {
			if (pw != null) {
				try {
					pw.close();
				} catch (Exception e) {
				}
				pw = null;
			}

			File fileDir = new File(filepath);
			// 如果不存在指定的目录时,创建一个
			if (!fileDir.exists()) {
				if (!fileDir.mkdirs()) {
					System.out.println("文件创建失败!");
				}
			}
			String sFileName = filepath + "/" + filename;
			pw = new PrintWriter(new FileWriter(sFileName, true), true);

			OutputStreamWriter out = new OutputStreamWriter(
					new FileOutputStream(sFileName), "UTF-8");
			out.write(code);
			out.flush();
			out.close();
		} catch (Exception e) {
			System.out.println("文件生成失败，请检查路径是否可写后再重试");
		}
	}

	/**
	 * 创建hbm.xml文件
	 */
	private void createHbmXmlFile() {
		StringBuffer code = new StringBuffer();
		code.append("<?xml version=\"1.0\"?>\r\n");
		code.append("<!DOCTYPE hibernate-mapping PUBLIC\r\n");
		code.append("\t\t\"-//Hibernate/Hibernate Mapping DTD 3.0//EN\"\r\n");
		code.append("\t\t\"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd\">\r\n");
		code.append("<hibernate-mapping>\r\n");
		code.append("<class name=\"");
		code.append(basepackage);
		code.append("bo.");
		code.append(objname);
		code.append("\" table=\"");
		code.append(tablename.toLowerCase());
		code.append("\">\r\n");
		code.append("<id name=\"");
		code.append(n[0].toLowerCase());
		code.append("\" type=\"java.lang.");
		code.append(t[0]);
		code.append("\" column=\"");
		code.append(n[0]);
		code.append("\">  <generator class=\"native\" /> </id>\r\n");
		for (int i = 1; i < isize; i++) {
			code.append("<property  name=\"");
			code.append(n[i].toLowerCase());
			code.append("\" type=\"java.lang.");
			code.append(t[i]);
			code.append("\" column=\"");
			code.append(n[i].toLowerCase());
			code.append("\"/>\r\n");
		}
		code.append("</class>\r\n");
		code.append("</hibernate-mapping>");
		if (createfile) {
			creatCodeFile(hbmxmlpath, objname + ".hbm.xml", code.toString());
		} else {
			System.out.println(code.toString());
		}
	}

	private void createBoFile() {
		StringBuffer code = new StringBuffer();
		code.append("package ");
		code.append(basepackage);
		code.append("bo;\r\n\r\n");
		code.append("import java.io.Serializable;\r\n\r\n");
		code.append("import org.apache.commons.lang.builder.ToStringBuilder;\r\n");
		code.append("import org.apache.commons.lang.builder.EqualsBuilder;\r\n");
		code.append("import org.apache.commons.lang.builder.HashCodeBuilder;\r\n\r\n");
		code.append("import com.util.bo.BaseObject;\r\n\r\n");
		code.append("/**\r\n");
		code.append(" *<p>Description: ");
		code.append(title);
		code.append("</p>\r\n");
		code.append(" *<p>E-mail: zhangxuedong28@gmail.com</p>\r\n");
		code.append(" *@version 1.0\r\n");
		code.append(" */\r\n");
		code.append("public class ");
		code.append(objname);
		code.append(" extends BaseObject implements Serializable, Cloneable {\r\n\r\n");
		code.append("\tprivate static final long serialVersionUID = 1L;\r\n\r\n");
		// 定义变量
		for (int i = 0; i < isize; i++) {
			code.append("\tprivate ");
			code.append(t[i]);
			code.append(" ");
			code.append(n[i].toLowerCase());
			code.append(";\r\n");
		}
		// 构造函数
		code.append("\r\n\tpublic ");
		code.append(objname);
		code.append("(){\r\n\t}\r\n\r\n ");

		// get,set函数
		for (int i = 0; i < isize; i++) {
			code.append("\tpublic ");
			code.append(t[i]);
			code.append(" get");
			code.append(n[i].substring(0, 1).toUpperCase());
			code.append(n[i].substring(1, n[i].length()).toLowerCase());
			code.append("() {\r\n");
			code.append("\t\treturn this.");
			code.append(n[i].toLowerCase());
			code.append(";\r\n\t}\r\n\r\n");

			code.append("\tpublic void set");
			code.append(n[i].substring(0, 1).toUpperCase());
			code.append(n[i].substring(1, n[i].length()).toLowerCase());
			code.append("(");
			code.append(t[i]);
			code.append(" ");
			code.append(n[i].toLowerCase());
			code.append(") {\r\n");
			code.append("\t\tthis.");
			code.append(n[i].toLowerCase());
			code.append("=");
			code.append(n[i].toLowerCase());
			code.append(";\r\n\t}\r\n\r\n");
		}
		// toString
		code.append("\tpublic String toString() {\r\n");
		code.append("\t\treturn new ToStringBuilder(this)\r\n");
		code.append("\t\t.append(\"");
		code.append(n[0].toLowerCase());
		code.append("\",get");
		code.append(n[0].substring(0, 1).toUpperCase());
		code.append(n[0].substring(1, n[0].length()).toLowerCase());
		code.append("()).toString();\r\n\t}\r\n\r\n");
		// equals
		code.append("\tpublic boolean equals(Object other) {\r\n");
		code.append("\t\tif (!(other instanceof ");
		code.append(objname);
		code.append("))\r\n\t\treturn false;\r\n\t\t");
		code.append(objname);
		code.append(" castOther = (");
		code.append(objname);
		code.append(")other;\r\n");
		code.append("\t\treturn new EqualsBuilder().append(");
		code.append(n[0].toLowerCase());
		code.append(", castOther.");
		code.append(n[0].toLowerCase());
		code.append(").isEquals();\r\n\t}\r\n\r\n");

		code.append("\tpublic int hashCode() {\r\n");
		code.append("\t\treturn new HashCodeBuilder().append(");
		code.append(n[0].toLowerCase());
		code.append(").toHashCode();\r\n\t}\r\n\r\n");

		code.append("}");

		if (createfile) {
			creatCodeFile(bopath, objname + ".java", code.toString());
		} else {
			System.out.println(code.toString());
		}

	}

	private void createManagerFile() {
		StringBuffer code = new StringBuffer();
		code.append("package ");
		code.append(basepackage);
		code.append("service;\r\n\r\n");

		code.append("import java.util.List;\r\n\r\n");
		code.append("import ");
		code.append(basepackage);
		code.append("bo.");
		code.append(objname);
		code.append(";\r\n");
		code.append("import com.util.search.PageList;\r\n");
		code.append("import com.util.search.SearchModel;\r\n\r\n");

		code.append("/**\r\n");
		code.append(" *<p>Description: ");
		code.append(title);
		code.append("</p>\r\n");
		code.append(" *<p>E-mail: zhangxuedong28@gmail.com</p>\r\n");
		code.append(" *@version 1.0\r\n");
		code.append(" */\r\n");
		code.append("public interface ");
		code.append(objname);
		code.append("Manager {\r\n");
		// 取对象
		code.append("\t/**\r\n");
		code.append("\t *根据id获取");
		code.append(objnamecn);
		code.append("\r\n");
		code.append("\t *@param ");
		code.append(n[0].toLowerCase());
		code.append(" ");
		code.append(t[0]);
		code.append("\r\n");
		code.append("\t *@return ");
		code.append(objname);
		code.append("\r\n\t */\r\n");
		code.append("\tpublic ");
		code.append(objname);
		code.append(" get");
		code.append(objname);
		code.append("(");
		code.append("String");
		code.append(" ");
		code.append(n[0].toLowerCase());
		code.append(");\r\n\r\n");
		
		code.append("\t/**\r\n");
		code.append("\t *根据id获取");
		code.append(objnamecn);
		code.append("\r\n");
		code.append("\t *@param ");
		code.append(n[0].toLowerCase());
		code.append(" ");
		code.append(t[0]);
		code.append("\r\n");
		code.append("\t *@return ");
		code.append(objname);
		code.append("\r\n\t */\r\n");
		code.append("\tpublic ");
		code.append(objname);
		code.append(" get");
		code.append(objname);
		code.append("(");
		code.append("Integer");
		code.append(" ");
		code.append(n[0].toLowerCase());
		code.append(");\r\n\r\n");

		// 增加
		code.append("\t/**\r\n");
		code.append("\t *增加");
		code.append(objnamecn);
		code.append("\r\n");
		code.append("\t *@param ");
		code.append(objname.substring(0, 1).toLowerCase());
		code.append(objname.substring(1, objname.length()));
		code.append(" ");
		code.append(objname);
		code.append("\r\n");
		code.append("\t *@return ");
		code.append(objname);
		code.append("\r\n\t */\r\n");
		code.append("\tpublic ");
		code.append(objname);
		code.append(" add");
		code.append(objname);
		code.append("(");
		code.append(objname);
		code.append(" ");
		code.append(objname.substring(0, 1).toLowerCase());
		code.append(objname.substring(1, objname.length()));
		code.append(");\r\n\r\n");

		// 删除
		code.append("\t/**\r\n");
		code.append("\t *删除");
		code.append(objnamecn);
		code.append("\r\n");
		code.append("\t *@param ");
		code.append(n[0].toLowerCase());
		code.append(" ");
		code.append(t[0]);
		code.append("\r\n");
		code.append("\t *@return ");
		code.append(objname);
		code.append("\r\n\t */\r\n");
		code.append("\tpublic ");
		code.append(objname);
		code.append(" del");
		code.append(objname);
		code.append("(");
		code.append("String");
		code.append(" ");
		code.append(n[0].toLowerCase());
		code.append(");\r\n\r\n");
		
		code.append("\t/**\r\n");
		code.append("\t *删除");
		code.append(objnamecn);
		code.append("\r\n");
		code.append("\t *@param ");
		code.append(objname.substring(0, 1).toLowerCase());
		code.append(objname.substring(1, objname.length()));
		code.append(" ");
		code.append(objname);
		code.append("\r\n");
		code.append("\t *@return ");
		code.append(objname);
		code.append("\r\n\t */\r\n");
		code.append("\tpublic ");
		code.append(objname);
		code.append(" del");
		code.append(objname);
		code.append("(");
		code.append(objname);
		code.append(" ");
		code.append(objname.substring(0, 1).toLowerCase());
		code.append(objname.substring(1, objname.length()));
		code.append(");\r\n\r\n");

		// 修改
		code.append("\t/**\r\n");
		code.append("\t *修改");
		code.append(objnamecn);
		code.append("\r\n");
		code.append("\t *@param ");
		code.append(objname.substring(0, 1).toLowerCase());
		code.append(objname.substring(1, objname.length()));
		code.append(" ");
		code.append(objname);
		code.append("\r\n");
		code.append("\t *@return ");
		code.append(objname);
		code.append("\r\n\t */\r\n");
		code.append("\tpublic ");
		code.append(objname);
		code.append(" update");
		code.append(objname);
		code.append("(");
		code.append(objname);
		code.append(" ");
		code.append(objname.substring(0, 1).toLowerCase());
		code.append(objname.substring(1, objname.length()));
		code.append(");\r\n\r\n");

		// 取对象集合
		code.append("\t/**\r\n");
		code.append("\t *获取");
		code.append(objnamecn);
		code.append("集合\r\n");
		code.append("\t *@return List\r\n\t */\r\n");
		code.append("\tpublic List get");
		code.append(objname);
		code.append("s (List<SearchModel> condition, String orderby, int pagesize);\r\n\r\n");

		// 取一页对象集合
		code.append("\t/**\r\n");
		code.append("\t *获取一页");
		code.append(objnamecn);
		code.append("集合\r\n");
		code.append("\t *@param start int\r\n");
		code.append("\t *@param pagesize int\r\n");
		code.append("\t *@return PageList\r\n\t */\r\n");
		code.append("\tpublic PageList getPage");
		code.append(objname);
		code.append("s (List<SearchModel> condition, String orderby, int start, int pagesize);\r\n\r\n");

		code.append("}");

		if (createfile) {
			creatCodeFile(managerpath, objname + "Manager.java", code
					.toString());
		} else {
			System.out.println(code.toString());
		}

	}

	private void createManagerImplFile() {
		StringBuffer code = new StringBuffer();
		code.append("package ");
		code.append(basepackage);
		code.append("service.impl;\r\n\r\n");

		code.append("import java.util.List;\r\n\r\n");
		
		code.append("import ");
		code.append(basepackage);
		code.append("bo.");
		code.append(objname);
		code.append(";\r\n");
		code.append("import ");
		code.append(basepackage);
		code.append("service.");
		code.append(objname);
		code.append("Manager;\r\n\r\n");
		code.append("import com.util.dao.BaseDAO;\r\n");
		code.append("import com.util.search.PageList;\r\n");
		code.append("import com.util.search.SearchModel;\r\n\r\n");

		code.append("/**\r\n");
		code.append(" *<p>Description: ");
		code.append(title);
		code.append("</p>\r\n");
		code.append(" *<p>E-mail: zhangxuedong28@gmail.com</p>\r\n");
		code.append(" *@version 1.0\r\n");
		code.append(" */\r\n");
		code.append("public class ");
		code.append(objname);
		code.append("ManagerImpl implements ");
		code.append(objname);
		code.append("Manager{\r\n\r\n");

		code.append("\tprivate BaseDAO baseDAO;\r\n");
		code.append("\tprivate String modelname = \"");
		code.append(objnamecn);
		code.append("\";\r\n\r\n");

		code.append("\t/**\r\n");
		code.append("\t *加载baseDAO \r\n");
		code.append("\t *@param BaseDAO baseDAO");
		code.append("\r\n\t */\r\n");
		code.append("\tpublic void setBaseDAO(BaseDAO baseDAO) {\r\n");
		code.append("\t\tthis.baseDAO = baseDAO;\r\n");
		code.append("\t}\r\n\r\n");

		// 取对象
		code.append("\t/**\r\n");
		code.append("\t *根据id获取");
		code.append(objnamecn);
		code.append("\r\n");
		code.append("\t *@param ");
		code.append(n[0].toLowerCase());
		code.append(" ");
		code.append(t[0]);
		code.append("\r\n");
		code.append("\t *@return ");
		code.append(objname);
		code.append("\r\n\t */\r\n");
		code.append("\tpublic ");
		code.append(objname);
		code.append(" get");
		code.append(objname);
		code.append("(");
		code.append("String");
		code.append(" ");
		code.append(n[0].toLowerCase());
		code.append("){\r\n");
		code.append("\t\tInteger iid = Integer.valueOf("+n[0].toLowerCase()+");\r\n");
		code.append("\t\treturn  (");
		code.append(objname);
		code.append(")baseDAO.getObject(modelname,");
		code.append(objname);
		code.append(".class,");
		code.append("iid");
		code.append(");\r\n\t}\r\n\r\n");
		
		code.append("\t/**\r\n");
		code.append("\t *根据id获取");
		code.append(objnamecn);
		code.append("\r\n");
		code.append("\t *@param ");
		code.append(n[0].toLowerCase());
		code.append(" ");
		code.append(t[0]);
		code.append("\r\n");
		code.append("\t *@return ");
		code.append(objname);
		code.append("\r\n\t */\r\n");
		code.append("\tpublic ");
		code.append(objname);
		code.append(" get");
		code.append(objname);
		code.append("(");
		code.append("Integer");
		code.append(" ");
		code.append(n[0].toLowerCase());
		code.append("){\r\n\t\treturn  (");
		code.append(objname);
		code.append(")baseDAO.getObject(modelname,");
		code.append(objname);
		code.append(".class,");
		code.append(n[0].toLowerCase());
		code.append(");\r\n\t}\r\n\r\n");

		// 增加
		code.append("\t/**\r\n");
		code.append("\t *增加");
		code.append(objnamecn);
		code.append("\r\n");
		code.append("\t *@param ");
		code.append(objname.substring(0, 1).toLowerCase());
		code.append(objname.substring(1, objname.length()));
		code.append(" ");
		code.append(objname);
		code.append("\r\n");
		code.append("\t *@return ");
		code.append(objname);
		code.append("\r\n\t */\r\n");
		code.append("\tpublic ");
		code.append(objname);
		code.append(" add");
		code.append(objname);
		code.append("(");
		code.append(objname);
		code.append(" ");
		code.append(objname.substring(0, 1).toLowerCase());
		code.append(objname.substring(1, objname.length()));
		code.append("){\r\n\t\treturn (");
		code.append(objname);
		code.append(")baseDAO.addObject(modelname,");
		code.append(objname.substring(0, 1).toLowerCase());
		code.append(objname.substring(1, objname.length()));
		code.append(");\r\n\t}\r\n\r\n");

		// 删除
		code.append("\t/**\r\n");
		code.append("\t *删除");
		code.append(objnamecn);
		code.append("\r\n");
		code.append("\t *@param ");
		code.append(n[0].toLowerCase());
		code.append(" ");
		code.append(t[0]);
		code.append("\r\n");
		code.append("\t *@return ");
		code.append(objname);
		code.append("\r\n\t */\r\n");
		code.append("\tpublic ");
		code.append(objname);
		code.append(" del");
		code.append(objname);
		code.append("(");
		// code.append(t[0]);
		code.append("String");
		code.append(" ");
		code.append(n[0].toLowerCase());
		code.append("){\r\n\t\t");
		code.append(objname);
		code.append(" model = get");
		code.append(objname);
		code.append("(");
		code.append(n[0].toLowerCase());
		code.append(");\r\n\t\treturn (");
		code.append(objname);
		code.append(")baseDAO.delObject(modelname,model);\r\n\t}\r\n\r\n");
		
		code.append("\t/**\r\n");
		code.append("\t *删除");
		code.append(objnamecn);
		code.append("\r\n");
		code.append("\t *@param ");
		code.append(objname.substring(0, 1).toLowerCase());
		code.append(objname.substring(1, objname.length()));
		code.append(" ");
		code.append(objname);
		code.append("\r\n");
		code.append("\t *@return ");
		code.append(objname);
		code.append("\r\n\t */\r\n");
		code.append("\tpublic ");
		code.append(objname);
		code.append(" del");
		code.append(objname);
		code.append("(");
		code.append(objname);
		code.append(" ");
		code.append(objname.substring(0, 1).toLowerCase());
		code.append(objname.substring(1, objname.length()));
		code.append("){\r\n\t\treturn (");
		code.append(objname);
		code.append(")baseDAO.delObject(modelname,");
		code.append(objname.substring(0, 1).toLowerCase());
		code.append(objname.substring(1, objname.length()));
		code.append(");\r\n\t}\r\n\r\n");

		// 修改
		code.append("\t/**\r\n");
		code.append("\t *修改");
		code.append(objnamecn);
		code.append("\r\n");
		code.append("\t *@param ");
		code.append(objname.substring(0, 1).toLowerCase());
		code.append(objname.substring(1, objname.length()));
		code.append(" ");
		code.append(objname);
		code.append("\r\n");
		code.append("\t *@return ");
		code.append(objname);
		code.append("\r\n\t */\r\n");
		code.append("\tpublic ");
		code.append(objname);
		code.append(" update");
		code.append(objname);
		code.append("(");
		code.append(objname);
		code.append(" ");
		code.append(objname.substring(0, 1).toLowerCase());
		code.append(objname.substring(1, objname.length()));
		code.append("){\r\n\t\treturn (");
		code.append(objname);
		code.append(")baseDAO.updateObject(modelname,");
		code.append(objname.substring(0, 1).toLowerCase());
		code.append(objname.substring(1, objname.length()));
		code.append(");\r\n\t}\r\n\r\n");

		// 取对象集合
		code.append("\t/**\r\n");
		code.append("\t *获取");
		code.append(objnamecn);
		code.append("集合\r\n ");
		code.append("\t *@return List\r\n\t */\r\n");
		code.append("\tpublic List get");
		code.append(objname);
		code.append("s(List<SearchModel> condition,String orderby,int pagesize){\r\n\t\treturn baseDAO.getObjects(\"");
		code.append(objname);
		code.append("\",condition,orderby,pagesize);\r\n\t}\r\n\r\n");

		// 取一页对象集合
		code.append("\t/**\r\n");
		code.append("\t *获取一页");
		code.append(objnamecn);
		code.append("集合\r\n");
		code.append("\t *@param start int\r\n");
		code.append("\t *@param pagesize int\r\n");
		code.append("\t *@return PageList\r\n\t */\r\n");
		code.append("\tpublic PageList getPage");
		code.append(objname);
		code.append("s (List<SearchModel> condition,String orderby,int start,int pagesize){\r\n\t\treturn baseDAO.getPageObjects(\"");
		code.append(objname);
		code.append("\",\"");
		code.append(n[0].toLowerCase());
		code.append("\",condition, orderby, start,pagesize);\r\n\t}\r\n\r\n}");

		if (createfile) {
			creatCodeFile(managerimplpath, objname + "ManagerImpl.java", code
					.toString());
		} else {
			System.out.println(code.toString());
		}

	}

	public void createDaoInclude() {
		StringBuffer code = new StringBuffer();
		code.append("<bean id=\"");
		code.append(objname.substring(0, 1).toLowerCase());
		code.append(objname.substring(1, objname.length()));
		code.append("DAO\" class=\"");
		code.append(basepackage);
		code.append("dao.hibernate.");
		code.append(objname);
		code.append("DAOHibernate\">\n\r");
		code.append("\t<property name=\"sessionFactory\" ref=\"sessionFactory\"/>\n\r");
		code.append("</bean>");
		System.out.println(code.toString());
	}

	public void createManagerInclude() {
		StringBuffer code = new StringBuffer();
		code.append("<bean id=\"");
		code.append(objname.substring(0, 1).toLowerCase());
		code.append(objname.substring(1, objname.length()));
		code.append("Manager\" parent=\"txProxyTemplate\">\r\t<property name=\"target\">\r\t\t<bean class=\"");
		code.append(basepackage);
		code.append("service.impl.");
		code.append(objname);
		code.append("ManagerImpl\">\r");
		code.append("\t\t\t<property name=\"baseDAO\" ref=\"baseDAO\"/>");
		code.append("\r\t\t</bean>\r\t</property>\r</bean>");
		System.out.println(code.toString());
	}

	public void createManagerInterface() {
		StringBuffer code = new StringBuffer();
		code.append("/**\r\n");
		code.append("*得到");
		code.append(objnamecn);
		code.append("接口\r\n*/\r\n");

		code.append("public static ");
		code.append(objname);
		code.append("Manager get");
		code.append(objname);
		code.append("Manager(){\n\r");
		code.append(objname);
		code.append("Manager manager=(");
		code.append(objname);
		code.append("Manager)ctx.getBean(\"");
		code.append(objname.substring(0, 1).toLowerCase());
		code.append(objname.substring(1, objname.length()));
		code.append("Manager\");\n\rreturn manager;\n\r}");
		System.out.println(code.toString());
	}

	private void createActionFormFile() {
		StringBuffer code = new StringBuffer();
		code.append("package ");
		code.append(basepackage);
		code.append("web.form;\r\n\r\n");

		code.append("import javax.servlet.http.HttpServletRequest;\r\n\r\n");
		
		code.append("import org.apache.struts.action.ActionErrors;\r\n");
		code.append("import org.apache.struts.action.ActionForm;\r\n");
		code.append("import org.apache.struts.action.ActionMapping;\r\n\r\n");
		
		code.append("import ");
		code.append(basepackage);
		code.append("bo.");
		code.append(objname);
		code.append(";\r\n\r\n");

		code.append("/**\r\n");
		code.append(" *<p>Description: ");
		code.append(title);
		code.append("</p>\r\n");
		code.append(" *<p>E-mail: zhangxuedong28@gmail.com</p>\r\n");
		code.append(" *@version 1.0\r\n");
		code.append(" */\r\n");

		code.append("public class ");
		code.append(objname);
		code.append("ActionForm extends ActionForm {\r\n\r\n");
		// 定义对象
		code.append("\tprivate static final long serialVersionUID = 1L;\r\n\r\n");
		code.append("\tprivate String act;\r\n");
		code.append("\tprivate ");
		code.append(objname);
		code.append(" ");
		code.append(objname.substring(0, 1).toLowerCase());
		code.append(objname.substring(1, objname.length()));
		code.append(" = new ");
		code.append(objname);
		code.append("();\r\n\r\n");

		// getAct
		code.append("\tpublic String getAct() {\r\n");
		code.append("\t\treturn act;\r\n");
		code.append("\t}\r\n\r\n");

		// setAct
		code.append("\tpublic void setAct(String act) {\r\n");
		code.append("\t\tthis.act = act;");
		code.append("\t}\r\n\r\n");

		// getObj
		code.append("\tpublic ");
		code.append(objname);
		code.append(" get");
		code.append(objname);
		code.append("(){\r\n");
		code.append("\t\treturn this.");
		code.append(objname.substring(0, 1).toLowerCase());
		code.append(objname.substring(1, objname.length()));
		code.append(";\r\n\t}\r\n\r\n");

		// setObj
		code.append("\tpublic void set");
		code.append(objname);
		code.append("(");
		code.append(objname);
		code.append(" ");
		code.append(objname.substring(0, 1).toLowerCase());
		code.append(objname.substring(1, objname.length()));
		code.append("){\r\n");
		code.append("\t\tthis.");
		code.append(objname.substring(0, 1).toLowerCase());
		code.append(objname.substring(1, objname.length()));
		code.append("=");
		code.append(objname.substring(0, 1).toLowerCase());
		code.append(objname.substring(1, objname.length()));
		code.append(";\r\n\t}\r\n\r\n");

		// validate
		code.append("\tpublic ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {\r\n");
		code.append("\t\treturn null;\r\n");
		code.append("\t}\r\n\r\n");

		// reset
		code.append("\tpublic void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {\r\n");
		code.append("\t}\r\n}\r\r");

		if (createfile) {
			creatCodeFile(actionformpath, objname + "ActionForm.java", code.toString());
		} else {
			System.out.println(code.toString());
		}

	}

	private void createActionFormInclude() {
		StringBuffer code = new StringBuffer();
		code.append("<form-bean name=\"");
		code.append(objname.substring(0, 1).toLowerCase());
		code.append(objname.substring(1, objname.length()));
		code.append("ActionForm\" type=\"");
		code.append(basepackage);
		code.append("web.form.");
		code.append(objname);
		code.append("ActionForm\" />");
		System.out.println(code.toString());
	}

	private void createActionFile() {
		StringBuffer code = new StringBuffer();
		code.append("package ");
		code.append(basepackage);
		code.append("web.action;\r\n\r\n");

		code.append("import java.util.ArrayList;\r\n");
		code.append("import java.util.List;\r\n\r\n");
		
		code.append("import javax.servlet.http.HttpServletRequest;\r\n");
		code.append("import javax.servlet.http.HttpServletResponse;\r\n\r\n");
		
		code.append("import org.apache.struts.action.ActionForm;\r\n");
		code.append("import org.apache.struts.action.ActionForward;\r\n");
		code.append("import org.apache.struts.action.ActionMapping;\r\n\r\n");
		
		code.append("import ");
		code.append(basepackage);
		code.append("bo.");
		code.append(objname);
		code.append(";\r\n");
		code.append("import ");
		code.append(basepackage);
		code.append("service.");
		code.append(objname);
		code.append("Manager;\r\n");
		code.append("import ");
		code.append(basepackage);
		code.append("web.form.");
		code.append(objname);
		code.append("ActionForm;\r\n\r\n");
		code.append("import com.util.action.BaseAction;\r\n");
		code.append("import com.util.search.PageList;\r\n");
		code.append("import com.util.search.PageUtil;\r\n");
		code.append("import com.util.search.SearchModel;\r\n");
		code.append("import com.util.string.Encode;\r\n\r\n");
		
		code.append("/**\r\n");
		code.append(" *<p>Description: ");
		code.append(title);
		code.append("</p>\r\n");
		code.append(" *<p>E-mail: zhangxuedong28@gmail.com</p>\r\n");
		code.append(" *@version 1.0\r\n");
		code.append(" */\r\n");

		code.append("public class ");
		code.append(objname);
		code.append("Action extends BaseAction {\r\n\r\n");
		// list
		code.append("\t/**\r\n");
		code.append("\t *列表显示\r\n");
		code.append("\t *@param actionMapping ActionMapping\r\n");
		code.append("\t *@param actionForm ActionForm\r\n");
		code.append("\t *@param httpServletRequest HttpServletRequest\r\n");
		code.append("\t *@param httpServletResponse HttpServletResponse\r\n");
		code.append("\t *@return ActionForward\r\n");
		code.append("\t */\r\n");
		code.append("\tpublic ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {\r\n");
		code.append("\t\t");
		code.append(objname);
		code.append("Manager manager = (");
		code.append(objname);
		code.append("Manager)getBean(\"");
		code.append(objname.substring(0, 1).toLowerCase());
		code.append(objname.substring(1, objname.length()));
		code.append("Manager\");\r\n");
		code.append("\t\tPageUtil pageUtil = new PageUtil(httpServletRequest);\r\n");
		code.append("\t\tList<SearchModel> condition = new ArrayList<SearchModel>();\r\n");
		code.append("\t\tPageList page = manager.getPage");
		code.append(objname);
		code.append("s(condition, \"\", pageUtil.getStartCount(), pageUtil.getPageSize());\r\n");
		code.append("\t\thttpServletRequest.setAttribute(\"pagelist\", page);\r\n");
		code.append("\t\treturn actionMapping.findForward(\"list\");\r\n");
		code.append("\t}\r\n\r\n");
		// beforeAdd
		code.append("\t/**\r\n");
		code.append("\t *增加前\r\n");
		code.append("\t *@param actionMapping ActionMapping\r\n");
		code.append("\t *@param actionForm ActionForm\r\n");
		code.append("\t *@param httpServletRequest HttpServletRequest\r\n");
		code.append("\t *@param httpServletResponse HttpServletResponse\r\n");
		code.append("\t *@return ActionForward\r\n");
		code.append("\t */\r\n");
		code.append("\tpublic ActionForward beforeAdd(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {\r\n");
		code.append("\t\t");
		code.append(objname);
		code.append(" model = new ");
		code.append(objname);
		code.append("();\r\n");
		code.append("\t\thttpServletRequest.setAttribute(\"model\", model);\r\n");
		code.append("\t\thttpServletRequest.setAttribute(\"act\", \"addSave\");\r\n\r\n");
		code.append("\t\tsaveToken(httpServletRequest);\r\n");
		code.append("\t\treturn actionMapping.findForward(\"edit\");\r\n\t}\r\n\r\n");
		//addSave
		code.append("\t/**\r\n");
		code.append("\t *增加时保存\r\n");
		code.append("\t *@param actionMapping ActionMapping\r\n");
		code.append("\t *@param actionForm ActionForm\r\n");
		code.append("\t *@param httpServletRequest HttpServletRequest\r\n");
		code.append("\t *@param httpServletResponse HttpServletResponse\r\n");
		code.append("\t *@return ActionForward\r\n");
		code.append("\t */\r\n");
		code.append("\tpublic ActionForward addSave(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {\r\n");
		code.append("\t\t");
		code.append(objname);
		code.append("ActionForm form = (");
		code.append(objname);
		code.append("ActionForm)actionForm;\r\n");
		code.append("\t\t");
		code.append(objname);
		code.append("Manager manager = (");
		code.append(objname);
		code.append("Manager)getBean(\"");
		code.append(objname.substring(0, 1).toLowerCase());
		code.append(objname.substring(1, objname.length()));
		code.append("Manager\");\r\n");

		code.append("\t\tif (isTokenValid(httpServletRequest, true)) {\r\n");
		code.append("\t\t\ttry {\r\n");
		code.append("\t\t\t\t");
		code.append(objname);
		code.append(" model = form.get");
		code.append(objname);
		code.append("();\r\n");
		code.append("\t\t\t\tmanager.add");
		code.append(objname);
		code.append("(model);\r\n");
		code.append("\t\t\t\taddLog(httpServletRequest,\"增加了一个");
		code.append(objnamecn);
		code.append("\");\r\n\t\t\t}catch (Exception e){\r\n");
		code.append("\t\t\t}\r\n");
		code.append("\t\t}\r\n\r\n");
		code.append("\t\treturn list(actionMapping, actionForm, httpServletRequest, httpServletResponse);\r\n\t}\r\n\r\n");

		code.append("\t/**\r\n");
		code.append("\t *修改前\r\n");
		code.append("\t *@param actionMapping ActionMapping\r\n");
		code.append("\t *@param actionForm ActionForm\r\n");
		code.append("\t *@param httpServletRequest HttpServletRequest\r\n");
		code.append("\t *@param httpServletResponse HttpServletResponse\r\n");
		code.append("\t *@return ActionForward\r\n");
		code.append("\t */\r\n");
		code.append("\tpublic ActionForward beforeUpdate(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {\r\n");
		code.append("\t\t");
		code.append(objname);
		code.append("Manager manager = (");
		code.append(objname);
		code.append("Manager)getBean(\"");
		code.append(objname.substring(0, 1).toLowerCase());
		code.append(objname.substring(1, objname.length()));
		code.append("Manager\");\r\n");
		code.append("\t\tString objid = Encode.nullToBlank(httpServletRequest.getParameter(\"objid\"));\r\n");
		code.append("\t\ttry {\r\n");
		code.append("\t\t\t");
		code.append(objname);
		code.append(" model = manager.get");
		code.append(objname);
		code.append("(objid);\r\n");
		code.append("\t\t\thttpServletRequest.setAttribute(\"act\", \"updateSave\");\r\n");
		code.append("\t\t\thttpServletRequest.setAttribute(\"model\", model);\r\n");
		code.append("\t\t}catch (Exception e){\r\n");
		code.append("\t\t}\r\n\r\n");
		code.append("\t\tsaveToken(httpServletRequest);\r\n");
		code.append("\t\treturn actionMapping.findForward(\"edit\");\r\n\t}\r\n\r\n");

		code.append("\t/**\r\n");
		code.append("\t *修改时保存\r\n");
		code.append("\t *@param actionMapping ActionMapping\r\n");
		code.append("\t *@param actionForm ActionForm\r\n");
		code.append("\t *@param httpServletRequest HttpServletRequest\r\n");
		code.append("\t *@param httpServletResponse HttpServletResponse\r\n");
		code.append("\t *@return ActionForward\r\n");
		code.append("\t */\r\n");
		code.append("\tpublic ActionForward updateSave(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {\r\n");
		code.append("\t\t");
		code.append(objname);
		code.append("ActionForm form = (");
		code.append(objname);
		code.append("ActionForm)actionForm;\r\n");
		code.append("\t\t");
		code.append(objname);
		code.append("Manager manager = (");
		code.append(objname);
		code.append("Manager)getBean(\"");
		code.append(objname.substring(0, 1).toLowerCase());
		code.append(objname.substring(1, objname.length()));
		code.append("Manager\");\r\n");

		code.append("\t\tif (isTokenValid(httpServletRequest, true)) {\r\n");
		code.append("\t\t\ttry {\r\n");
		code.append("\t\t\t\t");
		code.append(objname);
		code.append(" model = form.get");
		code.append(objname);
		code.append("();\r\n");
		code.append("\t\t\t\tmanager.update");
		code.append(objname);
		code.append("(model);\r\n");
		code.append("\t\t\t\taddLog(httpServletRequest,\"修改了一个");
		code.append(objnamecn);
		code.append("\");\r\n\t\t\t}catch (Exception e){\r\n");
		code.append("\t\t\t}\r\n");
		code.append("\t\t}\r\n\r\n");
		code.append("\t\treturn list(actionMapping, actionForm, httpServletRequest, httpServletResponse);\r\n\t}\r\n\r\n");

		code.append("\t/**\r\n");
		code.append("\t *批量删除\r\n");
		code.append("\t *@param actionMapping ActionMapping\r\n");
		code.append("\t *@param actionForm ActionForm\r\n");
		code.append("\t *@param httpServletRequest HttpServletRequest\r\n");
		code.append("\t *@param httpServletResponse HttpServletResponse\r\n");
		code.append("\t *@return ActionForward\r\n");
		code.append("\t */\r\n");
		code.append("\tpublic ActionForward delBatchRecord(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {\r\n");
		code.append("\t\t");
		code.append(objname);
		code.append("Manager manager = (");
		code.append(objname);
		code.append("Manager)getBean(\"");
		code.append(objname.substring(0, 1).toLowerCase());
		code.append(objname.substring(1, objname.length()));
		code.append("Manager\");\r\n");
		code.append("\t\tString[] checkids = httpServletRequest.getParameterValues(\"checkid\");\n\r");
		code.append("\t\tfor (int i = 0; i < checkids.length; i++) {\r\n");
		code.append("\t\t\tmanager.del");
		code.append(objname);
		code.append("(checkids[i]);\r\n\t\t}\r\n");
		code.append("\t\treturn list(actionMapping, actionForm, httpServletRequest, httpServletResponse);\r\n\t}\r\n}");

		if (createfile) {
			creatCodeFile(actionpath, objname + "Action.java", code.toString());
		} else {
			System.out.println(code.toString());
		}

	}

	private void createActionInclude() {
		StringBuffer code = new StringBuffer();
		code.append("<action name=\"");
		code.append(objname.substring(0, 1).toLowerCase());
		code.append(objname.substring(1, objname.length()));
		code.append("ActionForm\" parameter=\"method\" path=\"/");
		code.append(objname.substring(0, 1).toLowerCase());
		code.append(objname.substring(1, objname.length()));
		code.append("Action\" scope=\"request\" type=\"");
		code.append(basepackage);
		code.append("web.action.");
		code.append(objname);
		code.append("Action\" validate=\"false\">\r\n");
		code.append("\t<forward contextRelative=\"true\" name=\"list\" path=\"\"/>\r\n");
		code.append("\t<forward contextRelative=\"true\" name=\"edit\" path=\"\"/>\r\n");
		code.append("</action>");
		System.out.println(code.toString());
	}

	public void creatCode() {
		getTableField(tablename);
		createHbmXmlFile();
		createBoFile();
		createManagerFile();
		createManagerImplFile();
		createManagerInclude();
		createActionFormFile();
		createActionFormInclude();
		createActionFile();
		createActionInclude();
	}

	public static void main(String[] args) {
		CreateSpringBaseCode createSpringBaseCode1 = new CreateSpringBaseCode();
		createSpringBaseCode1.creatCode();
	}
}
