package io.github.sjouwer.gammautils.mixin;

import io.github.sjouwer.gammautils.GammaUtils;
import net.minecraft.entity.effect.StatusEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StatusEffect.class)
public class MixinStatusEffect {

    @Inject(method = "getTranslationKey", at = @At("HEAD"), cancellable = true)
    public void getGammaTranslationKey(CallbackInfoReturnable<String> info) {
        if (((StatusEffect) (Object) this).equals(GammaUtils.BRIGHT)) {
            info.setReturnValue("effect.gammautils.bright");
        }

        if (((StatusEffect) (Object) this).equals(GammaUtils.DIM)) {
            info.setReturnValue("effect.gammautils.dim");
        }
    }
}
