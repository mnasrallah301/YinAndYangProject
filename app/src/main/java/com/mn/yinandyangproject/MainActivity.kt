package com.mn.yinandyangproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mn.yinandyangproject.ui.theme.Coffee
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1e1f22))
    )
    {
        YinAndYang(
            diameter = 300.dp,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center)
                .padding(top = 32.dp)
        )
        QuoteText(
            quote = """
        - Why did Yin go to therapy?
        - To 'find herself.' And Yang?
        - He was just there for the free coffee.
    """.trimIndent(),
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun YinYanHalfWithPathOperation() {
    Canvas(modifier = Modifier.size(200.dp, 200.dp)) {
        val center = Offset(size.width / 2, size.height / 2)
        val radius = size.width / 2
        val path = Path().apply {
            arcTo(
                Rect(
                    0f,
                    0f,
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
                    0f,
                    center.x + radius / 2,
                    center.y
                ),
                270f,
                180f,
                false
            )
        }
        val path2 = Path().apply {
            arcTo(
                Rect(
                    center.x - radius / 2,
                    center.y,
                    center.x + radius / 2,
                    center.y + radius
                ),
                90f,
                180f,
                false
            )
        }
        val newPath = Path()
        newPath.op(path, path2, PathOperation.Difference)
        drawPath(newPath, Color.Red)
    }

}

@Composable
fun YinYangHalf(
    diameter: Dp,
    sideColor: Color,
    modifier: Modifier = Modifier,
    dotColor: Color = Color.White,
    radiusBigDot: Float = LocalDensity.current.run { diameter.toPx() } / 20,
    radiusSmallDot: Float = LocalDensity.current.run { diameter.toPx() } / 80,
) {
    Canvas(
        modifier = modifier.requiredSize(diameter)
    ) {
        val canvasSize = this.size.minDimension
        val center = Offset(canvasSize / 2, canvasSize / 2)
        val radius = canvasSize / 2
        val path = Path().apply {
            arcTo(
                Rect(
                    0f,
                    0f,
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
                    0f,
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

        //Draw a half Yin & Yang
        drawPath(path, color = sideColor)

        val dotCenter = Offset(center.x, center.y - radius / 2)
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

@Composable
fun YinAndYang(
    diameter: Dp,
    modifier: Modifier = Modifier,
    yinModifier: Modifier = Modifier.graphicsLayer(rotationZ = 0f),
    yangModifier: Modifier = Modifier.graphicsLayer(rotationZ = 180f),
    radiusBigDot: Float = LocalDensity.current.run { diameter.toPx() } / 20,
    radiusSmallDot: Float = LocalDensity.current.run { diameter.toPx() } / 80,
) {
    Box(modifier = modifier) {
        //Draw Yang side
        YinYangHalf(
            diameter = diameter,
            modifier = yinModifier,
            sideColor = Color.White,
            dotColor = Color.Black,
            radiusBigDot = radiusBigDot,
            radiusSmallDot = radiusSmallDot,
        )
        //Draw Yin side
        YinYangHalf(
            diameter = diameter,
            modifier = yangModifier,
            sideColor = Color.Black,
            dotColor = Color.White,
            radiusBigDot = radiusBigDot,
            radiusSmallDot = radiusSmallDot,
        )
    }
}

@Composable
fun Pepsi(
    modifier: Modifier = Modifier,
    diameter: Dp = 300.dp
) {
    Box(
        modifier = modifier
            .size(diameter)
            .clip(CircleShape)
            .background(Color.White)
    ) {
        YinYangHalf(
            modifier = Modifier.graphicsLayer(rotationZ = 90f, scaleY = 2f, rotationY = 180f),
            diameter = diameter,
            sideColor = Color.Red,
            dotColor = Color.White,
            radiusBigDot = 0f,
            radiusSmallDot = 0f,
        )
        YinYangHalf(
            modifier = Modifier
                .padding(top = diameter / 4, start = diameter / 3)
                .graphicsLayer(rotationZ = 270f, scaleY = 2f, rotationY = 180f),
            diameter = diameter,
            sideColor = Color.Blue,
            dotColor = Color.White,
            radiusBigDot = 0f,
            radiusSmallDot = 0f,
        )
    }
}

@Composable
fun Baristas(
    modifier: Modifier = Modifier,
) {
    Row(modifier) {
        Text(text = "Barista' ", style = MaterialTheme.typography.displayLarge)
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(Color.White)
        ) {
            YinYangHalf(
                modifier = Modifier
                    .graphicsLayer(rotationX = 180f, rotationZ = -30f)
                    .padding(end = 16.dp, bottom = 2.dp),
                diameter = 80.dp,
                sideColor = Color.Black,
                dotColor = Color.White,
                radiusBigDot = 0f,
                radiusSmallDot = 0f,
            )
            YinYangHalf(
                modifier = Modifier
                    .padding(start = 16.dp, top = 6.dp)
                    .graphicsLayer(rotationZ = 150f, rotationX = 180f),
                diameter = 80.dp,
                sideColor = Coffee,
                dotColor = Color.White,
                radiusBigDot = 0f,
                radiusSmallDot = 0f,
            )
        }
    }
}

@Composable
fun TomAndJerry(
    modifier: Modifier = Modifier,
    diameter: Dp = 300.dp
) {
    Box(
        modifier = modifier
    ) {
        Image(
            modifier = Modifier
                .size(diameter)
                .rotate(180f)
                .clip(YinOrYangSideShape)
                .background(Color(0xFFd90c18))
                .padding(end = 90.dp, bottom = 140.dp),
            painter = painterResource(id = R.drawable.mouse),
            contentDescription = "Jerry"
        )
        Image(
            modifier = Modifier
                .size(diameter)
                .rotate(0f)
                .clip(YinOrYangSideShape)
                .background(Color(0xFFf89c0d))
                .padding(end = 90.dp, bottom = 30.dp),
            painter = painterResource(id = R.drawable.cat),
            contentDescription = "Tom"
        )
    }
}

@Composable
fun QuoteText(
    quote: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Row {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(YinOrYangSideShape)
                    .background(MaterialTheme.colorScheme.primary)
            )
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(YinOrYangSideShape)
                    .background(MaterialTheme.colorScheme.primary)
            )
        }
        Text(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
            color = Color.White,
            style = MaterialTheme.typography.headlineSmall,
            text = quote
        )
    }
}

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

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun YinYangHalfPreview() {
    YinAndYangProjectTheme {
        YinYangHalf(
            diameter = 200.dp,
            sideColor = Color.Black,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFffc802)
@Composable
fun YinAndYangPreview() {
    YinAndYangProjectTheme {
        YinAndYang(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center),
            diameter = 300.dp
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun BaristasPreview() {
    YinAndYangProjectTheme {
        Baristas(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .wrapContentSize(Alignment.Center)
        )
    }
}

@Preview
@Composable
fun TomAndJerryPreview() {
    YinAndYangProjectTheme {
        TomAndJerry(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center)
        )
    }
}

@Preview
@Composable
fun QuoteTextPreview() {
    YinAndYangProjectTheme {
        QuoteText(
            quote = """
        - Why did Yin go to therapy?
        - To 'find herself.' And Yang?
        - He was just there for the free coffee.
    """.trimIndent()
        )
    }
}



