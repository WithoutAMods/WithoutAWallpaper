package withoutaname.mods.withoutawallpaper.blocks;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import withoutaname.mods.withoutalib.blocks.BaseScreen;
import withoutaname.mods.withoutawallpaper.WithoutAWallpaper;
import withoutaname.mods.withoutawallpaper.gui.DesignSelectionWidget;
import withoutaname.mods.withoutawallpaper.gui.IDesignSelectable;
import withoutaname.mods.withoutawallpaper.tools.WallpaperDesign;
import withoutaname.mods.withoutawallpaper.tools.WallpaperType;

@OnlyIn(Dist.CLIENT)
public class PastingTableScreen extends BaseScreen<PastingTableContainer> implements IDesignSelectable {
	
	public PastingTableScreen(PastingTableContainer container, PlayerInventory playerInventory, ITextComponent title) {
		super(container, new ResourceLocation(WithoutAWallpaper.MODID, "textures/gui/container/pasting_table.png"), playerInventory, title, 176, 177);
	}
	
	@Override
	protected void init() {
		super.init();
		addButton(new DesignSelectionWidget(leftPos + 112, topPos + 17, 2, 3, this::addButton, this));
	}
	
	@Override
	protected void renderBg(@Nonnull MatrixStack matrixStack, float partialTicks, int x, int y) {
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
		
		i = this.leftPos + 52;
		j = this.topPos + 41;
		if (menu.getOutputSlot().hasItem()) {
			CompoundNBT tag = menu.getOutputSlot().getItem().getTag();
			assert tag != null;
			WallpaperType wallpaperType = WallpaperType.fromNBT(tag.getCompound("wallpaperType"));
			
			for (ResourceLocation resourceLocation : wallpaperType.getResourceLocations()) {
				assert this.minecraft != null;
				this.minecraft.getTextureManager().bind(new ResourceLocation(resourceLocation.getNamespace(), "textures/" + resourceLocation.getPath() + ".png"));
				blit(matrixStack, i, j, 40, 40, 0, 0, 16, 16, 16, 16);
			}
		}
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
