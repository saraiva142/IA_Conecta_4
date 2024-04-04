import java.util.LinkedList;
import java.util.PriorityQueue;

public class MCTS{
    private MCTSNode root;

    MCTS(Board r){
        root = new MCTSNode(r, null);
    }

    public MCTSNode select(){

        MCTSNode cur = root;

        while (cur.children.size() > 0){

            PriorityQueue<MCTSNode> cur_children = new PriorityQueue<MCTSNode>();
            cur_children.addAll(cur.children);

            LinkedList<MCTSNode> max_nodes = new LinkedList<MCTSNode>();
            double max_value = cur_children.peek().ucb();

            while (cur_children.peek() != null && cur_children.peek().ucb() == max_value)
                max_nodes.addFirst(cur_children.poll());
            
            int random_node = (int)(Math.random() * max_nodes.size());
            for (int i = 0; i < random_node; i++) max_nodes.removeFirst();

            cur = max_nodes.removeFirst();

            if (cur.visits == 0) return cur;
        }

        return cur;
    }

    public MCTSNode expand(MCTSNode node){

        if (node.board.isFullyExpanded() != 0) return null;// Se alguem jativer ganho ou se houve empate acaba

        Board nodeBoard = new Board(node.board.getBoard(), node.player);// cria um novo tabuleiro consoante o nodulo

        PriorityQueue<MCTSNode> nodeChildren = node.children;// guarda o nodulo visitado na lista 

        for (int i = 0; i < 7; i++){
            
            Board aux = new Board(node.board.getBoard(), node.player);
            aux = aux.makeMove(i);

            if (aux == null) continue;
            
            aux.setParent(nodeBoard);
            MCTSNode child = new MCTSNode(aux, node);
            nodeChildren.add(child);
        }

        int rand_child = (int)(Math.random() * node.children.size());
        PriorityQueue<MCTSNode> children = new PriorityQueue<MCTSNode>();
        children.addAll(node.children);

        for (int i = 0; i < rand_child; i++) children.poll();

        return children.poll();
    }   

    public int simulate(MCTSNode node){

        Board b = new Board(node.board.getBoard(), node.player);

        while(b.isFullyExpanded() == 0){

            int move = (int)(Math.random()*7);
            if(b.canInsert(move)){
                b = b.makeMove(move);
                b.setMove(move);
            }
        }

        return b.isFullyExpanded();
    }

    public void backpropagate(MCTSNode child, int outcome){

        int w = 1;
        if (outcome == child.player) w = 0;

        while (child != null){

            child.visits = child.visits + 1;
            child.wins = child.wins + w;

            if (child.parent != null) child.parent.setChildren(resort(child.parent.children));

            child = child.parent;

            if (outcome == 0) w = 0;
            else w = 1-w;
        }

    }

    public PriorityQueue<MCTSNode> resort(PriorityQueue<MCTSNode> children){

        PriorityQueue<MCTSNode> newQ = new PriorityQueue<MCTSNode>();

        while (children.size() > 0) newQ.add(children.poll());

        return newQ;
    }

    public Board run(){
        for (int i = 0; i < 30000; i++){
            MCTSNode node = select();
            
            if (node.board.isFullyExpanded() != 0) backpropagate(node, node.board.isFullyExpanded());
            else{
                MCTSNode child = expand(node);
                int simVal = simulate(child);
                backpropagate(child, simVal);
            }   
        }
        MCTSNode result = root.children.peek();

        return result.board;
    }

}
