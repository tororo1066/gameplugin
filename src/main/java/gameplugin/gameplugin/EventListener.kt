package gameplugin.gameplugin

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.scheduler.BukkitRunnable


object EventListener : Listener {

    @EventHandler
    fun onJoin(e : PlayerJoinEvent){
        e.player.sendMessage("aa")
        if (e.player.hasPlayedBefore()) {
            e.joinMessage = e.eventName + "§eがログインしました"
        }else {
            e.joinMessage = e.eventName + "§eがログインしました (初参加)"
        }
    }

    @EventHandler
    fun onPlayerInteract(e : PlayerInteractEvent){
        e.player.sendMessage("aaa")
        if (aoonistart) {
            if (e.player.itemInHand.type == Material.SLIME_BALL) {
                e.player.openInventory(huwadamamenu)
            }
        }


    }

    @EventHandler
    fun onClick(e : InventoryClickEvent){
        val player : Player = e.whoClicked as Player
        if (e.inventory == huwadamamenu){
            when(e.slot){
                2 -> {
                    object : BukkitRunnable(){
                        override fun run() {

                        }
                    }.runTaskLater(plugin,100)
                    object : BukkitRunnable(){
                        var cooltime = 60

                        override fun run() {
                            val score = huwadamaer?.getScore("")
                            if (cooltime == 60) {
                                player.scoreboard = scoreboard
                            }
                            if (cooltime == 0){

                            }
                            cooltime--
                        }
                    }.runTaskTimer(plugin,0,20)
                }
                6 -> {

                }
            }
            e.isCancelled
            e.whoClicked.closeInventory()
        }
    }
}