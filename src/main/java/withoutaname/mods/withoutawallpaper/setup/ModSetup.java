package withoutaname.mods.withoutawallpaper.setup;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import withoutaname.mods.withoutawallpaper.tools.WallpaperDesign;

public class ModSetup {

	public static final ItemGroup defaultItemGroup = new ItemGroup("withoutawallpaper") {

		@Override
		public ItemStack createIcon() {
			return new ItemStack(Registration.PASTING_TABLE_ITEM.get());
		}

	};

	public static void init(FMLCommonSetupEvent event) {
		WallpaperDesign.loadDesigns();
	}

	public static final Item.Properties defaultItemProperties = new Item.Properties().group(defaultItemGroup);

}
