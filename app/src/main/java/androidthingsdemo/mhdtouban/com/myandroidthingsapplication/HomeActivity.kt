package androidthingsdemo.mhdtouban.com.myandroidthingsapplication

import android.app.Activity
import android.hardware.Sensor
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import com.google.android.things.contrib.driver.bmx280.Bmx280
import com.google.android.things.contrib.driver.ht16k33.AlphanumericDisplay
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat
import com.google.android.things.pio.Gpio

/**
 * Skeleton of an Android Things activity.
 *
 * Android Things peripheral APIs are accessible through the class
 * PeripheralManagerService. For example, the snippet below will open a GPIO pin and
 * set it to HIGH:
 *
 * <pre>{@code
 * val service = PeripheralManagerService()
 * val mLedGpio = service.openGpio("BCM6")
 * mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)
 * mLedGpio.value = true
 * }</pre>
 * <p>
 * For more complex peripherals, look for an existing user-space driver, or implement one if none
 * is available.
 *
 * @see <a href="https://github.com/androidthings/contrib-drivers#readme">https://github.com/androidthings/contrib-drivers#readme</a>
 *
 */
class HomeActivity : Activity() {

    private lateinit var handler: Handler
    private lateinit var display: AlphanumericDisplay
    private lateinit var sensor: Bmx280
    private lateinit var led: Gpio

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        display = RainbowHat.openDisplay()
        display.setEnabled(true)

        sensor = RainbowHat.openSensor()
        sensor.setMode(Bmx280.MODE_NORMAL)
        sensor.temperatureOversampling = Bmx280.OVERSAMPLING_1X

        led =  RainbowHat.openLedRed()
        led.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)

        handler = Handler(mainLooper)

        runOnReschedule()
        /*led = RainbowHat.openLedRed()
        // led.value = true
        var value = true
        while (true) {
            led.value = value
            value  = !value
            SystemClock.sleep(300)
        }
*/
    }

    private fun runOnReschedule() {
        display.display(sensor.readTemperature().toInt())
    }
}
