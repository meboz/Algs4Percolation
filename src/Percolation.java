public class Percolation{

    private boolean[] openSites;
    private int gridSize;
    private WeightedQuickUnionUF ufHelper;

    public Percolation(int N) {
        gridSize = N;
        ufHelper = new WeightedQuickUnionUF(N * N + 3);
        initAndCloseSites(N);
    }

    private void initAndCloseSites(int N) {
        openSites = new boolean[N * N + 4];

        for (int i = 1; i <= N; i++) {
            openSites[i] = false;
        }

        openSites[N * N + 2] = true;
        openSites[N * N + 3] = true;
    }

    public void open(int i, int j) {
        validate(i, j);
        if (!isOpen(i, j)) {
            openSites[convertTo1D(i, j)] = true;

            if (i == 1) {
                connectToVirtualTop(i, j);
            } else {
                if (i == gridSize) {
                    connectToVirtualBottom(i, j);
                } else {
                    // if cell in the 1-st column (not 1st and last row)
                    if (j == 1) {
                        if (isOpen(i + 1, j)) {
                            ufHelper.union(convertTo1D(i, j),
                                    convertTo1D(i + 1, j));
                        }
                        if (isOpen(i, j + 1)) {
                            ufHelper.union(convertTo1D(i, j),
                                    convertTo1D(i, j + 1));
                        }
                        if (isOpen(i - 1, j)) {
                            ufHelper.union(convertTo1D(i, j),
                                    convertTo1D(i - 1, j));
                        }
                    } else {
                        // if cell in the last column (not 1st and last row)
                        if (j == gridSize) {
                            if (isOpen(i, j - 1)) {
                                ufHelper.union(convertTo1D(i, j),
                                        convertTo1D(i, j - 1));
                            }
                            if (isOpen(i + 1, j)) {
                                ufHelper.union(convertTo1D(i, j),
                                        convertTo1D(i + 1, j));
                            }
                            if (isOpen(i - 1, j)) {
                                ufHelper.union(convertTo1D(i, j),
                                        convertTo1D(i - 1, j));
                            }
                        } else {
                            if (isOpen(i + 1, j)) {
                                ufHelper.union(convertTo1D(i + 1, j),
                                        convertTo1D(i, j));
                            }
                            if (isOpen(i, j + 1)) {
                                ufHelper.union(convertTo1D(i, j + 1),
                                        convertTo1D(i, j));
                            }
                            if (isOpen(i - 1, j)) {
                                ufHelper.union(convertTo1D(i, j),
                                        convertTo1D(i - 1, j));
                            }
                            if (isOpen(i, j - 1)) {
                                ufHelper.union(convertTo1D(i, j),
                                        convertTo1D(i, j - 1));
                            }
                        }
                    }
                }
            }
        }
    }

    private void connectToVirtualBottom(int i, int j) {
        ufHelper.union(convertTo1D(i, j), gridSize * gridSize + 2);
        if (isOpen(i - 1, j)) {
            ufHelper.union(convertTo1D(i, j), convertTo1D(i - 1, j));
        }
    }

    private void connectToVirtualTop(int i, int j) {
        ufHelper.union(convertTo1D(i, j), gridSize * gridSize + 1);
        if (isOpen(i, j)) {
            ufHelper.union(convertTo1D(i, j), convertTo1D(i + 1, j));
        }
    }

    public boolean isOpen(int i, int j) {
        validate(i, j);
        return openSites[convertTo1D(i, j)];
    }

    public boolean isFull(int i, int j) {
        validate(i, j);
        return isOpen(i, j)
                && ufHelper.connected(gridSize * gridSize + 1,
                        convertTo1D(i, j));
    }

    public boolean percolates() {
        return ufHelper.connected(gridSize * gridSize + 1, gridSize * gridSize
                + 2);
    }

    private int convertTo1D(int i, int j) {
        return gridSize * (i - 1) + j;
    }

    private void validate(int i, int j) {
        if (i > gridSize || j > gridSize || i < 1 || j < 1) {
            throw new IndexOutOfBoundsException("i or j were outside bounds - "
                    + i + ", " + j);
        }
    }
}