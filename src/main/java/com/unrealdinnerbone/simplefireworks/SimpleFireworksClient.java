package com.unrealdinnerbone.simplefireworks;

import com.unrealdinnerbone.simplefireworks.api.FireworkWrapper;
import com.unrealdinnerbone.simplefireworks.api.firework.Firework;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.network.PacketContext;
import net.fabricmc.fabric.impl.network.ClientSidePacketRegistryImpl;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

import static com.unrealdinnerbone.simplefireworks.api.SimpleFireworkAPI.getExplosionCompound;

public class SimpleFireworksClient implements ClientModInitializer {

    public static final Identifier SPAWN_FIREWORK = new Identifier(SimpleFirework.MOD_ID, "spawn_firework");

    private void handlePacket(PacketContext packetContext, PacketByteBuf packetByteBuf) {
        int l = packetByteBuf.readInt();
        String id = packetByteBuf.readString(l);
        BlockPos blockPos = packetByteBuf.readBlockPos();
        double xSpeed = packetByteBuf.readDouble();
        double ySpeed = packetByteBuf.readDouble();
        double zSpeed = packetByteBuf.readDouble();
        spawnFirework(SimpleFirework.getFireworkFromID(new Identifier(id)), blockPos, xSpeed, ySpeed, zSpeed);
    }

    private void spawnFirework(Firework firework, BlockPos pos, double xSpeed, double ySpeed, double zSpeed) {
        MinecraftClient.getInstance().world.addFireworkParticle(pos.getX(), pos.getY(), pos.getZ(), xSpeed, ySpeed, zSpeed, getExplosionCompound(1, firework));
    }

    private void spawnFirework(FireworkWrapper fireworkWrapper, BlockPos pos, double xSpeed, double ySpeed, double zSpeed) {
        Firework[] fireworks = new Firework[fireworkWrapper.getFireworks().size()];
        fireworks = fireworkWrapper.getFireworks().toArray(fireworks);
        MinecraftClient.getInstance().world.addFireworkParticle(pos.getX(), pos.getY(), pos.getZ(), xSpeed, ySpeed, zSpeed, getExplosionCompound(1, fireworks));
    }

    @Override
    public void onInitializeClient() {
        ClientSidePacketRegistryImpl.INSTANCE.register(SPAWN_FIREWORK, this::handlePacket);

    }
}
