package perf.mc

import com.google.code.yanf4j.core.impl.StandardSocketOption
import net.rubyeye.xmemcached.XMemcachedClientBuilder
import com.yiguang.perf.{KeyValueGenerator, Test}
import org.slf4s.Logging

import scala.concurrent.{Await, Promise, Future}
import scala.util.Random

/**
 * Created by yigli on 14-12-22.
 */

protected abstract sealed class XMCTest extends Test {
  override def beforeAll: Unit = warmUp(100,100)

  private val mcBuilder = new XMemcachedClientBuilder("localhost:11211")
  mcBuilder.setConnectionPoolSize(100)
  mcBuilder.setConnectTimeout(2000)
  mcBuilder.setOpTimeout(2000)
  mcBuilder.setSocketOption(StandardSocketOption.SO_RCVBUF, 32 * 1024 * 1024);
  mcBuilder.setSocketOption(StandardSocketOption.SO_SNDBUF, 32 * 1024 * 1024);
  mcBuilder.setSocketOption(StandardSocketOption.TCP_NODELAY, false)

  protected val client = mcBuilder.build()
  protected  val gen = new KeyValueGenerator(5,16)

  override def afterAll: Unit = client.shutdown()
}

class XMCTestSet extends XMCTest {
  override def doTest: Unit = client.set(gen.key,0,gen.value)
}

class XMCTestGet extends XMCTest {

  override def beforeAll: Unit = {
    super.beforeAll
    client.set(gen.key,0,gen.value)
  }

  override def doTest: Unit = client.get(gen.key)
}

class XMCTestGetAndSet extends XMCTest {
  override def doTest: Unit = {
    client.set(gen.key,0,gen.value)
    client.get(gen.key)
  }
}



