import java.lang.Math;

public class MinMax {

    public int minmax(Board board) {
        int minVal = 512;
        int move = 0;

        for (int i = 0; i < 7; i++) {
            if (board.canInsert(i)) {
                Board b = new Board(board.getBoard(), board.getTurn());
                b = b.makeMove(i);
                int heurVal = minmax(b, 0, true);
                if (heurVal < minVal) {
                    minVal = heurVal;
                    move = i;
                }
            }
        }
        return move;
    }

    public int minmax(Board state, int depth, boolean isMax) {

        if (depth == 6 || state.isFullyExpanded() != 0)
            return state.evaluator();

        if (isMax) {
            int value = -513;
            for (int i = 0; i < 7; i++) {
                if (state.canInsert(i)) {
                    Board child = new Board(state.getBoard(), 1);
                    child = child.makeMove(i);
                    value = Math.max(value,minmax(child, depth + 1, false));
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
                    value = Math.min(value, minmax(child, depth + 1, true));
                }
            }
            return value;
        }

    }

}