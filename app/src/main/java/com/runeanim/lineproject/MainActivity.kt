package com.runeanim.lineproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.runeanim.lineproject.ui.addeditmemo.AddEditMemoFragment
import com.runeanim.lineproject.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment())
                .commitNow()
        }
    }

}
