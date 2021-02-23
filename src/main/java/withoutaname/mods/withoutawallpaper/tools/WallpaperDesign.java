package withoutaname.mods.withoutawallpaper.tools;

import net.minecraft.item.DyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import withoutaname.mods.withoutawallpaper.WithoutAWallpaper;
import withoutaname.mods.withoutawallpaper.setup.Config;

import java.util.ArrayList;
import java.util.List;

public class WallpaperDesign {
	public static final WallpaperDesign NONE = new WallpaperDesign("none");

	private static List<WallpaperDesign> designs = new ArrayList<>();

	public static final Logger LOGGER = LogManager.getLogger();

	private final String name;
	private final Colors[] availableColors;

	public WallpaperDesign(String name, Colors... availableColors) {
		this.name = name;
		this.availableColors = availableColors;
	}

	public static void loadDesigns() {
		designs = new ArrayList<>();
		List<String> designsString = Config.CUSTOM_DESIGNS.get();
		for (String designString : designsString) {
			if (designString.contains("[") && designString.contains("]")) {
				String[] designStringSplit = designString.substring(0, designString.length() - 1).split("\\[");
				String[] availableColorsString = designStringSplit[1].split(";");
				if (availableColorsString.length <= 3){
					Colors[] availableColors = new Colors[availableColorsString.length];
					for (int i = 0; i < availableColorsString.length; i++) {
						availableColors[i] = new Colors(availableColorsString[i]);
					}
					designs.add(new WallpaperDesign(designStringSplit[0], availableColors));
					LOGGER.info("Successfully loaded design \"" + designStringSplit[0] + "\".");
				} else {
					LOGGER.error("Error loading design \"" + designStringSplit[0] + "\". Only three colors are allowed!");
				}
			} else {
				LOGGER.error("Error loading design \"" + designString + "\". No colors defined");
			}
		}
	}

	//varies with changes to the config
	public int toInt() {
		for (int i = 0; i < designs.size(); i++) {
			if (this.name.equals(designs.get(i).getName())) {
				return i;
			}
		}
		return -1; //NONE or wrong name
	}

	@Override
	public String toString() {
		return this.name;
	}

	@OnlyIn(Dist.CLIENT)
	public static List<ResourceLocation> getAllTextures() {
		List<ResourceLocation> textures = new ArrayList<>();
		for (WallpaperDesign wallpaperDesign : WallpaperDesign.getValuesExceptNone()) {
			textures.addAll(wallpaperDesign.getTextures());
		}
		return textures;
	}

	public String getName() {
		return name;
	}

	public Colors[] getAvailableColors() {
		return availableColors;
	}

	public int getColorCount() {
		return availableColors.length;
	}

	@OnlyIn(Dist.CLIENT)
	public List<ResourceLocation> getTextures() {
		List<ResourceLocation> textures = new ArrayList<>();
		Colors colors;
		for (int i = 0; i < availableColors.length; i++) {
			colors = availableColors[i];
			for (DyeColor color : colors.getColors()) {
				textures.add(new ResourceLocation(WithoutAWallpaper.MODID, "block/wallpaper/" + name + "/color" + i + "/" + color.toString()));
			}
		}
		return textures;
	}

	public static List<WallpaperDesign> getValuesExceptNone() {
		return designs;
	}

	public static WallpaperDesign fromInt(int i) {
		if (i == -1) {
			return NONE;
		}
		if (i >= 0 && i < designs.size()) {
			return designs.get(i);
		}
		return null;
	}

	public static WallpaperDesign fromString(String design) {
		if (design.equals("none")) {
			return NONE;
		}
		for (WallpaperDesign d : designs) {
			if (d.getName().equals(design)) {
				return d;
			}
		}
		throw new IllegalArgumentException("No design " + design);
	}

}
