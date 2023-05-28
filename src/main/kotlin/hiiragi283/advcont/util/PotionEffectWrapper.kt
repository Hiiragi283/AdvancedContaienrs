package hiiragi283.advcont.util

import net.minecraft.potion.Potion
import net.minecraft.potion.PotionEffect

data class PotionEffectWrapper(
    val potion: Potion,
    val duration: Int,
    val amplifier: Int,
    val isBeacon: Boolean,
    val showParticle: Boolean
) {

    constructor(effect: PotionEffect) : this(
        effect.potion,
        effect.duration,
        effect.amplifier,
        effect.isAmbient,
        effect.doesShowParticles()
    )

    fun hasSameAmplifier(other: PotionEffectWrapper): Boolean =
        this.potion == other.potion && this.amplifier == other.amplifier

}