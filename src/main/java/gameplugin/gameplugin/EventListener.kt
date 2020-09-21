package gameplugin.gameplugin

import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent


object EventListener : Listener {
    fun onJoinEvent(e : PlayerJoinEvent){
        val p = e.player
    }
}