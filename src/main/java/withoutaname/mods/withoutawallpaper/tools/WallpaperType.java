package withoutaname.mods.withoutawallpaper.tools;

import net.minecraft.util.IStringSerializable;

public enum WallpaperType implements IStringSerializable {
	NONE("none"),
	ORANGE("orange");

	private final String name;

	WallpaperType(String name) {
		this.name = name;
	}

	@Override
	public String getString() {
		return name;
	}

	@Override
	public String toString() {
		return getString();
	}

	public static WallpaperType[] getValuesExceptNone() {
		return new WallpaperType[] {ORANGE};
	}
}
