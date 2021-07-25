package withoutaname.mods.withoutawallpaper.blocks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import withoutaname.mods.withoutalib.blocks.BaseScreen;
import withoutaname.mods.withoutawallpaper.WithoutAWallpaper;
import withoutaname.mods.withoutawallpaper.gui.WallpaperWidget;
import withoutaname.mods.withoutawallpaper.gui.designselection.DesignSelectionWidget;
import withoutaname.mods.withoutawallpaper.gui.designselection.IDesignSelectable;
import withoutaname.mods.withoutawallpaper.tools.WallpaperDesign;
import withoutaname.mods.withoutawallpaper.tools.WallpaperType;

@OnlyIn(Dist.CLIENT)
public class PastingTableScreen extends BaseScreen<PastingTableContainer> implements IDesignSelectable {
	
	public PastingTableScreen(PastingTableContainer container, Inventory playerInventory, Component title) {
		super(container, new ResourceLocation(WithoutAWallpaper.MODID, "textures/gui/container/pasting_table.png"), playerInventory, title, 176, 177);
	}
	
	@Override
	protected void init() {
		super.init();
		addRenderableWidget(new DesignSelectionWidget(leftPos + 112, topPos + 17, 2, 3, this::addRenderableWidget, this));
		addRenderableWidget(new WallpaperWidget(leftPos + 52, topPos + 41, 40, this::getWallpaperType));
	}
	
	@Override
	protected void renderBg(@Nonnull PoseStack matrixStack, float partialTicks, int x, int y) {
		super.renderBg(matrixStack, partialTicks, x, y);
		int i = this.leftPos;
		int j = this.topPos;
		
		Slot paperSlot = menu.getPaperSlot();
		Slot dyeSlot0 = menu.getDyeSlot0();
		Slot dyeSlot1 = menu.getDyeSlot1();
		Slot dyeSlot2 = menu.getDyeSlot2();
		if (!paperSlot.hasItem()) {
			blit(matrixStack, i + paperSlot.x, j + paperSlot.y, 176, 0, 16, 16);
		}
		if (!dyeSlot0.hasItem()) {
			blit(matrixStack, i + dyeSlot0.x, j + dyeSlot0.y, 192, 0, 16, 16);
		}
		if (!dyeSlot1.hasItem()) {
			blit(matrixStack, i + dyeSlot1.x, j + dyeSlot1.y, 192, 0, 16, 16);
		}
		if (!dyeSlot2.hasItem()) {
			blit(matrixStack, i + dyeSlot2.x, j + dyeSlot2.y, 192, 0, 16, 16);
		}
	}
	
	@Nullable
	private WallpaperType getWallpaperType() {
		if (!menu.getPaperSlot().hasItem()) {
			return null;
		} else if (!menu.getOutputSlot().hasItem()) {
			return WallpaperType.NONE;
		}
		CompoundTag tag = menu.getOutputSlot().getItem().getTag();
		assert tag != null;
		return WallpaperType.fromNBT(tag.getCompound("wallpaperType"));
	}
	
	@Override
	public WallpaperDesign getDesign() {
		return menu.getSelectedWallpaperDesign();
	}
	
	@Override
	public void setDesign(@Nonnull WallpaperDesign design) {
		assert minecraft != null;
		assert minecraft.gameMode != null;
		minecraft.gameMode.handleInventoryButtonClick((this.menu).containerId, design.toInt());
	}
	
}
