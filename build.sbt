name := "cli-micro-tools"

organization := "de.21re"

version := {
  "0.1-" + sys.props.get("BUILD_NUMBER").orElse(sys.env.get("BUILD_NUMBER")).getOrElse("SNAPSHOT")
}

scalaVersion := "2.12.3"

crossScalaVersions := Seq("2.12.3", "2.11.11")

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding",
  "UTF-8",
  "-feature",
  "-target:jvm-1.8",
  "-unchecked",
  "-Ywarn-adapted-args",
  "-Ywarn-numeric-widen",
  "-Ywarn-unused",
  "-Xfuture",
  "-Xlint"
)

libraryDependencies ++= Seq(
  "com.chuusai"    %% "shapeless"                   % "2.3.2"   % Provided,
  "ch.qos.logback" % "logback-classic"              % "1.1.7"   % Provided,
  "org.scalatest"  %% "scalatest"                   % "3.0.1"   % Test
)


licenses += ("MIT", url("http://opensource.org/licenses/MIT"))

resolvers += "21re-bintray" at "http://dl.bintray.com/21re/public"

publishMavenStyle := true

bintrayOrganization := Some("21re")

bintrayRepository := "public"

bintrayCredentialsFile := {
  sys.props
    .get("BINTRAY_CREDENTIALS")
    .orElse(sys.env.get("BINTRAY_CREDENTIALS"))
    .map(new File(_))
    .getOrElse(baseDirectory.value / ".bintray" / "credentials")
}

lazy val mainSourcesScalaStyle = taskKey[Unit]("mainSourcesScalaStyle")
mainSourcesScalaStyle := org.scalastyle.sbt.ScalastylePlugin.autoImport.scalastyle
  .in(Compile)
  .toTask("")
  .value
(test in Test) := { (test in Test) dependsOn mainSourcesScalaStyle }.value

enablePlugins(ScalafmtPlugin)
scalafmtVersion := "0.6.8"
(compile in Compile) := {
  (compile in Compile) dependsOn (scalafmt in Compile).toTask
}.value
(compile in Test) := {
  (compile in Test) dependsOn (scalafmt in Test).toTask
}.value
