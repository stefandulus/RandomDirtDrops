package randomdirtdrops.randomdirtdrops;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.alchemy.Potions;

import java.util.List;
import java.util.Random;

public final class PotionGenerator {

    private static final Random RANDOM = new Random();

    private PotionGenerator() {
    }

    public static ItemStack create(Item item, Level world) {

        var registry = world.registryAccess().lookupOrThrow(Registries.POTION);

        List<Holder.Reference<Potion>> potions =
                registry.listElements()
                        .filter(potion ->
                                !potion.value().getEffects().isEmpty()
                                        || potion.is(Potions.WATER))
                        .toList();

        Holder<Potion> potion =
                potions.get(RANDOM.nextInt(potions.size()));

        return PotionContents.createItemStack(item, potion);
    }
}