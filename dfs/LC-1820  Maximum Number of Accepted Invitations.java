/* observation
1 max pair can generate
2 if A has two choices, and B has 1, do not let A choose B's, let A choose the other one, then can generate more pair
3 based on #2, if there is confliction for boy A and boy B - a girl can be taken by both boy A and boy B
    e.g A already took a girl, but this girl can also be taken by B
    check if A has any other avaliable girl to choose and give this girl to B
    or check if B has any other option to choose then do not take A's
*/
// TC: boys m, girls n m *n
// SC: match n, set n, recursion call stack n = 3n
class Solution {
    public int maximumInvitations(int[][] grid) {
        int boys = grid.length;
        int girls = grid[0].length;
        
        // how many girls need to be taken
        int[] match = new int[girls];
        Arrays.fill(match, -1); // -1 means available, not taken yet
        
        int res = 0;
        for (int b = 0; b < boys; b++) {
            Set<Integer> set = new HashSet<>();
            if (dfs(grid, match, b, set, girls)) res++; 
        }
        
        return res;
    }
    
    private boolean dfs(int[][] grid, int[] match, int boy, Set<Integer> set, int girls) {
        for (int g = 0; g < girls; g++) {
            if (grid[boy][g] == 1 && !set.contains(g)) { // if can be invited
                set.add(g);
                // if available just take the girl
                // if not check the boy who already took the girl(match[g]) if has any other option to pick(check if A has any other avaliable girl to choose and give this girl to B)
                if (match[g] == -1 || dfs(grid, match, match[g], set, girls)) {
                    match[g] = boy;
                    return true;
                }
            }
        } // the for loop for check if B has any other option to choose then do not take A's (A has no more option in this case)
        return false;
    }
}