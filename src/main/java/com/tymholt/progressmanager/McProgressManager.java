package com.tymholt.progressmanager;

import com.tymholt.progressmanager.command.ProgressCommand;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The main class of this mod.
 */
public class McProgressManager implements ModInitializer {

	/** The Name-ID of this mod. */
	public static final String MOD_ID = "mc-progress-manager";

	private static final boolean DEBUG_FLAG = true; // TODO Make this configurable
	private static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("McProgressManager mod initialize...");

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			ProgressCommand.register(dispatcher);
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
}
