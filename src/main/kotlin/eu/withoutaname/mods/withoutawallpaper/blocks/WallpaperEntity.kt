package eu.withoutaname.mods.withoutawallpaper.blocks

import eu.withoutaname.mods.withoutawallpaper.setup.Registration
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState

class WallpaperEntity(pos: BlockPos, state: BlockState) : BlockEntity(Registration.WALLPAPER_ENTITY.get(), pos, state)