package withoutaname.mods.withoutawallpaper.setup;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import withoutaname.mods.withoutawallpaper.blocks.WallpaperBlock;
import withoutaname.mods.withoutawallpaper.items.WallpaperItem;
import withoutaname.mods.withoutawallpaper.tools.WallpaperType;

import static withoutaname.mods.withoutawallpaper.WithoutAWallpaper.MODID;

public class Registration {

	private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

	public static void init() {
		BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}

	public static final RegistryObject<WallpaperBlock> WALLPAPER_BLOCK = BLOCKS.register("wallpaper", WallpaperBlock::new);

	public static final RegistryObject<WallpaperItem> ORANGE_WALLPAPER_ITEM = ITEMS.register("orange_wallpaper", () -> new WallpaperItem(WallpaperType.ORANGE));

}
