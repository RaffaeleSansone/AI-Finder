package myWork;

import java.util.Scanner;

public class GameTester {

    /*
     * Classe Tester per l'utilizzo degli algoritmi di ricerca
     * 
     * Parametri da inserire:
     * - stato iniziale del puzzle game
     * - scelta dell'algoritmo da usare
     * 
     */
    public static void main(String[] args) {

        Scanner reader = new Scanner(System.in);

        System.out.println("Selezionare lo stato iniziale (esempio : 0 1 2 3 4 5 6 7 8) : \n");

        int[] initialStateBoard = insertPuzzleGame(reader.nextLine().split(" "));

        System.out.println("Con quale algoritmo risolvere puzzle 8 ?\n" +
                "1 - Ricerca in Profondità\n");

        int choiceSearch = reader.nextInt();

        switch (choiceSearch) {
            case 1:
                RP_Algorithm.search(initialStateBoard); // Ricerca in Profondità
                break;
        }
    }

    // restitusce l'array con lo stato iniziale passato dall'utente
    private static int[] insertPuzzleGame(String[] a) {
        int[] initState = new int[9];
        for (int i = 0; i < a.length; i++) {
            initState[i] = Integer.parseInt(a[i]);
        }
        return initState;
    }
}