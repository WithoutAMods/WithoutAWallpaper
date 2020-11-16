package withoutaname.mods.withoutawallpaper.datagen;

import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.loot.AlternativesLootEntry;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.conditions.BlockStateProperty;
import net.minecraft.state.EnumProperty;
import withoutaname.mods.withoutalib.datagen.BaseLootTableProvider;
import withoutaname.mods.withoutawallpaper.blocks.WallpaperBlock;
import withoutaname.mods.withoutawallpaper.setup.Registration;
import withoutaname.mods.withoutawallpaper.tools.WallpaperType;

public class LootTables extends BaseLootTableProvider {

	public LootTables(DataGenerator dataGeneratorIn) {
		super(dataGeneratorIn);
	}

	@Override
	protected void addTables() {
		Block block = Registration.WALLPAPER_BLOCK.get();
		LootTable.Builder lootTable = LootTable.builder()
				.addLootPool(getLootPool(block, WallpaperBlock.NORTH))
				.addLootPool(getLootPool(block, WallpaperBlock.EAST))
				.addLootPool(getLootPool(block, WallpaperBlock.SOUTH))
				.addLootPool(getLootPool(block, WallpaperBlock.WEST))
				.addLootPool(getLootPool(block, WallpaperBlock.UP))
				.addLootPool(getLootPool(block, WallpaperBlock.DOWN));
		lootTables.put(block, lootTable);
	}

	private LootPool.Builder getLootPool(Block block, EnumProperty<WallpaperType> property) {
		return getStandardLootPool(block.getRegistryName().toString(), AlternativesLootEntry.builder(
				ItemLootEntry.builder(Registration.ORANGE_WALLPAPER_ITEM.get())
						.acceptCondition(BlockStateProperty.builder(block)
								.fromProperties(StatePropertiesPredicate.Builder.newBuilder().withProp(property, WallpaperType.ORANGE)))));
	}

}
