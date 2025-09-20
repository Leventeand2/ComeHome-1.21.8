package net.levente;

import net.fabricmc.api.ModInitializer;

import net.levente.component.ModDataComponentTypes;
import net.levente.item.ModItems;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComeHome implements ModInitializer {
	public static final String MOD_ID = "home";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
        ModItems.registerModItems();
        ModDataComponentTypes.registerModDataCompTypes();

		LOGGER.info("Hello Fabric world!");
	}

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
}