import eu.withoutaname.gradle.withoutamods.mod

plugins {
    id("eu.withoutaname.gradle.mods.withoutamods") version "1.0.0-alpha.6"
}

mod {
    group = "eu.withoutaname.mods"
    modid = "withoutawallpaper"
    version = "1.18-2.0.0-alpha.1"
    forgeVersion = "1.18.2-40.0.36"
    mappings {
        parchment("2022.03.13-1.18.2")
    }
    dependencies {
//        mod("mcjty.theoneprobe", "theoneprobe", "1.18-5.1.0-8", uri("https://maven.k-4u.nl"), true)
        mod("mezz.jei", "jei-1.18.2", "9.5.5.174", uri("https://dvs1.progwml6.com/files/maven"), true)
    }
}