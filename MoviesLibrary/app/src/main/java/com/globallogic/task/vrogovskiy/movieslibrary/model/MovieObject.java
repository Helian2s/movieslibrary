package com.globallogic.task.vrogovskiy.movieslibrary.model;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vrogovskiy on 5/23/17.
 *
 *     {
 "poster_path": "/tWqifoYuwLETmmasnGHO7xBjEtt.jpg",
 "adult": false,
 "overview": "A live-action adaptation of Disney's version of the classic 'Beauty and the Beast' tale of a cursed prince and a beautiful young woman who helps him break the spell.",
 "release_date": "2017-03-16",
 "genre_ids": [
 10402,
 10751,
 14,
 10749
 ],
 "id": 321612,
 "original_title": "Beauty and the Beast",
 "original_language": "en",
 "title": "Beauty and the Beast",
 "backdrop_path": "/7QshG75xKCmClghQDU1ta2BTaja.jpg",
 "popularity": 149.112938,
 "vote_count": 2609,
 "video": false,
 "vote_average": 6.8
 }
 */

public class MovieObject implements Parcelable {

    private int id;
    private String title;
    private String poster_path;
    private String overview;


    protected MovieObject(Parcel in) {
        id = in.readInt();
        title = in.readString();
        poster_path = in.readString();
        overview = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(poster_path);
        dest.writeString(overview);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MovieObject> CREATOR = new Creator<MovieObject>() {
        @Override
        public MovieObject createFromParcel(Parcel in) {
            return new MovieObject(in);
        }

        @Override
        public MovieObject[] newArray(int size) {
            return new MovieObject[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterUrl(){
        return "http://image.tmdb.org/t/p/w780" + poster_path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovieObject that = (MovieObject) o;

        if (id != that.id) return false;
        if (!title.equals(that.title)) return false;
        if (!poster_path.equals(that.poster_path)) return false;
        return overview.equals(that.overview);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + title.hashCode();
        result = 31 * result + poster_path.hashCode();
        result = 31 * result + overview.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "MovieObject{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", poster_path='" + poster_path + '\'' +
                ", overview='" + overview + '\'' +
                '}';
    }
}
