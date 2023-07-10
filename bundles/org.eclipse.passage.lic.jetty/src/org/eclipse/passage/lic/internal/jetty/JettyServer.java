/*******************************************************************************
 * Copyright (c) 2021 ArSysOp
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
package org.eclipse.passage.lic.internal.jetty;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.passage.lic.internal.jetty.i18n.Messages;
import org.eclipse.passage.lic.internal.net.connect.Port;

public final class JettyServer
{
	
	private final Logger					log		= LogManager.getLogger(getClass());
	private final Supplier<JettyHandler>	handler;
	private Optional<Server>				server	= Optional.empty();
	
	public JettyServer(Supplier<JettyHandler> handler)
	{
		Objects.requireNonNull(handler, "JettyServer::handler"); //$NON-NLS-1$
		this.handler = handler;
	}
	
	public void launch(Port port) throws JettyException
	{
		try
		{
			server = Optional.of(new Server(port.get().get()));
			
			// HTTP config
			HttpConfiguration httpConfig = new HttpConfiguration();
			httpConfig.setSecureScheme("https"); //$NON-NLS-1$
			httpConfig.setSecurePort(8090);
			
			ServerConnector http = new ServerConnector(server.get(), new HttpConnectionFactory(httpConfig));
			http.setPort(port.get().get());
			
			// HTTPS config
			HttpConfiguration httpsConfig = new HttpConfiguration(httpConfig);
			httpsConfig.addCustomizer(new SecureRequestCustomizer());
			
			String ksName = ""; //$NON-NLS-1$
			String ksPW = ""; //$NON-NLS-1$
			
			try
			{
				File ks = new File("/keystore/ks"); //$NON-NLS-1$
				Scanner ksScanner = new Scanner(ks);
				
				ksName = (ksScanner.nextLine().split(":"))[1]; //$NON-NLS-1$
				ksPW = (ksScanner.nextLine().split(":"))[1]; //$NON-NLS-1$
				
				ksScanner.close();
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
			
			Path keystorePath = Paths.get("keystore/" + ksName).toAbsolutePath(); //$NON-NLS-1$
			
			if (!Files.exists(keystorePath))
			{
				throw new FileNotFoundException(keystorePath.toString());
			}
			
			SslContextFactory.Server sslContextFactory = new SslContextFactory.Server();
			
			sslContextFactory.setKeyStorePath(keystorePath.toString());
			sslContextFactory.setKeyStorePassword(ksPW);
			sslContextFactory.setKeyManagerPassword(ksPW);
			
			ServerConnector sslConnector = new ServerConnector(server.get(),
					new SslConnectionFactory(sslContextFactory, HttpVersion.HTTP_1_1.asString()), new HttpConnectionFactory(httpsConfig));
			
			sslConnector.setPort(8090);
			
			server.get().setConnectors(new Connector[] { sslConnector });
			server.get().setHandler(handler.get());
			server.get().start();
			log.info(String.format(Messages.started, port.get().get()));
		}
		catch (Exception e)
		{
			logAndRethrow(e, Messages.error_onstart);
		}
	}
	
	public void terminate() throws JettyException
	{
		try
		{
			if (server.isPresent())
			{
				server.get().stop();
			}
			log.info(String.format(Messages.stopped));
		}
		catch (Exception e)
		{
			logAndRethrow(e, Messages.error_onstop);
		}
	}
	
	public String state() throws JettyException
	{
		try
		{
			if (!server.isPresent())
			{
				return "NOT LAUNCHED"; //$NON-NLS-1$
			}
			return server.get().getState();
		}
		catch (Exception e)
		{
			throw new JettyException(Messages.error_onstate, e);
		}
	}
	
	private void logAndRethrow(	Exception e,
								String template) throws JettyException
	{
		String message = String.format(template, e.getClass(), e.getMessage());
		log.error(message, e);
		throw new JettyException(message, e);
	}
}
