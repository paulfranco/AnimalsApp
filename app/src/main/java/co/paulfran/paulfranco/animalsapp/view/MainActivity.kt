package co.paulfran.paulfranco.animalsapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.paulfran.paulfranco.animalsapp.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle("Animals App")
    }
}
