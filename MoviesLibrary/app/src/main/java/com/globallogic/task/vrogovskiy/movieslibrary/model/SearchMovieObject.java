package com.globallogic.task.vrogovskiy.movieslibrary.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vrogovskiy on 5/25/17.
 */

public class SearchMovieObject implements Parcelable {

    private int id;
    private String logo_path;
    private String name;

    public SearchMovieObject(int id, String logo_path, String name) {
        this.id = id;
        this.logo_path = logo_path;
        this.name = name;
    }


    protected SearchMovieObject(Parcel in) {
        id = in.readInt();
        logo_path = in.readString();
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(logo_path);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SearchMovieObject> CREATOR = new Creator<SearchMovieObject>() {
        @Override
        public SearchMovieObject createFromParcel(Parcel in) {
            return new SearchMovieObject(in);
        }

        @Override
        public SearchMovieObject[] newArray(int size) {
            return new SearchMovieObject[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogo_path() {
        return logo_path;
    }

    public void setLogo_path(String logo_path) {
        this.logo_path = logo_path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SearchMovieObject that = (SearchMovieObject) o;

        if (id != that.id) return false;
        if (!logo_path.equals(that.logo_path)) return false;
        return name.equals(that.name);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + logo_path.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "SearchMovieObject{" +
                "id=" + id +
                ", logo_path='" + logo_path + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
