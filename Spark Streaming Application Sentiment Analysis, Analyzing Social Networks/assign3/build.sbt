name := "assign3"

version := "0.1"

scalaVersion := "2.11.12"

val sparkVersion = "2.4.3"

lazy val root = (project in file(".")).
  settings(
    name := "kafka",
    version := "1.0",
    scalaVersion := "2.11.12",
    mainClass in Compile := Some("kafka")
  )

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-library" % "2.11.12" % "provided",
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.spark" %% "spark-mllib" % sparkVersion,
  "org.apache.spark" %% "spark-streaming" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-hive" % sparkVersion % "provided",
  "org.twitter4j" % "twitter4j" % "3.0.6",
  "org.twitter4j" % "twitter4j-stream" % "3.0.6",
  "org.apache.bahir" %% "spark-streaming-twitter" % "2.3.2",
  "org.apache.spark" %% "spark-streaming-kafka-0-10" % "2.4.0",
  "edu.stanford.nlp" % "stanford-corenlp" % "3.9.2" artifacts (Artifact("stanford-corenlp","models"), Artifact("stanford-corenlp")),
  "org.apache.spark" % "spark-graphx_2.11" % "1.2.1" % "provided"
)

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs@_*) => MergeStrategy.discard
  case x => MergeStrategy.first
}