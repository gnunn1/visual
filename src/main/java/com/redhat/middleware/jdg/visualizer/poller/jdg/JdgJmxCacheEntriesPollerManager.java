/*
* JBoss, Home of Professional Open Source
* Copyright 2011 Red Hat Inc. and/or its affiliates and other
* contributors as indicated by the @author tags. All rights reserved.
* See the copyright.txt in the distribution for a full listing of
* individual contributors.
*
* This is free software; you can redistribute it and/or modify it
* under the terms of the GNU Lesser General Public License as
* published by the Free Software Foundation; either version 2.1 of
* the License, or (at your option) any later version.
*
* This software is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
* Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public
* License along with this software; if not, write to the Free
* Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
* 02110-1301 USA, or see the FSF site: http://www.fsf.org.
*/

package com.redhat.middleware.jdg.visualizer.poller.jdg;

import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.SocketAddress;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.management.remote.JMXServiceURL;

import com.redhat.middleware.jdg.visualizer.cdi.Resources;
import com.redhat.middleware.jdg.visualizer.internal.VisualizerRemoteCacheManager;
import com.redhat.middleware.jdg.visualizer.poller.CacheEntriesPollerThread;
import com.redhat.middleware.jdg.visualizer.poller.jmx.JmxCacheEntriesPoller;
import com.redhat.middleware.jdg.visualizer.poller.jmx.JmxCacheEntriesPollerManager;
import com.redhat.middleware.jdg.visualizer.rest.NodeInfo;

/**
 * 
 * @author <a href="mailto:rtsang@redhat.com">Ray Tsang</a>
 *
 */
public class JdgJmxCacheEntriesPollerManager extends JmxCacheEntriesPollerManager {
	private Logger logger = Logger.getLogger(JdgJmxCacheEntriesPollerManager.class.getName());

	public JdgJmxCacheEntriesPollerManager(VisualizerRemoteCacheManager cacheManager) {
		super(cacheManager);
	}

	@Override
	protected JMXServiceURL generateServiceURL(SocketAddress address)
			throws MalformedURLException {
		InetSocketAddress isa = (InetSocketAddress) address;
		String host = isa.getAddress().getHostAddress();
		int port = isa.getPort() - getJmxHotrodPortOffset();
		//previous impl
		//return new JMXServiceURL("service:jmx:remoting-jmx://" + host + ":" + port);
		// TODO - should port be passed in?
		port = 9991;
		String jmxURLString="service:jmx:remote+http://" + host + ":" + port;
		logger.log(Level.INFO,"isa.getPort: ["+isa.getPort()+","+getJmxHotrodPortOffset()+"]");
		logger.log(Level.INFO,"REAL JMX URL: "+jmxURLString);


		return new JMXServiceURL(jmxURLString);
	}

	@Override
	protected JmxCacheEntriesPoller createPoller(JMXServiceURL url,
			Map<String, Object> env) {
		return new JdgJmxCacheEntriesPoller(url, env, getCacheName());
	}

	@Override
	protected CacheEntriesPollerThread createPollerThread(SocketAddress address,
			NodeInfo nodeInfo) throws Exception {
		return new CacheEntriesPollerThread(createPoller(address), nodeInfo);
	}

}
