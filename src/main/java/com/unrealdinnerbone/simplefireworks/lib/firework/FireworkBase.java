package com.unrealdinnerbone.simplefireworks.lib.firework;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.ModInfo;
import net.minecraft.client.MinecraftClient;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.command.TagCommand;
import net.minecraft.util.math.BlockPos;


import java.util.ArrayList;
import java.util.List;

public class FireworkBase {

    private List<EnumFireworkEffect> fireworkEffects;
    private EnumExplodeEffect explodeEffect;
    private List<Integer> colors;
    private List<Integer> fadeColors;

    public FireworkBase() {
        fireworkEffects = new ArrayList<>();
        explodeEffect = null;
        colors = new ArrayList<>();
        fadeColors = new ArrayList<>();
    }

    public void setFireworkEffects(List<EnumFireworkEffect> fireworkEffects) {
        this.fireworkEffects = fireworkEffects;
    }

    public void addFireworkEffect(EnumFireworkEffect fireworkEffect) {
        if (!this.fireworkEffects.contains(fireworkEffect)) {
            this.fireworkEffects.add(fireworkEffect);
        }
    }

    public void removeFireworkEffect(EnumFireworkEffect fireworkEffect) {
        this.fireworkEffects.remove(fireworkEffect);
    }

    public void setExplodeEffect(EnumExplodeEffect explodeEffect) {
        this.explodeEffect = explodeEffect;
    }

    public void setColors(List<Integer> colors) {
        this.colors = colors;
    }

    public void addColor(Integer fireworkColor) {
        if (!this.colors.contains(fireworkColor)) {
            this.colors.add(fireworkColor);
        }
    }

    public void removeColor(Integer fireworkColor) {
        this.colors.remove(fireworkColor);
    }


    public void setFadeColors(List<Integer> fadeColors) {
        this.fadeColors = fadeColors;
    }

    public void addFadeColor(Integer fireworkColor) {
        if (!this.fadeColors.contains(fireworkColor)) {
            this.fadeColors.add(fireworkColor);
        }
    }

    public void removeFadeColor(Integer fireworkColor) {
        this.fadeColors.remove(fireworkColor);
    }

    public List<EnumFireworkEffect> getFireworkEffects() {
        return fireworkEffects;
    }

    public EnumExplodeEffect getExplodedEffect() {
        return explodeEffect;
    }

    public List<Integer> getBrustColors() {
        return colors;
    }

    public List<Integer> getFadeColors() {
        return fadeColors;
    }

    public CompoundTag getExplodeCompound()  {
        CompoundTag compound = new CompoundTag();
        //Todo do i need this?
//        compound.setInteger("Flight", 1);
        CompoundTag explosionsCompound = new CompoundTag();
        explosionsCompound.putInt("Type", getExplodedEffect().getExplodeID() - 1);
        this.getFireworkEffects().forEach(effect -> explosionsCompound.putInt(effect.getEffectName(), 1));
        explosionsCompound.putIntArray("Colors",  getBrustColors().stream().mapToInt(Integer::intValue).toArray());
        explosionsCompound.putIntArray("FadeColors", getFadeColors().stream().mapToInt(Integer::intValue).toArray());
        ListTag nbtList = new ListTag();
        nbtList.add(explosionsCompound);
        compound.put("Explosions", nbtList);
        return compound;
    }

    @Environment(EnvType.CLIENT)
    public void spawnFirework(BlockPos pos, int xSpeed, int ySpeed, int zSpeed) {
        MinecraftClient.getInstance().world.addFireworkParticle(pos.getX(), pos.getY(), pos.getZ(), xSpeed, ySpeed, zSpeed, getExplodeCompound());
    }
}




