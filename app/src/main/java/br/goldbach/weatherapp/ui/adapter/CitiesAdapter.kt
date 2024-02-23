package br.goldbach.weatherapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.goldbach.weatherapp.R
import br.goldbach.weatherapp.data.model.TodayWeather
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject
import kotlin.math.roundToInt

@InstallIn(ActivityComponent::class)
@Module
class CitiesAdapter @Inject constructor(
    private var cities: List<TodayWeather>,
    @ActivityContext private val context: Context
) : RecyclerView.Adapter<CitiesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater : View = LayoutInflater.from(context).inflate(R.layout.city2_viewholder, parent, false)
        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvName.text = cities[position].location?.name
        holder.tvTemp.text = "${cities[position].current?.tempC?.roundToInt()}°"

        Picasso
            .get()
            .load("https:${cities[position].current?.condition?.icon}")
            .placeholder(R.drawable.loading_img)
            .error(R.drawable.ic_broken_image)
            .into(holder.ivNow)
    }

    override fun getItemCount(): Int {
        return cities.size
    }

    fun update(it: List<TodayWeather>) {
        cities = it
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName : TextView = itemView.findViewById(R.id.tvName)
        val tvTemp : TextView = itemView.findViewById(R.id.tvTemp)
        val ivNow : ImageView = itemView.findViewById(R.id.ivNow)
    }
}