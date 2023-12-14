package com.pluralsight;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataManager {
    private DataSource dataSource;

    public DataManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Actor> getActors(String lastName) {
        List<Actor> res = new ArrayList<>();
        String sql = "SELECT actor_id, first_name, last_name " +
                "FROM actor " +
                "WHERE last_name = ?;";

        try {
            Connection myConnection = dataSource.getConnection();
            PreparedStatement statement = myConnection.prepareStatement(sql);
            statement.setString(1, lastName);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                int id = results.getInt(1);
                String first = results.getString(2);
                String last_Name = results.getString(3);
                Actor actor = new Actor(id, first, last_Name);
                res.add(actor);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return res;
    }

    public List<Film> getAllFilms(int Id) {
        List<Film> res = new ArrayList<>();

        String sql = String.format("""
                SELECT film.film_id, film.title, film.description, film.release_year, film.length
                FROM film
                JOIN film_actor ON film_actor.film_id = film.film_id
                WHERE film_actor.actor_id = ?;
                                                        """);

        try {
            Connection myConnection = dataSource.getConnection();
            PreparedStatement statement = myConnection.prepareStatement(sql);
            statement.setInt(1, Id);
            ResultSet results = statement.executeQuery();
            while (results.next()) {
                int film_id = results.getInt(1);
                String title = results.getString(2);
                String description = results.getString(3);
                Date release_year = results.getDate(4);
                int length = results.getInt(5);

                Film film = new Film(film_id, title, description, release_year, length);
                res.add(film);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return res;
    }

}
