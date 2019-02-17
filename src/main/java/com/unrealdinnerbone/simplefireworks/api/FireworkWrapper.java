package com.unrealdinnerbone.simplefireworks.api;

import com.unrealdinnerbone.simplefireworks.api.firework.Firework;
import com.unrealdinnerbone.simplefireworks.lib.FireworkUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class FireworkWrapper {

    private List<Firework> fireworks;

    public List<Firework> getFireworks() {
        return fireworks;
    }

    public void spawnAllFireworks(BlockPos pos, double xSpeed, double ySpeed, double zSpeed) {
        fireworks.forEach(firework -> FireworkUtils.spawnFirework(firework, pos, xSpeed, ySpeed, zSpeed));
    }
}
