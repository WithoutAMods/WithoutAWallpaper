package withoutaname.mods.withoutawallpaper.items;

import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import withoutaname.mods.withoutawallpaper.blocks.WallpaperBlock;
import withoutaname.mods.withoutawallpaper.setup.ModSetup;
import withoutaname.mods.withoutawallpaper.setup.Registration;
import withoutaname.mods.withoutawallpaper.tools.WallpaperType;

public class WallpaperItem extends Item {

	private WallpaperType type;

	public WallpaperItem(WallpaperType type) {
		super(ModSetup.defaultItemProperties);
		if (type == WallpaperType.NONE) {
			throw new IllegalStateException("Cannot create item with wallpaper type 'none'!");
		}
		this.type = type;
	}

	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		World world = context.getWorld();
		BlockPos pos = world.getBlockState(context.getPos()).getMaterial().isReplaceable() ? context.getPos() : context.getPos().offset(context.getFace());
		if (world.getBlockState(pos).getMaterial().isReplaceable() || world.getBlockState(pos).getBlock() == Registration.WALLPAPER_BLOCK.get()) {
			if (!world.isRemote) {
				Direction face = context.getFace().getOpposite();
				BlockState state = world.getBlockState(pos);
				if (state.getBlock() != Registration.WALLPAPER_BLOCK.get()) {
					state = Registration.WALLPAPER_BLOCK.get().getDefaultState();
				}
				world.setBlockState(pos, state.with(WallpaperBlock.getProperty(face), type));
				context.getItem().shrink(1);
			}
			return ActionResultType.SUCCESS;
		}
		return super.onItemUse(context);
	}

}
