package com.yiguang.perf

import scala.collection.mutable

/**
 * Created by yigli on 14-12-17.
 */
object StatsUtil {

  private[this] var datas = Seq[Array[(Long,Long,Int,Int,Boolean)]]()

  private[this] var totalTime = 0L

  private[this] var totalCount2 = 0L

  private[this] var successCount = 0L

  private[this] var totalError = 0L

  private[this] var responses = new mutable.ArrayBuffer[Long]()

  private[this] var p25 = 0L
  private[this] var p50 = 0L
  private[this] var p75 = 0L
  private[this] var p80 = 0L
  private[this] var p90 = 0L
  private[this] var p95 = 0L
  private[this] var p99 = 0L
  private[this] var p999 = 0L

  def addData(data:Array[(Long,Long,Int,Int,Boolean)]) = {
    datas = datas :+ data
  }

  def stats = {
    datas foreach {
      array => {
        array foreach {
          e =>
          if(e._5){
            totalTime += e._2
            responses += e._2
          }
        }
      }

      totalCount2 += array.size
      successCount += array.last._3
      totalError += array.last._4
    }



    println("requests:"+responses.size)

    val soredResponses = responses.sorted
    val size  = soredResponses.size

    for(i <- 1 to size){
      if(i >= calc(0.25 * size) && p25 == 0){
        p25 = soredResponses(i-1)
      }
      if(i >= calc(0.50 * size) && p50 == 0){
        p50 = soredResponses(i-1)
      }

      if(i >= calc(0.75 * size) && p75 == 0){
        p75 = soredResponses(i-1)
      }
      if(i >= calc(0.80 * size) && p80 == 0){
        p80 = soredResponses(i-1)
      }

      if(i >= calc(0.90 * size) && p90 == 0){
        p90 = soredResponses(i-1)
      }

      if(i >= calc(0.95 * size) && p95 == 0){
        p95 = soredResponses(i-1)
      }

      if(i >= calc(0.99 * size) && p99 == 0){
        p99 = soredResponses(i-1)
      }

      if(i >= calc(0.999 * size) && p999 == 0){
        p999 = soredResponses(i-1)
      }
    }

    val min = if(soredResponses.nonEmpty) soredResponses.head else 0
    val max = if(soredResponses.nonEmpty) soredResponses.last else 0

    println("Send Request: "+ totalCount2)
    println("Successful Request: "+ successCount)
    println("Response rate: "+throughput+" rsp/s ")
    println("Response time [ms]: avg "+avarageResponseTime+" min "+min+" max "+max)
    println("Response time [ms]: p25 "+p25+" p50 "+p50+" p75 "+p75)
    println("Response time [ms]: p80 "+p80+" p90 "+p90+" p95 "+p95)
    println("Response time [ms]: p99 "+p99+" p99.9 "+p999+" p100 "+max)

    println("Errors: total "+totalError)


  }

  var parralTime:Long = _

  private[this] def avarageResponseTime = {
    if(successCount > 0) totalTime / successCount else 0L
  }

  private[this] def throughput = {
    if(parralTime > 0) successCount * 1000 / parralTime else 0L
  }

  private[this] def calc(d:Double) = Math.round(d)


}
