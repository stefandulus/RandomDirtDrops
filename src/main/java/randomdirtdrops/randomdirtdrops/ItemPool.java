package randomdirtdrops.randomdirtdrops;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class ItemPool {

    private static final List<Item> ITEMS = new ArrayList<>();
    private static final Random RANDOM = new Random();

    private ItemPool() {
    }

    public static void initialize() {

        ITEMS.clear();

        for (Item item : BuiltInRegistries.ITEM) {

            if (Configuration.isExcludedItem(item)) {
                continue;
            }

            ITEMS.add(item);
        }

        RandomDirtDrops.LOGGER.info(
                "Loaded {} items into random drop pool.",
                ITEMS.size()
        );
    }

    public static Item getRandomItem() {
        return ITEMS.get(RANDOM.nextInt(ITEMS.size()));
    }
}