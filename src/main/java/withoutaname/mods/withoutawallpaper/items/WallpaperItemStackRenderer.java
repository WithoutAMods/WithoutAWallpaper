package withoutaname.mods.withoutawallpaper.items;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import org.jetbrains.annotations.NotNull;
import withoutaname.mods.withoutawallpaper.tools.WallpaperType;

public class WallpaperItemStackRenderer extends ItemStackTileEntityRenderer {

	private void add(IVertexBuilder builder, MatrixStack stack, float x, float y, float z, float u, float v) {
		builder.pos(stack.getLast().getMatrix(), x, y, z)
				.color(1.0f, 1.0f, 1.0f, 1.0f)
				.tex(u, v)
				.lightmap(0, 240)
				.normal(1, 0, 0)
				.endVertex();
	}

	@Override
	public void func_239207_a_(ItemStack stack, @NotNull ItemCameraTransforms.TransformType p_239207_2_, @NotNull MatrixStack matrixStack, @NotNull IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
		WallpaperType wallpaperType = WallpaperType.NONE;
		if (stack.hasTag()) {
			CompoundNBT tag = stack.getTag();
			if (tag != null && tag.contains("wallpaperType")) {
				wallpaperType = WallpaperType.fromNBT(tag.getCompound("wallpaperType"));
			}
		}

		IVertexBuilder builder = buffer.getBuffer(RenderType.getTranslucent());

		matrixStack.push();

		if (p_239207_2_ != ItemCameraTransforms.TransformType.GUI) {
			matrixStack.translate(.5, .5, .5);
			matrixStack.scale(.5f, .5f, .5f);
			matrixStack.translate(-.5, -.5, -.5);
		}

		float z = p_239207_2_ == ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND || p_239207_2_ == ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND ? .75f : .5f;

		for (TextureAtlasSprite sprite : wallpaperType.getAtlasSprites()) {
			add(builder, matrixStack, 0, 0, z, sprite.getMinU(), sprite.getMinV());
			add(builder, matrixStack, 1, 0, z, sprite.getMaxU(), sprite.getMinV());
			add(builder, matrixStack, 1, 1, z, sprite.getMaxU(), sprite.getMaxV());
			add(builder, matrixStack, 0, 1, z, sprite.getMinU(), sprite.getMaxV());

			if (!(p_239207_2_.isFirstPerson() && p_239207_2_ == ItemCameraTransforms.TransformType.GUI)) {
				add(builder, matrixStack, 0, 1, z, sprite.getMinU(), sprite.getMaxV());
				add(builder, matrixStack, 1, 1, z, sprite.getMaxU(), sprite.getMaxV());
				add(builder, matrixStack, 1, 0, z, sprite.getMaxU(), sprite.getMinV());
				add(builder, matrixStack, 0, 0, z, sprite.getMinU(), sprite.getMinV());
			}
		}

		matrixStack.pop();
	}
}
