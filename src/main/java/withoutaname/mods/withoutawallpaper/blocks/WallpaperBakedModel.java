package withoutaname.mods.withoutawallpaper.blocks;

import java.util.*;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.data.IModelData;

import withoutaname.mods.withoutalib.blocks.BaseBakedModel;
import withoutaname.mods.withoutawallpaper.WithoutAWallpaper;
import withoutaname.mods.withoutawallpaper.tools.WallpaperType;

public class WallpaperBakedModel extends BaseBakedModel {
	
	public static final ResourceLocation PARTICLE_TEXTURE = new ResourceLocation(WithoutAWallpaper.MODID, "block/wallpaper/particles");
	
	@Nonnull
	@Override
	public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData) {
		RenderType layer = MinecraftForgeClient.getRenderLayer();
		
		if (side != null || (layer != null && !layer.equals(RenderType.translucent()))) {
			return Collections.emptyList();
		}
		
		List<BakedQuad> quads = new ArrayList<>();
		
		HashMap<Direction, WallpaperType> designs = extraData.getData(WallpaperTile.DESIGNS);
		
		if (designs != null) {
			final double thickness = WallpaperBlock.THICKNESS / 16;
			designs.forEach((direction, wallpaperType) -> {
				for (TextureAtlasSprite textureAtlasSprite : wallpaperType.getAtlasSprites()) {
					switch (direction) {
						case UP:
							quads.addAll(createCube(v(0, 1 - thickness, 0), v(1, 1, 1), textureAtlasSprite, true));
							break;
						case DOWN:
							quads.addAll(createCube(v(0, 0, 0), v(1, thickness, 1), textureAtlasSprite, true));
							break;
						case NORTH:
							quads.addAll(createCube(v(0, 0, 0), v(1, 1, thickness), textureAtlasSprite, true));
							break;
						case SOUTH:
							quads.addAll(createCube(v(0, 0, 1 - thickness), v(1, 1, 1), textureAtlasSprite, true));
							break;
						case EAST:
							quads.addAll(createCube(v(1 - thickness, 0, 0), v(1, 1, 1), textureAtlasSprite, true));
							break;
						case WEST:
							quads.addAll(createCube(v(0, 0, 0), v(thickness, 1, 1), textureAtlasSprite, true));
							break;
					}
				}
			});
		}
		
		return quads;
	}
	
	@Nonnull
	@Override
	public TextureAtlasSprite getParticleIcon() {
		return Minecraft.getInstance().getTextureAtlas(AtlasTexture.LOCATION_BLOCKS).apply(PARTICLE_TEXTURE);
	}
	
	@Override
	public boolean useAmbientOcclusion() {
		return false;
	}
	
	@Override
	public boolean isCustomRenderer() {
		return true;
	}
	
}
