package com.org.modulostalleres



import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.GsonBuilder
import com.org.modulostalleres.databinding.ActivityVistaTalleresBinding
import java.util.*


class VistaTalleres : AppCompatActivity() {

    private lateinit var binding: ActivityVistaTalleresBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVistaTalleresBinding.inflate(layoutInflater)
        setContentView(binding.root)


        fetchJSON("talleres")//, recyclerViewMain) //traer datos

        // handle the retry button
        binding.lceeRecyclerView.setOnRetryClickListener {
            fetchJSON("talleres")
        }
    }


    private fun fetchJSON(path:String ){
        val url = "http://10.150.45.137:3000/api/"+path
        val request = Request.Builder().url(url).build()

        val talleresVacio = ArrayList<Taller>()
        var homeFeed = HomeFeed(talleresVacio)
        binding.lceeRecyclerView.recyclerView.adapter = MainAdapter(homeFeed)

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                runOnUiThread {
                    //me genera un nombre personalizado por el id, es por esto que puedo obtener de binding recylcerView
                    binding.lceeRecyclerView.showErrorView()
                }
            }
            override fun onResponse(call: okhttp3.Call, response: Response) {
                val body = response.body?.string()
                println(body)
                val gson = GsonBuilder().create()
                homeFeed = HomeFeed(gson.fromJson(body, Array<Taller>::class.java).toList())
                if(homeFeed.talleres.count() == 0) { //handle if there are no
                    runOnUiThread{
                        binding.lceeRecyclerView.recyclerView.adapter = MainAdapter(homeFeed)
                        binding.lceeRecyclerView.showEmptyView()
                    }

                }else{
                    runOnUiThread{
                        binding.lceeRecyclerView.recyclerView.setBackgroundColor(Color.GRAY)
                        binding.lceeRecyclerView.recyclerView.adapter = MainAdapter(homeFeed)
                        binding.lceeRecyclerView.hideAllViews()
                    }
                }
            }
        })
    }



}

class HomeFeed(val talleres: List<Taller>)

class Taller(val codigo_actividad:Int, val nombre_actividad:String, val modalidad:String, val area:String, val fecha_inicio:String)
