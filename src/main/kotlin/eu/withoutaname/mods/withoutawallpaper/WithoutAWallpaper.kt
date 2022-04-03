package eu.withoutaname.mods.withoutawallpaper

import eu.withoutaname.mods.withoutawallpaper.setup.ModSetup
import eu.withoutaname.mods.withoutawallpaper.setup.Registration
import net.minecraftforge.fml.common.Mod
import thedarkcolour.kotlinforforge.forge.MOD_BUS

@Mod(WithoutAWallpaper.ID)
object WithoutAWallpaper {

    const val ID = "withoutawallpaper"

    init {
        Registration.BLOCKS.register(MOD_BUS)
        Registration.ITEMS.register(MOD_BUS)
        Registration.BLOCK_ENTITIES.register(MOD_BUS)
        Registration.CONTAINERS.register(MOD_BUS)

        MOD_BUS.addListener(ModSetup::init)
    }
}