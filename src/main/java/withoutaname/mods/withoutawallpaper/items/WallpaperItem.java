package withoutaname.mods.withoutawallpaper.items;

import javax.annotation.Nonnull;

import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import withoutaname.mods.withoutawallpaper.blocks.WallpaperTile;
import withoutaname.mods.withoutawallpaper.setup.Registration;
import withoutaname.mods.withoutawallpaper.tools.WallpaperType;

public class WallpaperItem extends Item {
	
	public WallpaperItem() {
		super(new Item.Properties().setISTER(() -> WallpaperItemStackRenderer::new));
	}
	
	@Nonnull
	@Override
	public ActionResultType useOn(@Nonnull ItemUseContext context) {
		World world = context.getLevel();
		BlockPos pos = world.getBlockState(context.getClickedPos()).getMaterial().isReplaceable() ? context.getClickedPos() : context.getClickedPos().relative(context.getClickedFace());
		if (world.getBlockState(pos).getMaterial().isReplaceable() || world.getBlockState(pos).getBlock() == Registration.WALLPAPER_BLOCK.get()) {
			ItemStack stack = context.getItemInHand();
			CompoundNBT compoundNBT = stack.getOrCreateTag();
			if (compoundNBT.contains("wallpaperType")) {
				BlockState state = world.getBlockState(pos);
				if (state.getBlock() != Registration.WALLPAPER_BLOCK.get()) {
					state = Registration.WALLPAPER_BLOCK.get().defaultBlockState();
				}
				if (!world.isClientSide) {
					Direction face = context.getClickedFace().getOpposite();
					world.setBlockAndUpdate(pos, state);
					WallpaperTile te = (WallpaperTile) world.getBlockEntity(pos);
					te.setType(face, WallpaperType.fromNBT(compoundNBT.getCompound("wallpaperType")));
					context.getItemInHand().shrink(1);
				} else {
					context.getPlayer().playSound(Registration.WALLPAPER_BLOCK.get().getSoundType(state, world, pos, context.getPlayer()).getPlaceSound()
							, 1.0f, 1.0f);
				}
				return ActionResultType.SUCCESS;
			}
		}
		return super.useOn(context);
	}
	
	
}
