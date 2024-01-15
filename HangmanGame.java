import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class HangmanGame {
    private static final String WORDS_FILE_PATH = "HangmanWordList.txt";
    private static final int MAX_GUESSES = 7;

    private String[] words; // Array to store words read from file
    private String targetWord; // The word to guess
    private StringBuilder display; // Current progress of the guessed word
    private int incorrectGuesses; // Number of incorrect guesses

    public HangmanGame() {
        words = readFileIntoArray("HangmanWordList.txt"); // Initialize the words array by reading from file
        display = new StringBuilder(); // Initialize the display StringBuilder
        incorrectGuesses = 0; // Initialize the incorrect guesses counter
    }

    // Read words from file and return as an array of strings
    private static int countLinesInFile(String nameOfFile) throws FileNotFoundException {

        File file = new File(nameOfFile);
        Scanner scanner = new Scanner(file);

        int lineCount = 0;
        while (scanner.hasNextLine()) {
            lineCount++;
            scanner.nextLine();
        }
        return lineCount;
    }

    private static String[] readFileIntoArray(String nameOfFile)  {
        String[] array = null;
        try {
            int linesInFile = countLinesInFile(nameOfFile);
            array = new String[linesInFile];

            File file = new File(nameOfFile);
            Scanner scanner = new Scanner(file);

            int index = 0;
            while (scanner.hasNextLine()) {
                array[index++] = scanner.nextLine().strip();
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("Word file not found.");
            System.exit(0);
        }
        return array;
    }

    // Get a random word from the array
    private String getRandomWord(String[] words) {
        Random random = new Random(); // Create a Random object for generating random numbers
        int randomIndex = random.nextInt(words.length); // Generate a random index within the bounds of the words array
        //randomIndex = (int) (Math.random() * words.length);
        //System.out.println(Math.random());
        return words[randomIndex]; // Return the word at the randomly chosen index
    }

    public void play() {
        targetWord = getRandomWord(words); // Choose a random word from the words array
        //System.out.println(targetWord);
        for (int i = 0; i < targetWord.length(); i++) {
            display.append("-"); // Fill the display with dashes, representing unknown letters
        }

        System.out.println("Welcome to Samson's game, Hangman! Let's begin."); // Display a welcome message
        Scanner scanner = new Scanner(System.in); // Create a Scanner object to read user input

        while (incorrectGuesses < MAX_GUESSES) { // Loop until the maximum number of incorrect guesses is reached
            System.out.println("Guess a singular letter: "); // Prompt the user to enter a letter
            String input = scanner.next(); // Read the user's input

            if (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
                System.out.println("Invalid input. Please enter a single letter."); // Display an error message for invalid input
                continue; // Skip to the next iteration of the loop
            }

            char guess = Character.toLowerCase(input.charAt(0)); // Convert the user's guess to lowercase

            boolean found = false; // Initialize a flag to track if the guess is found in the target word
            char[] targetWordChars = targetWord.toCharArray(); // Convert the target word to a char array
            char[] displayChars = display.toString().toCharArray(); // Convert the display string to a char array
            for (int i = 0; i < targetWordChars.length; i++) {
                if (Character.toLowerCase(targetWordChars[i]) == guess) { // Compare the lowercase target word char with the lowercase guess
                    displayChars[i] = guess; // Update the display char array with the correct guess
                    found = true; // Set the flag to true
                }
            }

            if (!found) {
                incorrectGuesses++; // Increment the incorrect guesses counter if the guess was not found
            }

            display = new StringBuilder(String.valueOf(displayChars)); // Update the display string with the modified char array

            System.out.println("Current progress: " + display); // Display the current progress of the guessed word
            System.out.println("Incorrect guesses: " + incorrectGuesses); // Display the number of incorrect guesses
            System.out.println("Guesses left: "+ (MAX_GUESSES - incorrectGuesses)); // Display the remaining guesses

            if (!display.toString().contains("-")) {
                System.out.println("Congratulations! You guessed the word correctly. The game is now finished!"); // Display a congratulations message if the word is guessed correctly
                return; // Exit the play method
            }
        }

        System.out.println("Game over! You ran out of guesses. The word was: " + targetWord); // Display a game over message and reveal the target word
    }

    public static void main(String[] args)  {
        HangmanGame game = new HangmanGame(); // Create an instance of the HangmanGame class
        game.play(); // Start the game by calling the play method
    }
}