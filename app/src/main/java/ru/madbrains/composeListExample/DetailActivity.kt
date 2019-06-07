package ru.madbrains.composeListExample

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {

    companion object {
        const val CAT_FACT_TEXT = "cat_fact_text"

        fun openDetailActivity(context: Context, catFactText: String) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(CAT_FACT_TEXT, catFactText)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent?.extras?.getString(CAT_FACT_TEXT)?.let {
            //todo set text
        }
    }
}
