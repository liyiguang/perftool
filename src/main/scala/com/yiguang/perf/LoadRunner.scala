package com.yiguang.perf

import perf.http.HttpClientGet

/**
 * Created by yigli on 14-12-17.
 */
object LoadRunner extends App{

  val parser = new scopt.OptionParser[Config]("perf"){

    head("perf", "0.0.1")

    opt[Int]('c', "concurrency") action { (x, c) =>c.copy(threads = x)} text("Number of thread")

    opt[Int]('n', "count") action { (x, c) =>c.copy(count = x)} text("Number of requests per thread")

    opt[String]('u', "url") action { (x, c) => c.copy(url = x)  } text("Http test url")

    arg[String]("<test class name>") optional() action {(x,c) => c.copy(clazz = x)} text("Test class name")

    help("help") text("Usage")

    override def showUsageOnError: Boolean = true

    checkConfig { c =>
      if (c.clazz == null && c.url == null) failure("Must set test class name") else success
    }

  }

  parser.parse(args, Config()) match {

    case Some(config) =>

      if(config.url != null){
        doHttpTest(config)
      }else{
        doCommonTest(config)
      }

    case None => //parser.showUsage
  }

  private def doCommonTest(config:Config) = {
    val testInstance = Class.forName(config.clazz).newInstance().asInstanceOf[Test]
    val loader = test(config,testInstance)
    stats(loader)
  }

  private def doHttpTest(config:Config) = {
    val testInstance = new HttpClientGet(config.url)
    val loader = test(config,testInstance)
    stats(loader)
  }

  private def test(config: Config, testInstance:Test) = {
    val loader = LoadGenerator(config.threads, config.count, testInstance)
    loader.start
    loader.await
    loader.close

    loader
  }

  private def stats(loader:LoadGenerator) = {
    loader.works.foreach { a =>
      StatsUtil.addData(a.stats)
      StatsUtil.parralTime = loader.totalTime
    }

    println("---------------------- report start -----------------------------")
    StatsUtil.stats
    println("---------------------- report end -------------------------------")
  }

  case class Config(
    threads:Int=1,
    count:Int=1,
    clazz:String=null,
    url:String=null
  )
}


