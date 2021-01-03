package ru.vigivn.assignment

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.*
import ru.vigivn.assignment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var job: Job
    private val adapter = Adapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val orientation = resources.configuration.orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            binding.recyclerView.layoutManager = GridLayoutManager(applicationContext, 2)
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.recyclerView.layoutManager = GridLayoutManager(applicationContext, 4)
        }

        binding.recyclerView.adapter = adapter
        adapter.setData(List(15) { it })
    }

    override fun onStart() {
        super.onStart()
        job = CoroutineScope(Dispatchers.Main).launch {
            while (this.isActive) {
                delay(5000)
                adapter.newItem()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        CoroutineScope(Dispatchers.Main).launch {
            job.cancelAndJoin()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            binding.recyclerView.layoutManager = GridLayoutManager(applicationContext, 2)
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            binding.recyclerView.layoutManager = GridLayoutManager(applicationContext, 4)
        }
    }
}