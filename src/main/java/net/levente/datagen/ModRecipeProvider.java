package net.levente.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.levente.block.ModBlocks;
import net.levente.item.ModItems;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
        return new RecipeGenerator(wrapperLookup, recipeExporter) {
            @Override
            public void generate() {
                RegistryWrapper.Impl<Item> itemLookup = registries.getOrThrow(RegistryKeys.ITEM);

                offerSmelting(
                        List.of(ModBlocks.CONDENSED_VOID),
                        RecipeCategory.MISC,
                        ModItems.PURIFIED_VOID,
                        12f,
                        300,
                        "void_block_to_purified_void"
                );

                createShapeless(RecipeCategory.TOOLS, ModItems.VOID_COMPASS)
                        .input(Items.RECOVERY_COMPASS)
                        .input(ModItems.BROKEN_VOID_COMPASS)
                        .input(Items.NETHER_STAR)
                        .criterion(hasItem(Items.COMPASS), conditionsFromItem(Items.COMPASS))
                        .criterion(hasItem(ModItems.BROKEN_VOID_COMPASS), conditionsFromItem(ModItems.BROKEN_VOID_COMPASS))
                        .criterion(hasItem(Items.NETHER_STAR), conditionsFromItem(Items.NETHER_STAR))
                        .offerTo(recipeExporter);
            }
        };
    }

    @Override
    public String getName() {
        return "ModRecipeProvider";
    }
}
