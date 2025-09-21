package net.levente.event.custom;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public interface ItemPickupCallback {
    void onPickup(PlayerEntity player, ItemEntity itemEntity, ItemStack stack, int count);

    Event<ItemPickupCallback> EVENT = EventFactory.createArrayBacked(
            ItemPickupCallback.class,
            (listeners) -> ((player, itemEntity, stack, count) -> {
                for (ItemPickupCallback listener : listeners) {
                    listener.onPickup(player, itemEntity, stack, count);
                }
            })
    );
}
