package withoutaname.mods.withoutawallpaper.gui.colorselection;

import java.util.function.Supplier;
import javax.annotation.Nonnull;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.item.DyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

import withoutaname.mods.withoutawallpaper.WithoutAWallpaper;

public class ColorButton extends Button {
	
	private static final ResourceLocation BLANK_TEXTURE = new ResourceLocation(WithoutAWallpaper.MODID, "textures/block/pasting_table/dyes.png");
	private final Supplier<DyeColor> colorSupplier;
	
	public ColorButton(int x, int y, Supplier<DyeColor> colorSupplier, IPressable onPress) {
		super(x, y, 20, 20, StringTextComponent.EMPTY, onPress);
		this.colorSupplier = colorSupplier;
	}
	
	@Override
	public void renderButton(@Nonnull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		super.renderButton(matrixStack, mouseX, mouseY, partialTicks);
		DyeColor color = colorSupplier.get();
		if (color != null) {
			Minecraft.getInstance().getTextureManager().bind(BLANK_TEXTURE);
			float[] textureDiffuseColors = color.getTextureDiffuseColors();
			RenderSystem.color3f(textureDiffuseColors[0], textureDiffuseColors[1], textureDiffuseColors[2]);
			blit(matrixStack, x + 4, y + 4, 12, 12, 0, 0, 16, 16, 16, 16);
			RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		}
	}
	
}
