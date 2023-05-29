package com.training.localization

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.util.DisplayMetrics
import com.training.localization.Philology

internal class ResourcesUtil(internal val baseResources: Resources, base: Context) {
    private val repository = Philology.getConfigData(base)

    @Throws(Resources.NotFoundException::class)
    fun getText(id: Int): CharSequence {
        return repository.value?.get(baseResources.getResourceEntryName(id))?.asString
            ?: baseResources.getText(id)
    }

    @Throws(Resources.NotFoundException::class)
    fun getString(id: Int): String = getText(id).toString()

    fun getDisplayMetrics(): DisplayMetrics = baseResources.displayMetrics

    fun getConfiguration(): Configuration = baseResources.configuration
}
