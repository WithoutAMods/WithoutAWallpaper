package withoutaname.mods.withoutawallpaper.gui;

import java.util.function.Supplier;
import javax.annotation.Nonnull;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

import withoutaname.mods.withoutawallpaper.WithoutAWallpaper;
import withoutaname.mods.withoutawallpaper.tools.WallpaperType;

public class WallpaperWidget extends Widget {
	
	private final Supplier<WallpaperType> wallpaperTypeSupplier;
	
	public WallpaperWidget(int x, int y, int size, Supplier<WallpaperType> wallpaperTypeSupplier) {
		super(x, y, size, size, StringTextComponent.EMPTY);
		this.wallpaperTypeSupplier = wallpaperTypeSupplier;
	}
	
	@Override
	public void render(@Nonnull MatrixStack matrixStack, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
		if (visible) {
			WallpaperType wallpaperType = wallpaperTypeSupplier.get();
			if (wallpaperType != null) {
				Minecraft minecraft = Minecraft.getInstance();
				if (wallpaperType == WallpaperType.NONE) {
					minecraft.getTextureManager().bind(new ResourceLocation(WithoutAWallpaper.MODID, "textures/block/pasting_table/dyes.png"));
					blit(matrixStack, x, y, width, height, 0, 0, 16, 16, 16, 16);
				} else {
					for (ResourceLocation resourceLocation : wallpaperType.getResourceLocations()) {
						minecraft.getTextureManager().bind(new ResourceLocation(resourceLocation.getNamespace(), "textures/" + resourceLocation.getPath() + ".png"));
						blit(matrixStack, x, y, width, height, 0, 0, 16, 16, 16, 16);
					}
				}
			}
		}
	}
	
}
