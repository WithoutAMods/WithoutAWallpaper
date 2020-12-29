package withoutaname.mods.withoutawallpaper.blocks;

import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.geometry.IModelGeometry;
import withoutaname.mods.withoutawallpaper.tools.WallpaperDesign;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.function.Function;

public class WallpaperModelGeometry implements IModelGeometry<WallpaperModelGeometry> {



	@Override
	public IBakedModel bake(IModelConfiguration owner, ModelBakery bakery, Function spriteGetter, IModelTransform modelTransform, ItemOverrideList overrides, ResourceLocation modelLocation) {
		return new WallpaperBakedModel();
	}

	@Override
	public Collection<RenderMaterial> getTextures(IModelConfiguration owner, Function modelGetter, Set missingTextureErrors) {
		Collection<RenderMaterial> renderMaterials = new ArrayList<>();
		for (ResourceLocation resourceLocation : WallpaperDesign.getAllTextures()) {
			renderMaterials.add(new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, resourceLocation));
		}
		return renderMaterials;
	}

}
