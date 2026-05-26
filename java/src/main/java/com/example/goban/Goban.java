package com.example.goban;

import java.util.List;

public class Goban {
    private final List<String> goban;

    public Goban(List<String> goban) {
        this.goban = goban;
    }

    public Status getStatus(int x, int y) {
        if (goban == null || goban.isEmpty() || x < 0 || y < 0 || y >= goban.size() || x >= goban.getFirst().length()) {
            return Status.OUT;
        }
        char stone = goban.get(y).charAt(x);
        return switch (stone) {
            case '.' -> Status.EMPTY;
            case 'o' -> Status.WHITE;
            case '#' -> Status.BLACK;
            default -> throw new IllegalArgumentException("Unknown goban value " + stone);
        };
    }

    public boolean isTaken(int x, int y) {
		var stone = getStatus(x, y);
		if (stone == Status.EMPTY || stone == Status.OUT) {
			return false;
		}
		var visited = new boolean[goban.getFirst().length()][goban.size()];
		return !hasLiberty(x, y, stone, visited);
    }

	private boolean hasLiberty(int x, int y, Status stone, boolean[][] visited) {
		var currentStone = getStatus(x, y);
		if (currentStone == Status.OUT) return false;
		if (currentStone == Status.EMPTY) return true;
		if (currentStone != stone) return false;
		if (visited[x][y]) return false;
		visited[x][y] = true;
		for (var dir : Direction.values()) {
			if (hasLiberty(x + dir.getDx(), y + dir.getDy(), stone, visited)) {
				return true;
			}
		}
		return false;
	}
}
