package com.yiguang.perf

import perf.mc.WhalinTestGet

/**
 * Created by yigli on 14-12-19.
 */

object TestLoader extends App {
  val loader = new LoadGenerator(1, 1, new WhalinTestGet)

  loader.start
  loader.await
  loader.close

  loader.works.foreach { a =>
    StatsUtil.addData(a.stats)
    StatsUtil.parralTime = loader.totalTime
  }

  println("---------------------- report start -----------------------------")

  StatsUtil.stats

  println("---------------------- report end -------------------------------")

}
