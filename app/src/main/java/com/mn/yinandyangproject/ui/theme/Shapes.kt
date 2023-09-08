package com.mn.yinandyangproject.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

val YinOrYangSideShape = object : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val center = Offset(size.width / 2, size.height / 2)
        val radius = minOf(size.width, size.height) / 2
        val path = Path().apply {
            arcTo(
                Rect(
                    center.x - radius,
                    center.y - radius,
                    center.x + radius,
                    center.y + radius
                ),
                90f,
                180f,
                false
            )
            arcTo(
                Rect(
                    center.x - radius / 2,
                    center.y - radius,
                    center.x + radius / 2,
                    center.y
                ),
                270f,
                180f,
                false
            )
            arcTo(
                Rect(
                    center.x - radius / 2,
                    center.y,
                    center.x + radius / 2,
                    center.y + radius
                ),
                270f,
                -180f,
                false
            )
            close()
        }
        return Outline.Generic(path)
    }
}

