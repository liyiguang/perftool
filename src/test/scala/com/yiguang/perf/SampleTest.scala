package com.yiguang.perf

import scala.util.Random

/**
 * Created by yigli on 14-12-22.
 */
class SampleTest extends Test{

  override def doTest: Unit = {
    fibonacci(Random.nextInt(5)+25)

  }


  override def beforeAll: Unit = ???

  //@tailrec
  private def fibonacci(n:Int):Int = {
    if(n <= 2){
      return 1;
    }else{

      return (fibonacci(n-1) + fibonacci(n-2))
    }
  }
}
