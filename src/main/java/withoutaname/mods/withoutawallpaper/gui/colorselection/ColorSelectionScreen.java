package withoutaname.mods.withoutawallpaper.gui.colorselection;

import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nonnull;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.DyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

import withoutaname.mods.withoutawallpaper.WithoutAWallpaper;

public class ColorSelectionScreen extends Screen {
	
	protected final ResourceLocation GUI_TEXTURE = new ResourceLocation(WithoutAWallpaper.MODID, "textures/gui/color_selection.png");
	
	private final int xSize = 112;
	private final int ySize = 112;
	private final Screen parent;
	private final List<DyeColor> availableColors;
	private final Consumer<DyeColor> colorConsumer;
	private int guiLeft;
	private int guiTop;
	
	public ColorSelectionScreen(Screen parent, List<DyeColor> availableColors, Consumer<DyeColor> colorConsumer) {
		super(new TranslationTextComponent("screen." + WithoutAWallpaper.MODID + ".color_selection"));
		this.parent = parent;
		this.availableColors = availableColors;
		this.colorConsumer = colorConsumer;
	}
	
	@Override
	protected void init() {
		super.init();
		guiLeft = (width - xSize) / 2;
		guiTop = (height - ySize) / 2;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				DyeColor color = DyeColor.byId(i + j * 4);
				ColorButton colorButton = addButton(new ColorButton(guiLeft + 10 + i * 24, guiTop + 10 + j * 24, () -> color, button -> onPress(color), this));
				if (!availableColors.contains(color)) {
					colorButton.active = false;
				}
			}
		}
	}
	
	@Override
	public void render(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
		this.drawGuiBackgroundLayer(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
	}
	
	private void onPress(DyeColor color) {
		colorConsumer.accept(color);
		Minecraft.getInstance().setScreen(parent);
	}
	
	protected void drawGuiBackgroundLayer(MatrixStack matrixStack) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		assert this.minecraft != null;
		this.minecraft.getTextureManager().bind(GUI_TEXTURE);
		int i = this.guiLeft;
		int j = this.guiTop;
		this.blit(matrixStack, i, j, 0, 0, this.xSize, this.ySize);
	}
	
	@Override
	public boolean isPauseScreen() {
		return false;
	}
	
	@Override
	public void onClose() {
		assert minecraft != null;
		minecraft.setScreen(parent);
	}
	
}
