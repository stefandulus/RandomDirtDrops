package randomdirtdrops.randomdirtdrops;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

public final class RandomLootHandler {

    private RandomLootHandler() {
    }

    public static void initialize() {

        LootTableEvents.MODIFY_DROPS.register((holder, context, drops) -> {

            if (!context.hasParameter(LootContextParams.BLOCK_STATE)) {
                return;
            }

            BlockState state = context.getParameter(LootContextParams.BLOCK_STATE);
            Block block = state.getBlock();

            if (!Configuration.isAllBlocksMode()
                    && !Configuration.isTriggerBlock(block)) {
                return;
            }

            drops.clear();

            Item randomItem = ItemPool.getRandomItem();

            ItemStack stack = RandomItemFactory.createRandomStack(
                    randomItem,
                    context.getLevel()
            );

            drops.add(stack);
        });
    }
}