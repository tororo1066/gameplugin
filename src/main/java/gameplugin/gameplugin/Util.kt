package gameplugin.gameplugin

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player

object Util {
    fun aooniselect(oni: Player){
        scoreboard.getTeam("aooni")?.addPlayer(oni)
        for (p in Bukkit.getOnlinePlayers()) {
            oni.teleport(Location(Bukkit.getWorld("world"), -6.5, 51.0, 76.5))
            val data =
            p.sendMessage(prefix + "§9" + (oni.name) + "が青鬼に選ばれました")
        }
    }
}