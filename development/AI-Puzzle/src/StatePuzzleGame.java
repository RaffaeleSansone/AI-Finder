package myWork;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * StatePuzzleGame definisce uno stato per risolvere Puzzle-8.
 * Il gioco Puzzle-8 corrisponde nell'ordinare dei tasselli numerati
 * da 1 a 8 in una griglia 3x3 in cui un tassello risulta vuoto per
 * effettuare i movimenti:
 * 
 * 	1 | 2 | 3
	---------
	4 | 5 | 6
	---------
	7 | 8 | 0
	
 * Il tavolo da gioco è rappresentato da un array a una dimensione.
 * Il tassello vuoto viene rappresentato con '0'.
 * La casella '0' è usata per generare la mossa successiva.
 *
 */

public class StatePuzzleGame implements StateNode {
	
    /**
     * Costruttore per StatePuzzleGame
     *
     * @param board - lo stato del tavolo da gioco che servirà per la costruzione dello stato successore
     */
    public StatePuzzleGame(int[] board) {
    	currentBoardState = board;
    }
    
    /*
     *
     * @return Ritorna la posizione della casella vuota (indicato con zero)
     */
    private int getEmptyDowel() {
        // Se viene ritornato -1 è prensente un errore inaspettato.
    	// Di norma la casella vuota deve sempre essere trovata sul tavolo di gioco
        int emptyDowelIndex = -1;

        for (int i = 0; i < PUZZLE_SIZE; i++) {
            if (currentBoardState[i] == 0)
                emptyDowelIndex = i;
        }
        return emptyDowelIndex;
    }
    
    /*
     * Metodo che fa una copia del tavolo da gioco passato
     */
    private int[] copyBoardState(int[] state) {
        int[] copy = new int[PUZZLE_SIZE];
        for (int i = 0; i < PUZZLE_SIZE; i++) {
            copy[i] = state[i];
        }
        return copy;
    }
    
    /*
     * Sposto la cella '0' in una nuova posizione e poi aggiungo il nuovo
     * stato all'arrayList.
     */
    private void positionChange(int d1, int d2, ArrayList<StateNode> s) {
        int[] pos = copyBoardState(currentBoardState);
        int temp = pos[d1];
        pos[d1] = currentBoardState[d2];
        pos[d2] = temp;
        s.add((new StatePuzzleGame(pos)));
    }
    
    /**
     * Può effettuare massimo 4 operazioni:
     * - il tassello può effettuare 4 spostamenti se è al centro del tavolo da gioco
     * - il tassello può effettuare 2 spostamenti se è in un angolo
     * - il tassello può effettuare 3 spostamenti se è al centro di una riga (che non sia il centro del tavolo da gioco)
     *
     * @return Ritorna un ArrayList contenente tutti i possibili stati successori
     */
    @Override
    public ArrayList<StateNode> generateNextNode() {
    	
        ArrayList<StateNode> nextNode = new ArrayList<StateNode>();
        int emptyDowel = getEmptyDowel();

        // Proviamo a generare un nuovo stato facendo scorrere il tassello '0' sulla sinistra
        if (emptyDowel != 0 && emptyDowel != 3 && emptyDowel != 6) {
            /*
             * Se siamo qui significa che è possibile andare a sinistra.
             * Viene generato un nuovo stato
             */
        	positionChange(emptyDowel - 1, emptyDowel, nextNode);
        }

        // Proviamo a generare un nuovo stato facendo scorrere il tassello '0' verso il basso
        if (emptyDowel != 6 && emptyDowel != 7 && emptyDowel != 8) {
        	/*
             * Se siamo qui significa che è possibile andare in basso.
             * Viene generato un nuovo stato
             */
        	positionChange(emptyDowel + 3, emptyDowel, nextNode);
        }

        // Proviamo a generare un nuovo stato facendo scorrere il tassello '0' verso l'alto
        if (emptyDowel != 0 && emptyDowel != 1 && emptyDowel != 2) {
        	/*
             * Se siamo qui significa che è possibile andare in alto.
             * Viene generato un nuovo stato
             */
        	positionChange(emptyDowel - 3, emptyDowel, nextNode);
        }
        
        // Proviamo a generare un nuovo stato facendo scorrere il tassello '0' verso destra
        if (emptyDowel != 2 && emptyDowel != 5 && emptyDowel != 8) {
        	/*
             * Se siamo qui significa che è possibile andare a destra.
             * Viene generato un nuovo stato
             */
        	positionChange(emptyDowel + 1, emptyDowel, nextNode);
        }

        return nextNode;
    }
    
    /**
     * Verifica se lo stato corrente è lo stato obiettivo.
     *
     * @return - Ritorna true se è lo stato obiettivo, false altrimenti
     */
    @Override
    public boolean isWin() {
        return Arrays.equals(currentBoardState, WIN_STATE);
    }
    
    /**
     * Metodo che calcola il costo del nodo corrente
     */
    @Override
    public double getCost() {
        int cost = 0;
        for (int i = 0; i < currentBoardState.length; i++) {
            int correctPosition = WIN_STATE[i] == 0 ? 9 : WIN_STATE[i];
            cost += Math.abs((currentBoardState[i] == 0 ? 9 : currentBoardState[i]) - correctPosition);
        }
        
        return cost;
    }

    /**
     * Metodo che stampa il tavolo da gioco.
     */
    @Override
    public void printNode() {
        System.out.println(currentBoardState[0] + " | " + currentBoardState[1] + " | "
                + currentBoardState[2]);
        System.out.println("---------");
        System.out.println(currentBoardState[3] + " | " + currentBoardState[4] + " | "
        		+ currentBoardState[5]);
        System.out.println("---------");
        System.out.println(currentBoardState[6] + " | " + currentBoardState[7] + " | "
                + currentBoardState[8]);

    }

    /**
     * Metodo equals che compara due stati.
     *
     * @return Ritorna true se sono uguali, false altrimenti
     */
    @Override
    public boolean equals(StateNode s) {
        return Arrays.equals(currentBoardState, ((StatePuzzleGame) s).getCurrentBoard());

    }

    /**
     *
     * @return Ritorna lo stato corrente del tavolo da gioco
     */
    public int[] getCurrentBoard() {
    	return currentBoardState;
    }

    private int[] currentBoardState; // lo stato corrente del tavolo da gioco

    private final int[] WIN_STATE = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 0}; // lo stato obiettivo da raggiungere
    private final int PUZZLE_SIZE = 9; // La dimensione del tavolo da gioco
}