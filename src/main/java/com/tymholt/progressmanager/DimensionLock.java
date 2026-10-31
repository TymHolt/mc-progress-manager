package com.tymholt.progressmanager;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

/**
 * A collection of lockable dimensions.
 */
public enum DimensionLock {

    /** Lock state for the nether dimension. */
    NETHER(Level.NETHER),

    /** Lock state for the end dimension. */
    END(Level.END);

    private final ResourceKey<Level> level;
    private boolean lockState = false;

    DimensionLock(ResourceKey<Level> level) {
        this.level = level;
    }

    /**
     * Set the lock of this dimension.
     *
     * @param lockState The new state. {@code True} if players should be prevented from entering.
     */
    public void setLocked(boolean lockState) {
        this.lockState = lockState;
    }

    /**
     * @return If this dimension is locked and prevents players from entering.
     */
    public boolean isLocked() {
        return this.lockState;
    }

    /**
     * Test if a given dimension, represented by the internal {@link Level}, is locked. If a {@link Level} is not
     * tracked in this enum, the default is not locked ({@code false}).
     *
     * @param  level The level to look for. Must not be {@code null}.
     * @return       {@code true} if the dimension is tracked and locked, {@code false} if not locked or tracked here.
     */
    public static boolean isDimensionLocked(ResourceKey<Level> level) {
        for (DimensionLock dimensionLock : DimensionLock.values())
            if (dimensionLock.level.equals(level))
                return dimensionLock.isLocked();

        return false;
    }

    /**
     * Get the dimension name of the given level. Either returns the enum identifier or the level identifier, depending
     * on if the dimension is known by this mod.
     *
     * @param  level The level to look for. Must not be {@code null}.
     * @return      The name of the given level, never {@code null}.
     */
    public static String getDimensionName(ResourceKey<Level> level) {
        for (DimensionLock dimensionLock : DimensionLock.values())
            if (dimensionLock.level.equals(level))
                return dimensionLock.name();

        return level.identifier().toString();
    }

    /**
     * Apply the given {@link ModConfig} to the dimension lock settings.
     *
     * @param config The configuration to apply. Must not be {@code null}.
     */
    public static void readFromConfig(ModConfig config) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Update the given {@link ModConfig} with the dimension lock settings.
     *
     * @param config The configuration to update. Must not be {@code null}.
     */
    public static void writeToConfig(ModConfig config) {
        throw new RuntimeException("Not implemented");
    }
}
