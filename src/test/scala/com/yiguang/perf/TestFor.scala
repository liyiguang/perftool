package com.yiguang.perf



/**
 * Created by yigli on 14-12-22.
 */
object TestFor extends App{

  val start = System.currentTimeMillis()

  fibonacci(35)

  val end = System.currentTimeMillis()

  println(end-start)


  private def fibonacci(n:Int):Int = {
    if(n <= 2){
      return 1;
    }else{

      return (fibonacci(n-1) + fibonacci(n-2))
    }
  }
}
