package com.gorkem.flirthelper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gorkem.flirthelper.adapter.RecyclerViewAdapter
import com.gorkem.flirthelper.databinding.ActivityMainBinding
import com.gorkem.flirthelper.model.Question
import com.gorkem.flirthelper.service.ApiService
import com.gorkem.flirthelper.util.Constant.BASE_URL
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private var questionModel : ArrayList<Question>? = null
    private var recyclerViewAdapter : RecyclerViewAdapter? = null
    private var compositeDisposable : CompositeDisposable? = null
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        compositeDisposable = CompositeDisposable()
        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        loadData()
    }

    private fun loadData() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(ApiService::class.java)

        compositeDisposable?.add(retrofit.getQuestion()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleResponse))
    }

    private fun handleResponse(questionList: List<Question>){
        questionModel = ArrayList(questionList)
        questionModel?.let {
            recyclerViewAdapter = RecyclerViewAdapter(it)
            binding.recyclerView.adapter = recyclerViewAdapter

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.clear()
    }
}