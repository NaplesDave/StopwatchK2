package com.hfad.stopwatchk2
// May 14, 2022 David King
// Head First Android Development, 3rd ed
// chapter 5  StopWatch   KOTLIN using the Chronometer Widget
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.Button
import android.widget.Chronometer
import kotlin.io.print as kotlinIoPrint


class MainActivity : AppCompatActivity() {

    // declare the vars to hold our values
    lateinit var stopwatch: Chronometer  // The stopwatch
    var running = false // is the stopwatch running?
    var offset: Long = 0 // The base offset for the stopwatch

    // Add key Strings for use with Bundle data
    val OFFSET_KEY = "offset"
    val RUNNING_KEY = "running"
    val BASE_KEY = "base"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // get a ref to the stopwatch view
        stopwatch = findViewById<Chronometer>(R.id.stopwatch)

    // Restore the previous state if it had one
        if (savedInstanceState != null) {
          //Log.i("MyTag", "loading instance data section")
            offset = savedInstanceState.getLong(OFFSET_KEY)
            running = savedInstanceState.getBoolean(RUNNING_KEY)
         //   Log.i("MyTag", running.toString() )
            if (running) {
          //    Log.i("MyTag", "loading running data")
                stopwatch.base = savedInstanceState.getLong(BASE_KEY)
                stopwatch.start()
            }else {
                setBaseTime()
                kotlinIoPrint("was not running load base state")
            }

        }



        // the start button starts the stopwatch if it's not running
        val startButton = findViewById<Button>(R.id.start_button)
        startButton.setOnClickListener {
            if (!running){
                setBaseTime()
                stopwatch.start()
                running = true
                Log.i("MyTag", running.toString())
            }
        } // end startbutton fun

        // the pause button pauses the stopwatch if it's running
        val pauseButton = findViewById<Button>(R.id.pause_button)
        pauseButton.setOnClickListener {
            if (running) {
                saveOffset()
                stopwatch.stop()
                running = false
            }
        } // end pausebutton func

        // the reset button sets the offset and stopwatch to 0
        val resetButton = findViewById<Button>(R.id.reset_button)
        resetButton.setOnClickListener {
            offset = 0
            setBaseTime()

        }

    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
     // Log.i("MyTag", "instance data saved!")
        savedInstanceState.putLong(OFFSET_KEY, offset)
        savedInstanceState.putBoolean(RUNNING_KEY, running)
        savedInstanceState.putLong(BASE_KEY, stopwatch.base)
        super.onSaveInstanceState(savedInstanceState)
        
    }
    
    
    // update the stopwatch.base time, allowing for any offset
    fun setBaseTime() {
        stopwatch.base = SystemClock.elapsedRealtime() - offset
    }

    // record the offset
    fun saveOffset() {
        offset = SystemClock.elapsedRealtime() - stopwatch.base
    }
}