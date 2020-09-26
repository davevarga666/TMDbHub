package com.davevarga.tmdbmovieswithpaging.utils

import androidx.room.TypeConverter
import com.davevarga.tmdbmovieswithpaging.models.Genre
import com.davevarga.tmdbmovieswithpaging.models.ProdCompany
import com.davevarga.tmdbmovieswithpaging.models.ProdCountry
import com.davevarga.tmdbmovieswithpaging.models.SpokenLanguage

class Converters {

    @TypeConverter
    fun fromGenre(genre: Genre): String? {
        return genre.genreName
    }

    @TypeConverter
    fun toSource(name: String): Genre {
        return Genre(name, name)
    }

    @TypeConverter
    fun fromProdCompany(prodCompany: ProdCompany): String? {
        return prodCompany.companyName
    }

    @TypeConverter
    fun toProdCompany(name: String): ProdCompany {
        return ProdCompany(1, name, name, name)
    }

    @TypeConverter
    fun fromProdCountry(prodCountry: ProdCountry): String? {
        return prodCountry.countryName
    }

    @TypeConverter
    fun toProdCountry(name: String): ProdCountry {
        return ProdCountry(name, name)
    }

    @TypeConverter
    fun fromSpokenLanguage(spokenLanguage: SpokenLanguage): String? {
        return spokenLanguage.language
    }

    @TypeConverter
    fun toSpokenLanguage(name: String): SpokenLanguage {
        return SpokenLanguage(name, name)
    }


}