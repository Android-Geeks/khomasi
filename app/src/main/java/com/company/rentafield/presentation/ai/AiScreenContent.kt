package com.company.rentafield.presentation.ai

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.rentafield.R
import com.company.rentafield.presentation.components.MyOutlinedButton
import com.company.rentafield.presentation.playground.booking.PaymentTopBar
import com.company.rentafield.theme.KhomasiTheme

@Composable
fun AiScreenContent(
    onBackClicked: () -> Unit,
    onBrowseClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            PaymentTopBar(
                titleId = R.string.today_challenge,
                onBackClicked = onBackClicked
            )
        },
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)
                            .padding(vertical = 28.dp, horizontal = 12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .drawDashedBorder()
                                .padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.upload),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(top = 16.dp)
                                    .size(100.dp),
                                tint = Color.Gray
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = stringResource(id = R.string.browse),
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.Gray
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            MyOutlinedButton(
                                text = R.string.upload,
                                onClick = onBrowseClick,
                                modifier = Modifier.width(100.dp),
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.instructions),
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 26.dp, end = 8.dp, bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.dribbling_challenge_instructions).trimMargin(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.outline,
                        textAlign = TextAlign.Start,
                    )
                }
            }
        }
    }
}

fun Modifier.drawDashedBorder(
    color: Color = Color.Gray,
    strokeWidth: Float = 2f,
    cornerRadius: Float = 16f,
    dashLengths: FloatArray = floatArrayOf(30f, 25f)
) = this.then(
    Modifier.drawWithContent {
        drawIntoCanvas { canvas ->
            val paint = androidx.compose.ui.graphics.Paint().apply {
                this.color = color
                this.style = androidx.compose.ui.graphics.PaintingStyle.Stroke
                this.strokeWidth = strokeWidth
                this.pathEffect =
                    androidx.compose.ui.graphics.PathEffect.dashPathEffect(dashLengths)
            }

            val width = size.width
            val height = size.height

            val path = androidx.compose.ui.graphics.Path().apply {
                addRoundRect(
                    roundRect = androidx.compose.ui.geometry.RoundRect(
                        left = 0f,
                        top = 0f,
                        right = width,
                        bottom = height,
                        radiusX = cornerRadius,
                        radiusY = cornerRadius
                    )
                )
            }

            canvas.drawPath(path, paint)
        }
        drawContent()
    }
)

@Preview(showBackground = true)
@Composable
fun ScreenPreview() {
    KhomasiTheme {
        AiScreenContent(onBackClicked = {}, {})
    }
}