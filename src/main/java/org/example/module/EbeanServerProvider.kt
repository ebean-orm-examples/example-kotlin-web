package org.example.module

import com.avaje.ebean.EbeanServer
import com.avaje.ebean.EbeanServerFactory
import com.avaje.ebean.config.ServerConfig
import com.google.inject.Provider
import javax.inject.Singleton

//import org.apache.ignite.configuration.IgniteConfiguration
//import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi
//import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder
//import org.apache.ignite.spi.swapspace.file.FileSwapSpaceSpi

/**
 * Creates the default EbeanServer instance.
 *
 * This should be a singleton.
 */
@Singleton
class EbeanServerProvider : Provider<EbeanServer> {

  override fun get(): EbeanServer {

    val config = ServerConfig()
    config.name = "db"
    config.isDefaultServer = true
    config.loadFromProperties()

//    val igniteConf = IgniteConfiguration()
//    igniteConf.isClientMode = true
//    igniteConf.swapSpaceSpi = FileSwapSpaceSpi()
//    igniteConf.discoverySpi = TcpDiscoverySpi()
//
//    val multiCast = TcpDiscoveryMulticastIpFinder()
//    multiCast.setAddresses(arrayListOf("127.0.0.1"))
//
//    val tcpDiscovery = TcpDiscoverySpi()
//    tcpDiscovery.ipFinder = multiCast
//    igniteConf.discoverySpi = tcpDiscovery
//
//    config.putServiceObject("igniteConfiguration", igniteConf);

    return EbeanServerFactory.create(config);
  }
}