package io.github.sleeplessdevelopment.chunkbutter.mixin;

import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.chunk.ChunkRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChunkRenderer.class)
abstract class ChunkRendererMixin {
  private ChunkRendererMixin() {
    throw new AssertionError();
  }

  @Inject(method = "shouldRebuildOnClientThread", at = @At("RETURN"), cancellable = true)
  private void chunkbutter$forceRebuildOffMainThread(final CallbackInfoReturnable<Boolean> cir) {
    cir.setReturnValue(false);
  }
}

@Mixin(WorldRenderer.class)
abstract class WorldRendererMixin {
  private WorldRendererMixin() {
    throw new AssertionError();
  }

  @ModifyConstant(method = "setUpTerrain", constant = @Constant(doubleValue = 768.0))
  private double chunkbutter$reduceMinimumRange(final double range) {
    return 0.0;
  }
}
