package dev.shoroa.mod.moonlight.features.registers;

import dev.shoroa.mod.moonlight.Moonlight;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.blockstate.JBlockModel;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.blockstate.JVariant;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.models.JTextures;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.awt.geom.Area;
import java.util.Map;

import static dev.shoroa.mod.moonlight.Moonlight.MOD_ID;

public class ResourceRegister {
    public static final RuntimeResourcePack rrp = Moonlight.rrp;

    public static void register() {
        for (Triplet<String, String, Block> pair : BlockRegister.blocks) {
            if(pair.getValue0().endsWith("_log")) {
                registerLogBlockState(pair.getValue0());
                registerBlockModel(pair.getValue0(), "block/cube_column",getTextures(EntryTextureType.SIDE_END,pair.getValue0(),pair.getValue0() + "_top"));
                registerBlockModel(pair.getValue0() + "_horizontal", "block/cube_column_horizontal",getTextures(EntryTextureType.SIDE_END,pair.getValue0(),pair.getValue0() + "_top"));
            } else if (pair.getValue0().endsWith("_stairs")) {
                registerStairBlockState(pair.getValue0());
                registerBlockModel(pair.getValue0(), "block/stairs",getTextures(EntryTextureType.BOTTOM_TOP_SIDE,pair.getValue1()));
                registerBlockModel(pair.getValue0()+"_inner", "block/inner_stairs",getTextures(EntryTextureType.BOTTOM_TOP_SIDE,pair.getValue1()));
                registerBlockModel(pair.getValue0()+"_outer", "block/outer_stairs",getTextures(EntryTextureType.BOTTOM_TOP_SIDE,pair.getValue1()));
            } else if (pair.getValue0().endsWith("_slab")) {
                registerSlabBlockState(pair.getValue0(),pair.getValue0().replace("slab", "planks"),pair.getValue0()+"_top");
                registerBlockModel(pair.getValue0(), "block/slab",getTextures(EntryTextureType.BOTTOM_TOP_SIDE,pair.getValue1()));
                registerBlockModel(pair.getValue0()+"_top", "block/slab_top",getTextures(EntryTextureType.BOTTOM_TOP_SIDE,pair.getValue1()));
            } else {
                registerBlockState(pair.getValue0());
                registerBlockModel(pair.getValue0(), "block/cube_all",
                        Map.entry("side", pair.getValue0()),Map.entry("all", pair.getValue0())
                );
            }
        }
    }

    @SafeVarargs
    private static void registerBlockModel(String name, String parent, Map.Entry<String ,String>...textures) {
        JModel model = JModel.model(parent);
        JTextures jtextures = JModel.textures();
        for (Map.Entry val : textures) {
            jtextures.var((String) val.getKey(), MOD_ID+":block/"+ val.getValue());
        }
        model.textures(jtextures);
        rrp.addModel(model, new Identifier(MOD_ID,"block/"+name));
        rrp.addModel(JModel.model(MOD_ID+":block/"+name), new Identifier(MOD_ID,"item/"+name));
    }
    public static void registerItemModel(String name, String parent, Map.Entry<String ,String>...textures) {
        JModel model = JModel.model(parent);
        JTextures jtextures = JModel.textures();
        for (Map.Entry val : textures) {
            jtextures.var((String) val.getKey(), MOD_ID+":item/"+ val.getValue());
        }
        model.textures(jtextures);
        rrp.addModel(model, new Identifier(MOD_ID,"item/"+name));
    }
    private static void registerLogBlockState(String name) {
        rrp.addBlockState(JState.state(
                        JState.variant()
                                .put("axis=x", JState.model(MOD_ID+":block/"+name+"_horizontal").x(90).y(90))
                                .put("axis=y", JState.model(MOD_ID+":block/"+name))
                                .put("axis=z", JState.model(MOD_ID+":block/"+name+"_horizontal").x(90))
                ),
                new Identifier(MOD_ID,name));
    }

    private static void registerBlockState(String name) {
        rrp.addBlockState(JState.state(JState.variant(JState.model(MOD_ID+":block/"+name))), new Identifier(MOD_ID,name));
    }

    private static void registerStairBlockState(String name) {
        rrp.addBlockState(JState.state(JState.variant()
                .put("facing=east,half=bottom,shape=inner_left", JState.model(MOD_ID+":block/"+name+"_inner").y(270).uvlock())
                .put("facing=east,half=bottom,shape=inner_right", JState.model(MOD_ID+":block/"+name+"_inner"))
                .put("facing=east,half=bottom,shape=outer_left", JState.model(MOD_ID+":block/"+name+"_outer").y(270).uvlock())
                .put("facing=east,half=bottom,shape=outer_right", JState.model(MOD_ID+":block/"+name+"_outer"))
                .put("facing=east,half=bottom,shape=straight", JState.model(MOD_ID+":block/"+name))

                .put("facing=east,half=top,shape=inner_left", JState.model(MOD_ID+":block/"+name+"_inner").x(180).uvlock())
                .put("facing=east,half=top,shape=inner_right", JState.model(MOD_ID+":block/"+name+"_inner").x(180).y(90).uvlock())
                .put("facing=east,half=top,shape=outer_left", JState.model(MOD_ID+":block/"+name+"_outer").x(180).uvlock())
                .put("facing=east,half=top,shape=outer_right", JState.model(MOD_ID+":block/"+name+"_outer").x(180).y(90).uvlock())
                .put("facing=east,half=top,shape=straight", JState.model(MOD_ID+":block/"+name).x(180).uvlock())

                .put("facing=north,half=bottom,shape=inner_left", JState.model(MOD_ID+":block/"+name+"_inner").y(180).uvlock())
                .put("facing=north,half=bottom,shape=inner_right", JState.model(MOD_ID+":block/"+name+"_inner").y(270).uvlock())
                .put("facing=north,half=bottom,shape=outer_left", JState.model(MOD_ID+":block/"+name+"_outer").y(180).uvlock())
                .put("facing=north,half=bottom,shape=outer_right", JState.model(MOD_ID+":block/"+name+"_outer").y(270).uvlock())
                .put("facing=north,half=bottom,shape=straight", JState.model(MOD_ID+":block/"+name).y(270).uvlock())

                .put("facing=north,half=top,shape=inner_left", JState.model(MOD_ID+":block/"+name+"_inner").y(270).x(180).uvlock())
                .put("facing=north,half=top,shape=inner_right", JState.model(MOD_ID+":block/"+name+"_inner").x(180).uvlock())
                .put("facing=north,half=top,shape=outer_left", JState.model(MOD_ID+":block/"+name+"_outer").y(270).x(180).uvlock())
                .put("facing=north,half=top,shape=outer_right", JState.model(MOD_ID+":block/"+name+"_outer").x(180).uvlock())
                .put("facing=north,half=top,shape=straight", JState.model(MOD_ID+":block/"+name).y(270).x(180).uvlock())

                .put("facing=south,half=bottom,shape=inner_left", JState.model(MOD_ID+":block/"+name+"_inner"))
                .put("facing=south,half=bottom,shape=inner_right", JState.model(MOD_ID+":block/"+name+"_inner").y(90).uvlock())
                .put("facing=south,half=bottom,shape=outer_left", JState.model(MOD_ID+":block/"+name+"_outer"))
                .put("facing=south,half=bottom,shape=outer_right", JState.model(MOD_ID+":block/"+name+"_outer").y(90).uvlock())
                .put("facing=south,half=bottom,shape=straight", JState.model(MOD_ID+":block/"+name).y(90).uvlock())

                .put("facing=south,half=top,shape=inner_left", JState.model(MOD_ID+":block/"+name+"_inner").y(90).x(180).uvlock())
                .put("facing=south,half=top,shape=inner_right", JState.model(MOD_ID+":block/"+name+"_inner").y(180).x(180).uvlock())
                .put("facing=south,half=top,shape=outer_left", JState.model(MOD_ID+":block/"+name+"_outer").y(90).x(180).uvlock())
                .put("facing=south,half=top,shape=outer_right", JState.model(MOD_ID+":block/"+name+"_outer").y(180).x(180).uvlock())
                .put("facing=south,half=top,shape=straight", JState.model(MOD_ID+":block/"+name).y(90).x(180).uvlock())

                .put("facing=west,half=bottom,shape=inner_left", JState.model(MOD_ID+":block/"+name+"_inner").y(90).uvlock())
                .put("facing=west,half=bottom,shape=inner_right", JState.model(MOD_ID+":block/"+name+"_inner").y(180).uvlock())
                .put("facing=west,half=bottom,shape=outer_left", JState.model(MOD_ID+":block/"+name+"_outer").y(90).uvlock())
                .put("facing=west,half=bottom,shape=outer_right", JState.model(MOD_ID+":block/"+name+"_outer").y(180).uvlock())
                .put("facing=west,half=bottom,shape=straight", JState.model(MOD_ID+":block/"+name).y(180).uvlock())

                .put("facing=west,half=top,shape=inner_left", JState.model(MOD_ID+":block/"+name+"_inner").y(180).x(180).uvlock())
                .put("facing=west,half=top,shape=inner_right", JState.model(MOD_ID+":block/"+name+"_inner").y(270).x(180).uvlock())
                .put("facing=west,half=top,shape=outer_left", JState.model(MOD_ID+":block/"+name+"_outer").y(180).x(180).uvlock())
                .put("facing=west,half=top,shape=outer_right", JState.model(MOD_ID+":block/"+name+"_outer").y(270).x(180).uvlock())
                .put("facing=west,half=top,shape=straight", JState.model(MOD_ID+":block/"+name).y(180).x(180).uvlock())
        ), new Identifier(MOD_ID,name));
    }
    private static void registerSlabBlockState(String name,String double_name, String top_name) {
        rrp.addBlockState(JState.state(JState.variant()
                .put("type=bottom", JState.model(MOD_ID+":block/"+name))
                .put("type=double", JState.model(MOD_ID+":block/"+double_name))
                .put("type=top", JState.model(MOD_ID+":block/"+top_name))
        ), new Identifier(MOD_ID,name));
    }

    private static Map.Entry<String,String>[] getTextures(@NotNull EntryTextureType type, String...textures){
        switch (type) {
            case BOTTOM_TOP_SIDE -> {
                return new Map.Entry[]{
                        Map.entry("bottom",textures[0]),
                        Map.entry("top",textures[0]),
                        Map.entry("side",textures[0])
                };
            }
            default -> {
                return new Map.Entry[]{Map.entry("side",textures[0]),Map.entry("end",textures[1])};
            }
        }
    }

    private enum EntryTextureType {
        BOTTOM_TOP_SIDE,
        SIDE_END
    }
}
