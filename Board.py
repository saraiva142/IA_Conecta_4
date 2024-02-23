class Board:
    def __init__(self):
        self.board = [[0] * 7 for _ in range(6)]
        self.turn = 1  # 1 = player 1, 2 = player 2
        self.move = None  # Jogada feita pelo jogador {turn}
        self.parent = None
        self.depth = 0

    def print_board(self):
        print("-----------------------------")
        print("| 1 | 2 | 3 | 4 | 5 | 6 | 7 |")
        print("-----------------------------")
        for row in self.board:
            for cell in row:
                if cell == 1:
                    print("| X ", end="")
                elif cell == 2:
                    print("| O ", end="")
                else:
                    print("|   ", end="")
            print("|")
            print("-----------------------------")

    def valid_move(self, col):
        if col < 0 or col > 6 or self.board[0][col] != 0: #verifica se nao esta a escrever fora do limite ou se ja nao tem uma peça naqula posição
            return False
        return True

    def make_move(self, column):
        if not self.valid_move(column):
            return None
        else:
            new_board = [row[:] for row in self.board]
            for i in range(5, -1, -1):
                if new_board[i][column] == 0:
                    new_board[i][column] = self.turn
                    break
            temp = Board()
            temp.board = new_board
            temp.turn = self.change_turn()
            return temp

    def change_turn(self):
        if self.turn == 1:
            return 2
        return 1

    def is_winner(self):
        if self.evaluator() == 512 or self.evaluator() == -512:
            return True
        return False

    def evaluator(self):
        util = 0
        for h in range(6):
            for u in range(4):
                x = 0
                o = 0
                for i in range(u, u + 4):
                    if self.board[h][i] == 1:
                        x += 1
                    if self.board[h][i] == 2:
                        o += 1
                if x == 4 and o == 0:
                    return 512
                if x == 3 and o == 0:
                    util += 50
                if x == 2 and o == 0:
                    util += 10
                if x == 1 and o == 0:
                    util += 1
                if x == 0 and o == 1:
                    util -= 1
                if x == 0 and o == 2:
                    util -= 10
                if x == 0 and o == 3:
                    util -= 50
                if x == 0 and o == 4:
                    return -512
        for h in range(7):
            for u in range(3):
                x = 0
                o = 0
                for i in range(u, u + 4):
                    if self.board[i][h] == 1:
                        x += 1
                    if self.board[i][h] == 2:
                        o += 1
                if x == 4 and o == 0:
                    return 512
                if x == 3 and o == 0:
                    util += 50
                if x == 2 and o == 0:
                    util += 10
                if x == 1 and o == 0:
                    util += 1
                if x == 0 and o == 1:
                    util -= 1
                if x == 0 and o == 2:
                    util -= 10
                if x == 0 and o == 3:
                    util -= 50
                if x == 0 and o == 4:
                    return -512
        for h in range(3):
            for u in range(4):
                x = 0
                o = 0
                for i in range(4):
                    if self.board[h + i][u + i] == 1:
                        x += 1
                    if self.board[h + i][u + i] == 2:
                        o += 1
                if x == 4 and o == 0:
                    return 512
                if x == 3 and o == 0:
                    util += 50
                if x == 2 and o == 0:
                    util += 10
                if x == 1 and o == 0:
                    util += 1
                if x == 0 and o == 1:
                    util -= 1
                if x == 0 and o == 2:
                    util -= 10
                if x == 0 and o == 3:
                    util -= 50
                if x == 0 and o == 4:
                    return -512
        for h in range(3):
            for u in range(3, 7):
                x = 0
                o = 0
                for i in range(4):
                    if self.board[h + i][u - i] == 1:
                        x += 1
                    if self.board[h + i][u - i] == 2:
                        o += 1
                if x == 4 and o == 0:
                    return 512
                if x == 3 and o == 0:
                    util += 50
                if x == 2 and o == 0:
                    util += 10
                if x == 1 and o == 0:
                    util += 1
                if x == 0 and o == 1:
                    util -= 1
                if x == 0 and o == 2:
                    util -= 10
                if x == 0 and o == 3:
                    util -= 50
                if x == 0 and o == 4:
                    return -512
        return util


class ConnectFour:
    def main(self, mode):
        inicial = Board()

        if mode == "PVP":
            self.play_pvp(inicial)

        elif mode == "MCTS":
            self.play_mcts(inicial)

        else:
            print("Invalid Argument")
            print("Usage: python ConnectFour.py [PVP | MCTS]")

    def play_pvp(self, inicial):
        inicial.print_board()

        for i in range(42):
            print("\n      Player", (i % 2 + 1), "move: ", end="")
            col = int(input()) - 1

            while not inicial.valid_move(col):
                print(" Invalid move, try again: ", end="")
                col = int(input()) - 1

            inicial = inicial.make_move(col)
            inicial.print_board()

            if inicial.is_winner():
                print("Player", (i % 2 + 1), "wins !")
                break


if __name__ == "__main__":
    import sys

    connect_four = ConnectFour()
    connect_four.main(sys.argv[1])
