package withoutaname.mods.withoutawallpaper.gui.colorselection;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import withoutaname.mods.withoutawallpaper.WithoutAWallpaper;

import java.util.List;
import java.util.function.Consumer;

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
		super(new TranslatableComponent("screen." + WithoutAWallpaper.MODID + ".color_selection"));
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
				ColorButton colorButton = addRenderableWidget(new ColorButton(guiLeft + 10 + i * 24, guiTop + 10 + j * 24, () -> color, button -> onPress(color), this));
				if (!availableColors.contains(color)) {
					colorButton.active = false;
				}
			}
		}
	}
	
	@Override
	public void render(@Nonnull PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
		this.drawGuiBackgroundLayer(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
	}
	
	private void onPress(DyeColor color) {
		colorConsumer.accept(color);
		Minecraft.getInstance().setScreen(parent);
	}
	
	protected void drawGuiBackgroundLayer(PoseStack matrixStack) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, GUI_TEXTURE);
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
