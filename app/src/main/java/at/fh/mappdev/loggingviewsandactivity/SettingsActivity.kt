package at.fh.mappdev.loggingviewsandactivity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES

class SettingsActivity : AppCompatActivity() {

    companion object{
        val USERNAME = "USERNAME"
        val DARKMODE = "DARKMODE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val usernameView = findViewById<EditText>(R.id.settings_input_username)
        val darkModeView = findViewById<Switch>(R.id.settings_switch_DarkMode)
        val sharedPreferences = getSharedPreferences(packageName, Context.MODE_PRIVATE)
        // read from sharedPreferences

        val usernameStored = sharedPreferences.getString(USERNAME, null)
        val darkModeStored =  sharedPreferences.getBoolean(DARKMODE, false)

        if (!usernameStored.isNullOrEmpty()){
            usernameView.setText(usernameStored)
            darkModeView.isChecked = darkModeStored
        }



        // Save Button
        findViewById<Button>(R.id.settings_button_confirm).setOnClickListener {
            val username = usernameView.text.toString()
            val darkMode = darkModeView.isChecked
            if (username.isNotEmpty()){
                sharedPreferences.edit().putString(USERNAME, username).apply()
                sharedPreferences.edit().putBoolean(DARKMODE, darkMode).apply()
                if (darkMode) AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
                else AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
                finish()
            }else{
                val toast = Toast.makeText(applicationContext, "Please enter a Username", Toast.LENGTH_LONG)
                toast.show()
            }

        }
    }
}