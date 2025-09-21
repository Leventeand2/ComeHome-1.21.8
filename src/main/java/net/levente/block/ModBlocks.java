package net.levente.block;

import net.levente.ComeHome;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AmethystBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;

import java.util.function.Function;

public class ModBlocks {

    public static final Block CONDENSED_VOID = register(
            "condensed_void",
            Block::new,
            AbstractBlock.Settings.create().sounds(BlockSoundGroup.AMETHYST_BLOCK).requiresTool().strength(4.5f)
    );

    private static Block register(String name, Function<AbstractBlock.Settings, Block> blockFactory,
                                  AbstractBlock.Settings settings) {
        RegistryKey<Block> blocKey = keyOfBlock(name);

        Block block = blockFactory.apply(settings.registryKey(blocKey));

        RegistryKey<Item> itemKey = keyOfItem(name);

        BlockItem blockItem = new BlockItem(block, new Item.Settings().registryKey(itemKey).useBlockPrefixedTranslationKey());
        Registry.register(Registries.ITEM, itemKey, blockItem);

        return Registry.register(Registries.BLOCK, blocKey, block);
    }

    private static RegistryKey<Block> keyOfBlock(String name) {
        return RegistryKey.of(RegistryKeys.BLOCK, ComeHome.id(name));
    }

    private static RegistryKey<Item> keyOfItem(String name) {
        return RegistryKey.of(RegistryKeys.ITEM, ComeHome.id(name));
    }
    public static void registerModBlocks() {}
}
