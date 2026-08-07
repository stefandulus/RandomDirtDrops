package randomdirtdrops.randomdirtdrops;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Random;

public final class StewGenerator {

    private static final Random RANDOM = new Random();

    private static final Holder<MobEffect>[] EFFECTS = new Holder[]{
            MobEffects.SPEED,
            MobEffects.SLOWNESS,
            MobEffects.HASTE,
            MobEffects.MINING_FATIGUE,
            MobEffects.STRENGTH,
            MobEffects.INSTANT_HEALTH,
            MobEffects.JUMP_BOOST,
            MobEffects.NAUSEA,
            MobEffects.REGENERATION,
            MobEffects.RESISTANCE,
            MobEffects.FIRE_RESISTANCE,
            MobEffects.WATER_BREATHING,
            MobEffects.INVISIBILITY,
            MobEffects.BLINDNESS,
            MobEffects.NIGHT_VISION,
            MobEffects.HUNGER,
            MobEffects.WEAKNESS,
            MobEffects.POISON,
            MobEffects.WITHER,
            MobEffects.HEALTH_BOOST,
            MobEffects.ABSORPTION,
            MobEffects.SATURATION,
            MobEffects.GLOWING,
            MobEffects.LEVITATION,
            MobEffects.LUCK,
            MobEffects.UNLUCK,
            MobEffects.SLOW_FALLING,
            MobEffects.CONDUIT_POWER,
            MobEffects.DOLPHINS_GRACE,
            MobEffects.HERO_OF_THE_VILLAGE,
            MobEffects.DARKNESS,
            MobEffects.BREATH_OF_THE_NAUTILUS
    };

    private StewGenerator() {
    }

    public static ItemStack create(ItemStack stack, Level world) {

        Holder<MobEffect> effect =
                EFFECTS[RANDOM.nextInt(EFFECTS.length)];

        int durationTicks = (RANDOM.nextInt(26) + 5) * 20;

        SuspiciousStewEffects.Entry entry =
                new SuspiciousStewEffects.Entry(effect, durationTicks);

        stack.set(
                DataComponents.SUSPICIOUS_STEW_EFFECTS,
                new SuspiciousStewEffects(List.of(entry))
        );

        return stack;
    }
}