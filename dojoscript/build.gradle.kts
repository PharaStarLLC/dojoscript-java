import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

dependencies {
    // NONE
}

tasks.named<ShadowJar>("shadowJar") {
    // Get rid of all the libs which are 100% unused.
    minimize()
    mergeServiceFiles()

    doFirst {
        manifest {
            attributes["Dojo-Web"] = "https://digitaldojo.tech/"
        }
    }

    doLast {
        copy {
            from("$projectDir/../out/shaded/${project.name}-${project.version}-shaded.jar")
            rename("${project.name}-${project.version}-shaded.jar", "${rootProject.name}-${rootProject.version}.jar")
            into("$projectDir/../out/")
        }
    }

}


