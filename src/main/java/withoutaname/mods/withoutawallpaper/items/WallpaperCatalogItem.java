package withoutaname.mods.withoutawallpaper.items;

import javax.annotation.Nonnull;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import withoutaname.mods.withoutawallpaper.gui.WallpaperCatalogGUI;
import withoutaname.mods.withoutawallpaper.setup.ModSetup;

public class WallpaperCatalogItem  extends Item {
	
	public WallpaperCatalogItem() {
		super(ModSetup.defaultItemProperties);
	}
	
	@Nonnull
	@Override
	public ActionResult<ItemStack> use(@Nonnull World world, @Nonnull PlayerEntity playerEntity, @Nonnull Hand hand) {
		if (world.isClientSide) {
			WallpaperCatalogGUI.open();
		}
		return ActionResult.success(playerEntity.getItemInHand(hand));
	}
	
}
