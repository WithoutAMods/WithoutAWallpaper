package withoutaname.mods.withoutawallpaper.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import withoutaname.mods.withoutawallpaper.setup.Registration;
import withoutaname.mods.withoutawallpaper.tools.WallpaperDesign;
import withoutaname.mods.withoutawallpaper.tools.WallpaperType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class WallpaperBlock extends BaseEntityBlock {
	
	public static final float THICKNESS = 0.1F;
	
	public WallpaperBlock() {
		super(Properties.of(Material.DECORATION)
				.sound(new SoundType(1.0f, 1.0f, SoundEvents.VILLAGER_WORK_LIBRARIAN, SoundEvents.VILLAGER_WORK_LIBRARIAN, SoundEvents.VILLAGER_WORK_LIBRARIAN, SoundEvents.VILLAGER_WORK_LIBRARIAN, SoundEvents.VILLAGER_WORK_LIBRARIAN))
				.strength(0.1F).noCollission());
	}
	
	@Nullable
	@Override
	public BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
		return new WallpaperEntity(pos, state);
	}
	
	@Override
	public ItemStack getPickBlock(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
		WallpaperType wallpaperType = null;
		BlockEntity te = world.getBlockEntity(pos);
		if (te instanceof WallpaperEntity tile) {
			Vec3 vec = target.getLocation().subtract(pos.getX(), pos.getY(), pos.getZ());
			final double d = THICKNESS * 1.01; // thickness * tolerance
			if (tile.getType(Direction.UP).getDesign() != WallpaperDesign.NONE && 1d - vec.y < d / 16d) {
				wallpaperType = tile.getType(Direction.UP);
			} else if (tile.getType(Direction.DOWN).getDesign() != WallpaperDesign.NONE && vec.y < d / 16d) {
				wallpaperType = tile.getType(Direction.DOWN);
			} else if (tile.getType(Direction.NORTH).getDesign() != WallpaperDesign.NONE && vec.z < d / 16d) {
				wallpaperType = tile.getType(Direction.NORTH);
			} else if (tile.getType(Direction.SOUTH).getDesign() != WallpaperDesign.NONE && 1d - vec.z < d / 16d) {
				wallpaperType = tile.getType(Direction.SOUTH);
			} else if (tile.getType(Direction.EAST).getDesign() != WallpaperDesign.NONE && 1d - vec.x < d / 16d) {
				wallpaperType = tile.getType(Direction.EAST);
			} else if (tile.getType(Direction.WEST).getDesign() != WallpaperDesign.NONE && vec.x < d / 16d) {
				wallpaperType = tile.getType(Direction.WEST);
			}
		}
		if (wallpaperType != null) {
			ItemStack itemStack = new ItemStack(Registration.WALLPAPER_ITEM.get());
			CompoundTag tag = itemStack.getOrCreateTag();
			tag.put("wallpaperType", wallpaperType.toNBT());
			return itemStack;
		} else {
			return super.getPickBlock(state, target, world, pos, player);
		}
	}
	
	@SuppressWarnings("deprecation")
	@Nonnull
	@Override
	public PushReaction getPistonPushReaction(@Nonnull BlockState state) {
		return PushReaction.DESTROY;
	}
	
	@SuppressWarnings("deprecation")
	@Nonnull
	@Override
	public VoxelShape getShape(@Nonnull BlockState state, BlockGetter worldIn, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
		List<VoxelShape> shapeList = new ArrayList<>();
		
		BlockEntity te = worldIn.getBlockEntity(pos);
		if (te instanceof WallpaperEntity wallpaperTile) {
			if (wallpaperTile.getType(Direction.UP).getDesign() != WallpaperDesign.NONE) {
				shapeList.add(Block.box(0.0D, 16.0D - THICKNESS, 0.0D, 16.0D, 16.0D, 16.0D));
			}
			if (wallpaperTile.getType(Direction.DOWN).getDesign() != WallpaperDesign.NONE) {
				shapeList.add(Block.box(0.0D, 0.0D, 0.0D, 16.0D, THICKNESS, 16.0D));
			}
			if (wallpaperTile.getType(Direction.NORTH).getDesign() != WallpaperDesign.NONE) {
				shapeList.add(Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, THICKNESS));
			}
			if (wallpaperTile.getType(Direction.SOUTH).getDesign() != WallpaperDesign.NONE) {
				shapeList.add(Block.box(0.0D, 0.0D, 16.0D - THICKNESS, 16.0D, 16.0D, 16.0D));
			}
			if (wallpaperTile.getType(Direction.EAST).getDesign() != WallpaperDesign.NONE) {
				shapeList.add(Block.box(16.0D - THICKNESS, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D));
			}
			if (wallpaperTile.getType(Direction.WEST).getDesign() != WallpaperDesign.NONE) {
				shapeList.add(Block.box(0.0D, 0.0D, 0.0D, THICKNESS, 16.0D, 16.0D));
			}
		}
		if (shapeList.isEmpty()) {
			shapeList.add(Shapes.empty());
		}
		VoxelShape shape = shapeList.get(0);
		for (int i = 1; i < shapeList.size(); i++) {
			shape = Shapes.join(shape, shapeList.get(i), BooleanOp.OR);
		}
		return shape;
	}
	
	@Nonnull
	@Override
	public RenderShape getRenderShape(@Nonnull BlockState pState) {
		return RenderShape.MODEL;
	}
}
