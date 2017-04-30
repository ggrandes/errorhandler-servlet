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
		errors.putAll(DefaultErrors.getDefaultErrors());
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
			final int extraLen = ((err != null) ? err.tag.length() + err.body.length() : 0);
			final StringBuilder sb = new StringBuilder(32 + extraLen);
			sb.append(mapCode(res.getStatus())).append('-').append(res.getStatus());
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

	private static final String mapCode(final int code) {
		if (code >= 600) { // UNKNOWN
			return "CODE";
		} else if (code >= 500) { // SERVER ERROR
			return "ERROR";
		} else if (code >= 400) { // CLIENT ERROR
			return "ERROR";
		} else if (code >= 300) { // REDIRECT
			return "REDIRECT";
		} else if (code >= 200) { // OK
			return "OK";
		} else if (code >= 100) { // INFO
			return "INFO";
		} else { // UNKNOWN
			return "CODE";
		}
	}

	private static class Error {
		final String tag;
		final String body;

		public Error(final String tag, final String body) {
			this.tag = tag;
			this.body = body;
		}
	}

	private static class DefaultErrors {
		private static final HashMap<Integer, Error> errors = new HashMap<Integer, Error>();

		static {
			put("400", "BAD_REQUEST", //
					"Your browser (or proxy) sent a request that this server could not understand.");
			put("401", "UNAUTHORIZED", //
					"This server could not verify that you are authorized to access");
			put("403", "FORBIDDEN", //
					"You don't have permission to access the requested object.");
			put("404", "NOT_FOUND", //
					"The requested URL was not found on this server.");
			put("405", "METHOD_NOT_ALLOWED", //
					"The method is not allowed for the requested URL.");
			put("408", "REQUEST_TIME_OUT", //
					"The server closed the network connection because the browser didn't finish the request within the specified time.");
			put("410", "GONE", //
					"The requested URL is no longer available on this server and there is no forwarding address.");
			put("411", "LENGTH_REQUIRED", //
					"The request method requires a valid Content-Length header.");
			put("412", "PRECONDITION_FAILED", //
					"The precondition on the request for the URL failed positive evaluation.");
			put("413", "REQUEST_ENTITY_TOO_LARGE", //
					"The method does not allow the data transmitted, or the data volume exceeds the capacity limit.");
			put("414", "REQUEST_URI_TOO_LARGE", //
					"The length of the requested URL exceeds the capacity limit for");
			put("415", "UNSUPPORTED_MEDIA_TYPE", //
					"The server does not support the media type transmitted in the request.");
			put("500", "INTERNAL_SERVER_ERROR", //
					"The server encountered an internal error and was unable to complete your request. Either the server is overloaded or there was an error in a CGI script.");
			put("501", "NOT_IMPLEMENTED", //
					"The server does not support the action requested by the browser.");
			put("502", "BAD_GATEWAY", //
					"The proxy server received an invalid response from an upstream server.");
			put("503", "SERVICE_UNAVAILABLE", //
					"The server is temporarily unable to service your request due to maintenance downtime or capacity problems. Please try again later.");
			put("504", "GATEWAY_TIMEOUT", //
					"The server was acting as a gateway or proxy and did not receive a timely response from the upstream server.");
			put("506", "VARIANT_ALSO_VARIES", //
					"A variant for the requested entity is itself a negotiable resource. Access not possible.");
			put("508", "LOOP_DETECTED", //
					"The server detected an infinite loop while processing the request.");
		}

		private static void put(final String error, final String tag, final String text) {
			errors.put(Integer.valueOf(error), new Error(tag, text));
		}

		public static Map<Integer, Error> getDefaultErrors() {
			return Collections.unmodifiableMap(errors);
		}
	}
}
