package ru.madbrains.composeListExample

import CircularProgressIndicator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.*
import androidx.ui.core.CraneWrapper
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.engine.text.FontStyle
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.MaterialTheme
import androidx.ui.material.TransparentButton
import androidx.ui.painting.TextStyle
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    var cats: List<Cat>? = null
    var currentCat: Cat? = null
    private lateinit var state: State<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = "List"

        setContent {
            state = +state { 0 }

            when (state.value) {
                -1 -> finish()
                0 -> {
                    if (cats != null) showList()
                    else {
                        showProgress()
                        getCatsFromServer()
                    }
                }
                1 -> currentCat?.run { showDetail() }

            }
        }
    }

    private fun getCatsFromServer() {
        getApi().getCats().enqueue(callback)
    }

    private val callback = object : Callback<List<Cat>> {
        override fun onFailure(call: Call<List<Cat>>, t: Throwable) {
            t.printStackTrace()
        }

        override fun onResponse(call: Call<List<Cat>>, response: Response<List<Cat>>) {
            cats = response.body()
            state.value = 0
        }
    }

    private fun showList() {
        supportActionBar?.run {
            title = "List"
            setDisplayHomeAsUpEnabled(false)
            setDisplayShowHomeEnabled(false)
        }

        CraneWrapper {
            MaterialTheme {
                VerticalScroller {
                    Column {
                        cats?.forEach { cat ->
                            Padding(8.dp) {
                                TransparentButton(
                                    onClick = {
                                        currentCat = cat
                                        state.value = 1
                                    },
                                    text = cat.text,
                                    textStyle = TextStyle(
                                        color = Color.Black,
                                        fontSize = 48f,
                                        fontStyle = FontStyle.Normal
                                    )
                                )
                                //Text(text = cat.text, style = +themeTextStyle { body1 })
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showDetail() {
        supportActionBar?.run {
            title = "Detail"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        CraneWrapper {
            MaterialTheme {
                FlexColumn {
                    expanded(flex = 1f) {
                        Padding(16.dp) {
                            Text(text = currentCat!!.text, style = TextStyle(fontSize = 40.toFloat()))
                        }
                    }
                }
            }
        }
    }

    private fun showProgress() {
        CraneWrapper {
            MaterialTheme {
                FlexColumn {
                    expanded(flex = 1f) {
                        Row(mainAxisAlignment = MainAxisAlignment.SpaceEvenly) {
                            // Indeterminate indicators
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        state.value--
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun getApi(): CatApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.myjson.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(CatApi::class.java)
    }
}