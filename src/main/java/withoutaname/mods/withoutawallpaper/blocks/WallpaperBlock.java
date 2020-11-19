package withoutaname.mods.withoutawallpaper.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import org.jetbrains.annotations.NotNull;
import withoutaname.mods.withoutawallpaper.tools.WallpaperType;

import java.util.ArrayList;
import java.util.List;

public class WallpaperBlock extends Block {

	public static final EnumProperty<WallpaperType> NORTH = EnumProperty.create("north", WallpaperType.class);
	public static final EnumProperty<WallpaperType> EAST = EnumProperty.create("east", WallpaperType.class);
	public static final EnumProperty<WallpaperType> SOUTH = EnumProperty.create("south", WallpaperType.class);
	public static final EnumProperty<WallpaperType> WEST = EnumProperty.create("west", WallpaperType.class);
	public static final EnumProperty<WallpaperType> UP = EnumProperty.create("up", WallpaperType.class);
	public static final EnumProperty<WallpaperType> DOWN = EnumProperty.create("down", WallpaperType.class);

	public WallpaperBlock() {
		super(Properties.create(Material.MISCELLANEOUS)
				.sound(SoundType.CLOTH)
				.hardnessAndResistance(0.5F));
		this.setDefaultState(this.stateContainer.getBaseState()
				.with(NORTH, WallpaperType.NONE)
				.with(EAST, WallpaperType.NONE)
				.with(SOUTH, WallpaperType.NONE)
				.with(WEST, WallpaperType.NONE)
				.with(UP, WallpaperType.NONE)
				.with(DOWN, WallpaperType.NONE));
	}

	@NotNull
	public static EnumProperty<WallpaperType> getProperty(Direction face) {
		EnumProperty<WallpaperType> property;
		switch (face) {
			default:
			case NORTH:
				property = WallpaperBlock.NORTH;
				break;
			case EAST:
				property = WallpaperBlock.EAST;
				break;
			case SOUTH:
				property = WallpaperBlock.SOUTH;
				break;
			case WEST:
				property = WallpaperBlock.WEST;
				break;
			case UP:
				property = WallpaperBlock.UP;
				break;
			case DOWN:
				property = WallpaperBlock.DOWN;
				break;
		}
		return property;
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
	}

	@SuppressWarnings("deprecation")
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		List<VoxelShape> shapeList = new ArrayList<>();
		if (state.get(NORTH) != WallpaperType.NONE) {
			shapeList.add(Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 1.0D));
		}
		if (state.get(EAST) != WallpaperType.NONE) {
			shapeList.add(Block.makeCuboidShape(15.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D));
		}
		if (state.get(SOUTH) != WallpaperType.NONE) {
			shapeList.add(Block.makeCuboidShape(0.0D, 0.0D, 15.0D, 16.0D, 16.0D, 16.0D));
		}
		if (state.get(WEST) != WallpaperType.NONE) {
			shapeList.add(Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 1.0D, 16.0D, 16.0D));
		}
		if (state.get(UP) != WallpaperType.NONE) {
			shapeList.add(Block.makeCuboidShape(0.0D, 15.0D, 0.0D, 16.0D, 16.0D, 16.0D));
		}
		if (state.get(DOWN) != WallpaperType.NONE) {
			shapeList.add(Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D));
		}
		if (shapeList.isEmpty()) {
			shapeList.add(VoxelShapes.fullCube());
		}
		VoxelShape shape = shapeList.get(0);
		for (int i = 1; i < shapeList.size(); i++) {
			shape = VoxelShapes.combineAndSimplify(shape, shapeList.get(i), IBooleanFunction.OR);
		}
		return shape;
	}

}
