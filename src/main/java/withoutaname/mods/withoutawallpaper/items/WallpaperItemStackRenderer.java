package withoutaname.mods.withoutawallpaper.items;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import withoutaname.mods.withoutawallpaper.tools.WallpaperType;

import javax.annotation.Nonnull;

public class WallpaperItemStackRenderer extends BlockEntityWithoutLevelRenderer {
	
	public WallpaperItemStackRenderer() {
		super(Minecraft.getInstance() == null ? null : Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance() == null ? null : Minecraft.getInstance().getEntityModels());
	}
	
	private void add(@Nonnull VertexConsumer builder, @Nonnull PoseStack stack, float x, float y, float z, float u, float v) {
		builder.vertex(stack.last().pose(), x, y, z)
				.color(1.0f, 1.0f, 1.0f, 1.0f)
				.uv(u, v)
				.uv2(0, 240)
				.normal(1, 0, 0)
				.endVertex();
	}
	
	@Override
	public void renderByItem(@Nonnull ItemStack stack, @Nonnull ItemTransforms.TransformType p_239207_2_, @Nonnull PoseStack matrixStack, @Nonnull MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
		WallpaperType wallpaperType = WallpaperType.NONE;
		if (stack.hasTag()) {
			CompoundTag tag = stack.getTag();
			if (tag != null && tag.contains("wallpaperType")) {
				wallpaperType = WallpaperType.fromNBT(tag.getCompound("wallpaperType"));
			}
		}
		
		VertexConsumer builder = buffer.getBuffer(RenderType.translucent());
		
		matrixStack.pushPose();
		
		matrixStack.translate(.5, .5, .5);
		switch (p_239207_2_) {
			case NONE:
				break;
			case THIRD_PERSON_LEFT_HAND:
			case THIRD_PERSON_RIGHT_HAND:
				matrixStack.translate(0d / 16d, 3d / 16d, 3d / 16d);
				matrixStack.mulPose(new Quaternion(180, 0, 0, true));
				matrixStack.scale(.5f, .5f, .5f);
				break;
			case FIRST_PERSON_LEFT_HAND:
				matrixStack.translate(-1.13d / 16d, 3.2d / 16d, 1.13d / 16d);
				matrixStack.mulPose(new Quaternion(0, -90, 180 + 25, true));
				matrixStack.scale(.5f, .5f, .5f);
				break;
			case FIRST_PERSON_RIGHT_HAND:
				matrixStack.translate(1.13d / 16d, 3.2d / 16d, 1.13d / 16d);
				matrixStack.mulPose(new Quaternion(0, 90, 180 - 25, true));
				matrixStack.scale(.5f, .5f, .5f);
				break;
			case HEAD:
				matrixStack.translate(0d / 16d, 6.4375d / 16d, 0d / 16d);
				matrixStack.mulPose(new Quaternion(90, 0, 180, true));
				matrixStack.scale(.8f, .8f, .8f);
				break;
			case GUI:
				matrixStack.mulPose(new Quaternion(180, 0, 0, true));
				break;
			case GROUND:
				matrixStack.mulPose(new Quaternion(180, 0, 0, true));
				matrixStack.scale(.4f, .4f, .4f);
				break;
			case FIXED:
				matrixStack.translate(0d / 16d, 0d / 16d, 0.75d / 16d);
				matrixStack.mulPose(new Quaternion(0, 0, 180, true));
				break;
		}
		matrixStack.translate(-.5, -.5, -.5);
		
		float z = p_239207_2_ == ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND || p_239207_2_ == ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND ? .75f : .5f;
		
		for (TextureAtlasSprite sprite : wallpaperType.getAtlasSprites()) {
			add(builder, matrixStack, 0, 0, z, sprite.getU0(), sprite.getV0());
			add(builder, matrixStack, 1, 0, z, sprite.getU1(), sprite.getV0());
			add(builder, matrixStack, 1, 1, z, sprite.getU1(), sprite.getV1());
			add(builder, matrixStack, 0, 1, z, sprite.getU0(), sprite.getV1());
			
			if (!(p_239207_2_.firstPerson() && p_239207_2_ == ItemTransforms.TransformType.GUI)) {
				add(builder, matrixStack, 0, 1, z, sprite.getU0(), sprite.getV1());
				add(builder, matrixStack, 1, 1, z, sprite.getU1(), sprite.getV1());
				add(builder, matrixStack, 1, 0, z, sprite.getU1(), sprite.getV0());
				add(builder, matrixStack, 0, 0, z, sprite.getU0(), sprite.getV0());
			}
		}
		
		matrixStack.popPose();
	}
	
}
