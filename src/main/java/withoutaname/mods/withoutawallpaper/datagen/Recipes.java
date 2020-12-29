package withoutaname.mods.withoutawallpaper.datagen;

import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import withoutaname.mods.withoutawallpaper.setup.Registration;

import java.util.function.Consumer;

public class Recipes extends RecipeProvider{

	public Recipes(DataGenerator generatorIn) {
		super(generatorIn);
	}

	@Override
	protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
		/*
		Not possible to generate criterion with tag

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
