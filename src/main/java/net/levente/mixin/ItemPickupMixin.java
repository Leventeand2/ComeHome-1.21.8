package net.levente.mixin;

import net.levente.event.custom.ItemPickupCallback;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public class ItemPickupMixin {
    @Inject(method = "onPlayerCollision",
    at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/ItemEntity;getStack()Lnet/minecraft/item/ItemStack;"))
    private void firePickupEvent(PlayerEntity player, CallbackInfo ci) {
        ItemEntity self = (ItemEntity)(Object)this;
        ItemStack stack = self.getStack();
        int count = stack.getCount();

        ItemPickupCallback.EVENT.invoker().onPickup(player, self, stack, count);
    }

}
