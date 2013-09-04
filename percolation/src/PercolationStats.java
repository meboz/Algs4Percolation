public class PercolationStats{

    private static double[] openedSitesBeforePercolation;
    private static int[] openSites;
    private static int experiments;

    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0)
            throw new java.lang.IllegalArgumentException();

        openedSitesBeforePercolation = new double[T];
        experiments = T;
        run(N, T);
    }

    private void run(int N, int T) {
        int i = 0;
        Percolation p;
        int k;
        while (i < T) {
            openSites = new int[N * N];
            p = new Percolation(N);
            k = 0;

            while (!p.percolates()) {
                openRandomSite(p, N, k);
                k++;
            }

            double percentOpen = (double) k / ((double) N * N);
            openedSitesBeforePercolation[i] = percentOpen;
            i++;
        }
    }

    private static boolean siteIsOpen(int site) {
        boolean open = false;
        for (int i = 0; i < openSites.length; i++) {
            if (openSites[i] == site) {
                open = true;
                break;
            }
        }

        return open;
    }

    private void openRandomSite(Percolation p, int N, int k) {
        int randomSiteNumber = generateRandomNumber(1, N * N);

        while (siteIsOpen(randomSiteNumber))
            randomSiteNumber = generateRandomNumber(1, N * N);

        openSites[k] = randomSiteNumber;

        int rowId = RowIdFor(randomSiteNumber, N);
        int colId = ColIdFor(randomSiteNumber, N);

        p.open(rowId, colId);
    }

    private int generateRandomNumber(int from, int to) {
        return StdRandom.uniform(from, to + 1);
    }

    public double mean() {
        return StdStats.mean(openedSitesBeforePercolation);
    }

    public double stddev() {
        return StdStats.stddev(openedSitesBeforePercolation);
    }

    public double confidenceLo() {
        return mean() - 1.96 * stddev() / java.lang.Math.sqrt(experiments);
    }

    public double confidenceHi() {
        return mean() + 1.96 * stddev() / java.lang.Math.sqrt(experiments);
    }

    public static void main(String[] args) {
        int gridSize = Integer.parseInt(args[0]);
        int experiments = Integer.parseInt(args[1]);

        if (gridSize <= 0 || experiments <= 0)
            throw new java.lang.IllegalArgumentException();

        PercolationStats stats = new PercolationStats(gridSize, experiments);

        System.out.println("mean			= " + stats.mean());
        System.out.println("stddev			= " + stats.stddev());
        System.out.println("95% confidence interval = " + stats.confidenceLo()
                + ", " + stats.confidenceHi());
    }

    private static int RowIdFor(int arrayId, int N) {
        return (int) java.lang.Math.ceil((double) arrayId / N);
    }

    private static int ColIdFor(int arrayId, int N) {
        int mod = arrayId % N;

        if (mod > 0)
            return mod;

        return N;
    }
}
