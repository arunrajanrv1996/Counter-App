package com.example.counterapp


import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.counterapp.ui.theme.CounterAppTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            CounterAppTheme {
                    Counter()
            }
        }
    }
}

@Composable
fun Counter(){
    val viewModel = viewModel<MainViewModel>()
    val context = LocalContext.current
    val num = viewModel.stateflow.collectAsState(0)
    val autoIncrement by viewModel.sharedflow.collectAsState(initial = false)
    val scrollState= rememberScrollState()


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize().verticalScroll(scrollState)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.width(350.dp).height(350.dp)
                .padding(40.dp)
                .background(Color.LightGray, shape = CircleShape)
                .padding(40.dp)
        )
            {
            Text(text = num.value.toString(),
                fontSize = 80.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .border(shape = CircleShape, width = 2.dp, color = Color.LightGray)
                    .background(Color.LightGray)
                    .padding(30.dp)
            )
        }

        Row {
            Button(onClick = {
                 if(num.value==98){
                     Toast.makeText(context,"Cannot Increment, Maximum Value is 98",Toast.LENGTH_SHORT).show()
                 }else{
                     viewModel.incrementCounter()
                 }

            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.LightGray
                ),
                modifier = Modifier.padding(10.dp)

                ) {
                Icon(
                   imageVector = Icons.Default.Add,
                   contentDescription = "Increment Button",
                   tint = Color.Black
                   )
            }
            Button(onClick = {
                viewModel.resetCounter()
            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.LightGray
                ),
                modifier = Modifier.padding(10.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Refresh Button",
                    tint = Color.Black
                )
            }
            Button(onClick = {
            if(num.value==0){
                Toast.makeText(context,"Cannot Decrement, Minimum Value is 0",Toast.LENGTH_SHORT).show()
            }else{
                viewModel.decrementCounter()
            }

            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.LightGray
                ),
                modifier = Modifier.padding(10.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.minus),
                    contentDescription = "Decrement Button",
                    tint = Color.Black
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(10.dp)
        ){

            Text(text = "Auto Increment",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(50.dp))
            Switch(
                checked = autoIncrement,
                onCheckedChange = { isEnabled ->
                    viewModel.toggleAutoIncrement(isEnabled, context)
                    if (isEnabled) {
                        Toast.makeText(context, "Auto Increment Enabled", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Auto Increment Disabled", Toast.LENGTH_SHORT).show()
                    }
                },
                colors = SwitchDefaults.colors(Color.LightGray),
                modifier = Modifier.padding(10.dp)
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun Preview() {
    CounterAppTheme {
        Counter()
    }
}