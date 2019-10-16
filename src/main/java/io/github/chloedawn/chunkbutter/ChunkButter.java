package io.github.chloedawn.chunkbutter;

import com.google.common.base.Preconditions;
import net.minecraft.client.options.BooleanOption;
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
  public static final BooleanOption OPTION = new BooleanOption("options.chunkbutter", o -> isEnabled(), (o, v) -> setEnabled(v));
  public static final Logger LOGGER = LogManager.getLogger("ChunkButter");

  private static final Path CHUNKBUTTER_TXT = Paths.get("chunkbutter.txt");
  private static final String ENABLED = "enabled";

  private static boolean loaded = false;
  private static boolean enabled = true;

  private ChunkButter() {
  }

  @Contract(pure = true)
  public static boolean isEnabled() {
    Preconditions.checkState(loaded, "Not loaded");
    return enabled;
  }

  private static void setEnabled(final boolean value) {
    Preconditions.checkState(loaded, "Not loaded");
    Preconditions.checkState(enabled != value, value ? "Already enabled" : "Already disabled");
    enabled = value;
    writeProperties(CHUNKBUTTER_TXT);
  }

  @Deprecated
  public static void load() {
    Preconditions.checkState(!loaded, "Already loaded");
    readProperties(CHUNKBUTTER_TXT);
    loaded = true;
  }

  private static void readProperties(final Path file) {
    LOGGER.debug("Reading properties from {}", file);

    final Properties properties = new Properties();

    try (final Reader reader = Files.newBufferedReader(file)) {
      properties.load(reader);
    } catch (final NoSuchFileException e) {
      writeProperties(file);
    } catch (final IOException e) {
      throw new RuntimeException("Reading properties from " + file, e);
    } catch (final IllegalArgumentException e) {
      LOGGER.error("Malformed properties in {}", file, e);
      writeProperties(file);
    }

    enabled = Boolean.parseBoolean(properties.getProperty(ENABLED, "true"));
  }

  private static void writeProperties(final Path file) {
    LOGGER.debug("Writing properties to {}", file);

    final Properties properties = new Properties();

    properties.setProperty(ENABLED, Boolean.toString(enabled));

    try (final Writer writer = Files.newBufferedWriter(file)) {
      properties.store(writer, null);
    } catch (final IOException e) {
      throw new RuntimeException("Writing properties to " + file, e);
    }
  }
}
