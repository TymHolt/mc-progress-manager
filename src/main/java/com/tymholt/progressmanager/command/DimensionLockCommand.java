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

    private static final MutableComponent LOCKED = Component.literal("LOCKED").withStyle(ChatFormatting.RED);
    private static final MutableComponent UNLOCKED = Component.literal("UNLOCKED").withStyle(ChatFormatting.GREEN);
    private static final int SUCCESS = 1;

    private static MutableComponent getStatusComponent(boolean locked) {
        return locked ? DimensionLockCommand.LOCKED : DimensionLockCommand.UNLOCKED;
    }

    private DimensionLockCommand() {}

    /**
     * Register the command. Designed to be called by the plugin class
     * ({@link com.tymholt.progressmanager.McProgressManager}).
     *
     * @param dispatcher Dispatcher to register the command on. Must not be {@code null}.
     */
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        final LiteralArgumentBuilder<CommandSourceStack> dimlockCommand = Commands.literal("dimlock");
        dimlockCommand.then(Commands.literal("status").executes(new StatusRunner()));

        final LiteralArgumentBuilder<CommandSourceStack> lockSubCommand = Commands.literal("lock");
        for (DimensionLock dimensionLock : DimensionLock.values()) {
            final String name = dimensionLock.name().toLowerCase();
            final LiteralArgumentBuilder<CommandSourceStack> dimensionLiteral = Commands.literal(name);
            dimensionLiteral.executes(new LockRunner(dimensionLock));
            lockSubCommand.then(dimensionLiteral);
        }
        dimlockCommand.then(lockSubCommand);

        final LiteralArgumentBuilder<CommandSourceStack> unlockSubCommand = Commands.literal("unlock");
        for (DimensionLock dimensionLock : DimensionLock.values()) {
            final String name = dimensionLock.name().toLowerCase();
            final LiteralArgumentBuilder<CommandSourceStack> dimensionLiteral = Commands.literal(name);
            dimensionLiteral.executes(new UnlockRunner(dimensionLock));
            unlockSubCommand.then(dimensionLiteral);
        }
        dimlockCommand.then(unlockSubCommand);

        dispatcher.register(dimlockCommand);
    }

    private static class StatusRunner implements Command<CommandSourceStack> {

        @Override
        public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
            for (DimensionLock dimensionLock : DimensionLock.values()) {
                final MutableComponent messageComponent = Component.literal(dimensionLock.name() + " is ");
                final MutableComponent stateComponent = getStatusComponent(dimensionLock.isLocked());
                context.getSource().sendSuccess(() -> messageComponent.append(stateComponent), false);
            }

            return DimensionLockCommand.SUCCESS;
        }
    }

    private static class LockRunner implements Command<CommandSourceStack> {

        private final DimensionLock dimensionLock;

        LockRunner(DimensionLock dimensionLock) {
            this.dimensionLock = dimensionLock;
        }

        @Override
        public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
            this.dimensionLock.setLocked(true);
            final MutableComponent messageComponent = Component.literal(dimensionLock.name() + " is now ");
            final MutableComponent stateComponent = getStatusComponent(dimensionLock.isLocked());
            context.getSource().sendSuccess(() -> messageComponent.append(stateComponent), false);
            return DimensionLockCommand.SUCCESS;
        }
    }

    private static class UnlockRunner implements Command<CommandSourceStack> {

        private final DimensionLock dimensionLock;

        UnlockRunner(DimensionLock dimensionLock) {
            this.dimensionLock = dimensionLock;
        }

        @Override
        public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
            this.dimensionLock.setLocked(false);
            final MutableComponent messageComponent = Component.literal(dimensionLock.name() + " is now ");
            final MutableComponent stateComponent = getStatusComponent(dimensionLock.isLocked());
            context.getSource().sendSuccess(() -> messageComponent.append(stateComponent), false);
            return DimensionLockCommand.SUCCESS;
        }
    }
}
