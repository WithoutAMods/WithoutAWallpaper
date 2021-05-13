package withoutaname.mods.withoutawallpaper.gui;

import java.util.Arrays;
import javax.annotation.Nonnull;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.DyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

import withoutaname.mods.withoutawallpaper.WithoutAWallpaper;
import withoutaname.mods.withoutawallpaper.gui.designselection.DesignSelectionWidget;
import withoutaname.mods.withoutawallpaper.gui.designselection.IDesignSelectable;
import withoutaname.mods.withoutawallpaper.tools.Colors;
import withoutaname.mods.withoutawallpaper.tools.WallpaperDesign;
import withoutaname.mods.withoutawallpaper.tools.WallpaperType;

public class WallpaperCatalogGUI extends Screen implements IDesignSelectable {
	
	protected final ResourceLocation GUI_TEXTURE = new ResourceLocation("textures/gui/demo_background.png");
	
	private final int xSize = 248;
	private final int ySize = 166;
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
		addButton(new WallpaperWidget(guiLeft + 10, guiTop + 50, 40, () -> type));
		addButton(new DesignSelectionWidget(guiLeft + 60, guiTop + 10, 2, 3, this::addButton, this));
		
	}
	
	@Override
	public void render(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
		this.drawGuiContainerBackgroundLayer(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
	}
	
	protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack) {
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
		for (int i = 0; i < availableColors.length; i++) {
			if (!availableColors[i].getColors().contains(colors[i])) {
				colors[i] = availableColors[i].getColors().get(0);
			}
		}
		type = new WallpaperType(design, Arrays.copyOf(colors, design.getColorCount()));
	}
	
	public static void open() {
		Minecraft.getInstance().setScreen(new WallpaperCatalogGUI());
	}
	
}
