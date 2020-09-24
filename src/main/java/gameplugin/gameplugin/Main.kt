package gameplugin.gameplugin

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.Objective
import org.bukkit.scoreboard.Team

var prefix = "§d[GamePlugin]"

var time = ""
var timer : Objective? = null
var huwadama = ""
var huwadamaer : Objective? = null
var aooni : Team? = null
var hiroshi : Team? = null
val sbm = Bukkit.getScoreboardManager()
val teamboard = sbm.newScoreboard
val objeboard = sbm.newScoreboard
var scoreteam : Team? = null
lateinit var plugin : Main


class Main : JavaPlugin(){








    override fun onEnable() {
        getCommand("gp")?.setExecutor(GameCommand)

        plugin = this
        server.logger.info("enable")
        var aooni = teamboard.getTeam("aooni")
        if (aooni == null){
            aooni = teamboard.registerNewTeam("aooni")
        }
        aooni.prefix = ChatColor.BLUE.toString() + "[青鬼]"

        var hiroshi = teamboard.getTeam("hiroshi")
        if (hiroshi == null){
            hiroshi = teamboard.registerNewTeam("hiroshi")
        }
        hiroshi.prefix = "[ひろし]"

        var timer = objeboard.getObjective("Timer")
        if (timer == null) {
            timer = objeboard.registerNewObjective("Timer", "Dummy")
        }
        timer.displaySlot = DisplaySlot.SIDEBAR
        timer.displayName = "§9青鬼§fごっこ"
        var huwadamaer = objeboard.getObjective("Huwa")
        if (huwadamaer == null) {
            huwadamaer = objeboard.registerNewObjective("Huwa", "Dummy")
        }
        var scoreteam = objeboard.getTeam("Time")
        if (scoreteam == null) {
            scoreteam = objeboard.registerNewTeam("Time")
        }
        scoreteam.addEntry("§9青鬼§fごっこ")
    }

}






