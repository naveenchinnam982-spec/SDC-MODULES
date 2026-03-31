import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,  // Unique ID

    val title: String, // Note title

    val content: String, // Note content

    val isSynced: Boolean = false // Sync status
)