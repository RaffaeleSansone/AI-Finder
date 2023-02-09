package myWork;

/**
 * Classe che rappresenta il nodo esaminato.
 * Tiene traccia del nodo corrente, il nodo genitore e i costi 
 * per ragiungere il nodo
 *
 */
public class SearchNode {
    /**
     * Costruttore per il nodo radice
     *
     * @param state è lo stato che viene passato
     */
    public SearchNode(StateNode state) {
    	currentState = state;
    	parentState = null;
        cost = 0;
        hCost = 0;
        fCost = 0;
    }

    /**
     * Costruttore per tutti gli altri nodi
     *
     * @param prevState é il nodo genitore
     * @param state     è lo stato del nodo
     * @param c         il costo g(n) per raggiungere il nodo
     * @param h         il costo h(n) per raggiungere il nodo
     */
    public SearchNode(SearchNode prevState, StateNode state, double c, double h) {
    	parentState = prevState;
        currentState = state;
        cost = c;
        hCost = h;
        fCost = cost + hCost;
    }

    /**
     * @return restituisce il nodo corrente
     */
    public StateNode getCurrentState() {
        return currentState;
    }

    /**
     * @return restituisce il nodo genitore
     */
    public SearchNode getParentState() {
        return parentState;
    }

    /**
     * @return restituisce il costo
     */
    public double getCost() {
        return cost;
    }

    /**
     * @return restituisce il costo dell'euristica
     */
    public double getHCost() {
        return hCost;
    }

    /**
     * @return restituisce il costo f(n) per A*
     */
    public double getFCost() {
        return fCost;
    }

    public void setFCost(double fCost) {
        this.fCost = fCost;
    }

    private StateNode currentState; // stato corrente del nodo
    private SearchNode parentState; // stato del nodo genitore
    private double cost; // Costo per arrivare al nodo
    private double hCost; // Costo dell'euristica
    private double fCost; // Costo di f(n)
}
