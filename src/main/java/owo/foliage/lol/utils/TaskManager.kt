package owo.foliage.lol.utils

import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable
import owo.foliage.lol.LOL
import java.lang.RuntimeException

object TaskManager {
    private val tasks = arrayListOf<BukkitRunnable>()

    @Synchronized
    private fun applyTask(invokeRun: () -> Unit, methodName: String, vararg args: Any): BukkitRunnable {
        val runnable = object : BukkitRunnable() {
            override fun run() = invokeRun()
        }
        val params = arrayOf(LOL.instance, *args)
        runnable.javaClass.methods.find { it.name == methodName && it.parameterCount == params.size }
            ?.invoke(runnable, *params)
        tasks.add(runnable)
        return runnable
    }

    @Synchronized
    fun createTask(invokeRun: () -> Unit) = applyTask(invokeRun, "runTask")

    @Synchronized
    fun createTaskAsynchronously(invokeRun: () -> Unit) = applyTask(invokeRun, "runTaskAsynchronously")

    @Synchronized
    fun createTaskLater(delay: Long, invokeRun: () -> Unit) = applyTask(invokeRun, "runTaskLater", delay)

    @Synchronized
    fun createTaskLaterAsynchronously(delay: Long, invokeRun: () -> Unit) =
        applyTask(invokeRun, "runTaskLaterAsynchronously", delay)

    @Synchronized
    fun createTaskTimer(delay: Long, period: Long, invokeRun: () -> Unit) =
        applyTask(invokeRun, "runTaskTimer", delay, period)

    @Synchronized
    fun createTaskTimerAsynchronously(delay: Long, period: Long, invokeRun: () -> Unit) =
        applyTask(invokeRun, "runTaskTimerAsynchronously", delay, period)

    fun cancelTask(runnable: BukkitRunnable, remove: Boolean = true) {
        runnable.cancel()
        if (remove) tasks.remove(runnable)
    }

    fun cancelActiveTasks() {
        tasks.forEach { cancelTask(it, remove = false) }
        tasks.clear()
        Bukkit.getScheduler().cancelTasks(LOL.instance)
    }
}