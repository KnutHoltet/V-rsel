package com.example.vaersel.data.streak

import android.content.Context
import java.util.Calendar

class StreakManager(context: Context) {
    companion object { //konstanter
        private const val PREF_NAME = "StreakPrefs"
        private const val STREAK_COUNT_KEY = "streak_count"
        private const val LAST_DAY_KEY = "last_day"
        private const val STREAK_RECORD_KEY = "streak_record"
    }

    /* object for lagring av info på enhet */
    private val sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    /* funksjon for å claime dagens streak */
    fun recordDailyAction() {
        val cal = Calendar.getInstance()

        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)

        val today = cal.timeInMillis
        val lastDay = sharedPref.getLong(LAST_DAY_KEY, 0)

        if (today == lastDay){ //streak er allerede claimet for i dag
            return
        }

        val editor = sharedPref.edit()

        val streakRecord = sharedPref.getInt(STREAK_RECORD_KEY, 0)

        if (isYesterday(lastDay)){ //streak skal økes
            val newStreakCount = sharedPref.getInt(STREAK_COUNT_KEY, 0) + 1
            editor.putInt(STREAK_COUNT_KEY, newStreakCount)


            if (streakRecord < newStreakCount) { // streakRecord skal økes
                editor.putInt(STREAK_RECORD_KEY, newStreakCount)
            }
        }
        else { //streak skal resettes
            editor.putInt(STREAK_COUNT_KEY, 1)
            if (streakRecord == 0){ editor.putInt(STREAK_RECORD_KEY, 1) }
        }
        editor.putLong(LAST_DAY_KEY, today) //oppdaterer last day
        editor.apply() //committer nye verdier
    }

    /* Sjekker om forrige streak-dag var i går */
    private fun isYesterday(lastDay: Long): Boolean {
        val yesterday = Calendar.getInstance()

        yesterday.add(Calendar.DAY_OF_YEAR, -1)
        yesterday.set(Calendar.HOUR_OF_DAY, 0)
        yesterday.set(Calendar.MINUTE, 0)
        yesterday.set(Calendar.SECOND, 0)
        yesterday.set(Calendar.MILLISECOND, 0)

        return lastDay >= yesterday.timeInMillis
    }

    fun getCurrentStreakCount(): Int { //returnerer streaklengde
        return sharedPref.getInt(STREAK_COUNT_KEY, 0)
    }
    fun getStreakRecordCount(): Int { //returnerer streak-rekord
        return sharedPref.getInt(STREAK_RECORD_KEY, 0)
    }
}