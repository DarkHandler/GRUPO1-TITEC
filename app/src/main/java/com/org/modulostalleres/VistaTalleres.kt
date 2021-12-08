package com.org.modulostalleres


import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.SnackbarContentLayout
import com.google.gson.GsonBuilder
import com.org.modulostalleres.databinding.ActivityVistaTalleresBinding
//import com.org.modulostalleres.databinding.ActivityVistaTalleresBinding
import org.json.JSONObject
import java.util.*


class VistaTalleres : AppCompatActivity() {

    private lateinit var binding: ActivityVistaTalleresBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_talleres)

        val recyclerViewMain = findViewById<RecyclerView>(R.id.recyclerViewMain)

        recyclerViewMain.setBackgroundColor(Color.GRAY)
        recyclerViewMain.layoutManager = LinearLayoutManager(this)
        //recyclerViewMain.adapter = MainAdapter()

        fetchJSON("talleres", recyclerViewMain) //traer datos
    }


    private fun fetchJSON(path:String, recyclerViewMain: RecyclerView){
        val url = "http://10.150.45.137:3000/api/"+path
        val request = Request.Builder().url(url).build()

        val leceeRecyclerView = LCEERecyclerView(this)

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                //println(e)
                //println("Failed to execute the request")
                //mostrar la vista error
                runOnUiThread {
                    showAlertDialog(recyclerViewMain, "Algo ha ido mal!")
                }
            }
            override fun onResponse(call: okhttp3.Call, response: Response) {
                val body = response.body?.string()
                println(body)
                val gson = GsonBuilder().create()
                val homeFeed = HomeFeed(gson.fromJson(body, Array<Taller>::class.java).toList())
                if(homeFeed.talleres.count() == 0) { //handle if there are no talleres
                    runOnUiThread{
                        showAlertDialog(recyclerViewMain, "No hay nada que mostrar")
                    }
                }else{
                    runOnUiThread {
                        recyclerViewMain.adapter = MainAdapter(homeFeed)
                    }
                }
            }
        })
    }

    fun showAlertDialog(view: View, message:String){
        MaterialAlertDialogBuilder(this)
            .setTitle("Mensaje de error")
            .setMessage(message)
            .show()
    }


}

class HomeFeed(val talleres: List<Taller>)

class Taller(val codigo_actividad:Int, val nombre_actividad:String, val modalidad:String, val area:String, val fecha_inicio:String)

/*
class Taller(val rut_responsable:String, val tipo:String, val cupos:String, val direccion:String,
             val nombre_actividad:String, val estado_actividad:String, val descripcion:String,
             val fecha_inicio:String, val fecha_termino:String, val modalidad:String,
             val requisitos:String, val area:String) */