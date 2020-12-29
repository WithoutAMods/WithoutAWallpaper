package withoutaname.mods.withoutawallpaper.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import org.jetbrains.annotations.Nullable;
import withoutaname.mods.withoutawallpaper.tools.WallpaperDesign;

import java.util.ArrayList;
import java.util.List;

public class WallpaperBlock extends Block {

	public static final double THICKNESS = 0.1D;

	public WallpaperBlock() {
		super(Properties.create(Material.MISCELLANEOUS)
				.sound(SoundType.CLOTH)
				.hardnessAndResistance(0.5F));
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
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
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
			shapeList.add(Block.makeCuboidShape(1.0D, 1.0D, 1.0D, 15.0D, 15.0D, 15.0D));
		}
		VoxelShape shape = shapeList.get(0);
		for (int i = 1; i < shapeList.size(); i++) {
			shape = VoxelShapes.combineAndSimplify(shape, shapeList.get(i), IBooleanFunction.OR);
		}
		return shape;
	}

}
