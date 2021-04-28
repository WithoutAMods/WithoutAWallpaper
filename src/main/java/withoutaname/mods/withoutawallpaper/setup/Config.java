package withoutaname.mods.withoutawallpaper.setup;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import withoutaname.mods.withoutawallpaper.tools.WallpaperDesign;

@Mod.EventBusSubscriber
public class Config {
	
	public static final String CATEGORY_WALLPAPER = "wallpaper";
	
	public static ForgeConfigSpec SERVER_CONFIG;
	
	public static ForgeConfigSpec.BooleanValue ENABLE_CUSTOM_DESIGNS;
	public static List<String> defaultDesigns = new ArrayList<>();
	public static ForgeConfigSpec.ConfigValue<List<String>> CUSTOM_DESIGNS;
	
	static {
		ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
		
		SERVER_BUILDER.comment("Wallpaper settings").push(CATEGORY_WALLPAPER);
		setupServerWallpaperDesignConfig(SERVER_BUILDER);
		SERVER_BUILDER.pop();
		
		SERVER_CONFIG = SERVER_BUILDER.build();
	}
	
	private static void setupServerWallpaperDesignConfig(@Nonnull ForgeConfigSpec.Builder SERVER_BUILDER) {
		ENABLE_CUSTOM_DESIGNS = SERVER_BUILDER.comment("If this is enabled, customDesigns can be edited. [default: false]").define("enableCustomDesigns", false);
		defaultDesigns = new ArrayList<>();
		defaultDesigns.add("wallpaper0[orange]");
		defaultDesigns.add("wallpaper1[all;all]");
		CUSTOM_DESIGNS = SERVER_BUILDER.comment("Custom designs").define("customDesigns", defaultDesigns); //TODO add explanation to comment
	}
	
	private static void loadConfigs() {
		if (!ENABLE_CUSTOM_DESIGNS.get()) {
			CUSTOM_DESIGNS.set(defaultDesigns);
		}
		WallpaperDesign.loadDesigns();
	}
	
	@SubscribeEvent
	public static void onLoad(final ModConfig.Loading configEvent) {
		loadConfigs();
	}
	
	@SubscribeEvent
	public static void onReload(final ModConfig.Reloading configEvent) {
		loadConfigs();
	}
	
}
