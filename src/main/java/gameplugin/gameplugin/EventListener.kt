package gameplugin.gameplugin

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.*
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockCanBuildEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.FoodLevelChangeEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.*
import org.bukkit.event.vehicle.VehicleDestroyEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable


object EventListener : Listener {

    var eme = true
    var huwadamause = false
    var cooltime = 60
    private var count = 1
    private var count1 = 1
    private var death = false
    private var bow = HashMap<Player,Boolean>()


    @EventHandler
    fun onJoin(e: PlayerJoinEvent) {
        if (e.player.hasPlayedBefore()) {
            e.joinMessage = e.player.name + "§eがログインしました"
        } else {
            e.joinMessage = e.player.name + "§eがログインしました (初参加)"
        }
    }

    @EventHandler
    fun onPlayerInteract(e: PlayerInteractEvent) {
        if (!aoonistart)return
        if (e.action == Action.RIGHT_CLICK_BLOCK && e.clickedBlock?.type == Material.DIAMOND_BLOCK){
            if (count1 == 1) {
                count1--
            } else { count1 = 1

                for (p in Bukkit.getOnlinePlayers()){
                    p.sendMessage(prefix + "§b${e.player.name}が青鬼の館から脱出しました")
                }
            }
        }
        if (e.action == Action.RIGHT_CLICK_BLOCK && e.clickedBlock?.type == Material.GOLD_ORE) {

            if (count == 1) {
                count--
            } else { count = 1

                death = true
                Bukkit.dispatchCommand(e.player, "sneak")
                scoreboard.getTeam("hiroshi")?.addEntry(e.player.name)
                if (easy){
                    zanki[e.player] = 3
                }
                e.player.health = 0.0
                for (p in Bukkit.getOnlinePlayers()) {
                    p.sendMessage(prefix + "§b${e.player.name}が復活しました")
                }
            }
        }
        if (huwadamause) {
            if (e.action == Action.RIGHT_CLICK_AIR || e.action == Action.RIGHT_CLICK_BLOCK) {
                if (e.player.inventory.itemInMainHand.type == Material.SLIME_BALL) {
                    e.player.openInventory(huwadamamenu)
                }
            }
        }


    }

    @EventHandler
    fun onClick(e: InventoryClickEvent) {
            val player: Player = e.whoClicked as Player
            if (e.inventory == huwadamamenu) {
                when (e.slot) {
                    2 -> {
                        cooltime = 60

                        huwadamause = false
                        object : BukkitRunnable() {
                            override fun run() {

                                if (cooltime == 1) {
                                    huwadamause = true
                                    cancel()
                                }
                                cooltime--
                            }
                        }.runTaskTimer(plugin, 0, 20)
                        object : BukkitRunnable() {
                            override fun run() {
                                player.teleport(Location(player.world, 10.0, 10.0, 10.0))
                            }
                        }.runTaskLater(plugin, 100)


                    }

                    6 -> {
                        cooltime = 60

                        huwadamause = false
                        object : BukkitRunnable() {
                            var cooltime = 60
                            override fun run() {

                                if (cooltime == 1) {
                                    huwadamause = true
                                    cancel()
                                }
                                cooltime--
                            }
                        }.runTaskTimer(plugin, 0, 20)

                        object : BukkitRunnable() {
                            override fun run() {
                                player.teleport(Location(player.world, 25.0, 63.0, 40.0))
                            }
                        }.runTaskLater(plugin, 100)


                    }
                }
                e.isCancelled
                e.whoClicked.closeInventory()
            }
        }
    @EventHandler
    fun damage(e: EntityDamageByEntityEvent){
        if (!aoonistart)return

        if (e.damager is Arrow && e.entity is Cow){

            val arrow = e.damager as Arrow
            val player = arrow.shooter as Player
            player.teleport(Location(player.world,-128.0,52.0,-6.0))
            e.isCancelled = true
        }


        if (e.entityType == EntityType.PLAYER && e.damager.type == EntityType.PLAYER){
            val attackplayer = e.damager as Player
            val damager = e.entity as Player
            for (p in Bukkit.getOnlinePlayers()){
                p.sendMessage(scoreboard.getTeam("aooni")?.hasEntry(attackplayer.name).toString())
                p.sendMessage(scoreboard.getTeam("hiroshi")?.hasEntry(damager.name).toString())

            }

            if (scoreboard.getTeam("aooni")?.hasEntry(attackplayer.name)!! && scoreboard.getTeam("hiroshi")?.hasEntry(damager.name)!! && taizai){
                e.damage = 20.0
            }
            if (scoreboard.getTeam("aooni")?.hasEntry(attackplayer.name)!! && scoreboard.getTeam("hiroshi")?.hasEntry(damager.name)!! && !taizai && 74.0 < damager.location.y && damager.location.y < 78.0 && taizai){
                e.damage = 10.0
            }

        }
    }

    @EventHandler
    fun death(e: PlayerDeathEvent){
        if (!aoonistart)return

        if (scoreboard.getTeam("hiroshi")?.hasEntry(e.entity.name)!!){


            if (easy && !death){
                val ki = zanki[e.entity.player!!]

                if (ki == 0){
                    die = true
                }else{
                    zanki[e.entity] = ki?.minus(1)!!
                }
            }
            if (normal && !death)die = true


            e.deathMessage = ""
            if (!death){
                e.deathMessage = ChatColor.BLUE.toString() + e.entity.player?.name + "が青鬼に食べられた"
            }else{
                death = false
            }

            e.drops.clear()
            e.entity.spigot().respawn()
        }
        if (scoreboard.getTeam("hukkatu")?.hasEntry(e.entity.name)!!){
            e.drops.clear()
            e.entity.spigot().respawn()
        }


    }


    @EventHandler
    fun itemcatch(e: PlayerAttemptPickupItemEvent){
        if (!aoonistart)return
        if (scoreboard.getTeam("aooni")?.hasEntry(e.player.name)!!){
            e.isCancelled = true
        }

    }

    @EventHandler
    fun playerdrop(e: PlayerDropItemEvent){
        if (!aoonistart)return
        if (easy || normal){
            if (e.itemDrop.itemStack.type != Material.COOKED_PORKCHOP)e.isCancelled = true
        }
    }

    @EventHandler
    fun playerrespawn(e: PlayerRespawnEvent){
        if (!aoonistart)return
        if (die){
            die = false
            scoreboard.getTeam("hukkatu")?.addEntry(e.player.name)
        }

        if (scoreboard.getTeam("hiroshi")?.hasEntry(e.player.name)!!){
            e.respawnLocation = Location(e.player.world,-45.0,51.0,67.0)
        }
        if (scoreboard.getTeam("hukkatu")?.hasEntry(e.player.name)!!){
            bow[e.player] = true
            e.respawnLocation = Location(e.player.world, -31.0, 50.0, 4.0)
        }
        if (scoreboard.getTeam("hiroshi")?.size == 0){
            time = 0
        }


    }

    @EventHandler
    fun blockinteract(e: BlockCanBuildEvent){
        if (!aoonistart)return

        if (e.material == Material.OAK_PRESSURE_PLATE && e.block.location == Location(e.player?.world, 41.0, 51.0, 43.0)){
            for (p in Bukkit.getOnlinePlayers()){
                p.sendMessage(prefix + " §d${e.player?.name} さんが地下室を開放しました")
            }
        }else{
            if (e.material == Material.BLUE_WOOL && e.block.location == Location(e.player?.world, -1.0, 69.0, 40.0)){
                for (p in Bukkit.getOnlinePlayers()){
                    p.sendMessage(prefix + " §d${e.player?.name} さんが5階へ行けるようにしました")
                }
            }else e.isBuildable = true
        }

    }

    @EventHandler
    fun move(e: PlayerMoveEvent){
        if (!aoonistart)return
        if (!e.player.isSneaking && !scoreboard.getTeam("hukkatu")?.hasEntry(e.player.name)!!){
            Bukkit.dispatchCommand(e.player,"sneak")
        }
        if (e.player.location.subtract(0.0, 1.0, 0.0).block.type == Material.EMERALD_BLOCK){

            if (!eme)return
            eme = false
            var time = 3
            object : BukkitRunnable(){
                override fun run() {
                    if (e.player.location.subtract(0.0, 1.0, 0.0).block.type == Material.EMERALD_BLOCK){
                        time--
                    }else{
                        eme = true
                        cancel()
                    }
                    if (time == 0){
                        eme = true
                        onitime = 180
                        cancel()
                    }
                }
            }.runTaskTimer(plugin, 0, 20)
        }
    }

    @EventHandler
    fun sneak(e: PlayerToggleSneakEvent){
        if (!aoonistart)return
        e.isCancelled = true
    }

    @EventHandler
    fun block(e: BlockBreakEvent){
        if (!aoonistart)return
        if (scoreboard.getTeam("hukkatu")?.hasEntry(e.player.name)!!)e.isCancelled = true
        if (scoreboard.getTeam("aooni")?.hasEntry(e.player.name)!!)e.isCancelled = true
        if (scoreboard.getTeam("hiroshi")?.hasEntry(e.player.name)!!)e.isCancelled = true

    }

    @EventHandler
    fun hugrey(e: FoodLevelChangeEvent){
        if (scoreboard.getTeam("aooni")?.hasEntry(e.entity.name)!!)e.isCancelled = true
        if (scoreboard.getTeam("hukkatu")?.hasEntry(e.entity.name)!!)e.isCancelled = true

    }

    @EventHandler
    fun entityinteract(e: PlayerInteractEntityEvent){
        if (!aoonistart)return
        if (e.rightClicked.type == EntityType.ITEM_FRAME){
            val clicked = e.rightClicked
            val frame = clicked as ItemFrame
            val item = frame.item
            when(item.type){
                Material.BOW->{

                    if (scoreboard.getTeam("hukkatu")?.hasEntry(e.player.name)!! && bow[e.player] == true){
                        bow[e.player] = false
                        e.player.inventory.clear()
                        e.player.inventory.setItemInMainHand(ItemStack(Material.BOW))
                        e.player.inventory.setItemInOffHand(ItemStack(Material.ARROW,2))
                    }

                }

                Material.STONE_SWORD->{
                    if (scoreboard.getTeam("hukkatu")?.hasEntry(e.player.name)!!) {
                        e.player.inventory.clear()
                        e.player.inventory.setItemInMainHand(ItemStack(Material.STONE_SWORD))
                    }
                }
                else -> return
            }
            e.isCancelled = true
        }
    }

    @EventHandler
    fun destroy(e : VehicleDestroyEvent){
        if (!aoonistart)return
        if (e.vehicle.type == EntityType.MINECART){
            val p = e.attacker as Player
            p.sendMessage("asdo")
            e.isCancelled = true
        }

    }

    @EventHandler
    fun alldamage(e : EntityDamageEvent){

        if (e.entity.type == EntityType.PLAYER) {
            val p = e.entity as Player
            if (scoreboard.getTeam("aooni")?.hasEntry(p.name)!!){
                e.isCancelled = true
            }
        }
    }

}