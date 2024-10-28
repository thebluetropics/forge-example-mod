plugins {
  id("idea")
  id("net.minecraftforge.gradle") version "6.0.29"
  id("org.parchmentmc.librarian.forgegradle") version "1.+"
  id("org.spongepowered.mixin") version "0.7.38"
}

val minecraft_version = properties["minecraft_version"] as String

base {
  archivesName = "${properties["artifact_name"].toString()}-forge-${minecraft_version}"
}

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(21)
  }
}

minecraft {
  mappings("parchment", properties["mappings_version"].toString())

  runs {
    configureEach {
      workingDirectory(project.file("run"))
      property("forge.logging.markers", "REGISTRIES")
      property("forge.logging.console.level", "debug")
    }

    create("client") {
    }

    create("server") {
      args("--nogui")
    }
  }
}

mixin {
  config("examplemod.mixins.json")
}

dependencies {
  minecraft("net.minecraftforge:forge:${minecraft_version}-${properties["forge_version"].toString()}")
  implementation("net.sf.jopt-simple:jopt-simple:5.0.4") {
    version {
      strictly("5.0.4")
    }
  }
  annotationProcessor("org.spongepowered:mixin:0.8.5:processor")
}

tasks.withType(JavaCompile::class.java).configureEach {
  options.encoding = "UTF-8"
}

sourceSets.forEach {
  val dir = layout.buildDirectory.dir("sourceSets/${it.name}").get()
  it.output.setResourcesDir(dir)
  it.java.destinationDirectory = dir
}
