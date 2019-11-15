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

package io.github.chloedawn.chunkbutter;

import com.google.common.base.Preconditions;
import net.minecraft.client.options.BooleanOption;
import net.minecraft.client.options.Option;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Contract;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public final class ChunkButter {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final Path FILE = Paths.get("chunkbutter.txt");
	private static final String ENABLED = "enabled";

	private static boolean loaded = false;
	private static boolean enabled = true;

	private ChunkButter() {
	}

	@Contract(pure = true)
	public static Option getOption() {
		checkLoaded();
		return OptionHolder.OPTION;
	}

	@Contract(pure = true)
	public static boolean isEnabled() {
		checkLoaded();
		return enabled;
	}

	private static void setEnabled(final boolean value) {
		checkLoaded();
		Preconditions.checkState(enabled != value, value ? "Already enabled" : "Already disabled");
		enabled = value;
		writePropertiesToFile();
	}

	@Deprecated
	public static void load() {
		Preconditions.checkState(!loaded, "Already loaded");
		readPropertiesFromFile();
		loaded = true;
	}

	private static void checkLoaded() {
		Preconditions.checkState(loaded, "Not loaded");
	}

	private static void readPropertiesFromFile() {
		LOGGER.debug("Reading properties from {}", FILE);

		final Properties properties = new Properties();

		try (final Reader reader = Files.newBufferedReader(FILE)) {
			properties.load(reader);
		} catch (final NoSuchFileException e) {
			writePropertiesToFile();
		} catch (final IOException e) {
			throw new RuntimeException("Reading properties from " + FILE, e);
		} catch (final IllegalArgumentException e) {
			LOGGER.error("Malformed properties in {}", FILE, e);
			writePropertiesToFile();
		}

		enabled = Boolean.parseBoolean(properties.getProperty(ENABLED, "true"));
	}

	private static void writePropertiesToFile() {
		LOGGER.debug("Writing properties to {}", FILE);

		final Properties properties = new Properties();

		properties.setProperty(ENABLED, Boolean.toString(enabled));

		try (final Writer writer = Files.newBufferedWriter(FILE)) {
			properties.store(writer, null);
		} catch (final IOException e) {
			throw new RuntimeException("Writing properties to " + FILE, e);
		}
	}

	private static final class OptionHolder {
		private static final BooleanOption OPTION = new BooleanOption("options.chunkbutter", o -> isEnabled(), (o, v) -> setEnabled(v));
	}
}
