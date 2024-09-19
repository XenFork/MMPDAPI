package io.github.xenfork.mmpd.api.mixins.main;

import io.github.xenfork.mmpd.api.Main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value = Main.class, remap = false)
public class TestOutMixin {
    @Inject(method = "init", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILEXCEPTION, remap = false)
    private static void init(CallbackInfo ci) {

    }
}
