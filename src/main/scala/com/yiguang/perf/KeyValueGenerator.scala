package com.yiguang.perf

import scala.util.Random

/**
 * Created by yigli on 14-12-25.
 */
class KeyValueGenerator(val keyLen:Int=5,valueLen:Int=16) {

  require(keyLen > 0 && valueLen > 0)

  var key:String = {
    val b = new StringBuilder(keyLen)
    while(b.length < keyLen){
      b.append('K')
    }
    b.toString()
  }

  var value:String = {
    val b = new StringBuilder(valueLen)
    while(b.length < keyLen){
      b.append('V')
    }
    b.toString()
  }

  def randomValue = Random.nextString(valueLen)

}


