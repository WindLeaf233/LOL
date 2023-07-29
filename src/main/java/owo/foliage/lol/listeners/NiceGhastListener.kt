package owo.foliage.lol.listeners

import org.bukkit.entity.EntityType
import org.bukkit.entity.Projectile
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.event.entity.ProjectileLaunchEvent
import org.bukkit.scheduler.BukkitRunnable
import owo.foliage.lol.utils.TaskManager
import owo.foliage.lol.utils.VectorUtil

class NiceGhastListener : Listener {
    private val fireballTimers = hashMapOf<Projectile, BukkitRunnable>()

    @EventHandler
    fun onProjectileLaunch(e: ProjectileLaunchEvent) {
        val fireball = e.entity
        if (fireball.type == EntityType.FIREBALL) {
            val timer = TaskManager.createTaskTimerAsynchronously(0L, 5L) {
                fireball.velocity =
                    VectorUtil.randomRotate(fireball.velocity, excludes = listOf(VectorUtil.VectorDirection.Y))
            }
            fireballTimers[fireball] = timer
        }
    }

    @EventHandler
    fun onProjectileHit(e: ProjectileHitEvent) {
        val entity = e.entity
        val timer = fireballTimers[entity]
        if (timer != null) {
            TaskManager.cancelTask(timer)
            fireballTimers.remove(entity)
        }
    }
}