package withoutaname.mods.withoutawallpaper.datagen;

import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Items;
import withoutaname.mods.withoutawallpaper.setup.Registration;

import java.util.function.Consumer;

public class Recipes extends RecipeProvider{

	public Recipes(DataGenerator generatorIn) {
		super(generatorIn);
	}

	@Override
	protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
		ShapedRecipeBuilder.shapedRecipe(Registration.ORANGE_WALLPAPER_ITEM.get(), 16)
				.patternLine("PPP")
				.patternLine("PDP")
				.patternLine("PPP")
				.key('P', Items.PAPER)
				.key('D', Items.ORANGE_DYE)
				.addCriterion("paper", InventoryChangeTrigger.Instance.forItems(Items.PAPER))
				.build(consumer);
	}

}
