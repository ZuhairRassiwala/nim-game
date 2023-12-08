import java.util.Scanner;
import java.util.Random;

class PlayerClass {
    private int playerScore;

    public PlayerClass() {
        this.playerScore = 0;
    }

    public void PlayerMove(NimClass nim) {
        Scanner scanner = new Scanner(System.in);

        int row = getPositiveWholeNumberInput(scanner, "Enter the row number (1, 2, or 3): ");
        int sticks = getPositiveWholeNumberInput(scanner, "Enter the number of sticks to remove: ");

        if (nim.isValidMove(row, sticks)) {
            nim.makeMove(row, sticks);
        } else {
            System.out.println("Invalid move! Try again.");
            PlayerMove(nim);
        }
    }

    private int getPositiveWholeNumberInput(Scanner scanner, String prompt) {
        int input = -1;

        while (input <= 0) {
            System.out.print(prompt);

            try {
                input = Integer.parseInt(scanner.next());

                if (input <= 0) {
                    System.out.println("Please enter a positive whole number.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid integer.");
            }
        }

        return input;
    }

    public void playNimGame() {
        NimClass nim = new NimClass();
        AIClass ai = new AIClass();

        while (!nim.GameOver()) {
            System.out.println("\nPlayer's turn:");
            nim.GameBoard();
            PlayerMove(nim);

            if (nim.GameOver()) {
                System.out.println("Player wins!");
                playerScore++;
                break;
            }

            System.out.println("\nAI's turn:");
            nim.GameBoard();
            ai.AIMove(nim);

            if (nim.GameOver()) {
                System.out.println("AI wins!");
                break;
            }
        }

        System.out.println("Final scores:");
        System.out.println("Player: " + playerScore);
        System.out.println("AI: " + ai.Score());
    }
 
    }

      

    class NimClass {
        public static final int MAX_ROWS = 3;
        private int[][] gameBoard;
    
        public NimClass() {
            gameBoard = new int[MAX_ROWS][];
            Random random = new Random();
    
            for (int i = 0; i < MAX_ROWS; i++) {
                int pileSize = random.nextInt(5) + 1; // Random pile size between 1 and 5
                gameBoard[i] = new int[pileSize];
    
                for (int j = 0; j < pileSize; j++) {
                    gameBoard[i][j] = 1;
                }
            }
        }
    
        public boolean isValidMove(int row, int sticks) {
            return row >= 1 && row <= MAX_ROWS && sticks > 0 && sticks <= gameBoard[row - 1].length;
        }
    
        public void makeMove(int row, int sticks) {
            for (int i = 0; i < sticks; i++) {
                gameBoard[row - 1][i] = 0;
            }
        }
    
        public boolean GameOver() {
            for (int i = 0; i < MAX_ROWS; i++) {
                for (int j = 0; j < gameBoard[i].length; j++) {
                    if (gameBoard[i][j] == 1) {
                        return false;
                    }
                }
            }
            return true;
        }
    
        public void GameBoard() {
            System.out.println("\nCurrent Game Board:");
            for (int i = 0; i < MAX_ROWS; i++) {
                for (int j = 0; j < gameBoard[i].length; j++) {
                    if (gameBoard[i][j] == 1) {
                        System.out.print("| ");
                    } else {
                        System.out.print("  ");
                    }
                }
                System.out.println();
            }
        }
    
        public int[][] getGameBoard() {
            return gameBoard;
        }
    
        public int Score() {
            int score = 0;
            for (int i = 0; i < MAX_ROWS; i++) {
                for (int j = 0; j < gameBoard[i].length; j++) {
                    if (gameBoard[i][j] == 0) {
                        score++;
                    }
                }
            }
            return score;
        }
    }

class AIClass {
    private int aiScore;

    public AIClass() {
        this.aiScore = 0;
    }

    public void AIMove(NimClass nim) {
        Random random = new Random();
        int row, sticks;

        do {
            row = random.nextInt(NimClass.MAX_ROWS) + 1;
            sticks = random.nextInt(nim.getGameBoard()[row - 1].length) + 1;
        } while (!nim.isValidMove(row, sticks));

        System.out.println("AI removes " + sticks + " sticks from row " + row);
        nim.makeMove(row, sticks);
    }

    public int Score() {
        return aiScore;
    }
}

