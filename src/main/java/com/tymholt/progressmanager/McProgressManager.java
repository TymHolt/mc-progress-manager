package com.tymholt.progressmanager;

import com.tymholt.progressmanager.command.DimensionLockCommand;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * The main class of this mod.
 */
public class McProgressManager implements ModInitializer {

	/** The Name-ID of this mod. */
	public static final String MOD_ID = "mc-progress-manager";

	private static final boolean DEBUG_FLAG = true; // TODO Make this configurable
	private static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	private static final ModConfig CONFIG = new ModConfig();

	@Override
	public void onInitialize() {
		LOGGER.info("McProgressManager mod initialize...");

        try {
            McProgressManager.CONFIG.load();
        } catch (IOException exception) {
			logError("Could not load config: " + exception.getMessage());
        }

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			DimensionLockCommand.register(dispatcher);
		});

		ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
			try {
				McProgressManager.CONFIG.save();
			} catch (IOException exception) {
				logError("Could not save config: " + exception.getMessage());
			}
		});
	}

	/**
	 * @return If this mod is configured in debug mode.
	 */
	public static boolean inDebugMode() {
		return McProgressManager.DEBUG_FLAG; // TODO Make this configurable
	}

	/**
	 * Log a given message with the mods logging backend.
	 *
	 * @param message The message to log. May be {@code null}.
	 */
	public static void logInfo(String message) {
		if (message == null)
			message = "null";

		McProgressManager.LOGGER.info(message);
	}

	/**
	 * Log a given message with the mods logging backend, but only when the mod is configured for debugging, see
	 * {@link #inDebugMode()}.
	 *
	 * @param message The message to log. May be {@code null}.
	 */
	public static void logDebug(String message) {
		if (McProgressManager.inDebugMode())
			logInfo(message);
	}

	/**
	 * Log a given message with the mods logging backend.
	 *
	 * @param message The message to log. May be {@code null}.
	 */
	public static void logError(String message) {
		if (message == null)
			message = "null";

		McProgressManager.LOGGER.error(message);
	}

	/**
	 * @return The mod configuration instance.
	 */
	public static ModConfig getConfig() {
		return McProgressManager.CONFIG;
	}
}
