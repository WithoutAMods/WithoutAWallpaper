package withoutaname.mods.withoutawallpaper.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
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

public class WallpaperTile extends TileEntity {

	public static final ModelProperty<HashMap<Direction, WallpaperType>> DESIGNS = new ModelProperty<>();

	private final HashMap<Direction, WallpaperType> designs = new HashMap<>();

	public WallpaperTile() {
		super(Registration.WALLPAPER_TILE.get());
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
		if (world != null) {
			if (world.isRemote()) {
				ModelDataManager.requestModelDataRefresh(this);
			} else {
				markDirty();
			}
			world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), Constants.BlockFlags.BLOCK_UPDATE + Constants.BlockFlags.NOTIFY_NEIGHBORS);
		}
	}

	@Nonnull
	@Override
	public CompoundNBT getUpdateTag() {
		CompoundNBT tag = super.getUpdateTag();
		writeDesigns(tag);
		return tag;
	}

	@Override
	public void handleUpdateTag(BlockState state, CompoundNBT tag) {
		readDesigns(tag);
	}

	@Nullable
	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
		return new SUpdateTileEntityPacket(pos, 1, getUpdateTag());
	}

	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
		handleUpdateTag(getBlockState(), pkt.getNbtCompound());
	}

	@Nonnull
	@Override
	public IModelData getModelData() {
		return new ModelDataMap.Builder()
				.withInitial(DESIGNS, designs)
				.build();
	}

	@Override
	public void read(@Nonnull BlockState state, CompoundNBT nbt) {
		super.read(state, nbt);
		readDesigns(nbt);
	}

	private void readDesigns(CompoundNBT nbt) {
		for (Direction direction : Direction.values()) {
			if (nbt.contains(direction.toString())) {
				designs.put(direction, WallpaperType.fromNBT(nbt.getCompound(direction.toString())));
			}
		}
		update();
	}

	@Nonnull
	@Override
	public CompoundNBT write(@Nonnull CompoundNBT nbt) {
		writeDesigns(nbt);
		return super.write(nbt);
	}

	private void writeDesigns(CompoundNBT nbt) {
		designs.forEach((direction, wallpaperType) -> {
			nbt.put(direction.toString(), wallpaperType.toNBT());
		});
	}

}
