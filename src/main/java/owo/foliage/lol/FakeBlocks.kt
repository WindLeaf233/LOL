package owo.foliage.lol

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import owo.foliage.lol.utils.MaterialUtil
import owo.foliage.lol.utils.TaskManager

object FakeBlocks {
    private fun shouldConvert(type: Material): Material? {
        val ores: List<Material> = MaterialUtil.getOres()
        val logs: List<Material> = MaterialUtil.getLogs()
        val leaves: List<Material> = MaterialUtil.getLeaves()
        val plantFruits: List<Material> = MaterialUtil.getPlantFruits()
        val flowers: List<Material> = MaterialUtil.getFlowers()

        return if (ores.contains(type)) Material.DIAMOND_ORE
//        else if (logs.contains(type)) Material.valueOf("STRIPPED_${type.name}")
        else if (logs.contains(type)) logs.filter { it != type && !it.name.startsWith("STRIPPED_") }.random()
        else if (leaves.contains(type)) leaves.filter { it != type }.random()
        else if (plantFruits.contains(type)) plantFruits.filter { it != type }.random()
        else if (flowers.contains(type)) flowers.filter { it != type }.random()
        else null
    }

    fun start() {
        // 31x11x31 假方块
        val size = 31
        val height = 11
        TaskManager.createTaskTimerAsynchronously(0L, 5L) {
            Bukkit.getOnlinePlayers().forEach {
                val loc = it.location
                val x = loc.x.toInt()
                val y = loc.y.toInt()
                val z = loc.z.toInt()
                for (bx in x - ((size - 1) / 2)..x + ((size - 1) / 2)) {
                    for (by in y - ((height - 1) / 2)..y + ((height - 1) / 2)) {
                        for (bz in z - ((size - 1) / 2)..z + ((size - 1) / 2)) {
                            val location = Location(it.world, bx.toDouble(), by.toDouble(), bz.toDouble())
                            val convertType: Material? = shouldConvert(it.world.getBlockAt(location).type)
                            if (convertType != null) {
                                it.sendBlockChange(location, Bukkit.createBlockData(convertType))
                            }

                        }
                    }
                }
            }
        }
    }
}