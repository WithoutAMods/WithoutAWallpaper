package withoutaname.mods.withoutawallpaper.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.util.Direction;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import withoutaname.mods.withoutawallpaper.WithoutAWallpaper;
import withoutaname.mods.withoutawallpaper.blocks.WallpaperBlock;
import withoutaname.mods.withoutawallpaper.setup.Registration;
import withoutaname.mods.withoutawallpaper.tools.WallpaperType;

public class BlockStates extends BlockStateProvider {

	public BlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
		super(gen, WithoutAWallpaper.MODID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		ModelFile wallpaperModel = models().getBuilder("block/wallpaper")
				.texture("particle", "#wallpaper")
				.element().from(0F, 0F, 0F).to(16F, 16F, 1F)
					.face(Direction.NORTH).texture("#wallpaper").end()
					.face(Direction.EAST).texture("#wallpaper").end()
					.face(Direction.SOUTH).texture("#wallpaper").end()
					.face(Direction.WEST).texture("#wallpaper").end()
					.face(Direction.UP).texture("#wallpaper").end()
					.face(Direction.DOWN).texture("#wallpaper").end()
					.end();

		MultiPartBlockStateBuilder builder = getMultipartBuilder(Registration.WALLPAPER_BLOCK.get());
		ModelFile model;
		for (WallpaperType type : WallpaperType.getValuesExceptNone()) {
			itemModels().withExistingParent(type.getString() + "_wallpaper", mcLoc("item/generated"))
					.texture("layer0", modLoc("block/" + type.getString() + "_wallpaper"));

			model = models().getBuilder("block/" + type.getString() + "_wallpaper")
					.parent(wallpaperModel)
					.texture("wallpaper", modLoc("block/" + type.getString() + "_wallpaper"));
			addModel(builder, model, 0, 0, Direction.NORTH, type);
			addModel(builder, model, 90, 0, Direction.EAST, type);
			addModel(builder, model, 180, 0, Direction.SOUTH, type);
			addModel(builder, model, 270, 0, Direction.WEST, type);
			addModel(builder, model, 0, -90, Direction.UP, type);
			addModel(builder, model, 0, 90, Direction.DOWN, type);
		}
	}

	private void addModel(MultiPartBlockStateBuilder builder, ModelFile model, int rotY, int rotX, Direction dir, WallpaperType type) {
		builder.part().modelFile(model).rotationY(rotY).rotationX(rotX).addModel().condition(WallpaperBlock.getProperty(dir), type);
	}

}
