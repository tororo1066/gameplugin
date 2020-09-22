package gameplugin.gameplugin

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scoreboard.Team

var prefix = "§d[GamePlugin]"

var time = ""
var timer = ""
var huwadama = ""
var huwadamaer = ""
var aooni : Team? = null
var hiroshi : Team? = null
val sbm = Bukkit.getScoreboardManager()
val scoreboard = sbm.mainScoreboard
lateinit var plugin : Main


class Main : JavaPlugin(){






    companion object{

    }

    override fun onEnable() {
        getCommand("gp")?.setExecutor(GameCommand)

        server.logger.info("enable")
        var aooni = scoreboard.getTeam("aooni")
        if (aooni == null){
            aooni = scoreboard.registerNewTeam("aooni")
        }
        aooni.prefix = ChatColor.BLUE.toString() + "[青鬼]"

        var hiroshi = scoreboard.getTeam("hiroshi")
        if (hiroshi == null){
            hiroshi = scoreboard.registerNewTeam("hiroshi")
        }
        hiroshi.prefix = "[ひろし]"

        var timer = scoreboard.registerNewObjective("Timer","Dummy")
        var huwadamaer = scoreboard.registerNewObjective("Huwa","Dummy")
    }

}






