package owo.foliage.lol.listeners

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.util.Vector

class TsundereNetherPortalListener : Listener {
    @EventHandler
    fun onPlayerMove(e: PlayerMoveEvent) {
        val player = e.player
        val vc: Vector = player.velocity
        if ((vc.length() > 0.0784000015258789 || player.isSprinting || player.isJumping) && e.to.block.type == Material.NETHER_PORTAL) {
            PlayerCustomDeathListener.deathMessageLock.add(player)
            player.damage(player.health)
            Bukkit.broadcastMessage("${player.name}心急进不了下界门")
        }
    }
}