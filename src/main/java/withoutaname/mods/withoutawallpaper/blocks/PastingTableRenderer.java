package withoutaname.mods.withoutawallpaper.blocks;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.DyeColor;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import withoutaname.mods.withoutawallpaper.WithoutAWallpaper;
import withoutaname.mods.withoutawallpaper.setup.Registration;
import withoutaname.mods.withoutawallpaper.tools.WallpaperDesign;
import withoutaname.mods.withoutawallpaper.tools.WallpaperType;

public class PastingTableRenderer extends TileEntityRenderer<PastingTableTile> {
	
	public static final ResourceLocation DYES_TEXTURE = new ResourceLocation(WithoutAWallpaper.MODID, "block/pasting_table/dyes");
	
	public PastingTableRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
	}
	
	public static void register() {
		ClientRegistry.bindTileEntityRenderer(Registration.PASTING_TABLE_TILE.get(), PastingTableRenderer::new);
	}
	
	private void add(IVertexBuilder builder, MatrixStack stack, float x, float y, float z, float u, float v) {
		add(builder, stack, x, y, z, u, v, new float[]{1.0f, 1.0f, 1.0f, 1.0f});
	}
	
	private void add(IVertexBuilder builder, MatrixStack stack, float x, float y, float z, float u, float v, float[] color) {
		builder.vertex(stack.last().pose(), x, y, z)
				.color(color[0], color[1], color[2], color.length > 3 ? color[3] : 1.0f)
				.uv(u, v)
				.uv2(0, 240)
				.normal(0, 1, 0)
				.endVertex();
	}
	
	@Override
	public void render(PastingTableTile tileEntityIn, float partialTicks, MatrixStack matrixStackIn, @Nonnull IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		matrixStackIn.pushPose();
		Quaternion rotation;
		switch (tileEntityIn.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING)) {
			default:
			case NORTH:
				rotation = Vector3f.YP.rotationDegrees(0);
				break;
			case WEST:
				rotation = Vector3f.YP.rotationDegrees(90);
				break;
			case SOUTH:
				rotation = Vector3f.YP.rotationDegrees(180);
				break;
			case EAST:
				rotation = Vector3f.YP.rotationDegrees(270);
				break;
		}
		matrixStackIn.translate(.5, .5, .5);
		matrixStackIn.mulPose(rotation);
		matrixStackIn.translate(-.5, -.5, -.5);
		
		TextureAtlasSprite dyeSprite = Minecraft.getInstance().getTextureAtlas(AtlasTexture.LOCATION_BLOCKS).apply(DYES_TEXTURE);
		IVertexBuilder builder = bufferIn.getBuffer(RenderType.solid());
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
