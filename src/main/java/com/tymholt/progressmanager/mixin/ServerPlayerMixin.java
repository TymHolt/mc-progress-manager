package com.tymholt.progressmanager.mixin;

import com.tymholt.progressmanager.McProgressManager;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.TeleportTransition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * This is a collection of {@code Mixins} for the class {@link ServerPlayer}.
 */
@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin {

    /**
     * A {@code Mixin} blocking teleportation to locked dimensions.
     *
     * @param transition The teleportation info.
     * @param result     The {@code Mixin} result.
     */
    @Inject(method = "teleport", at = @At("HEAD"), cancellable = true)
    private void mcprogressmanager$blockLockedDimensions(TeleportTransition transition,
                                                   CallbackInfoReturnable<ServerPlayer> result) {
        final ServerPlayer player = (ServerPlayer) (Object) this;
        final ResourceKey<Level> dimension = transition.newLevel().dimension();
        McProgressManager.logDebug(String.format("Player %s teleporting to dimension %s",
                player.getGameProfile().name(), dimension.identifier()));
    }
}
