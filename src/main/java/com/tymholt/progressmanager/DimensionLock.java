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
     * @param level The level to look for.
     * @return {@code true} if the dimension is tracked and locked, {@code false} if not locked or not tracked here.
     */
    public static boolean isDimensionLocked(ResourceKey<Level> level) {
        for (DimensionLock dimensionLock : DimensionLock.values())
            if (dimensionLock.level.equals(level))
                return dimensionLock.isLocked();

        return false;
    }
}
