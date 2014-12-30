package perf.mc

import com.whalin.MemCached.{MemCachedClient, SockIOPool}
import com.yiguang.perf.{KeyValueGenerator, Test}


/**
 * Created by yigli on 14-12-23.
 */

protected abstract sealed class WhalinMCTest extends Test {
  override def beforeAll: Unit = warmUp(100,100)

  private val serverlist = Array[String]("localhost:11211")
  private val pool = SockIOPool.getInstance("perf.mc")
  pool.setServers(serverlist);
  pool.setInitConn(100)
  pool.setMinConn(100)
  pool.setMaxConn(100)
  pool.setNagle(false)
  pool.initialize()

  protected  val client = new MemCachedClient("perf.mc")

  protected  val gen = new KeyValueGenerator(5,16)

  override def afterAll: Unit = pool.shutDown()
}

class WhalinTestSet extends WhalinMCTest {
  override def doTest: Unit = client.set(gen.key,gen.value)
}

class WhalinTestGet extends WhalinMCTest {

  override def beforeAll: Unit = {
    super.beforeAll
    client.set(gen.key,gen.value)
  }

  override def doTest: Unit = client.get(gen.key)
}

class WhalinTestGetAndSet extends WhalinMCTest {

  override def doTest: Unit = {
    client.set(gen.key,gen.value)
    client.get(gen.key)
  }
}


