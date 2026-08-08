package randomdirtdrops.randomdirtdrops;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Random;

public final class TippedArrowGenerator {

    private static final Random RANDOM = new Random();

    private TippedArrowGenerator() {
    }

    public static ItemStack create(Level world) {

        var registry = world.registryAccess().lookupOrThrow(Registries.POTION);

        List<Holder.Reference<Potion>> potions =
                registry.listElements()
                        .filter(potion -> !potion.value().getEffects().isEmpty())
                        .toList();

        Holder<Potion> potion =
                potions.get(RANDOM.nextInt(potions.size()));

        return PotionContents.createItemStack(
                Items.TIPPED_ARROW,
                potion
        );
    }
}