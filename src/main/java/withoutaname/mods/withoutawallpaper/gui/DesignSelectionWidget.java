package withoutaname.mods.withoutawallpaper.gui;

import java.util.function.Consumer;
import javax.annotation.Nonnull;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

import withoutaname.mods.withoutawallpaper.WithoutAWallpaper;
import withoutaname.mods.withoutawallpaper.tools.WallpaperDesign;

public class DesignSelectionWidget extends Widget {
	
	private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(WithoutAWallpaper.MODID, "textures/gui/container/pasting_table.png");
	
	private final int designsPerRow;
	private final int designsPerCollum;
	private final DesignButton[] buttons;
	
	private final int rowsCount;
	private final boolean scrollable;
	private int selectedRow = 0;
	private int scrolledY = 0;
	private boolean isScrolling = false;
	
	public DesignSelectionWidget(int x, int y, int designsPerRow, int designsPerCollum, Consumer<Widget> widgetConsumer, IDesignSelectable designSelectable) {
		super(x, y, designsPerRow * 21 + 14, designsPerCollum * 21, StringTextComponent.EMPTY);
		this.designsPerRow = designsPerRow;
		this.designsPerCollum = designsPerCollum;
		rowsCount = (int) Math.ceil((double) WallpaperDesign.getValuesExceptNone().size() / designsPerRow);
		scrollable = rowsCount > designsPerCollum;
		buttons = new DesignButton[designsPerRow * designsPerCollum];
		for (int i = 0; i < designsPerRow; i++) {
			for (int j = 0; j < designsPerCollum; j++) {
				buttons[i + j * designsPerRow] = new DesignButton(x + i * 21, y + j * 21, designSelectable, WallpaperDesign.NONE);
				widgetConsumer.accept(buttons[i + j * designsPerRow]);
			}
		}
		updateButtons();
	}
	
	private void updateButtons() {
		for (int i = 0; i < designsPerRow * designsPerCollum; i++) {
			int id = i + selectedRow * designsPerRow;
			if (id < WallpaperDesign.getValuesExceptNone().size()) {
				buttons[i].setDesign(WallpaperDesign.getValuesExceptNone().get(id));
			} else {
				buttons[i].setDesign(WallpaperDesign.NONE);
			}
		}
	}
	
	@Override
	public void render(@Nonnull MatrixStack matrixStack, int x, int y, float partialTicks) {
		Minecraft minecraft = Minecraft.getInstance();
		minecraft.getTextureManager().bind(GUI_TEXTURE);
		
		int i = this.x + 44;
		int j = this.y;
		if (this.scrollable) {
			blit(matrixStack, i, j + this.scrolledY, 232, 0, 12, 15);
		} else {
			blit(matrixStack, i, j, 244, 0, 12, 15);
		}
	}
	
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		Minecraft minecraft = Minecraft.getInstance();
		minecraft.getTextureManager().bind(GUI_TEXTURE);
		
		this.isScrolling = false;
		int i = this.x + 44;
		int j = this.y;
		if (mouseX >= (double) i && mouseX < (double) (i + 12) && mouseY >= (double) j && mouseY < (double) (j + 70)) {
			this.isScrolling = true;
			mouseDragged(mouseX, mouseY, button, 0, 0);
		}
		return super.mouseClicked(mouseX, mouseY, button);
	}
	
	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
		if (this.isScrolling && this.scrollable) {
			double y = mouseY + dragY - this.y - 7.5;
			if (y < 0) {
				y = 0;
			} else if (y > height - 15) {
				y = height - 15;
			}
			this.scrolledY = (int) Math.round(y);
			this.selectedRow = (int) Math.round(y / (height - 15) * (double) (rowsCount - designsPerCollum));
			updateButtons();
			return true;
		} else {
			return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
		}
	}
	
	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
		if (this.scrollable) {
			while (delta < 0 && this.selectedRow < this.rowsCount - designsPerCollum) {
				this.selectedRow++;
				delta++;
			}
			while (delta > 0 && this.selectedRow > 0) {
				this.selectedRow--;
				delta--;
			}
			this.scrolledY = (int) Math.round((double) this.selectedRow / (double) (rowsCount - designsPerCollum) * (height - 15));
			updateButtons();
		}
		
		return true;
	}
	
}
