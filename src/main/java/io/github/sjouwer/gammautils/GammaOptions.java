package io.github.sjouwer.gammautils;

import io.github.sjouwer.gammautils.config.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.BaseText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

import static net.minecraft.text.Style.EMPTY;

public class GammaOptions {
    private final ModConfig config;
    private final MinecraftClient minecraft = MinecraftClient.getInstance();

    public GammaOptions() {
        config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    }

    public void toggleGamma() {
        double value = minecraft.options.gamma;
        if (value == config.defaultGamma()) {
            value = config.toggledGamma();
        }
        else {
            value = config.defaultGamma();
        }
        setGamma(value);
    }

    public void increaseGamma(double value) {
        double newValue;
        if (value == 0) {
            newValue = minecraft.options.gamma + config.gammaStep();
        }
        else {
            newValue = minecraft.options.gamma + value;
        }
        setGamma(newValue);
    }

    public void decreaseGamma(double value) {
        double newValue;
        if (value == 0) {
            newValue = minecraft.options.gamma - config.gammaStep();
        }
        else {
            newValue = minecraft.options.gamma - value;
        }
        setGamma(newValue);
    }

    public void minGamma() {
        setGamma(config.minGamma());
    }

    public void maxGamma() {
        setGamma(config.maxGamma());
    }

    public void setGamma(double value) {
        if (config.maxGamma() < config.minGamma() || !config.limitCheck()) {
            minecraft.options.gamma = value;
        }
        else {
            minecraft.options.gamma = Math.max(config.minGamma(), Math.min(value, config.maxGamma()));
        }

        sendMessage();
    }

    private void sendMessage() {
        int gamma = (int)Math.round(minecraft.options.gamma * 100);
        BaseText message = new TranslatableText("text.gamma_utils.message.gamma", gamma);

        if (gamma < 0) {
            message.setStyle(EMPTY.withColor(Formatting.DARK_RED));
        }
        else if (gamma > 100) {
            message.setStyle(EMPTY.withColor(Formatting.GOLD));
        }
        else {
            message.setStyle(EMPTY.withColor(Formatting.DARK_GREEN));
        }
        minecraft.inGameHud.setOverlayMessage(message, false);
    }

    public void saveOptions() {
        if (config.saveEnabled()) {
            minecraft.options.write();
        }
    }
}
