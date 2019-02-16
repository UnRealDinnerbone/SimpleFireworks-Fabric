package com.unrealdinnerbone.simplefireworks.command.spawn;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.unrealdinnerbone.simplefireworks.SimpleFirework;
import com.unrealdinnerbone.simplefireworks.lib.SimpleFireworkHelper;
import net.minecraft.command.arguments.ResourceLocationArgumentType;
import net.minecraft.command.arguments.Vec3ArgumentType;
import net.minecraft.server.command.CommandSource;
import net.minecraft.server.command.ServerCommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.StringTextComponent;
import net.minecraft.text.TranslatableTextComponent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class CommandSpawnFireworkObject {

    private static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslatableTextComponent("commands.summon.failed"));

    public static void register(CommandDispatcher<ServerCommandSource> commandDispatcher) {
        commandDispatcher.register(ServerCommandManager.literal("spawnfirework")
                .requires((commandSource) -> commandSource.hasPermissionLevel(2))
                    .then(ServerCommandManager.argument("id", ResourceLocationArgumentType.create())
                        .suggests((context, suggestionsBuilder) -> CommandSource.suggestIdentifiers(SimpleFirework.FIREWORK_DATA.getValues().keySet(), suggestionsBuilder))
                            .then(ServerCommandManager.argument("pos", Vec3ArgumentType.create())
                                .then(ServerCommandManager.argument("direction", DirectionArgugmentType.create())
                                    .executes(CommandSpawnFireworkObject::onCommandSpawn)))));
    }

    private static int onCommandSpawn(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        BlockPos blockPos = new BlockPos(Vec3ArgumentType.getVec3Argument(context, "pos"));
        Identifier identifier = ResourceLocationArgumentType.getIdentifierArgument(context, "id");
        Direction direction = DirectionArgugmentType.getDirection(context, "direction");
        SimpleFireworkHelper.spawnFireworkObject(identifier, blockPos, direction);
        return 0;
    }
}
