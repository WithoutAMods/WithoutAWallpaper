package withoutaname.mods.withoutawallpaper.setup;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

import com.mojang.blaze3d.platform.ScreenManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.FolderPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.PackSource;
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

import static withoutaname.mods.withoutawallpaper.blocks.PastingTableRenderer.DYES_TEXTURE;
import static withoutaname.mods.withoutawallpaper.blocks.WallpaperBakedModel.PARTICLE_TEXTURE;

@Mod.EventBusSubscriber(modid = WithoutAWallpaper.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {
	
	public static final Logger LOGGER = LogManager.getLogger();
	
	public static void init(@Nonnull final FMLClientSetupEvent event) {
		MenuScreens.register(Registration.PASTING_TABLE_CONTAINER.get(), PastingTableScreen::new);
		PastingTableRenderer.register();
		
		event.enqueueWork(() -> ItemBlockRenderTypes.setRenderLayer(Registration.WALLPAPER_BLOCK.get(), RenderType.translucent()));
	}
	
	@SubscribeEvent
	public static void onModelRegistryEvent(ModelRegistryEvent event) {
		ModelLoaderRegistry.registerLoader(new ResourceLocation(WithoutAWallpaper.MODID, "wallpaper_loader"), new WallpaperModelLoader());
	}
	
	@SubscribeEvent
	public static void onTextureStitch(@Nonnull TextureStitchEvent.Pre event) {
		if (event.getMap().location().equals(TextureAtlas.LOCATION_BLOCKS)) {
			event.addSprite(DYES_TEXTURE);
			event.addSprite(PARTICLE_TEXTURE);
		}
		
	}
	
	public static void registerResourcePack() {
		Minecraft instance = Minecraft.getInstance();
		if (instance != null) {// instance is null in runData
			Path packPath = instance.gameDirectory.toPath().resolve("config").resolve("withoutawallpaper").resolve("CustomWallpaperResources");
			try {
				createEmptyPack(packPath);
				PackRepository resourcePackList = Minecraft.getInstance().getResourcePackRepository();
				resourcePackList.addPackFinder((infoConsumer, infoFactory) ->
				{
					Pack packInfo = Pack.create("CustomWallpaperResources", true,
							() -> new FolderPackResources(packPath.toFile()) {
								@Override
								public boolean isHidden() {
									return true;
								}
							}, infoFactory, Pack.Position.TOP, PackSource.DEFAULT);
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
	}
	
	private static void createEmptyPack(@Nonnull Path packPath) throws IOException {
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