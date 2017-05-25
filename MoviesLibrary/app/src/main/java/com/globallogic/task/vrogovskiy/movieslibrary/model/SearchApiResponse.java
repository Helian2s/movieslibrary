package com.globallogic.task.vrogovskiy.movieslibrary.model;

import java.util.ArrayList;

/**
 * Created by vrogovskiy on 5/25/17.
 */

public class SearchApiResponse {

    private int page;
    private ArrayList<SearchMovieObject> results;
    private int total_results;
    private int total_pages;

    public SearchApiResponse(int page, ArrayList<SearchMovieObject> results, int total_results, int total_pages) {
        this.page = page;
        this.results = results;
        this.total_results = total_results;
        this.total_pages = total_pages;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public ArrayList<SearchMovieObject> getResults() {
        return results;
    }

    public void setResults(ArrayList<SearchMovieObject> results) {
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

        SearchApiResponse that = (SearchApiResponse) o;

        if (page != that.page) return false;
        if (total_results != that.total_results) return false;
        if (total_pages != that.total_pages) return false;
        return results.equals(that.results);

    }

    @Override
    public int hashCode() {
        int result = page;
        result = 31 * result + results.hashCode();
        result = 31 * result + total_results;
        result = 31 * result + total_pages;
        return result;
    }

    @Override
    public String toString() {
        return "SearchApiResponse{" +
                "page=" + page +
                ", results=" + results +
                ", total_results=" + total_results +
                ", total_pages=" + total_pages +
                '}';
    }
}
