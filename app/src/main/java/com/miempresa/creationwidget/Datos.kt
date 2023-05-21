package com.miempresa.creationwidget

import android.appwidget.AppWidgetManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


import kotlinx.android.synthetic.main.*
import kotlinx.android.synthetic.main.activity_datos.*

import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.Array.get


private var widgetId = 0

class Datos : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datos)

        val recibidowidget = intent
        val parametros =  recibidowidget.extras
        if (parametros != null){
            //Se obtiene el ID del widget que se esta configurando
            widgetId = parametros.getInt(
                AppWidgetManager.EXTRA_APPWIDGET_ID , AppWidgetManager.INVALID_APPWIDGET_ID

            )
        }

        //se establece un resultado por defecto (cuando se pulasa el boton
        //de Atras del telefono, este sera el mensaje mostrado)
        setResult(RESULT_CANCELED)

        btnAceptar.setOnClickListener( {     //se apertura un archivo de preferencias
            //para escribir datos que almacene la actividad
            val datos = getSharedPreferences("Datos" , MODE_PRIVATE)

            //se apertura editor paa guardar datos
            val editor = datos.edit()
            editor.putString("mensaje" , txtEnviar.getText().toString())
            //aplicar los cambios
            editor.commit()

            //se apertura editor paa guardar datos
            val editor2 = datos.edit()
            editor2.putString("mensaje2" , txtEnviar2.getText().toString())
            //aplicar los cambios
            editor2.commit()

            val notifcarwidget = AppWidgetManager.getInstance(this)
            val usoClaseWidget = tarea_widget()
            usoClaseWidget.actualizarWidget(this , notifcarwidget , widgetId)

            //se devuelve como resultado : ACEPTAR(RESULT_OK)
            val resultado = Intent()
            resultado.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID , widgetId)
            setResult(RESULT_OK , resultado)
            finish()
        })

        btnCancelar.setOnClickListener(  {finish()})



    }
}