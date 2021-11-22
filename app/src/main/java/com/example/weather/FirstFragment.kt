package com.example.weather

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

class FirstFragment : Fragment() {
    var retrofitBuilder = RetrofitCreator().getRetrofit()
    lateinit var button: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weatherMain()

    }


    private fun weatherMain() {
        val tvTemp = view?.findViewById<TextView>(R.id.tvTemp)
        val tvDescription = view?.findViewById<TextView>(R.id.tvDescription)
        val tvWind = view?.findViewById<TextView>(R.id.tvWind)

        val edText = view?.findViewById<EditText>(R.id.edCountryName)
        button = requireView().findViewById(R.id.buttonCheck)


        button.setOnClickListener {
            val countryName = edText?.text.toString()

            when (countryName) {
                "" -> Toast.makeText(activity, "Input City Name", Toast.LENGTH_LONG).show()
                else -> {
                    GlobalScope.launch(Dispatchers.IO) {
                        val response = retrofitBuilder.weather(
                            "$countryName",
                            "6e298e72d16587b721abb30bbf7c721a",
                            "metric",
                            "ru"
                        ).awaitResponse()
                        if (response.isSuccessful) {
                            val data = response.body()!!
                            Log.d("MainFragment", data.name)

                            withContext(Dispatchers.Main) {
                                tvTemp?.text = data.main.temp.toString() + "°C"
                                tvDescription?.text = data.weather[0].description
                                tvWind?.text =
                                    "Скорость ветра:\n" + data.wind.speed.toString() + "м/с"


                            }


                        }

                    }
                }


            }

        }
    }

}