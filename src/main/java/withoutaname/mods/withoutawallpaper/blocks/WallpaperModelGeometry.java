package withoutaname.mods.withoutawallpaper.blocks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.function.Function;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.geometry.IModelGeometry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WallpaperModelGeometry implements IModelGeometry<WallpaperModelGeometry> {
	
	public static final Logger LOGGER = LogManager.getLogger();
	
	@Override
	public BakedModel bake(IModelConfiguration owner, ModelBakery bakery, Function spriteGetter, ModelState modelTransform, ItemOverrides overrides, ResourceLocation modelLocation) {
		return new WallpaperBakedModel();
	}
	
	@Override
	public Collection<Material> getTextures(IModelConfiguration owner, Function modelGetter, Set missingTextureErrors) {
		Collection<ResourceLocation> allResourceLocations = Minecraft.getInstance().getResourceManager().listResources("textures/block/wallpaper", s -> s.endsWith(".png"));
		
		Collection<Material> renderMaterials = new ArrayList<>();
		for (ResourceLocation resourceLocation : allResourceLocations) {
			resourceLocation = new ResourceLocation(resourceLocation.getNamespace(), resourceLocation.getPath().substring(9, resourceLocation.getPath().length() - 4));
			renderMaterials.add(new Material(TextureAtlas.LOCATION_BLOCKS, resourceLocation));
		}
		return renderMaterials;
	}
	
}
