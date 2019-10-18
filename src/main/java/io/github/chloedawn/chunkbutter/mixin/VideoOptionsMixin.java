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
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.VideoOptionsScreen;
import net.minecraft.client.options.Option;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(VideoOptionsScreen.class)
abstract class VideoOptionsMixin extends Screen {
  VideoOptionsMixin() {
    super(null);
  }

  // TODO Replace lack of requirement with @Group implementation once cross-mixin groups are supported
  @ModifyArg(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/ButtonListWidget;addAll([Lnet/minecraft/client/options/Option;)V"), require = 0)
  private Option[] chunkbutter$appendOption(final Option[] options) {
    final Option[] newOptions = new Option[options.length + 1];
    System.arraycopy(options, 0, newOptions, 0, options.length);
    newOptions[options.length] = ChunkButter.OPTION;
    return newOptions;
  }
}
