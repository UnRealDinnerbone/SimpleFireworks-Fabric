package com.unrealdinnerbone.simplefireworks.command.spawn;

import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandExceptionType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.util.math.Direction;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class DirectionArgugmentType implements ArgumentType<Direction> {

    private static final Collection<String> EXAMPLES = Arrays.stream(Direction.values()).map(Direction::getName).collect(Collectors.toList());

    public static DirectionArgugmentType create() {
        return new DirectionArgugmentType();
    }

    @Override
    public <S> Direction parse(StringReader reader) throws CommandSyntaxException {
        String theString = reader.readString();
        for (Direction value : Direction.values()) {
            if(theString.equalsIgnoreCase(value.getName())) {
                return value;
            }
        }
        return Direction.DOWN;
//        throw new CommandSyntaxException(new SimpleCommandExceptionType(new LiteralMessage("Unknown Direction")), new LiteralMessage("Unknown Direction"));
    }


    public static Direction getDirection(final CommandContext<?> context, final String name) {
        return context.getArgument(name, Direction.class);
    }


    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(final CommandContext<S> context, final SuggestionsBuilder builder) {
        Arrays.stream(Direction.values()).filter(value -> value.getName().startsWith(builder.getRemaining().toLowerCase())).forEach(value -> builder.suggest(value.getName()));
        return builder.buildFuture();
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }
}
