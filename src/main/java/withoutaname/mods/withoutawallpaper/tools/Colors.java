package withoutaname.mods.withoutawallpaper.tools;

import net.minecraft.world.item.DyeColor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class Colors {
	
	public static final Logger LOGGER = LogManager.getLogger();
	
	private List<DyeColor> colors = new ArrayList<>();
	
	public Colors() {
	}
	
	public Colors(@Nonnull String colors) {
		for (String color : colors.split(",")) {
			if (color.equals("all")) {
				addAll();
				break;
			} else {
				DyeColor dyeColor = DyeColor.byName(color, null);
				if (dyeColor != null) {
					this.colors.add(dyeColor);
				} else {
					LOGGER.error("Color \"" + color + "\" does not exist.");
				}
			}
		}
	}
	
	public List<DyeColor> getColors() {
		return colors;
	}
	
	public void setColors(List<DyeColor> colors) {
		this.colors = colors;
	}
	
	public Colors add(DyeColor dyeColor) {
		colors.add(dyeColor);
		return this;
	}
	
	public Colors addAll() {
		for (int i = 0; i < 16; i++) {
			addByID(i);
		}
		return this;
	}
	
	public Colors addAll(@Nonnull List<DyeColor> dyeColors) {
		for (DyeColor color : dyeColors) {
			add(color);
		}
		return this;
	}
	
	public Colors addByID(int id) {
		return add(DyeColor.byId(id));
	}
	
	public Colors addAllByID(@Nonnull List<Integer> ids) {
		for (int id : ids) {
			addByID(id);
		}
		return this;
	}
	
	public Colors addWhite() {
		return addByID(0);
	}
	
	public Colors addOrange() {
		return addByID(1);
	}
	
	public Colors addMagenta() {
		return addByID(2);
	}
	
	public Colors addLightBlue() {
		return addByID(3);
	}
	
	public Colors addYellow() {
		return addByID(4);
	}
	
	public Colors addLime() {
		return addByID(5);
	}
	
	public Colors addPink() {
		return addByID(6);
	}
	
	public Colors addGray() {
		return addByID(7);
	}
	
	public Colors addLightGray() {
		return addByID(8);
	}
	
	public Colors addCyan() {
		return addByID(9);
	}
	
	public Colors addPurple() {
		return addByID(10);
	}
	
	public Colors addBlue() {
		return addByID(11);
	}
	
	public Colors addBrown() {
		return addByID(12);
	}
	
	public Colors addGreen() {
		return addByID(13);
	}
	
	public Colors addRed() {
		return addByID(14);
	}
	
	public Colors addBlack() {
		return addByID(15);
	}
	
}
