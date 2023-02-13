package myWork;

import java.util.ArrayList;
import java.util.Stack;


/**
 * Implementazione dell'algoritmo di ricerca in profondità per risolvere
 * il Puzzle Game
 * 
 */
public class RP_Algorithm 
{

	public static void search(int[] board)
	{
		SearchNode root = new SearchNode(new StatePuzzleGame(board));
		Stack<SearchNode> stack = new Stack<SearchNode>();

		stack.add(root);

		rp_Search(stack);
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

	/**
	 * Metodo che effettua la ricerca in profondità
	 */
	public static void rp_Search(Stack<SearchNode> s)
	{
		long startTime = System.currentTimeMillis(); // inizializzazione timer per controllare durata algoritmo
		int count = 0; // Variabile che conta il numero di iterazioni dell'algoritmo

		while (!s.isEmpty()) // cicla fin quando lo stack non è vuoto
		{
			SearchNode tempNode = s.pop(); // Rimuove e restituisce l'ultimo elemento

			// Verifica se nella variabile tempNode c'è lo stato abiettivo
			if (!tempNode.getCurrentState().isWin())
			{
				// vengono generati gli stati successori da quello corrente
				ArrayList<StateNode> tempNextNode = tempNode.getCurrentState()
						.generateNextNode();

				/*
				 * Vengono creati i nuovi nodi e vengono inseriti nello stack
				 */
				for (int i = 0; i < tempNextNode.size(); i++)
				{
					// il secondo parametro qui aggiunge il costo del nuovo nodo
					// al costo totale corrente
					SearchNode newNode = new SearchNode(tempNode, tempNextNode.get(i), tempNode.getCost() + tempNextNode.get(i).getCost(), 0);

					if (!checkNodes(newNode))
					{
						s.add(newNode);
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
				
				// fine timer e stampo
				long endTime = System.currentTimeMillis();
				System.out.println("Durata in millisecondi: " + (endTime - startTime) + "ms");
				
				System.out.println("Il costo è: " + tempNode.getCost());
				System.out.println("Numero di Iterazioni: " + count);

				System.exit(0);
			}
		}

		// Questo non dovrebbe accadere giocando con il puzzle inserito.
		System.out.println("Errore! Nessuna soluzione trovata!");
	}
}