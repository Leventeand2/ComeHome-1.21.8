package net.levente.event;

import net.levente.component.ModDataComponentTypes;
import net.levente.event.custom.ItemPickupCallback;
import net.levente.item.ModItems;
import net.minecraft.component.DataComponentTypes;

public class ModEvents {

    public static void registerModEvents() {
        // Item pickup event
        ItemPickupCallback.EVENT.register(((player, itemEntity, stack, count) -> {
            if (stack.isOf(ModItems.VOID_COMPASS)) {
                if (player.get(ModDataComponentTypes.HOME_POS) != null) {
                    stack.set(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true);
                }
            }
        }));
    }
}
