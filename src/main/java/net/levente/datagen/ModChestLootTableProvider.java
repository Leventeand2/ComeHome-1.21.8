package net.levente.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.levente.item.ModItems;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.EmptyEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.util.context.ContextType;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class ModChestLootTableProvider extends SimpleFabricLootTableProvider {
    public ModChestLootTableProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup, ContextType contextType) {
        super(output, registryLookup, contextType);
    }

    @Override
    public void accept(BiConsumer<RegistryKey<LootTable>, LootTable.Builder> lootTableBiConsumer) {
        RegistryKey<LootTable> nonSecret = RegistryKey.of(RegistryKeys.LOOT_TABLE,
                Identifier.of("home", "chest/traveller_house_loot"));
        RegistryKey<LootTable> secret = RegistryKey.of(RegistryKeys.LOOT_TABLE,
                Identifier.of("home", "chest/traveller_house_secret_loot"));

        LootTable.Builder nonSecretBuilder = LootTable.builder()
                .pool(LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(7))
                        .with(ItemEntry.builder(Items.BREAD).weight(5))
                        .with(ItemEntry.builder(Items.IRON_INGOT).weight(2))
                        .with(ItemEntry.builder(ModItems.PURIFIED_VOID).weight(1))
                        .with(ItemEntry.builder(Items.DIAMOND).weight(1)))
                .pool(LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.VOID_COMPASS)
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1)))
                                .conditionally(RandomChanceLootCondition.builder(0.05f)))
                        .with(ItemEntry.builder(ModItems.BROKEN_VOID_COMPASS)
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1)))
                                .conditionally(RandomChanceLootCondition.builder(0.1f))));

        LootTable.Builder secretBuilder = LootTable.builder()
                .pool(LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(9))
                        .with(ItemEntry.builder(Items.BREAD).weight(10))
                        .with(ItemEntry.builder(Items.IRON_INGOT).weight(3))
                        .with(ItemEntry.builder(ModItems.PURIFIED_VOID).weight(3))
                        .with(ItemEntry.builder(Items.DIAMOND).weight(3)))
                .pool(LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.VOID_COMPASS)
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1)))
                                .conditionally(RandomChanceLootCondition.builder(0.015f)))
                        .with(ItemEntry.builder(ModItems.BROKEN_VOID_COMPASS)
                                .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1)))
                                .conditionally(RandomChanceLootCondition.builder(0.112f)))
                        .with(EmptyEntry.builder().weight(3)));

        lootTableBiConsumer.accept(nonSecret, nonSecretBuilder);
        lootTableBiConsumer.accept(secret, secretBuilder);
    }
}
