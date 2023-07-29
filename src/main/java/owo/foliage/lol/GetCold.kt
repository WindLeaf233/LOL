package owo.foliage.lol

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import owo.foliage.lol.listeners.PlayerCustomDeathListener
import owo.foliage.lol.utils.TaskManager

object GetCold {
    private val playerColdValue = hashMapOf<Player, Int>()

    fun start() {
        TaskManager.createTaskTimerAsynchronously(0L, 20L) {
            val players = Bukkit.getOnlinePlayers()
            players.forEach {
                val world = it.world
                var coldValue = playerColdValue[it] ?: 0
                if (coldValue >= 5 * 60) {
                    playerColdValue[it] = 0
                    PlayerCustomDeathListener.deathMessageLock.add(it)
                    TaskManager.createTask { it.damage(it.health) }
                    Bukkit.broadcastMessage("${it.name}感冒了")
                } else {
                    val location = it.location
                    var canIncrease = true
                    for (y in location.blockY..319) {
                        val blockLocation =
                            Location(it.world, location.blockX.toDouble(), y.toDouble(), location.blockZ.toDouble())
                        if (blockLocation.block.type != Material.AIR) {
                            canIncrease = false
                        }
                    }
                    if (!world.isClearWeather && canIncrease) {
                        coldValue++
                        playerColdValue[it] = coldValue
                    }
                }
            }
        }
    }
}