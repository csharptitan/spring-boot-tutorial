package com.example.springboottutorial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SpringBootTutorialApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootTutorialApplication.class, args);
    }

    @GetMapping("/hello")
    public String sayHello(@RequestParam(value = "myName", defaultValue = "World") String name) {

        var app = new DatabaseFunctions();

        try {
            var conn = app.makeConnection("chinook.db");

            app.selectFromTable(conn);

            // Grab and display all items from a table
            app.selectAll(conn);
            app.dropDatabaseTable(conn);
            app.createNewTable(conn);

            // insert three new rows
            app.insert(conn, "Raw Materials", 3000);
            app.insert(conn, "Semifinished Goods", 4000);
            app.insert(conn, "Finished Goods", 5000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return String.format("Hello %s!", name);
    }
}