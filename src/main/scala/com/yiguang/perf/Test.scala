package com.yiguang.perf

import org.slf4s.Logging

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future, Promise}

/**
 * Created by yigli on 14-12-17.
 */
abstract class Test extends Logging{

  protected def warmUp(threads:Int,requests:Int):Unit = {

    var fs = Seq[Future[Unit]]()

    for(i <- 1 to threads){
      val p = Promise[Unit]()
      val t = new Thread(new Runnable {
        override def run(): Unit = {
          for(j <- 1 to requests){
            try {
              doTest
            }
            catch {
              case e:Throwable => log.error("",e)
            }
          }
          p.success()
        }
      })

      fs = fs :+ p.future
      t.start()
    }

    val f = Future.sequence(fs)
    Await.result(f, 10 minute)

    println("Test warm up finished !")
  }

  def beforeAll:Unit= warmUp(1,1)

  def doTest:Unit

  def afterAll:Unit = {}
}

