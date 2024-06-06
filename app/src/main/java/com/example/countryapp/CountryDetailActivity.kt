package com.example.countryapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.countryapp.ui.theme.MyApplicationTheme
import com.example.myapplication.R
import com.squareup.picasso.Picasso

// CountryDetailActivity.kt
class CountryDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_detail)

        val country: Country? = intent.getParcelableExtra<Country>("country")

        country?.let {
            findViewById<TextView>(R.id.countryDetailName).text = it.name
            findViewById<TextView>(R.id.countryDetailCapital).text = it.capital
            Picasso.get().load(it.flag).into(findViewById<ImageView>(R.id.countryDetailFlag))
        }
    }
}



@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    MyApplicationTheme {
        Greeting2("Android")
    }
}