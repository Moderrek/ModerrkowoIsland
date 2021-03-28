package pl.moderr.moderrkowo.core.api.utils.text;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class ChatUtils {

    // Clear chat
    public static void clearChat(Player player) {
        for (int i = 0; i < 100; i++) {
            player.sendMessage(" ");
        }
    }

    // Chat clearly
    public static String formatMaterial(Material material) {
        String materialName = material.toString();
        materialName = materialName.replaceAll("_", " ");
        materialName = materialName.toLowerCase();
        return WordUtils.capitalizeFully(materialName);
    }
    public static String formatMaterial(EntityType material) {
        String materialName = material.toString();
        materialName = materialName.replaceAll("_", " ");
        materialName = materialName.toLowerCase();
        return WordUtils.capitalizeFully(materialName);
    }
}
