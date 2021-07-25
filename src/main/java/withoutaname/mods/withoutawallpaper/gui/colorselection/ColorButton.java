package withoutaname.mods.withoutawallpaper.gui.colorselection;

import java.util.function.Supplier;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import withoutaname.mods.withoutawallpaper.WithoutAWallpaper;

public class ColorButton extends Button {
	
	private static final ResourceLocation BLANK_TEXTURE = new ResourceLocation(WithoutAWallpaper.MODID, "textures/block/pasting_table/dyes.png");
	private final Supplier<DyeColor> colorSupplier;
	
	public ColorButton(int x, int y, Supplier<DyeColor> colorSupplier, OnPress onPress, @Nullable Screen screen) {
		super(x, y, 20, 20, TextComponent.EMPTY, onPress,
				screen != null ?
						(button, matrixStack, x1, y1) -> {
							if (colorSupplier.get() != null)
								screen.renderTooltip(matrixStack, new TranslatableComponent("color.minecraft." + colorSupplier.get().getName()), x1, y1);
						} : Button.NO_TOOLTIP);
		this.colorSupplier = colorSupplier;
	}
	
	@Override
	public void renderButton(@Nonnull PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		super.renderButton(matrixStack, mouseX, mouseY, partialTicks);
		DyeColor color = colorSupplier.get();
		if (color != null) {
			RenderSystem.setShader(GameRenderer::getPositionTexShader);
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			RenderSystem.setShaderTexture(0, BLANK_TEXTURE);
			float[] textureDiffuseColors = color.getTextureDiffuseColors();
			RenderSystem.setShaderColor(textureDiffuseColors[0], textureDiffuseColors[1], textureDiffuseColors[2], 1.0F);
			blit(matrixStack, x + 4, y + 4, 12, 12, 0, 0, 16, 16, 16, 16);
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		}
	}
	
}
