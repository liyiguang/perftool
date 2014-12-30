package com.yiguang.perf

/**
 * Created by yigli on 14-12-22.
 */
object TestReflect extends App {

  //val t = new TargetC
  val c =  Class.forName("com.yiguang.perf.TargetC")
  val o =c.newInstance().asInstanceOf[TargetC]

  val m =c.getMethod("doTest")


  var i = 0;

  val start = System.currentTimeMillis()
  while(i < 10000000){
    m.invoke(o)
    i += 1
  }
  val end = System.currentTimeMillis()

  println("total time:"+(end-start))

}
