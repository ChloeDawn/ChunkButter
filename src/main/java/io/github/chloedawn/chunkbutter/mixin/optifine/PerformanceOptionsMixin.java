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

package io.github.chloedawn.chunkbutter.mixin.optifine;

import io.github.chloedawn.chunkbutter.ChunkButter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.options.Option;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "net.optifine.gui.GuiPerformanceSettingsOF", remap = false)
abstract class PerformanceOptionsMixin extends Screen {
  @Shadow(remap = false)
  private static Option[] enumOptions;

  PerformanceOptionsMixin() {
    super(null);
  }

  @Inject(method = "Lnet/optifine/gui/GuiPerformanceSettingsOF;init()V", at = @At("TAIL"))
  private void chunkbutter$appendOption(final CallbackInfo ci) {
    final int i = enumOptions.length;
    final int x = this.width / 2 - 155 + i % 2 * 160;
    final int y = this.height / 6 + 21 * (i / 2) - 12;
    this.addButton(ChunkButter.OPTION.createButton(MinecraftClient.getInstance().options, x, y, 150));
  }
}
