package com.globallogic.task.vrogovskiy.movieslibrary.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vrogovskiy on 5/23/17.
 */

public class MoviesListApiResponse {
    private int page;
    private ArrayList<MovieObject> results;
    private int total_results;
    private int total_pages;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public ArrayList<MovieObject> getResults() {
        return results;
    }

    public void setResults(ArrayList<MovieObject> results) {
        this.results = results;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MoviesListApiResponse that = (MoviesListApiResponse) o;

        if (page != that.page) return false;
        if (total_results != that.total_results) return false;
        if (total_pages != that.total_pages) return false;
        return results != null ? results.equals(that.results) : that.results == null;

    }

    @Override
    public int hashCode() {
        int result = page;
        result = 31 * result + (results != null ? results.hashCode() : 0);
        result = 31 * result + total_results;
        result = 31 * result + total_pages;
        return result;
    }
}
