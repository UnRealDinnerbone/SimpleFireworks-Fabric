package com.unrealdinnerbone.simplefireworks.command.spawn;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.unrealdinnerbone.simplefireworks.SimpleFirework;
import com.unrealdinnerbone.simplefireworks.lib.FireworkUtils;
import net.minecraft.command.arguments.ResourceLocationArgumentType;
import net.minecraft.command.arguments.Vec3ArgumentType;
import net.minecraft.server.command.CommandSource;
import net.minecraft.server.command.ServerCommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableTextComponent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

@SuppressWarnings("ALL")
public class CommandSpawnFireworkObject {

    private static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslatableTextComponent("commands.summon.failed"));

    public static void register(CommandDispatcher<ServerCommandSource> commandDispatcher) {
        commandDispatcher.register(ServerCommandManager.literal("spawnfirework")
                .requires((commandSource) -> commandSource.hasPermissionLevel(2))
                .then(ServerCommandManager.literal("firework")
                    .then(ServerCommandManager.argument("id", ResourceLocationArgumentType.create())
                    .suggests((context, suggestionsBuilder) -> CommandSource.suggestIdentifiers(SimpleFirework.getFireworkIDs(), suggestionsBuilder))
                            .then(ServerCommandManager.argument("pos", Vec3ArgumentType.create())
                                .executes(CommandSpawnFireworkObject::spawnFirework)
                                .then(ServerCommandManager.argument("xSpeed", DoubleArgumentType.doubleArg())
                                    .then(ServerCommandManager.argument("ySpeed", DoubleArgumentType.doubleArg())
                                        .then(ServerCommandManager.argument("zSpeed", DoubleArgumentType.doubleArg())
                                            .executes(CommandSpawnFireworkObject::spawnFireworkWithSpeed)))))))
                .then(ServerCommandManager.literal("shape")
                    .then(ServerCommandManager.argument("id", ResourceLocationArgumentType.create())
                    .suggests((context, suggestionsBuilder) -> CommandSource.suggestIdentifiers(SimpleFirework.getFireworkShapesIDs(), suggestionsBuilder))
                            .then(ServerCommandManager.argument("direction", DirectionArgugmentType.create())
                                    .then(ServerCommandManager.argument("pos", Vec3ArgumentType.create())
                                            .executes(CommandSpawnFireworkObject::spawnShapefirework)
                                            .then(ServerCommandManager.argument("xSpeed", DoubleArgumentType.doubleArg())
                                                .then(ServerCommandManager.argument("ySpeed", DoubleArgumentType.doubleArg())
                                                    .then(ServerCommandManager.argument("zSpeed", DoubleArgumentType.doubleArg())
                                                    .executes(CommandSpawnFireworkObject::spawnShapefireworkWithSpeed)))))))));
    }

    private static int spawnFireworkWithSpeed(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        BlockPos blockPos = new BlockPos(Vec3ArgumentType.getVec3Argument(context, "pos"));
        Identifier identifier = ResourceLocationArgumentType.getIdentifierArgument(context, "id");
        FireworkUtils.spawnFirework(identifier, blockPos, DoubleArgumentType.getDouble(context, "xSpeed"), DoubleArgumentType.getDouble(context, "ySpeed"), DoubleArgumentType.getDouble(context, "zSpeed"));
        return 0;
    }

    private static int spawnFirework(CommandContext<ServerCommandSource> context) throws CommandSyntaxException{
        BlockPos blockPos = new BlockPos(Vec3ArgumentType.getVec3Argument(context, "pos"));
        Identifier identifier = ResourceLocationArgumentType.getIdentifierArgument(context, "id");
        FireworkUtils.spawnFirework(identifier, blockPos, 1, 1, 1);
        return 0;
    }

    private static int spawnShapefirework(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        BlockPos blockPos = new BlockPos(Vec3ArgumentType.getVec3Argument(context, "pos"));
        Identifier identifier = ResourceLocationArgumentType.getIdentifierArgument(context, "id");
        Direction direction = DirectionArgugmentType.getDirection(context, "direction");
        FireworkUtils.spawnFireworkShape(identifier, blockPos, direction, 1, 1, 1);
        return 0;
    }
    private static int spawnShapefireworkWithSpeed(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        BlockPos blockPos = new BlockPos(Vec3ArgumentType.getVec3Argument(context, "pos"));
        Identifier identifier = ResourceLocationArgumentType.getIdentifierArgument(context, "id");
        Direction direction = DirectionArgugmentType.getDirection(context, "direction");
        FireworkUtils.spawnFireworkShape(identifier, blockPos, direction, DoubleArgumentType.getDouble(context, "xSpeed"), DoubleArgumentType.getDouble(context, "ySpeed"), DoubleArgumentType.getDouble(context, "zSpeed"));
        return 0;
    }
}
