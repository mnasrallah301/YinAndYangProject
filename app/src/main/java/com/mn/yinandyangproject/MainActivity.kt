package com.mn.yinandyangproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mn.yinandyangproject.ui.theme.YinAndYangProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YinAndYangProjectTheme {
                YinAndYangScreen()
            }
        }
    }
}

@Composable
fun YinAndYangScreen() {
    val size = 300.dp
    val pxSize = LocalDensity.current.run { size.toPx() }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1e1f22))
    )
    {
        YinAndYang(
            modifier = Modifier
                .padding(top = 32.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
                .fillMaxWidth(),
            radius = size,
            angle = 0f,
            radiusBigDots = pxSize / 20,
            radiusSmallDots = pxSize / 80,
        )
        Text(
            modifier = Modifier.padding(16.dp),
            color = Color.White,
            style = MaterialTheme.typography.headlineSmall,
            text = """
        - Why did Yin go to therapy?
        - To 'find herself.' And Yang?
        - He was just there for the free coffee.
    """.trimIndent()
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1e1f22)
@Composable
fun YinYangCanvas() {
    Canvas(
        modifier = Modifier.size(200.dp),
        onDraw = {
            val canvasSize = size.minDimension
            val center = Offset(canvasSize / 2, canvasSize / 2)
            val radius = canvasSize / 2
            val smallCircleRadius = radius / 2

            // Draw the white (Yang) part
            drawArc(
                color = Color.White,
                startAngle = -180f,
                sweepAngle = 180f,
                useCenter = true,
                size = Size(radius * 2, radius * 2),
                topLeft = Offset(center.x - radius, center.y - radius)
            )

            // Draw the black (Yin) part
            drawArc(
                color = Color.Black,
                startAngle = 0f,
                sweepAngle = 180f,
                useCenter = true,
                size = Size(radius * 2, radius * 2),
                topLeft = Offset(center.x - radius, center.y - radius)
            )

            // Draw the small circles
            drawCircle(
                color = Color.Black,
                radius = smallCircleRadius,
                center = center.copy(x = center.x / 2)
            )
            drawCircle(
                color = Color.White,
                radius = smallCircleRadius,
                center = center.copy(x = 3 * center.x / 2)
            )
        }
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF1e1f22)
@Composable
fun YinAndYang(
    modifier: Modifier = Modifier,
    radius: Dp = 300.dp,
    angle: Float = 0f,
    radiusBigDots: Float = LocalDensity.current.run { radius.toPx() } / 20,
    radiusSmallDots: Float = LocalDensity.current.run { radius.toPx() } / 80,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        //Draw Yang side
        YinYangHalf(
            modifier = Modifier.padding(16.dp),
            size = radius,
            halfColor = Color.Black,
            dotColor = Color.White,
            angle = angle,
            radiusBigDot = radiusBigDots,
            radiusSmallDot = radiusSmallDots,
        )
        //Draw Yin side
        YinYangHalf(
            modifier = Modifier.wrapContentSize().padding(16.dp),
            size = radius,
            halfColor = Color.White,
            dotColor = Color.Black,
            angle = angle + 180f,
            radiusBigDot = radiusBigDots,
            radiusSmallDot = radiusSmallDots,
        )
    }
}


@Preview(showBackground = true)
@Composable
fun YinYangHalf(
    modifier: Modifier = Modifier,
    size: Dp = 200.dp,
    halfColor: Color = Color.Black,
    dotColor: Color = Color.White,
    angle: Float = 0f,
    radiusBigDot: Float = LocalDensity.current.run { size.toPx() } / 20,
    radiusSmallDot: Float = LocalDensity.current.run { size.toPx() } / 80,
) {
    Canvas(
        modifier = modifier
            .requiredSize(size)
            .rotate(angle)
    ) {
        val canvasSize = this.size.minDimension
        val center = Offset(canvasSize / 2, canvasSize / 2)
        val radius = canvasSize / 2
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

        val dotCenter = Offset(center.x, center.y - radius / 2)
        //Draw a half Yin & Yang
        drawPath(path, color = halfColor)
        //Draw the dot inside the path with the dotColor
        drawCircle(
            dotColor, center = dotCenter, radius = radiusBigDot
        )
        //Draw the little dot inside the previous dot with the halfColor
        drawCircle(
            halfColor, center = dotCenter, radius = radiusSmallDot
        )
    }
}

