package eu.withoutaname.mods.withoutawallpaper

import eu.withoutaname.mods.withoutawallpaper.setup.ClientSetup
import eu.withoutaname.mods.withoutawallpaper.setup.ModSetup
import eu.withoutaname.mods.withoutawallpaper.setup.Registration
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.fml.DistExecutor
import net.minecraftforge.fml.common.Mod
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import thedarkcolour.kotlinforforge.forge.MOD_BUS


@Mod(WithoutAWallpaper.ID)
object WithoutAWallpaper {

    const val ID = "withoutawallpaper"
    val LOGGER: Logger = LogManager.getLogger(ID)

    init {
        Registration.BLOCKS.register(MOD_BUS)
        Registration.ITEMS.register(MOD_BUS)
        Registration.BLOCK_ENTITIES.register(MOD_BUS)
        Registration.CONTAINERS.register(MOD_BUS)

        MOD_BUS.addListener(ModSetup::init)
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT) { Runnable { MOD_BUS.addListener(ClientSetup::init) } }
    }
}