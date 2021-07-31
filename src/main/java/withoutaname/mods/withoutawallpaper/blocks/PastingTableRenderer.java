package withoutaname.mods.withoutawallpaper.blocks;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import withoutaname.mods.withoutalib.blocks.BaseRenderer;
import withoutaname.mods.withoutawallpaper.WithoutAWallpaper;
import withoutaname.mods.withoutawallpaper.setup.Registration;
import withoutaname.mods.withoutawallpaper.tools.WallpaperDesign;
import withoutaname.mods.withoutawallpaper.tools.WallpaperType;

import javax.annotation.Nonnull;

public class PastingTableRenderer extends BaseRenderer<PastingTableEntity> {
	
	public static final ResourceLocation DYES_TEXTURE = new ResourceLocation(WithoutAWallpaper.MODID, "block/pasting_table/dyes");
	
	public PastingTableRenderer(BlockEntityRendererProvider.Context context) {}
	
	public static void register() {
		BlockEntityRenderers.register(Registration.PASTING_TABLE_TILE.get(), PastingTableRenderer::new);
	}
	
	@Override
	public void render(@Nonnull PastingTableEntity tileEntityIn, float partialTicks, @Nonnull PoseStack matrixStackIn, @Nonnull MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
		matrixStackIn.pushPose();
		Quaternion rotation = switch (tileEntityIn.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING)) {
			default -> Vector3f.YP.rotationDegrees(0);
			case WEST -> Vector3f.YP.rotationDegrees(90);
			case SOUTH -> Vector3f.YP.rotationDegrees(180);
			case EAST -> Vector3f.YP.rotationDegrees(270);
		};
		matrixStackIn.translate(.5, .5, .5);
		matrixStackIn.mulPose(rotation);
		matrixStackIn.translate(-.5, -.5, -.5);
		
		TextureAtlasSprite dyeSprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(DYES_TEXTURE);
		VertexConsumer builder = bufferIn.getBuffer(RenderType.solid());
		DyeColor[] colors = tileEntityIn.getColors();
		if (colors[0] != null) {
			add(builder, matrixStackIn, 0.09375f, 0.84275f, 0.109375f, dyeSprite.getU0(), dyeSprite.getV0(), colors[0].getTextureDiffuseColors());
			add(builder, matrixStackIn, 0.09375f, 0.84275f, 0.265625f, dyeSprite.getU0(), dyeSprite.getV1(), colors[0].getTextureDiffuseColors());
			add(builder, matrixStackIn, 0.25f, 0.84275f, 0.265625f, dyeSprite.getU1(), dyeSprite.getV1(), colors[0].getTextureDiffuseColors());
			add(builder, matrixStackIn, 0.25f, 0.84275f, 0.109375f, dyeSprite.getU1(), dyeSprite.getV0(), colors[0].getTextureDiffuseColors());
		}
		if (colors[1] != null) {
			add(builder, matrixStackIn, 0.46875f, 0.84275f, 0.03125f, dyeSprite.getU0(), dyeSprite.getV0(), colors[1].getTextureDiffuseColors());
			add(builder, matrixStackIn, 0.46875f, 0.84275f, 0.1875f, dyeSprite.getU0(), dyeSprite.getV1(), colors[1].getTextureDiffuseColors());
			add(builder, matrixStackIn, 0.625f, 0.84275f, 0.1875f, dyeSprite.getU1(), dyeSprite.getV1(), colors[1].getTextureDiffuseColors());
			add(builder, matrixStackIn, 0.625f, 0.84275f, 0.03125f, dyeSprite.getU1(), dyeSprite.getV0(), colors[1].getTextureDiffuseColors());
		}
		if (colors[2] != null) {
			add(builder, matrixStackIn, 0.78125f, 0.84275f, 0.265625f, dyeSprite.getU0(), dyeSprite.getV0(), colors[2].getTextureDiffuseColors());
			add(builder, matrixStackIn, 0.78125f, 0.84275f, 0.421875f, dyeSprite.getU0(), dyeSprite.getV1(), colors[2].getTextureDiffuseColors());
			add(builder, matrixStackIn, 0.9375f, 0.84275f, 0.421875f, dyeSprite.getU1(), dyeSprite.getV1(), colors[2].getTextureDiffuseColors());
			add(builder, matrixStackIn, 0.9375f, 0.84275f, 0.265625f, dyeSprite.getU1(), dyeSprite.getV0(), colors[2].getTextureDiffuseColors());
		}
		
		if (tileEntityIn.hasPaper()) {
			WallpaperType wallpaperType = tileEntityIn.getWallpaperType();
			if (wallpaperType.getDesign() == WallpaperDesign.NONE) {
				add(builder, matrixStackIn, 0.0625f, 0.69375f, 0.3125f, dyeSprite.getU0(), dyeSprite.getV0());
				add(builder, matrixStackIn, 0.0625f, 0.69375f, 0.9375f, dyeSprite.getU0(), dyeSprite.getV1());
				add(builder, matrixStackIn, 0.6875f, 0.69375f, 0.9375f, dyeSprite.getU1(), dyeSprite.getV1());
				add(builder, matrixStackIn, 0.6875f, 0.69375f, 0.3125f, dyeSprite.getU1(), dyeSprite.getV0());
			} else {
				builder = bufferIn.getBuffer(RenderType.translucent());
				for (TextureAtlasSprite sprite : wallpaperType.getAtlasSprites()) {
					add(builder, matrixStackIn, 0.0625f, 0.69375f, 0.3125f, sprite.getU0(), sprite.getV0());
					add(builder, matrixStackIn, 0.0625f, 0.69375f, 0.9375f, sprite.getU0(), sprite.getV1());
					add(builder, matrixStackIn, 0.6875f, 0.69375f, 0.9375f, sprite.getU1(), sprite.getV1());
					add(builder, matrixStackIn, 0.6875f, 0.69375f, 0.3125f, sprite.getU1(), sprite.getV0());
				}
			}
		}
		
		matrixStackIn.popPose();
	}
	
}
