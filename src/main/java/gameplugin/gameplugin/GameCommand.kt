    package gameplugin.gameplugin

    import gameplugin.gameplugin.EventListener.cooltime
    import gameplugin.gameplugin.EventListener.huwadamause
    import gameplugin.gameplugin.Util.aooniselect
    import org.bukkit.*
    import org.bukkit.command.Command
    import org.bukkit.command.CommandExecutor
    import org.bukkit.command.CommandSender
    import org.bukkit.entity.Player
    import org.bukkit.inventory.ItemStack
    import org.bukkit.scheduler.BukkitRunnable
    import org.bukkit.scoreboard.*
    import java.util.*
    import kotlin.collections.HashMap


    object GameCommand : CommandExecutor {

        lateinit var scoreboardManager: ScoreboardManager
        var hiroshisc = HashMap<OfflinePlayer,Scoreboard>()
        var aoonisc = HashMap<OfflinePlayer, Scoreboard>()
        var spesc = HashMap<OfflinePlayer, Scoreboard>()

        override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

            if (sender !is Player) return false
            when (args[0]) {
                "help" -> {
                    sender.sendMessage("§a=====================GamePlugin=====================")
                    sender.sendMessage("§9/aooni help §f青鬼のヘルプを表示できます")
                    sender.sendMessage("§a=====================GamePlugin=====================${aoonistart}")
                }
                "aooni" -> {
                    when (args[1]) {
                        "help" -> {
                            sender.sendMessage("§9=====================AooniGokko=====================")
                            sender.sendMessage("§9/start §d青鬼ごっこをスタートします")
                            sender.sendMessage("§9/wp (Player) §d青鬼を選択します(wpだけだと抽選(ダイブロ2段の上))")
                            sender.sendMessage("§9/stop §d青鬼ごっこを終了します")
                            sender.sendMessage("§9/time (Time) §d青鬼ごっこの時間を変更します(プレイ中のみ)")
                            sender.sendMessage("§9=====================AooniGokko=====================")
                        }
                        "start" -> {

                            if (aoonistart)return false
                            for (p in Bukkit.getOnlinePlayers()){
                                Bukkit.dispatchCommand(p,"sneak")
                            }
                            aoonistart = true


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
                                        huwadamause = true
                                        for (spe in scoreboard.getTeam("spectator")?.players!!){
                                            spe.player?.inventory?.setItemInMainHand(ItemStack(Material.COMPASS))
                                            spe.player?.gameMode = GameMode.SPECTATOR
                                            spe.player?.sendMessage(prefix + "右クリックで壁を越えてTPができます")
                                        }

                                        for (p in Bukkit.getOnlinePlayers()) {
                                            p.world.playSound(p.location, Sound.ENTITY_GENERIC_EXPLODE, 100f, 1f)
                                            p.sendTitle("§9青§f鬼ごっこ", "STRAT", 1, 100, 20)
                                        }

                                        for (h in scoreboard.getTeam("hiroshi")?.players!!) {
                                            h.player?.inventory?.clear()
                                            zanki[h.player!!] = 3
                                            sender.sendMessage(zanki[h.player!!].toString())
                                            h.player?.teleport(Location(Bukkit.getWorld("world"), 4.0, 50.0, 16.0))
                                            cancel()
                                        }
                                    }
                                    start--


                                }

                            }.runTaskTimer(plugin, 0, 20)
                            time = 900
                            onitime = 180

                            object : BukkitRunnable() {


                                override fun run() {

                                    if (time == 0) {
                                        huwadamause = false
                                        aoonistart = false
                                        for (p in Bukkit.getOnlinePlayers()){
                                            p.sendTitle("§9青§f鬼ごっこ", "終了", 1, 100, 20)
                                            Bukkit.dispatchCommand(p,"sneak")
                                            p.foodLevel = 20
                                            scoreboard.getTeam("aooni")?.removeEntry(p.name)
                                            scoreboard.getTeam("hiroshi")?.removeEntry(p.name)
                                            scoreboard.getTeam("hukkatu")?.removeEntry(p.name)
                                        }
                                        cancel()
                                    }

                                    for (p in scoreboard.getTeam("aooni")?.players!!){
                                        taizai = onitime != 0
                                        if (74.0 < p.player?.location?.y!! && p.player?.location?.y!! < 78.0 && taizai){
                                            onitime--
                                        }
                                    }

                                    for (a in scoreboard.getTeam("aooni")?.players!!){
                                        scoreboardManager = Bukkit.getScoreboardManager()
                                        aoonisc[a] = scoreboardManager.newScoreboard
                                        val ob = aoonisc[a]?.registerNewObjective(a.name!!,"Dummy",a.name!!)
                                        ob?.displaySlot = DisplaySlot.SIDEBAR
                                        ob?.getScore("§a残り時間:" + time + "秒")?.score = 0
                                        ob?.getScore("§35階滞在可能時間:" + onitime + "秒")?.score = -1
                                        if (!huwadamause) {
                                            ob?.getScore("§bフワ玉:" + cooltime + "秒")?.score = -2
                                        }else{
                                            aoonisc[a]?.resetScores("§bフワ玉:" + cooltime + "秒")
                                        }
                                        a.player?.scoreboard = aoonisc[a]!!
                                    }


                                    for (h in scoreboard.getTeam("hiroshi")?.players!!){
                                        scoreboardManager = Bukkit.getScoreboardManager()
                                        hiroshisc[h] = scoreboardManager.newScoreboard
                                        val ob = hiroshisc[h]?.registerNewObjective(h.name!!,"Dummy",h.name!!)
                                        ob?.displaySlot = DisplaySlot.SIDEBAR
                                        ob?.getScore("§a残り時間:" + time + "秒")?.score = 0
                                        ob?.getScore("§6残り人数:" + scoreboard.getTeam("hiroshi")?.size + "人")?.score = -1
                                        if (easy){
                                            ob?.getScore("§3残機:" + zanki[h])?.score = -2
                                        }

                                        h.player?.scoreboard = hiroshisc[h]!!
                                    }

                                    for (hu in scoreboard.getTeam("hukkatu")?.players!!){
                                        scoreboardManager = Bukkit.getScoreboardManager()
                                        hiroshisc[hu] = scoreboardManager.newScoreboard
                                        val ob = hiroshisc[hu]?.registerNewObjective(hu.name!!,"Dummy",hu.name!!)
                                        ob?.displaySlot = DisplaySlot.SIDEBAR
                                        ob?.getScore("§a残り時間:" + time + "秒")?.score = 0
                                        ob?.getScore("§6残り人数:" + scoreboard.getTeam("hiroshi")?.size + "人")?.score = -1

                                        hu.player?.scoreboard = hiroshisc[hu]!!
                                    }

                                    for (spe in scoreboard.getTeam("spectator")?.players!!){
                                        scoreboardManager = Bukkit.getScoreboardManager()
                                        spesc[spe] = scoreboardManager.newScoreboard
                                        val ob = spesc[spe]?.registerNewObjective(spe.name!!,"Dummy",spe.name!!)
                                        ob?.displaySlot = DisplaySlot.SIDEBAR
                                        ob?.getScore("§a残り時間:" + time + "秒")?.score = 0
                                        ob?.getScore("§6残り人数:" + scoreboard.getTeam("hiroshi")?.size + "人")?.score = -1
                                        ob?.getScore("§35階滞在可能時間:" + onitime + "秒")?.score = -2
                                        ob?.getScore("§bフワ玉:" + cooltime + "秒")?.score = -3
                                    }

                                    time--
                                }
                            }.runTaskTimer(plugin, 60, 20)
                        }

                        "wp" -> {

                            if (args.size == 3) {
                                val oni = Bukkit.getPlayer(args[2])
                                if (oni != null && oni.isOnline) {

                                        for (p in Bukkit.getOnlinePlayers()) {
                                            scoreboard.getTeam("hiroshi")?.addEntry(p.name)
                                            p.foodLevel = 20
                                        }
                                        aooniselect(oni)


                                    } else {
                                        sender.sendMessage(prefix + "このプレイヤーは存在しない、またはこのサーバーにいません！")
                                    }


                            } else {

                                val playerList = arrayListOf<Player>()

                                for (player in Bukkit.getOnlinePlayers()) {
                                    if (player.location.subtract(0.0, 1.0, 0.0).block.type == Material.DIAMOND_BLOCK && player.location.subtract(0.0, 2.0, 0.0).block.type == Material.DIAMOND_BLOCK) playerList.add(player)
                                }
                                if (playerList.isEmpty()) {
                                    sender.sendMessage(prefix + "誰か一人はダイヤモンドブロックの上に乗ってください！")
                                } else {
                                    val oni = playerList[Random().nextInt(playerList.size)]
                                    for (p in Bukkit.getOnlinePlayers()) {
                                        scoreboard.getTeam("hiroshi")?.addEntry(p.name)
                                    }
                                    aooniselect(oni)
                                }
                            }
                        }

                        "time" -> {
                            try {
                                val number = args[2].toInt()
                                time = number
                            }catch (e : NumberFormatException){
                                sender.sendMessage("$prefix/gp aooni time (number)で使用できます")
                            }
                        }

                        "stop" -> {
                            time = 0
                        }

                        "mode" ->{
                            if (args.size != 3)return true
                            if (aoonistart){
                                sender.sendMessage(prefix + "青鬼ごっこは始まっています！")
                                return true
                            }
                            when(args[2]){
                                "easy"->{
                                    easy = true
                                    normal = false
                                    for (p in Bukkit.getOnlinePlayers()){
                                        p.sendMessage(prefix + "モードがeasyに変更されました")
                                    }
                                }
                                "normal"->{
                                    normal = true
                                    easy = false
                                    for (p in Bukkit.getOnlinePlayers()){
                                        p.sendMessage(prefix + "モードがnormalに変更されました")
                                    }

                                }
                            }
                        }
                        "spe"->{
                            if (scoreboard.getTeam("spectator")?.hasEntry(sender.name)!!){
                                scoreboard.getTeam("hiroshi")?.addEntry(sender.name)
                                for (p in Bukkit.getOnlinePlayers()){
                                    p.sendMessage(prefix + "§a${sender.name}が観戦をキャンセルしました")
                                }
                            }else{
                                scoreboard.getTeam("spectator")?.addEntry(sender.name)
                                for (p in Bukkit.getOnlinePlayers()){
                                    p.sendMessage(prefix + "§a${sender.name}が観戦席に移動しました")
                                }
                            }
                        }
                    }
                }
            }
            return true
        }


    }
