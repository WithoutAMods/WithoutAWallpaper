package withoutaname.mods.withoutawallpaper.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import withoutaname.mods.withoutawallpaper.WithoutAWallpaper;
import withoutaname.mods.withoutawallpaper.setup.Registration;

public class BlockStates extends BlockStateProvider {
	
	public BlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
		super(gen, WithoutAWallpaper.MODID, exFileHelper);
	}
	
	@Override
	protected void registerStatesAndModels() {
		simpleBlock(Registration.WALLPAPER_BLOCK.get(), models().getExistingFile(modLoc("block/wallpaper")));
		itemModels().withExistingParent("wallpaper", modLoc("block/wallpaper"));
		
		horizontalBlock(Registration.PASTING_TABLE_BLOCK.get(), models().getExistingFile(modLoc("block/pasting_table")));
		itemModels().withExistingParent("pasting_table", modLoc("block/pasting_table"));
		
		itemModels().singleTexture("wallpaper_catalog", mcLoc("item/generated"), "layer0", modLoc("item/wallpaper_catalog"));
	}
	
}
