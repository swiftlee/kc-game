package net.jmdev.util;

import me.clip.placeholderapi.external.EZPlaceholderHook;
import net.jmdev.game.TurretPriorityHandler;
import net.jmdev.game.Turrets;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Created by kedri on 8/19/2017.
 */
public class Placeholders extends EZPlaceholderHook{
    public Placeholders(Plugin plugin) {
        super(plugin, "game");
    }

    @Override
    public String onPlaceholderRequest(Player player, String s) {
        if (s.equalsIgnoreCase("redturret1")){

        }else if (s.equalsIgnoreCase("redturret2")){

        }else if (s.equalsIgnoreCase("redturret3")){

        }else if (s.equalsIgnoreCase("blueturret1")){

        }else if (s.equalsIgnoreCase("blueturret2")){

        }else if (s.equalsIgnoreCase("blueturret3")){

        }
        return "";
    }
}
