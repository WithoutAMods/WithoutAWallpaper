package withoutaname.mods.withoutawallpaper.blocks;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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

import javax.annotation.Nonnull;

public class PastingTableContainer extends BaseContainer {

	private TileEntity tileEntity;
	private PlayerEntity playerEntity;
	private IItemHandler playerInventory;
	private Slot paperSlot;
	private Slot dyeSlot0;
	private Slot dyeSlot1;
	private Slot dyeSlot2;
	private Slot outputSlot;

	public PastingTableContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player) {
		super(Registration.PASTING_TABLE_CONTAINER.get(), windowId, 5);
		this.tileEntity = world.getTileEntity(pos);
		this.playerEntity = player;
		this.playerInventory = new InvWrapper(playerInventory);
		if (tileEntity != null) {
			tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
				paperSlot = addSlot(new SlotItemHandler(handler, 0, 12, 20));
				dyeSlot0 = addSlot(new SlotItemHandler(handler, 1, 42, 20));
				dyeSlot1 = addSlot(new SlotItemHandler(handler, 2, 64, 20));
				dyeSlot2 = addSlot(new SlotItemHandler(handler, 3, 86, 20));
			});
			outputSlot = addSlot(new Slot(new Inventory(1), 0, 12, 60) {

				public boolean isItemValid(@Nonnull ItemStack stack) {
					return false;
				}

				@Nonnull
				public ItemStack onTake(@Nonnull PlayerEntity thePlayer, @Nonnull ItemStack stack) {
					WallpaperType wallpaperType = WallpaperType.fromNBT(stack.getTag().getCompound("wallpaperType"));

					PastingTableContainer.this.paperSlot.decrStackSize(1);

					if (wallpaperType.getDesign().getColorCount() > 0) {
						PastingTableContainer.this.dyeSlot0.decrStackSize(1);
					}
					if (wallpaperType.getDesign().getColorCount() > 1) {
						PastingTableContainer.this.dyeSlot1.decrStackSize(1);
					}
					if (wallpaperType.getDesign().getColorCount() > 2) {
						PastingTableContainer.this.dyeSlot2.decrStackSize(1);
					}

					return super.onTake(thePlayer, stack);
				}

			});
			if (tileEntity instanceof PastingTableTile) {
				PastingTableTile rollingStationTile = (PastingTableTile) this.tileEntity;
				trackInt(new IntReferenceHolder() {
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
		addPlayerInventorySlots(this.playerInventory, 8 ,95);
	}

	private void updateOutput(WallpaperType wallpaperType) {
		if (wallpaperType.getDesign() != WallpaperDesign.NONE && PastingTableContainer.this.paperSlot.getHasStack()) {
			ItemStack itemStack = new ItemStack(Registration.WALLPAPER_ITEM.get());
			itemStack.getOrCreateTag().put("wallpaperType", wallpaperType.toNBT());
			itemStack.setCount(8);
			outputSlot.putStack(itemStack);
		} else {
			outputSlot.putStack(ItemStack.EMPTY);
		}
	}

	@Override
	public boolean enchantItem(@Nonnull PlayerEntity playerIn, int id) {
		if (id >= 0 && id < WallpaperDesign.getValuesExceptNone().size() && tileEntity instanceof PastingTableTile) {
			((PastingTableTile) this.tileEntity).setSelectedWallpaperDesign(WallpaperDesign.getValuesExceptNone().get(id));
			return true;
		} else {
			return false;
		}
	}

	public WallpaperDesign getSelectedWallpaperDesign() {
		return ((PastingTableTile) this.tileEntity).getSelectedWallpaperDesign();
	}

	@Override
	public void onContainerClosed(@Nonnull PlayerEntity playerIn) {
		if (tileEntity != null && tileEntity instanceof PastingTableTile) {
			((PastingTableTile) tileEntity).removeWallpaperChangedListener(this);
		}
		super.onContainerClosed(playerIn);
	}

	@Override
	public boolean canInteractWith(@Nonnull PlayerEntity playerIn) {
		return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), playerEntity, Registration.PASTING_TABLE_BLOCK.get());
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
