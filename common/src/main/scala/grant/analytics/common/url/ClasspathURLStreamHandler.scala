package grant.analytics.common.url

import java.io.InputStream
import java.net.{URL, URLConnection, URLStreamHandler}

/**
  * Created by grant on 2016-04-05.
  *
  * URL foramt is "classpath:///package1/packag2/....
  */
class ClasspathURLStreamHandler extends URLStreamHandler{
  override def openConnection(u: URL): URLConnection = {
//    getClass.getResource(u.getPath).openConnection()  // here path format should be "/viafoura/analytics/...".

//    getClass.getClassLoader.getResource(u.getPath).openConnection() // here path format should be "viafoura/analytics/..."

    new ClasspathURLConnection(u)
  }
}