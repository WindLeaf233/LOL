package owo.foliage.lol

import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import owo.foliage.lol.listeners.*
import owo.foliage.lol.utils.TaskManager

class LOL : JavaPlugin() {
    companion object {
        lateinit var instance: LOL

        fun registerEvent(vararg listener: Listener) {
            listener.forEach { Bukkit.getPluginManager().registerEvents(it, instance) }
        }
    }

    override fun onEnable() {
        instance = this
        registerEvent(
            FurnaceEventsListener(),
            PlayerCustomDeathListener(),
            SkeletonShootListener(),
            RandomlyFlyEntityListener(),
            ChestGameListener(),
            MitosisCreepersListener(),
            NiceGhastListener(),
            LoseMineralsListener(),
            BrokenShieldListener(),
            LightingListener(),
            TsundereNetherPortalListener()
        )
        FakeBlocks.start()
        GetCold.start()
    }

    override fun onDisable() {
        TaskManager.cancelActiveTasks()
    }
}
