package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipeDetailsDBUtil {

	
	
    private static final String URL = "jdbc:postgresql://localhost:5432/Team3_CodeScrappers_Dec24";
    private static final String USER = "postgres";
    private static final String PASSWORD = "yadnesh#123";

    public static Connection getConnection() throws SQLException {

		Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
		return connection;	

    }
    

    }
