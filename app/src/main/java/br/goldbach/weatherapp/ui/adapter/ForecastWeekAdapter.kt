package br.goldbach.weatherapp.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import br.goldbach.weatherapp.R
import br.goldbach.weatherapp.data.model.ForecastWeather
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import java.time.LocalDate
import javax.inject.Inject
import kotlin.math.roundToInt

@InstallIn(ActivityComponent::class)
@Module
class ForecastWeekAdapter @Inject constructor(
    private var days: List<ForecastWeather.Forecast.Forecastday.Day?>?,
    private var dates: List<String?>?,
    @ActivityContext private val context: Context
) : RecyclerView.Adapter<ForecastWeekAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater: View =
            LayoutInflater.from(context).inflate(R.layout.forecastweek_viewholder, parent, false)
        return ViewHolder(inflater)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date: LocalDate = LocalDate.parse(dates?.get(position))
        if(LocalDate.now().dayOfWeek == date.dayOfWeek) {
            holder.dayNameTxt.text = "Today"
        } else {
            val dayName = date.dayOfWeek.toString().lowercase().replaceFirstChar { it.uppercase() }
            holder.dayNameTxt.text = dayName
        }
        holder.dayHumidityTxt.text = days?.get(position)?.avghumidity.toString() + "%"
        holder.dayMinMaxTxt.text = days?.get(position)?.maxtempC?.roundToInt().toString() + "°/" +
                days?.get(position)?.mintempC?.roundToInt().toString() + "°"

        Picasso
            .get()
            .load("https:${days?.get(position)?.condition?.icon}")
            .placeholder(R.drawable.loading_img)
            .error(R.drawable.ic_broken_image)
            .into(holder.img2)
    }

    override fun getItemCount(): Int {
        return days?.size ?: 0
    }

    fun updateData(days: List<ForecastWeather.Forecast.Forecastday.Day?>?, dates: List<String?>?) {
        this.days = days
        this.dates = dates
        notifyDataSetChanged()
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dayNameTxt: TextView = itemView.findViewById(R.id.dayNameTxt)
        val dayHumidityTxt: TextView = itemView.findViewById(R.id.dayHumidityTxt)
        val dayMinMaxTxt: TextView = itemView.findViewById(R.id.dayMinMaxTxt)
        val img2: ImageView = itemView.findViewById(R.id.img2)
    }
}