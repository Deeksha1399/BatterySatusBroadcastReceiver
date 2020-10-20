package com.example.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var textView: TextView
    var batteryStatus: StringBuilder = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.textView)

        val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        this.registerReceiver(broadcastReceiver, intentFilter)
    }

    private val broadcastReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            val percentage = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
            batteryStatus.append("Battery percentage: $percentage \n")
            val statusOfBattery = intent?.getIntExtra(BatteryManager.EXTRA_STATUS, 0)

            //textView.text = textView.text.toString() + "\nBattery Status: "
            batteryStatus.append("\nBattery Status")

            when (statusOfBattery) {
                BatteryManager.BATTERY_STATUS_FULL ->
                    //textView.text = textView.text.toString() + "\Battery Full "
                    batteryStatus.append("\nBattery full")

                BatteryManager.BATTERY_STATUS_CHARGING ->
                    //textView.text = textView.text.toString() + "\nCharger connected "
                    batteryStatus.append("\nCharger Connected")

                BatteryManager.BATTERY_STATUS_DISCHARGING ->
                    //textView.text = textView.text.toString() + "\nBattery discharging "
                    batteryStatus.append("\nBattery discharging")

                BatteryManager.BATTERY_STATUS_NOT_CHARGING ->
                    //textView.text = textView.text.toString() + "\nCharger disconnected "
                    batteryStatus.append("\nBattery disconnected")

                BatteryManager.BATTERY_STATUS_UNKNOWN ->
                    //textView.text = textView.text.toString() + "\nStatus Unknown "
                    batteryStatus.append("\nBattery status unknown")

                else ->
                    batteryStatus.append("\nBattery status unknown")
            }

            batteryStatus.append("\n\nBattery Health")

            when (intent?.getIntExtra(BatteryManager.EXTRA_HEALTH, 0)) {
                BatteryManager.BATTERY_HEALTH_OVERHEAT ->
                    batteryStatus.append("\nOver heat")

                BatteryManager.BATTERY_HEALTH_GOOD ->
                    batteryStatus.append("\nGood")

                BatteryManager.BATTERY_HEALTH_COLD ->
                    batteryStatus.append("\nCold")

                BatteryManager.BATTERY_HEALTH_DEAD ->
                    batteryStatus.append("\nDead")

                BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE ->
                    batteryStatus.append("\nOver Voltage")

                BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE ->
                    batteryStatus.append("\nFailure")

                else ->
                    batteryStatus.append("\nUnknown")
            }
            textView.text = batteryStatus
        }
    }

//    override fun onStart() {
//        registerReceiver(broadcastReceiver,IntentFilter(Intent.ACTION_BATTERY_CHANGED))
//        super.onStart()
//    }

    override fun onStop() {
        unregisterReceiver(broadcastReceiver)
        super.onStop()
    }
}