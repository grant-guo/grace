import Dependencies.{default_dependencies_seq, _}
import Resolvers._

lazy val commonSettings = Seq(
  organization := "grant",
  scalaVersion := "2.11.11",
  autoScalaLibrary := false,
  resolvers ++= Seq(
    clojars,
    maven_local
  ),
  dependencyOverrides ++=  Set(
    "org.apache.commons" % "commons-lang3" % "3.3.2",
    "com.fasterxml.jackson.core" % "jackson-databind" % "2.6.5",
    "commons-beanutils" % "commons-beanutils" % "1.9.2"
  ),
  test in assembly := {},
  assemblyMergeStrategy in assembly := {
    {
      case PathList("javax", "servlet", xs @ _*) => MergeStrategy.last
      case PathList("javax", "activation", xs @ _*) => MergeStrategy.last
      case PathList("javax", "annotation", xs @ _*) => MergeStrategy.last
      case PathList("org", "apache", "spark", "unused", "UnusedStubClass.class") => MergeStrategy.first
      case PathList("org", "apache", "commons", "beanutils", xs @ _*) => MergeStrategy.last
      case PathList("org", "apache", "commons", "shadebeanutils", xs @ _*) => MergeStrategy.first
      case PathList("org", "apache", "commons", "logging", xs @ _*) => MergeStrategy.first
      case PathList("org", "apache", "commons", "collections", xs @ _*) => MergeStrategy.first
      case PathList("org", "apache", "hadoop", "fs", xs @ _*) => MergeStrategy.last
      case PathList("org", "apache", "hadoop", "yarn", xs @ _*) => MergeStrategy.last
      case PathList("org", "aopalliance", xs @ _*) => MergeStrategy.last
      case PathList("com", "google", xs @ _*) => MergeStrategy.last
      case PathList("javax", "inject", xs @ _*) => MergeStrategy.last
      case PathList("com", "esotericsoftware", xs @ _*) => MergeStrategy.last
      case PathList("com", "codahale", xs @ _*) => MergeStrategy.last
      case PathList("com", "yammer", xs @ _*) => MergeStrategy.last
      case PathList("org", "slf4j", xs @ _*) => MergeStrategy.last
      case PathList("org", "joda", "time", "base", xs @ _*) => MergeStrategy.last
      case PathList("org", "tartarus", "snowball", xs @ _*) => MergeStrategy.last
      case "about.html" => MergeStrategy.rename
      case "META-INF/ECLIPSEF.RSA" => MergeStrategy.last
      case "META-INF/mailcap" => MergeStrategy.last
      case "META-INF/mimetypes.default" => MergeStrategy.last
      case "META-INF/io.netty.versions.properties" => MergeStrategy.last
      case "plugin.properties" => MergeStrategy.last
      case "log4j.properties" => MergeStrategy.last
      case "stylesheet.css" => MergeStrategy.last
      case "mime.types" => MergeStrategy.last
      case "META-INF/eclipse.inf" => MergeStrategy.last
      case "PropertyList-1.0.dtd" => MergeStrategy.last
      case "properties.dtd" => MergeStrategy.last
      case "vfmetrics.properties" => MergeStrategy.last
      case x => val oldStrategy = (assemblyMergeStrategy in assembly).value

        oldStrategy(x)
    }
  },

  assemblyShadeRules in assembly := Seq(
    ShadeRule.rename("org.apache.commons.beanutils.**" -> "org.apache.commons.shadebeanutils.@1").inAll,
    ShadeRule.rename("org.apache.http.**" -> "org.apache.shadehttp.@1").inAll,
    ShadeRule.rename("org.apache.commons.cli.**" -> "org.apache.commons.shadecli.@1").inAll
  )

)

lazy val data_analytics = (project in file(".")).settings(
  scalaVersion := "2.11.11"
)

lazy val common = (project in file("common")).settings(commonSettings: _*)

lazy val stats = (project in file("stats")).dependsOn(common).settings(commonSettings: _*)

lazy val es_aggr = (project in file("es_aggr")).dependsOn(common).settings(commonSettings: _*)

lazy val restperformance = (project in file("restperformance")).dependsOn(common).settings(commonSettings:_*)

lazy val embedded = (project in file("embedded")).dependsOn(common).settings(commonSettings:_*)

lazy val grpc = (project in file("grpc")).settings(commonSettings:_*)

lazy val misc = (project in file("misc")).settings(commonSettings:_*)