package randomdirtdrops.randomdirtdrops;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class BookGenerator {

    private static final Random RANDOM = new Random();

    private BookGenerator() {
    }

    public static ItemStack create(Level world) {

        ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);

        var registry = world.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);

        List<Holder.Reference<Enchantment>> enchantments =
                registry.listElements().toList();

        int enchantmentCount;

        int roll = RANDOM.nextInt(100);

        if (roll < 90) {
            enchantmentCount = 1;
        } else if (roll < 99) {
            enchantmentCount = 2;
        } else {
            enchantmentCount = 3;
        }

        EnchantmentHelper.updateEnchantments(book, mutable -> {

            List<Holder.Reference<Enchantment>> remaining =
                    new ArrayList<>(enchantments);

            List<Holder<Enchantment>> chosen =
                    new ArrayList<>();

            for (int i = 0; i < enchantmentCount && !remaining.isEmpty();) {

                Holder<Enchantment> enchantment =
                        remaining.remove(RANDOM.nextInt(remaining.size()));

                boolean compatible = true;

                for (Holder<Enchantment> existing : chosen) {
                    if (!Enchantment.areCompatible(existing, enchantment)) {
                        compatible = false;
                        break;
                    }
                }

                if (!compatible) {
                    continue;
                }

                int level = RANDOM.nextInt(enchantment.value().getMaxLevel()) + 1;

                mutable.set(enchantment, level);

                chosen.add(enchantment);

                i++;
            }
        });

        return book;
    }
}