package edu.gatech.sqltutor;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;

/** Static utility functions. */
public class Utils {
	/** 
	 * Try to close a resource, suppressing any exception.
	 * 
	 * @param closeable the closeable or <code>null</code>
	 * @return the exception thrown or <code>null</code>
	 */
	public static IOException tryClose(Closeable closeable) {
		try {
			if( closeable != null )
				closeable.close();
			return null;
		} catch( IOException e ) {
			return e;
		}
	}
	
	/**
	 * Try to close a database connection, suppressing any exception.
	 * 
	 * @param conn the connection or <code>null</code>
	 * @return the exception thrown or <code>null</code>
	 */
	public static SQLException tryClose(Connection conn) {
		try {
			if( conn != null )
				conn.close();
			return null;
		} catch( SQLException e ) {
			return e;
		}
	}
	
	/**
	 * Try to close a resource, suppressing any exception.
	 * <p>This is done by reflection. The object must have a 
	 * <code>close()</code> method.</p>
	 * 
	 * @param obj the resource to close
	 * @return any exception
	 */
	public static Throwable tryClose(Object obj) {
		if( obj == null )
			return null;
		
		try {
			obj.getClass().getMethod("close").invoke(obj);
			return null;
		} catch( InvocationTargetException e ) {
			return e.getCause();
		} catch( Exception e ) {
			return e;
		}
	}
	
	public static String escapeChars(String input, String chars) {
		if( input == null ) return null;
		if( chars == null ) return input;
		
		StringBuilder b = new StringBuilder(input.length());
		for( int i = 0, ilen = input.length(); i < ilen; ++i ) {
			char c = input.charAt(i);
			if( chars.indexOf(c) > -1 )
				b.append('\\');
			b.append(c);
		}
		if( b.length() == input.length() )
			return input;
		return b.toString();
	}
}
