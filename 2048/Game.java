import java.io.*;
import java.util.*;
import java.lang.Math.*;
import java.util.ArrayList;

/* The following program is a recreation of the game '2048.' The goal of 2048
is to swipe tiles in directions so as to combine tiles in order to create the largest
tile possible. The game is over when all of the spaces on the game board are filled.
The following game uses System out to generate the game graphics. Simply compiling this Java
file and running it will start the game. To exit the game, type "exit."

Created by: Justin Segler
Date: December 28, 2016
 */

public class Game {

    public static void main(String[] args) {

        /* The following code sets up the gameboard with an
        initialized random tile (either 2 or 4) and places
        it on a random position on the board. The instructions
        for the game are also outputted.
        */

        //generate scanner and board
        Scanner in = new Scanner(System.in);
        int[][] board = new int[4][4];
        boolean goodRand = false;

        //populate original board with 0's
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                board[i][j] = 0;
            }
        }

        //generates first random number and placement
        int random = (int) (Math.round(Math.random())+1)*2;
        int randomX = (int) (Math.round(Math.random()*3));
        int randomY = (int) (Math.round(Math.random()*3));

        board[randomX][randomY] = random;

        //prints game instructions
        System.out.println();
        System.out.println("USE W-A-S-D TO MOVE TILES");
        System.out.println("TYPE \'exit\' TO QUIT");
        System.out.println("---------------");

        //writes out the preliminary gameboard
        Game.write(board);

        //takes in first user input
        String next = in.next();
        System.out.println("---------------");

        //while input is not 'exit', remain in game
        while (!next.equals("exit")) {

            /* The following code takes in a direction to swipe the tiles.
            According to the swipe direction, the tiles are moved to that side
            of the gameboard. If two tiles are next to each other and are swiped
            in a certain direction, they will combine into a tile twice as
            large.
             */

            //creates copy of gameboard
            int[][] tempArr = Game.createDuplicateBoard(board);

            //if input is left
            if (next.equals("a")) {
                //for each row
                for (int i = 0; i < 4; i++) {
                    //find all non zero numbers and put in arraylist
                    ArrayList<Integer> temp = new ArrayList<Integer>();
                    for (int j = 0; j < 4; j++) {
                        if (board[i][j] != 0) {
                            temp.add(board[i][j]);
                        }
                    }

                    //if arraylist is not empty, run the combine method
                    if (!temp.isEmpty()) {
                        temp = Game.addNums(temp);
                    }

                    //place manuevered tiles back in board
                    int index = 0;
                    if (temp.size() > 0) {
                        for (; index < temp.size(); index++) {
                            board[i][index] = temp.get(index);
                        }
                        //fill rest of row with zeroes
                        for (; index < 4; index++) {
                            board[i][index] = 0;
                        }
                    }
                }
            }

            //if input is right
            else if (next.equals("d")) {
                //for each row
                for (int i = 0; i < 4; i++) {
                    //find all non zero numbers and put in arraylist
                    ArrayList<Integer> temp = new ArrayList<Integer>();
                    for (int j = 3; j >= 0; j--) {
                        if (board[i][j] != 0) {
                            temp.add(board[i][j]);
                        }
                    }

                    //if arraylist is not empty, run the combine method
                    if (!temp.isEmpty()) {
                        temp = Game.addNums(temp);
                    }

                    //place manuevered tiles back in board
                    int index = 0;
                    if (temp.size() > 0) {
                        for (; index < temp.size(); index++) {
                            board[i][3-index] = temp.get(index);
                        }
                        //fill rest of row with zeroes
                        for (; index < 4; index++) {
                            board[i][3-index] = 0;
                        }
                    }
                }
            }

            //if input is up
            else if (next.equals("w")) {
                //for each column
                for (int i = 0; i < 4; i++) {
                    //find all non zero numbers and put in arraylist
                    ArrayList<Integer> temp = new ArrayList<Integer>();
                    for (int j = 0; j < 4; j++) {
                        if (board[j][i] != 0) {
                            temp.add(board[j][i]);
                        }
                    }

                    //if arraylist is not empty, run the combine method
                    if (!temp.isEmpty()) {
                        temp = Game.addNums(temp);
                    }

                    //place manuevered tiles back in board
                    int index = 0;
                    if (temp.size() > 0) {
                        for (; index < temp.size(); index++) {
                            board[index][i] = temp.get(index);
                        }
                        //fill rest of row with zeroes
                        for (; index < 4; index++) {
                            board[index][i] = 0;
                        }
                    }
                }
            }

            //if input is down
            else if (next.equals("s")) {
                //for each column
                for (int i = 0; i < 4; i++) {
                    //find all non zero numbers and put in arraylist
                    ArrayList<Integer> temp = new ArrayList<Integer>();
                    for (int j = 3; j >= 0; j--) {
                        if (board[j][i] != 0) {
                            temp.add(board[j][i]);
                        }
                    }

                    //if arraylist is not empty, run the combine method
                    if (!temp.isEmpty()) {
                        temp = Game.addNums(temp);
                    }

                    //place manuevered tiles back in board
                    int index = 0;
                    if (temp.size() > 0) {
                        for (; index < temp.size(); index++) {
                            board[3-index][i] = temp.get(index);
                        }
                        //fill rest of row with zeroes
                        for (; index < 4; index++) {
                            board[3-index][i] = 0;
                        }
                    }
                }

            }

            /* If after the swipe, the board remains the same, the user
            is forced to swipe in a different direction and the board is
            reoutputted until the user picks a new direction.
            */

            if (!Game.checkEqualBoard(board, tempArr)) {
                //generate next random number and placement
                while (!goodRand) {
                    random = (int) (Math.round(Math.random()) + 1) * 2;
                    randomX = (int) (Math.round(Math.random() * 3));
                    randomY = (int) (Math.round(Math.random() * 3));

                    if (board[randomX][randomY] == 0) {
                        board[randomX][randomY] = random;
                        goodRand = true;
                    }
                }
                goodRand = false;
            }

            //outputs gameboard
            Game.write(board);

            /* If the board generates a new random tile in a spot such
            that the entire board is filled, then the game is over.
            */

            //check if all spots in the 2D array are filled
            boolean gameOver = true;
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (board[i][j] == 0) {
                        gameOver = false;
                    }
                }
            }

            //prints out game over and ends the game
            if (gameOver) {
                System.out.println("GAME OVER");
                return;
            }

            //take in next input
            next = in.next();
            System.out.println("---------------");
        }
    }

    //helper method to write out the game board
    public static void write(int[][] board) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (board[i][j] != 0) {
                    System.out.print(board[i][j]);
                } else {
                    System.out.print(" ");
                }
                System.out.print("   ");
            }
            System.out.println("\n");
        }
        System.out.println("---------------");
    }

    //helper function to add the numbers in the rows after a given swipe
    public static ArrayList<Integer> addNums(ArrayList<Integer> arr) {
        int index = 0;
        while (index < arr.size()-1) {
            if (arr.get(index) == arr.get(index+1)) {
                arr.set(index, arr.get(index)+arr.get(index+1));
                arr.set(index+1, 0);
                index+=2;
            } else {
                index++;
            }
        }

        ArrayList<Integer> temp = new ArrayList<Integer>();

        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i) != 0) {
                temp.add(arr.get(i));
            }
        }

        for (int i = 0; i < temp.size(); i++) {
            if (temp.get(i) == 0) {
                temp.remove(i);
            }
        }

        return temp;
    }

    //helper function to create a duplicate board to check if the user
    //must pick another swipe direction
    public static int[][] createDuplicateBoard(int[][] board) {
        int[][] tempArr = new int[4][4];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                tempArr[i][j] = board[i][j];
            }
        }

        return tempArr;
    }

    //helper function to check if the temp board and the original board
    //are equal
    public static boolean checkEqualBoard(int[][] board, int[][] tempArr) {
        boolean equal = true;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (tempArr[i][j] != board[i][j]){
                    equal = false;
                }
            }
        }

        return equal;
    }
}