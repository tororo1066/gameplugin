package gameplugin.gameplugin

import org.bukkit.*
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask
import org.bukkit.scoreboard.Team

object GameCommand :CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) return false
        when (args[1]) {
            "help" -> {

            }
            "aooni" -> {
                when (args[2]) {
                    "help" -> {

                    }
                    "start" -> {

                        object : BukkitRunnable() {
                            var start = 3
                            override fun run() {
                                for (p in Bukkit.getOnlinePlayers()) {
                                    val w = p.world
                                    if (start == 3) {
                                        w.playSound(p.location, Sound.BLOCK_ANVIL_PLACE, 100f, 1f)
                                        p.sendTitle("§9ーーー3ーーー", "", 1, 20, 1)
                                    }
                                    if (start == 2) {
                                        w.playSound(p.location, Sound.BLOCK_ANVIL_PLACE, 100f, 1f)
                                        p.sendTitle("§9ーーー2ーーー", "", 1, 20, 1)
                                    }
                                    if (start == 1) {
                                        w.playSound(p.location, Sound.BLOCK_ANVIL_PLACE, 100f, 1f)
                                        p.sendTitle("§9ーーー1ーーー", "", 1, 20, 1)
                                    }
                                    if (start == 0) {
                                        w.playSound(p.location, Sound.BLOCK_ANVIL_PLACE, 100f, 1f)
                                        p.sendTitle("§9青鬼§fごっこ", "スタート", 1, 100, 1)
                                        for (h : OfflinePlayer in scoreboard.getTeam("hiroshi")?.players!!) {
                                            h.player?.teleport(Location(Bukkit.getWorld("world"), 4.0, 50.0, 16.0))
                                        }
                                        cancel()

                                    }
                                }


                            }

                        }.runTaskTimer(plugin, 0, 20)
                    }

                    "wp" -> {

                    }
                }
            }
        }
        return true
    }



}









