import java.util.LinkedList;
import java.util.List;

public class Board {
    private String algType;
    private int boardSize;
    private int playerType;
    private int depthLimited;
    private int[][] valueOfBoard;
    private int[][] stateOfBoard;

    public Board(String algType, int player, int depth, int[][] value,  int[][] state) {
        this.algType = algType;
        boardSize = value.length;
        playerType = player;
        depthLimited = depth;
        valueOfBoard = value;
        stateOfBoard = state;
    }

    public Action playGame() {
        Action result = null;
        if (algType.equals("MINIMAX")) {
            result = doSearch();
        }
        else if (algType.equals("ALPHABETA")) {
            result = doSearchByPruning();
        }
        return result;
    }

    private Action doSearch() {
        Action selectedAction = null;
        List<Action> possibleActions = getActions(stateOfBoard, playerType);
        int utility = Integer.MIN_VALUE;
        for (Action action: possibleActions) {
            int actionUtility = minValue(1, doAction(stateOfBoard, action, playerType), -playerType);
            if (actionUtility > utility) {
                utility = actionUtility;
                selectedAction = action;
            }
        }
        return selectedAction;
    }

    private int minValue(int depth, int[][] state, int player) {
        if (isTerminal(depth, state)){
            return getUtility(state);
        }
        int utility = Integer.MAX_VALUE;
        List<Action> possibleActions = getActions(state, player);
        for (Action action: possibleActions) {
            int actionUtility = maxValue(depth + 1, doAction(state, action, player), -player);
            if (actionUtility < utility) {
                utility = actionUtility;
            }
        }
        return utility;
    }

    private int maxValue(int depth, int[][] state, int player) {
        if (isTerminal(depth, state)){
            return getUtility(state);
        }
        int utility = Integer.MIN_VALUE;
        List<Action> possibleActions = getActions(state, player);
        for (Action action: possibleActions) {
            int actionUtility = minValue(depth + 1, doAction(state, action, player), -player);
            if (actionUtility > utility) {
                utility = actionUtility;
            }
        }
        return utility;
    }

    private Action doSearchByPruning() {
        Action selectedAction = null;
        List<Action> possibleActions = getActions(stateOfBoard, playerType);
        int utility = Integer.MIN_VALUE;
        for (Action action: possibleActions) {
            int actionUtility = minValueWithPruning(1, doAction(stateOfBoard, action, playerType),
                                                Integer.MIN_VALUE, Integer.MAX_VALUE, -playerType);
            if (actionUtility > utility) {
                utility = actionUtility;
                selectedAction = action;
            }
        }
        return selectedAction;
    }

    private int minValueWithPruning(int depth, int[][] state, int alpha, int beta, int player) {
        if (isTerminal(depth, state)){
            return getUtility(state);
        }
        int utility = Integer.MAX_VALUE;
        List<Action> possibleActions = getActions(state, player);
        for (Action action: possibleActions) {
            int actionUtility = maxValueWithPruning(depth + 1, doAction(state, action, player),
                                                                        alpha, beta, -player);
            utility = Math.min(utility, actionUtility);
            if (utility <= alpha) {
                return utility;
            }
            beta = Math.min(beta, utility);
        }
        return utility;
    }

    private int maxValueWithPruning(int depth, int[][] state, int alpha, int beta, int player) {
        if (isTerminal(depth, state)){
            return getUtility(state);
        }
        int utility = Integer.MIN_VALUE;
        List<Action> possibleActions = getActions(state, player);
        for (Action action: possibleActions) {
            int actionUtility = minValueWithPruning(depth + 1, doAction(state, action, player),
                                                                alpha, beta, -player);
            utility = Math.max(utility, actionUtility);
            if (utility >= beta) {
                return utility;
            }
            alpha = Math.max(alpha, utility);
        }
        return utility;
    }

    private List<Action> getActions(int[][] state, int player) {
        List<Action> availableActions = new LinkedList<Action>();
        // Search all stake actions before raid actions, search the board
        // in order (top left to bottom right, row by row) for each action type
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (state[i][j] == 0) {
                    Action action = new Action(i, j, "Stake");
                    availableActions.add(action);
                }
            }
        }
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (state[i][j] == player) {
                    if (i - 1 >= 0 && state[i - 1][j] == 0) {
                        Action action = new Action(i - 1, j, "Raid");
                        availableActions.add(action);
                    }
                    if (i + 1 < boardSize  && state[i + 1][j] == 0) {
                        Action action = new Action(i + 1, j, "Raid");
                        availableActions.add(action);
                    }
                    if (j - 1 >= 0 && state[i][j - 1] == 0) {
                        Action action = new Action(i, j - 1, "Raid");
                        availableActions.add(action);
                    }
                    if (j + 1 < boardSize && state[i][j + 1] == 0) {
                        Action action = new Action(i, j + 1, "Raid");
                        availableActions.add(action);
                    }
                }
            }
        }
        return availableActions;
    }

    public int[][] doAction(int[][] state, Action action, int player) {
        int row = action.row;
        int col = action.col;
        int[][] resultAction = new int[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            System.arraycopy(state[i], 0, resultAction[i], 0, boardSize);
        }

        if (action.moveType.equals("Stake")) {
            resultAction[row][col] = player;
        }

        if (action.moveType.equals("Raid")) {
            resultAction[row][col] = player;
            if (row - 1 >= 0 && resultAction[row - 1][col] == -player) {
                resultAction[row - 1][col] = player;
            }
            if (row + 1 < boardSize && resultAction[row + 1][col] == -player) {
                resultAction[row + 1][col] = player;
            }
            if (col - 1 >= 0 && resultAction[row][col - 1] == -player) {
                resultAction[row][col - 1] = player;
            }
            if (col + 1 < boardSize && resultAction[row][col + 1] == -player) {
                resultAction[row][col + 1] = player;
            }
        }

        return resultAction;
    }

    private boolean isTerminal(int depth, int[][] state) {
        if (depth == depthLimited) {
            return true;
        }

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (state[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private int getUtility(int[][] state) {
        int utility = 0;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (state[i][j] == playerType) {
                    utility +=  valueOfBoard[i][j];
                }
                else if (state[i][j] == -playerType) {
                    utility -= valueOfBoard[i][j];
                }

            }
        }

        return utility;
    }
}

class Action {
    public int row;
    public int col;
    public String moveType;

    public Action(int row, int col, String moveType) {
        this.row = row;
        this.col = col;
        this.moveType = moveType;
    }
}
