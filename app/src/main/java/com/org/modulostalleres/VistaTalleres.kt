package com.org.modulostalleres


import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import java.io.IOException
import android.annotation.SuppressLint
import org.json.JSONObject


class VistaTalleres : AppCompatActivity() {

    //private lateinit var binding: ActivityVistaTalleresBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_talleres)

        fetchJSON("talleres") //traer datos
    }


    @SuppressLint("ResourceType")
    fun showTalleres(data: String?){ //rederizar datos en vista
        val jsonData =  JSONArray(data)
        val layout = findViewById<RelativeLayout>(R.id.talleres)


        for(i in 0 until jsonData.length()){

            val width=160 //tamanios de los botones
            val height=90
            val item = jsonData.getJSONObject(i)

            val textNombre = generateTextView(item, "nombre_actividad", 1)
            val textModalidad = generateTextView(item, "modalidad", 2)
            val textArea = generateTextView(item, "area", 3)
            val textFechaInicio = generateTextView(item, "fecha_inicio", 4)


            val btnMasDetalle = Button(this)
            btnMasDetalle.text = "Más detalles"
            btnMasDetalle.textSize = 10F
            btnMasDetalle.id = 111+(item.get("codigo_actividad") as Int)

            if(i == 0){
                //PRIMER ATT
                val rlp2 = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
                rlp2.addRule(RelativeLayout.ALIGN_PARENT_TOP)
                textNombre.layoutParams = rlp2
                layout.addView(textNombre)


                //SEGUNDO ATT
                val rlp3 = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
                rlp3.addRule(RelativeLayout.ALIGN_PARENT_TOP)
                rlp3.addRule(RelativeLayout.BELOW,textNombre.id)
                textModalidad.layoutParams = rlp3
                layout.addView(textModalidad)


                //TERCERO ATT
                val rlp4 = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
                rlp4.addRule(RelativeLayout.ALIGN_PARENT_TOP)
                rlp4.addRule(RelativeLayout.BELOW,textModalidad.id)
                textArea.layoutParams = rlp4
                layout.addView(textArea)

                //CUARTO ATT
                val rlp5 = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
                rlp5.addRule(RelativeLayout.ALIGN_PARENT_TOP)
                rlp5.addRule(RelativeLayout.BELOW,textArea.id)
                textFechaInicio.layoutParams = rlp5
                layout.addView(textFechaInicio)

                //BOTON
                val rlp1: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(
                    width, height
                )
                rlp1.addRule(RelativeLayout.CENTER_HORIZONTAL)
                rlp1.addRule(RelativeLayout.BELOW, textFechaInicio.id)
                btnMasDetalle.layoutParams = rlp1
                layout.addView(btnMasDetalle)

            }else {  //si no es el primer item, se posiciona debajo parece
                //PRIMER ATT
                val rlp2 = RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
                )
                rlp2.addRule(RelativeLayout.CENTER_HORIZONTAL)
                rlp2.addRule(RelativeLayout.BELOW, btnMasDetalle.id)
                textNombre.layoutParams = rlp2
                layout.addView(textNombre)

                /*
                //SEGUNDO ATT
                val rlp3 = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
                rlp3.addRule(RelativeLayout.CENTER_HORIZONTAL)
                rlp3.addRule(RelativeLayout.BELOW, textNombre.id - 1)
                textModalidad.layoutParams = rlp3
                layout.addView(textModalidad)

                //TERCERO ATT
                val rlp4 = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
                rlp4.addRule(RelativeLayout.CENTER_HORIZONTAL)
                rlp4.addRule(RelativeLayout.BELOW, textModalidad.id - 1)
                textArea.layoutParams = rlp4
                layout.addView(textArea)

                //CUARTO ATT
                val rlp5 = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
                rlp5.addRule(RelativeLayout.CENTER_HORIZONTAL)
                rlp5.addRule(RelativeLayout.BELOW, textArea.id - 1)
                textFechaInicio.layoutParams = rlp5
                layout.addView(textFechaInicio)
                 */

                //BOTON
                val rlp1 = RelativeLayout.LayoutParams(
                    width, height
                )
                rlp1.addRule(RelativeLayout.CENTER_HORIZONTAL)
                rlp1.addRule(RelativeLayout.BELOW, textNombre.id)
                btnMasDetalle.layoutParams = rlp1
                layout.addView(btnMasDetalle)
            }

        }

    }

    private fun generateTextView(item: JSONObject, attribute:String, adder:Int): TextView {
        val textView = TextView(this) //Nombre actividad
        textView.text = "${item.get(attribute)}"
        textView.textSize = 20F
        textView.setTextColor(Color.WHITE)
        textView.id = adder+(item.get("codigo_actividad") as Int)

        return textView
    }


    private fun fetchJSON(path:String){
        val url = "http://10.150.45.137:3000/api/"+path
        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                println(e)
                println("Failed to execute the request")
            }
            override fun onResponse(call: okhttp3.Call, response: Response) {
                val body = response.body?.string()
                runOnUiThread {
                    showTalleres(body)
                }
            }
        })

    }

}