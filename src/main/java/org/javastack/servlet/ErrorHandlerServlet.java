package org.javastack.servlet;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class ErrorHandlerServlet extends HttpServlet {
	private static final long serialVersionUID = 42L;
	private static final Charset ISOLatin1 = Charset.forName("ISO-8859-1");
	private Map<Integer, Error> errors = null;

	@Override
	public void init() throws ServletException {
		final HashMap<Integer, Error> errors = new HashMap<Integer, Error>();
		final Enumeration<String> e = getInitParameterNames();
		while (e.hasMoreElements()) {
			final String name = e.nextElement();
			final String value = getInitParameter(name);
			final String[] ss = name.split(":", 2);
			errors.put(Integer.valueOf(ss[0]), new Error(ss[1], value));
		}
		this.errors = Collections.unmodifiableMap(errors);
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) //
			throws ServletException, IOException {
		final Error err = errors.get(res.getStatus());
		final byte[] buf;
		if (true) {
			final StringBuilder sb = new StringBuilder(32 + err.tag.length() + err.body.length());
			sb.append("ERROR-").append(res.getStatus());
			if (err != null) {
				sb.append(" ").append(err.tag).append("\r\n");
				sb.append("\r\n");
				sb.append(err.body).append("\r\n");
			}
			sb.append("\r\n");
			buf = sb.toString().getBytes(ISOLatin1);
		}
		//
		res.resetBuffer();
		res.setContentLength(buf.length);
		res.setContentType("text/plain");
		res.setCharacterEncoding("ISO-8859-1");
		final ServletOutputStream out = res.getOutputStream();
		out.write(buf);
		out.flush();
	}

	private static class Error {
		final String tag;
		final String body;

		public Error(final String tag, final String body) {
			this.tag = tag;
			this.body = body;
		}
	}
}
