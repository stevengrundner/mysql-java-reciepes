package recipes;

import java.sql.Connection;
import recipes.dao.DbConnection;

@SuppressWarnings("unused")
public class Recipes {

	public static void main(String[] args) {

		DbConnection.getConnection();
	}

}
