package eu.withoutaname.mods.withoutawallpaper.blocks

import net.minecraft.core.BlockPos
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.Material
import net.minecraftforge.common.util.ForgeSoundType

object WallpaperBlock : BaseEntityBlock(
    Properties.of(Material.DECORATION).sound(
        ForgeSoundType(
            1f,
            1f,
            { SoundEvents.VILLAGER_WORK_LIBRARIAN },
            { SoundEvents.VILLAGER_WORK_LIBRARIAN },
            { SoundEvents.VILLAGER_WORK_LIBRARIAN },
            { SoundEvents.VILLAGER_WORK_LIBRARIAN },
            { SoundEvents.VILLAGER_WORK_LIBRARIAN }
        )
    ).strength(0.1F).noCollission()
) {

    override fun newBlockEntity(pPos: BlockPos, pState: BlockState): BlockEntity = WallpaperEntity(pPos, pState)

}