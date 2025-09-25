package net.levente.item.custom;

import net.levente.component.ModDataComponentTypes;
import net.levente.item.ModItems;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
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
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient()) {
            return ActionResult.PASS;
        }

        ItemStack stack = user.getStackInHand(hand);

        if (user.isSneaking()) {
            stack.set(ModDataComponentTypes.HOME_POS, null);
            stack.set(ModDataComponentTypes.HOME_POS_WORLD, null);
            stack.set(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, false);
            return ActionResult.SUCCESS;
        } else {
            double maxDistance;
            if (user.isCreative()) { maxDistance = 5.0; } else { maxDistance = 4.5; }
            HitResult hit = user.raycast(maxDistance, 0.0f, false);


            if (hit.getType() == HitResult.Type.BLOCK) {
                BlockPos homePos = ((BlockHitResult) hit).getBlockPos();
                RegistryKey<World> homeDim = world.getRegistryKey();

                stack.set(ModDataComponentTypes.HOME_POS, homePos);
                stack.set(ModDataComponentTypes.HOME_POS_WORLD, homeDim);

                stack.set(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true);

                return ActionResult.SUCCESS;
            } else {
                @Nullable BlockPos homePos = stack.get(ModDataComponentTypes.HOME_POS);
                @Nullable RegistryKey<World> homeDim = stack.get(ModDataComponentTypes.HOME_POS_WORLD);

                if (homePos != null && homeDim != null) {
                    MinecraftServer server = user.getServer();
                    if (server != null) {
                        ServerWorld targetWorld = server.getWorld(homeDim);
                        if (targetWorld != null) {
                            user.teleport(targetWorld, homePos.getX(), (double) homePos.getY() + 1, homePos.getZ(), Collections.emptySet(), user.getYaw(), user.getPitch(), false);
                            stack.damage(1, user, user.getActiveHand().equals(Hand.MAIN_HAND)
                                    ? EquipmentSlot.MAINHAND
                                    : EquipmentSlot.OFFHAND);
                            if (stack.getDamage() >= stack.getMaxDamage()) {
                                ItemStack newItem = new ItemStack(ModItems.BROKEN_VOID_COMPASS);
                                user.setStackInHand(hand, newItem);
                            }

                            return ActionResult.SUCCESS;
                        }
                    }
                    return ActionResult.FAIL;
                }
            }
        }
        return ActionResult.PASS;
    }
}
