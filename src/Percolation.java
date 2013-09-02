public class Percolation {
    private int gridSize = 0;
    private boolean[][] openSites = null;
    private WeightedQuickUnionUF weightedQUF = null;

    public Percolation(int N) {

        gridSize = N;
        openSites = new boolean[N][N];
        weightedQUF = new WeightedQuickUnionUF(N * N);

        closeAllSites();
    }

    public boolean isOpen(int i, int j) {
        validate(i, j);
        return openSites[i - 1][j - 1];
    }

    public boolean isFull(int i, int j) {
        validate(i, j);
        boolean isOpenSite = isOpen(i, j);

        for (int k = 0; k < gridSize; k++) {
            int otherSite = gridSize * (i - 1) + (j - 1);
            boolean isConnected = weightedQUF.connected(k, otherSite);

            if (isOpenSite && isConnected) {
                return true;
            }
        }

        return false;
    }

    public void open(int i, int j) {
        validate(i, j);
        openSites[i - 1][j - 1] = true;

        if (i > 1 && isOpen(i - 1, j)) { // top
            weightedQUF.union(gridSize * (i - 1) + (j - 1), gridSize
                    * (i - 2) + (j - 1));
        }

        if (i < gridSize && isOpen(i + 1, j)) { // bottom
            weightedQUF.union(gridSize * (i - 1) + (j - 1), gridSize * (i)
                    + (j - 1));
        }

        if (j > 1 && isOpen(i, j - 1)) { // left
            weightedQUF.union(gridSize * (i - 1) + (j - 1), gridSize
                    * (i - 1) + j - 2);
        }

        if (j < gridSize && isOpen(i, j + 1)) { // right.
            weightedQUF.union(gridSize * (i - 1) + (j - 1), gridSize
                    * (i - 1) + j);
        }
    }

    public boolean percolates() {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                int cur = (gridSize - 1) * gridSize + j;
                if (isOpen(1, i + 1) && isOpen(gridSize, j + 1)
                        && weightedQUF.connected(i, cur)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void closeAllSites() {
        for (int i = 1; i < gridSize; i++) {
            for (int j = 1; j < gridSize; j++) {
                openSites[i][j] = false;
            }
        }
    }

    private void validate(int i, int j) {
        if (i > gridSize)
            throw new java.lang.IndexOutOfBoundsException();

        if (i < 1)
            throw new java.lang.IndexOutOfBoundsException();

        if (j > gridSize)
            throw new java.lang.IndexOutOfBoundsException();

        if (j < 1)
            throw new java.lang.IndexOutOfBoundsException();

    }
}
