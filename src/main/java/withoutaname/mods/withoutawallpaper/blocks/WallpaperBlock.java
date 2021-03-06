package withoutaname.mods.withoutawallpaper.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import withoutaname.mods.withoutawallpaper.setup.Registration;
import withoutaname.mods.withoutawallpaper.tools.WallpaperDesign;
import withoutaname.mods.withoutawallpaper.tools.WallpaperType;

import java.util.ArrayList;
import java.util.List;

public class WallpaperBlock extends Block {

	public static final double THICKNESS = 0.1D;

	public WallpaperBlock() {
		super(Properties.create(Material.MISCELLANEOUS)
				.sound(new SoundType(1.0f, 1.0f, SoundEvents.ENTITY_VILLAGER_WORK_LIBRARIAN, SoundEvents.ENTITY_VILLAGER_WORK_LIBRARIAN, SoundEvents.ENTITY_VILLAGER_WORK_LIBRARIAN, SoundEvents.ENTITY_VILLAGER_WORK_LIBRARIAN, SoundEvents.ENTITY_VILLAGER_WORK_LIBRARIAN))
				.hardnessAndResistance(0.1F).doesNotBlockMovement());
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new WallpaperTile();
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
		WallpaperType wallpaperType = null;
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof WallpaperTile) {
			WallpaperTile tile = (WallpaperTile) te;
			Vector3d vec = target.getHitVec().subtract(pos.getX(), pos.getY(), pos.getZ());
			final double d = THICKNESS * 1.01; // thickness * tolerance
			if (tile.getType(Direction.UP).getDesign() != WallpaperDesign.NONE && 1d - vec.y < d /16d) {
				wallpaperType = tile.getType(Direction.UP);
			} else if (tile.getType(Direction.DOWN).getDesign() != WallpaperDesign.NONE && vec.y < d /16d) {
				wallpaperType = tile.getType(Direction.DOWN);
			} else if (tile.getType(Direction.NORTH).getDesign() != WallpaperDesign.NONE && vec.z < d /16d) {
				wallpaperType = tile.getType(Direction.NORTH);
			} else if (tile.getType(Direction.SOUTH).getDesign() != WallpaperDesign.NONE && 1d - vec.z < d /16d) {
				wallpaperType = tile.getType(Direction.SOUTH);
			} else if (tile.getType(Direction.EAST).getDesign() != WallpaperDesign.NONE && 1d - vec.x < d /16d) {
				wallpaperType = tile.getType(Direction.EAST);
			} else if (tile.getType(Direction.WEST).getDesign() != WallpaperDesign.NONE && vec.x < d /16d) {
				wallpaperType = tile.getType(Direction.WEST);
			}
		}
		if (wallpaperType != null) {
			ItemStack itemStack = new ItemStack(Registration.WALLPAPER_ITEM.get());
			CompoundNBT tag = itemStack.getOrCreateTag();
			tag.put("wallpaperType", wallpaperType.toNBT());
			return itemStack;
		} else {
			return super.getPickBlock(state, target, world, pos, player);
		}
	}

	@SuppressWarnings("deprecation")
	@NotNull
	@Override
	public PushReaction getPushReaction(@NotNull BlockState state) {
		return PushReaction.DESTROY;
	}

	@SuppressWarnings("deprecation")
	@NotNull
	@Override
	public VoxelShape getShape(@NotNull BlockState state, IBlockReader worldIn, @NotNull BlockPos pos, @NotNull ISelectionContext context) {
		List<VoxelShape> shapeList = new ArrayList<>();

		TileEntity te = worldIn.getTileEntity(pos);
		if (te instanceof WallpaperTile) {
			WallpaperTile wallpaperTile = (WallpaperTile) te;

			if (wallpaperTile.getType(Direction.UP).getDesign() != WallpaperDesign.NONE) {
				shapeList.add(Block.makeCuboidShape(0.0D, 16.0D - THICKNESS, 0.0D, 16.0D, 16.0D, 16.0D));
			}
			if (wallpaperTile.getType(Direction.DOWN).getDesign() != WallpaperDesign.NONE) {
				shapeList.add(Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, THICKNESS, 16.0D));
			}
			if (wallpaperTile.getType(Direction.NORTH).getDesign() != WallpaperDesign.NONE) {
				shapeList.add(Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, THICKNESS));
			}
			if (wallpaperTile.getType(Direction.SOUTH).getDesign() != WallpaperDesign.NONE) {
				shapeList.add(Block.makeCuboidShape(0.0D, 0.0D, 16.0D - THICKNESS, 16.0D, 16.0D, 16.0D));
			}
			if (wallpaperTile.getType(Direction.EAST).getDesign() != WallpaperDesign.NONE) {
				shapeList.add(Block.makeCuboidShape(16.0D - THICKNESS, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D));
			}
			if (wallpaperTile.getType(Direction.WEST).getDesign() != WallpaperDesign.NONE) {
				shapeList.add(Block.makeCuboidShape(0.0D, 0.0D, 0.0D, THICKNESS, 16.0D, 16.0D));
			}
		}
		if (shapeList.isEmpty()) {
			shapeList.add(VoxelShapes.empty());
		}
		VoxelShape shape = shapeList.get(0);
		for (int i = 1; i < shapeList.size(); i++) {
			shape = VoxelShapes.combineAndSimplify(shape, shapeList.get(i), IBooleanFunction.OR);
		}
		return shape;
	}

}
