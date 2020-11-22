name := "lab5"

version := "0.1"

scalaVersion := "2.13.1"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-Xcheckinit", "-encoding", "utf8", "-source", "1.7", "-target", "1.7")

resolvers ++= Seq(
  "Sonatype OSS Snapshots" at
    "https://oss.sonatype.org/content/repositories/snapshots",
  "Sonatype OSS Releases" at
    "https://oss.sonatype.org/content/repositories/releases"
)
libraryDependencies ++= Seq(
  "com.storm-enroute" %% "macrogl" % "0.4-SNAPSHOT")