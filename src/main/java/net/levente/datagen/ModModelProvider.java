package net.levente.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.levente.block.ModBlocks;
import net.levente.item.ModItems;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Models;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator modelGenerator) {
        modelGenerator.registerSimpleCubeAll(ModBlocks.CONDENSED_VOID);
    }

    @Override
    public void generateItemModels(ItemModelGenerator modelGenerator) {
        modelGenerator.register(ModItems.VOID_COMPASS, Models.GENERATED);
        modelGenerator.register(ModItems.PURIFIED_VOID, Models.GENERATED);
    }
}
