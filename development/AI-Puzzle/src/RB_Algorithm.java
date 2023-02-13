package myWork;

import java.util.*;

/**
 * Implementazione dell'algoritmo di ricerca bidirezionale per risolvere
 * il Puzzle Game
 * 
 */
public class RB_Algorithm {

    public static StateNode initialState;

    public static void search(int[] board) {

    	// inizializzazione dello stato obiettivo e dello stato iniziale
        int[] winBoard = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 0};
        
        initialState = new StatePuzzleGame(board);
        StateNode winState = new StatePuzzleGame(winBoard);

        SearchNode root = new SearchNode(initialState);
        SearchNode win = new SearchNode(winState);

        // creazione delle code per la ricerca
        Queue<SearchNode> forwardQueue = new LinkedList<>(); // dall'inizio
        Queue<SearchNode> backwardQueue = new LinkedList<>(); // dalla fine

        // aggiunta dei nodi alle code
        
        forwardQueue.add(root); // nodo radice
        backwardQueue.add(win); // nodo obiettivo

        rb_Search(forwardQueue, backwardQueue); // avvio ricerca bidirezionale
    }

	/*
	 * Metodo che verifica se un nodo è già stato valutato
	 */
	private static boolean checkNodes(SearchNode n)
	{
		boolean isEqual = false;
		SearchNode checkNode = n;

		// Verifica se lo stato genitore è uguale allo stato corrente
		while (n.getParentState() != null && !isEqual)
		{
			if (n.getParentState().getCurrentState().equals(checkNode.getCurrentState()))
			{
				isEqual = true;
			}
			n = n.getParentState();
		}

		return isEqual;
	}

	// metodo che verifica se lo stato corrente è uguale allo stato iniziale
    private static boolean isInitialState(StateNode state) {
        return state.equals(initialState);
    }

    // metodo che verifica se il nodo rimosso da una delle due code è presente nell'altra coda.
    // se è uguale il nodo viene ritornato
    private static SearchNode queueContainsNode(Queue<SearchNode> q, SearchNode targetNode) {
        for (SearchNode n : q) {
            if (n.getCurrentState().equals(targetNode.getCurrentState())) {
                return n;
            }
        }
        return null;
    }

    // sfrutto la ricerca in ampiezza per generare i nodi successori
    private static void ra_search(SearchNode n, Queue<SearchNode> q) {
        ArrayList<StateNode> tempNextNode = n.getCurrentState()
                .generateNextNode();

        /*
         * vengono creati i nuovi nodi e se non sono già
         * stati valutati vengono inseriti nella coda
         */
        for (int i = 0; i < tempNextNode.size(); i++) {
        	// il secondo parametro qui aggiunge il costo del nuovo nodo
			// al costo totale corrente
            SearchNode newNode = new SearchNode(n,
            		tempNextNode.get(i), n.getCost()
                    + tempNextNode.get(i).getCost(), 0);

            if (!checkNodes(newNode)) {
                q.add(newNode);
            }
        }
    }

    // Metodo utilizzato una volta trovato il punto di contatto tra le due code.
    // Vengono stampati i due percordi che portano alla soluzione
    private static void pathFound(SearchNode node, int count, long startTime) {
        
        Stack<SearchNode> solutionPath = new Stack<SearchNode>();
        solutionPath.push(node);
        
        // controllo per vedere se il nodo è lo stato obiettivo
        node = node.getParentState() == null ? node : node.getParentState();

        while (node.getParentState() != null) {
            solutionPath.push(node);
            node = node.getParentState();
        }
        solutionPath.push(node);

        // Variabile usata per tenere traccia della dimensione dello stack.
        int stackSize = solutionPath.size();

        for (int i = 0; i < stackSize; i++) {
            node = solutionPath.pop();
            node.getCurrentState().printNode();
            System.out.println();
        }
        
		// fine timer e stampo
		long endTime = System.currentTimeMillis();
		System.out.println("Durata in millisecondi: " + (endTime - startTime) + "ms");
        
        System.out.println("Il costo è: " + node.getCost());
        System.out.println("Numero di Iterazioni: " + count);
    }

    // applico l'algoritmo di ricerca bidirezionale utilizzando l'algoritmo di ricerca in
    // ampiezza sulle due code per trovare il punto di contatto
    private static void rb_Search(Queue<SearchNode> fq, Queue<SearchNode> bq) {
    	long startTime = System.currentTimeMillis(); // inizializzazione timer per controllare durata algoritmo
        int count = 0;

        // ciclo fin quando le due code non sono vuote
        while (!fq.isEmpty() && !bq.isEmpty()) {

        	// se la coda fq non è vuota entro nell'if.
        	// rimuovo un elemento dalla coda e verifico se è presente nella coda bq.
        	// se lo è ho trovato il punto di contatto altrimenti continuo a cercarlo
        	// chiamando la ricerca in ampiezza
            if (!fq.isEmpty()) { 

                SearchNode tempNode = fq.poll();
                SearchNode nodeExistOnBQ = queueContainsNode(bq, tempNode);
                if (tempNode.getCurrentState().isWin() || nodeExistOnBQ != null) {
                    if (nodeExistOnBQ != null) {
                    	pathFound(nodeExistOnBQ, count, startTime); // stampo il nodo di contatto con il relativo percorso da bq
                    }
                    pathFound(tempNode, count, startTime); // stampo il nodo di contatto di fq con il relativo percorso
                    System.exit(0);
                } else {
                	ra_search(tempNode, fq);
                	count++;
                }

            }

            // se la coda bq non è vuota entro nell'if.
        	// rimuovo un elemento dalla coda e verifico se è presente nella coda fq.
        	// se lo è ho trovato il punto di contatto altrimenti continuo a cercarlo
        	// chiamando la ricerca in ampiezza
            if (!bq.isEmpty()) {

                SearchNode tempNode = bq.poll();
                SearchNode nodeExistOnFQ = queueContainsNode(fq, tempNode);
                if (isInitialState(tempNode.getCurrentState()) || nodeExistOnFQ != null) {
                    if (nodeExistOnFQ != null) {
                    	pathFound(nodeExistOnFQ, count, startTime); // stampo il nodo di contatto con il relativo percorso da fq
                    }
                    pathFound(tempNode, count, startTime); // stampo il nodo di contatto di bq con il relativo percorso
                    System.exit(0);
                } else {
                	ra_search(tempNode, bq);
                	count++;
                }
            }

        }

    }
}
