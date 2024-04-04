import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class AStar {
    private Board root; // O estado inicial do problema, começa na raiz tudo a zero

    //Construtor para inicializar o algoritmo Astar com o estado inicial
    public AStar(Board root) {
        this.root = root;
    }

    //Metodo para correr o algorimo Astar
    public Board run() {
        //Inicializa a priority queue para que ela possa guardar os nódulos a serem avaliados 
        // A prioridade e determinada pelo custo de cada nódulo apartir da função evaluator
        PriorityQueue<Board> openSet = new PriorityQueue<>((a, b) -> a.getCost() - b.getCost());
        //Inicializa a lista para guardar os nódulos que já foram avaliados
        List<Board> closedSet = new ArrayList<>();
        
        //Define o custo do estado inicial apartir da função evaluator
        root.setCost(root.evaluator());
        //Adiciona o estado inicial á lista
        openSet.add(root);
    
        //Cria um loop até que nao haja movimentos possiveis, isto é nao consegue encontrar mais nódulos a serem avaliados
        while (!openSet.isEmpty()) {
            //Seleciona o nódulo com o menor custo do openSet
            Board current = openSet.poll();
            
            // Verifica se o nodulo foi totalmete expandido( chegou ao objetivo)
            if (current.isFullyExpanded() != 0) {
                return current;//Devolve o nodulo atual se for o objetivo
            }
            
            //Adiciona o nodulo explorado á lista
            closedSet.add(current);
            
            //Verifica todos os movimentos possiveis
            for (int i = 0; i < 7; i++) {
                //Verifica se o movimento é possivel
                if (current.canInsert(i)) {
                    Board child = current.makeMove(i);//Cria um nodulo filho ao fazer o movimento
                    //Define o custo do filho ao usar o evaluator e ao determinar a profundidade
                    child.setCost(child.evaluator() + child.getDepth());
                    
                    //Se o fillho não estiver na lista de explorados
                    if (!closedSet.contains(child)) {
                        //Adiciona-se á priority queue 
                        openSet.add(child);
                        // Define-se o pai do fillho como o atual nódulo
                        child.setParent(current);
                        return child; // Para de gerar após um movimento
                    }
                }
            }
        }
    
        // Se não for encontardo um movimento para ganhar devolve-se o tabuleiro atual
        return root;
    }
    
}
