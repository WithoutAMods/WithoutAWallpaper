package withoutaname.mods.withoutawallpaper.datagen;

import java.util.function.Consumer;
import javax.annotation.Nonnull;

import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import withoutaname.mods.withoutawallpaper.setup.Registration;

public class Recipes extends RecipeProvider {
	
	public Recipes(DataGenerator generatorIn) {
		super(generatorIn);
	}
	
	@Override
	protected void buildCraftingRecipes(@Nonnull Consumer<FinishedRecipe> consumer) {
		ShapedRecipeBuilder.shaped(Registration.PASTING_TABLE_ITEM.get())
				.pattern("DD")
				.pattern("WW")
				.pattern("WW")
				.define('D', Tags.Items.DYES)
				.define('W', ItemTags.PLANKS)
				.unlockedBy("rolling_station", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ItemTags.PLANKS).build()))
				.save(consumer);
		
		ShapedRecipeBuilder.shaped(Registration.WALLPAPER_CATALOG_ITEM.get())
				.pattern("DDD")
				.pattern("DBD")
				.pattern("DDD")
				.define('B', Items.BOOK)
				.define('D', Items.BLUE_DYE)
				.unlockedBy("book", InventoryChangeTrigger.TriggerInstance.hasItems(Items.BOOK))
				.save(consumer);
	}
	
}
