package withoutaname.mods.withoutawallpaper.datagen;

import java.util.function.Consumer;
import javax.annotation.Nonnull;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;

public class Recipes extends RecipeProvider {
	
	public Recipes(DataGenerator generatorIn) {
		super(generatorIn);
	}
	
	@Override
	protected void buildShapelessRecipes(@Nonnull Consumer<IFinishedRecipe> consumer) {
		/*
		Not possible to generate criterion with tag (manually added)

		ShapedRecipeBuilder.shaped(Registration.ROLLING_STATION_BLOCK.get())
				.pattern("DD")
				.pattern("WW")
				.pattern("WW")
				.define('D', Tags.Items.DYES)
				.define('W', ItemTags.PLANKS)
				.unlockedBy("rolling_station", InventoryChangeTrigger.Instance.hasItems(ItemTags.PLANKS))
				.save(consumer);
		
		ShapedRecipeBuilder.shaped(Registration.WALLPAPER_CATALOG_ITEM.get())
				.pattern("DDD")
				.pattern("DBD")
				.pattern("DDD")
				.define('B', Items.BOOK)
				.define('D', Items.BLUE_DYE)
				.unlockedBy("book", InventoryChangeTrigger.Instance.hasItems(Items.BOOK))
				.save(consumer);*/
	}
	
}
