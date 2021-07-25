package withoutaname.mods.withoutawallpaper.tools;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import withoutaname.mods.withoutawallpaper.WithoutAWallpaper;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class WallpaperType {
	
	public static final WallpaperType NONE = new WallpaperType(WallpaperDesign.NONE);
	
	private WallpaperDesign design;
	private DyeColor[] colors;
	
	public WallpaperType(@Nonnull WallpaperDesign design, @Nonnull DyeColor... colors) {
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
	
	public static WallpaperType fromNBT(@Nonnull CompoundTag wallpaperTypeNBT) {
		List<DyeColor> colors = new ArrayList<>();
		for (int colorID : wallpaperTypeNBT.getIntArray("colors")) {
			colors.add(DyeColor.byId(colorID));
		}
		try {
			return new WallpaperType(WallpaperDesign.fromString(wallpaperTypeNBT.getString("design")), colors);
		} catch (IllegalArgumentException e) {
			Colors[] availableColors = new Colors[colors.size()];
			for (int i = 0; i < colors.size(); i++) {
				availableColors[i] = new Colors().add(colors.get(i));
			}
			return new WallpaperType(new WallpaperDesign(wallpaperTypeNBT.getString("design"), availableColors), colors);
		}
	}
	
	@Nonnull
	private static DyeColor[] getColorsFromList(@Nonnull List<DyeColor> colors) {
		DyeColor[] array = new DyeColor[colors.size()];
		for (int i = 0; i < colors.size(); i++) {
			array[i] = colors.get(i);
		}
		return array;
	}
	
	public CompoundTag toNBT() {
		CompoundTag wallpaperTypeNBT = new CompoundTag();
		wallpaperTypeNBT.putString("design", design.toString());
		int[] colorIDs = new int[colors.length];
		for (int i = 0; i < colors.length; i++) {
			colorIDs[i] = colors[i].getId();
		}
		wallpaperTypeNBT.putIntArray("colors", colorIDs);
		return wallpaperTypeNBT;
	}
	
	public DyeColor[] getColors() {
		return colors;
	}
	
	public void setColors(@Nonnull DyeColor[] colors) {
		if (colors.length != design.getColorCount()) {
			throw new IllegalStateException(colors.length + " colors defined, " + design.getColorCount() + " colors expected for design " + design.toString() + "!");
		} else {
			this.colors = colors;
		}
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
			atlasSprites.add(Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(resourceLocation));
		}
		return atlasSprites;
	}
	
	public WallpaperDesign getDesign() {
		return design;
	}
	
	public void setDesign(WallpaperDesign design) {
		this.design = design;
	}
	
}
