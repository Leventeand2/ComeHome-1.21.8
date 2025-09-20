package net.levente;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.levente.datagen.ModBlockTagProvider;
import net.levente.datagen.ModLangProvider;
import net.levente.datagen.ModModelProvider;
import net.minecraft.client.data.ModelProvider;

public class ComeHomeDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(ModLangProvider::new);
        pack.addProvider(ModModelProvider::new);
        pack.addProvider(ModBlockTagProvider::new);

	}
}
