 package myWork;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Implementazione dell'algoritmo di ricerca A Star per risolvere
 * il Puzzle Game
 * 
 */
public class AStar_Algorithm
{

	public static void search(int[] board)
	{
		SearchNode root = new SearchNode(new StatePuzzleGame(board)); // inizializzazione nodo radice
		
		// creazione e aggiunta del nodo radice alla coda
		Queue<SearchNode> q = new LinkedList<SearchNode>();
		q.add(root);

		int count = 0; // Variabile che conta il numero di iterazioni dell'algoritmo

		while (!q.isEmpty()) // Ciclo fin quando la coda non è vuota
		{
			SearchNode tempNode = q.poll(); // Rimuove e restituisce l'ultimo elemento

			// Se non è un nodo obiettivo creo dei successori
			if (!tempNode.getCurrentState().isWin())
			{
				ArrayList<StateNode> tempNextNodes = tempNode.getCurrentState().generateNextNode();
				
				ArrayList<SearchNode> nextNodes = new ArrayList<SearchNode>();

				/*
				 * Vengono creati i nuovi nodi dai successori e
				 * se non sono stati già valutati vengono inseriti in nextNodes
				 */
				for (int i = 0; i < tempNextNodes.size(); i++)
				{
					
					/*
					 * Crea un nuovo nodo con tempNode come genitore,
					 * il nodo attuale,
					 * il costo del genitore + il costo del nodo attuale
					 * e il costo dell'euristica (quanti tasselli sono fuori posto)
					 */
					SearchNode newNode = new SearchNode(tempNode, tempNextNodes.get(i), tempNode.getCost() + tempNextNodes.get(i).getCost(), ((StatePuzzleGame) tempNextNodes.get(i)).getOutPlace());
					
					if (!checkNodes(newNode))
					{
						nextNodes.add(newNode);
					}
				}

				// Controlla se il nodo successore è vuoto.
				// Se lo è continua
				if (nextNodes.size() == 0)
					continue;

				SearchNode lowestNode = nextNodes.get(0);

				/*
				 * Questo ciclo trova il nodo con il costo minore
				 * e lo setta in lowestNode
				 */
				for (int i = 0; i < nextNodes.size(); i++)
				{
					if (lowestNode.getHCost() > nextNodes.get(i).getHCost())
					{
						lowestNode = nextNodes.get(i);
					}
				}

				int lowestValue = (int) lowestNode.getHCost();

				// Vengono aggiunti alla coda tutti i nodi che hanno
				// lo stesso valore del nodo con valore più basso.
				for (int i = 0; i < nextNodes.size(); i++)
				{
					if (nextNodes.get(i).getHCost() == lowestValue)
					{
						q.add(nextNodes.get(i));
					}
				}

				count++;
			}
			else
				// Qui si entra quando viene ragiunto lo stato obiettivo e
				// viene stampato il percorso che ci ha portato a esso.
			{
				// Viene usato lo stack per tracciare il percorso dall'inizio alla fine
				Stack<SearchNode> solutionPath = new Stack<SearchNode>();
				solutionPath.push(tempNode);
				
				// controllo per vedere se il nodo è lo stato obiettivo
				tempNode = tempNode.getParentState() == null ? tempNode : tempNode.getParentState();

				while (tempNode.getParentState() != null)
				{
					solutionPath.push(tempNode);
					tempNode = tempNode.getParentState();
				}
				
				solutionPath.push(tempNode);

				// Variabile usata per tenere traccia della dimensione dello stack.
				int stackSize = solutionPath.size();

				// Lo stack viene svuotato e stampato per visualizzare
				// l'intero percorso della soluzione
				for (int i = 0; i < stackSize; i++)
				{
					tempNode = solutionPath.pop();
					tempNode.getCurrentState().printNode();
					System.out.println();
				}
				System.out.println("Il costo è: " + tempNode.getCost());
				System.out.println("Numero di Iterazioni: " + count);
				
				System.exit(0);
			}
		}

		// Questo non dovrebbe accadere giocando con il puzzle inserito.
		System.out.println("Errore! Nessuna soluzione trovata!");

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
}
