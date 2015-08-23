package com.mycontainer.ex03;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletInputStream;

import org.apache.tomcat.util.res.StringManager;

import com.mycontainer.ex03.connector.http.Constants;
import com.mycontainer.ex03.connector.http.HttpRequest;

public class RequestStream extends ServletInputStream {

	/**
	 * Has this stream been closed?
	 */
	protected boolean closed = false;

	/**
	 * The number of bytes which have already been returned by this stream.
	 */
	protected int count = 0;

	/**
	 * The content length past which we will not read, or -1 if there is no
	 * defined content length.
	 */
	protected int length = -1;

	/**
	 * The localized strings for this package.
	 */
	protected static StringManager sm = StringManager.getManager(Constants.Package);

	/**
	 * The underlying input stream from which we should read data.
	 */
	protected InputStream stream = null;

	public RequestStream(HttpRequest request) {
		super();
		closed = false;
		count = 0;
		length = request.getContentLength();
		stream = request.getStream();
	}
	
	/**
	 * Close this input stream. No physical level I-O is performed, but any further attempt to read from this stream will throw 
	 *an IOException.
	 *If a content length has been set but not all of the bytes have
	 *yet been
	 *consumed, the remaining bytes will be swallowed. 
	 */
	public void close() throws IOException {
		if (closed)
			throw new IOException(sm.getString(
					"requestStream.close.closed"));
		
		if (length > 0) {
			while (count < length) {
				int b = read();
				if (b < 0) {
					break;
				}
			}
		}
		closed = true;
	}
	
	

	/* Read and return a single byte from this input stream, or -1 if
	 *end of 
	 *file has been encountered.
	 *
	 * @exception IOException if an input/output error occurs
	 * 
	 */
	@Override
	public int read() throws IOException {
		//Has this stream been closed?
		if (closed) 
			throw new IOException(sm.getString(
					"requestStream.read.closed"));
		
		//Have we read the specified content length already?
		if ((length >= 0) && (count >= length))
			return (-1);  //End of file indicator
		
		//Read and count the next byte, then return it
		int b = stream.read();
		if (b >= 0) {
			count++;
		}
		return (b);
	}
	
	   /**
     * Read some number of bytes from the input stream, and store them
     * into the buffer array b.  The number of bytes actually read is
     * returned as an integer.  This method blocks until input data is
     * available, end of file is detected, or an exception is thrown.
     *
     * @param b The buffer into which the data is read
     *
     * @exception IOException if an input/output error occurs
     */
    public int read(byte b[]) throws IOException {

        return (read(b, 0, b.length));

    }

}
