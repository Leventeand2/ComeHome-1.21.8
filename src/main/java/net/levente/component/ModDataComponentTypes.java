package net.levente.component;

import net.levente.ComeHome;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.UnaryOperator;

public class ModDataComponentTypes {

    public static final ComponentType<BlockPos> HOME_POS = register("home_pos",
            builder -> builder.codec(BlockPos.CODEC));

    public static final ComponentType<RegistryKey<World>> HOME_POS_WORLD = register("home_pos_world",
            builder -> builder.codec(World.CODEC));

    private static <T>ComponentType<T> register(String name, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        ComponentType.Builder<T> builder = ComponentType.<T>builder();
        ComponentType<T> type = builderOperator.apply(builder).build();
        return Registry.register(Registries.DATA_COMPONENT_TYPE, ComeHome.id(name), type);
    }

    public static void registerModDataCompTypes() {}
}
