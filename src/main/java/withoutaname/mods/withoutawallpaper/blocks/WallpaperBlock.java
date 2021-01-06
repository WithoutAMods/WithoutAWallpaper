package withoutaname.mods.withoutawallpaper.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import withoutaname.mods.withoutawallpaper.tools.WallpaperDesign;

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
