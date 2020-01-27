package com.example.uzapp.tools

import android.graphics.Path

class FingerPath(color: Int, emboss: Boolean, blur: Boolean, strokeWidth: Int, path: Path) {
    var color: Int = 0
    var emboss: Boolean = false
    var blur: Boolean = false
    var strokeWidth: Int = 0
    var path: Path? = null

    init {
        this.color = color
        this.emboss = emboss
        this.blur = blur
        this.strokeWidth = strokeWidth
        this.path = path
    }
}