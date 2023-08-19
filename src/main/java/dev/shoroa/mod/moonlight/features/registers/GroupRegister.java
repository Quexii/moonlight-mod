package dev.shoroa.mod.moonlight.features.registers;

import dev.shoroa.mod.moonlight.Moonlight;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;

public class GroupRegister {
    public static final LinkedHashMap<String, ItemGroup> itemGroups = new LinkedHashMap<>();
    public static ItemGroup register(String groupName, String displayName, Item displayItem) {
        ItemGroup ig = FabricItemGroup.builder().icon(() -> new ItemStack(displayItem)).displayName(Text.translatable(displayName))
                .entries((context,entries) -> {
                    for (Item blockItem : BlockRegister.blockItems) entries.add(new ItemStack(blockItem));
                })
                .build();
        Registry.register(Registries.ITEM_GROUP, new Identifier(Moonlight.MOD_ID, groupName), ig);
        itemGroups.put(groupName, ig);
        return ig;
    }
}
