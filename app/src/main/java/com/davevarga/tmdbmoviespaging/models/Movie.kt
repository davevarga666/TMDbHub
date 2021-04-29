package com.davevarga.tmdbmoviespaging.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import org.jetbrains.annotations.NotNull

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
    val genreName: String?
)

data class ProdCompany(
    @SerializedName("company_id")
    val companyId: Int?,

    @SerializedName("logo_path")
    val logoPath: String?,

    @SerializedName("company_name")
    val companyName: String?,

    @SerializedName("origin_country")
    val originCountry: String?
)

data class ProdCountry(
    @SerializedName("country_iso")
    val countryIso: String?,

    @SerializedName("country_name")
    val countryName: String?
)

data class SpokenLanguage(
    @SerializedName("language_iso")
    val langIso: String?,

    @SerializedName("language")
    val language: String?
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

data class MovieList(
    @SerializedName("page")
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
