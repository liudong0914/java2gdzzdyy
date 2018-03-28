package com.wkmk.util.action;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.util.action.BaseAction;
import com.util.search.SearchModel;
import com.wkmk.sys.bo.SysAreaInfo;
import com.wkmk.sys.service.SysAreaInfoManager;

public class ExportAreaAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		SysAreaInfoManager areainfoManager = (SysAreaInfoManager) getBean("sysAreaInfoManager");
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("地域导出表");
		HSSFDataFormat format = wb.createDataFormat();
		sheet.setColumnWidth((short) 3, 20 * 256);// 设置单元格宽度
		sheet.setColumnWidth((short) 4, 20 * 256);
		sheet.setDefaultRowHeight((short) 300);// 统一单元格高度
		// 设置样式
		HSSFCellStyle style = wb.createCellStyle();
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平
		// 设置字体样式
		Font font = wb.createFont();
		font.setFontHeightInPoints((short) 12);
		font.setFontName("Courier New");
		font.setItalic(true);
		style.setFont(font);
		HSSFRow row1 = sheet.createRow(0);
		HSSFCell cell = row1.createCell((short) 0);
		cell.setCellValue("唯一标识");
		cell.setCellStyle(style);
		cell = row1.createCell((short) 1);
		cell.setCellValue("省");
		cell.setCellStyle(style);
		cell = row1.createCell((short) 2);
		cell.setCellValue("市");
		cell.setCellStyle(style);
		cell = row1.createCell((short) 3);
		cell.setCellValue("区");
		cell.setCellStyle(style);
		cell = row1.createCell((short) 4);
		cell.setCellValue("学校名称");
		cell.setCellStyle(style);
		List<SearchModel> condiitoin = new ArrayList<SearchModel>();
		List<SysAreaInfo> areainfolist = areainfoManager.getSysAreaInfos(condiitoin, "areano", 0);
		Map<String, SysAreaInfo> map = new HashMap<String, SysAreaInfo>();
		for (int i = 0; i < areainfolist.size(); i++) {
			map.put(areainfolist.get(i).getAreano(), areainfolist.get(i));
		}
		for (int i = 0; i < areainfolist.size(); i++) {
			HSSFRow row2 = sheet.createRow(1+i);
			HSSFCell rowcell = row2.createCell((short) 0);
			rowcell.setCellValue(areainfolist.get(i).getAreaid());
			if (areainfolist.get(i).getAreano().length() == 4) {
				// 当城市为省的时候
				rowcell = row2.createCell((short) 1);
				rowcell.setCellValue(areainfolist.get(i).getAreaname());
			} else if (areainfolist.get(i).getAreano().length() == 6) {
				// 当城市为市的时候
				rowcell = row2.createCell((short) 1);
				rowcell.setCellValue(map.get(areainfolist.get(i).getParentno()).getAreaname());
				rowcell = row2.createCell((short) 2);
				rowcell.setCellValue(areainfolist.get(i).getAreaname());
			} else if (areainfolist.get(i).getAreano().length() == 8) {
				// 当城市为区的时候
				SysAreaInfo areafather = map.get(areainfolist.get(i).getParentno());// 获取父对象
				rowcell = row2.createCell((short) 1);
				rowcell.setCellValue(map.get(areafather.getParentno()).getAreaname());
				rowcell = row2.createCell((short) 2);
				rowcell.setCellValue(areafather.getAreaname());
				rowcell = row2.createCell((short) 3);
				rowcell.setCellValue(areainfolist.get(i).getAreaname());
			}
		}
		FileOutputStream fileout = null;
		try {
			fileout = new FileOutputStream("d:\\workbook.xls");
			wb.write(fileout);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileout != null) {
				try {
					fileout.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return null;
	}
}
