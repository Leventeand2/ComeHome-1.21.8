package net.levente.util;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.levente.item.ModItems;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.util.Identifier;

public class ModLootTableModifier {

    public static void modifyLootTables() {
        LootTableEvents.REPLACE.register(((registryKey, lootTable, lootTableSource, wrapperLookup) -> {
            Identifier target = Identifier.of("home", "chests/traveller_house_chest_loot");

            if (registryKey.getValue().equals(target)) {
                return LootTable.builder()
                        .pool(LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(Items.ENDER_EYE).weight(1))
                        .with(ItemEntry.builder(ModItems.VOID_COMPASS).weight(1))
                        .with(ItemEntry.builder(Items.IRON_INGOT).weight(5))
                        .with(ItemEntry.builder(Items.BREAD).weight(5))).build();
            }
            return lootTable;
        }));
    }
}
