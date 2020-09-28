package gameplugin.gameplugin

import gameplugin.gameplugin.Util.aooniselect
import org.bukkit.*
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scoreboard.DisplaySlot
import java.util.*

var time = 900
object GameCommand :CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        if (sender !is Player) return false
        when (args[0]) {
            "help" -> {
                sender.sendMessage("§a===============GamePlugin===============")
                sender.sendMessage("§9/aooni help§f青鬼のヘルプを表示できます")
                sender.sendMessage("§a===============GamePlugin===============")
            }
            "aooni" -> {
                when (args[1]) {
                    "help" -> {

                    }
                    "start" -> {
                        aoonistart = true
                        for (p in Bukkit.getOnlinePlayers()){
                            timer?.displayName = p.name
                        }

                        object : BukkitRunnable() {
                            var start = 3
                            override fun run() {

                                    if (start == 3) {
                                        for (p in Bukkit.getOnlinePlayers()) {
                                            p.world.playSound(p.location, Sound.BLOCK_ANVIL_PLACE, 100f, 1f)
                                            p.sendTitle("§9ーーー3ーーー", "", 0, 20, 0)
                                        }
                                    }
                                    if (start == 2) {
                                        for (p in Bukkit.getOnlinePlayers()) {
                                            p.world.playSound(p.location, Sound.BLOCK_ANVIL_PLACE, 100f, 1f)
                                            p.sendTitle("§9ーーー2ーーー", "", 0, 20, 0)
                                        }
                                    }
                                    if (start == 1) {
                                        for (p in Bukkit.getOnlinePlayers()) {
                                            p.world.playSound(p.location, Sound.BLOCK_ANVIL_PLACE, 100f, 1f)
                                            p.sendTitle("§9ーーー1ーーー", "", 0, 20, 0)
                                        }
                                    }
                                    if (start == 0) {
                                        for (p in Bukkit.getOnlinePlayers()) {
                                            p.world.playSound(p.location, Sound.ENTITY_GENERIC_EXPLODE, 100f, 1f)
                                            p.sendTitle("§9青§f鬼ごっこ", "STRAT", 1, 100, 20)
                                        }

                                        for (h : OfflinePlayer in scoreboard.getTeam("hiroshi")?.players!!) {
                                            h.player?.teleport(Location(Bukkit.getWorld("world"), 4.0, 50.0, 16.0))
                                            cancel()
                                        }
                                    }
                                    start--



                            }

                        }.runTaskTimer(plugin, 0, 20)
                        time = 900
                        timer?.displaySlot = DisplaySlot.SIDEBAR
                        object : BukkitRunnable(){

                            override fun run() {

                                    for (p: Player in Bukkit.getOnlinePlayers()) {

                                        timer?.getScore("§a残り時間" + time + "秒")?.score = 0


                                        if (time == 900) {
                                            p.scoreboard = scoreboard
                                        }
                                        if (time == 0){
                                            p.sendTitle("§9青§f鬼ごっこ","終了",1,100,20)
                                            aoonistart = false
                                            cancel()
                                        }
                                    }
                                time--
                            }
                        }.runTaskTimer(plugin,60,20)
                    }

                    "wp" -> {

                        if (args.size == 3) {
                            val oni = Bukkit.getPlayer(args[2])
                            if (oni != null) {
                                if (oni.isOnline){
                                    for (p in Bukkit.getOnlinePlayers()){
                                        scoreboard.getTeam("hiroshi")?.addPlayer(p)
                                    }
                                    aooniselect(oni)


                                }else{
                                    sender.sendMessage(prefix + "このプレイヤーは存在しない、またはこのサーバーにいません！")
                                }
                            }

                        } else {

                            val playerList = arrayListOf<Player>()

                            for (player in Bukkit.getOnlinePlayers()) {
                                if (player.location.subtract(0.0, 1.0, 0.0).block.type == Material.DIAMOND_BLOCK) playerList.add(player)
                            }
                            if (playerList.isEmpty()) {
                                sender.sendMessage(prefix + "誰か一人はダイヤモンドブロックの上に乗ってください！")
                            } else {
                                val oni = playerList[Random().nextInt(playerList.size)]
                                for (p in Bukkit.getOnlinePlayers()){
                                    scoreboard.getTeam("hiroshi")?.addPlayer(p)
                                }
                                aooniselect(oni)
                            }
                        }
                    }

                    "settime" -> {

                            val number = args[2].toIntOrNull()
                            if (number != null) {
                                time = number
                            }else{
                                sender.sendMessage("$prefix/gp aooni settime (number)で使用できます")
                            }







                    }

                    "stop" -> {
                        time = 0
                    }
                }
            }
        }
        return true
    }




}














