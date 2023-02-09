package myWork;

import java.util.ArrayList;

/**
 * Inferfaccia che definisce i metodi da usare
 * per manipolare lo stato corrente analizzato
 */

public interface StateNode {
	
    // Determina se lo stato corrente è uno stato obiettivo
    boolean isWin();

    // Metodo per generare lo stato successivo a quello corrente
    ArrayList<StateNode> generateNextNode();

    // Metodo per determinare il costo dallo stato iniziale allo stato corrente
    double getCost();

    // Metodo per stampare lo stato corrente
    void printNode();

    // Metodo per confrontare i dati sullo stato
    boolean equals(StateNode s);
}