package withoutaname.mods.withoutawallpaper.blocks;

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
import org.jetbrains.annotations.NotNull;
import withoutaname.mods.withoutawallpaper.WithoutAWallpaper;
import withoutaname.mods.withoutawallpaper.setup.Registration;
import withoutaname.mods.withoutawallpaper.tools.WallpaperDesign;
import withoutaname.mods.withoutawallpaper.tools.WallpaperType;

public class PastingTableRenderer extends TileEntityRenderer<PastingTableTile> {

	public static final ResourceLocation DYES_TEXTURE = new ResourceLocation(WithoutAWallpaper.MODID, "block/rolling_station/dyes");

	public PastingTableRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
		super(rendererDispatcherIn);
	}

	private void add(IVertexBuilder builder, MatrixStack stack, float x, float y, float z, float u, float v) {
		add(builder, stack, x, y, z, u, v, new float[] {1.0f, 1.0f, 1.0f, 1.0f});
	}

	private void add(IVertexBuilder builder, MatrixStack stack, float x, float y, float z, float u, float v, float[] color) {
		builder.pos(stack.getLast().getMatrix(), x, y, z)
				.color(color[0], color[1], color[2], color.length > 3 ? color[3] : 1.0f)
				.tex(u, v)
				.lightmap(0, 240)
				.normal(0, 1, 0)
				.endVertex();
	}

	@Override
	public void render(PastingTableTile tileEntityIn, float partialTicks, MatrixStack matrixStackIn, @NotNull IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
		matrixStackIn.push();
		Quaternion rotation;
				switch (tileEntityIn.getBlockState().get(BlockStateProperties.HORIZONTAL_FACING)) {
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
		matrixStackIn.rotate(rotation);
		matrixStackIn.translate(-.5, -.5, -.5);

		TextureAtlasSprite dyeSprite = Minecraft.getInstance().getAtlasSpriteGetter(AtlasTexture.LOCATION_BLOCKS_TEXTURE).apply(DYES_TEXTURE);
		IVertexBuilder builder = bufferIn.getBuffer(RenderType.getSolid());
		DyeColor[] colors = tileEntityIn.getColors();
		if (colors[0] != null) {
			add(builder, matrixStackIn, 0.09375f, 0.84275f, 0.109375f, dyeSprite.getMinU(), dyeSprite.getMinV(), colors[0].getColorComponentValues());
			add(builder, matrixStackIn, 0.09375f, 0.84275f, 0.265625f, dyeSprite.getMinU(), dyeSprite.getMaxV(), colors[0].getColorComponentValues());
			add(builder, matrixStackIn, 0.25f, 0.84275f, 0.265625f, dyeSprite.getMaxU(), dyeSprite.getMaxV(), colors[0].getColorComponentValues());
			add(builder, matrixStackIn, 0.25f, 0.84275f, 0.109375f, dyeSprite.getMaxU(), dyeSprite.getMinV(), colors[0].getColorComponentValues());
		}
		if (colors[1] != null) {
			add(builder, matrixStackIn, 0.46875f, 0.84275f, 0.03125f, dyeSprite.getMinU(), dyeSprite.getMinV(), colors[1].getColorComponentValues());
			add(builder, matrixStackIn, 0.46875f, 0.84275f, 0.1875f, dyeSprite.getMinU(), dyeSprite.getMaxV(), colors[1].getColorComponentValues());
			add(builder, matrixStackIn, 0.625f, 0.84275f, 0.1875f, dyeSprite.getMaxU(), dyeSprite.getMaxV(), colors[1].getColorComponentValues());
			add(builder, matrixStackIn, 0.625f, 0.84275f, 0.03125f, dyeSprite.getMaxU(), dyeSprite.getMinV(), colors[1].getColorComponentValues());
		}
		if (colors[2] != null) {
			add(builder, matrixStackIn, 0.78125f, 0.84275f, 0.265625f, dyeSprite.getMinU(), dyeSprite.getMinV(), colors[2].getColorComponentValues());
			add(builder, matrixStackIn, 0.78125f, 0.84275f, 0.421875f, dyeSprite.getMinU(), dyeSprite.getMaxV(), colors[2].getColorComponentValues());
			add(builder, matrixStackIn, 0.9375f, 0.84275f, 0.421875f, dyeSprite.getMaxU(), dyeSprite.getMaxV(), colors[2].getColorComponentValues());
			add(builder, matrixStackIn, 0.9375f, 0.84275f, 0.265625f, dyeSprite.getMaxU(), dyeSprite.getMinV(), colors[2].getColorComponentValues());
		}

		if (tileEntityIn.hasPaper()){
			WallpaperType wallpaperType = tileEntityIn.getWallpaperType();
			if (wallpaperType.getDesign() == WallpaperDesign.NONE) {
				add(builder, matrixStackIn, 0.0625f, 0.69375f, 0.3125f, dyeSprite.getMinU(), dyeSprite.getMinV());
				add(builder, matrixStackIn, 0.0625f, 0.69375f, 0.9375f, dyeSprite.getMinU(), dyeSprite.getMaxV());
				add(builder, matrixStackIn, 0.6875f, 0.69375f, 0.9375f, dyeSprite.getMaxU(), dyeSprite.getMaxV());
				add(builder, matrixStackIn, 0.6875f, 0.69375f, 0.3125f, dyeSprite.getMaxU(), dyeSprite.getMinV());
			} else {
				builder = bufferIn.getBuffer(RenderType.getTranslucent());
				for (TextureAtlasSprite sprite : wallpaperType.getAtlasSprites()) {
					add(builder, matrixStackIn, 0.0625f, 0.69375f, 0.3125f, sprite.getMinU(), sprite.getMinV());
					add(builder, matrixStackIn, 0.0625f, 0.69375f, 0.9375f, sprite.getMinU(), sprite.getMaxV());
					add(builder, matrixStackIn, 0.6875f, 0.69375f, 0.9375f, sprite.getMaxU(), sprite.getMaxV());
					add(builder, matrixStackIn, 0.6875f, 0.69375f, 0.3125f, sprite.getMaxU(), sprite.getMinV());
				}
			}
		}

		matrixStackIn.pop();
	}

	public static void register() {
		ClientRegistry.bindTileEntityRenderer(Registration.PASTING_TABLE_TILE.get(), PastingTableRenderer::new);
	}

}
