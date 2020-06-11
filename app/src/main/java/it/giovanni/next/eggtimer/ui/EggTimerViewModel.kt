package it.giovanni.next.eggtimer.ui

import android.app.AlarmManager
import android.app.Application
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.os.SystemClock
import androidx.core.app.AlarmManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import it.giovanni.next.R
import it.giovanni.next.eggtimer.receiver.AlarmReceiver
import it.giovanni.next.eggtimer.util.cancelNotifications
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EggTimerViewModel(private val app: Application) : AndroidViewModel(app) {

    private companion object {
        private const val MINUTE: Long = 60_000L
        private const val SECOND: Long = 1_000L
        private const val REQUEST_CODE = 0
        private const val TRIGGER_TIME = "TRIGGER_AT"
    }

    private val timerLengthOptions: IntArray
    private val notifyPendingIntent: PendingIntent

    private val alarmManager = app.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    private var prefs = app.getSharedPreferences("eggTimerNotification", Context.MODE_PRIVATE)
    private val notifyIntent = Intent(app, AlarmReceiver::class.java)

    private val mTimeSelection = MutableLiveData<Int>()
    val timeSelection: LiveData<Int>
        get() = mTimeSelection

    private val mElapsedTime = MutableLiveData<Long>()
    val elapsedTime: LiveData<Long>
        get() = mElapsedTime

    private var mAlarmOn = MutableLiveData<Boolean>()
    val isAlarmOn: LiveData<Boolean>
        get() = mAlarmOn

    private lateinit var timer: CountDownTimer

    init {
        mAlarmOn.value = PendingIntent.getBroadcast(
            getApplication(),
            REQUEST_CODE,
            notifyIntent,
            PendingIntent.FLAG_NO_CREATE
        ) != null

        notifyPendingIntent = PendingIntent.getBroadcast(
            getApplication(),
            REQUEST_CODE,
            notifyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        timerLengthOptions = app.resources.getIntArray(R.array.minutes_array)

        // If alarm is not null, resume the timer back for this alarm
        if (mAlarmOn.value!!) {
            createTimer()
        }
    }

    // Turns on or off the alarm
    fun setAlarm(isChecked: Boolean) {
        when (isChecked) {
            true -> timeSelection.value?.let { startTimer(it) }
            false -> cancelNotification()
        }
    }

    // Sets the desired interval for the alarm
    fun setTimeSelected(timerLengthSelection: Int) {
        mTimeSelection.value = timerLengthSelection
    }

    // Creates a new alarm, notification and timer
    private fun startTimer(timerLengthSelection: Int) {
        mAlarmOn.value?.let {
            if (!it) {
                mAlarmOn.value = true
                val selectedInterval = when (timerLengthSelection) {
                    0 -> SECOND * 10 //For testing only
                    else -> timerLengthOptions[timerLengthSelection] * MINUTE
                }
                val triggerTime = SystemClock.elapsedRealtime() +
                        selectedInterval

                // Get an instance of NotificationManager and call sendNotification
                // val notificationManager = ContextCompat.getSystemService(app, NotificationManager::class.java) as NotificationManager
                // notificationManager.sendNotification(app.getString(R.string.timer_running), app)

                // Get an instance of NotificationManager and call cancelNotifications
                val notificationManager = ContextCompat.getSystemService(app, NotificationManager::class.java) as NotificationManager
                notificationManager.cancelNotifications()

                AlarmManagerCompat.setExactAndAllowWhileIdle(
                    alarmManager,
                    AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    triggerTime,
                    notifyPendingIntent
                )

                viewModelScope.launch {
                    saveTime(triggerTime)
                }
            }
        }
        createTimer()
    }

    // Creates a new timer
    private fun createTimer() {
        viewModelScope.launch {

            val triggerTime = loadTime()

            timer = object : CountDownTimer(triggerTime, SECOND) {

                override fun onTick(millisUntilFinished: Long) {

                    mElapsedTime.value = triggerTime - SystemClock.elapsedRealtime()
                    if (mElapsedTime.value!! <= 0) {
                        resetTimer()
                    }
                }

                override fun onFinish() {
                    resetTimer()
                }
            }
            timer.start()
        }
    }

    // Cancels the alarm, notification and resets the timer
    private fun cancelNotification() {
        resetTimer()
        alarmManager.cancel(notifyPendingIntent)
    }

    // Resets the timer on screen and sets alarm value false
    private fun resetTimer() {
        timer.cancel()
        mElapsedTime.value = 0
        mAlarmOn.value = false
    }

    private suspend fun saveTime(triggerTime: Long) = withContext(Dispatchers.IO) {
            prefs.edit().putLong(TRIGGER_TIME, triggerTime).apply()
        }

    private suspend fun loadTime(): Long = withContext(Dispatchers.IO) {
            prefs.getLong(TRIGGER_TIME, 0)
        }
}
