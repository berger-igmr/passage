package org.eclipse.passage.lic.hc.remote.impl;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * Enthält Methoden, die Nachrichten und Stack-Traces in die C:\Users\USERNAME\igmr\mechdev\.metadata\.log Log-Datei schreiben
 *
 * @author berger
 * @version 06.06.2024
 *
 */
public class MessageLogger
{
	
	private Bundle	b;
	private ILog	logger;
	
	public MessageLogger(Class<?> c)
	{
		this.b = FrameworkUtil.getBundle(c);
		this.logger = Platform.getLog(b);
	}
	
	/**
	 * Schreibt eine Nachricht in die C:\Users\USERNAME\igmr\mechdev\.metadata\.log Log-Datei
	 *
	 * @author berger
	 * @version 06.06.2024
	 *
	 *
	 * @param message
	 * 
	 * @return void
	 */
	public void logMessage(String message)
	{
		logger.log((new Status(Status.INFO, b.getSymbolicName(), message, null)));
	}
	
	/**
	 * Schreibt StackTrace von Exception e in C:\Users\USERNAME\igmr\mechdev\.metadata\.log Log-Datei
	 *
	 * @author berger
	 * @version 06.06.2024
	 *
	 *
	 * @param e
	 * 
	 * @return void
	 */
	public void logStackTrace(Exception e)
	{
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		
		e.printStackTrace(pw);
		
		this.logMessage(sw.toString());
	}
	
}
