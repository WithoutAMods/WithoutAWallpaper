package withoutaname.mods.withoutawallpaper.datagen;

import net.minecraft.advancements.criterion.NBTPredicate;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.conditions.Inverted;
import net.minecraft.loot.functions.CopyNbt;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;

import withoutaname.mods.withoutalib.datagen.BaseLootTableProvider;
import withoutaname.mods.withoutalib.datagen.loot.conditions.NBTCondition;
import withoutaname.mods.withoutawallpaper.blocks.PastingTableBlock;
import withoutaname.mods.withoutawallpaper.setup.Registration;
import withoutaname.mods.withoutawallpaper.tools.WallpaperDesign;

public class LootTables extends BaseLootTableProvider {
	
	public LootTables(DataGenerator dataGeneratorIn) {
		super(dataGeneratorIn);
	}
	
	@Override
	protected void addTables() {
		PastingTableBlock rollingStationBlock = Registration.PASTING_TABLE_BLOCK.get();
		lootTables.put(rollingStationBlock, getStandardLootTable(getStandardLootPool(rollingStationBlock.toString(),
				getStandardItemLootEntry(Registration.PASTING_TABLE_ITEM.get())
						.apply(CopyNbt.copyData(CopyNbt.Source.BLOCK_ENTITY)
								.copy("inv", "BlockEntityTag.inv",
										CopyNbt.Action.REPLACE)
								.copy("selectedDesign", "BlockEntityTag.selectedDesign",
										CopyNbt.Action.REPLACE)))));
		
		Block wallpaperBlock = Registration.WALLPAPER_BLOCK.get();
		LootTable.Builder lootTable = LootTable.lootTable()
				.withPool(getWallpaperLootPool(wallpaperBlock, Direction.NORTH))
				.withPool(getWallpaperLootPool(wallpaperBlock, Direction.EAST))
				.withPool(getWallpaperLootPool(wallpaperBlock, Direction.SOUTH))
				.withPool(getWallpaperLootPool(wallpaperBlock, Direction.WEST))
				.withPool(getWallpaperLootPool(wallpaperBlock, Direction.UP))
				.withPool(getWallpaperLootPool(wallpaperBlock, Direction.DOWN));
		lootTables.put(wallpaperBlock, lootTable);
	}
	
	private LootPool.Builder getWallpaperLootPool(Block block, Direction direction) {
		CompoundNBT tag = new CompoundNBT();
		CompoundNBT wallpaperType = new CompoundNBT();
		wallpaperType.putString("design", WallpaperDesign.NONE.toString());
		tag.put(direction.toString(), wallpaperType);
		return getStandardLootPool(block.getRegistryName().toString(), ItemLootEntry
				.lootTableItem(Registration.WALLPAPER_ITEM.get())
				.apply(CopyNbt.copyData(CopyNbt.Source.BLOCK_ENTITY)
						.copy(direction.toString(), "wallpaperType", CopyNbt.Action.REPLACE)))
				.when(Inverted
						.invert(new NBTCondition.Builder(NBTCondition.Source.BLOCK_ENTITY)
								.fromPredicate(new NBTPredicate(tag))));
	}
	
}
