package com.yiguang.perf

/**
 * Created by yigli on 14-12-22.
 */
object TestCollection extends App {

  val ab = new scala.collection.mutable.ArrayBuffer[Int]()
  val testSize = 1000000

  val start = System.currentTimeMillis()
  for(i <- 0 until testSize){
    ab += i
  }
  val end = System.currentTimeMillis()

  println("total:"+(end - start))

  var ab1 = new scala.collection.mutable.ArrayBuffer[Int]()

  val start1 = System.currentTimeMillis()
  for(i <- 0 until testSize){
    ab1 = ab1 :+ i
  }
  val end1 = System.currentTimeMillis()

  println("total1:"+(end - start))

}
