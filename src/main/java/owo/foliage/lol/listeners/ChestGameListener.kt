package owo.foliage.lol.listeners

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.block.Chest
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import owo.foliage.lol.utils.TaskManager
import java.util.*

class ChestGameListener : Listener {
    @EventHandler
    fun onPlayerInteract(e: PlayerInteractEvent) {
        val block: Block? = e.clickedBlock
        if (block != null && e.action == Action.RIGHT_CLICK_BLOCK && block.type == Material.CHEST && Random().nextBoolean()) {
            e.isCancelled = true
            TaskManager.createTask {
                val originLocation: Location = block.location
                val oppoFace: BlockFace = e.blockFace.oppositeFace
                val toLocation = originLocation.add(oppoFace.direction)

                val toBlock = toLocation.block
                toBlock.type = block.type
                toBlock.blockData = block.blockData
                (toBlock.state as Chest).blockInventory.contents = (block.state as Chest).blockInventory.contents
                block.type = Material.AIR
            }
        }

    }
}