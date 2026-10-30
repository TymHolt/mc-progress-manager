package com.tymholt.progressmanager.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

/**
 * The command class for controlling the progress modifiers.
 */
public final class ProgressCommand {

    private ProgressCommand() {}

    /**
     * Register the command. Designed to be called by the plugin class
     * ({@link com.tymholt.progressmanager.McProgressManager}).
     *
     * @param dispatcher Dispatcher to register the command on. Must not be {@code null}.
     */
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("progress")
        );
    }
}
