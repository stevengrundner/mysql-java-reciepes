// Copyright (c) 2022 Promineo Tech

package recipes.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import recipes.dao.RecipeDao;
import recipes.entity.Category;
import recipes.entity.Ingredient;
import recipes.entity.Recipe;
import recipes.entity.Step;
import recipes.entity.Unit;
import recipes.exception.DbException;

/**
 * The recipe service represents the service (or business) layer of the Recipe
 * application.
 * 
 * @author Promineo
 *
 */
public class RecipeService {
	private static final String SCHEMA_FILE = "recipe_schema.sql";
	private static final String DATA_FILE = "recipe_data.sql";

	private RecipeDao recipeDao = new RecipeDao();

	/**
	 * This method creates the recipe schema, then populates the tables with data.
	 * Before tables are created, they are dropped, so calling this method resets
	 * the data tables to a known, initial state.
	 */
	public void createAndPopulateTables() {
		loadFromFile(SCHEMA_FILE);
		loadFromFile(DATA_FILE);
	}

	/**
	 * Loads a file from the classpath. On the hard drive, this would be loaded from
	 * {project root}/target/classes. Maven and Eclipse automatically copies files
	 * in src/main/resources into a folder on the classpath.
	 * 
	 * @param fileName The name of the file to load.
	 */
	private void loadFromFile(String fileName) {
		String content = readFileContent(fileName);
		List<String> sqlStatements = convertContentToSqlStatements(content);

		recipeDao.executeBatch(sqlStatements);
	}

	/**
	 * This method converts the given file content to a list of SQL statements.
	 * 
	 * @param content The file content read off the disk.
	 * @return A list of SQL statements.
	 */
	private List<String> convertContentToSqlStatements(String content) {
		content = removeComments(content);
		content = replaceWhitespaceSequencesWithSingleSpace(content);

		return extractLinesFromContent(content);
	}

	/**
	 * This method extracts the SQL statements in the file content. A SQL statement
	 * is text separated by semicolons.
	 * 
	 * @param content The file content without comments or multiple sequences of
	 *                whitespace.
	 * @return The list of extracted SQL statements.
	 */
	private List<String> extractLinesFromContent(String content) {
		List<String> lines = new LinkedList<>();

		/*
		 * Using String.split would probably have been more efficient. Feel free to
		 * rewrite this using String.split.
		 */
		while (!content.isEmpty()) {
			int semicolon = content.indexOf(";");

			if (semicolon == -1) {
				if (!content.isBlank()) {
					lines.add(content);
				}

				content = "";
			} else {
				lines.add(content.substring(0, semicolon).trim());
				content = content.substring(semicolon + 1);
			}
		}

		return lines;
	}

	/**
	 * This uses a regular expression to replace all whitespace sequences with
	 * single spaces.
	 * 
	 * @param content The file content.
	 * @return The trimmed content.
	 */
	private String replaceWhitespaceSequencesWithSingleSpace(String content) {
		/*
		 * This regular expression says to find whitespace sequences with one or more
		 * characters. (\s is a character class that means a whitespace character
		 * (space, newline, carriage return, and tab). The plus character is a
		 * quantifier that means 1 or more. In Java, a backslash is an escape character.
		 * To get an actual backslash to pass to the regular expression parser, a double
		 * backslash is used (i.e., "\\s" --> "\s"). In replaceAll, the first parameter
		 * is the regular expression used to search the input string (content). The
		 * second parameter is the replacement value. So, this says to find one or more
		 * whitespace sequences and replace the sequence with a single space. A good
		 * cheat sheet on regular expressions can be found here:
		 * https://cheatography.com/davechild/cheat-sheets/regular-expressions/ pdf_bw/
		 */
		return content.replaceAll("\\s+", " ");
	}

	/**
	 * This method removes the single-line comment ("-- ") from a .sql file. It does
	 * not remove multiline comments (slash-star star-slash). If that comment type
	 * needs to be removed, additional coding is necessary. Any text including and
	 * after a single-line comment is removed until an end-of-line character ("\n")
	 * is found or until the end of the file.
	 * 
	 * @param content The file content.
	 * @return The file content without single-line comments.
	 */
	private String removeComments(String content) {
		StringBuilder builder = new StringBuilder(content);
		int commentPos = 0;

		while ((commentPos = builder.indexOf("-- ", commentPos)) != -1) {
			int eolPos = builder.indexOf("\n", commentPos + 1);

			if (eolPos == -1) {
				builder.replace(commentPos, builder.length(), "");
			} else {
				builder.replace(commentPos, eolPos + 1, "");
			}
		}

		return builder.toString();
	}

	/**
	 * This uses Java library classes in java.nio.file to read a file on the
	 * classpath and return a String containing the entire file content.
	 * 
	 * @param fileName The name of the file relative to the classpath to read.
	 * @return The file content.
	 */
	private String readFileContent(String fileName) {
		try {
			/*
			 * This works as follows: 1) This object's class is obtained by calling
			 * getClass() (short for this.getClass()). 2) The class loader that loaded this
			 * class is obtained from the class. The class loader knows how to read objects
			 * on the classpath (as opposed to the file system outside the classpath). 3)
			 * getResource() is called to create a URL. If the resource isn't found
			 * getResource() returns null, which will generate a NullPointerException when
			 * it is dereferenced. 4) The URL.toURI() method is called to convert the URL
			 * object to a URI object. 5) The URI is passed to Paths.get, which returns a
			 * Path pointing to the file.
			 */
			Path path = Paths.get(getClass().getClassLoader().getResource(fileName).toURI());

			/*
			 * Files.readString takes a Path and reads the contents of the file as a String.
			 */
			return Files.readString(path);

		} catch (Exception e) {
			throw new DbException(e);
		}
	}

	/**
	 * This calls the DAO object to insert a recipe into the recipe table.
	 * 
	 * @param recipe The recipe to insert.
	 * @return The original recipe with the primary key (recipeId) set.
	 */
	public Recipe addRecipe(Recipe recipe) {
		return recipeDao.insertRecipe(recipe);
	}

	/**
	 * This method returns all the recipes without ingredients, steps, or
	 * categories.
	 * 
	 * @return The list of all recipes.
	 */
	public List<Recipe> fetchRecipes() {
		 // @formatter:off
	    return recipeDao.fetchAllRecipes()
	        .stream()
	        .sorted((r1, r2) -> r1.getRecipeId() - r2.getRecipeId())
	        .collect(Collectors.toList());
	    // @formatter:on
	  }

	/**
	 * Calls the DAO to return a single recipe with ingredients, steps, and
	 * categories.
	 * 
	 * @param recipeId The recipe ID to fetch.
	 * @return The requested recipe, if successful.
	 * @throws NoSuchElementException Thrown if the recipe with the given ID is not
	 *                                found.
	 */
	public Recipe fetchRecipeById(Integer recipeId) {
		/*
		 * The DAO returns an Optional. If the Optional is not empty, return the recipe.
		 * If the Optional is empty, throw a NoSuchElementException with a custom
		 * message. The .orElseThrow method takes a Lambda expression with no parameters
		 * (the "() ->" expression). The body of the Lambda just returns a new
		 * NoSuchElementException. Note that in the abbreviated form of the Lambda (a
		 * single-line Lambda) the return keyword is implied.
		 */
		return recipeDao.fetchRecipeById(recipeId)
				.orElseThrow(() -> new NoSuchElementException("Recipe with id=" + recipeId + " does not exist"));
	}

	public List<Unit> fetchUnits() {
		return recipeDao.fetchAllUnits();
	}

	public void addIngredient(Ingredient ingredient) {
		recipeDao.addIngredientToRecipe(ingredient);
	}

	public void addStep(Step step) {
		recipeDao.addStepToRecipe(step);

	}

	public List<Category> fetchCategories() {
		return recipeDao.fetchAllCategories();
	}

	public void addCategoryToRecipe(Integer recipeId, String category) {
		recipeDao.addCategoryToRecipe(recipeId, category);

	}

	public List<Step> fetchSteps(Integer recipeId) {
		return recipeDao.fetchRecipeSteps(recipeId);
	}

	public void modifyStep(Step step) {
		if (!recipeDao.modifyRecipeStep(step)) {
			throw new DbException("Step with ID=" + step.getRecipeId() + "does not exist.");
		}
	}

	public void deleteRecipe(Integer recipeId) {
		if(!recipeDao.deleteRecipe(recipeId)) {
			throw new DbException("Recipe with ID=" + recipeId + " does not exist.");
			
		}
	}

}
