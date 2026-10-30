package com.tymholt.progressmanager.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.tymholt.progressmanager.DimensionLock;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

/**
 * The command class for controlling the progress modifiers.
 */
public final class DimensionLockCommand {

    private static final int SUCCESS = 1;

    private DimensionLockCommand() {}

    /**
     * Register the command. Designed to be called by the plugin class
     * ({@link com.tymholt.progressmanager.McProgressManager}).
     *
     * @param dispatcher Dispatcher to register the command on. Must not be {@code null}.
     */
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        final LiteralArgumentBuilder<CommandSourceStack> progressCommand =
                Commands.literal("dimlock").then(
                    Commands.literal("status").executes(new ProgressStatusRunner()));

        dispatcher.register(progressCommand);
    }

    private static class ProgressStatusRunner implements Command<CommandSourceStack> {

        private static final MutableComponent LOCKED = Component.literal("Locked").withStyle(ChatFormatting.RED);
        private static final MutableComponent UNLOCKED = Component.literal("Unlocked").withStyle(ChatFormatting.GREEN);

        @Override
        public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
            for (DimensionLock dimensionLock : DimensionLock.values()) {
                final MutableComponent nameComponent = Component.literal(dimensionLock.name());
                final MutableComponent stateComponent = dimensionLock.isLocked() ? LOCKED : UNLOCKED;
                context.getSource().sendSuccess(() -> nameComponent.append(stateComponent), false);
            }

            return DimensionLockCommand.SUCCESS;
        }
    }
}
