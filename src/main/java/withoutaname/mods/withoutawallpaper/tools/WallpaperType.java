package withoutaname.mods.withoutawallpaper.tools;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.DyeColor;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import withoutaname.mods.withoutawallpaper.WithoutAWallpaper;

import java.util.ArrayList;
import java.util.List;

public class WallpaperType {

	public static final WallpaperType NONE = new WallpaperType(WallpaperDesign.NONE);

	private WallpaperDesign design;
	private DyeColor[] colors;

	public WallpaperType(WallpaperDesign design, DyeColor... colors) {
		if (colors.length != design.getColorCount()) {
			throw new IllegalStateException(colors.length + " colors defined, " + design.getColorCount() + " colors expected for design " + design.toString() + "!");
		} else {
			this.design = design;
			this.colors = colors;
		}
	}

	public WallpaperType(WallpaperDesign design, List<DyeColor> colors) {
		this(design, getColorsFromList(colors));
	}

	public static WallpaperType fromNBT(CompoundNBT wallpaperTypeNBT) {
		List<DyeColor> colors = new ArrayList<>();
		for (int colorID : wallpaperTypeNBT.getIntArray("colors")) {
			colors.add(DyeColor.byId(colorID));
		}
		return new WallpaperType(WallpaperDesign.fromString(wallpaperTypeNBT.getString("design")), colors);
	}

	public CompoundNBT toNBT() {
		CompoundNBT wallpaperTypeNBT;
		wallpaperTypeNBT = new CompoundNBT();
		wallpaperTypeNBT.putString("design", design.toString());
		int[] colorIDs = new int[colors.length];
		for (int i = 0; i < colors.length; i++) {
			colorIDs[i] = colors[i].getId();
		}
		wallpaperTypeNBT.putIntArray("colors", colorIDs);
		return wallpaperTypeNBT;
	}

	private static DyeColor[] getColorsFromList(List<DyeColor> colors) {
		DyeColor[] array = new DyeColor[colors.size()];
		for (int i = 0; i < colors.size(); i++) {
			array[i] = colors.get(i);
		}
		return array;
	}

	public void setColors(DyeColor[] colors) {
		if (colors.length != design.getColorCount()) {
			throw new IllegalStateException(colors.length + " colors defined, " + design.getColorCount() + " colors expected for design " + design.toString() + "!");
		} else {
			this.colors = colors;
		}
	}

	public DyeColor[] getColors() {
		return colors;
	}

	public List<ResourceLocation> getResourceLocations() {
		List<ResourceLocation> resourceLocations = new ArrayList<>();
		for (int i = 0; i < colors.length; i++) {
			resourceLocations.add(new ResourceLocation(WithoutAWallpaper.MODID, "block/wallpaper/" + design.toString() + "/color" + i + "/" + colors[i].toString()));
		}
		return resourceLocations;
	}

	public List<TextureAtlasSprite> getAtlasSprites() {
		List<TextureAtlasSprite> atlasSprites = new ArrayList<>();
		for (ResourceLocation resourceLocation : getResourceLocations()) {
			atlasSprites.add(Minecraft.getInstance().getAtlasSpriteGetter(AtlasTexture.LOCATION_BLOCKS_TEXTURE).apply(resourceLocation));
		}
		return atlasSprites;
	}

	public void setDesign(WallpaperDesign design) {
		this.design = design;
	}

	public WallpaperDesign getDesign() {
		return design;
	}

}
