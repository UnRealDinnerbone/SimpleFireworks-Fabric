package com.unrealdinnerbone.simplefireworks.api.firework;

import java.util.Arrays;

public enum FireworkEffect
{
    FLICKER("Flicker", 0),
    TRAIL("Trail", 1);

    private final String effectName;
    private final int explodeID;

    FireworkEffect(String effectName, int id) {
        this.effectName = effectName;
        this.explodeID = id;
    }

    public String getEffectName() {
        return effectName;
    }

    public int getExplodeID() {
        return explodeID;
    }

    public static FireworkEffect getEffectFormID(int id) {
        return Arrays.stream(values()).filter(enumExplodeEffect -> enumExplodeEffect.explodeID == id).findFirst().orElse(null);
    }
}
