//package com.unrealdinnerbone.simplefireworks.network;
//
//import com.unrealdinnerbone.simplefireworks.SimpleFirework;
//import com.unrealdinnerbone.simplefireworks.lib.firework.FireworkObject;
//import io.netty.buffer.ByteBuf;
//import net.minecraft.network.Packet;
//import net.minecraft.network.listener.PacketListener;
//import net.minecraft.text.TextComponent;
//import net.minecraft.util.PacketByteBuf;
//import net.minecraft.util.math.BlockPos;
//
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//
//public class PacketSpawnFireworkObject implements Packet<PacketSpawnFireworkObject>, PacketListener {
//
//    private CharSequence charSequence;
//    private BlockPos blockPos;
//    private EnumFacing facing;
//
//    private boolean isGood;
//    private FireworkObject object;
//
//    public PacketSpawnFireworkObject() {
//
//    }
//
//
//    public PacketSpawnFireworkObject(CharSequence objectName, BlockPos pos, EnumFacing facing) {
//        this.charSequence = objectName;
//        this.blockPos = pos;
//        this.facing = facing;
//    }
//
//    @Override
//    public void fromBytes(ByteBuf buf) {
//        int length = buf.readInt();
//        charSequence = buf.readCharSequence(length, StandardCharsets.UTF_8);
//        for (FireworkObject fireworkObject : SimpleFirework.getObjectParser().getFireworkObjects()) {
//            if (fireworkObject.getID().equalsIgnoreCase(String.valueOf(charSequence))) {
//                this.object = fireworkObject;
//            }
//        }
//        isGood = object != null;
//        this.blockPos = new BlockPos(buf.readInt(),buf.readInt(),buf.readInt());
//        this.facing = FacingUtil.getFacingFormID(buf.readInt());
//    }
//
//    @Override
//    public void toBytes(ByteBuf buf) {
//        buf.writeInt(charSequence.length());
//        buf.writeCharSequence(charSequence, StandardCharsets.UTF_8);
//        buf.writeInt(blockPos.getX());
//        buf.writeInt(blockPos.getY());
//        buf.writeInt(blockPos.getZ());
//        buf.writeInt(facing.getIndex());
//    }
//
//    public BlockPos getBlockPos() {
//        return blockPos;
//    }
//
//    public FireworkObject getObject() {
//        return object;
//    }
//
//    public boolean isGood() {
//        return isGood;
//    }
//
//    public EnumFacing getFacing() {
//        return facing;
//    }
//
//    @Override
//    public IMessage onMessage(PacketSpawnFireworkObject message, MessageContext ctx) {
//        if (message.isGood()) {
//            EnumFacing facing = message.getFacing();
//            FireworkObject fireworkObject = message.getObject();
//            BlockPos pos = message.getBlockPos().add(0, fireworkObject.getObjectArray().length * 5, 0);
//
//            for (String[] row : fireworkObject.getObjectArray()) {
//                pos = pos.add(0, -5, 0);
//                for (String key : row) {
//                    String fireworkName = fireworkObject.getFireworkNameFormIdentifier(key);
//                    if(fireworkName != null) {
//                        if(SimpleFirework.getFireworkParser().getFireworkObjects().containsKey(fireworkName)) {
//                            FireworkParser.FireworkWrapper wrapper = SimpleFirework.getFireworkParser().getFireworkObjects().get(fireworkName);
//                            wrapper.spawnAllFireworks(pos, 0, 0, 0);
//                        }else {
//                            //Todo Better Message
//                            SimpleFirework.LOG_HELPER.error("Firework is not there " + fireworkName);
//                        }
//                    } else {
//
//                        //Todo Better Message for when key is set but no identifer found
//                        SimpleFirework.LOG_HELPER.debug("No ");
//                    }
//                    pos = pos.add(facing.getFrontOffsetX() * 5, 0, facing.getFrontOffsetZ() * 5);
//                }
//                pos = pos.add(row.length * -5 * facing.getFrontOffsetX(), 0, row.length * -5 * facing.getFrontOffsetZ());
//            }
//        } else {
//            ctx.getClientHandler().handleChat(new SPacketChat(new TextComponentString("Error Test")));
//        }
//        return null;
//    }
//
//    @Override
//    public void read(PacketByteBuf var1) throws IOException {
//
//    }
//
//    @Override
//    public void write(PacketByteBuf var1) throws IOException {
//
//    }
//
//    @Override
//    public void apply(PacketSpawnFireworkObject var1) {
//
//    }
//
//    @Override
//    public void apply(PacketListener var1) {
//
//    }
//
//    @Override
//    public void onConnectionLost(TextComponent var1) {
//
//    }
//}
