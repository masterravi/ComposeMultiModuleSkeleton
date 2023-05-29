package com.training.localization

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.widget.VectorEnabledTintResources

@SuppressWarnings("RestrictedApi")
@SuppressLint("RestrictedApi")
internal class PhilologyVectorEnabledTintResources(
    baseContext: Context,
    private val resourcesUtil: ResourcesUtil,
) : VectorEnabledTintResources(baseContext, resourcesUtil.baseResources) {
    override fun getText(id: Int): CharSequence = resourcesUtil.getText(id)

    override fun getString(id: Int): String = resourcesUtil.getString(id)
}