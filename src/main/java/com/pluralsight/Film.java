package com.pluralsight;

import java.util.Date;

public class Film {
    int filmId;
    String title;
    String description;
    Date releaseYear;
    int length;

    public Film(int filmId, String title, String description, Date releaseYear, int length) {
        this.filmId = filmId;
        this.title = title;
        this.description = description;
        this.releaseYear = releaseYear;
        this.length = length;
    }
    @Override
    public String toString(){
        return String.format("""
                                                    %s
                --------------------------------------------------------------------------------------------------------
                id = %-10d
                description = %-25s
                release year = %-10s
                length = %-10d
                """, title, filmId, description, releaseYear, length);
    }
}
