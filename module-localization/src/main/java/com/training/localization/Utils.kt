package com.training.localization

import android.content.Context
import android.util.Log
import com.google.gson.JsonParseException
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

object Utils {
    fun getJsonFromAssets(context: Context, fileName: String, fileType: String = "json"): String? {
        val jsonString: String = try {
            val `is` = context.assets.open("$fileName.$fileType")
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer, Charsets.UTF_8)
            String(buffer)
        } catch (e: IOException) {
            Log.d("Localization",e.message.toString())
            e.printStackTrace()
            return null
        }
        return jsonString
    }

    fun isValidJsonString(value:String):Boolean{
        try {
            val x = JSONObject(value)
        }
        catch (e: JSONException){
            return false
        }
        catch (e: JsonParseException){
            return false
        }
        return true
    }

}