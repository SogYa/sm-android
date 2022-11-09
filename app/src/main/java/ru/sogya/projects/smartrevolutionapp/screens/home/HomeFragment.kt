package ru.sogya.projects.smartrevolutionapp.screens.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.sogya.projects.smartrevolutionapp.R
import ru.sogya.projects.smartrevolutionapp.data.network.NetworkService
import ru.sogya.projects.smartrevolutionapp.data.network.entity.State
import ru.sogya.projects.smartrevolutionapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Получение всех стейтов лаборатории
        getUserName()
        binding.textView.text = arguments?.getString("response")

        binding.button.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun getUserName() {
        NetworkService.getretrofitService("https://96890a116fb0.sn.mynetname.net/")
            .getApiStates("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiI2NWI0YmU1YjllMzA0YzI3ODEwMDdhNzUyNTM2ZjZjMCIsImlhdCI6MTY2NzMzNDEzNiwiZXhwIjoxOTgyNjk0MTM2fQ.tPyqb82IKVuAUJlH4k0LY_U54ta9GNjGHGPZ5MaSHKs")
            .enqueue(object : Callback<List<State>> {
                override fun onResponse(call: Call<List<State>>, response: Response<List<State>>) {
                    Log.d("States", response.body().toString())
                }

                override fun onFailure(call: Call<List<State>>, t: Throwable) {
                    Log.d("StatesError", t.message.toString())
                }

            })
    }
}