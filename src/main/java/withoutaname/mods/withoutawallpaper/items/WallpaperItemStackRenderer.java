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
import net.minecraft.util.math.vector.Quaternion;
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

		matrixStack.translate(.5, .5, .5);
		switch (p_239207_2_) {
			case NONE:
				break;
			case THIRD_PERSON_LEFT_HAND:
			case THIRD_PERSON_RIGHT_HAND:
				matrixStack.translate(0d/16d, 3d/16d, 3d/16d);
				matrixStack.rotate(new Quaternion(180, 0, 0, true));
				matrixStack.scale(.5f, .5f, .5f);
				break;
			case FIRST_PERSON_LEFT_HAND:
				matrixStack.translate(-1.13d/16d, 3.2d/16d, 1.13d/16d);
				matrixStack.rotate(new Quaternion(0, -90, 180+25, true));
				matrixStack.scale(.5f, .5f, .5f);
				break;
			case FIRST_PERSON_RIGHT_HAND:
				matrixStack.translate(1.13d/16d, 3.2d/16d, 1.13d/16d);
				matrixStack.rotate(new Quaternion(0, 90, 180-25, true));
				matrixStack.scale(.5f, .5f, .5f);
				break;
			case HEAD:
				matrixStack.translate(0d/16d, 6.4375d/16d, 0d/16d);
				matrixStack.rotate(new Quaternion(90, 0, 180, true));
				matrixStack.scale(.8f, .8f, .8f);
				break;
			case GUI:
				matrixStack.rotate(new Quaternion(180, 0, 0, true));
				break;
			case GROUND:
				matrixStack.rotate(new Quaternion(180, 0, 0, true));
				matrixStack.scale(.4f, .4f, .4f);
				break;
			case FIXED:
				matrixStack.translate(0d/16d, 0d/16d, 0.75d/16d);
				matrixStack.rotate(new Quaternion(0, 0, 180, true));
				break;
		}
		matrixStack.translate(-.5, -.5, -.5);

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
