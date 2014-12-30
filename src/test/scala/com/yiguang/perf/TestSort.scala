package com.yiguang.perf

/**
 * Created by yigli on 14-12-19.
 */
object TestSort extends App {

  val a = Seq(1,2,2,4,2,3,7,4,8,3,7,4,8)
  a foreach((e)=>print(e+","))
  println("\n")
  a.sorted foreach((e)=>print(e+","))

}
