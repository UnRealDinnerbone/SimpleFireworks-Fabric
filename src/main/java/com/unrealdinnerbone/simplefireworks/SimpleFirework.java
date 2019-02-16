package com.unrealdinnerbone.simplefireworks;

import com.unrealdinnerbone.simplefireworks.api.FireworkObjectWrapper;
import com.unrealdinnerbone.simplefireworks.api.FireworkWrapper;
import com.unrealdinnerbone.simplefireworks.command.spawn.CommandSpawnFireworkObject;
import com.unrealdinnerbone.simplefireworks.data.FireworkDataBase;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;

public class SimpleFirework implements ModInitializer {

    public static final String MOD_ID = "simplefireworks";
    public static final FireworkDataBase<FireworkWrapper> FIREWORK_DATA = new FireworkDataBase<>(FireworkWrapper.class, "fireworks");
    public static final FireworkDataBase<FireworkObjectWrapper> FIREWORK_OBJECT_DATA = new FireworkDataBase<>(FireworkObjectWrapper.class, "firework_objects");

    @Override
    public void onInitialize() {
        System.out.println("Loading SimpleFireworks!");
        ResourceManagerHelper.get(ResourceType.DATA).registerReloadListener(FIREWORK_DATA);
        ResourceManagerHelper.get(ResourceType.DATA).registerReloadListener(FIREWORK_OBJECT_DATA);
        CommandRegistry.INSTANCE.register(false, CommandSpawnFireworkObject::register);
    }

}
