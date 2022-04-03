package eu.withoutaname.mods.withoutawallpaper.setup

import eu.withoutaname.mods.withoutawallpaper.WithoutAWallpaper
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent


object ModSetup {

    val DEFAULT_CREATIVE_TAB: CreativeModeTab = object : CreativeModeTab(WithoutAWallpaper.ID) {
        override fun makeIcon(): ItemStack {
            return ItemStack(Items.BARRIER)
        }
    }
    val DEFAULT_ITEM_PROPERTIES: Item.Properties = Item.Properties().tab(DEFAULT_CREATIVE_TAB)

    fun init(event: FMLCommonSetupEvent?) {
    }
}