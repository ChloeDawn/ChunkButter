/*
 * Copyright (C) 2019 Chloe Dawn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
