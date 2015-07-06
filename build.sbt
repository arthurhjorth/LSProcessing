name := "processing-extension"

organization := "org.ahjorth"

version := "0.1"

scalaVersion := "2.9.3"

javaSource in Compile <<= baseDirectory(_ / "src/main")

libraryDependencies += "org.nlogo" % "NetLogoHeadless" % "5.2.0-SNAPSHOT" from "file://./lib/NetLogo.jar"

libraryDependencies += "org.processing" % "Processing" % "1.3.0-SNAPSHOT" from "file://./lib/core.jar"



packageOptions := Seq(
  Package.ManifestAttributes(
    ("Extension-Name", "lsp"),
    ("Class-Manager", "org.ahjorth.lsp.LSProcessing"),
    ("NetLogo-Extension-API-Version", "5.0")))