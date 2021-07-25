package withoutaname.mods.withoutawallpaper.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.ModelDataManager;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.client.model.data.ModelProperty;
import net.minecraftforge.common.util.Constants;
import withoutaname.mods.withoutawallpaper.setup.Registration;
import withoutaname.mods.withoutawallpaper.tools.WallpaperType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;

public class WallpaperEntity extends BlockEntity {
	
	public static final ModelProperty<HashMap<Direction, WallpaperType>> DESIGNS = new ModelProperty<>();
	
	private final HashMap<Direction, WallpaperType> designs = new HashMap<>();
	
	public WallpaperEntity(BlockPos pos, BlockState state) {
		super(Registration.WALLPAPER_TILE.get(), pos, state);
		for (Direction direction : Direction.values()) {
			designs.put(direction, WallpaperType.NONE);
		}
	}
	
	public WallpaperType getType(Direction direction) {
		return designs.get(direction);
	}
	
	public void setType(Direction direction, WallpaperType wallpaperType) {
		designs.put(direction, wallpaperType);
		update();
	}
	
	public void update() {
		if (level != null) {
			if (level.isClientSide()) {
				ModelDataManager.requestModelDataRefresh(this);
			} else {
				setChanged();
			}
			level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Constants.BlockFlags.BLOCK_UPDATE + Constants.BlockFlags.NOTIFY_NEIGHBORS);
		}
	}
	
	@Nonnull
	@Override
	public CompoundTag getUpdateTag() {
		CompoundTag tag = super.getUpdateTag();
		writeDesigns(tag);
		return tag;
	}
	
	@Override
	public void handleUpdateTag(CompoundTag tag) {
		readDesigns(tag);
	}
	
	@Nullable
	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return new ClientboundBlockEntityDataPacket(worldPosition, 1, getUpdateTag());
	}
	
	@Override
	public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
		handleUpdateTag(pkt.getTag());
	}
	
	@Nonnull
	@Override
	public IModelData getModelData() {
		return new ModelDataMap.Builder()
				.withInitial(DESIGNS, designs)
				.build();
	}
	
	@Override
	public void load(@Nonnull CompoundTag nbt) {
		super.load(nbt);
		readDesigns(nbt);
	}
	
	private void readDesigns(CompoundTag nbt) {
		for (Direction direction : Direction.values()) {
			if (nbt.contains(direction.toString())) {
				designs.put(direction, WallpaperType.fromNBT(nbt.getCompound(direction.toString())));
			}
		}
		update();
	}
	
	@Nonnull
	@Override
	public CompoundTag save(@Nonnull CompoundTag nbt) {
		writeDesigns(nbt);
		return super.save(nbt);
	}
	
	private void writeDesigns(CompoundTag nbt) {
		designs.forEach((direction, wallpaperType) -> nbt.put(direction.toString(), wallpaperType.toNBT()));
	}
	
}
