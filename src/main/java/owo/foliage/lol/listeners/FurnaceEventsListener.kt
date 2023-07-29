package owo.foliage.lol.listeners

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.inventory.FurnaceBurnEvent
import org.bukkit.event.inventory.FurnaceSmeltEvent
import org.bukkit.inventory.FurnaceRecipe
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable
import owo.foliage.lol.utils.MaterialUtil
import owo.foliage.lol.utils.TaskManager

class FurnaceEventsListener : Listener {
    companion object {
        val furnaceFuelMaxAmount = hashMapOf<Block, Int>()
    }

    private val furnaceTimers = hashMapOf<Block, BukkitRunnable>()

    @EventHandler
    fun onFurnaceBurn(e: FurnaceBurnEvent) {
        val block: Block = e.block

        val fuel: ItemStack = e.fuel
//        var maxAmount: Int? = furnaceFuelMaxAmount[block]
//        if (maxAmount == null) {
//            furnaceFuelMaxAmount[block] = fuel.amount
////            maxAmount = fuel.amount
//        }
        lateinit var cancelTimer: () -> Unit
        val timer: BukkitRunnable = TaskManager.createTaskTimer(20L, /*3L*/20L) {
//            val rand = Random()
//            val ex = rand.nextInt(maxAmount) + 1
//            fuel.amount = ex
            if (fuel.amount != 0) fuel.amount--
            else {
                block.world.createExplosion(block.location, 7.0F, true)
                cancelTimer()
            }
        }
        cancelTimer = { timer.cancel() }
        furnaceTimers[block] = timer
    }

    @EventHandler
    fun onFurnaceSmelt(e: FurnaceSmeltEvent) {
        val ores: List<Material> = MaterialUtil.getOres()
        if (ores.contains(e.source.type)) {
            val randomMaterial: Material = ores.filter { it != e.source.type }.random()
            Bukkit.recipeIterator().forEachRemaining {
                if (it is FurnaceRecipe && it.inputChoice.test(ItemStack(randomMaterial))) {
                    e.result = it.result
                }
            }
        }
    }

    @EventHandler
    fun onBlockBreak(e: BlockBreakEvent) {
        val block: Block = e.block
        // 如果是熔炉就删除熔炉的随机燃料限定
        if (block.type == Material.FURNACE) {
            furnaceFuelMaxAmount.remove(block)
            if (furnaceTimers.containsKey(block)) {
                TaskManager.cancelTask(furnaceTimers[block]!!)
                furnaceTimers.remove(block)
            }
        }
    }
}