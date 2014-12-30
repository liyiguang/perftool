package com.yiguang.perf

import org.scalatest.{Matchers, FlatSpec}

import scala.util.Random

/**
 * Created by yigli on 14-12-22.
 */
class StatsUtilSpec extends FlatSpec with Matchers {

  "StatsUtil" should  "Work" in {
    val size = 10000000
    val result = new Array[(Long,Long,Int,Int,Boolean)](size)

    for (i <- 0 until size) {
      var time = 0L
      if (i< size -1000){
        time = Random.nextInt(30)+1
      }else if(i< size -100){
        time = 32
      }else {
        time = 33
      }
      result(i) = (System.currentTimeMillis(),time,i+1,0,true)
    }

    StatsUtil.addData(result)

    StatsUtil.stats


  }
}
