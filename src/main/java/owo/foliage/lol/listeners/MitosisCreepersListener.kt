package owo.foliage.lol.listeners

import org.bukkit.entity.Creeper
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityExplodeEvent

class MitosisCreepersListener : Listener {
    @EventHandler
    fun onEntityExplode(e: EntityExplodeEvent) {
        val entity = e.entity
        if (entity.type == EntityType.CREEPER && !entity.isGlowing) {
            e.isCancelled = true
            for (i in 1..6) {
                val location = entity.location
                val world = entity.world
                val subCreeper = world.spawnEntity(location, EntityType.CREEPER)
                subCreeper.isGlowing = true
                (subCreeper as Creeper).isPowered = true
            }
        }
    }
}