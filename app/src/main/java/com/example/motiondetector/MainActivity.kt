package com.example.motiondetector

import android.content.DialogInterface
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import kotlin.math.pow
import kotlin.math.sqrt

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var alertDialog: AlertDialog? = null
    private var isMoving: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        alertDialog = AlertDialog.Builder(this)
            .setTitle("Motion Detected")
            .setMessage("You are in motion. Don't use your phone while you are moving.")
            .setPositiveButton("OK"){ dialog, _ ->
                dialog.dismiss()
                isMoving = false
            }
            .create()
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {

                val x = event?.values?.get(0)
                val y = event?.values?.get(1)
                val z = event?.values?.get(2)
                var acceleration = Math.sqrt(x!!.toDouble().pow(2.0) + y!!.toDouble().pow(2.0) + z!!.toDouble().pow(2.0))
                if (acceleration > 20) {
                    isMoving = true
                }
        if (isMoving) {
            alertDialog?.show()

        }
        else{
            alertDialog?.dismiss()
        }
    }
}





