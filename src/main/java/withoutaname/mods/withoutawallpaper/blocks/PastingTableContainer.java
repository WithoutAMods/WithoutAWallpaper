package withoutaname.mods.withoutawallpaper.blocks;

import javax.annotation.Nonnull;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import withoutaname.mods.withoutalib.blocks.BaseContainer;
import withoutaname.mods.withoutawallpaper.setup.Registration;
import withoutaname.mods.withoutawallpaper.tools.WallpaperDesign;
import withoutaname.mods.withoutawallpaper.tools.WallpaperType;

public class PastingTableContainer extends BaseContainer {
	
	private final BlockEntity tileEntity;
	private final Player playerEntity;
	
	private Slot paperSlot;
	private Slot dyeSlot0;
	private Slot dyeSlot1;
	private Slot dyeSlot2;
	private Slot outputSlot;
	
	public PastingTableContainer(int windowId, Level world, BlockPos pos, Inventory playerInventory, Player player) {
		super(Registration.PASTING_TABLE_CONTAINER.get(), windowId, 5);
		this.tileEntity = world.getBlockEntity(pos);
		this.playerEntity = player;
		IItemHandler playerInventory1 = new InvWrapper(playerInventory);
		if (tileEntity != null) {
			tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
				paperSlot = addSlot(new SlotItemHandler(handler, 0, 12, 20));
				dyeSlot0 = addSlot(new SlotItemHandler(handler, 1, 42, 20));
				dyeSlot1 = addSlot(new SlotItemHandler(handler, 2, 64, 20));
				dyeSlot2 = addSlot(new SlotItemHandler(handler, 3, 86, 20));
			});
			outputSlot = addSlot(new Slot(new SimpleContainer(1), 0, 12, 60) {
				
				public boolean mayPlace(@Nonnull ItemStack stack) {
					return false;
				}
				
				public void onTake(@Nonnull Player thePlayer, @Nonnull ItemStack stack) {
					CompoundTag tag = stack.getTag();
					assert tag != null;
					WallpaperType wallpaperType = WallpaperType.fromNBT(tag.getCompound("wallpaperType"));
					
					PastingTableContainer.this.paperSlot.remove(1);
					
					if (wallpaperType.getDesign().getColorCount() > 0) {
						PastingTableContainer.this.dyeSlot0.remove(1);
					}
					if (wallpaperType.getDesign().getColorCount() > 1) {
						PastingTableContainer.this.dyeSlot1.remove(1);
					}
					if (wallpaperType.getDesign().getColorCount() > 2) {
						PastingTableContainer.this.dyeSlot2.remove(1);
					}
					
					super.onTake(thePlayer, stack);
				}
				
			});
			if (tileEntity instanceof PastingTableEntity) {
				PastingTableEntity rollingStationTile = (PastingTableEntity) this.tileEntity;
				addDataSlot(new DataSlot() {
					@Override
					public int get() {
						return rollingStationTile.getSelectedWallpaperDesign().toInt();
					}
					
					@Override
					public void set(int value) {
						rollingStationTile.setSelectedWallpaperDesign(WallpaperDesign.fromInt(value));
					}
				});
				rollingStationTile.addWallpaperChangedListener(this, this::updateOutput);
				updateOutput(rollingStationTile.getWallpaperType());
			}
		}
		addPlayerInventorySlots(playerInventory1, 8, 95);
	}
	
	private void updateOutput(WallpaperType wallpaperType) {
		if (wallpaperType.getDesign() != WallpaperDesign.NONE && PastingTableContainer.this.paperSlot.hasItem()) {
			ItemStack itemStack = new ItemStack(Registration.WALLPAPER_ITEM.get());
			itemStack.getOrCreateTag().put("wallpaperType", wallpaperType.toNBT());
			itemStack.setCount(8);
			outputSlot.set(itemStack);
		} else {
			outputSlot.set(ItemStack.EMPTY);
		}
	}
	
	@Override
	public boolean clickMenuButton(@Nonnull Player playerIn, int id) {
		if (tileEntity instanceof PastingTableEntity) {
			((PastingTableEntity) tileEntity).setSelectedWallpaperDesign(
					id >= 0 && id < WallpaperDesign.getValuesExceptNone().size() ?
							WallpaperDesign.getValuesExceptNone().get(id) :
							WallpaperDesign.NONE);
			return true;
		} else {
			return false;
		}
	}
	
	public WallpaperDesign getSelectedWallpaperDesign() {
		return ((PastingTableEntity) this.tileEntity).getSelectedWallpaperDesign();
	}
	
	@Override
	public void removed(@Nonnull Player playerIn) {
		if (tileEntity != null && tileEntity instanceof PastingTableEntity) {
			((PastingTableEntity) tileEntity).removeWallpaperChangedListener(this);
		}
		super.removed(playerIn);
	}
	
	@Override
	public boolean stillValid(@Nonnull Player playerIn) {
		Level level = tileEntity.getLevel();
		assert level != null;
		return stillValid(ContainerLevelAccess.create(level, tileEntity.getBlockPos()), playerEntity, Registration.PASTING_TABLE_BLOCK.get());
	}
	
	@OnlyIn(Dist.CLIENT)
	public Slot getPaperSlot() {
		return this.paperSlot;
	}
	
	@OnlyIn(Dist.CLIENT)
	public Slot getDyeSlot0() {
		return this.dyeSlot0;
	}
	
	@OnlyIn(Dist.CLIENT)
	public Slot getDyeSlot1() {
		return this.dyeSlot1;
	}
	
	@OnlyIn(Dist.CLIENT)
	public Slot getDyeSlot2() {
		return this.dyeSlot2;
	}
	
	@OnlyIn(Dist.CLIENT)
	public Slot getOutputSlot() {
		return this.outputSlot;
	}
	
}
