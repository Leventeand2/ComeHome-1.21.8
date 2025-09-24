package net.levente.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.fabricmc.fabric.impl.loot.FabricLootTable;
import net.levente.item.ModItems;
import net.minecraft.data.DataWriter;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.util.context.ContextType;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class ModLootTableProvider extends SimpleFabricLootTableProvider {
    public ModLootTableProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup, ContextType contextType) {
        super(output, registryLookup, contextType); // TODO: HAVE TO FIX THIS
    }
    @Override
    public void accept(BiConsumer<RegistryKey<LootTable>, LootTable.Builder> exporter) {
        exporter.accept(
                RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of("home","chests/traveller_house")),
                LootTable.builder()
                        .pool(LootPool.builder()
                                .rolls(ConstantLootNumberProvider.create(1))
                                .with(ItemEntry.builder(Items.ENDER_EYE).weight(1))
                                .with(ItemEntry.builder(ModItems.VOID_COMPASS).weight(1))
                                .with(ItemEntry.builder(Items.IRON_INGOT).weight(5))
                                .with(ItemEntry.builder(Items.BREAD).weight(5)))
        );
    }
}
