package withoutaname.mods.withoutawallpaper.setup;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.resources.FolderPack;
import net.minecraft.resources.IPackNameDecorator;
import net.minecraft.resources.ResourcePackInfo;
import net.minecraft.resources.ResourcePackList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import withoutaname.mods.withoutawallpaper.WithoutAWallpaper;
import withoutaname.mods.withoutawallpaper.blocks.PastingTableRenderer;
import withoutaname.mods.withoutawallpaper.blocks.PastingTableScreen;
import withoutaname.mods.withoutawallpaper.blocks.WallpaperModelLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static withoutaname.mods.withoutawallpaper.blocks.PastingTableRenderer.DYES_TEXTURE;
import static withoutaname.mods.withoutawallpaper.blocks.WallpaperBakedModel.PARTICLE_TEXTURE;

@Mod.EventBusSubscriber(modid = WithoutAWallpaper.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {

	public static final Logger LOGGER = LogManager.getLogger();

	public static void init(final FMLClientSetupEvent event) {
		ScreenManager.registerFactory(Registration.PASTING_TABLE_CONTAINER.get(), PastingTableScreen::new);
		PastingTableRenderer.register();

		event.enqueueWork(() -> RenderTypeLookup.setRenderLayer(Registration.WALLPAPER_BLOCK.get(), RenderType.getTranslucent()));
	}

	@SubscribeEvent
	public static void onModelRegistryEvent(ModelRegistryEvent event) {
		ModelLoaderRegistry.registerLoader(new ResourceLocation(WithoutAWallpaper.MODID, "wallpaper_loader"), new WallpaperModelLoader());
	}

	@SubscribeEvent
	public static void onTextureStitch(TextureStitchEvent.Pre event) {
		if (event.getMap().getTextureLocation().equals(AtlasTexture.LOCATION_BLOCKS_TEXTURE)) {
			event.addSprite(DYES_TEXTURE);
			event.addSprite(PARTICLE_TEXTURE);
		}

	}

	public static void registerResourcePack() {
		Path packPath = Minecraft.getInstance().gameDir.toPath().resolve("config").resolve("withoutawallpaper").resolve("CustomWallpaperResources");
		try {
			createEmptyPack(packPath);
			ResourcePackList resourcePackList = Minecraft.getInstance().getResourcePackList();
			resourcePackList.addPackFinder((infoConsumer, infoFactory) ->
			{
				ResourcePackInfo packInfo = ResourcePackInfo.createResourcePack("CustomWallpaperResources", true,
						() -> new FolderPack(packPath.toFile()) {
							@Override
							public boolean isHidden() {
								return true;
							}
						}, infoFactory, ResourcePackInfo.Priority.TOP, IPackNameDecorator.PLAIN);
				if (packInfo != null) {
					infoConsumer.accept(packInfo);
				} else {
					LOGGER.error("Couldn't register resource pack CustomWallpaperResources");
				}
			});

		} catch (IOException e) {
			LOGGER.error("Couldn't create resource pack " + packPath.toString(), e);
		}
	}

	private static void createEmptyPack(Path packPath) throws IOException {
		Path packAssetsPath = packPath.resolve("assets").resolve("withoutawallpaper").resolve("textures").resolve("block").resolve("wallpaper");
		if (!Files.exists(packAssetsPath)) {
			Files.createDirectories(packAssetsPath);
			LOGGER.debug("Created custom wallpaper resource folder: " + packPath.toString());
		}
		Path packInfoPath = packPath.resolve("pack.mcmeta");
		if (!Files.exists(packInfoPath)) {
			Files.createFile(packInfoPath);
			List<String> mcMetaFile = new ArrayList<>();
			mcMetaFile.add("{");
			mcMetaFile.add("    \"pack\": {");
			mcMetaFile.add("        \"description\": \"withoutawallpaper custom wallpaper resources\",");
			mcMetaFile.add("        \"pack_format\": 6");
			mcMetaFile.add("    }");
			mcMetaFile.add("}");
			Files.write(packInfoPath, mcMetaFile);
			LOGGER.debug("Created pack.mcmeta for custom wallpaper resource pack: " + packInfoPath.toString());
		}
	}

}