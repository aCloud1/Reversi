import com.example.reversi.Board;
import com.example.reversi.Point;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class BoardTest {
    @Test
    @DisplayName("Horizontal: return coordinates of opponent cells in happy path")
    public void horizontalHappyPath1() {
        Board board = new Board(new int[][]{
                {1, 2, 2, 2, 2, 2, 0},
        });

        ArrayList<Point> actual = board.checkHorizontally(1, 0, 6);

        ArrayList<Point> expected = new ArrayList<>();
        expected.add(new Point(0, 5));
        expected.add(new Point(0, 4));
        expected.add(new Point(0, 3));
        expected.add(new Point(0, 2));
        expected.add(new Point(0, 1));

        assertArrayEqualsIgnoreOrder(actual, expected);
    }

    @Test
    @DisplayName("Horizontal: return coordinates of opponent cells in happy path2")
    public void horizontalHappyPath2() {
        Board board = new Board(new int[][]{
                {1, 2, 0, 2, 1},
        });
        ArrayList<Point> expected = new ArrayList<>();
        expected.add(new Point(0, 1));
        expected.add(new Point(0, 3));

        ArrayList<Point> actual = board.checkHorizontally(1, 0, 2);

        assertArrayEqualsIgnoreOrder(actual, expected);
    }

    @Test
    @DisplayName("Horizontal: return no coordinates when not surrounding opponent cells")
    public void horizontalErrorCase1() {
        Board board = new Board(new int[][]{
                {0, 2, 0},
        });

        ArrayList<Point> actual = board.checkHorizontally(1, 0, 2);

        assertArrayEqualsIgnoreOrder(actual, new ArrayList<>());
    }

    @Test
    @DisplayName("Horizontal: return no coordinates when encountering self without previously encountering opponent")
    public void horizontalErrorCase2() {
        Board board = new Board(new int[][]{
                {1, 2, 1, 0},
        });

        ArrayList<Point> actual = board.checkHorizontally(1, 0, 3);

        assertArrayEqualsIgnoreOrder(actual, new ArrayList<>());
    }


    @Test
    @DisplayName("Vertical: return coordinates of opponent cells in happy path")
    public void verticalHappyPath1() {
        Board board = new Board(new int[][]{
                {1},
                {2},
                {2},
                {0},
        });
        ArrayList<Point> expected = new ArrayList<>();
        expected.add(new Point(2, 0));
        expected.add(new Point(1, 0));

        ArrayList<Point> actual = board.checkVertically(1, 3, 0);

        assertArrayEqualsIgnoreOrder(actual, expected);
    }


    @Test
    @DisplayName("Vertical: return coordinates of opponent cells in happy path2")
    public void verticalHappyPath2() {
        Board board = new Board(new int[][]{
                {1},
                {2},
                {0},
                {2},
                {1}
        });
        ArrayList<Point> expected = new ArrayList<>();
        expected.add(new Point(1, 0));
        expected.add(new Point(3, 0));

        ArrayList<Point> actual = board.checkVertically(1, 2, 0);

        assertArrayEqualsIgnoreOrder(actual, expected);
    }

    @Test
    @DisplayName("Vertical: return no coordinates when not surrounding opponent cells")
    public void verticalErrorCase1() {
        Board board = new Board(new int[][]{
                {0},
                {2},
                {0},
        });

        ArrayList<Point> actual = board.checkVertically(1, 2, 0);

        assertArrayEqualsIgnoreOrder(actual, new ArrayList<>());
    }

    @Test
    @DisplayName("Vertical: return no coordinates when encountering self without previously encountering opponent")
    public void verticalErrorCase2() {
        Board board = new Board(new int[][]{
                {1},
                {2},
                {1},
                {0},
        });

        ArrayList<Point> actual = board.checkVertically(1, 3, 0);

        assertArrayEqualsIgnoreOrder(actual, new ArrayList<>());
    }

    @Test
    @DisplayName("Diagonal: return coordinates of opponent cells in happy path")
    public void diagonalHappyPath1() {
        Board board = new Board(new int[][]{
                {1, 0, 0, 0, 1},
                {0, 2, 0, 2, 0},
                {0, 0, 0, 0, 0},
                {0, 2, 0, 2, 0},
                {1, 0, 0, 0, 1},
        });

        ArrayList<Point> actual = board.checkDiagonally(1, 2, 2);
        ArrayList<Point> expected = new ArrayList<>();
        expected.add(new Point(1, 1));
        expected.add(new Point(1, 3));
        expected.add(new Point(3, 3));
        expected.add(new Point(3, 1));

        assertArrayEqualsIgnoreOrder(actual, expected);
    }

    @Test
    @DisplayName("Diagonal: return no coordinates when not surrounding opponent cells")
    public void diagonalErrorCase1() {
        Board board = new Board(new int[][]{
                {0, 0, 0, 0, 0},
                {0, 2, 0, 2, 0},
                {0, 0, 0, 0, 0},
                {0, 2, 0, 2, 0},
                {0, 0, 0, 0, 0},
        });

        ArrayList<Point> actual = board.checkDiagonally(1, 2, 2);
        ArrayList<Point> expected = new ArrayList<>();

        assertArrayEqualsIgnoreOrder(actual, expected);
    }

    @Test
    @DisplayName("Diagonal: return no coordinates when encountering self without previously encountering opponent")
    public void diagonalErrorCase2() {
        Board board = new Board(new int[][]{
                {2, 0, 0, 0, 2},
                {0, 1, 0, 1, 0},
                {0, 0, 0, 0, 0},
                {0, 1, 0, 1, 0},
                {2, 0, 0, 0, 2},
        });

        ArrayList<Point> actual = board.checkDiagonally(1, 2, 2);
        ArrayList<Point> expected = new ArrayList<>();

        assertArrayEqualsIgnoreOrder(actual, expected);
    }


    private void assertArrayEqualsIgnoreOrder(ArrayList<Point> actual, ArrayList<Point> expected) {
        Assertions.assertTrue(actual.size() == expected.size() &&
                actual.containsAll(expected) &&
                expected.containsAll(actual)
        );
    }
}
