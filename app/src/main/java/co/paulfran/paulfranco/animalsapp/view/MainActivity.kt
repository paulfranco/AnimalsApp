package co.paulfran.paulfranco.animalsapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import co.paulfran.paulfranco.animalsapp.R
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes


class MainActivity : AppCompatActivity() {

    // Back Button step 1
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(co.paulfran.paulfranco.animalsapp.R.layout.activity_main)

        AppCenter.start(
            application, "ade989c9-fd94-45cd-837a-11adf5e26374",
            Analytics::class.java, Crashes::class.java
        )


        // Back Button step 2
        navController = Navigation.findNavController(this, co.paulfran.paulfranco.animalsapp.R.id.fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)



    }

    // Back Button step 3
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }
}
