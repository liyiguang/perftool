package perf.http

import com.yiguang.perf.Test
import com.yiguang.util.SimpleHttpClient

/**
 * Created by liyiguang on 12/31/14.
 */

protected abstract sealed class SimpleHttpClientTest extends Test {

  override protected def warmUp(threads: Int, requests: Int): Unit = super.warmUp(1, 1)

  protected  val client: SimpleHttpClient = new SimpleHttpClient
}

class SimpleHttpClientGet() extends SimpleHttpClientTest {

  val url = "http://localhost:8080/test.html"

  override def doTest: Unit = {
    client.get(url)
  }
}
