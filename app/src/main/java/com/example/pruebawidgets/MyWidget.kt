package com.example.pruebawidgets

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import com.example.pruebawidgets.databinding.MyWidgetBinding


/**
 * Implementation of App Widget functionality.
 */
class MyWidget : AppWidgetProvider() {

    private val ipsum = LoremIpsum(100)
    private var string = ""
    private var actual = 0
    private val patras = "patras"
    private val palante = "palante"
    private lateinit var views: RemoteViews


    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            for(i in actual*10..(actual*10)+10){
                string += ipsum.values.elementAt(0).elementAt(i)
            }
            // Construct the RemoteViews object
            views = RemoteViews(context.packageName, R.layout.my_widget)
            views.setTextViewText(R.id.texto, string)
            views.setOnClickPendingIntent(R.id.patras, getPendingSelfIntent(context, patras))
            views.setOnClickPendingIntent(R.id.palante, getPendingSelfIntent(context, palante))
            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    override fun onEnabled(context: Context) {
        for(i in actual*10..(actual*10)+10){
            string += ipsum.values.elementAt(0).elementAt(i)
        }
        // Construct the RemoteViews object
        views = RemoteViews(context.packageName, R.layout.my_widget)
        views.setTextViewText(R.id.texto, string)
        views.setOnClickPendingIntent(R.id.patras, getPendingSelfIntent(context, patras))
        views.setOnClickPendingIntent(R.id.palante, getPendingSelfIntent(context, palante))
        // Instruct the widget manager to update the widget
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    protected fun getPendingSelfIntent(context: Context?, action: String?): PendingIntent? {
        val intent = Intent(context, javaClass)
        intent.action = action
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    override fun onReceive(context: Context?, intent: Intent) {
        if (patras.equals(intent.action)) {
            if(actual < 10){
                actual++
                string = ""
                for(i in actual*10..(actual*10)+10){
                    string += ipsum.values.elementAt(0).elementAt(i)
                }
            }
        }
        if (palante.equals(intent.action)) {
            if(actual > 0){
                actual--
                string = ""
                for(i in actual*10..(actual*10)+10){
                    string += ipsum.values.elementAt(0).elementAt(i)
                }
            }
        }
        views.setTextViewText(R.id.texto, string)
    }
}

