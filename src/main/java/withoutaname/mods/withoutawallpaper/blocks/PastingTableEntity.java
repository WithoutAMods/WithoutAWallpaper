package withoutaname.mods.withoutawallpaper.blocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import withoutaname.mods.withoutawallpaper.setup.Registration;
import withoutaname.mods.withoutawallpaper.tools.WallpaperDesign;
import withoutaname.mods.withoutawallpaper.tools.WallpaperType;

public class PastingTableEntity extends BlockEntity {
	
	private final HashMap<PastingTableContainer, Consumer<WallpaperType>> wallpaperChangedListeners = new HashMap<>();
	
	private WallpaperDesign selectedWallpaperDesign = WallpaperDesign.NONE;
	private WallpaperType wallpaperType = WallpaperType.NONE;
	
	private final ItemStackHandler itemHandler = createInputHandler();
	private final LazyOptional<IItemHandler> itemHandlerLazyOptional = LazyOptional.of(() -> itemHandler);
	
	public PastingTableEntity(BlockPos pos, BlockState state) {
		super(Registration.PASTING_TABLE_TILE.get(), pos, state);
	}
	
	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return itemHandlerLazyOptional.cast();
		}
		return super.getCapability(cap, side);
	}
	
	@Override
	public void setRemoved() {
		super.setRemoved();
		itemHandlerLazyOptional.invalidate();
	}
	
	@Override
	public AABB getRenderBoundingBox() {
		return new AABB(getBlockPos(), getBlockPos().offset(1, 1, 1));
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
		wallpaperChangedListeners.forEach((rollingStationContainer, wallpaperTypeConsumer) -> wallpaperTypeConsumer.accept(wallpaperType));
	}
	
	public void addWallpaperChangedListener(PastingTableContainer container, Consumer<WallpaperType> wallpaperChangedListener) {
		wallpaperChangedListeners.put(container, wallpaperChangedListener);
	}
	
	public void removeWallpaperChangedListener(PastingTableContainer container) {
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
		if (level != null) {
			if (!level.isClientSide()) {
				setChanged();
				level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
			}
			updateWallpaper();
		}
	}
	
	@Nonnull
	@Override
	public CompoundTag getUpdateTag() {
		CompoundTag tag = super.getUpdateTag();
		writeData(tag);
		return tag;
	}
	
	@Override
	public void handleUpdateTag(CompoundTag tag) {
		readData(tag);
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
	
	@Override
	public void load(@Nonnull CompoundTag nbt) {
		readData(nbt);
		super.load(nbt);
		update();
	}
	
	private void readData(CompoundTag nbt) {
		itemHandler.deserializeNBT(nbt.getCompound("inv"));
		try {
			selectedWallpaperDesign = WallpaperDesign.fromString(nbt.getString("selectedDesign"));
		} catch (IllegalArgumentException e) { // If custom WallpaperDesign was removed from world
			selectedWallpaperDesign = WallpaperDesign.NONE;
		}
		updateWallpaper();
	}
	
	@Nonnull
	@Override
	public CompoundTag save(@Nonnull CompoundTag nbt) {
		writeData(nbt);
		
		return super.save(nbt);
	}
	
	private void writeData(CompoundTag nbt) {
		nbt.put("inv", itemHandler.serializeNBT());
		nbt.putString("selectedDesign", selectedWallpaperDesign.toString());
	}
	
	private ItemStackHandler createInputHandler() {
		return new ItemStackHandler(4) {
			
			@Nonnull
			@Override
			public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
				if (!isItemValid(slot, stack)) {
					return stack;
				}
				return super.insertItem(slot, stack, simulate);
			}
			
			@Override
			public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
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
		return new DyeColor[]{
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