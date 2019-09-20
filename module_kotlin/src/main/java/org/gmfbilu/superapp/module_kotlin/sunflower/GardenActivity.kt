package org.gmfbilu.superapp.module_kotlin.sunflower

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import org.gmfbilu.superapp.module_kotlin.R
import org.gmfbilu.superapp.module_kotlin.databinding.ModuleKotlinActivityGardenBinding


class GardenActivity : AppCompatActivity() {

    /**
     *  lateinit var只能用来修饰成员变量，不能修饰局部变量，并且只能用来修饰对象，不能用来修饰基本类型。就是让编译期在检查时不要因为属性变量未被初始化而报错
     */
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ModuleKotlinActivityGardenBinding = DataBindingUtil.setContentView(this, R.layout.module_kotlin_activity_garden)
        drawerLayout = binding.drawerLayout
        navController = Navigation.findNavController(this, R.id.garden_nav_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        // Set up ActionBar
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
        // Set up navigation menu
        binding.navigationView.setupWithNavController(navController)
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


}