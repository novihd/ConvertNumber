package com.example.convertnumber

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.convertnumber.ui.theme.ConvertNumberTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConvertNumberTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    ConvertNumber()
                }
            }
        }
    }
}

@Composable
fun ConvertNumber() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        
        var inputNumber by rememberSaveable { mutableStateOf("") }
        val max = 6
        OutlinedTextField(value = inputNumber, onValueChange = {
            if (it.length <= max) {
                inputNumber = it
            }
        },
            label = { Text(text = "Number")},
            placeholder = { Text(text = "Enter a number")},
            maxLines = 1,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )

        var result by remember {
            mutableStateOf("Result")
        }

        Button(onClick = { result = numberToWord(inputNumber.toLong()) },
        modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Text(text = "Convert")
        }
        
        Text(text = result, modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally), fontSize = 16.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ConvertNumberTheme {
        ConvertNumber()
    }
}

// Data
val words = listOf<String>("", "Satu", "Dua", "Tiga", "Empat", "Lima", "Enam", "Tujuh", "Delapan", "Sembilan", "Sepuluh", "Sebelas")

// Convert number to word
fun numberToWord(number: Long) : String {
    return when (number) {
        in 0..11 -> words[number.toInt()]
        in 12..19 -> words[number.toInt() % 10] + " Belas"
        in 20..99 -> words[number.toInt() / 10] + " Puluh " + words[number.toInt() % 10]
        in 100..199 -> "Seratus " + numberToWord(number % 100)
        in 200..999 -> words[number.toInt() / 100] + " Ratus " + numberToWord(number % 100)
        in 1000..1999 -> "Seribu " + numberToWord(number / 1000)
        in 2000..999999 -> numberToWord(number / 1000) + " Ribu " + numberToWord(number % 1000)

        else -> "Unable to process numbers"
    }
}