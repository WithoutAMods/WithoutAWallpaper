package withoutaname.mods.withoutawallpaper.items;

import javax.annotation.Nonnull;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import withoutaname.mods.withoutawallpaper.gui.WallpaperCatalogGUI;
import withoutaname.mods.withoutawallpaper.setup.ModSetup;

public class WallpaperCatalogItem  extends Item {
	
	public WallpaperCatalogItem() {
		super(ModSetup.DEFAULT_ITEM_PROPERTIES);
	}
	
	@Nonnull
	@Override
	public InteractionResultHolder<ItemStack> use(@Nonnull Level world, @Nonnull Player playerEntity, @Nonnull InteractionHand hand) {
		if (world.isClientSide) {
			WallpaperCatalogGUI.open();
		}
		return InteractionResultHolder.success(playerEntity.getItemInHand(hand));
	}
	
}
