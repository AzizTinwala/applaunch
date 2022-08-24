package io.applaunch.applaunchmini.session

import android.content.Context
import android.content.SharedPreferences

class SessionManagement(context: Context) {

    //Shared Preferences
    private var pref: SharedPreferences? = null

    //Editor For Shared Preferences
    private var editor: SharedPreferences.Editor? = null

    //Context
    private var _context: Context? = null

    //Shared Preferences Mode
    private val privateMode: Int = 0

    /**
     * Initializing the Variables
     */
    init {

        this._context = context
        pref = _context!!.getSharedPreferences(PREF_NAME, privateMode)
        editor = pref?.edit()

    }

    /**
     * Creating A Login  Session
     * */

    fun createLoginSession(
    ) {

        //Storing Login Value As TRUE
        editor!!.putBoolean(IS_LOGIN, true)

        //Commit Changes
        editor!!.commit()
    }

    /**
     * Quick Check For Login */

    fun isLoggedIn(): Boolean {
        return pref!!.getBoolean(IS_LOGIN, false)
    }

    companion object {
        //Shared Preferences File Name
        const val PREF_NAME = "MyApp"

        //All Shared Preferences Keys
        //Login Key
        const val IS_LOGIN = "IsLoggedIn"
    }
}