package com.unrealdinnerbone.simplefireworks.lib.firework;

import java.util.Arrays;

public enum EnumFireworkEffect
{
    FLICKER("Flicker", 0),
    TRAIL("Trail", 1);

    private final String effectName;
    private final int explodeID;

    EnumFireworkEffect(String effectName, int id) {
        this.effectName = effectName;
        this.explodeID = id;
    }

    public String getEffectName() {
        return effectName;
    }

    public int getExplodeID() {
        return explodeID;
    }

    public static EnumFireworkEffect getEffectFormID(int id) {
        return Arrays.stream(values()).filter(enumExplodeEffect -> enumExplodeEffect.explodeID == id).findFirst().orElse(null);
    }
}
