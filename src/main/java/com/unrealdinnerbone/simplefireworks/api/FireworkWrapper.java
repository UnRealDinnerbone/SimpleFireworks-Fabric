package com.unrealdinnerbone.simplefireworks.api;

import com.unrealdinnerbone.simplefireworks.lib.firework.FireworkBase;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class FireworkWrapper {

    private List<FireworkBase> fireworks;

    public List<FireworkBase> getFireworks() {
        return fireworks;
    }

    public void spawnAllFireworks(BlockPos pos, int xSpeed, int ySpeed, int zSpeed) {
        fireworks.forEach(firework -> firework.spawnFirework(pos, xSpeed, ySpeed, zSpeed));
    }
}
