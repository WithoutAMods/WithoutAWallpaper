package withoutaname.mods.withoutawallpaper.gui.designselection;

import javax.annotation.Nonnull;

import withoutaname.mods.withoutawallpaper.tools.WallpaperDesign;

public interface IDesignSelectable {
	
	WallpaperDesign getDesign();
	
	void setDesign(@Nonnull WallpaperDesign design);
	
}
