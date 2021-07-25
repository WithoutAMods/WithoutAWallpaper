package withoutaname.mods.withoutawallpaper.items;

import javax.annotation.Nonnull;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.IItemRenderProperties;
import withoutaname.mods.withoutawallpaper.blocks.WallpaperEntity;
import withoutaname.mods.withoutawallpaper.setup.Registration;
import withoutaname.mods.withoutawallpaper.tools.WallpaperType;

import java.util.function.Consumer;

public class WallpaperItem extends Item {
	
	public WallpaperItem() {
		super(new Item.Properties());
	}
	
	@Override
	public void initializeClient(@Nonnull Consumer<IItemRenderProperties> consumer) {
		consumer.accept(new IItemRenderProperties() {
			final BlockEntityWithoutLevelRenderer renderer = new WallpaperItemStackRenderer();
			
			@Override
			public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
				return renderer;
			}
		});
	}
	
	@Nonnull
	@Override
	public InteractionResult useOn(@Nonnull UseOnContext context) {
		Level world = context.getLevel();
		BlockPos pos = world.getBlockState(context.getClickedPos()).getMaterial().isReplaceable() ? context.getClickedPos() : context.getClickedPos().relative(context.getClickedFace());
		if (world.getBlockState(pos).getMaterial().isReplaceable() || world.getBlockState(pos).getBlock() == Registration.WALLPAPER_BLOCK.get()) {
			ItemStack stack = context.getItemInHand();
			CompoundTag compoundNBT = stack.getOrCreateTag();
			if (compoundNBT.contains("wallpaperType")) {
				BlockState state = world.getBlockState(pos);
				if (state.getBlock() != Registration.WALLPAPER_BLOCK.get()) {
					state = Registration.WALLPAPER_BLOCK.get().defaultBlockState();
				}
				if (!world.isClientSide) {
					Direction face = context.getClickedFace().getOpposite();
					world.setBlockAndUpdate(pos, state);
					WallpaperEntity te = (WallpaperEntity) world.getBlockEntity(pos);
					te.setType(face, WallpaperType.fromNBT(compoundNBT.getCompound("wallpaperType")));
					context.getItemInHand().shrink(1);
				} else {
					context.getPlayer().playSound(Registration.WALLPAPER_BLOCK.get().getSoundType(state, world, pos, context.getPlayer()).getPlaceSound()
							, 1.0f, 1.0f);
				}
				return InteractionResult.SUCCESS;
			}
		}
		return super.useOn(context);
	}
	
	
}
