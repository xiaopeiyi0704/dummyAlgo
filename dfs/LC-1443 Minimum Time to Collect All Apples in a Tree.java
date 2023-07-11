/* observation
1 need to traverse all the tree to confirm the apples
2 once the node is apple we count 2 steps - back and forth
3 do not count the path without apples
4 start from index 0
5 need to build a graph for start a node to adjacent nodes
  the graph should be bi-direction - why
  e.g [0, 2], [1, 2] 
  if not bi-direction, start from 0, never get a chance to 1
  but if bi-direction, need to make sure same node is only visited once, otherwise stack overflow
*/
// TC : e + v
// SC : recursion call stack n, visited n, graph e + v
class Solution {
    public int minTime(int n, int[][] edges, List<Boolean> hasApple) {
        List<Integer>[] graph = buildGraph(n, edges); // build adjacent graph
        boolean[] visited = new boolean[n]; // visited to track if the node is ever visited
        return dfs(graph, 0, hasApple, visited);
    }
    
    private int dfs(List<Integer>[] graph, int v, List<Boolean> hasApple, boolean[] visited) {
        
        int total = 0;
        visited[v] = true;
        for (int node : graph[v]) {
            if (!visited[node]) { // check visited to avoid the node is duplicated visit
                total+= dfs(graph, node, hasApple, visited);
            }
        }
        
        // if it is apple, it is counted
        // if it is not apple, but it neighbor has apples, it is counted since have to pass this one to reach to its neighbor
        // if current node is 0, then no need to count since no "efforts" needed
        if ((hasApple.get(v) || total > 0) && v != 0) {
            total+= 2; // each time count 2 since back and forth(need to back to index 0)
        }
        
        return total;
    }
    
    private List<Integer>[] buildGraph(int n, int[][] edges) {
        List<Integer>[] graph = new List[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<Integer>();
        }
        
        for (int[] edge : edges) {
            int from = edge[0];
            int to = edge[1];
            
            graph[from].add(to);
            graph[to].add(from); // bi-direction to make sure from 0 to all the other nodes
        }
        
        return graph;
    }
}