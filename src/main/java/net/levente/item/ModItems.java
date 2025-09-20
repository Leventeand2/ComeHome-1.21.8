package net.levente.item;

import net.levente.ComeHome;
import net.levente.item.custom.VoidCompass;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import java.util.function.Function;

public class ModItems {

    public static final Item VOID_COMPASS = register(
            "void_compass",
            VoidCompass::new,
            new Item.Settings().maxCount(1)
    );
    private static Item register(String name, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, ComeHome.id(name));

        Item item = itemFactory.apply(settings.registryKey(itemKey));

        Registry.register(Registries.ITEM, itemKey, item);

        return item;
    }

    public static void registerModItems() {
    }
}
