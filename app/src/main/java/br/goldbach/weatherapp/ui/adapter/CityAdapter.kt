package br.goldbach.weatherapp.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.goldbach.weatherapp.R
import br.goldbach.weatherapp.data.model.City
import br.goldbach.weatherapp.databinding.CityViewholderBinding
import br.goldbach.weatherapp.ui.view.MainActivity

class CityAdapter(
    private var cities: List<City.CityItem>,
    private var context: Context
) : RecyclerView.Adapter<CityAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater : View = LayoutInflater.from(context).inflate(R.layout.city_viewholder, parent, false)
        context = parent.context
        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = CityViewholderBinding.bind(holder.itemView)
        holder.cityCountryTxt.text = cities[position].country
        holder.cityNameTxt.text = cities[position].name
        binding.root.setOnClickListener {
            val intent = Intent(binding.root.context, MainActivity::class.java)
            intent.putExtra("name", cities[position].name)
            binding.root.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return cities.size
    }

    fun updateData(newCities: List<City.CityItem>) {
        cities = newCities
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cityNameTxt : TextView = itemView.findViewById(R.id.cityNameVh)
        val cityCountryTxt : TextView = itemView.findViewById(R.id.cityCountryVh)
    }
}