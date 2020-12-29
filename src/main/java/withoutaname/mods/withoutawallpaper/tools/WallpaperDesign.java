package withoutaname.mods.withoutawallpaper.tools;

import net.minecraft.item.DyeColor;
import net.minecraft.util.ResourceLocation;
import withoutaname.mods.withoutawallpaper.WithoutAWallpaper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum WallpaperDesign {
	NONE("none"),
	ORANGE("orange", new Colors().addOrange()),
	WALLPAPER1("wallpaper1", new Colors().addAll(), new Colors().addAll());

	private final String name;
	private final Colors[] availableColors;

	WallpaperDesign(String name, Colors... availableColors) {
		this.name = name;
		this.availableColors = availableColors;
	}

	@Override
	public String toString() {
		return this.name;
	}

	public static List<ResourceLocation> getAllTextures() {
		List<ResourceLocation> textures = new ArrayList<>();
		for (WallpaperDesign wallpaperDesign : WallpaperDesign.getValuesExceptNone()) {
			textures.addAll(wallpaperDesign.getTextures());
		}
		return textures;
	}

	public Colors[] getAvailableColors() {
		return availableColors;
	}

	public int getColorCount() {
		return availableColors.length;
	}

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
		return Arrays.stream(values()).filter(wallpaperDesign -> wallpaperDesign != NONE).collect(Collectors.toList());
	}

	public static WallpaperDesign fromString(String design) {
		WallpaperDesign[] designs = values();
		for (WallpaperDesign d : designs) {
			if (d.toString().equalsIgnoreCase(design)) {
				return d;
			}
		}
		throw new IllegalArgumentException("No enum constant " + design);
	}

}
