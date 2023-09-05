package com.mn.yinandyangproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.tooling.preview.Preview
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
    val radius = 400f
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1e1f22))
    )
    {
        YinAndYang(
            radius = radius,
            angle = 0f,
            radiusBigDots = radius / 10,
            radiusSmallDots = 10f,
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
fun YinAndYang(
    radius: Float = 400f,
    angle: Float = 0f,
    radiusBigDots: Float = radius / 10,
    radiusSmallDots: Float = 10f,
) {
    Box {
        //Draw Yang
        YinOrYang(
            pathColor = Color.Black,
            oppositeColor = Color.White,
            radius = radius,
            angle = angle,
            radiusBigDot = radiusBigDots,
            radiusSmallDot = radiusSmallDots,
        )
        //Draw Yin
        YinOrYang(
            pathColor = Color.White,
            oppositeColor = Color.Black,
            radius = radius,
            angle = angle + 180f,
            radiusBigDot = radiusBigDots,
            radiusSmallDot = radiusSmallDots,
        )
    }
}


@Preview(showBackground = true)
@Composable
fun YinOrYang(
    pathColor: Color = Color.Black,
    oppositeColor: Color = Color.White,
    radius: Float = 400f,
    angle: Float = 0f,
    radiusBigDot: Float = 40f,
    radiusSmallDot: Float = 0f,
) {

    Canvas(
        modifier = Modifier
            .size(radius.dp)
            .rotate(angle)
    ) {
        val width = this.size.width
        val height = this.size.height
        val path = Path().apply {
            arcTo(
                Rect(
                    width / 2 - radius,
                    height / 2 - radius,
                    width / 2 + radius,
                    height / 2 + radius
                ),
                90f,
                180f,
                false
            )
            arcTo(
                Rect(
                    width / 2 - radius / 2,
                    height / 2 - radius,
                    width / 2 + radius / 2,
                    height / 2
                ),
                270f,
                180f,
                false
            )
            arcTo(
                Rect(
                    width / 2 - radius / 2,
                    height / 2,
                    width / 2 + radius / 2,
                    height / 2 + radius
                ),
                270f,
                -180f,
                false
            )
            close()
        }

        val center = Offset(width / 2, height / 2 - radius / 2)

        //Draw Path of Yin or Yang
        drawPath(path, color = pathColor)
        //Draw the dot inside the path with the opposite color
        drawCircle(
            oppositeColor, center = center, radius = radiusBigDot
        )
        //Draw the little dot inside the previous dot with the path color
        drawCircle(
            pathColor, center = center, radius = radiusSmallDot
        )
    }
}

