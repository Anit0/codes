import java.util.*;

public class MarathonRoute 

{
    static final int INF = Integer.MAX_VALUE / 2;
    static int[][] dist;
    static int[] degree;
    static List<int[]> edges;
    static int n, m;

    public static void main(String[] args) 
    
    {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();

        while (T-- > 0) 
        {
            n = sc.nextInt();
            m = sc.nextInt();

            dist = new int[n + 1][n + 1];
            degree = new int[n + 1];
            edges = new ArrayList<>();

            for (int i = 1; i <= n; i++) 
            {
                Arrays.fill(dist[i], INF);
                dist[i][i] = 0;
            }

            int totalWeight = 0;
            for (int i = 0; i < m; i++) 
            
            {
                int u = sc.nextInt();
                int v = sc.nextInt();
                int w = sc.nextInt();
                dist[u][v] = dist[v][u] = Math.min(dist[u][v], w);
                degree[u]++;
                degree[v]++;
                totalWeight += w;
            }

            floydWarshall();
            List<Integer> oddDegreeNodes = new ArrayList<>();
            for (int i = 1; i <= n; i++) 
            
            {
                if (degree[i] % 2 == 1) 
                
                {
                    oddDegreeNodes.add(i);
                }
            }

            int extraWeight = minimumWeightMatching(oddDegreeNodes);
            System.out.println("The Result: " + (totalWeight + extraWeight));

        }
    }

    // Floyd-Warshall to find shortest paths between all pairs of nodes
    static void floydWarshall() 
    {
        for (int k = 1; k <= n; k++) 
        {
            for (int i = 1; i <= n; i++) 
            {
                for (int j = 1; j <= n; j++) 
                {
                    if (dist[i][k] + dist[k][j] < dist[i][j]) 
                    {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }
    }

    // Minimum weight perfect matching for odd-degree nodes
    static int minimumWeightMatching(List<Integer> oddDegreeNodes) 
    {
        int size = oddDegreeNodes.size();
        if (size == 0) return 0;

        int[][] matching = new int[size][size];
        for (int i = 0; i < size; i++) 
        {
            for (int j = 0; j < size; j++) 
            {
                matching[i][j] = dist[oddDegreeNodes.get(i)][oddDegreeNodes.get(j)];
            }
        }

        // Use bitmask DP to find the minimum weight matching
        int[] dp = new int[1 << size];
        Arrays.fill(dp, INF);
        dp[0] = 0;

        for (int mask = 0; mask < (1 << size); mask++) 
        {
            if (dp[mask] == INF) continue;

            int i = 0;
            while (((1 << i) & mask) != 0) i++;
            for (int j = i + 1; j < size; j++) 
            {
                if (((1 << j) & mask) == 0) 
                {
                    int newMask = mask | (1 << i) | (1 << j);
                    dp[newMask] = Math.min(dp[newMask], dp[mask] + matching[i][j]);
                }
            }
        }
        return dp[(1 << size) - 1];
    }
}
