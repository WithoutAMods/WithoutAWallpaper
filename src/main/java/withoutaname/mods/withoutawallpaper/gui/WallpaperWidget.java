package withoutaname.mods.withoutawallpaper.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import withoutaname.mods.withoutawallpaper.WithoutAWallpaper;
import withoutaname.mods.withoutawallpaper.tools.WallpaperType;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class WallpaperWidget extends AbstractWidget {
	
	private final Supplier<WallpaperType> wallpaperTypeSupplier;
	
	public WallpaperWidget(int x, int y, int size, Supplier<WallpaperType> wallpaperTypeSupplier) {
		super(x, y, size, size, TextComponent.EMPTY);
		this.wallpaperTypeSupplier = wallpaperTypeSupplier;
	}
	
	@Override
	public void render(@Nonnull PoseStack matrixStack, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
		if (visible) {
			WallpaperType wallpaperType = wallpaperTypeSupplier.get();
			if (wallpaperType != null) {
				RenderSystem.setShader(GameRenderer::getPositionTexShader);
				RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
				if (wallpaperType == WallpaperType.NONE) {
					RenderSystem.setShaderTexture(0, new ResourceLocation(WithoutAWallpaper.MODID, "textures/block/pasting_table/dyes.png"));
					blit(matrixStack, x, y, width, height, 0, 0, 16, 16, 16, 16);
				} else {
					for (ResourceLocation resourceLocation : wallpaperType.getResourceLocations()) {
						RenderSystem.setShaderTexture(0, new ResourceLocation(resourceLocation.getNamespace(), "textures/" + resourceLocation.getPath() + ".png"));
						blit(matrixStack, x, y, width, height, 0, 0, 16, 16, 16, 16);
					}
				}
			}
		}
	}
	
	@Override
	public void updateNarration(@Nonnull NarrationElementOutput pNarrationElementOutput) {
	
	}
}
