package randomdirtdrops.randomdirtdrops;

import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public final class RandomItemFactory {

    private static final RandomSource RANDOM = RandomSource.create();

    private RandomItemFactory() {
    }

    public static ItemStack createRandomStack(Item item, Level world) {

        if (item == Items.POTION
                || item == Items.SPLASH_POTION
                || item == Items.LINGERING_POTION) {

            if (!Configuration.randomPotionsEnabled()) {
                return new ItemStack(item);
            }

            return PotionGenerator.create(item, world);
        }

        if (item == Items.SUSPICIOUS_STEW) {

            if (!Configuration.randomStewsEnabled()) {
                return new ItemStack(item);
            }

            return StewGenerator.create(new ItemStack(item), world);
        }

        if (item == Items.FIREWORK_ROCKET) {

            if (!Configuration.randomFireworksEnabled()) {
                return new ItemStack(item);
            }

            return FireworkGenerator.create(new ItemStack(item), world);
        }

        if (item == Items.ENCHANTED_BOOK) {

            if (!Configuration.randomBooksEnabled()) {
                return new ItemStack(item);
            }

            return BookGenerator.create(world);
        }

        ItemStack stack = new ItemStack(item);

        if (stack.isEnchantable()
                && RANDOM.nextFloat() < (Configuration.getEnchantChance() / 100.0F)) {

            EnchantmentGenerator.enchant(stack, world);
        }

        return stack;
    }
}