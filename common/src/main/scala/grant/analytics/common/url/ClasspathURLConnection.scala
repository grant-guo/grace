package grant.analytics.common.url

import java.io.InputStream
import java.net.{URL, URLConnection}

class ClasspathURLConnection(url:URL) extends URLConnection(url) {
  override def connect(): Unit = {
    getClass.getResource(url.getPath).openConnection()
  }

  /**
    * by override this method, i could program like the following codes:
    *
    * new URL(...).openStream()
    *
    * @return
    */
  override def getInputStream: InputStream = {
    getClass.getResourceAsStream(url.getPath)
  }

}