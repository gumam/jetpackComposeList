package ru.madbrains.composeListExample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.*
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

        setContent {
            state = +state { 0 }

            when (state.value) {
                -1 -> finish()
                0 -> {
                    if (cats != null) showList(cats!!)
                    else {
                        showProgress()
                        getCatsFromServer()
                    }
                }
                1 -> currentCat?.run { showDetail(text) }

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
            cats?.run {
                showList(this)
            }
        }
    }

    private fun showList(cats: List<Cat>) {

        //TODO set list
    }

    private fun showDetail(catFactText: String) {
        //TODO set list
    }

    private fun showProgress() {
        //todo show progress
    }

    override fun onBackPressed() {
        state.value--
    }

    private fun getApi(): CatApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.myjson.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(CatApi::class.java)
    }
}