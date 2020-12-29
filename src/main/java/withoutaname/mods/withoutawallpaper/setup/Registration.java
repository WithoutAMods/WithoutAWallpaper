package withoutaname.mods.withoutawallpaper.setup;

import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import withoutaname.mods.withoutawallpaper.blocks.*;
import withoutaname.mods.withoutawallpaper.items.WallpaperItem;

import static withoutaname.mods.withoutawallpaper.WithoutAWallpaper.MODID;

public class Registration {

	private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
	private static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, MODID);
	private static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, MODID);

	public static void init() {
		BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
		TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
		CONTAINERS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}

	public static final RegistryObject<WallpaperBlock> WALLPAPER_BLOCK = BLOCKS.register("wallpaper", WallpaperBlock::new);
	public static final RegistryObject<WallpaperItem> WALLPAPER_ITEM = ITEMS.register("wallpaper", WallpaperItem::new);
	public static final RegistryObject<TileEntityType<WallpaperTile>> WALLPAPER_TILE = TILES.register("wallpaper", () -> TileEntityType.Builder.create(WallpaperTile::new, WALLPAPER_BLOCK.get()).build(null));

	public static final RegistryObject<RollingStationBlock> ROLLING_STATION_BLOCK = BLOCKS.register("rolling_station", RollingStationBlock::new);
	public static final RegistryObject<BlockItem> ROLLING_STATION_ITEM = ITEMS.register("rolling_station", () -> new BlockItem(ROLLING_STATION_BLOCK.get(), ModSetup.defaultItemProperties));
	public static final RegistryObject<TileEntityType<RollingStationTile>> ROLLING_STATION_TILE = TILES.register("rolling_station", () -> TileEntityType.Builder.create(RollingStationTile::new, ROLLING_STATION_BLOCK.get()).build(null));
	public static final RegistryObject<ContainerType<RollingStationContainer>> ROLLING_STATION_CONTAINER = CONTAINERS.register("rolling_station", () -> IForgeContainerType.create((windowId, inv, data) -> {
		BlockPos pos = data.readBlockPos();
		World world = inv.player.getEntityWorld();
		return new RollingStationContainer(windowId, world, pos, inv, inv.player);
	}));

}
