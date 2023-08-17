package dev.shoroa.mod.moonlight;

import dev.shoroa.mod.moonlight.features.registers.BlockRegister;
import dev.shoroa.mod.moonlight.features.registers.GroupRegister;
import dev.shoroa.mod.moonlight.features.registers.ResourceRegister;
import dev.shoroa.mod.moonlight.util.item.ItemFactory;
import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.time.format.TextStyle;
import java.util.Map;

public class Moonlight implements ModInitializer {
    public static final String MOD_ID = "moonlight";
    public static final Logger logger = LogManager.getLogger(MOD_ID);
    public static final RuntimeResourcePack rrp = RuntimeResourcePack.create(MOD_ID);
    @Override
    public void onInitialize() {
        final Item ITEM_GROUP_ICON = Registry.register(Registries.ITEM, new Identifier(MOD_ID,"item_group_icon"),
                ItemFactory.itemWithTooltip(
                        Text.literal("This item is for the creative tab").setStyle(Style.EMPTY.withColor(0xc73f38)),
                        Text.literal("You probably shouldn't have this!").setStyle(Style.EMPTY.withColor(0xc73f38))
                ));
        ResourceRegister.registerItemModel("item_group_icon", "item/generated", Map.entry("layer0", "item_group_icon"));

        BlockRegister.register();
        GroupRegister.register("item_group", "Moonlight", ITEM_GROUP_ICON);
        ResourceRegister.register();
        RRPCallback.EVENT.register(e -> e.add(rrp));
        rrp.dump(new File("test"));
    }
}
