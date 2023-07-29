package owo.foliage.lol.listeners

import io.papermc.paper.event.entity.EntityMoveEvent
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.util.Vector
import java.util.*

class RandomlyFlyEntityListener : Listener {
    private val flyingEntities = arrayListOf<Entity>()
//    private val rotateTimers = hashMapOf<Entity, BukkitRunnable>()

    @EventHandler
    fun onEntityDamageByEntity(e: EntityDamageByEntityEvent) {
        val damager: Entity = e.damager

        val entity: Entity = e.entity
        if (damager is Player && entity is LivingEntity) {
            if (Random().nextInt(101) <= 30) {
                e.isCancelled = true
                val flyingEffect = PotionEffect(PotionEffectType.LEVITATION, 2 * 20, 3, true, true)
                flyingEffect.apply(entity)
                flyingEntities.add(entity)
//                val timer = TaskManager.createTaskTimerAsynchronously(0L, 5L) {
//                    entity.velocity =
//                        VectorUtil.randomRotate(entity.velocity, excludes = listOf(VectorUtil.VectorDirection.Y))
//                }
//                rotateTimers[entity] = timer
                damager.playSound(damager, Sound.ENTITY_GENERIC_EXPLODE, 10.0F, 5.0F)
            }
        }
    }

    @EventHandler
    fun onEntityMove(e: EntityMoveEvent) {
        val entity = e.entity
        val vc: Vector = entity.velocity
        if (e.to.block.type != Material.WATER && vc.y <= -0.5 && flyingEntities.contains(entity)) {
            vc.y = 0.0
            flyingEntities.remove(entity)
//            rotateTimers.forEach { (e, timer) ->
//                if (e == entity) {
//                    TaskManager.cancelTask(timer)
//                    rotateTimers.remove(entity)
//                }
//            }
        }
    }

    @EventHandler
    fun onEntityDeath(e: EntityDeathEvent) {
        val entity = e.entity
//        rotateTimers.forEach { (e, timer) ->
//            if (e == entity) {
//                TaskManager.cancelTask(timer)
//                rotateTimers.remove(entity)
//            }
//        }
    }
}