package com.yiguang.perf

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, ExecutionContext, Future}

/**
 * Created by yigli on 14-12-17.
 */
class LoadGenerator(val threadnumber:Int,val requestCount:Int,val testInstance:Test){

  require(threadnumber > 0)
  require(requestCount > 0)

  private[this] var startTime:Long = _
  private[this] var endTime:Long = _

  private[this] var threads = Seq[Thread]()
  var works = Seq[Worker]()

  init

  private[this] def init = {

    testInstance.beforeAll

    for(i <- 1 to threadnumber){

      val worker = new Worker(requestCount,()=> testInstance.doTest)
      works = works :+ worker

      val t = new Thread(worker)
      threads = threads :+ t
    }
  }


  def start = {
    startTime = System.currentTimeMillis()
    threads foreach(_.start)
  }

  private def result = {
    val fs = works.map(_.result)
    val f = Future.sequence(fs)

    f onComplete {
      case _ => endTime = System.currentTimeMillis()
    }

    f
  }

  def totalTime = endTime - startTime

  def await = {
    import scala.concurrent.duration._
    Await.result(result, 10 hour)
  }

  def close = testInstance.afterAll
}

object LoadGenerator {
  def apply(threadNum:Int,requestCount:Int,testInstance:Test) = {
    new LoadGenerator(threadNum,requestCount,testInstance)
  }
}


