package com.org.modulostalleres

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.org.modulostalleres.databinding.ActivityVistaTalleresBinding
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class VistaTalleres : AppCompatActivity() {

    private lateinit var binding: ActivityVistaTalleresBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_talleres)


        // Capture the layout's TextView and set the string as its text
       //findViewById<TextView>(R.id.textView3).apply {
       //     text = "que bueno"
       //}
        fetchJSON()
    }

    fun showTalleres(data: String?){
        val json_data =  JSONArray(data) //arrayOf(JSONArray(data))
        //println(json_data)

        println("----------------------------------")
        for(i in 0 until json_data.length()){
            val item = json_data.getJSONObject(i)
            //println("${item.get("nombre_actividad")}")
            println(item)
        }
        println("----------------------------------")
        println("")


        /*
        val layout = findViewById<RelativeLayout>(R.id.talleres)

        val textView = TextView(this)
        //setting height and width
        textView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)

        // setting text
        textView.setText("GEEKSFORGEEKS")
        textView.textSize=20F
        textView.setTextColor(Color.RED)
        // onClick the text a message will be displayed "HELLO GEEK"
        textView.setOnClickListener()
        {
            Toast.makeText(this, "HELLO GEEK",
                Toast.LENGTH_LONG).show()
        }
        // Add TextView to LinearLayout
        layout?.addView(textView)
        */

    }


    //buscar orquestacion de servicios

    fun fetchJSON(){
        //println("Hello world")
        val url = "http://10.150.45.137:3000/api/talleres"

        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: okhttp3.Call, e: IOException) {
                println(e)
                println("Failed to execute the request")
            }

            override fun onResponse(call: okhttp3.Call, response: Response) {
                val body = response.body?.string()
                //println("Response:")
                //println(body)
                showTalleres(body)
            }
        })

    }

}