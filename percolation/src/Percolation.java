public class Percolation{
    private WeightedQuickUnionUF percolateUFHelper;
    private WeightedQuickUnionUF fullUFHelper;

    private static final int VIRTUAL_TOP_INDEX = 0;
    private static final int VIRTUAL_BOTTOM_INDEX = 1;
    private int gridSize;
    private boolean[][] gridOpen;

    public Percolation(int N) {
        gridSize = N;

        initializeUFHelpers(N);
        initializeSitesOpenTrackerToClose(N);
    }

    private void initializeUFHelpers(int N) {
        percolateUFHelper = new WeightedQuickUnionUF((N + 1) * (N + 1) + 1);
        fullUFHelper = new WeightedQuickUnionUF((N + 1) * (N + 1) + 1);
    }

    private void initializeSitesOpenTrackerToClose(int N) {
        gridOpen = new boolean[N + 1][N + 1];

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                gridOpen[i][j] = false;
            }
        }
    }

    public void open(int i, int j) {
        if (i <= 0 || i > gridSize)
            throw new IndexOutOfBoundsException("row index i out of bounds");
        if (j <= 0 || j > gridSize)
            throw new IndexOutOfBoundsException("column index j out of bounds");

        if (isOpen(i, j))
            return;

        int siteIndex = xyTo1D(i, j);
        gridOpen[i][j] = true;

        if (i == 1) {
            connectVirtualTop(siteIndex);
        }
        if (i == gridSize) {
            connectVirtualBottom(siteIndex);
        }

        if (i > 1 && isOpen(i - 1, j)) {
            connectSiteAbove(i, j, siteIndex);
        }

        if (i < gridSize && isOpen(i + 1, j)) {
            connectSiteBelow(i, j, siteIndex);
        }

        if (j > 1 && isOpen(i, j - 1)) {
            connectSiteOnLeft(i, j, siteIndex);
        }

        if (j < gridSize && isOpen(i, j + 1)) {
            connectSiteOnRight(i, j, siteIndex);
        }
    }

    private void connectSiteOnRight(int i, int j, int siteIndex) {
        percolateUFHelper.union(siteIndex, xyTo1D(i, j) + 1);
        fullUFHelper.union(siteIndex, xyTo1D(i, j) + 1);
    }

    private void connectSiteOnLeft(int i, int j, int siteIndex) {
        percolateUFHelper.union(siteIndex, xyTo1D(i, j) - 1);
        fullUFHelper.union(siteIndex, xyTo1D(i, j) - 1);
    }

    private void connectSiteBelow(int i, int j, int siteIndex) {
        percolateUFHelper.union(siteIndex, (i + 1) * gridSize + j);
        fullUFHelper.union(siteIndex, (i + 1) * gridSize + j);
    }

    private void connectSiteAbove(int i, int j, int siteIndex) {
        percolateUFHelper.union(siteIndex, (i - 1) * gridSize + j);
        fullUFHelper.union(siteIndex, (i - 1) * gridSize + j);
    }

    private void connectVirtualBottom(int siteIndex) {
        percolateUFHelper.union(VIRTUAL_BOTTOM_INDEX, siteIndex);
    }

    private void connectVirtualTop(int siteIndex) {
        percolateUFHelper.union(VIRTUAL_TOP_INDEX, siteIndex);
        fullUFHelper.union(VIRTUAL_TOP_INDEX, siteIndex);
    }

    private int xyTo1D(int i, int j) {
        return i * gridSize + j;
    }

    public boolean isOpen(int i, int j) {
        validate(i, j);
        return gridOpen[i][j];
    }

    private void validate(int i, int j) {
        if (i <= 0 || i > gridSize || j <= 0 || j > gridSize)
            throw new IndexOutOfBoundsException();
    }

    public boolean isFull(int i, int j) {
        validate(i, j);
        return fullUFHelper.connected(xyTo1D(i, j), VIRTUAL_TOP_INDEX);
    }

    public boolean percolates() {
        return percolateUFHelper.connected(VIRTUAL_TOP_INDEX,
                VIRTUAL_BOTTOM_INDEX);
    }
}