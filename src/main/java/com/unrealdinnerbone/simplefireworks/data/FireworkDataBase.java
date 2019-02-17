package com.unrealdinnerbone.simplefireworks.data;

import com.google.gson.Gson;
import com.unrealdinnerbone.simplefireworks.SimpleFirework;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class FireworkDataBase<T> implements SimpleSynchronousResourceReloadListener {

    private final String type;
    private final Class<T> tClass;
    private final Identifier ID;
    private final HashMap<Identifier, T> REGISTERED_VALUES = new HashMap<>();

    private final static Gson GSON = new Gson();

    public FireworkDataBase(Class<T> tClass, String type) {
        this.type = type;
        this.tClass = tClass;
        this.ID = new Identifier(SimpleFirework.MOD_ID, type);
    }

    @Override
    public Identifier getFabricId() {
        return ID;
    }

    @Override
    public void apply(ResourceManager resourceManager) {
        REGISTERED_VALUES.clear();
        resourceManager.findResources(type, name -> name.endsWith(".json")).forEach(identifier -> {
            try {
                Resource resource = resourceManager.getResource(identifier);
                String json = IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
                T tObject = GSON.fromJson(json, tClass);
                String theID = identifier.toString();
                Identifier theNewOne = new Identifier(theID.replace(".json", "").replace(type + "/", ""));
                REGISTERED_VALUES.put(theNewOne, tObject);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public HashMap<Identifier, T> getValues() {
        return REGISTERED_VALUES;
    }

}
