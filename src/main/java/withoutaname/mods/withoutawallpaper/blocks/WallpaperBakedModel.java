package withoutaname.mods.withoutawallpaper.blocks;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.data.IModelData;
import withoutaname.mods.withoutalib.blocks.BaseBakedModel;
import withoutaname.mods.withoutawallpaper.WithoutAWallpaper;
import withoutaname.mods.withoutawallpaper.tools.WallpaperType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

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
		
		HashMap<Direction, WallpaperType> designs = extraData.getData(WallpaperEntity.DESIGNS);
		
		if (designs != null) {
			final float thickness = WallpaperBlock.THICKNESS / 16;
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
		return Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(PARTICLE_TEXTURE);
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
