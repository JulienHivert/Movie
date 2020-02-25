package com.example.allocinoche.data


import android.os.Parcel
import android.os.Parcelable
import com.example.allocinoche.createIntList
import com.example.allocinoche.writeIntList
import com.google.gson.annotations.SerializedName

data class Movie(
    var id: Int,
    var title: String?,
    var video: Boolean,
    @SerializedName("original_title")
    var originalTitle: String?,
    @SerializedName("vote_average")
    var voteAverage: Double,
    var popularity: Double,
    @SerializedName("original_language")
    var originalLanguage: String?,
    @SerializedName("vote_count")
    var voteCount: Int,
    @SerializedName("poster_path")
    var posterPath: String?,
    @SerializedName("genre_ids")
    var genreIds: List<Int>,
    @SerializedName("backdrop_path")
    var backdropPath: String?,
    var adult: Boolean,
    var overview: String?,
    @SerializedName("release_date")
    var releaseDate: String?): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.createIntList(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeByte(if (video) 1 else 0)
        parcel.writeString(originalTitle)
        parcel.writeDouble(voteAverage)
        parcel.writeDouble(popularity)
        parcel.writeString(originalLanguage)
        parcel.writeInt(voteCount)
        parcel.writeString(posterPath)
        parcel.writeString(backdropPath)
        parcel.writeByte(if (adult) 1 else 0)
        parcel.writeString(overview)
        parcel.writeString(releaseDate)
        parcel.writeIntList(genreIds)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }

}