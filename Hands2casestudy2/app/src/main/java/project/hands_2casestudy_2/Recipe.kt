package project.hands_2casestudy_2

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val ingredients: List<String>,
    val instructions: List<String>
) : Parcelable
