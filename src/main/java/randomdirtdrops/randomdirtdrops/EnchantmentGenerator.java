package randomdirtdrops.randomdirtdrops;

import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;

import java.util.Optional;

public final class  EnchantmentGenerator {

    private static final RandomSource RANDOM = RandomSource.create();

    private EnchantmentGenerator() {
    }

    public static void enchant(ItemStack stack, Level world) {

        int min = Configuration.getMinEnchantPower();
        int max = Configuration.getMaxEnchantPower();

        // Prevent invalid config values from crashing the game
        if (min > max) {
            int temp = min;
            min = max;
            max = temp;
        }

        int enchantingPower = min + RANDOM.nextInt(max - min + 1);

        EnchantmentHelper.enchantItem(
                RANDOM,
                stack,
                enchantingPower,
                world.registryAccess(),
                Optional.empty()
        );
    }
}