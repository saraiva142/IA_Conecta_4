import java.util.PriorityQueue;

public class MCTSNode implements Comparable<MCTSNode>{
    //Atributos para representar o estado do nódulo(o tabuleiro de jogo, o jogador, o número de visitas, o número de vitórias, uma referência ao nó pai e uma fila de prioridade para armazenar nós filhos.)
    Board board;// O tabuleiro associado com o nodulo
    int player;//O jogador que tem a vez neste estado
    int visits;//O número de vezes que este nódulo foi visitado 
    int wins;//O número de vitórias deste nódulo
    MCTSNode parent;
    PriorityQueue<MCTSNode> children;// pryority queue para armazenar o nodulos filhos 
    
    //Construtor para inicializar o novo MCTSNode (isto é,  inicializa um novo MCTSNode com o estado do tabuleiro e o nó pai fornecidos. Ele define outros atributos para valores iniciais.)
    public MCTSNode(Board tab, MCTSNode par){
        board = tab;
        player = tab.getTurn();
        visits = 0;
        wins = 0;
        parent = par;
        children = new PriorityQueue<MCTSNode>();
    }

    public double ucb(){
        if(visits == 0){
            return Integer.MAX_VALUE;
        }
        return (double)wins/visits + Math.sqrt(2*Math.log(parent.visits)/visits);
    }

    public int compareTo(MCTSNode other){
        return -1*Double.compare(this.ucb(),other.ucb());
    }

    public void setChildren(PriorityQueue<MCTSNode> children){
        this.children = children;
    }


}
/*

ucb(): Calcula o valor do Limite de Confiança Superior (UCB) para o nó. Este valor é usado para equilibrar exploração e exploração durante o processo de pesquisa.
compareTo(): Implementa o método compareTo da interface Comparable. Ele compara dois nós com base em seus valores UCB, permitindo a classificação em ordem decrescente dos valores UCB.
setChildren(): Define os filhos deste nó. Este método é usado durante a fase de expansão do algoritmo MCTS.
 */