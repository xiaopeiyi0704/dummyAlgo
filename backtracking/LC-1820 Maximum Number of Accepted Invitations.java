/* TLE
1 m boys, n girls - 1 boy can try to invite n girls(can ONLY take one)
2 based on #1 need to find a combination of max pair(invitation)
    e.g #1boy can invite #1girl and #2girl(all agree), and 2boy only invite #1girl(#2girl disagree)
    then max is 2, if we let #1boy invite #2 girl. 
3 need to track which girl is not available anymore
4 try all the scenarios and get the max one, use #3 for backtracking
*/
// TC: TLE n^m
// SC: recursion stack m, and set is n total is m + n
class Solution {
    Set<Integer> set;
    int max = 0;
    public int maximumInvitations(int[][] grid) {
        set = new HashSet<>();
        
        // start from 1st boy and current sum is 0
        backtracking(grid, 0, 0);
        
        return max;
    }
    
    private void backtracking(int[][] grid, int index, int sum) {
        // base case when all the boys made choices
        if (index == grid.length) {
            max = Math.max(sum, max);
            return;
        }
        
        // each boy has n girls to invite
        for (int i = 0; i < grid[0].length; i++) {
            // if the girl disagree or the girl already taken away then skip
            if (grid[index][i] == 0 || set.contains(i)) continue; 
            
            // otherwise take the girl
            set.add(i);
            backtracking(grid, index + 1, sum + 1); // next boy's turn and sum + 1 since get 1 pair
            set.remove(i);
        }
        // why we need this - there is a possiblity that the boy cannot take any girl (all disgree or the agree ones are already taken away)
        backtracking(grid, index + 1, sum);
    }
}