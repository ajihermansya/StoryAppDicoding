package com.rumahproduksi.storyappdicoding.activity_utils.preferences

import android.content.Context
import android.content.SharedPreferences
import hu.autsoft.krate.Krate
import hu.autsoft.krate.booleanPref
import hu.autsoft.krate.default.withDefault
import hu.autsoft.krate.stringPref

class PreferManager (context: Context) : Krate {

    override val sharedPreferences: SharedPreferences = context.applicationContext.getSharedPreferences("prefer_manager", Context.MODE_PRIVATE)

    var exampleBoolean by booleanPref().withDefault(false)
    var token by stringPref().withDefault("")

    fun clear() {
        sharedPreferences.edit().clear().apply()
    }


}