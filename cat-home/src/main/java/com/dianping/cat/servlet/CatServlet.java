package com.dianping.cat.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.unidal.initialization.DefaultModuleContext;
import org.unidal.initialization.ModuleContext;
import org.unidal.initialization.ModuleInitializer;
import org.unidal.web.AbstractContainerServlet;

public class CatServlet extends AbstractContainerServlet {
	private static final long serialVersionUID = 1L;

	private Exception m_exception;

	@Override
	protected void initComponents(ServletConfig servletConfig) throws ServletException {
		try {
			ModuleContext ctx = new DefaultModuleContext(getContainer());
			ModuleInitializer initializer = ctx.lookup(ModuleInitializer.class);
			String catServerXml = servletConfig.getInitParameter("cat-server-xml");

			ctx.setAttribute("cat-client-config-file", new File("/data/appdatas/cat/client.xml"));

			if (catServerXml != null) {
				ctx.setAttribute("cat-server-config-file", new File(catServerXml));
			} else {
				ctx.setAttribute("cat-server-config-file", new File("/data/appdatas/cat/server.xml"));
			}

			initializer.execute(ctx);
		} catch (Exception e) {
			m_exception = e;
			throw new ServletException(e);
		}
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setCharacterEncoding("utf-8");
		res.setContentType("text/plain");

		PrintWriter writer = res.getWriter();

		if (m_exception != null) {
			writer.write("Server has NOT been initialized successfully! \r\n\r\n");
			m_exception.printStackTrace(writer);
		} else {
			writer.write("Not implemented yet!");
		}
	}
}