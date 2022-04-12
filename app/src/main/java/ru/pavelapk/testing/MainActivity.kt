package ru.pavelapk.testing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import ru.pavelapk.testing.data.CacheResultDao
import ru.pavelapk.testing.data.CacheResultEntity
import ru.pavelapk.testing.data.Database
import ru.pavelapk.testing.databinding.ActivityMainBinding
import java.lang.IndexOutOfBoundsException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val parityOutlier = ParityOutlier()


    private lateinit var cacheResultDao: CacheResultDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = Database.getInstance(applicationContext)
        cacheResultDao = db.cacheResultDao()

        binding.btnRun.setOnClickListener {
            lifecycleScope.launch {
                run()
            }
        }
    }

    private suspend fun run() {
        val input = binding.etInput.text.split(',')
            .map { it.trim() }
            .mapNotNull { it.toIntOrNull() }
            .toTypedArray()
        val inputStr = input.joinToString(",")
        val cacheResultFromDb = cacheResultDao.getByInput(inputStr)

        if (cacheResultFromDb != null) {
            displayResult(cacheResultFromDb.output, cacheResultFromDb.error)
        } else {
            var result: Int? = null
            var error: String? = null
            try {
                result = parityOutlier.find(input)
            } catch (e: IndexOutOfBoundsException) {
                error = e.message
            }

            val cacheResult = CacheResultEntity(
                input = inputStr,
                output = result,
                error = error
            )
            cacheResultDao.insertAll(cacheResult)
            displayResult(result, error)
        }
    }

    private fun displayResult(output: Int?, error: String?) {
        binding.tvOutput.text = output?.toString() ?: ""
        binding.tvError.text = error ?: ""
    }

}