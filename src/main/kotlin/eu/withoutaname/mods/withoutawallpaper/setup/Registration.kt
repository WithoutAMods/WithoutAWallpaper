package eu.withoutaname.mods.withoutawallpaper.setup

import eu.withoutaname.mods.withoutawallpaper.WithoutAWallpaper
import eu.withoutaname.mods.withoutawallpaper.blocks.WallpaperBlock
import eu.withoutaname.mods.withoutawallpaper.blocks.WallpaperEntity
import net.minecraft.world.inventory.MenuType
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.forge.registerObject


object Registration {

    val BLOCKS: DeferredRegister<Block> =
        DeferredRegister.create(ForgeRegistries.BLOCKS, WithoutAWallpaper.ID)
    val ITEMS: DeferredRegister<Item> =
        DeferredRegister.create(ForgeRegistries.ITEMS, WithoutAWallpaper.ID)
    val BLOCK_ENTITIES: DeferredRegister<BlockEntityType<*>> =
        DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, WithoutAWallpaper.ID)
    val CONTAINERS: DeferredRegister<MenuType<*>> =
        DeferredRegister.create(ForgeRegistries.CONTAINERS, WithoutAWallpaper.ID)


    val WALLPAPER_BLOCK: WallpaperBlock by BLOCKS.registerObject("wallpaper") { WallpaperBlock }
    val WALLPAPER_ITEM: BlockItem by ITEMS.registerObject("wallpaper") {
        BlockItem(WALLPAPER_BLOCK, ModSetup.DEFAULT_ITEM_PROPERTIES)
    }
    val WALLPAPER_ENTITY: BlockEntityType<WallpaperEntity> by BLOCK_ENTITIES.registerObject("wallpaper") {
        BlockEntityType.Builder.of(::WallpaperEntity, WALLPAPER_BLOCK).build(null)
    }
}