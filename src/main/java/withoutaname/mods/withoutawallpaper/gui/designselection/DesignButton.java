package withoutaname.mods.withoutawallpaper.gui.designselection;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import withoutaname.mods.withoutawallpaper.WithoutAWallpaper;
import withoutaname.mods.withoutawallpaper.tools.WallpaperDesign;

public class DesignButton extends Button {
	
	private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(WithoutAWallpaper.MODID, "textures/gui/container/pasting_table.png");
	@Nonnull
	private WallpaperDesign design;
	@Nonnull
	private final IDesignSelectable designSelectable;
	
	public DesignButton(int x, int y, @Nonnull IDesignSelectable designSelectable) {
		this(x, y, designSelectable, WallpaperDesign.NONE);
	}
	
	public DesignButton(int x, int y, @Nonnull IDesignSelectable designSelectable, @Nonnull WallpaperDesign design) {
		super(x, y, 21, 21, TextComponent.EMPTY, (button) -> {});
		this.design = design;
		this.designSelectable = designSelectable;
	}
	
	@Override
	public void onPress() {
		designSelectable.setDesign(design);
	}
	
	@Override
	public void render(@Nonnull PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		if (design != WallpaperDesign.NONE) {
			RenderSystem.setShader(GameRenderer::getPositionTexShader);
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			RenderSystem.setShaderTexture(0, GUI_TEXTURE);
			if (design == designSelectable.getDesign()) {
				blit(matrixStack, x, y, 0, 198, 21, 21);
			} else if (mouseX >= x && mouseX < x + 21 && mouseY >= y && mouseY < y + 21) {
				blit(matrixStack, x, y, 0, 219, 21, 21);
			} else {
				blit(matrixStack, x, y, 0, 177, 21, 21);
			}
			RenderSystem.setShaderTexture(0, new ResourceLocation(WithoutAWallpaper.MODID, "textures/block/wallpaper/" + design + "/design.png"));
			blit(matrixStack, x + 2, y + 2, 17, 17, 0, 0, 16, 16, 16, 16);
		}
	}
	
	public void setDesign(@Nonnull WallpaperDesign design) {
		this.design = design;
	}
	
}
