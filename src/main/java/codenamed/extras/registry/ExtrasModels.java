package codenamed.extras.registry;

import net.minecraft.data.client.Model;
import net.minecraft.data.client.TextureKey;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class ExtrasModels {

    public static final Model TEMPLATE_RACK_SLOT_TOP_LEFT;
    public static final Model TEMPLATE_RACK_SLOT_TOP_MID;
    public static final Model TEMPLATE_RACK_SLOT_TOP_RIGHT;
    public static final Model TEMPLATE_RACK_SLOT_BOTTOM_LEFT;
    public static final Model TEMPLATE_RACK_SLOT_BOTTOM_MID;
    public static final Model TEMPLATE_RACK_SLOT_BOTTOM_RIGHT;

    private static Model block(String parent, String variant, TextureKey... requiredTextureKeys) {
        return new Model(Optional.of(Identifier.ofVanilla("block/" + parent)), Optional.of(variant), requiredTextureKeys);
    }

    static {
        TEMPLATE_RACK_SLOT_TOP_LEFT = block("template_rack_slot_top_left", "_slot_top_left", TextureKey.TEXTURE);
        TEMPLATE_RACK_SLOT_TOP_MID = block("template_rack_slot_top_mid", "_slot_top_mid", TextureKey.TEXTURE);
        TEMPLATE_RACK_SLOT_TOP_RIGHT = block("template_rack_slot_top_right", "_slot_top_right", TextureKey.TEXTURE);
        TEMPLATE_RACK_SLOT_BOTTOM_LEFT = block("template_rack_slot_bottom_left", "_slot_bottom_left", TextureKey.TEXTURE);
        TEMPLATE_RACK_SLOT_BOTTOM_MID = block("template_rack_slot_bottom_mid", "_slot_bottom_mid", TextureKey.TEXTURE);
        TEMPLATE_RACK_SLOT_BOTTOM_RIGHT = block("template_rack_slot_bottom_right", "_slot_bottom_right", TextureKey.TEXTURE);
    }
}
