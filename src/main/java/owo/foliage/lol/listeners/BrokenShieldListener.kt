package owo.foliage.lol.listeners

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import java.util.*

class BrokenShieldListener : Listener {
    @EventHandler
    fun onLivingEntityAttack(e: EntityDamageByEntityEvent) {
        val entity = e.entity
        if (entity is Player && entity.isBlocking && Random().nextBoolean()) {
            entity.damage(e.damage, entity)
        }
    }
}