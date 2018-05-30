package fr.cabrolalexis.velo.view.activity

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import fr.cabrolalexis.velo.R
import fr.cabrolalexis.velo.view.base.BaseActivity

class LauncherActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        if(!isNetworkAvailable()) {

        } else {
            startActivity(HomeActivity.createIntent(this))
            finish()
        }
    }

    private fun isNetworkAvailable() : Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null
    }

}