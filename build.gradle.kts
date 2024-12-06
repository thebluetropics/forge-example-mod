plugins {
  id("idea")
  id("net.minecraftforge.gradle") version "6.0.29"
  id("org.parchmentmc.librarian.forgegradle") version "1.+"
  id("org.spongepowered.mixin") version "0.7.38"
  id("maven-publish")
}

val artifactName = properties["artifact_name"] as String
val minecraftVersion = properties["minecraft_version"] as String

base {
  archivesName = "${artifactName}-forge-${minecraftVersion}"
}

java {
  toolchain.languageVersion = JavaLanguageVersion.of(21)

  sourceCompatibility = JavaVersion.VERSION_21
  targetCompatibility = JavaVersion.VERSION_21

  withSourcesJar()
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
      workingDirectory(projectDir.resolve("run/client"))
    }

    create("server") {
      workingDirectory(projectDir.resolve("run/server"))
      args("--nogui")
    }
  }
}

mixin {
  config("${properties["mod_id"].toString()}.mixins.json")
}

dependencies {
  minecraft("net.minecraftforge:forge:${minecraftVersion}-${properties["forge_version"].toString()}")
  implementation("net.sf.jopt-simple:jopt-simple:5.0.4") {
    version {
      strictly("5.0.4")
    }
  }
  annotationProcessor("org.spongepowered:mixin:0.8.5:processor")
}

tasks.withType(JavaCompile::class.java).configureEach {
  options.encoding = "UTF-8"
  options.release = 21
}

// Adds the `LICENSE` file to the jar, renaming it to include the artifact name.
tasks.named<Jar>("jar") {
  from("LICENSE") {
    rename {
      "LICENSE-${artifactName}"
    }
  }
}

// Substitutes placeholders in `mods.toml` with values from `gradle.properties`.
tasks.processResources {
  filesMatching("META-INF/mods.toml") {
    filter { line ->
      Regex("%([a-z_]+)%").replace(line) { match ->
        properties[match.groupValues[1]]?.toString() ?: match.value
      }
    }
  }
}

// TODO: what is this?
sourceSets.forEach {
  val dir = layout.buildDirectory.dir("sourceSets/${it.name}").get()
  it.output.setResourcesDir(dir)
  it.java.destinationDirectory = dir
}

publishing {
  publications {
    create<MavenPublication>("mavenJava") {
      groupId    = project.group.toString()
      artifactId = project.base.archivesName.get()
      version    = project.version.toString()

      from(components["java"])
    }
  }

  repositories {
    maven {
      name = "Mods"
      url = uri("file://${projectDir}/repository")
    }
    maven {
      name = "GithubPackages"
      url = uri(properties["github_packages_url"].toString())
      credentials {
        username = System.getenv("GITHUB_ACTOR")
        password = System.getenv("GITHUB_TOKEN")
      }
    }
  }
}