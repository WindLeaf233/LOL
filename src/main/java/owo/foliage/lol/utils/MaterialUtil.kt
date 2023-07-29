package owo.foliage.lol.utils

import org.bukkit.Material
import org.bukkit.Material.*

object MaterialUtil {
    private inline fun filter(predicate: (Material) -> Boolean) = Material.values().filter(predicate)
    fun getOres() = filter { it.name.endsWith("_ORE") }
    fun getLogs() = filter { it.name.endsWith("_LOG") }
    fun getLeaves() = filter { it.name.endsWith("_LEAVES") }
    fun getPlantFruits() = listOf(MELON, PUMPKIN)
    fun getFlowers() = listOf(
        DANDELION,
        POPPY,
        BLUE_ORCHID,
        ALLIUM,
        AZURE_BLUET,
        OXEYE_DAISY,
        CORNFLOWER,
        LILY_OF_THE_VALLEY,
        *filter { it.name.endsWith("_TULIP") && !it.name.startsWith("POTTED_") }.toTypedArray()
    )

    fun getMinerals() = listOf(
        COAL,
        LAPIS_LAZULI,
        REDSTONE_WIRE,
        DIAMOND,
        EMERALD,
        NETHERITE_SCRAP,
        ANCIENT_DEBRIS,
        *filter { it.name.endsWith("_INGOT") || it.name.startsWith("RAW_") || it.name.endsWith("_NUGGET") }.toTypedArray()
    )
}