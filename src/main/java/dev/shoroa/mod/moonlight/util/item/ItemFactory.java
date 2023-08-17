package dev.shoroa.mod.moonlight.util.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static dev.shoroa.mod.moonlight.Moonlight.MOD_ID;

public class ItemFactory {
    public static Item itemWithTooltip(Text... texts) {
        return new Item(new FabricItemSettings()) {
            @Override
            public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
                tooltip.addAll(List.of(texts));
            }
        };
    }
}
