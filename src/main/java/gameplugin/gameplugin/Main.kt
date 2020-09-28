package gameplugin.gameplugin

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.Objective
import org.bukkit.scoreboard.Team

var prefix = "§a[GamePlugin]§f"

val sbm = Bukkit.getScoreboardManager()
val scoreboard = sbm.mainScoreboard
var timer : Objective? = scoreboard.getObjective("Timer")
var huwadama = ""
var huwadamaer : Objective? = scoreboard.getObjective("Huwa")
var aooni : Team? = scoreboard.getTeam("aooni")
var hiroshi : Team? = scoreboard.getTeam("hiroshi")
lateinit var plugin : Main
val huwadamamenu = Bukkit.createInventory(null,9,"フワ玉")
var aoonistart = false


class Main : JavaPlugin(){



    override fun onEnable() {
        aoonistart = false
        val washitu = ItemStack(Material.PAPER)
        val piano = ItemStack(Material.PAPER)
        washitu.itemMeta.setDisplayName("和室")
        piano.itemMeta.setDisplayName("ピアノ部屋")
        huwadamamenu.setItem(2,washitu)
        huwadamamenu.setItem(6,piano)
        plugin = this
        getCommand("gp")?.setExecutor(GameCommand)

        server.logger.info("enable")


        var aooni = scoreboard.getTeam("aooni")
        if (aooni == null) {
            aooni = scoreboard.registerNewTeam("aooni")
        }
        aooni.prefix = ChatColor.BLUE.toString() + "[青鬼]"

        var hiroshi = scoreboard.getTeam("hiroshi")
        if (hiroshi == null){
            hiroshi = scoreboard.registerNewTeam("hiroshi")
        }


        hiroshi.prefix = "[ひろし]"

        var timer = scoreboard.getObjective("Timer")
        if (timer == null) {
            timer = scoreboard.registerNewObjective("Timer", "Dummy")
        }
        timer.displaySlot = DisplaySlot.SIDEBAR

        var huwadamaer = scoreboard.getObjective("Huwa")
        if (huwadamaer == null) {
            huwadamaer = scoreboard.registerNewObjective("Huwa", "Dummy")
        }
        huwadamaer.displaySlot = DisplaySlot.SIDEBAR


    }

    override fun onDisable() {
        scoreboard.getTeam("aooni")?.unregister()
        scoreboard.getTeam("hiroshi")?.unregister()
    }


}






