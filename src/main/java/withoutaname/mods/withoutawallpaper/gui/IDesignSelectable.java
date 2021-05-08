package withoutaname.mods.withoutawallpaper.gui;

import javax.annotation.Nonnull;

import withoutaname.mods.withoutawallpaper.tools.WallpaperDesign;

public interface IDesignSelectable {
	
	WallpaperDesign getDesign();
	
	void setDesign(@Nonnull WallpaperDesign design);
	
}
