import android.content.Context
import androidx.work.*
import project.offline_firstappwithsynchronization.SyncWorker

fun startSync(context: Context) {

    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    val workRequest = PeriodicWorkRequestBuilder<SyncWorker>(
        15, java.util.concurrent.TimeUnit.MINUTES
    )
        .setConstraints(constraints)
        .build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "syncWork",
        ExistingPeriodicWorkPolicy.KEEP,
        workRequest
    )
}