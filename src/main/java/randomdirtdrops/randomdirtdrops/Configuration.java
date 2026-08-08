package randomdirtdrops.randomdirtdrops;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

public final class Configuration {

    private static final Path CONFIG_DIRECTORY =
            Path.of("config", "randomdirtdrops");

    private static final Path CONFIG_FILE =
            CONFIG_DIRECTORY.resolve("config.properties");

    private static final Path BLOCKS_FILE =
            CONFIG_DIRECTORY.resolve("blocks.txt");

    private static final Path EXCLUDED_ITEMS_FILE =
            CONFIG_DIRECTORY.resolve("excluded_items.txt");

    private static final Path README_FILE =
            CONFIG_DIRECTORY.resolve("README.txt");

    private static final Properties PROPERTIES = new Properties();

    private static final Set<Block> TRIGGER_BLOCKS = new HashSet<>();

    private static final Set<Item> EXCLUDED_ITEMS = new HashSet<>();

    private static boolean allBlocksMode = false;

    private static int enchantChance = 10;
    private static int minEnchantPower = 20;
    private static int maxEnchantPower = 40;

    private static boolean randomBooks = true;
    private static boolean randomPotions = true;
    private static boolean randomFireworks = true;
    private static boolean randomStews = true;
    private static boolean randomArrows = true;

    private Configuration() {
    }

    public static void initialize() {

        try {

            Files.createDirectories(CONFIG_DIRECTORY);

            if (Files.notExists(CONFIG_FILE)) {

                createDefaultConfig();

                RandomDirtDrops.LOGGER.info(
                        "Created default config.properties"
                );
            }

            if (Files.notExists(BLOCKS_FILE)) {

                createDefaultBlocks();

                RandomDirtDrops.LOGGER.info(
                        "Created default blocks.txt"
                );
            }

            if (Files.notExists(EXCLUDED_ITEMS_FILE)) {

                createDefaultExcludedItems();

                RandomDirtDrops.LOGGER.info(
                        "Created default excluded_items.txt"
                );
            }

            if (Files.notExists(README_FILE)) {

                createReadme();

                RandomDirtDrops.LOGGER.info(
                        "Created default README.txt"
                );
            }

            try (InputStream input = Files.newInputStream(CONFIG_FILE)) {

                PROPERTIES.load(input);

                allBlocksMode = Boolean.parseBoolean(
                        PROPERTIES.getProperty("allBlocksMode", "false")
                );

                enchantChance = Integer.parseInt(
                        PROPERTIES.getProperty("enchantChance", "10")
                );

                minEnchantPower = Integer.parseInt(
                        PROPERTIES.getProperty("minEnchantPower", "20")
                );

                maxEnchantPower = Integer.parseInt(
                        PROPERTIES.getProperty("maxEnchantPower", "40")
                );

                randomBooks = Boolean.parseBoolean(
                        PROPERTIES.getProperty("enableRandomBooks", "true")
                );

                randomPotions = Boolean.parseBoolean(
                        PROPERTIES.getProperty("enableRandomPotions", "true")
                );

                randomFireworks = Boolean.parseBoolean(
                        PROPERTIES.getProperty("enableRandomFireworks", "true")
                );

                randomStews = Boolean.parseBoolean(
                        PROPERTIES.getProperty("enableRandomStews", "true")
                );

                randomArrows = Boolean.parseBoolean(
                        PROPERTIES.getProperty("enableRandomArrows", "true")
                );
            }

            loadTriggerBlocks();
            loadExcludedItems();

        } catch (IOException e) {

            RandomDirtDrops.LOGGER.error(
                    "Failed to load configuration!",
                    e
            );

        }

        RandomDirtDrops.LOGGER.info(
                "Configuration directory: {}",
                CONFIG_DIRECTORY.toAbsolutePath()
        );

        RandomDirtDrops.LOGGER.info(
                "Loaded {} trigger blocks.",
                TRIGGER_BLOCKS.size()
        );

        RandomDirtDrops.LOGGER.info(
                "Loaded {} excluded items.",
                EXCLUDED_ITEMS.size()
        );

        RandomDirtDrops.LOGGER.info("All Blocks Mode: {}", allBlocksMode);
        RandomDirtDrops.LOGGER.info("Enchant Chance: {}", enchantChance);
        RandomDirtDrops.LOGGER.info("Random Books: {}", randomBooks);
        RandomDirtDrops.LOGGER.info("Random Potions: {}", randomPotions);
        RandomDirtDrops.LOGGER.info("Random Fireworks: {}", randomFireworks);
        RandomDirtDrops.LOGGER.info("Random Stews: {}", randomStews);
        RandomDirtDrops.LOGGER.info("Random Arrows: {}", randomArrows);
    }

    private static void createDefaultConfig() throws IOException {

        String config = """
        allBlocksMode=false

        enchantChance=10
        minEnchantPower=20
        maxEnchantPower=40

        enableRandomBooks=true
        enableRandomPotions=true
        enableRandomFireworks=true
        enableRandomStews=true
        enableRandomArrows=true
        """;

        Files.writeString(CONFIG_FILE, config);
    }

    private static void createDefaultBlocks() throws IOException {

        String blocks = """
                minecraft:dirt
                minecraft:grass_block
                """;

        Files.writeString(BLOCKS_FILE, blocks);
    }

    private static void createDefaultExcludedItems() throws IOException {

        String items = """
                minecraft:debug_stick
                minecraft:knowledge_book
                minecraft:written_book
                minecraft:command_block
                minecraft:chain_command_block
                minecraft:repeating_command_block
                minecraft:command_block_minecart
                minecraft:jigsaw
                minecraft:structure_block
                minecraft:structure_void
                minecraft:test_block
                minecraft:test_instance_block
                """;

        Files.writeString(EXCLUDED_ITEMS_FILE, items);
    }

    private static void createReadme() throws IOException {

        String readme = """
===========================================================
Random Dirt Drops Configuration Guide
===========================================================

This folder contains every configurable option of the mod.

-----------------------------------------------------------
config.properties
-----------------------------------------------------------

allBlocksMode
    false = Only blocks listed in blocks.txt drop random items.
    true  = Every block in the game drops random items.

enchantChance
    Chance (0-100) for enchantable items to receive enchantments.
    Recommended: 10

minEnchantPower
    Minimum enchanting power.
    Recommended: 20

maxEnchantPower
    Maximum enchanting power.
    Recommended: 40

enableRandomBooks
    true  = Enchanted books receive random enchantments.
    false = Enchanted books stay blank.

enableRandomPotions
    true  = Potions receive random effects.
    false = Potions stay normal.

enableRandomFireworks
    true  = Fireworks receive random colors and explosions.
    false = Fireworks stay normal.

enableRandomStews
    true  = Suspicious stews receive random effects.
    false = Suspicious stews stay normal.
    
enableRandomArrows
    true  = Tipped arrows receive random potion effects.
    false = Tipped arrows stay normal.

-----------------------------------------------------------
blocks.txt
-----------------------------------------------------------

One block ID per line.

Example:

minecraft:dirt
minecraft:grass_block
minecraft:sand

Lines beginning with # are ignored.

Block IDs are NOT case-sensitive.

Duplicate entries are ignored.

-----------------------------------------------------------
excluded_items.txt
-----------------------------------------------------------

One item ID per line.

Example:

minecraft:debug_stick
minecraft:command_block
minecraft:written_book

Items listed here will never appear as random drops.

Lines beginning with # are ignored.

Item IDs are NOT case-sensitive.

Duplicate entries are ignored.
""";

        Files.writeString(README_FILE, readme);
    }

    private static void loadTriggerBlocks() throws IOException {

        TRIGGER_BLOCKS.clear();

        List<String> lines = Files.readAllLines(BLOCKS_FILE);

        for (String line : lines) {

            line = line.trim().toLowerCase();

            if (line.isEmpty() || line.startsWith("#")) {
                continue;
            }

            try {

                Identifier id = Identifier.parse(line);

                Optional<Block> block =
                        BuiltInRegistries.BLOCK.getOptional(id);

                if (block.isPresent()) {

                    TRIGGER_BLOCKS.add(block.get());

                } else {

                    RandomDirtDrops.LOGGER.warn(
                            "Unknown block in blocks.txt: {}",
                            line
                    );
                }

            } catch (Exception e) {

                RandomDirtDrops.LOGGER.warn(
                        "Invalid block ID in blocks.txt: {}",
                        line
                );
            }
        }
    }

    private static void loadExcludedItems() throws IOException {

        EXCLUDED_ITEMS.clear();

        List<String> lines = Files.readAllLines(EXCLUDED_ITEMS_FILE);

        for (String line : lines) {

            line = line.trim().toLowerCase();

            if (line.isEmpty() || line.startsWith("#")) {
                continue;
            }

            try {

                Identifier id = Identifier.parse(line);

                Optional<Item> item =
                        BuiltInRegistries.ITEM.getOptional(id);

                if (item.isPresent()) {

                    EXCLUDED_ITEMS.add(item.get());

                } else {

                    RandomDirtDrops.LOGGER.warn(
                            "Unknown item in excluded_items.txt: {}",
                            line
                    );
                }

            } catch (Exception e) {

                RandomDirtDrops.LOGGER.warn(
                        "Invalid item ID in excluded_items.txt: {}",
                        line
                );
            }
        }
    }

    public static boolean isTriggerBlock(Block block) {
        return TRIGGER_BLOCKS.contains(block);
    }

    public static boolean isExcludedItem(Item item) {
        return EXCLUDED_ITEMS.contains(item);
    }

    public static boolean isAllBlocksMode() {
        return allBlocksMode;
    }

    public static int getEnchantChance() {
        return enchantChance;
    }

    public static int getMinEnchantPower() {
        return minEnchantPower;
    }

    public static int getMaxEnchantPower() {
        return maxEnchantPower;
    }

    public static boolean randomBooksEnabled() {
        return randomBooks;
    }

    public static boolean randomPotionsEnabled() {
        return randomPotions;
    }

    public static boolean randomFireworksEnabled() {
        return randomFireworks;
    }

    public static boolean randomStewsEnabled() {
        return randomStews;
    }

    public static boolean randomArrowsEnabled() {
        return randomArrows;
    }
}