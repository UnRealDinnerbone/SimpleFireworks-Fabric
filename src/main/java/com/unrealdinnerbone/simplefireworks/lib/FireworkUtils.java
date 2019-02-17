package com.unrealdinnerbone.simplefireworks.lib;

import com.unrealdinnerbone.simplefireworks.SimpleFirework;
import com.unrealdinnerbone.simplefireworks.api.FireworkWrapper;
import com.unrealdinnerbone.simplefireworks.api.firework.Firework;
import com.unrealdinnerbone.simplefireworks.api.firework.FireworkObject;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.Arrays;
import java.util.stream.Collectors;

public class FireworkUtils
{
    public static CompoundTag getExplosionCompound(int height, Firework... fireworks)  {
        CompoundTag compound = new CompoundTag();
        if(height < 0 || height > 3) {
            System.out.println("Invalid Height must be (1-3)");
            height = 1;
        }
        compound.putInt("Flight", height);
        ListTag nbtList = Arrays.stream(fireworks).map(FireworkUtils::getRawExplosionCompound).collect(Collectors.toCollection(ListTag::new));
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


    //    {Fireworks:{Explosions:[{Type:1,Flicker:1,Colors:[I;2651799],FadeColors:[I;2651799]}],Flight:1}}
    //[{Type:3,Colors:[I;16777215,10123010],FadeColors:[I;16777215]}],Flicker:1b}}
    public static ItemStack getFireworkItemStack(Firework firework) {
        ItemStack itemStack = new ItemStack(Items.FIREWORK_ROCKET);
        CompoundTag compoundTag = getExplosionCompound(1, firework);
        CompoundTag theTag = itemStack.getOrCreateTag();
        theTag.put("Fireworks", compoundTag);
        return itemStack;
    }
    public static ItemStack getFireworkItemStack(FireworkWrapper fireworkWrapper) {
        ItemStack itemStack = new ItemStack(Items.FIREWORK_ROCKET);
        Firework[] fireworks = new Firework[fireworkWrapper.getFireworks().size()];
        fireworks = fireworkWrapper.getFireworks().toArray(fireworks);
        CompoundTag compoundTag = getExplosionCompound(1, fireworks);
        CompoundTag theTag = itemStack.getOrCreateTag();
        theTag.put("Fireworks", compoundTag);
        return itemStack;
    }

    @Environment(EnvType.CLIENT)
    public static void  spawnFirework(Firework firework, BlockPos pos, double xSpeed, double ySpeed, double zSpeed) {
        MinecraftClient.getInstance().world.addFireworkParticle(pos.getX(), pos.getY(), pos.getZ(), xSpeed, ySpeed, zSpeed, getExplosionCompound(1, firework));
    }
    @Environment(EnvType.CLIENT)
    public static void spawnFirework(FireworkWrapper fireworkWrapper, BlockPos pos, double xSpeed, double ySpeed, double zSpeed) {
        Firework[] fireworks = new Firework[fireworkWrapper.getFireworks().size()];
        fireworks = fireworkWrapper.getFireworks().toArray(fireworks);
        MinecraftClient.getInstance().world.addFireworkParticle(pos.getX(), pos.getY(), pos.getZ(), xSpeed, ySpeed, zSpeed, getExplosionCompound(1, fireworks));
    }

    public static void spawnFireworkShape(Identifier identifier, BlockPos blockPos, Direction direction, double xSpeed, double ySpeed, double zSpeed) {
        FireworkObject fireworkObject = SimpleFirework.getFireworkShapeFromID(identifier);
        BlockPos pos = blockPos.add(0, fireworkObject.getObjectArray().length * 5, 0);
        for (String[] row : fireworkObject.getObjectArray()) {
            pos = pos.add(0, -5, 0);
            for (String key : row) {
                String fireworkIdentifier = fireworkObject.getFireworkNameFormIdentifier(key);
                if (fireworkIdentifier != null) {
                    spawnFirework(new Identifier(fireworkIdentifier), pos, xSpeed, ySpeed, zSpeed);
                }
                pos = pos.add(direction.getOffsetX() * 5, 0, direction.getOffsetZ() * 5);
            }
            pos = pos.add(row.length * -5 * direction.getOffsetX(), 0, row.length * -5 * direction.getOffsetZ());
        }
    }

    public static void spawnFirework(Identifier identifier, BlockPos blockPos, double xSpeed, double ySpeed, double zSpeed) {
        MinecraftClient.getInstance().player.inventory.insertStack(getFireworkItemStack(SimpleFirework.getFireworkFromID(identifier)));
        SimpleFirework.getFireworkFromID(identifier).spawnAllFireworks(blockPos, xSpeed, ySpeed, zSpeed);
    }
}
