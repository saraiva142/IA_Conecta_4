import java.util.ArrayList;
import java.util.List;

public class Board {
    public int[][] board = new int[6][7];

    int turn; // 1 = player 1, 2 = player 2
    int move; // Jogada feita pelo jogador {turn}
    Board parent;
    private int cost;

    int depth;

    public Board() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                board[i][j] = 0;
            }
        }

        turn = 1;
    }

    public Board(int[][] mat, int op) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                board[i][j] = mat[i][j];
            }
        }
        turn = op;
    }

    // --------------------------------------------------------------------------------------

    public void printBoard() {
        System.out.println("-----------------------------");
        System.out.println("| 1 | 2 | 3 | 4 | 5 | 6 | 7 |");
        System.out.println("-----------------------------");
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                // imprime as jogadas do jogador 1 como "x" e a joagadas do jogador 2 como "O" e imprime '-' se nao estiver nenhuma peça lá
                if (board[i][j] == 1) {
                    System.out.print("| X ");
                } else if (board[i][j] == 2) {
                    System.out.print("| O ");
                } else {
                    System.out.print("|   ");
                }
            }
            System.out.println("|");
            System.out.println("-----------------------------");
        }
    }

    public int[][] getBoard() {
        return board;
    }
    public void setCost(int cost){
        this.cost=cost;
    }
    public int getCost(){
        return cost;
    }

    public void setDepth(int p) {
        depth = p;
    }

    public int getDepth() {
        return depth;
    }

    public void setMove(int p) {
        move = p;
    }

    public int getMove() {
        return move;
    }

    public void setParent(Board p){ parent = p;}

    public Board getParent(){ return parent;}

    public boolean isTerminal() {
        for (int i = 0; i < 6; i++) {
            if (board[0][i] == 0)
                return false;

        }
        return true;
    }

    public int isFullyExpanded() {
        // Se tem vencedor ou se é empate
        if(evaluator() == 512) return 1;
        if(evaluator() == -512) return 2;
        if(isOver()) return 3;
        return 0;
    }

    public boolean canInsert(int column){
        for (int i = 5; i >= 0; i--) {
            if (board[i][column] == 0) {
                return true;
            }
        }
        return false;

    }

    public int changeTurn() {
        if (turn == 1)
            return 2;
        return 1;
    }

    public int getTurn() {
        return turn;
    }

    public boolean validMove(int col){
        if(col < 0 || col > 6 || board[0][col] != 0) return false;
        return true;
    }

    public Board makeMove(int column) {

        if (!validMove(column)) {
            return null;
        } else {

            if (turn == 1) {
                for (int i = 5; i >= 0; i--) {
                    if (board[i][column] == 0) {
                        board[i][column] = 1;
                        break;
                    }
                }
            } else {
                for (int i = 5; i >= 0; i--) {
                    if (board[i][column] == 0) {
                        board[i][column] = 2;
                        break;
                    }
                }
            }

            Board temp = new Board(board, changeTurn());
            return temp;
        }
    }

    public boolean isOver()
    {
        for (int i = 0; i < 6; i++)
        {
            if(board[0][i] == 0) return false;
        }
        return true;
    }

    public boolean isWinner() {
        if(evaluator() == 512 || evaluator() == -512) return true;
        return false;
    }

    public int evaluator() {

        // Para todos os 4 lados nos vamos fazer a soma da união entre peças iguais para as diagonais, na horizontal e na vertical
        int util = 0;

        // A win by X returns +512 points, a win by O returns -512 points
        // -50 for three Os, no Xs,
        // -10 for two Os, no Xs,
        // - 1 for one O, no Xs,
        // 0 for no tokens, or mixed Xs and Os,
        // 1 for one X, no Os,
        // 10 for two Xs, no Os,
        // 50 for three Xs, no Os

        // Beggining by the horizontals, we need to count 4 lines per board line, hence:
        for (int h = 0; h < 6; h++) {
            for (int u = 0; u < 4; u++) {
                // Count x's and o's on the 4 segment
                int x = 0;
                int o = 0;
                for (int i = u; i < u + 4; i++) {
                    if (board[h][i] == 1)
                        x++;
                    if (board[h][i] == 2)
                        o++;

                }

                // Verifica o resultado e adiciona á soma
                if (x == 4 && o == 0)
                    return 512;
                if (x == 3 && o == 0)
                    util += 50;
                if (x == 2 && o == 0)
                    util += 10;
                if (x == 1 && o == 0)
                    util += 1;
                if (x == 0 && o == 1)
                    util -= 1;
                if (x == 0 && o == 2)
                    util -= 10;
                if (x == 0 && o == 3)
                    util -= 50;
                if (x == 0 && o == 4)
                    return -512;

            }
        }

        // Verticals

        for (int h = 0; h < 7; h++) {
            for (int u = 0; u < 3; u++) {
                // Count x's and o's on the 4 segment
                int x = 0;
                int o = 0;
                for (int i = u; i < u + 4; i++) {
                    if (board[i][h] == 1)
                        x++;
                    if (board[i][h] == 2)
                        o++;

                }

                // Verifica o resultado e adiciona á soma
                if (x == 4 && o == 0)
                    return 512;
                if (x == 3 && o == 0)
                    util += 50;
                if (x == 2 && o == 0)
                    util += 10;
                if (x == 1 && o == 0)
                    util += 1;
                if (x == 0 && o == 1)
                    util -= 1;
                if (x == 0 && o == 2)
                    util -= 10;
                if (x == 0 && o == 3)
                    util -= 50;
                if (x == 0 && o == 4)
                    return -512;

            }
        }

        // Diagonals Pointing Up

        for (int h = 0; h < 3; h++) {
            for (int u = 0; u < 4; u++) {
                // Count x's and o's on the 4 segment
                int x = 0;
                int o = 0;
                for (int i = 0; i < 4; i++) {
                    if (board[h + i][u + i] == 1)
                        x++;
                    if (board[h + i][u + i] == 2)
                        o++;

                }

                // Verifica o resultado e adiciona á soma
                if (x == 4 && o == 0)
                    return 512;
                if (x == 3 && o == 0)
                    util += 50;
                if (x == 2 && o == 0)
                    util += 10;
                if (x == 1 && o == 0)
                    util += 1;
                if (x == 0 && o == 1)
                    util -= 1;
                if (x == 0 && o == 2)
                    util -= 10;
                if (x == 0 && o == 3)
                    util -= 50;
                if (x == 0 && o == 4)
                    return -512;

            }
        }

        // Diagonais para cima

        for (int h = 0; h < 3; h++) {
            for (int u = 3; u < 7; u++) {
                int x = 0;
                int o = 0;
                for (int i = 0; i < 4; i++) {
                    if (board[h + i][u - i] == 1)
                        x++;
                    if (board[h + i][u - i] == 2)
                        o++;

                }

                // Verifica o resultado e adiciona á soma
                if (x == 4 && o == 0)
                    return 512;
                if (x == 3 && o == 0)
                    util += 50;
                if (x == 2 && o == 0)
                    util += 10;
                if (x == 1 && o == 0)
                    util += 1;
                if (x == 0 && o == 1)
                    util -= 1;
                if (x == 0 && o == 2)
                    util -= 10;
                if (x == 0 && o == 3)
                    util -= 50;
                if (x == 0 && o == 4)
                    return -512;

            }
        }

        return util;
    }

}
