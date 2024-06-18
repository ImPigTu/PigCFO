package ru.pigtu.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.pigtu.Pig;
import ru.pigtu.command.EnablePigCommand;

import static ru.pigtu.Pig.limitPackets;
import static ru.pigtu.Pig.mc;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Inject(at = @At("HEAD"), method = "doItemUse", cancellable = true)
    private void onDoItemUse(CallbackInfo ci) {
        if (EnablePigCommand.fastCrystal) {
            ItemStack mainHand = mc.player.getMainHandStack();
            if (mainHand.isOf(Items.END_CRYSTAL))
                //ensures only are packets are being sent through
                if (Pig.hitCount != limitPackets())
                    ci.cancel();
        }
    }
}