package com.unrealdinnerbone.simplefireworks.command.spawn;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.unrealdinnerbone.simplefireworks.SimpleFirework;
import com.unrealdinnerbone.simplefireworks.api.SimpleFireworkAPI;
import net.minecraft.command.arguments.EntityArgumentType;
import net.minecraft.command.arguments.IdentifierArgumentType;
import net.minecraft.command.arguments.Vec3ArgumentType;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.CommandSource;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.Collection;

public class CommandSpawnFireworkObject {

    public static void register(CommandDispatcher<ServerCommandSource> commandDispatcher) {
        commandDispatcher.register(CommandManager.literal("fireworks")
                .requires((commandSource) -> commandSource.hasPermissionLevel(2))
                .then(CommandManager.literal("spawn")
                        .then(CommandManager.argument("id", IdentifierArgumentType.identifier())
                                .suggests((context, suggestionsBuilder) -> CommandSource.suggestIdentifiers(SimpleFirework.getFireworkIDs(), suggestionsBuilder))
                                .then(CommandManager.argument("pos", Vec3ArgumentType.vec3())
                                        .executes(CommandSpawnFireworkObject::spawnFirework)
                                        .then(CommandManager.argument("xSpeed", DoubleArgumentType.doubleArg())
                                                .then(CommandManager.argument("ySpeed", DoubleArgumentType.doubleArg())
                                                        .then(CommandManager.argument("zSpeed", DoubleArgumentType.doubleArg())
                                                                .executes(CommandSpawnFireworkObject::spawnFireworkWithSpeed)))))))
                .then(CommandManager.literal("give")
                        .then(CommandManager.argument("player", EntityArgumentType.players())
                                .then(CommandManager.argument("id", IdentifierArgumentType.identifier())
                                        .suggests((context, suggestionsBuilder) -> CommandSource.suggestIdentifiers(SimpleFirework.getFireworkIDs(), suggestionsBuilder))
                                        .executes(CommandSpawnFireworkObject::giveFirework)))));
    }

    private static int giveFirework(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        Identifier identifier = IdentifierArgumentType.getIdentifier(context,"id");
        Collection<ServerPlayerEntity> serverPlayerEntities = EntityArgumentType.getPlayers(context, "player");
        ItemStack itemStack = SimpleFireworkAPI.getFireworkItemStack(SimpleFirework.getFireworkFromID(identifier));
        serverPlayerEntities.forEach(serverPlayerEntity -> serverPlayerEntity.inventory.insertStack(itemStack));
        return 0;
    }

    private static int spawnFireworkWithSpeed(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        BlockPos blockPos = new BlockPos(Vec3ArgumentType.getVec3(context, "pos"));
        Identifier identifier = IdentifierArgumentType.getIdentifier(context,"id");
        SimpleFireworkAPI.spawnFirework(context.getSource().getWorld(), identifier, blockPos, DoubleArgumentType.getDouble(context, "xSpeed"), DoubleArgumentType.getDouble(context, "ySpeed"), DoubleArgumentType.getDouble(context, "zSpeed"));
        return 0;
    }

    private static int spawnFirework(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        BlockPos blockPos = new BlockPos(Vec3ArgumentType.getVec3(context, "pos"));
        Identifier identifier = IdentifierArgumentType.getIdentifier(context, "id");
        SimpleFireworkAPI.spawnFirework(context.getSource().getWorld(), identifier, blockPos, 1, 1, 1);
        return 0;
    }
}
