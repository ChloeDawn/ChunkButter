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

package io.github.chloedawn.chunkbutter.optifine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Contract;
import org.spongepowered.asm.mixin.Mixins;

@Deprecated
public final class OptiFineMixinLoader implements Runnable {
  private static final Logger LOGGER = LogManager.getLogger();

  @Override
  public void run() {
    LOGGER.info("The early rising bird catches the classpath loading worm");
    Mixins.addConfiguration("mixins/chunkbutter/mixins_of.json");
  }

  @Override
  @Contract(pure = true)
  public String toString() {
    return "ChunkButterOptifineMixinLoader";
  }
}
