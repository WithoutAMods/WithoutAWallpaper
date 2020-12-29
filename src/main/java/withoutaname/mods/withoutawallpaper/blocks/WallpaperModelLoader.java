package withoutaname.mods.withoutawallpaper.blocks;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.resources.IResourceManager;
import net.minecraftforge.client.model.IModelLoader;

public class WallpaperModelLoader implements IModelLoader<WallpaperModelGeometry> {

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {
	}

	@Override
	public WallpaperModelGeometry read(JsonDeserializationContext deserializationContext, JsonObject modelContents) {
		return new WallpaperModelGeometry();
	}

}
