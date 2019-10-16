package io.github.chloedawn.chunkbutter.mixin;

import io.github.chloedawn.chunkbutter.ChunkButter;
import net.minecraft.client.render.chunk.ChunkRenderer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChunkRenderer.class)
abstract class ChunkRendererMixin {
  @Contract(mutates = "param")
  @Inject(method = "shouldRebuildOnClientThread", at = @At("RETURN"), cancellable = true)
  private void chunkbutter$forceRebuildOffMainThread(final @NotNull CallbackInfoReturnable<Boolean> cir) {
    if (ChunkButter.isEnabled()) {
      cir.setReturnValue(false);
    }
  }
}
