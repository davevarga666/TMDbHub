package com.davevarga.tmdbhub.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "movie_table")
data class Movie(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @SerializedName("popularity")
    val popularity: String?,

    @SerializedName("vote_count")
    val voteCount: Int?,

    @SerializedName("video")
    val isThereVideo: Boolean?,

    @SerializedName("poster_path")
    val posterPath: String?,

    @SerializedName("standard_id")
    val standardId: String?,

    @SerializedName("adult")
    val isAdult: Boolean?,

    @SerializedName("backdrop_path")
    val backdropPath: String?,

    @SerializedName("original_language")
    val origLanguage: String?,

    @SerializedName("original_title")
    val originalTitle: String?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("overview")
    val overview: String?,

    @SerializedName("vote_average")
    val voteAverage: Double?,

    @SerializedName("release_date")
    val releaseDate: String?

) : Parcelable

@Entity(tableName = "genre_table")
data class GenreString(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @SerializedName("name")
    var genres: String = ""
)


data class Genre(
    @SerializedName("id")
    val genreId: String?,

    @SerializedName("name")
    val genreName: String?,

)

data class MovieResponse(
    val page: Int,

    @SerializedName("total_results")
    val totalResults: Int,

    @SerializedName("total_pages")
    val totalPages: Int,

    @SerializedName("results")
    val movieList: List<Movie>
)

data class GenreList(

    @SerializedName("genres")
    val genres: List<Genre>

)
