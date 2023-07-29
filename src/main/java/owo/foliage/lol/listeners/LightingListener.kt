package owo.foliage.lol.listeners

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.CraftItemEvent

class LightingListener : Listener {
    @EventHandler
    fun onCraftItem(e: CraftItemEvent) {
        val player = e.whoClicked
        println(e.result.ordinal)
        for (i in 1..e.currentItem?.amount!!) player.world.strikeLightning(player.location)
    }
}