package br.goldbach.weatherapp.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.goldbach.weatherapp.R
import br.goldbach.weatherapp.data.model.Hour
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject
import kotlin.math.roundToInt

@InstallIn(ActivityComponent::class)
@Module
class ForecastTodayAdapter @Inject constructor(
    private var forecastHours: List<Hour?>?,
    @ActivityContext private val context: Context
) : RecyclerView.Adapter<ForecastTodayAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater: View =
            LayoutInflater.from(context).inflate(R.layout.forecastday_viewholder, parent, false)
        return ViewHolder(inflater)
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.forecastTempTxt.text =
            forecastHours?.get(position)?.tempC?.roundToInt().toString() + "°"
        holder.forecastTimeTxt.text =
            forecastHours?.get(position)?.time?.split(" ")?.get(1) ?: "00:00"
        holder.forecastHumidityTxt.text = forecastHours?.get(position)?.humidity.toString() + "%"

        Picasso
            .get()
            .load("https:${forecastHours?.get(position)?.condition?.icon}")
            .placeholder(R.drawable.loading_img)
            .error(R.drawable.ic_broken_image)
            .into(holder.forecastImageTxt)

    }

    override fun getItemCount(): Int {
        return 24
    }

    fun update(hours: List<Hour?>?) {
        this.forecastHours = hours
        notifyDataSetChanged()
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val forecastTimeTxt: TextView = itemView.findViewById(R.id.forecastTimeTxt)
        val forecastTempTxt: TextView = itemView.findViewById(R.id.forecastTempTxt)
        val forecastHumidityTxt: TextView = itemView.findViewById(R.id.forecastHumidityTxt)
        val forecastImageTxt: ImageView = itemView.findViewById(R.id.forecastImageTxt)
    }
}