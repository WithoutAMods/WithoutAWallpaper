import eu.withoutaname.gradle.withoutamods.mod
import net.minecraftforge.gradle.userdev.DependencyManagementExtension

plugins {
    id("eu.withoutaname.gradle.mods.withoutamods") version "1.0.0-alpha.6"
}

mod {
    group = "eu.withoutaname.mods"
    modid = "withoutawallpaper"
    version = "1.18-2.0.0-alpha.1"
    forgeVersion = "1.18.2-40.1.0"
    mappings {
        parchment("2022.03.13-1.18.2")
    }
    dependencies {
//        mod("mcjty.theoneprobe", "theoneprobe", "1.18-5.1.0-8", uri("https://maven.k-4u.nl"), true)
//        mod("mezz.jei", "jei-1.18.2-forge", "10.0.0.191", uri("https://dvs1.progwml6.com/files/maven"))

        maven("https://dvs1.progwml6.com/files/maven")
        dependencies {
            "runtimeOnly"(it.the<DependencyManagementExtension>().deobf("mezz.jei:jei-1.18.2-forge:10.0.0.191"))
//            "compileOnly"(it.the<DependencyManagementExtension>().deobf("mezz.jei:jei-1.18.2-forge-api:10.0.0.191"))
//            "compileOnly"(it.the<DependencyManagementExtension>().deobf("mezz.jei:jei-1.18.2-common-api:10.0.0.191"))
        }
    }
}