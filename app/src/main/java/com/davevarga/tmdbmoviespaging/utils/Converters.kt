package com.davevarga.tmdbmoviespaging.utils

import androidx.room.TypeConverter
import com.davevarga.tmdbmoviespaging.models.Genre
import com.davevarga.tmdbmoviespaging.models.ProdCompany
import com.davevarga.tmdbmoviespaging.models.ProdCountry
import com.davevarga.tmdbmoviespaging.models.SpokenLanguage

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