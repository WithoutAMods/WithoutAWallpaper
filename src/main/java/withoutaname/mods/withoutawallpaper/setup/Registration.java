package withoutaname.mods.withoutawallpaper.setup;

import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import withoutaname.mods.withoutawallpaper.blocks.*;
import withoutaname.mods.withoutawallpaper.items.WallpaperCatalogItem;
import withoutaname.mods.withoutawallpaper.items.WallpaperItem;

import static withoutaname.mods.withoutawallpaper.WithoutAWallpaper.MODID;

public class Registration {

	private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
	private static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MODID);
	private static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, MODID);

	public static void init() {
		BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
		TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
		CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
	
	public static final RegistryObject<PastingTableBlock> PASTING_TABLE_BLOCK = BLOCKS.register("pasting_table", PastingTableBlock::new);
	public static final RegistryObject<BlockItem> PASTING_TABLE_ITEM = ITEMS.register("pasting_table", () -> new BlockItem(PASTING_TABLE_BLOCK.get(), ModSetup.DEFAULT_ITEM_PROPERTIES));
	public static final RegistryObject<BlockEntityType<PastingTableEntity>> PASTING_TABLE_TILE = TILES.register("pasting_table", () -> BlockEntityType.Builder.of(PastingTableEntity::new, PASTING_TABLE_BLOCK.get()).build(null));
	public static final RegistryObject<MenuType<PastingTableContainer>> PASTING_TABLE_CONTAINER = CONTAINERS.register("pasting_table", () -> IForgeContainerType.create((windowId, inv, data) -> {
		BlockPos pos = data.readBlockPos();
		Level world = inv.player.getCommandSenderWorld();
		return new PastingTableContainer(windowId, world, pos, inv, inv.player);
	}));

	public static final RegistryObject<WallpaperBlock> WALLPAPER_BLOCK = BLOCKS.register("wallpaper", WallpaperBlock::new);
	public static final RegistryObject<WallpaperItem> WALLPAPER_ITEM = ITEMS.register("wallpaper", WallpaperItem::new);
	public static final RegistryObject<BlockEntityType<WallpaperEntity>> WALLPAPER_TILE = TILES.register("wallpaper", () -> BlockEntityType.Builder.of(WallpaperEntity::new, WALLPAPER_BLOCK.get()).build(null));

	public static final RegistryObject<WallpaperCatalogItem> WALLPAPER_CATALOG_ITEM = ITEMS.register("wallpaper_catalog", WallpaperCatalogItem::new);

}
