package com.miempresa.creationwidget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import java.text.SimpleDateFormat
import java.util.*

import android.service.voice.VoiceInteractionSession




/**
 * Implementation of App Widget functionality.
 */

const val control_widget = "control_widget"


class tarea_widget : AppWidgetProvider() {

    fun actualizarWidget(context: Context , appWidgetManager: AppWidgetManager ,widgetId: Int){

        val datos = context.getSharedPreferences("Datos", Context.MODE_PRIVATE)

        val mensaje = datos.getString("mensaje" , "Mensaje recibido")
        val mensaje2 = datos.getString("mensaje2" , "78")

        val controles = RemoteViews(context.packageName , R.layout.tarea_widget)
        controles.setTextViewText(R.id.lblMensaje, mensaje)
        controles.setTextViewText(R.id.lblMensaje2, mensaje2 + "°")



        val sdfDate =  SimpleDateFormat("HH.mm a")
        val now = Date()
        val hora = sdfDate.format(now)
        controles.setTextViewText(R.id.lblhora , hora)

        val sdfFecha =  SimpleDateFormat("E.LLLL.dd ")
        val nowf = Date()
        val fecha = sdfFecha.format(nowf)
        controles.setTextViewText(R.id.lblfecha , fecha)




        //Se crea el control de evento cuando se haga clicken el widget
        val clicenwidget = Intent(context , Datos::class.java)
        val widgetesperando = PendingIntent.getActivity(
            context,
            widgetId,
            clicenwidget,
            PendingIntent.FLAG_CANCEL_CURRENT
        )
        controles.setOnClickPendingIntent(R.id.frmWidget,widgetesperando)

        //se crea un control de evento cuando se haga clicken el boton del widget
        val botonwidget = Intent(context , tarea_widget::class.java)
        botonwidget.action = control_widget
        botonwidget.putExtra("appWidgetId" , widgetId)

        val botonespera = PendingIntent.getBroadcast(
            context,
            0,
            botonwidget,
            PendingIntent.FLAG_CANCEL_CURRENT
        )
        controles.setOnClickPendingIntent(R.id.lblhora,botonespera)

        //se notifica al appWidgetManager de la actualizacion efectuada
        appWidgetManager.updateAppWidget(widgetId, controles)


    }




    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (control_widget == intent?.action){
            val widgetId = intent.getIntExtra("appWidgetId" , 0)
            actualizarWidget(context!! , AppWidgetManager.getInstance(context), widgetId)
        }
        super.onReceive(context, intent)
    }


}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val widgetText = context.getString(R.string.appwidget_text)
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.tarea_widget)
    //views.setTextViewText(R.id.appwidget_text, widgetText)

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}