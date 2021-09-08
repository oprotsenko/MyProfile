import android.content.Context

const val PREFS_FILE_NAME = "myProfileSettings"


class PreferenceStorage constructor(private val context: Context) {


    fun save(_key: String, _value: String) {
        val prefs = context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
        val prefsEdit = prefs.edit()


        prefsEdit.putString(_key, _value)
        prefsEdit.apply()
    }


    fun save(_key: String, _value: Int) {
        val prefs = context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
        val prefsEdit = prefs.edit()


        prefsEdit.putInt(_key, _value)
        prefsEdit.apply()
    }


    fun save(_key: String, _value: Boolean) {
        val prefs = context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
        val prefsEdit = prefs.edit()


        prefsEdit.putBoolean(_key, _value)
        prefsEdit.apply()
    }


    fun save(_key: String, _value: Long) {
        val prefs = context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
        val prefsEdit = prefs.edit()


        prefsEdit.putLong(_key, _value)
        prefsEdit.apply()
    }


    fun save(_key: String, _value: Double) {
        val prefs = context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
        val prefsEdit = prefs.edit()


        prefsEdit.putFloat(_key, _value.toFloat())
        prefsEdit.apply()
    }


    fun save(_key: String, _value: Float) {
        val prefs = context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
        val prefsEdit = prefs.edit()


        prefsEdit.putFloat(_key, _value)
        prefsEdit.apply()
    }


    fun getString(_key: String, default: String = ""): String? {
        val prefs = context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
        return prefs.getString(_key, default)
    }


    fun getFloat(_key: String): Float {
        val prefs = context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
        return prefs.getFloat(_key, 0f)
    }


    fun getBoolean(_key: String): Boolean {
        val prefs = context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(_key, false)
    }


    fun getBoolean(_key: String, _def: Boolean): Boolean {
        val prefs = context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(_key, _def)
    }


    fun getInt(_key: String): Int {
        val prefs = context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
        return prefs.getInt(_key, 0)
    }


    fun getInt(_key: String, default: Int): Int {
        val prefs = context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
        return prefs.getInt(_key, default)
    }


    fun getLong(_key: String, default: Long): Long {
        val prefs = context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
        return prefs.getLong(_key, default)
    }


    fun getLong(_key: String): Long {
        val prefs = context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
        return prefs.getLong(_key, 0L)
    }


    fun removePrefValue(key: String) {
        val prefs = context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
        val prefsEdit = prefs.edit()
        prefsEdit.remove(key)
        prefsEdit.apply()
    }


    fun contains(_key: String): Boolean {
        val prefs = context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
        return prefs.contains(_key)
    }


    fun clearPrefs() {
        val prefs = context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
    }
}