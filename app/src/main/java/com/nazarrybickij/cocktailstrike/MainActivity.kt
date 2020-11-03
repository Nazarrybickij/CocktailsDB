package com.nazarrybickij.cocktailstrike


import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.ads.MobileAds
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.nazarrybickij.cocktailstrike.viewmodels.MainViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseApp.initializeApp(App.context)
        MobileAds.initialize(this){}
        firebaseAnalytics = Firebase.analytics
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        bottom_navigation.itemIconTintList = null
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_container_view) as NavHostFragment? ?: return
        val navController = host.navController
        setUpBottomNav(navController)

        main_search.setOnClickListener {
            navController.navigate(R.id.search_dialog)
        }
    }

    private fun setUpBottomNav(navController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav?.setupWithNavController(navController)
    }
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            hideKeyboard(this)
        }
        return super.dispatchTouchEvent(ev)
    }
    fun hideKeyboard(context: Context) {
        val inputManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val v = (context as Activity).currentFocus ?: return
        inputManager.hideSoftInputFromWindow(v.windowToken, 0)
    }

}