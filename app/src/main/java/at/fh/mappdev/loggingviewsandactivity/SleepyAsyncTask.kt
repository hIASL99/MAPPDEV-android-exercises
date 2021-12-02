package at.fh.mappdev.loggingviewsandactivity

import android.os.AsyncTask
import android.util.Log

class SleepyAsyncTask(): AsyncTask<Unit, Unit, Unit>() {

    override fun onPreExecute() {
        super.onPreExecute()
        Log.e("SleepingAsyncTask", Thread.currentThread().name)
    }

    override fun doInBackground(vararg params: Unit?) {
        Log.e("SleepingAsyncTask", Thread.currentThread().name)
        Thread.sleep(5000)
    }

    override fun onPostExecute(result: Unit?) {
        super.onPostExecute(result)
        Log.e("SleepingAsyncTask", Thread.currentThread().name)
    }
}