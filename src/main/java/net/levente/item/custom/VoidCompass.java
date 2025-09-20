package net.levente.item.custom;

import net.levente.ComeHome;
import net.levente.component.ModDataComponentTypes;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class VoidCompass extends Item {
    public VoidCompass(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();

        if (player != null) {
            if (!player.isSneaking()) {
                BlockPos homePos = context.getBlockPos();
                player.setComponent(ModDataComponentTypes.HOME_POS, homePos);
                ItemStack stack = context.getStack();
                stack.set(DataComponentTypes.MAX_DAMAGE, 100);
                stack.set(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true);
                stack.set(DataComponentTypes.DAMAGE, 25);
                player.sendMessage(Text.literal("Set home pos to: " + homePos), true);
                ComeHome.LOGGER.warn(homePos.toString());
                return ActionResult.SUCCESS;
            } else {
                player.setComponent(ModDataComponentTypes.HOME_POS, null);
                ItemStack stack = context.getStack();
                stack.set(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, false);
                player.sendMessage(Text.literal("Removed homePos"), true);
                ComeHome.LOGGER.warn(ModDataComponentTypes.HOME_POS.toString());
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.FAIL;
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        BlockPos homePos = user.get(ModDataComponentTypes.HOME_POS);
        if (homePos != null) {
            ComeHome.LOGGER.warn(homePos.toString());
            user.teleport(
                    homePos.getX(),
                    homePos.getY(),
                    homePos.getZ(),
                    true
            );
            user.sendMessage(Text.literal("Teleported to: " + homePos), true);
            return ActionResult.SUCCESS;
        }
        user.sendMessage(Text.literal("Failed to teleport"), true);
        return ActionResult.FAIL;
    }
}
