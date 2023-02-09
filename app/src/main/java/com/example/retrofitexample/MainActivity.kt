package com.example.retrofitexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitexample.databinding.ActivityMainBinding
import com.google.android.material.internal.ViewUtils.hideKeyboard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class MainActivity : AppCompatActivity(), OnQueryTextListener {


    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var adapter: DogAdapter
    private val dogImages = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = DogAdapter(dogImages)
    binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        binding.searchView.setOnQueryTextListener(this)

    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //GET IMAGES BY BREED
    private fun searchByName(query: String) {
        //Launch a secondary thread
    CoroutineScope(Dispatchers.IO).launch {
        val call = getRetrofit().create(ApiService::class.java).getDogByBreeds("$query/images")
        val puppies = call.body()
        //Get back to the Main thread
        runOnUiThread{
            if (call.isSuccessful){
                //Show RecyclerView

                //Set the images in a variable getting from the body
                val images = puppies?.images ?: emptyList()
                dogImages.clear()
                dogImages.addAll(images)

            }else{
                //Show error
                showError()
            }

            hideKeyboard()
        }


    }
    }

    //Hide keyBoard whe click in search icon
    private fun hideKeyboard() {
        val imm :InputMethodManager= getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.parent.windowToken, 0)
    }

    private fun showError(){
        Toast.makeText(this, "Error occurred", Toast.LENGTH_LONG).show()

    }




    //method for the searchView
    override fun onQueryTextSubmit(query: String?): Boolean {
      if (!query.isNullOrEmpty()){
          searchByName(query.lowercase())
      }

        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }
}