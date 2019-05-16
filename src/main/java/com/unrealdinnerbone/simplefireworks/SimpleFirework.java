package com.unrealdinnerbone.simplefireworks;

import com.unrealdinnerbone.simplefireworks.api.FireworkWrapper;
import com.unrealdinnerbone.simplefireworks.command.spawn.CommandSpawnFireworkObject;
import com.unrealdinnerbone.simplefireworks.data.FireworkDataBase;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

public class SimpleFirework implements ModInitializer {

    public static Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "simplefireworks";
    public static final Identifier SPAWN_FIREWORK = new Identifier(MOD_ID, "spawn_firework");
    private static final FireworkDataBase<FireworkWrapper> FIREWORK_DATA = new FireworkDataBase<>(FireworkWrapper.class, "fireworks");

    @Override
    public void onInitialize() {
        LOGGER.info("[SimpleFireworks] Loading!");
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(FIREWORK_DATA);
        CommandRegistry.INSTANCE.register(false, CommandSpawnFireworkObject::register);
    }

    public static Set<Identifier> getFireworkIDs() {
        return FIREWORK_DATA.getValues().keySet();
    }

    public static FireworkWrapper getFireworkFromID(Identifier identifier) {
        return FIREWORK_DATA.getValues().getOrDefault(identifier, null);
    }
}
