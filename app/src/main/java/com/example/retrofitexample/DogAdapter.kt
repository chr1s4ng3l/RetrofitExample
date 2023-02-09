package com.example.retrofitexample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitexample.databinding.DogItemBinding
import com.squareup.picasso.Picasso
import kotlin.math.sign

class DogAdapter(val image: List<String>) : RecyclerView.Adapter<DogViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {

        //Set CardView on the RecyclerView
        val layoutInflater = LayoutInflater.from(parent.context)
        return DogViewHolder(layoutInflater.inflate(R.layout.dog_item, parent, false))
    }


    //Return image.size
    override fun getItemCount(): Int = image.size


    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        val item: String = image[position]
        holder.bind(item)

    }

}


//CLASS ViewHolder in the order to pass in DogAdapter
class DogViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding: DogItemBinding by lazy {
        DogItemBinding.bind(view)
    }

    fun bind(image: String) {
        Picasso.get().load(image).into(binding.imageDog)

    }

}