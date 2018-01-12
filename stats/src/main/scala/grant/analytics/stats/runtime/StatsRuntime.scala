package grant.analytics.stats.runtime

import com.google.inject.{Guice, Inject, Injector}
import grant.analytics.stats.injector.StatsInjector

/**
  * Created by grant on 2017-05-25.
  */
object StatsRuntime {
  lazy val injector = createInjector()

  private def createInjector():Injector = {
    Guice.createInjector(new StatsInjector)
  }
}
