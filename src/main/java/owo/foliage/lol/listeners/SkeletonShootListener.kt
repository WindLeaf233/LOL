package owo.foliage.lol.listeners

import org.bukkit.entity.EntityType.*
import org.bukkit.entity.Projectile
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityShootBowEvent
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.scheduler.BukkitRunnable
import owo.foliage.lol.utils.TaskManager
import owo.foliage.lol.utils.VectorUtil

class SkeletonShootListener : Listener {
    private val arrowTimers = hashMapOf<Projectile, BukkitRunnable>()

    @EventHandler
    fun onEntityShootBowEvent(e: EntityShootBowEvent) {
        if (e.entity.type in listOf(SKELETON, STRAY, PILLAGER, PIGLIN)) {
            val projectile = e.projectile
            projectile.velocity = projectile.velocity.multiply(0.3)
            projectile.setGravity(false)
            val timer = TaskManager.createTaskTimerAsynchronously(0L, 5L) {
                projectile.velocity =
                    VectorUtil.randomRotate(projectile.velocity, excludes = listOf(VectorUtil.VectorDirection.Y))
            }
            arrowTimers[projectile as Projectile] = timer
//            val arrowNearbyPlayers: List<Player> =
//                projectile.getNearbyEntities(20.0, 10.0, 20.0).filterIsInstance<Player>()
//            arrowNearbyPlayers.forEach { it.damage(3.0, projectile) }
        }
    }

    @EventHandler
    fun onProjectileHit(e: ProjectileHitEvent) {
        arrowTimers.forEach { (projectile, timer) ->
            if (projectile == e.entity) {
                TaskManager.cancelTask(timer)
                arrowTimers.remove(e.entity)
            }
        }
    }
}