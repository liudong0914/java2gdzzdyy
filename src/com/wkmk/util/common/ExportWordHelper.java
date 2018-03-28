package com.wkmk.util.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class ExportWordHelper {

	public static final String PREVIEW_DOC = "/upload/student_job.doc";

	public Template configTemplate(HttpServletRequest request, String temp)
			throws IOException {
		String templatepath = request.getSession().getServletContext()
				.getRealPath("/")
				+ "tk/analysis/ftl/";
		Configuration config = new Configuration();
		Locale loc = Locale.getDefault();
		config.setDirectoryForTemplateLoading(new File(templatepath));
		config.setEncoding(loc, "UTF-8");
		config.setOutputEncoding("UTF-8");
		Template template = config.getTemplate(temp, loc);
		return template;
	}

	public static void toPreview(HttpServletRequest request, String temp,
			Map<?, ?> root) {
		try {
			String previewPath = request.getSession().getServletContext()
					.getRealPath("/")
					+ PREVIEW_DOC;
			ExportWordHelper e = new ExportWordHelper();
			Template template = e.configTemplate(request, temp);
			FileOutputStream fos = new FileOutputStream(previewPath);
			Writer out = new OutputStreamWriter(fos, "UTF-8");
			template.process(root, out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
