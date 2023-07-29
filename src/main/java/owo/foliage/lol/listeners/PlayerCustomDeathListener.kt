package owo.foliage.lol.listeners

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.util.Vector
import owo.foliage.lol.utils.MaterialUtil


class PlayerCustomDeathListener : Listener {
    companion object {
        val deathMessageLock = arrayListOf<Player>()
    }

    @EventHandler
    fun onPlayerMove(e: PlayerMoveEvent) {
        val player = e.player
        val vc: Vector = player.velocity
        val toType = e.to.block.type
        if (toType == Material.WATER && vc.y <= -0.5 && !deathMessageLock.contains(player)) {
            deathMessageLock.add(player)
            player.damage(player.health)
            Bukkit.broadcastMessage("${player.name}在水中摔死了")
        } else if (MaterialUtil.getFlowers().contains(toType)) {
            deathMessageLock.add(player)
            player.damage(player.health)
            Bukkit.broadcastMessage("${player.name}对花粉过敏")
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    fun onPlayerDeath(e: PlayerDeathEvent) {
        if (deathMessageLock.remove(e.player)) {
            e.deathMessage = ""
        }
    }
}