package perf.mc

import com.yiguang.perf.{KeyValueGenerator, Test}
import libmemcached.wrapper.MemcachedClient
import libmemcached.wrapper.`type`.BehaviorType

/**
 * Created by yigli on 14-12-23.
 */

protected abstract sealed class LibMCJNATest extends Test {
  private val _client =  new MemcachedClient
  _client.getServerList().parse("localhost:11211").push()

  protected val pool = _client.createPool(100,100)
  pool.setBehavior(BehaviorType.BUFFER_REQUESTS,true)
  pool.setBehavior(BehaviorType.TCP_NODELAY,true)
  pool.setBehavior(BehaviorType.TCP_KEEPALIVE,true)

  override def beforeAll = warmUp(100,100)

  def borrow() = pool.pop(true)

  def back(client:MemcachedClient) = pool.push(client)

  protected  val gen = new KeyValueGenerator(5,16)
}

class LibMCJNATestSet extends LibMCJNATest {

  override def doTest: Unit = {
    val c = borrow()
    c.getStorage.set(gen.key,gen.value,0,0)
    back(c)
  }
}

class LibMCJNATestGet extends LibMCJNATest {

  override def beforeAll: Unit = {
    super.beforeAll
    val c = borrow()
    c.getStorage.set(gen.key,gen.value,0,0)
    back(c)
  }

  override def doTest: Unit = {
    val c = borrow()
    c.getStorage.get(gen.key)

    back(c)
  }
}

class LibMCJNATestGetAndSet extends LibMCJNATest {

  override def doTest: Unit = {
    val c = borrow()
    c.getStorage.set(gen.key,gen.value,0,0)
    c.getStorage.get(gen.key)

    back(c)
  }
}


