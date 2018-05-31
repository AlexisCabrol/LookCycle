package fr.cabrolalexis.velo.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import fr.cabrolalexis.velo.R
import fr.cabrolalexis.velo.view.base.BaseActivity

class CityDetailsActivity: BaseActivity() {

    companion object {
        fun createIntent(context: Context): Intent {
            val intent = Intent(context, CityDetailsActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_details)
    }
}