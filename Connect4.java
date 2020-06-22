/* Name: Safwan Wadud
 * Date: June 18, 2018
 * Brief Description: This program simulates a text-based version of the game Connect-4. The game ends when one player
 * gets 4 in a row and the winner is declared or when the whole board is filled without anyone getting 4 in a row in
 * which case it'd be a tie game. The user can choose to play person vs person, or person vs computer.
 */

import java.util.Scanner;

public class Connect4 {
    public static char[][] board = new char [6][7];//2d char array for the board. Global array to make it easier to access it without having to pass it everytime it is needed

    public static void main (String[] args){ //Main method
        //Declare Variables
        boolean exit = false; //Checks if user wants to exit the program
        int menuChoice, modeChoice; //holds the options that the user selects at the main menu and mode selection menu
        int botDifficulty = 0; //Level of difficulty user selects if the user is to play single player
        String p1Name, p2Name;
        Scanner in = new Scanner(System.in);

        //Input
        System.out.println("========WELCOME TO CONNECT FOUR========= \n");
        do {
            menuChoice = mainMenu();
            if (menuChoice == 1){ // if user wants to play
                modeChoice =  modeSelect();
                if (modeChoice == 1){ //if user wants to play single player
                    System.out.println("=============SINGLE PLAYER==============");
                    botDifficulty = singlePlayerMenu();//prints the single player menu and gets the level of difficulty of the computer
                    System.out.print("Enter your name: "); //Gets user's name
                    p1Name = in.nextLine().trim();
                    p2Name = "Computer"; //p2's name is set to computer
                } else{ //If user wants to play multiplayer
                    System.out.println("============MULTIPLAYER============");
                    System.out.print("Player 1, enter your name: ");//Gets players' names
                    p1Name = in.nextLine().trim();
                    System.out.print("Player 2, enter your name: ");
                    p2Name = in.nextLine().trim();
                }
                //Processing/Output
                playGame(p1Name, p2Name, modeChoice, botDifficulty);//calls method playGame which runs the actual game
            } else if (menuChoice == 2){ printRules();//call method rules
            } else { exit = exitProgram();}//call method exit game
            System.out.println();
        } while (!exit); // while exit game is false
        System.out.println("==============CREDITS=============== \nMade by Safwan Wadud \nJune 18, 2018");//Credits
    }

    public static void playGame(String p1Name, String p2Name, int mode, int botLevel){//Procedure to run the actual game (either 1p or 2p)
        boolean isFinished; //Determines if game is finished
        char p1Chip = 'R'; //variables to hold characters representing players' chips
        char p2Chip = 'Y';
        int turn; //variable to determine whose turn it is
        do {
            isFinished = false;
            boardSetup();//call methods setup board and print board
            printBoard();
            turn = 0;
            do {
                if (turn%2==0){ //if it's player 1's turn
                    placeChips(userInputs(p1Name), p1Chip);//call method placeChip passing parameters for player1
                } else if (mode == 2){//After player one goes, it checks to see if p2 is another user or a bot
                    placeChips(userInputs(p2Name), p2Chip);//call method placeChip passing parameters for player2
                } else {
                    placeChips(compMove(p1Chip,p2Chip,botLevel),p2Chip);//call method placeChip passing parameters for computer
                }
                printBoard();//call method print board
                if (isWinner(p1Chip) || isWinner(p2Chip) || isTie()){ //call methods isWinner and isTie to check if the game is over
                    isFinished = true;
                    declareWinner(p1Chip,p2Chip, p1Name, p2Name);//Outputs results of the game
                }
                turn++;
            } while (!isFinished);// loop while the game is not finished
        } while (restartGame()); //loop while user wants to restart the game
    }

    public static int mainMenu(){ //Function that prints the main menu and returns choice of user at the main menu
        Scanner in = new Scanner(System.in);
        String userInput;
        int choice = 0;
        boolean isValid = false;
        System.out.println("===============MAIN MENU================");//Prints main menu
        System.out.println("1) PLAY");
        System.out.println("2) RULES");
        System.out.println("3) EXIT GAME");
        do {
            System.out.print("Choose an option: ");
            userInput = in.nextLine().trim(); //Gets user input as a string
            try {
                choice = Integer.parseInt(userInput); //attempts to parse string input into integer value
                if (choice >=1 && choice <=3){ //Checks to see if user input is within the acceptable range
                    isValid = true;
                } else {
                    System.out.println("Invalid entry. Choice must be either 1, 2, or 3. Please try again."); //isValid remains false
                }
            } catch (Exception error){ //If it fails to parse the string into an integer value, it catches any exception and outputs error message
                System.out.println("Invalid entry. Please try again.");  //isValid remains false
            }
        } while (!isValid);//loop ends when user enters a valid entry
        return choice;
    }

    public static int modeSelect(){ //Function that prints the game mode selection menu and returns user's choice at the menu
        Scanner in = new Scanner(System.in);
        String userInput;
        int choice = 0;
        boolean isValid = false;
        System.out.println("==============GAME MODES================"); //Prints game mode selection menu
        System.out.println("1) SINGLE PLAYER (PERSON VS CPU)");
        System.out.println("2) MULTIPLAYER (PERSON VS PERSON)");
        do {
            System.out.print("Select a game mode: ");
            userInput = in.nextLine().trim(); //Gets user input as a string
            try {
                choice = Integer.parseInt(userInput); //attempts to parse string input into integer value
                if (!(choice ==1 || choice ==2)){ //Checks to see if user input is within the acceptable range
                    System.out.println("Invalid entry. Choice must be either 1 or 2. Please try again."); //isValid remains false
                } else {
                    isValid = true;
                }
            } catch (Exception error){//If it fails to parse the string into an integer value, it catches any exceptions and outputs error message
                System.out.println("Invalid entry. Please try again."); //isValid remains false
            }
        } while (!isValid); //loop ends when user enters a valid entry
        return choice;
    }

    public static int singlePlayerMenu(){//Integer function to print the single player menu and returns the user's choice at the menu
        Scanner in = new Scanner(System.in);
        String userInput;
        int choice = 0;
        boolean isValid = false;

        System.out.println("1) EASY");
        System.out.println("2) MEDIUM");
        System.out.println("3) HARD");
        do {
            System.out.print("Select the level of difficulty: ");
            userInput = in.nextLine().trim(); //Gets user input as a string
            try {
                choice = Integer.parseInt(userInput); //attempts to parse string input into integer value
                if (choice >=1 && choice <=3){ //Checks to see if user input is within the acceptable range
                    isValid = true;
                } else {
                    System.out.println("Invalid entry. Choice must be either 1, 2, or 3. Please try again."); //isValid remains false
                }
            } catch (Exception error){ //If it fails to parse the string into an integer value, it catches any exception and outputs error message
                System.out.println("Invalid entry. Please try again.");  //isValid remains false
            }
        } while (!isValid);//loop ends when user enters a valid entry
        return choice;
    }

    public static void printRules(){ //Procedure that prints the rules of the game
        System.out.println("===========RULES OF THE GAME============");
        System.out.println("- Choose a column to drop your chips in");
        System.out.println("- Players take turns placing their chips \n  on the board");
        System.out.println("- The first player to get 4 in a row, \n  either horizontally, vertically, or \n  diagonally, wins the game");
        System.out.println("- If the whole board is filled and there \n  is still no winner, it is a tie game");
        System.out.println("- Player 1 (Red) always goes first");
    }

    public static boolean exitProgram(){//Function to return wether or not user wants to exit the program
        Scanner in = new Scanner(System.in);
        boolean isValid = false;
        boolean exit = false;
        String userInput;
        System.out.println("Are you sure you want to exit the program? (type \"yes\" to exit, or \"no\" to return to the main menu)");
        do {
            userInput = in.nextLine().trim().toUpperCase();
            if (userInput.equals("YES")){ //If user enters yes, exit and isValid both become true
                exit = true;
                isValid = true;
            } else if (userInput.equals("NO")){ //If user enters no, exit remains false and isValid becomes true
                isValid = true;
            } else{ //If user enters anything else, outputs error message
                System.out.println("Invalid entry. Please try again."); //isValid and exit both remain false
            }
        } while (!isValid); //Loop ends when user enters a valid entry
        return exit;
    }

    public static void boardSetup (){ //Procedure to setup the board at the start of every game
        for (int y = 0; y < 6; y++){ //For loop to traverse through rows
            for (int x = 0; x < 7; x++){ //Nested for loop to traverse through columns
                board[y][x] = 'O'; //Sets the value of board at index y,x to the character 'O' to represent open spaces
            }
        }
    }

    public static void printBoard(){ //Procedure to print the board
        System.out.println("====BOARD====");
        System.out.println("1 2 3 4 5 6 7"); //Indicates column number
        System.out.println("-------------");
        for (int y = 0; y < 6; y++){ //For loop to traverse through rows
            for (int x = 0; x < 7; x++){ //Nested for loop to traverse through columns
                System.out.print(board[y][x] + " "); //Prints the value of board at index y,x
            }
            System.out.println();
        }
        System.out.println("=============");
    }

    public static int userInputs(String pName){ //Integer function for taking and checking valid user entries and only returning input when it is valid
        Scanner in = new Scanner(System.in);
        String userInput;
        int num = 0;
        boolean isValid = false;
        do{
            System.out.print(pName + ", enter a number: ");
            userInput = in.nextLine().trim(); //get user input as a string value
            try {
                num = Integer.parseInt(userInput); //attempts to parse string input into integer value
                if (!(num >=1 && num <=7)){ //Checks to see if user input is within the acceptable range
                    System.out.println("Invalid entry. Choice must be between 1 and 7. Please try again."); //isValid remains false
                } else if (!(board[0][num-1] == 'O')){ //Checks to see if the column that the user is trying to place their chip in is full by looking at the top row
                    System.out.println("Invalid entry. Selected column is full. Please try again.");
                } else {
                    isValid = true;
                }
            } catch (Exception error){//If it fails to parse the string into an integer value, it catches any exception and outputs error message
                System.out.println("Invalid entry. Please try again.");  //isValid remains false
            }
        } while (!isValid);//loop ends when user enters a valid entry
        return num-1; //subtracts 1 from num before returning it, in order to avoid array out of bounds error
    }

    public static void placeChips(int num, char playerChip){ //Procedure to take user inputs and set the value of the board array at index (row,column) to the players' chip
        boolean placedChip = false; //To tell if a chip has already been placed
        for (int x = 5; x >= 0; x--){ //Loops through rows from bottom up
            if (board[x][num] == 'O' && !placedChip){//Checks to see if there is an open space at index x,num and that a chip has not been placed already
                board[x][num] = playerChip;//Sets value of board at index x, num to either 'R' or 'Y', depending on whose turn it is
                placedChip = true;//Sets placedChip to true
            }
        }
    }

    public static boolean isWinVertical(char playerChip){ //Boolean function to check for a win in the vertical direction
        boolean vertWin = false;
        for (int x = 0; x < 7; x++){ //For loop to traverse through columns
            for (int y = 5; y > 2; y--){ //Nested for loop to traverse through rows 5,4, and 3 only since rows below 3 aren't necessary to be checked
                //Checks to see if 4 consecutive values in the vertical direction are equal to playerChip
                if (board[y][x] == playerChip && board[y - 1][x] == playerChip && board[y - 2][x] == playerChip && board[y - 3][x] == playerChip) {
                    vertWin = true;
                    break;
                }
            }
        }
        return vertWin;
    }

    public static boolean isWinHorizontal (char playerChip){ //Boolean function to check for a win in the horizontal direction
        boolean horiWin = false;
        for (int y = 0; y < 6; y++){ //For loop to traverse through rows
            for (int x = 0; x < 4; x++){ //Nested for loop to traverse through columns 0,1, 2 and 3 only since columns above 3, aren't necessary to be checked
                //Checks to see if 4 consecutive values in the horizontal direction are equal to playerChip
                if (board[y][x] == playerChip && board[y][x + 1] == playerChip && board[y][x + 2] == playerChip && board[y][x + 3] == playerChip) {
                    horiWin = true;
                    break;
                }
            }
        }
        return horiWin;
    }

    public static boolean isWinDiagonal (char playerChip){ //Boolean function to check for a win in the diagonal direction
        boolean diagWin = false;

        //Checking for 4 in a row diagonally from bottom left to top right
        for (int y = 5; y >2 ; y--){ //For loop to traverse through rows 5,4, and 3 only since rows below 3 aren't necessary to be checked
            for (int x = 0; x < 4; x++){ //Nested for loop to traverse through columns 0,1, 2 and 3 only since columns above 3, aren't necessary to be checked
                //Checks to see if 4 consecutive values in the diagonal direction (from bottom left to top rright) are equal to playerChip
                if (board[y][x] == playerChip && board[y - 1][x + 1] == playerChip && board[y - 2][x + 2] == playerChip && board[y - 3][x + 3] == playerChip) {
                    diagWin = true;
                    break;
                }
            }
        }

        //Checking for 4 in a row diagonally from top left to bottom right
        for (int y = 0; y < 3 ; y++){ //For loop to traverse through rows 0,1, and 2 only since rows above 2 aren't necessary to be checked
            for (int x = 0; x < 4; x++){ //Nested for loop to traverse through columns 0,1, 2 and 3 only since columns above 3, aren't necessary to be checked
                //Checks to see if 4 consecutive values in the diagonal direction (from bottom left to top rright) are equal to playerChip
                if (board[y][x] == playerChip && board[y + 1][x + 1] == playerChip && board[y + 2][x + 2] == playerChip && board[y + 3][x + 3] == playerChip) {
                    diagWin = true;
                    break;
                }
            }
        }
        return diagWin;
    }

    public static boolean isWinner (char playerChip){ //Boolean function to check for a winner
        boolean isWinner = false;
        if (isWinVertical(playerChip) || isWinHorizontal(playerChip) || isWinDiagonal(playerChip)){ //Checks to see if there is a 4 in a row in all directions
            isWinner = true;
        }
        return isWinner;
    }

    public static void declareWinner(char p1Chip, char p2Chip, String p1Name, String p2Name){//Procedure to print the results at the end of the game
        if (isWinner(p1Chip)){ //If there is a winner, it lets the users know who won
            System.out.println("\n" + p1Name.toUpperCase() + " WINS!");
        } else if (isWinner(p2Chip)){
            System.out.println("\n" + p2Name.toUpperCase() + " WINS!");
        } else{ //Else prints that it is a tie game
            System.out.println("\nTIE GAME!");
        }
    }

    public static boolean isTie(){ //Boolean function to check for a tie
        boolean isTie = false;
        int count =0;
        for (int x = 0; x < 7; x++){ //For loop to traverse through columns
            if (board[0][x] != 'O' ){//checks to see if the top row contains no open spaces
                count++;//For every top space for each column that has no open spaces, it adds 1 to the counter
            }
        }
        if (count == 7){//When counter reaches 7, it means that every top space of each column is filled
            isTie = true;
        }
        return isTie;
    }

    public static boolean restartGame(){//Function to return wether or not user wants to restart the game
        Scanner in = new Scanner(System.in);
        boolean isValid = false;
        boolean restart = false;
        String userInput;
        System.out.println("Do you want to restart the game? (type \"yes\" to restart the game, or \"no\" to return to the main menu)");
        do {
            userInput = in.nextLine().trim().toUpperCase();
            if (userInput.equals("YES")){ //If user enters yes, restart and isValid both become true
                restart = true;
                isValid = true;
            } else if (userInput.equals("NO")){ //If user enters no, restart remains false and isValid becomes true
                isValid = true;
            } else{ //If user enters anything else, outputs error message
                System.out.println("Invalid entry. Please try again."); //isValid and restart both remain false
            }
        } while (!isValid); //Loop ends when user enters a valid entry
        return restart;
    }

    public static int randCompMove(){//Integer function to get a possible random move for the computer
        int move;
        boolean isValid = false;
        do {
            move = (int)(7*Math.random());//Generates a random number between 0-6
            if (board[0][move] == 'O'){ //Checks to see if the value entered has an open space to put a chip
                isValid = true;
            }
        } while (!isValid);//Loops until it is a valid number
        return move;
    }

    public static int smartCompMove (char chip){//Integer function to get a possible intelligent move for the computer
        boolean moveSelected = false;
        int move = 8;//Set to 8 so that when it is called on, it will let the program know that a move between 0-7 has not been selected given the conditions below

        //Checking to see if there is 1 chip away from a 4 in a row vertically
        for (int x = 0; x < 7; x++){ //For loop to traverse through columns
            for (int y = 5; y > 2; y--){ //Nested for loop to traverse through rows 5,4, and 3 only since rows below 3 aren't necessary to be checked
                //Checks to see if there are 3 chips in a row with an open space on the top
                if (!moveSelected && board[y][x] == chip && board[y - 1][x] == chip && board[y - 2][x] == chip && board[y - 3][x] == 'O') {
                    move = x;
                    moveSelected = true;
                    break;
                }
            }
        }

        //Checking to see if there is 1 chip away from a 4 in a row horizontally
        for (int y = 0; y < 6; y++){ //For loop to traverse through rows
            for (int x = 0; x < 4; x++){ //Nested for loop to traverse through columns 0,1, 2 and 3 only since columns above 3, aren't necessary to be checked
                //Checks to see if there are 3 chips in a row with an open space to the left ((O)(R)(R)(R))
                if (!moveSelected && board[y][x+3]== chip && board[y][x+2]== chip && board[y][x+1]== chip && board[y][x] == 'O'){
                    if (y<5 && board[y+1][x]!='O'){//Checks to see if there is no open space below where the chip should be placed so that it wouldn't drop to that row
                        move = x;
                        moveSelected=true;
                    } else if (y==5){ //Doesn't need to check for open spaces below it since row 5 is at the bottom
                        move = x;
                        moveSelected=true;
                    }
                }
                //Checks to see if there is 1 away from a 4 in a row with an open space to the middle left ((R)(O)(R)(R))
                if (!moveSelected && board[y][x]== chip && board[y][x+1]== 'O' && board[y][x+2]== chip && board[y][x+3] == chip){
                    if (y<5 && board[y+1][x+1]!='O'){//Checks to see if there is no open space below where the chip should be placed so that it wouldn't drop to that row
                        move = x+1;
                        moveSelected=true;
                    } else if (y==5){ //Doesn't need to check for open spaces below it since row 5 is at the bottom
                        move = x+1;
                        moveSelected=true;
                    }
                }
                //Checks to see if there is 1 away from a 4 in a row with an open space to the middle right ((R)(R)(O)(R))
                if (!moveSelected && board[y][x]== chip && board[y][x+1]== chip && board[y][x+2]== 'O' && board[y][x+3] == chip){
                    if (y<5 && board[y+1][x+2]!='O'){//Checks to see if there is no open space below where the chip should be placed so that it wouldn't drop to that row
                        move = x+2;
                        moveSelected=true;
                    } else if (y==5){ //Doesn't need to check for open spaces below it since row 5 is at the bottom
                        move = x+2;
                        moveSelected=true;
                    }
                }
                //Checks to see if there are 3 chips in a row with an open space to the right ((R)(R)(R)(O))
                if (!moveSelected && board[y][x]== chip && board[y][x+1]== chip && board[y][x+2]== chip && board[y][x+3] == 'O'){
                    if (y<5 && board[y+1][x+3]!='O'){//Checks to see if there is no open space below where the chip should be placed so that it wouldn't drop to that row
                        move = x+3;
                        moveSelected=true;
                    } else if (y==5){ //Doesn't need to check for open spaces below it since row 5 is at the bottom
                        move = x+3;
                        moveSelected=true;
                    }
                }
            }
        }

        //Checking to see if there is 1 chip away from a 4 in a row diagonally (bottom left to top right)
        for (int y = 5; y >2 ; y--){ //For loop to traverse through rows 5,4, and 3 only since rows below 3 aren't necessary to be checked
            for (int x = 0; x < 4; x++){ //Nested for loop to traverse through columns 0,1, 2 and 3 only since columns above 3, aren't necessary to be checked
                //Checks to see if there are 3 chips in a row with an open space to the left ((O)(R)(R)(R))
                if (!moveSelected && board[y][x]== 'O' && board[y-1][x+1]== chip && board[y-2][x+2]== chip && board[y-3][x+3]== chip){
                    if (y<5 && board[y+1][x]!='O'){//Checks to see if there is no open space below where the chip should be placed so that it wouldn't drop to that row
                        move = x;
                        moveSelected=true;
                    } else if (y==5){ //Doesn't need to check for open spaces below it since row 5 is at the bottom
                        move = x;
                        moveSelected=true;
                    }
                }
                //Checks to see if there is 1 away from a 4 in a row with an open space to the middle left ((R)(O)(R)(R))
                if (!moveSelected && board[y][x]== chip && board[y-1][x+1]== 'O' && board[y-2][x+2]== chip && board[y-3][x+3]== chip && board[y][x+1]!='O'){
                    move = x+1;
                    moveSelected=true;
                }
                //Checks to see if there is 1 away from a 4 in a row with an open space to the middle right ((R)(R)(O)(R))
                if (!moveSelected && board[y][x]== chip && board[y-1][x+1]== chip && board[y-2][x+2]== 'O' && board[y-3][x+3]== chip && board[y-1][x+2]!='O'){
                    move = x+2;
                    moveSelected=true;
                }
                //Checks to see if there is 1 away from a 4 in a row with an open space to the right ((R)(R)(R)(O))
                if (!moveSelected && board[y][x]== chip && board[y-1][x+1]== chip && board[y-2][x+2]== chip && board[y-3][x+3]== 'O' && board[y-2][x+3]!='O'){
                    move = x+3;
                    moveSelected=true;
                }
            }
        }

        //Checking to see if there is 1 chip away from a 4 in a row diagonally (top left to bottom right)
        for (int y = 0; y < 3 ; y++){ //For loop to traverse through rows 0,1, and 2 only since rows above 2 aren't necessary to be checked
            for (int x = 0; x < 4; x++){ //Nested for loop to traverse through columns 0,1, 2 and 3 only since columns above 3, aren't necessary to be checked
                //Checks to see if there are 3 chips in a row with an open space to the left ((O)(R)(R)(R))
                if (!moveSelected && board[y][x]== 'O' && board[y+1][x+1]== chip && board[y+2][x+2]== chip && board[y+3][x+3]== chip && board[y+1][x]!='O'){
                    move = x;
                    moveSelected=true;
                }
                //Checks to see if there is 1 away from a 4 in a row with an open space to the middle left ((R)(O)(R)(R))
                if (!moveSelected && board[y][x]== chip && board[y+1][x+1]== 'O' && board[y+2][x+2]== chip && board[y+3][x+3]== chip && board[y+2][x+1]!='O'){
                    move = x+1;
                    moveSelected=true;
                }
                //Checks to see if there is 1 away from a 4 in a row with an open space to the middle right ((R)(R)(O)(R))
                if (!moveSelected && board[y][x]== chip && board[y+1][x+1]== chip && board[y+2][x+2]== 'O' && board[y+3][x+3]== chip && board[y+3][x+2]!='O'){
                    move = x+2;
                    moveSelected=true;
                }
                //Checks to see if there 1 away from a 4 in a row with an open space to the right ((R)(R)(R)(O))
                if (!moveSelected && board[y][x]== chip && board[y+1][x+1]== chip && board[y+2][x+2]== chip && board[y+3][x+3]== 'O'){
                    if ((y+3)<5 && board[y+4][x+3]!='O'){//Checks to see if there is no open space below where the chip should be placed so that it wouldn't drop to that row
                        move = x+3;
                        moveSelected=true;
                    } else if ((y+3)==5){ //Doesn't need to check for open spaces below it since row 5 is at the bottom
                        move = x+3;
                        moveSelected=true;
                    }
                }
            }
        }
        return move;
    }

    public static int compMove (char p1Chip, char compChip, int botLevel){//Integer function to get computer's move
        int move;
        //Checks to see the level of difficulty of the bot to determine how the bot will choose its move
        if (botLevel ==1){//Easy difficulty
            move = randCompMove();//Always selects a move at random
        } else if (botLevel == 2){//Medium difficulty
            if (smartCompMove(p1Chip) != 8){//Checks to see if computer needs to defend
                move = smartCompMove(p1Chip);
            } else{ //If computer doesn't need to defend, it selects a column at random
                move = randCompMove();
            }
        } else{//Hard Difficulty
            if (smartCompMove(compChip) != 8){//Checks to see if computer needs to attack
                move = smartCompMove(compChip);
            } else if (smartCompMove(p1Chip) != 8){//Checks to see if computer needs to defend
                move = smartCompMove(p1Chip);
            } else{ //If computer doesn't need to attack or defend, it selects a column at random
                move = randCompMove();
            }
        }
        System.out.println("\nComputer's move: " + (move+1));//Lets user know which column the computer chose
        return move;
    }
}