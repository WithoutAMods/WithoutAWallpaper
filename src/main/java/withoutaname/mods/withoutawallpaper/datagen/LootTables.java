package withoutaname.mods.withoutawallpaper.datagen;

import net.minecraft.data.DataGenerator;
import withoutaname.mods.withoutalib.datagen.BaseLootTableProvider;
import withoutaname.mods.withoutawallpaper.setup.Registration;

public class LootTables extends BaseLootTableProvider {

	public LootTables(DataGenerator dataGeneratorIn) {
		super(dataGeneratorIn);
	}

	@Override
	protected void addTables() {
		createStandardTable(Registration.ROLLING_STATION_BLOCK.get(), Registration.ROLLING_STATION_ITEM.get());
	}

}
