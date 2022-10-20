package app.asgn.cb.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import app.asgn.cb.R
import app.asgn.cb.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {
    lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }
}