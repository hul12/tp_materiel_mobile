package com.example.countryapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.countryapp.ui.theme.MyApplicationTheme
import com.example.myapplication.R
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// MainActivity.kt
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: CountryViewModel
    private lateinit var adapter: CountryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val countryNameEditText: EditText = findViewById(R.id.countryNameEditText)
        val searchButton: Button = findViewById(R.id.searchButton)
        val countryNameTextView: TextView = findViewById(R.id.countryNameTextView)
        val countryFlagImageView: ImageView = findViewById(R.id.countryFlagImageView)

        searchButton.setOnClickListener {
            val countryName = countryNameEditText.text.toString()
            if (countryName.isNotEmpty()) {
                getCountryInfo(countryName) { country ->
                    countryNameTextView.text = country.name.common
                    Glide.with(this).load(country.flags.png).into(countryFlagImageView)
                }
            } else {
                Toast.makeText(this, "Please enter a country name", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getCountryInfo(countryName: String, callback: (CountryInfo) -> Unit) {
        val api = RetrofitInstance.api
        api.getCountryByName(countryName).enqueue(object : Callback<List<CountryInfo>> {
            override fun onResponse(call: Call<List<CountryInfo>>, response: Response<List<CountryInfo>>) {
                if (response.isSuccessful && response.body() != null) {
                    callback(response.body()!![0])
                } else {
                    Toast.makeText(this@MainActivity, "Country not found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<CountryInfo>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel = ViewModelProvider(this).get(CountryViewModel::class.java)

        adapter = CountryAdapter(emptyList()) { country ->
            val intent = Intent(this, CountryDetailActivity::class.java)
            intent.putExtra("country", country)
            startActivity(intent)
        }

        findViewById<RecyclerView>(R.id.recyclerView).adapter = adapter
        findViewById<RecyclerView>(R.id.recyclerView).layoutManager = LinearLayoutManager(this)

        viewModel.countries.observe(this, Observer {
            adapter = CountryAdapter(it) { country ->
                val intent = Intent(this, CountryDetailActivity::class.java)
                intent.putExtra("country", country)
                startActivity(intent)
            }
            findViewById<RecyclerView>(R.id.recyclerView).adapter = adapter
        })

        findViewById<SearchView>(R.id.searchView).setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.searchCountries(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { viewModel.searchCountries(it) }
                return true
            }
        })
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}