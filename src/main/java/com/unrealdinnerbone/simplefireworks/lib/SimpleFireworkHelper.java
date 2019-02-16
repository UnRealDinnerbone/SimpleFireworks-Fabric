package com.unrealdinnerbone.simplefireworks.lib;

import com.unrealdinnerbone.simplefireworks.SimpleFirework;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class SimpleFireworkHelper
{
    public static void spawnFireworkObject(Identifier identifier, BlockPos pos, Direction direction) {
        System.out.println("SPAWN PACKET" + identifier.toString()  + pos.toString() + direction.getName());
        SimpleFirework.FIREWORK_DATA.getValues().forEach((identifier1, fireworkWrapper) -> {
            if(identifier.toString().equalsIgnoreCase(identifier1.toString())) {
                fireworkWrapper.spawnAllFireworks(pos, 1, 1, 1);
            }
        });
    }
}
