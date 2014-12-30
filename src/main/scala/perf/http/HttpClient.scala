package perf.http

import com.yiguang.perf.Test
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.methods.HttpGet
import org.apache.http.config.SocketConfig
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager
import org.apache.http.util.EntityUtils

/**
 * Created by yigli on 14-12-25.
 */
protected abstract sealed class HttpClientTest extends Test {

  override protected def warmUp(threads: Int, requests: Int): Unit = super.warmUp(500, 1)

  private val cm = new PoolingHttpClientConnectionManager()
  cm.setMaxTotal(500)
  cm.setDefaultMaxPerRoute(500)

  private val scBuilder = SocketConfig.custom()
  scBuilder.setSoKeepAlive(true)
  scBuilder.setTcpNoDelay(true)
  cm.setDefaultSocketConfig(scBuilder.build())

  private val cBuilder = HttpClientBuilder.create()
  cBuilder.setConnectionManager(cm)

  private val rcBuilder = RequestConfig.custom()
  rcBuilder.setSocketTimeout(2000)
  rcBuilder.setConnectTimeout(1000)

  cBuilder.setDefaultRequestConfig(rcBuilder.build())

  protected val client = cBuilder.build()


}

class HttpClientGet(url:String) extends HttpClientTest {

  val httpGet = new HttpGet(url)

  override def doTest: Unit = {
    val httpResponse = client.execute(httpGet)
    val entity = httpResponse.getEntity()
    EntityUtils.toByteArray(entity)
    val code = httpResponse.getStatusLine.getStatusCode
    if(code > 207 || code < 200){
      throw new Exception("error code:"+code)
    }
  }
}


