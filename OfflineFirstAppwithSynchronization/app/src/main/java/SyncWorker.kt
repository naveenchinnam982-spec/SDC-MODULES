import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class SyncWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {

        val db = DatabaseProvider.getDatabase(applicationContext)
        val dao = db.noteDao()

        val unsyncedNotes = dao.getUnsyncedNotes()

        for (note in unsyncedNotes) {

            try {
                val response = RetrofitClient.api.uploadNote(note)

                if (response.isSuccessful) {

                    // mark as synced
                    dao.update(note.copy(isSynced = true))
                }

            } catch (e: Exception) {
                return Result.retry()
            }
        }

        return Result.success()
    }
}