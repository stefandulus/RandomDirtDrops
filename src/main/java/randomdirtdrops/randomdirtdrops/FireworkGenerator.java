package randomdirtdrops.randomdirtdrops;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.FireworkExplosion;
import net.minecraft.world.item.component.Fireworks;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class FireworkGenerator {

    private static final Random RANDOM = new Random();

    private FireworkGenerator() {
    }

    public static ItemStack create(ItemStack stack, Level world) {

        int flight = RANDOM.nextInt(3) + 1;

        int roll = RANDOM.nextInt(100);

        int explosionCount;

        if (roll < 70) {
            explosionCount = 1;
        } else if (roll < 90) {
            explosionCount = 2;
        } else if (roll < 98) {
            explosionCount = 3;
        } else {
            explosionCount = RANDOM.nextInt(2) + 4;
        }

        List<FireworkExplosion> explosions = new ArrayList<>();

        FireworkExplosion.Shape[] shapes = FireworkExplosion.Shape.values();
        DyeColor[] colors = DyeColor.values();

        for (int i = 0; i < explosionCount; i++) {

            IntArrayList mainColors = new IntArrayList();
            IntArrayList fadeColors = new IntArrayList();

            int mainCount = RANDOM.nextInt(4) + 1;

            for (int j = 0; j < mainCount; j++) {
                mainColors.add(
                        colors[RANDOM.nextInt(colors.length)].getFireworkColor()
                );
            }

            int fadeCount = RANDOM.nextInt(4);

            for (int j = 0; j < fadeCount; j++) {
                fadeColors.add(
                        colors[RANDOM.nextInt(colors.length)].getFireworkColor()
                );
            }

            FireworkExplosion explosion = new FireworkExplosion(
                    shapes[RANDOM.nextInt(shapes.length)],
                    mainColors,
                    fadeColors,
                    RANDOM.nextBoolean(),
                    RANDOM.nextBoolean()
            );

            explosions.add(explosion);
        }

        stack.set(
                DataComponents.FIREWORKS,
                new Fireworks(flight, explosions)
        );

        return stack;
    }
}