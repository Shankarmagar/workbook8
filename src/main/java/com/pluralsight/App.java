package com.pluralsight;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Please Enter last Name of an actor that you want to look up: ");
        Scanner scanner = new Scanner(System.in);
        String lastName = scanner.nextLine();

        try (BasicDataSource dataSource = new BasicDataSource()) {
            dataSource.setUrl("jdbc:mysql://localhost:3306/sakila");
            dataSource.setUsername("root");
            dataSource.setPassword("Binduiloveu123@");

            try (Connection connection = dataSource.getConnection();
                 PreparedStatement myStatement = connection.prepareStatement(
                         "SELECT first_name, last_name " +
                                 "FROM actor " +
                                 "WHERE last_name = ?;"
                 )
            ) {
                myStatement.setString(1, lastName);
                ResultSet listOfActor = myStatement.executeQuery();
                System.out.println("\nHere are the list of all actors that have last name that you provided: ");

                if(!listOfActor.next())
                {
                    System.out.println("There is no records on provided last name");
                }
                else {
                    while (listOfActor.next()) {
                        System.out.println(listOfActor.getString(1) + " " + listOfActor.getString(2));
                    }
                }

            } catch (SQLException e) {
                System.out.println("First statement is not working See whats going on!!");
            }

            System.out.println("Please Enter FirstName and LastName: ");
            String fullName = scanner.nextLine();
            String[] seperatedName = fullName.split(" ");
            String first_Name = seperatedName[0];
            String last_Name = seperatedName[1];

            System.out.println(first_Name);
            System.out.println(last_Name);

            try (Connection connection = dataSource.getConnection();
                 PreparedStatement myStatement = connection.prepareStatement(
                         """
                                 SELECT title
                                 FROM  film
                                 JOIN film_actor ON film_actor.film_id = film.film_id
                                 JOIN actor ON actor.actor_id = film_actor.actor_id
                                 WHERE actor.first_name = ? AND actor.last_name = ?;
                                         """
                 )
            ) {
                myStatement.setString(1, first_Name);
                myStatement.setString(2, last_Name);


                ResultSet listOfFilm = myStatement.executeQuery();
                System.out.println("\nHere are the list of all films that the actors you have selected has been in: ");
                if(!listOfFilm.next())
                {
                    System.out.println("There is records of films that the actors you have selected has been in.");
                }
                else {
                    while (listOfFilm.next()) {
                        System.out.println(listOfFilm.getString(1));
                    }
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
