/*******************************************************************************
 * Copyright (c) 2020, 2021 ArSysOp
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0/.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     ArSysOp - initial API and implementation
 *******************************************************************************/
package org.eclipse.passage.lic.hc.remote.impl;

import java.util.Base64;
import java.util.Collections;

import javax.net.ssl.HttpsURLConnection;

import org.eclipse.passage.lic.api.ServiceInvocationResult;
import org.eclipse.passage.lic.api.diagnostic.Trouble;
import org.eclipse.passage.lic.base.BaseServiceInvocationResult;
import org.eclipse.passage.lic.base.diagnostic.BaseDiagnostic;
import org.eclipse.passage.lic.base.diagnostic.code.ServiceFailedOnInfrastructureDenial;
import org.eclipse.passage.lic.hc.remote.Client;
import org.eclipse.passage.lic.hc.remote.Request;
import org.eclipse.passage.lic.hc.remote.RequestContext;
import org.eclipse.passage.lic.hc.remote.ResponseHandler;
import org.eclipse.passage.lic.internal.hc.i18n.AccessMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

/**
 * 
 * @since 1.1
 */
public final class HttpClient<T> implements Client<NetConnection, T>
{
	
	// Logger
	private static MessageLogger logger = new MessageLogger(HttpClient.class);
	
	@Override
	public ServiceInvocationResult<T> request(	Request<NetConnection> request,
												ResponseHandler<T> handler)
	{
		try
		{
			return netResults(connection(request), handler, request.context());
		}
		catch (Exception e)
		{
			return new BaseServiceInvocationResult<>(//
					new BaseDiagnostic(//
							Collections.emptyList(), //
							Collections.singletonList(//
									new Trouble(new ServiceFailedOnInfrastructureDenial(), AccessMessages.HttpClient_failure, e))));
		}
	}
	
	private NetConnection connection(Request<NetConnection> request) throws Exception
	{
		HttpsURLConnection con = (HttpsURLConnection) request.url().openConnection();
		
		// extract username from request URL
		final String USER_IDENTIFIER = "user="; //$NON-NLS-1$
		final String PROD_VERSION_IDENTIFIER = "licensing.product.version="; //$NON-NLS-1$
		
		String urlString = request.url().toString();
		String[] split = urlString.split("&"); //$NON-NLS-1$
		String username = ""; //$NON-NLS-1$
		String prodVersion = ""; //$NON-NLS-1$
		
		// get username
		for (String s : split)
		{
			if ((s.length() >= USER_IDENTIFIER.length()) && (s.substring(0, 5).equals(USER_IDENTIFIER)))
			{
				// Stop when found
				username = s.substring(USER_IDENTIFIER.length());
				break;
			}
		}
		
		// get product ID number
		for (String s : split)
		{
			if ((s.length() >= PROD_VERSION_IDENTIFIER.length()) && (s.substring(0, 26).equals(PROD_VERSION_IDENTIFIER)))
			{
				// Stop when found
				prodVersion = s.substring(PROD_VERSION_IDENTIFIER.length());
				break;
			}
		}
		
		// Format for header information is username:password, with password being the concatenation of username and prodVersion
		String userPass = username + ":" + username + prodVersion; //$NON-NLS-1$
		
		// Encode header information
		byte[] encodedUserPass = Base64.getEncoder().encode(userPass.getBytes());
		String authHeader = "Basic " + new String(encodedUserPass); //$NON-NLS-1$
		
		// add authorization header to HTTPS request
		con.setRequestProperty("Authorization", authHeader); //$NON-NLS-1$
		
		// set timeouts
		con.setConnectTimeout(5000);
		con.setReadTimeout(5000);
		
		return request.config().apply(new NetConnection(con));
	}
	
	private ServiceInvocationResult<T> netResults(	NetConnection connection,
													ResponseHandler<T> handler,
													RequestContext context) throws Exception
	{
		logger.logMessage("netResults: Starting client connection attempt..."); //$NON-NLS-1$
		
		ResultsTransfered results = null;
		
		try
		{ // actual connection is happening on this construction of the results
			results = new ResultsTransfered(connection);
		}
		catch (Exception e)
		{
			logger.logMessage("Connection error: " + e.getMessage()); //$NON-NLS-1$
			
			MessageBox box = new MessageBox(new Shell(), SWT.CANCEL | SWT.OK);
			box.setText("Connection Error"); //$NON-NLS-1$
			box.setMessage(e.getMessage());
			box.open();
			
			throw e;
		}
		
		if (!results.successful())
		{
			logger.logMessage("netResults: not successful. Returning."); //$NON-NLS-1$
			return new BaseServiceInvocationResult<>(results.diagnose());
		}
		
		logger.logMessage("netResults: successful. Returning."); //$NON-NLS-1$
		return new BaseServiceInvocationResult<>(handler.read(results, context));
	}
	
}
