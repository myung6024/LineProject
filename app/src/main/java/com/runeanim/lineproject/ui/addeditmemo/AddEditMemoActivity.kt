package com.runeanim.lineproject.ui.addeditmemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.runeanim.lineproject.R
import com.runeanim.lineproject.ui.main.MainFragment

class AddEditMemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, AddEditMemoFragment.newInstance())
                .commitNow()
        }
    }

}
