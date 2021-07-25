package withoutaname.mods.withoutawallpaper.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;

public class PastingTableBlock extends BaseEntityBlock {
	
	public PastingTableBlock() {
		super(Properties.of(Material.WOOD)
				.strength(2.5F)
				.sound(SoundType.WOOD));
	}
	
	@Nullable
	@Override
	public BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
		return new PastingTableEntity(pos, state);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	@Nonnull
	public InteractionResult use(@Nonnull BlockState state, Level world, @Nonnull BlockPos pos, @Nonnull Player player, @Nonnull InteractionHand hand, @Nonnull BlockHitResult trace) {
		if (!world.isClientSide) {
			BlockEntity tileEntity = world.getBlockEntity(pos);
			if (tileEntity instanceof PastingTableEntity presentTile) {
				SimpleMenuProvider menuProvider = new SimpleMenuProvider(
						(pContainerId, pInventory, pPlayer) -> new PastingTableContainer(pContainerId, world, pos, pInventory, pPlayer),
						new TranslatableComponent("screen.withoutawallpaper.pasting_table"));
				NetworkHooks.openGui((ServerPlayer) player, menuProvider, tileEntity.getBlockPos());
			} else {
				throw new IllegalStateException("No tile entity found!");
			}
		}
		return InteractionResult.SUCCESS;
	}
	
	@SuppressWarnings("deprecation")
	@Nonnull
	@Override
	public VoxelShape getShape(@Nonnull BlockState state, @Nonnull BlockGetter worldIn, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
		VoxelShape shape = Block.box(0, 10, 0, 16, 11, 16);
		shape = Shapes.join(shape, Block.box(1, 0, 1, 3, 10, 3), BooleanOp.OR);
		shape = Shapes.join(shape, Block.box(1, 0, 13, 3, 10, 15), BooleanOp.OR);
		shape = Shapes.join(shape, Block.box(13, 0, 13, 15, 10, 15), BooleanOp.OR);
		shape = Shapes.join(shape, Block.box(13, 0, 1, 15, 10, 3), BooleanOp.OR);
		return shape;
	}
	
	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, context.getHorizontalDirection());
	}
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(BlockStateProperties.HORIZONTAL_FACING);
	}
	
}
