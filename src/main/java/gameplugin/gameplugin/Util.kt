package gameplugin.gameplugin

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object Util {

    fun aooniselect(oni: Player){
        scoreboard.getTeam("aooni")?.addEntry(oni.name)
        oni.teleport(Location(Bukkit.getWorld("world"), -6.5, 51.0, 76.5))
        val huwadama = ItemStack(Material.SLIME_BALL)
        val huwadamameta = huwadama.itemMeta
        huwadamameta.setDisplayName("§9フワ玉")
        huwadama.itemMeta = huwadamameta
        oni.inventory.clear()
        oni.inventory.setItem(4, huwadama)
        oni.foodLevel = 0
        oni.inventory.helmet = ItemStack(Material.DIAMOND_HELMET)
        oni.inventory.chestplate = ItemStack(Material.DIAMOND_CHESTPLATE)
        oni.inventory.leggings = ItemStack(Material.DIAMOND_LEGGINGS)
        oni.inventory.boots = ItemStack(Material.DIAMOND_BOOTS)
        for (p in Bukkit.getOnlinePlayers()) {
            p.sendMessage(prefix + "§9" + (oni.name) + "が青鬼に選ばれました")
        }
    }
}