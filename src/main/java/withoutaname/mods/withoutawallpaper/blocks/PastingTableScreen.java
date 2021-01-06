package withoutaname.mods.withoutawallpaper.blocks;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import withoutaname.mods.withoutalib.blocks.BaseScreen;
import withoutaname.mods.withoutawallpaper.WithoutAWallpaper;
import withoutaname.mods.withoutawallpaper.tools.WallpaperDesign;
import withoutaname.mods.withoutawallpaper.tools.WallpaperType;

@OnlyIn(Dist.CLIENT)
public class PastingTableScreen extends BaseScreen<PastingTableContainer> {

	private final int rowsCount = (int) Math.ceil((double) WallpaperDesign.getValuesExceptNone().size() / 2.0d);
	private int selectedRow = 0;
	private int scrolledY = 0;
	private final boolean scrollable = rowsCount > 3;
	private boolean isScrolling = false;

	public PastingTableScreen(PastingTableContainer container, PlayerInventory playerInventory, ITextComponent title) {
		super(container, new ResourceLocation(WithoutAWallpaper.MODID, "textures/gui/container/rolling_station.png"), playerInventory, title, 176, 177);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
		super.drawGuiContainerBackgroundLayer(matrixStack, partialTicks, x, y);
		int i = this.guiLeft;
		int j = this.guiTop;

		Slot paperSlot = container.getPaperSlot();
		Slot dyeSlot0 = container.getDyeSlot0();
		Slot dyeSlot1 = container.getDyeSlot1();
		Slot dyeSlot2 = container.getDyeSlot2();
		if (!paperSlot.getHasStack()) {
			blit(matrixStack, i + paperSlot.xPos, j + paperSlot.yPos, 176, 0, 16, 16);
		}
		if (!dyeSlot0.getHasStack()) {
			blit(matrixStack, i + dyeSlot0.xPos, j + dyeSlot0.yPos, 192, 0, 16, 16);
		}
		if (!dyeSlot1.getHasStack()) {
			blit(matrixStack, i + dyeSlot1.xPos, j + dyeSlot1.yPos, 192, 0, 16, 16);
		}
		if (!dyeSlot2.getHasStack()) {
			blit(matrixStack, i + dyeSlot2.xPos, j + dyeSlot2.yPos, 192, 0, 16, 16);
		}

		i = this.guiLeft + 156;
		j = this.guiTop + 17;
		if (this.scrollable) {
			blit(matrixStack, i, j + this.scrolledY, 232, 0, 12, 15);
		} else {
			blit(matrixStack, i, j, 244, 0, 12, 15);
		}

		i = this.guiLeft + 112;
		j = this.guiTop + 17;
		int k;
		int l;
		int relId;
		for (int id = selectedRow * 2; id < (selectedRow + 3) * 2; id++) {
			if (id < WallpaperDesign.getValuesExceptNone().size()) {
				relId = id - selectedRow * 2;
				k = i + relId % 2 * 21;
				l = j + relId / 2 * 21;
				WallpaperDesign design = WallpaperDesign.getValuesExceptNone().get(id);
				this.minecraft.getTextureManager().bindTexture(GUI_TEXTURE);
				if (design == container.getSelectedWallpaperDesign()) {
					blit(matrixStack, k, l, 0, 198, 21, 21);
				} else if (x >= k && x < k + 21 && y >= l && y < l + 21) {
					blit(matrixStack, k, l, 0, 219, 21, 21);
				} else {
					blit(matrixStack, k, l, 0, 177, 21, 21);
				}
				this.minecraft.getTextureManager().bindTexture(new ResourceLocation(WithoutAWallpaper.MODID, "textures/block/wallpaper/" + design.toString() + "/design.png"));
				blit(matrixStack, k + 2, l + 2, 17, 17, 0, 0, 16, 16, 16, 16 );
			} else {
				break;
			}
		}

		i = this.guiLeft + 52;
		j = this.guiTop + 41;
		if (container.getOutputSlot().getHasStack()) {
			WallpaperType wallpaperType = WallpaperType.fromNBT(container.getOutputSlot().getStack().getTag().getCompound("wallpaperType"));

			for (ResourceLocation resourceLocation : wallpaperType.getResourceLocations()) {
				this.minecraft.getTextureManager().bindTexture(new ResourceLocation(resourceLocation.getNamespace(), "textures/" + resourceLocation.getPath() + ".png"));
				blit(matrixStack, i, j, 40, 40, 0, 0, 16, 16, 16, 16);
			}
		}
	}


	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		this.isScrolling = false;
		int i = this.guiLeft + 112;
		int j = this.guiTop + 17;

		if (mouseX >= (double) i && mouseX < (double) (i + 42) && mouseY >= (double) j && mouseY < (double) (j + 70)) {
			int id = this.selectedRow * 3 + ((int) ((mouseX - i) / 21)) + ((int) ((mouseY - j) / 21)) * 3;
			this.minecraft.playerController.sendEnchantPacket((this.container).windowId, id);
			return true;
		}

		i = this.guiLeft + 156;
		j = this.guiTop + 17;
		if (mouseX >= (double)i && mouseX < (double)(i + 12) && mouseY >= (double)j && mouseY < (double)(j + 70)) {
			this.isScrolling = true;
			mouseDragged(mouseX, mouseY, button, mouseX, mouseY);
		}
		return super.mouseClicked(mouseX, mouseY, button);
	}

	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
		if (this.isScrolling && this.scrollable) {
			double y = dragY - this.guiTop - 24.5d;
			if (y < 0d) {
				y = 0d;
			} else if (y > 48d) {
				y = 48d;
			}
			this.scrolledY = (int) Math.round(y);
			this.selectedRow = (int) Math.round(y / 48d * (double) (rowsCount - 5));
			return true;
		} else {
			return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
		}
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
		if (this.scrollable) {
			while (delta < 0 && this.selectedRow < this.rowsCount - 5) {
				this.selectedRow++;
				delta++;
			}
			while (delta > 0 && this.selectedRow > 0) {
				this.selectedRow--;
				delta--;
			}
			this.scrolledY = (int) Math.round((double) this.selectedRow / (double) (rowsCount - 5) * 48d);
		}

		return true;
	}
}
