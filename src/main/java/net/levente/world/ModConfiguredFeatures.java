package net.levente.world;

import net.levente.ComeHome;
import net.levente.block.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;

import java.util.List;

public class ModConfiguredFeatures {

    public static final RegistryKey<ConfiguredFeature<?, ?>> END_VOID_BLOCK_KEY = registerKey("end_void_block_key");

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        RuleTest endReplaceable = new BlockMatchRuleTest(Blocks.OBSIDIAN);

        List<OreFeatureConfig.Target> endVoidBlocks =
                List.of(OreFeatureConfig.createTarget(endReplaceable, ModBlocks.CONDENSED_VOID.getDefaultState()));

        register(context, END_VOID_BLOCK_KEY, Feature.ORE, new OreFeatureConfig(endVoidBlocks, 12));


    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, ComeHome.id(name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?, ?>> key, F feature,
                                                                                   FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
