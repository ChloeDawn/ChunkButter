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

  @ModifyArg(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/ButtonListWidget;addAll([Lnet/minecraft/client/options/Option;)V"))
  private Option[] chunkbutter$appendOption(final Option[] options) {
    final Option[] newOptions = new Option[options.length + 1];
    System.arraycopy(options, 0, newOptions, 0, options.length);
    newOptions[options.length] = ChunkButter.OPTION;
    return newOptions;
  }
}
