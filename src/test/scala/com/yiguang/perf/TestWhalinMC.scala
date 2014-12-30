package com.yiguang.perf

import com.whalin.MemCached.{MemCachedClient, SockIOPool}

/**
 * Created by yigli on 14-12-30.
 */
object TestWhalinMC extends App{
  private val serverlist = Array[String]("localhost:11211")
  private val pool = SockIOPool.getInstance("perf.mc")
  pool.setServers(serverlist);
  pool.setInitConn(100)
  pool.setMinConn(100)
  pool.setMaxConn(100)
  pool.setNagle(false)
  pool.initialize()

  protected  val client = new MemCachedClient("perf.mc")

  client.set("key","value")

  val v = client.get("key")

  println(v)

}
