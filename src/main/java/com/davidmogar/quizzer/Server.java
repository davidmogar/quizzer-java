package com.davidmogar.quizzer;

import static spark.Spark.*;

public class Server {

    public static void startServer() {
        get("/hello", (req, res) -> "Hello World");
    }

}
