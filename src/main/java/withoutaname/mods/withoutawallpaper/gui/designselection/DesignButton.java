package withoutaname.mods.withoutawallpaper.gui.designselection;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

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
		super(x, y, 21, 21, StringTextComponent.EMPTY, (button) -> {});
		this.design = design;
		this.designSelectable = designSelectable;
	}
	
	@Override
	public void onPress() {
		designSelectable.setDesign(design);
	}
	
	@Override
	public void render(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		if (design != WallpaperDesign.NONE) {
			Minecraft minecraft = Minecraft.getInstance();
			minecraft.getTextureManager().bind(GUI_TEXTURE);
			if (design == designSelectable.getDesign()) {
				blit(matrixStack, x, y, 0, 198, 21, 21);
			} else if (mouseX >= x && mouseX < x + 21 && mouseY >= y && mouseY < y + 21) {
				blit(matrixStack, x, y, 0, 219, 21, 21);
			} else {
				blit(matrixStack, x, y, 0, 177, 21, 21);
			}
			minecraft.getTextureManager().bind(new ResourceLocation(WithoutAWallpaper.MODID, "textures/block/wallpaper/" + design + "/design.png"));
			blit(matrixStack, x + 2, y + 2, 17, 17, 0, 0, 16, 16, 16, 16);
		}
	}
	
	public void setDesign(@Nonnull WallpaperDesign design) {
		this.design = design;
	}
	
}
