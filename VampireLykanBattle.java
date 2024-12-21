import java.util.*;

public class VampireLykanBattle 
{
    public static void main(String[] args) 
    
    {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter test cases: ");
        int T = sc.nextInt();
        
        while (T-- > 0) {
            System.out.print("Enter number of dual fights: ");
            int n = sc.nextInt();
            
            // Graph representation using adjacency list
            Map<Integer, List<Integer>> graph = new HashMap<>();
            
            System.out.println("Enter the pairs of fighters (u v) for each fight:");
            for (int i = 0; i < n; i++) {
                int u = sc.nextInt();
                int v = sc.nextInt();
                
                graph.computeIfAbsent(u, k -> new ArrayList<>()).add(v);
                graph.computeIfAbsent(v, k -> new ArrayList<>()).add(u);
            }
            
            System.out.println("Maximum number of Vampires or Lykans: " + maxVampiresOrLykans(graph));
        }
        sc.close();
    }
    
    // Method to determine the maximum number of Vampires or Lykans
    private static int maxVampiresOrLykans(Map<Integer, List<Integer>> graph) 
    {
        Set<Integer> visited = new HashSet<>();
        int maxCount = 0;
        
        for (int node : graph.keySet()) 
        {
            if (!visited.contains(node)) 
            
            {
                maxCount += bfs(node, graph, visited);
            }
        }
        
        return maxCount;
    }
    
    // BFS method to check bipartite graph and count maximum group size
    private static int bfs(int start, Map<Integer, List<Integer>> graph, Set<Integer> visited) 
    
    {
        Queue<Integer> queue = new LinkedList<>();
        Map<Integer, Integer> color = new HashMap<>();
        
        queue.add(start);
        color.put(start, 0);
        visited.add(start);
        
        int[] count = new int[2]; // count[0] for Vampires, count[1] for Lykans
        count[0]++;
        
        while (!queue.isEmpty()) 
        
        {
            int node = queue.poll();
            int currentColor = color.get(node);
            
            for (int neighbor : graph.getOrDefault(node, Collections.emptyList())) 
            
            {
                if (!visited.contains(neighbor)) 
                
                {
                    visited.add(neighbor);
                    queue.add(neighbor);
                    color.put(neighbor, 1 - currentColor);
                    count[1 - currentColor]++;
                } 
                
                else if (color.get(neighbor) == currentColor) 
                
                {
                    // This means the graph is not bipartite, but for this problem, assume it is always bipartite
                    // as we are considering dual fights
                }
            }
        }
        
        return Math.max(count[0], count[1]);
    }
}