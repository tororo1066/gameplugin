package gameplugin.gameplugin

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scoreboard.Team

var prefix = "§a[GamePlugin]§f"


var taizai = true
var time = 900
var onitime = 180
val sbm = Bukkit.getScoreboardManager()
val scoreboard = sbm.mainScoreboard
lateinit var plugin : Main
val huwadamamenu = Bukkit.createInventory(null,9,"フワ玉")
var aoonistart = false
var easy = false
var normal = true
var zanki = HashMap<Player,Int>()
var die = false


class Main : JavaPlugin(){
    override fun onEnable() {

        aoonistart = false
        val washitu = ItemStack(Material.PAPER)
        val piano = ItemStack(Material.PAPER)
        val washitumeta = washitu.itemMeta
        washitumeta.setDisplayName("和室")
        val pianometa = piano.itemMeta
        pianometa.setDisplayName("ピアノ部屋")
        washitu.itemMeta = washitumeta
        piano.itemMeta = pianometa
        huwadamamenu.setItem(2,washitu)
        huwadamamenu.setItem(6,piano)
        plugin = this
        getCommand("gp")?.setExecutor(GameCommand)
        server.pluginManager.registerEvents(EventListener, plugin)


        server.logger.info("enable")


        var aooni = scoreboard.getTeam("aooni")
        if (aooni == null) {
            aooni = scoreboard.registerNewTeam("aooni")
        }
        aooni.prefix = ChatColor.BLUE.toString() + "[青鬼]"
        aooni.setOption(Team.Option.NAME_TAG_VISIBILITY,Team.OptionStatus.NEVER)

        var hiroshi = scoreboard.getTeam("hiroshi")
        if (hiroshi == null){
            hiroshi = scoreboard.registerNewTeam("hiroshi")
        }
        hiroshi.setOption(Team.Option.NAME_TAG_VISIBILITY,Team.OptionStatus.NEVER)
        hiroshi.setAllowFriendlyFire(false)

        hiroshi.prefix = "[ひろし]"

        var hukkatu = scoreboard.getTeam("hukkatu")
        if (hukkatu == null){
            hukkatu = scoreboard.registerNewTeam("hukkatu")
        }
        hukkatu.setAllowFriendlyFire(false)

        var spectator = scoreboard.getTeam("spectator")
        if (spectator == null){
            spectator = scoreboard.registerNewTeam("spectator")
        }
        spectator.prefix = "[観戦]"

    }

    override fun onDisable() {

    }


}