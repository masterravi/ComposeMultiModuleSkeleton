package com.training.localization

import android.content.res.Configuration
import android.content.res.Resources
import android.util.DisplayMetrics

@Suppress("DEPRECATION")
internal class PhilologyResources(private val resourcesUtil: ResourcesUtil) :
    Resources(
        resourcesUtil.baseResources.assets,
        resourcesUtil.baseResources.displayMetrics,
        resourcesUtil.baseResources.configuration
    ) {

    override fun getText(id: Int): CharSequence =
        resourcesUtil.getText(id)

    override fun getText(id: Int, def: CharSequence): CharSequence = try {
        getText(id)
    } catch (_: NotFoundException) {
        def
    }

    override fun getString(id: Int): String =
        resourcesUtil.getString(id)

    override fun getDisplayMetrics(): DisplayMetrics = resourcesUtil.getDisplayMetrics()

    override fun getConfiguration(): Configuration = resourcesUtil.getConfiguration()
}
