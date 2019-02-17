package com.unrealdinnerbone.simplefireworks;

import com.unrealdinnerbone.simplefireworks.api.FireworkShapeWrapper;
import com.unrealdinnerbone.simplefireworks.api.FireworkWrapper;
import com.unrealdinnerbone.simplefireworks.command.spawn.CommandSpawnFireworkObject;
import com.unrealdinnerbone.simplefireworks.data.FireworkDataBase;
import com.unrealdinnerbone.simplefireworks.api.firework.FireworkObject;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.impl.network.ServerSidePacketRegistryImpl;
import net.minecraft.client.network.packet.CustomPayloadS2CPacket;
import net.minecraft.resource.ResourceType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

//Todo Packets,
public class SimpleFirework implements ModInitializer {

    public static final String MOD_ID = "simplefireworks";
    private static final FireworkDataBase<FireworkWrapper> FIREWORK_DATA = new FireworkDataBase<>(FireworkWrapper.class, "fireworks");
    private static final FireworkDataBase<FireworkShapeWrapper> FIREWORK_OBJECT_DATA = new FireworkDataBase<>(FireworkShapeWrapper.class, "firework_objects");

    private static final Identifier SPAWN_FIREWORK = new Identifier(MOD_ID, "spawn_firework");

    @Override
    public void onInitialize() {
        System.out.println("Loading SimpleFireworks!");
        ResourceManagerHelper.get(ResourceType.DATA).registerReloadListener(FIREWORK_DATA);
        ResourceManagerHelper.get(ResourceType.DATA).registerReloadListener(FIREWORK_OBJECT_DATA);
        CommandRegistry.INSTANCE.register(false, CommandSpawnFireworkObject::register);
        ServerSidePacketRegistryImpl.INSTANCE.register(SPAWN_FIREWORK, (packetContext, packetByteBuf) -> {
            System.out.println("Spawn Firework!");
        });
    }

    public static void spawnFirework(ServerPlayerEntity entity, Identifier identifier, BlockPos blockPos, double xSpeed, double ySpeed, double zSpeed) {
        PacketByteBuf packetByteBuf = new PacketByteBuf(Unpooled.buffer());
        packetByteBuf.writeIdentifier(identifier);
        packetByteBuf.writeBlockPos(blockPos);
        packetByteBuf.writeDouble(xSpeed);
        packetByteBuf.writeDouble(ySpeed);
        packetByteBuf.writeDouble(zSpeed);
        CustomPayloadS2CPacket packet = new CustomPayloadS2CPacket(SPAWN_FIREWORK, packetByteBuf);
        entity.networkHandler.sendPacket(packet);
    }


    public static Set<Identifier> getFireworkIDs() {
        return FIREWORK_DATA.getValues().keySet();
    }

    public static FireworkWrapper getFireworkFromID(Identifier identifier) {
        return FIREWORK_DATA.getValues().getOrDefault(identifier, null);
    }

    public static Set<Identifier> getFireworkShapesIDs() {
        Set<Identifier> identifiers = new HashSet<>();
        FIREWORK_OBJECT_DATA.getValues().keySet().forEach(identifier -> FIREWORK_OBJECT_DATA.getValues().get(identifier).getFireworkObjects().stream().map(fireworkObject -> new Identifier(identifier.getNamespace(), identifier.getPath() + "/" + fireworkObject.getID().toLowerCase())).forEach(identifiers::add));
        return identifiers;
    }

    public static FireworkObject getFireworkShapeFromID(Identifier identifiert) {
        HashMap<Identifier, FireworkObject> identifiers = new HashMap<>();
        FIREWORK_OBJECT_DATA.getValues().keySet().forEach(identifier -> FIREWORK_OBJECT_DATA.getValues().get(identifier).getFireworkObjects().forEach(fireworkObject -> identifiers.put(new Identifier(identifier.getNamespace(), identifier.getPath() + "/" + fireworkObject.getID().toLowerCase()), fireworkObject)));
        return identifiers.getOrDefault(identifiert, null);
    }

}
