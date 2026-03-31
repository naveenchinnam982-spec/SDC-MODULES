import android.content.Context
import androidx.room.Room

object DatabaseProvider {

    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {

        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "notes_db"
            ).build()
        }

        return INSTANCE!!
    }
}