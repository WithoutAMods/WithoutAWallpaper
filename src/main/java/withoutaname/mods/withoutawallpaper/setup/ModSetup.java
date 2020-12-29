package withoutaname.mods.withoutawallpaper.setup;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ModSetup {

	public static final ItemGroup defaultItemGroup = new ItemGroup("withoutawallpaper") {

		@Override
		public ItemStack createIcon() {
			return new ItemStack(Items.ORANGE_DYE);
		}

	};

	public static void init(FMLCommonSetupEvent event) {
	}

	public static final Item.Properties defaultItemProperties = new Item.Properties().group(defaultItemGroup);

}
