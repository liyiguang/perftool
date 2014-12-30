package com.yiguang.perf

import scala.concurrent.Promise


/**
 * Created by yigli on 14-12-17.
 */
class Worker(val totalCount:Int,val test:()=>Unit) extends Runnable {

  require(totalCount > 0)

  private[this] var error = 0

  private[this] val promise = Promise[Unit]()

  //stamp response current,error,success with tuple
  val stats = new Array[(Long,Long,Int,Int,Boolean)](totalCount)

  override def run() = {

    var current = 0

    while (current < totalCount) {

      var r = true
      var t = 0L
      try {
        val start = System.currentTimeMillis()
        test()
        val end = System.currentTimeMillis()
        t = end - start
      }
      catch {
        case _:Throwable =>
          error += 1
          r = false
      }

      stats(current) = (System.currentTimeMillis(),t,current+1-error,error,r)
      current += 1
    }

    promise.success()
  }

  def result = promise.future

  def errorCount = error
}



