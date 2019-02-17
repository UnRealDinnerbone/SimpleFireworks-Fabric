package com.unrealdinnerbone.simplefireworks;

import com.unrealdinnerbone.simplefireworks.api.FireworkWrapper;
import com.unrealdinnerbone.simplefireworks.api.firework.Firework;
import com.unrealdinnerbone.simplefireworks.command.spawn.CommandSpawnFireworkObject;
import com.unrealdinnerbone.simplefireworks.data.FireworkDataBase;
import com.unrealdinnerbone.simplefireworks.lib.FireworkUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.impl.network.ServerSidePacketRegistryImpl;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class SimpleFireworkAPI {

    public static ItemStack getFireworkItemStack(FireworkWrapper fireworkWrapper) {
        ItemStack itemStack = new ItemStack(Items.FIREWORK_ROCKET);
        Firework[] fireworks = new Firework[fireworkWrapper.getFireworks().size()];
        fireworks = fireworkWrapper.getFireworks().toArray(fireworks);
        CompoundTag compoundTag = getExplosionCompound(1, fireworks);
        CompoundTag theTag = itemStack.getOrCreateTag();
        theTag.put("Fireworks", compoundTag);
        return itemStack;
    }

    public static CompoundTag getExplosionCompound(int height, Firework... fireworks)  {
        CompoundTag compound = new CompoundTag();
        if(height < 0 || height > 3) {
            System.out.println("Invalid Height must be (1-3)");
            height = 1;
        }
        compound.putInt("Flight", height);
        ListTag nbtList = Arrays.stream(fireworks).map(SimpleFireworkAPI::getRawExplosionCompound).collect(Collectors.toCollection(ListTag::new));
        compound.put("Explosions", nbtList);
        return compound;
    }

    public static CompoundTag getRawExplosionCompound(Firework firework) {
        CompoundTag explosionsCompound = new CompoundTag();
        explosionsCompound.putInt("Type", firework.getExplodedEffect().getExplodeID() - 1);
        firework.getFireworkEffects().forEach(effect -> explosionsCompound.putInt(effect.getEffectName(), 1));
        explosionsCompound.putIntArray("Colors",  firework.getBrustColors().stream().mapToInt(Integer::intValue).toArray());
        explosionsCompound.putIntArray("FadeColors", firework.getFadeColors().stream().mapToInt(Integer::intValue).toArray());
        return explosionsCompound;
    }

    public static ItemStack getFireworkItemStack(Firework firework) {
        ItemStack itemStack = new ItemStack(Items.FIREWORK_ROCKET);
        CompoundTag compoundTag = getExplosionCompound(1, firework);
        CompoundTag theTag = itemStack.getOrCreateTag();
        theTag.put("Fireworks", compoundTag);
        return itemStack;
    }


    @Environment(EnvType.CLIENT)
    public static void spawnFirework(Firework firework, BlockPos pos, double xSpeed, double ySpeed, double zSpeed) {
        MinecraftClient.getInstance().world.addFireworkParticle(pos.getX(), pos.getY(), pos.getZ(), xSpeed, ySpeed, zSpeed, getExplosionCompound(1, firework));
    }
    @Environment(EnvType.CLIENT)
    public static void spawnFirework(FireworkWrapper fireworkWrapper, BlockPos pos, double xSpeed, double ySpeed, double zSpeed) {
        Firework[] fireworks = new Firework[fireworkWrapper.getFireworks().size()];
        fireworks = fireworkWrapper.getFireworks().toArray(fireworks);
        MinecraftClient.getInstance().world.addFireworkParticle(pos.getX(), pos.getY(), pos.getZ(), xSpeed, ySpeed, zSpeed, getExplosionCompound(1, fireworks));
    }

    public static void spawnFirework(Identifier identifier, BlockPos blockPos, double xSpeed, double ySpeed, double zSpeed) {
        MinecraftClient.getInstance().player.inventory.insertStack(getFireworkItemStack(SimpleFirework.getFireworkFromID(identifier)));
        SimpleFirework.getFireworkFromID(identifier).spawnAllFireworks(blockPos, xSpeed, ySpeed, zSpeed);
    }
}
