package owo.foliage.lol.listeners

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import owo.foliage.lol.utils.MaterialUtil

class LoseMineralsListener : Listener {
    @EventHandler
    fun onPlayerDeath(e: PlayerDeathEvent) {
        val world = e.player.world
        val location = e.player.location
        val inventory = e.player.inventory
        val minerals: List<Material> = MaterialUtil.getMinerals()

        inventory.contents = inventory.contents.filter {
            if (!minerals.contains(it?.type)) {
                true
            } else {
                world.dropItemNaturally(location, it!!)
                false
            }
        }.toTypedArray()
    }
}