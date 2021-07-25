package withoutaname.mods.withoutawallpaper.blocks;

import javax.annotation.Nonnull;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.client.model.IModelLoader;

public class WallpaperModelLoader implements IModelLoader<WallpaperModelGeometry> {
	
	@Override
	public void onResourceManagerReload(@Nonnull ResourceManager pResourceManager) {}
	
	@Nonnull
	@Override
	public WallpaperModelGeometry read(@Nonnull JsonDeserializationContext deserializationContext, @Nonnull JsonObject modelContents) {
		return new WallpaperModelGeometry();
	}
}
