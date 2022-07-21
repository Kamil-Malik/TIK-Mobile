package com.example.mobiletik.domain.usecase

import android.app.Activity
import android.content.ContentValues.TAG
import android.os.Environment
import android.util.Log
import com.example.mobiletik.domain.usecase.ToastFunction.toastShort
import com.example.mobiletik.domain.utility.Loading
import com.github.doyaaaaaken.kotlincsv.client.CsvWriter
import com.github.doyaaaaaken.kotlincsv.client.KotlinCsvExperimental
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.File

object ExportCSV {

    @OptIn(KotlinCsvExperimental::class)
    fun exportCSV(mActivity: Activity) {
        val loading = Loading(mActivity)
        loading.startLoading()

        val header = arrayListOf<String>(
            "Nama",
            "NIS",
            "Kelas",
            "Kuis Satu",
            "Kuis Dua",
            "Kuis Tiga",
            "Kuis Empat",
            "Kuis Lima",
            "Nilai Akhir"
        )
        val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        val filename = "$path/eraport.csv"
        val file = File(filename)
        if (!file.exists()) {
            file.createNewFile()
        } else {
            file.delete()
            file.createNewFile()
        }
        val writer = CsvWriter().openAndGetRawWriter(file, true)
        writer.writeRow(header)
        CoroutineScope(Dispatchers.IO).launch {
            val query = Firebase.firestore.collection("Users").orderBy("userKelas", Query.Direction.ASCENDING).get().await()
            withContext(Dispatchers.Main) {
                for (document in query.documents) {
                    val avgScore = getAverage(
                        document.data!!["kuisSatu"] as Long,
                        document.data!!["kuisDua"] as Long,
                        document.data!!["kuisTiga"] as Long,
                        document.data!!["kuisEmpat"] as Long,
                        document.data!!["kuisLima"] as Long
                    )
                    val data = arrayListOf(
                        document.data!!["userName"],
                        document.data!!["userNIS"],
                        document.data!!["userKelas"],
                        document.data!!["kuisSatu"],
                        document.data!!["kuisDua"],
                        document.data!!["kuisTiga"],
                        document.data!!["kuisEmpat"],
                        document.data!!["kuisLima"],
                        avgScore.toLong()
                    )
                    writer.writeRow(data)
                }
                writer.close()
            }
        }

        Log.d(TAG, "exportCSV: Export data berhasil")
        toastShort(mActivity, "Export Data Berhasil")
        loading.dismissLoading()
    }

    private fun getAverage(numA: Long, numB: Long, numC: Long, numD: Long, numE: Long): Int =
        ((numA + numB + numC + numD + numE) / 5).toInt()
}