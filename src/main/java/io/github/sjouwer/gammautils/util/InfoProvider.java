package io.github.sjouwer.gammautils.util;

import io.github.sjouwer.gammautils.GammaOptions;
import io.github.sjouwer.gammautils.GammaUtils;
import io.github.sjouwer.gammautils.config.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public final class InfoProvider {
    private static final MinecraftClient client = MinecraftClient.getInstance();
    private static final ModConfig config = GammaUtils.getConfig();

    private InfoProvider() {
    }

    public static void showGammaHudMessage() {
        if (!config.isGammaMessageEnabled()) {
            return;
        }

        int gamma = GammaOptions.getGammaPercentage();
        MutableText message = Text.translatable("text.gamma_utils.message.gamma", gamma);

        Formatting format;
        if (gamma < 0) {
            format = Formatting.DARK_RED;
        }
        else if (gamma > 100) {
            format = Formatting.GOLD;
        }
        else {
            format = Formatting.DARK_GREEN;
        }

        message.formatted(format);
        client.inGameHud.setOverlayMessage(message, false);
    }

    public static void showNightVisionHudMessage(boolean enabled) {
        if (!config.isGammaMessageEnabled()) {
            return;
        }

        MutableText message;
        if (enabled) {
            message = Text.translatable("text.gamma_utils.message.nightvision.enabled");
            message.formatted(Formatting.DARK_GREEN);
        }
        else {
            message = Text.translatable("text.gamma_utils.message.nightvision.disabled");
            message.formatted(Formatting.DARK_RED);
        }

        client.inGameHud.setOverlayMessage(message, false);
    }
}
