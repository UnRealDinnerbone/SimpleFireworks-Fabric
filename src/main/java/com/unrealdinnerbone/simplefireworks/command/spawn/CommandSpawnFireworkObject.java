package com.unrealdinnerbone.simplefireworks.command.spawn;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.unrealdinnerbone.simplefireworks.SimpleFirework;
import com.unrealdinnerbone.simplefireworks.api.SimpleFireworkAPI;
import com.unrealdinnerbone.simplefireworks.api.firework.Firework;
import com.unrealdinnerbone.simplefireworks.lib.FireworkUtils;
import net.minecraft.command.arguments.EntityArgumentType;
import net.minecraft.command.arguments.ResourceLocationArgumentType;
import net.minecraft.command.arguments.Vec3ArgumentType;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandSource;
import net.minecraft.server.command.ServerCommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableTextComponent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.Collection;

public class CommandSpawnFireworkObject {

    public static void register(CommandDispatcher<ServerCommandSource> commandDispatcher) {
        commandDispatcher.register(ServerCommandManager.literal("fireworks")
                .requires((commandSource) -> commandSource.hasPermissionLevel(2))
                .then(ServerCommandManager.literal("spawn")
                        .then(ServerCommandManager.argument("id", ResourceLocationArgumentType.create())
                                .suggests((context, suggestionsBuilder) -> CommandSource.suggestIdentifiers(SimpleFirework.getFireworkIDs(), suggestionsBuilder))
                                .then(ServerCommandManager.argument("pos", Vec3ArgumentType.create())
                                        .executes(CommandSpawnFireworkObject::spawnFirework)
                                        .then(ServerCommandManager.argument("xSpeed", DoubleArgumentType.doubleArg())
                                                .then(ServerCommandManager.argument("ySpeed", DoubleArgumentType.doubleArg())
                                                        .then(ServerCommandManager.argument("zSpeed", DoubleArgumentType.doubleArg())
                                                                .executes(CommandSpawnFireworkObject::spawnFireworkWithSpeed)))))))
                .then(ServerCommandManager.literal("give")
                        .then(ServerCommandManager.argument("player", EntityArgumentType.multiplePlayer())
                            .then(ServerCommandManager.argument("id", ResourceLocationArgumentType.create())
                            .suggests((context, suggestionsBuilder) -> CommandSource.suggestIdentifiers(SimpleFirework.getFireworkIDs(), suggestionsBuilder))
                                    .executes(CommandSpawnFireworkObject::giveFirework)))));
    }

    private static int giveFirework(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        Identifier identifier = ResourceLocationArgumentType.getIdentifierArgument(context,"id");
        Collection<ServerPlayerEntity> serverPlayerEntities = EntityArgumentType.method_9310(context, "player");
        ItemStack itemStack = SimpleFireworkAPI.getFireworkItemStack(SimpleFirework.getFireworkFromID(identifier));
        serverPlayerEntities.forEach(serverPlayerEntity -> serverPlayerEntity.inventory.insertStack(itemStack));
        return 0;
    }

    private static int spawnFireworkWithSpeed(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        BlockPos blockPos = new BlockPos(Vec3ArgumentType.getVec3Argument(context, "pos"));
        Identifier identifier = ResourceLocationArgumentType.getIdentifierArgument(context,"id");
        SimpleFireworkAPI.spawnFirework(context.getSource().getWorld(), identifier, blockPos, DoubleArgumentType.getDouble(context, "xSpeed"), DoubleArgumentType.getDouble(context, "ySpeed"), DoubleArgumentType.getDouble(context, "zSpeed"));
        return 0;
    }

    private static int spawnFirework(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        BlockPos blockPos = new BlockPos(Vec3ArgumentType.getVec3Argument(context, "pos"));
        Identifier identifier = ResourceLocationArgumentType.getIdentifierArgument(context, "id");
        SimpleFireworkAPI.spawnFirework(context.getSource().getWorld(), identifier, blockPos, 1, 1, 1);
        return 0;
    }
}
