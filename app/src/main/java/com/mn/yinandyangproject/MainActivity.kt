package com.mn.yinandyangproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mn.yinandyangproject.ui.theme.YinAndYangProjectTheme
import com.mn.yinandyangproject.ui.theme.YinOrYangSideShape

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
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center)
                .padding(top = 32.dp),
            yin = {
                YinOrYangSide(
                    size = size,
                    sideColor = Color.White,
                    dotColor = Color.Black,
                    angle = 180f,
                    radiusBigDot = pxSize / 20,
                    radiusSmallDot = pxSize / 80,
                )
            },
            yang = {
                YinOrYangSide(
                    size = size,
                    sideColor = Color.Black,
                    dotColor = Color.White,
                    angle = 0f,
                    radiusBigDot = pxSize / 20,
                    radiusSmallDot = pxSize / 80,
                )
            }
        )
        QuoteText(
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1e1f22)
@Composable
fun YinYangCompact() {
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

@Preview
@Composable
fun YinOrYangSide(
    modifier: Modifier = Modifier,
    size: Dp = 200.dp,
    sideColor: Color = Color.Black,
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
        val canvasSize = this.size.maxDimension
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
        drawPath(path, color = sideColor)
        //Draw the dot inside the path with the dotColor
        drawCircle(
            dotColor, center = dotCenter, radius = radiusBigDot
        )
        //Draw the little dot inside the previous dot with the sideColor
        drawCircle(
            sideColor, center = dotCenter, radius = radiusSmallDot
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFffc802)
@Composable
fun YinAndYang(
    modifier: Modifier = Modifier,
    yang: @Composable () -> Unit = {
        YinOrYangSide(
            size = 300.dp,
            sideColor = Color.Black,
            dotColor = Color.White,
            angle = 0f,
        )
    },
    yin: @Composable () -> Unit = {
        YinOrYangSide(
            size = 300.dp,
            sideColor = Color.White,
            dotColor = Color.Black,
            angle = 180f,
        )
    },
) {
    BoxWithConstraints(modifier = modifier) {
        //Draw Yang side
       yang()
        //Draw Yin side
       yin()
    }
}

@Preview(showBackground = true)
@Composable
fun Pepsi(
    modifier: Modifier = Modifier,
    radius: Dp = 300.dp
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center)
            .clip(CircleShape)
            .background(Color.White)
    ) {
        YinOrYangSide(
            size = radius,
            sideColor = Color.Red,
            dotColor = Color.White,
            angle = 90f,
            radiusBigDot = 0f,
            radiusSmallDot = 0f,
        )
        YinOrYangSide(
            modifier = Modifier.padding(top = radius / 4),
            size = radius,
            sideColor = Color.Blue,
            dotColor = Color.White,
            angle = 270f,
            radiusBigDot = 0f,
            radiusSmallDot = 0f,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TomAndJerry(
    modifier: Modifier = Modifier,
    radius: Dp = 300.dp
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.Center)
    ) {

        Image(
            modifier = Modifier
                .size(radius)
                .rotate(180f)
                .clip(YinOrYangSideShape)
                .background(Color(0xFFd90c18))
                .padding(end = 90.dp, bottom = 140.dp),
            painter = painterResource(id = R.drawable.mouse),
            contentDescription = "Jerry"
        )
        Image(
            modifier = Modifier
                .size(radius)
                .rotate(0f)
                .clip(YinOrYangSideShape)
                .background(Color(0xFFf89c0d))
                .padding(end = 90.dp, bottom = 30.dp),
            painter = painterResource(id = R.drawable.cat),
            contentDescription = "Tom"
        )
    }
}

@Preview
@Composable
fun QuoteText(
    modifier: Modifier = Modifier, quote: String = """
        - Why did Yin go to therapy?
        - To 'find herself.' And Yang?
        - He was just there for the free coffee.
    """.trimIndent()
) {
    Column(modifier = modifier) {
        Row {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(YinOrYangSideShape)
                    .background(Color.Red)
            )
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(YinOrYangSideShape)
                    .background(Color.Red)
            )
        }
        Text(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
            color = Color.White,
            style = MaterialTheme.typography.headlineSmall,
            text = """
        Why did Yin go to therapy?
        To 'find herself.' And Yang?
        He was just there for the free coffee.
    """.trimIndent()
        )
    }
}


