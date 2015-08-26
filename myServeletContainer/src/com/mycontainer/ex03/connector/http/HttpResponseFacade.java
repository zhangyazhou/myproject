package com.mycontainer.ex03.connector.http;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class HttpResponseFacade implements HttpServletResponse {

	private HttpServletResponse response;

	public HttpResponseFacade(HttpResponse response) {
		this.response = response;
	}

	@Override
	public void flushBuffer() throws IOException {
		response.flushBuffer();
	}

	@Override
	public int getBufferSize() {
		return response.getBufferSize();
	}

	@Override
	public String getCharacterEncoding() {
		return response.getCharacterEncoding();
	}

	@Override
	public String getContentType() {
		return response.getContentType();
	}

	@Override
	public Locale getLocale() {
		return response.getLocale();
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		return response.getOutputStream();
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		return response.getWriter();
	}

	@Override
	public boolean isCommitted() {
		return response.isCommitted();
	}

	@Override
	public void reset() {
		response.reset();
	}

	@Override
	public void resetBuffer() {
		response.resetBuffer();
	}

	@Override
	public void setBufferSize(int arg0) {
		response.setBufferSize(arg0);
	}

	@Override
	public void setCharacterEncoding(String arg0) {
		response.setCharacterEncoding(arg0);
	}

	@Override
	public void setContentLength(int arg0) {
		response.setContentLength(arg0);
	}

	@Override
	public void setContentType(String arg0) {
		response.setContentType(arg0);
	}

	@Override
	public void setLocale(Locale arg0) {
		response.setLocale(arg0);
	}

	@Override
	public void addCookie(Cookie arg0) {
		response.addCookie(arg0);
	}

	@Override
	public void addDateHeader(String arg0, long arg1) {
		response.addDateHeader(arg0, arg1);
	}

	@Override
	public void addHeader(String arg0, String arg1) {
		response.addHeader(arg0, arg1);
	}

	@Override
	public void addIntHeader(String arg0, int arg1) {
		response.addIntHeader(arg0, arg1);
	}

	@Override
	public boolean containsHeader(String arg0) {
		return response.containsHeader(arg0);
	}

	@Override
	public String encodeRedirectURL(String arg0) {
		return response.encodeRedirectURL(arg0);
	}

	@Override
	public String encodeRedirectUrl(String arg0) {
		return response.encodeRedirectUrl(arg0);
	}

	@Override
	public String encodeURL(String arg0) {
		return response.encodeURL(arg0);
	}

	@Override
	public String encodeUrl(String arg0) {
		return response.encodeUrl(arg0);
	}

	@Override
	public String getHeader(String arg0) {
		return response.getHeader(arg0);
	}

	@Override
	public Collection<String> getHeaderNames() {
		return response.getHeaderNames();
	}

	@Override
	public Collection<String> getHeaders(String arg0) {
		return response.getHeaders(arg0);
	}

	@Override
	public int getStatus() {
		return response.getStatus();
	}

	@Override
	public void sendError(int arg0) throws IOException {
		response.sendError(arg0);
	}

	@Override
	public void sendError(int arg0, String arg1) throws IOException {
		response.sendError(arg0, arg1);
	}

	@Override
	public void sendRedirect(String arg0) throws IOException {
		response.sendRedirect(arg0);
	}

	@Override
	public void setDateHeader(String arg0, long arg1) {
		response.setDateHeader(arg0, arg1);
	}

	@Override
	public void setHeader(String arg0, String arg1) {
		response.setHeader(arg0, arg1);
	}

	@Override
	public void setIntHeader(String arg0, int arg1) {
		response.setIntHeader(arg0, arg1);
	}

	@Override
	public void setStatus(int arg0) {
		response.setStatus(arg0);
	}

	@Override
	public void setStatus(int arg0, String arg1) {
		response.setStatus(arg0, arg1);
	}

}
