package withoutaname.mods.withoutawallpaper.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import withoutaname.mods.withoutawallpaper.setup.Registration;
import withoutaname.mods.withoutawallpaper.tools.WallpaperDesign;
import withoutaname.mods.withoutawallpaper.tools.WallpaperType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class RollingStationTile extends TileEntity {

	private WallpaperDesign selectedWallpaperDesign = WallpaperDesign.NONE;
	private WallpaperType wallpaperType = WallpaperType.NONE;

	private final HashMap<RollingStationContainer, Consumer<WallpaperType>> wallpaperChangedListeners = new HashMap<>();

	private final ItemStackHandler itemHandler = createInputHandler();

	private final LazyOptional<IItemHandler> itemHandlerLazyOptional = LazyOptional.of(() -> itemHandler);

	public RollingStationTile() {
		super(Registration.ROLLING_STATION_TILE.get());
	}

	@NotNull
	@Override
	public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return itemHandlerLazyOptional.cast();
		}
		return super.getCapability(cap, side);
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return new AxisAlignedBB(getPos(), getPos().add(1, 1, 1));
	}

	private void updateWallpaper() {
		boolean validWallpaperType = true;

		List<DyeColor> colors = new ArrayList<>();
		DyeColor color;

		for (int i = 1; i <= selectedWallpaperDesign.getColorCount(); i++) {
			color = DyeColor.getColor(itemHandler.getStackInSlot(i));
			colors.add(color);
			if (!selectedWallpaperDesign.getAvailableColors()[i - 1].getColors().contains(color)) {
				validWallpaperType = false;
				break;
			}
		}
		if (validWallpaperType) {
			wallpaperType = new WallpaperType(selectedWallpaperDesign, colors);
		} else {
			wallpaperType = WallpaperType.NONE;
		}
		wallpaperChangedListeners.forEach((rollingStationContainer, wallpaperTypeConsumer) -> {
			wallpaperTypeConsumer.accept(wallpaperType);
		});
	}

	public void addWallpaperChangedListener(RollingStationContainer container, Consumer<WallpaperType> wallpaperChangedListener) {
		wallpaperChangedListeners.put(container, wallpaperChangedListener);
	}

	public void removeWallpaperChangedListener(RollingStationContainer container) {
		wallpaperChangedListeners.remove(container);
	}

	public WallpaperDesign getSelectedWallpaperDesign() {
		return this.selectedWallpaperDesign;
	}

	public void setSelectedWallpaperDesign(WallpaperDesign wallpaperDesign) {
		this.selectedWallpaperDesign = wallpaperDesign;
		update();
	}

	public void update() {
		if (world != null) {
			if (!world.isRemote()) {
				markDirty();
				world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
			}
			updateWallpaper();
		}
	}

	@NotNull
	@Override
	public CompoundNBT getUpdateTag() {
		CompoundNBT tag = super.getUpdateTag();
		writeData(tag);
		return tag;
	}

	@Override
	public void handleUpdateTag(BlockState state, CompoundNBT tag) {
		readData(tag);
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

	@Override
	public void read(BlockState state, CompoundNBT nbt) {
		readData(nbt);
		super.read(state, nbt);
		update();
	}

	private void readData(CompoundNBT nbt) {
		itemHandler.deserializeNBT(nbt.getCompound("inv"));
		selectedWallpaperDesign = WallpaperDesign.fromString(nbt.getString("selectedDesign"));
		updateWallpaper();
	}

	@Override
	public CompoundNBT write(CompoundNBT nbt) {
		writeData(nbt);

		return super.write(nbt);
	}

	private void writeData(CompoundNBT nbt) {
		nbt.put("inv", itemHandler.serializeNBT());
		nbt.putString("selectedDesign", selectedWallpaperDesign.toString());
	}

	private ItemStackHandler createInputHandler() {
		return new ItemStackHandler(4) {

			@NotNull
			@Override
			public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
				if (!isItemValid(slot, stack)) {
					return stack;
				}
				return super.insertItem(slot, stack, simulate);
			}

			@Override
			public boolean isItemValid(int slot, @NotNull ItemStack stack) {
				if (slot == 0) {
					return stack.getItem() == Items.PAPER;
				} else if (slot <= selectedWallpaperDesign.getColorCount()) {
					return selectedWallpaperDesign.getAvailableColors()[slot - 1].getColors().contains(DyeColor.getColor(stack));
				}
				return false;
			}

			@Override
			protected void onContentsChanged(int slot) {
				update();
			}
		};
	}

	public DyeColor[] getColors() {
		return new DyeColor[] {
				DyeColor.getColor(itemHandler.getStackInSlot(1)),
				DyeColor.getColor(itemHandler.getStackInSlot(2)),
				DyeColor.getColor(itemHandler.getStackInSlot(3))};
	}

	public WallpaperType getWallpaperType() {
		return wallpaperType;
	}

	public boolean hasPaper() {
		return !itemHandler.getStackInSlot(0).isEmpty();
	}

}