package com.yiguang.perf

import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager
import org.apache.http.util.EntityUtils

/**
 * Created by yigli on 14-12-29.
 */
object TestHttpclient extends App{
  private val cm = new PoolingHttpClientConnectionManager()
  cm.setMaxTotal(200)
  private val buider = HttpClientBuilder.create()
  buider.setConnectionManager(cm)

  protected val client = buider.build()

  val httpGet = new HttpGet("http://localhost:8080/")

  val httpResponse = client.execute(httpGet)
  val entity = httpResponse.getEntity()

  println(EntityUtils.toString(entity))

  val code = httpResponse.getStatusLine.getStatusCode
  if(code > 207 || code < 200){
    throw new Exception("error code:"+code)
  }

}
