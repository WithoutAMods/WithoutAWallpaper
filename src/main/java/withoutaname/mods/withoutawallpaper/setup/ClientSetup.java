package withoutaname.mods.withoutawallpaper.setup;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import withoutaname.mods.withoutawallpaper.WithoutAWallpaper;
import withoutaname.mods.withoutawallpaper.blocks.PastingTableRenderer;
import withoutaname.mods.withoutawallpaper.blocks.PastingTableScreen;
import withoutaname.mods.withoutawallpaper.blocks.WallpaperModelLoader;

import static withoutaname.mods.withoutawallpaper.blocks.PastingTableRenderer.DYES_TEXTURE;
import static withoutaname.mods.withoutawallpaper.blocks.WallpaperBakedModel.PARTICLE_TEXTURE;

@Mod.EventBusSubscriber(modid = WithoutAWallpaper.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {

	public static void init(final FMLClientSetupEvent event) {
		ScreenManager.registerFactory(Registration.PASTING_TABLE_CONTAINER.get(), PastingTableScreen::new);
		PastingTableRenderer.register();

		event.enqueueWork(() -> RenderTypeLookup.setRenderLayer(Registration.WALLPAPER_BLOCK.get(), (renderType) -> renderType.equals(RenderType.getTranslucent())));
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

}