package owo.foliage.lol.utils

import org.bukkit.util.Vector
import java.util.*

object VectorUtil {
    enum class VectorDirection {
        X, Y, Z
    }

    fun randomRotate(
        vector: Vector,
        randomAngel: Double = Random().nextDouble(366.0),
        excludes: List<VectorDirection> = listOf()
    ): Vector {
        val randomRotate = hashMapOf(
            VectorDirection.X to fun(vc: Vector) = vc.rotateAroundX(randomAngel),
            VectorDirection.Y to fun(vc: Vector) = vc.rotateAroundY(randomAngel),
            VectorDirection.Z to fun(vc: Vector) = vc.rotateAroundZ(randomAngel)
        )
        return randomRotate.filter { !excludes.contains(it.key) }.values.random().invoke(vector)
    }
}