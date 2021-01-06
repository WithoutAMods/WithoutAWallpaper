package withoutaname.mods.withoutawallpaper.datagen;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.common.data.LanguageProvider;
import withoutaname.mods.withoutawallpaper.WithoutAWallpaper;
import withoutaname.mods.withoutawallpaper.setup.Registration;

public class Language extends LanguageProvider {

	public static final String WALLPAPER_DE_DE = "Tapete";
	public static final String WALLPAPER_EN_US = "Wallpaper";
	public static final String PASTING_TABLE_DE_DE = "Tapeziertisch";
	public static final String PASTING_TABLE_EN_US = "Pasting Table";
	private final String locale;

	public Language(DataGenerator gen, String locale) {
		super(gen, WithoutAWallpaper.MODID, locale);
		this.locale = locale;
	}

	@Override
	protected void addTranslations() {
		add(Registration.WALLPAPER_BLOCK.get(), WALLPAPER_DE_DE, WALLPAPER_EN_US);
		add(Registration.WALLPAPER_ITEM.get(), WALLPAPER_DE_DE, WALLPAPER_EN_US);

		add(Registration.PASTING_TABLE_BLOCK.get(), PASTING_TABLE_DE_DE, PASTING_TABLE_EN_US);
		add("screen.withoutawallpaper.pasting_table", PASTING_TABLE_DE_DE, PASTING_TABLE_EN_US);

		add("itemGroup.withoutawallpaper", "WithoutAWallpaper", "WithoutAWallpaper");
	}

	private void add(String key, String de_de, String en_us) {
		switch(locale) {
			case "de_de":
				add(key, de_de);
				break;
			case "en_us":
				add(key, en_us);
				break;
		}
	}

	private void add(Item key, String de_de, String en_us) {
		add(key.getTranslationKey(), de_de, en_us);
	}

	private void add(Block key, String de_de, String en_us) {
		add(key.getTranslationKey(), de_de, en_us);
	}

}
