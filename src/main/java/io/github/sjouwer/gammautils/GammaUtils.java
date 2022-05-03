package io.github.sjouwer.gammautils;

import io.github.sjouwer.gammautils.config.ModConfig;
import io.github.sjouwer.gammautils.statuseffect.*;
import io.github.sjouwer.gammautils.util.InfoProvider;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class GammaUtils implements ClientModInitializer {
    public static final StatusEffect BRIGHT = new BrightStatusEffect();
    public static final StatusEffect DIM = new DimStatusEffect();

    private static ConfigHolder<ModConfig> configHolder;

    public static ModConfig getConfig() {
        return configHolder.getConfig();
    }

    @Override
    public void onInitializeClient() {
        configHolder = AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);
        configHolder.registerSaveListener((manager, data) -> {
            InfoProvider.updateStatusEffect();
            return ActionResult.SUCCESS;
        });

        GammaOptions gammaOptions = new GammaOptions();

        KeyBindings keyBindings = new KeyBindings(gammaOptions);
        keyBindings.setKeyBindings();

        Commands commands = new Commands(gammaOptions);
        commands.registerCommands(ClientCommandManager.DISPATCHER);

        Registry.register(Registry.STATUS_EFFECT, new Identifier("gammautils", "bright"), BRIGHT);
        Registry.register(Registry.STATUS_EFFECT, new Identifier("gammautils", "dim"), DIM);
    }
}
