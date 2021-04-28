package withoutaname.mods.withoutawallpaper;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import withoutaname.mods.withoutawallpaper.setup.ClientSetup;
import withoutaname.mods.withoutawallpaper.setup.Config;
import withoutaname.mods.withoutawallpaper.setup.ModSetup;
import withoutaname.mods.withoutawallpaper.setup.Registration;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(WithoutAWallpaper.MODID)
public class WithoutAWallpaper {
	
	public static final String MODID = "withoutawallpaper";
	
	public static final Logger LOGGER = LogManager.getLogger();
	
	public WithoutAWallpaper() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SERVER_CONFIG);
		
		Registration.init();
		
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClientSetup::registerResourcePack);
		
		// Register the setup method for modloading
		FMLJavaModLoadingContext.get().getModEventBus().addListener(ModSetup::init);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::init);
		
		FMLJavaModLoadingContext.get().getModEventBus().register(Config.class);
	}
	
}
