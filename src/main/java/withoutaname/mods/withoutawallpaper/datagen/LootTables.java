package withoutaname.mods.withoutawallpaper.datagen;

import net.minecraft.advancements.critereon.NbtPredicate;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction;
import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import withoutaname.mods.withoutalib.datagen.BaseLootTableProvider;
import withoutaname.mods.withoutalib.datagen.loot.conditions.NbtCondition;
import withoutaname.mods.withoutawallpaper.blocks.PastingTableBlock;
import withoutaname.mods.withoutawallpaper.setup.Registration;
import withoutaname.mods.withoutawallpaper.tools.WallpaperDesign;

public class LootTables extends BaseLootTableProvider {
	
	public LootTables(DataGenerator dataGeneratorIn) {
		super(dataGeneratorIn);
	}
	
	@Override
	protected void addTables() {
		PastingTableBlock pastingTableBlock = Registration.PASTING_TABLE_BLOCK.get();
		LootTable.Builder pastingTableLoot = getStandardLootTable(getStandardLootPool(pastingTableBlock.toString(),
				getStandardItemLootEntry(Registration.PASTING_TABLE_ITEM.get())
						.apply(CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY)
								.copy("inv", "BlockEntityTag.inv",
										CopyNbtFunction.MergeStrategy.REPLACE)
								.copy("selectedDesign", "BlockEntityTag.selectedDesign",
										CopyNbtFunction.MergeStrategy.REPLACE))));
		lootTables.put(pastingTableBlock, pastingTableLoot);
		
		Block wallpaperBlock = Registration.WALLPAPER_BLOCK.get();
		LootTable.Builder wallpaperLoot = LootTable.lootTable()
				.withPool(getWallpaperLootPool(wallpaperBlock, Direction.NORTH))
				.withPool(getWallpaperLootPool(wallpaperBlock, Direction.EAST))
				.withPool(getWallpaperLootPool(wallpaperBlock, Direction.SOUTH))
				.withPool(getWallpaperLootPool(wallpaperBlock, Direction.WEST))
				.withPool(getWallpaperLootPool(wallpaperBlock, Direction.UP))
				.withPool(getWallpaperLootPool(wallpaperBlock, Direction.DOWN));
		lootTables.put(wallpaperBlock, wallpaperLoot);
	}
	
	private LootPool.Builder getWallpaperLootPool(Block block, Direction direction) {
		CompoundTag tag = new CompoundTag();
		CompoundTag wallpaperType = new CompoundTag();
		wallpaperType.putString("design", WallpaperDesign.NONE.toString());
		tag.put(direction.toString(), wallpaperType);
		return getStandardLootPool(block.getRegistryName().toString(), LootItem
				.lootTableItem(Registration.WALLPAPER_ITEM.get())
				.apply(CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY)
						.copy(direction.toString(), "wallpaperType", CopyNbtFunction.MergeStrategy.REPLACE)))
				.when(InvertedLootItemCondition
						.invert(NbtCondition.create(ContextNbtProvider.BLOCK_ENTITY, new NbtPredicate(tag))));
	}
	
}
