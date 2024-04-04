import java.lang.Math;

public class MinMaxAB {
    //Metodo para encontrar o melhor movimento usando o algoritmo MinMax
    public int minmax(Board board) {
        int minVal = 512;// iniciamos a variavel com o valor mais alto
        int move = 0;// Variavel para guardar o melhor movimento
        //Um loop para passar por todos os movimentos possíveis
        for (int i = 0; i < 7; i++) {
            if (board.canInsert(i)) { // vê se o movimento é valido
                // Cria um novo tabuleiro com o estado atual
                Board b = new Board(board.getBoard(), board.getTurn());
                b = b.makeMove(i);//faz o movimento
                int heurVal = minmax(b, 0, true, -100000, 100000);//calcula o valor da heuristica usando o MinMax
                //ataualiza o valor minimo e o melhor movimento se necessário 
                if (heurVal < minVal) {
                    minVal = heurVal;
                    move = i;
                }
            }
        }
        return move;//Devolve o melhor movimento
    }

    public int minmax(Board state, int depth, boolean isMax, int alpha, int beta) {

        if (depth == 7 || state.isFullyExpanded() == 1 || state.isFullyExpanded() == 2 || state.isFullyExpanded() == 3)
            return state.evaluator(); // negativo = bom para o PC

        if (isMax) {
            int value = -513;
            for (int i = 0; i < 7; i++) {
                if (state.canInsert(i)) {
                    Board child = new Board(state.getBoard(), 1);
                    child = child.makeMove(i);
                    value = Math.max(value,minmax(child, depth + 1, false, alpha, beta));
                    alpha = Math.max(alpha, value);
                    if (beta <= alpha)
                        break;
                }
            }
            return value;
        }

        else {
            int value = 513;
            for (int i = 0; i < 7; i++) {
                if (state.canInsert(i)) {
                    Board child = new Board(state.getBoard(), 2);
                    child = child.makeMove(i);
                    value = Math.min(value, minmax(child, depth + 1, true, alpha, beta));
                    beta = Math.min(beta, value);
                    if (beta <= alpha)
                        break;
                }
            }
            return value;//Devolve o valor mínimo encontrado
        }

    }

}