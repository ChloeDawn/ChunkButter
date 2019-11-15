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
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.resource.SynchronousResourceReloadListener;
import org.jetbrains.annotations.Contract;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(WorldRenderer.class)
abstract class WorldRendererMixin implements AutoCloseable, SynchronousResourceReloadListener {
	@Contract(pure = true)
	@ModifyConstant(method = "setUpTerrain", constant = @Constant(doubleValue = 768.0), allow = 1)
	private double chunkbutter$reduceMinimumRange(final double range) {
		return ChunkButter.isEnabled() ? 0.0 : range;
	}
}
