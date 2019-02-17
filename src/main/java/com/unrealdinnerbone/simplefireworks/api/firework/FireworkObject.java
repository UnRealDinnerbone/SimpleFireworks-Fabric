package com.unrealdinnerbone.simplefireworks.lib.firework;

import net.minecraft.util.Identifier;

import java.util.HashMap;

public class FireworkObject {

    private String id;
    private HashMap<String, String> identifiers;
    private String[][] objects;


    public void setIdentifiers(HashMap<String, String> identifiers) {
        this.identifiers = identifiers;
    }

    public void setObjects(String[][] objects) {
        this.objects = objects;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getID() {
        return id;
    }

    public HashMap<String, String> getIdentifiers() {
        return identifiers;
    }

    public String[][] getObjectArray() {
        return objects;
    }

    public String getFireworkNameFormIdentifier(String identifier) {
        for(String key: identifiers.keySet()) {
            if(key.equalsIgnoreCase(identifier)) {
                return identifiers.get(key);
            }
        }
        return null;
    }
}
