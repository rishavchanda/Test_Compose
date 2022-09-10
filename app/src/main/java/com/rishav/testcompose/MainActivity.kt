package com.rishav.testcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rishav.testcompose.ui.theme.TestComposeTheme
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestComposeTheme {
                Surface(color = MaterialTheme.colors.background) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .background(Color(0xFF202C33))
                        ) {
                            MainScreen()
                        }
                }                
            }
        }
    }
}

@Composable
fun MainScreen(){
    var amountInput by remember {mutableStateOf("")};
    var tipInput by remember {mutableStateOf("")};
    var roundUp by remember { mutableStateOf(true) }
    val amount = amountInput.toDoubleOrNull() ?: 0.0
    val tipPercent = tipInput.toDoubleOrNull() ?: 0.0
    val tip = calculateTip(amount,tipPercent,roundUp)
    Column(
        modifier = Modifier.padding(22.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TitleText(text = "Tip Calculator")
        Spacer(modifier = Modifier.height(16.dp))
        TextField(text = amountInput,
            onTextChange = {amountInput = it},
            "Bill Amount",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )
        TextField(text = tipInput,
            onTextChange = {tipInput = it},
            "Tip (%)",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ))
        RoundTheTipRow(roundUp = roundUp, onRoundUpChanged = {roundUp = it})
        Spacer(modifier = Modifier.height(16.dp))
        TipText(amount = tip)
    }
}

@Composable
fun TitleText(text: String) {
    Text(
        text = text,
        textAlign = TextAlign.Center,
        fontSize = 36.sp,
        color = Color.White,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun TextField(
    text: String,
    onTextChange: (String)->Unit,
    label : String,
    keyboardOptions: KeyboardOptions
)
{
    OutlinedTextField(
        value = text,
        onValueChange = onTextChange,
        label = { Text(text = label)},
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = keyboardOptions,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = colorResource(id = R.color.purple_200),
            unfocusedBorderColor = Color.White,
            textColor = Color.White,
            unfocusedLabelColor = Color.White
        )
    )
}

@Composable
fun TipText(amount: Double){
    Text(
        text = "Tip Amount: ₹$amount",
        textAlign = TextAlign.Center,
        fontSize = 22.sp,
        color = Color.White,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun RoundTheTipRow(
    roundUp: Boolean,
    onRoundUpChanged: (Boolean) -> Unit,
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .size(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Round Tip ?", fontSize = 18.sp, color = Color.White,)
        Switch(
            checked = roundUp,
            onCheckedChange = onRoundUpChanged,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End)
        )
    }
}

private fun calculateTip(
    amount: Double,
    tipPercent: Double = 0.0,
    roundUp: Boolean
): Double {
    var tip = tipPercent / 100 * amount
    if (roundUp)
        tip = kotlin.math.ceil(tip)
    return tip
}
