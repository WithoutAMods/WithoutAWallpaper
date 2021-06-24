package withoutaname.mods.withoutawallpaper.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nonnull;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.DyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

import withoutaname.mods.withoutawallpaper.WithoutAWallpaper;
import withoutaname.mods.withoutawallpaper.gui.colorselection.ColorButton;
import withoutaname.mods.withoutawallpaper.gui.colorselection.ColorSelectionScreen;
import withoutaname.mods.withoutawallpaper.gui.designselection.DesignSelectionWidget;
import withoutaname.mods.withoutawallpaper.gui.designselection.IDesignSelectable;
import withoutaname.mods.withoutawallpaper.tools.Colors;
import withoutaname.mods.withoutawallpaper.tools.WallpaperDesign;
import withoutaname.mods.withoutawallpaper.tools.WallpaperType;

public class WallpaperCatalogGUI extends Screen implements IDesignSelectable {
	
	protected final ResourceLocation GUI_TEXTURE = new ResourceLocation(WithoutAWallpaper.MODID, "textures/gui/wallpaper_catalog.png");
	
	private final int xSize = 156;
	private final int ySize = 106;
	private int guiLeft;
	private int guiTop;
	
	private final DyeColor[] colors = new DyeColor[3];
	private WallpaperDesign design = WallpaperDesign.NONE;
	private WallpaperType type = WallpaperType.NONE;
	
	public WallpaperCatalogGUI() {
		super(new TranslationTextComponent("screen." + WithoutAWallpaper.MODID + ".wallpaper_catalog"));
	}
	
	@Override
	protected void init() {
		guiLeft = (width - xSize) / 2;
		guiTop = (height - ySize) / 2;
		for (int i = 0; i < 3; i++) {
			int finalI = i;
			addButton(new ColorButton(guiLeft + 10 + i * 24, guiTop + 10, () -> colors[finalI], button -> {
				assert minecraft != null;
				Colors[] availableColors = design.getAvailableColors();
				List<DyeColor> colors = availableColors.length > finalI ? availableColors[finalI].getColors() : new ArrayList<>();
				minecraft.setScreen(new ColorSelectionScreen(WallpaperCatalogGUI.this, colors, color -> {
					this.colors[finalI] = color;
					updateType();
				}));
			}, this));
		}
		addButton(new WallpaperWidget(guiLeft + 16, guiTop + 40, 56, () -> type));
		addButton(new DesignSelectionWidget(guiLeft + 89, guiTop + 11, 2, 4, this::addButton, this));
		
	}
	
	@Override
	public void render(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
		this.drawGuiBackgroundLayer(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
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
	public WallpaperDesign getDesign() {
		return design;
	}
	
	@Override
	public void setDesign(@Nonnull WallpaperDesign design) {
		this.design = design;
		Colors[] availableColors = design.getAvailableColors();
		for (int i = 0; i < 3; i++) {
			if (i < availableColors.length) {
				if (!availableColors[i].getColors().contains(colors[i])) {
					colors[i] = availableColors[i].getColors().get(0);
				}
			} else {
				colors[i] = null;
			}
		}
		updateType();
	}
	
	private void updateType() {
		type = new WallpaperType(design, Arrays.copyOf(colors, design.getColorCount()));
	}
	
	@Override
	public boolean isPauseScreen() {
		return false;
	}
	
	public static void open() {
		Minecraft.getInstance().setScreen(new WallpaperCatalogGUI());
	}
	
}
