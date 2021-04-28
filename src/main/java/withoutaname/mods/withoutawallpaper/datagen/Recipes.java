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

		ShapedRecipeBuilder.shapedRecipe(Registration.ROLLING_STATION_BLOCK.get())
				.patternLine("DD")
				.patternLine("WW")
				.patternLine("WW")
				.key('D', Tags.Items.DYES)
				.key('W', ItemTags.PLANKS)
				.addCriterion("rolling_station", InventoryChangeTrigger.Instance.forItems(ItemTags.PLANKS))
				.build(consumer);*/
	}
	
}
