package net.levente.item.custom;

import net.levente.ComeHome;
import net.levente.component.ModDataComponentTypes;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;

public class VoidCompass extends Item {
    public VoidCompass(Settings settings) {
        super(settings);
    }


    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 100;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        PlayerEntity player = context.getPlayer();

        if (world.isClient() || player == null) {
            return ActionResult.PASS;
        }

        ItemStack stack = context.getStack();

        if (!player.isSneaking()) {
            BlockPos homePos = context.getBlockPos();
            RegistryKey<World> homeDim = world.getRegistryKey();

            stack.set(ModDataComponentTypes.HOME_POS, homePos);
            stack.set(ModDataComponentTypes.HOME_POS_WORLD, homeDim);
            stack.set(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true);
            stack.set(DataComponentTypes.DAMAGE, 25);
            player.sendMessage(Text.literal("Set home pos to: " + homePos), true);
            ComeHome.LOGGER.warn(homePos.toString());
            return ActionResult.SUCCESS;
        } else {
            stack.set(ModDataComponentTypes.HOME_POS, null);
            stack.set(ModDataComponentTypes.HOME_POS_WORLD, null);
            stack.set(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, false);
            player.sendMessage(Text.literal("Removed homePos"), true);
            ComeHome.LOGGER.warn(ModDataComponentTypes.HOME_POS.toString());
            return ActionResult.SUCCESS;
        }
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient()) {
            return ActionResult.PASS;
        }

        ItemStack stack = user.getStackInHand(hand);

        @Nullable BlockPos homePos = stack.get(ModDataComponentTypes.HOME_POS);
        @Nullable RegistryKey<World> homeDim = stack.get(ModDataComponentTypes.HOME_POS_WORLD);

        if (homePos != null && homeDim != null) {
            MinecraftServer server = user.getServer();
            if (server != null) {
                ServerWorld targetWorld = server.getWorld(homeDim);
                if (targetWorld != null) {
                    user.teleport(targetWorld, (double)homePos.getX(), (double)homePos.getY() + 1, (double)homePos.getZ(), Collections.emptySet(), user.getYaw(), user.getPitch(), false);
                    return ActionResult.SUCCESS;
                }
            }
        }
        return ActionResult.FAIL;
    }
}
