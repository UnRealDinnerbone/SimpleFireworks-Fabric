package com.unrealdinnerbone.simplefireworks;

import com.unrealdinnerbone.simplefireworks.api.FireworkWrapper;
import com.unrealdinnerbone.simplefireworks.api.Firework;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.network.PacketContext;
import net.fabricmc.fabric.impl.network.ClientSidePacketRegistryImpl;
import net.minecraft.entity.player.PlayerEntity;
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
        spawnFirework(packetContext.getPlayer(), SimpleFirework.getFireworkFromID(new Identifier(id)), blockPos, xSpeed, ySpeed, zSpeed);
    }

    private void spawnFirework(PlayerEntity playerEntity, FireworkWrapper fireworkWrapper, BlockPos pos, double xSpeed, double ySpeed, double zSpeed) {
        Firework[] fireworks = new Firework[fireworkWrapper.getFireworks().size()];
        fireworks = fireworkWrapper.getFireworks().toArray(fireworks);
        playerEntity.world.addFireworkParticle(pos.getX(), pos.getY(), pos.getZ(), xSpeed, ySpeed, zSpeed, getExplosionCompound(1, fireworks));
    }

    @Override
    public void onInitializeClient() {
        System.out.println("Loading SimpleFireworks Client!");
        ClientSidePacketRegistryImpl.INSTANCE.register(SPAWN_FIREWORK, this::handlePacket);

    }
}
