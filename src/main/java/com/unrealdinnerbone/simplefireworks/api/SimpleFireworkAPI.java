package com.unrealdinnerbone.simplefireworks.api;

import com.unrealdinnerbone.simplefireworks.SimpleFirework;
import com.unrealdinnerbone.simplefireworks.api.firework.Firework;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.server.PlayerStream;
import net.fabricmc.fabric.impl.network.ServerSidePacketRegistryImpl;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.packet.CustomPayloadS2CPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BoundingBox;
import net.minecraft.world.World;
import org.apache.logging.log4j.core.jmx.Server;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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

    public static void spawnFirework(World world, Identifier identifier, BlockPos blockPos, double xSpeed, double ySpeed, double zSpeed) {
        PacketByteBuf bufffer = new PacketByteBuf(Unpooled.buffer());
        bufffer.writeInt(identifier.toString().length());
        bufffer.writeString(identifier.toString());
        bufffer.writeBlockPos(blockPos);
        bufffer.writeDouble(xSpeed);
        bufffer.writeDouble(ySpeed);
        bufffer.writeDouble(zSpeed);
        CustomPayloadS2CPacket customPayloadS2CPacket = new CustomPayloadS2CPacket(SimpleFirework.SPAWN_FIREWORK, bufffer);

        PlayerStream.around(world, blockPos, 50).forEach(playerEntity -> {
            if(playerEntity instanceof ServerPlayerEntity) {
                ((ServerPlayerEntity) playerEntity).networkHandler.sendPacket(customPayloadS2CPacket);
            }
        });

    }
}
