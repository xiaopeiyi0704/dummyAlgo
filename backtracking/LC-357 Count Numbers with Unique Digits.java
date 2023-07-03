/*observation
1 the selectable numbers are 0 to 9
2 need to track used digit to make sure no duplication usage
3 once there is a way to reach to the end, we count 1
4* from each "branch in the backtrack tree" we count start from 1 NOT 0 - why?
   e.g n is 2, the valid result can be either 1 or 2 digits, DO NOT HAVE TO BE 2 digits
   e.g n is 2, start from 1, could be 10, 12...19, but 1 itself is also an answer.
   e.g n is 3, start from 1, could be 100, 102...109, but 10 itself is also an answer.
   e.g n is 3, start from 1, could be 120, 123...129, but 12 itself is also an answer.
   so, each node of the "backtracking tree" along the completed path(without hit to the end of the path), every node is also an answer
   when n = 3, when we consider the each complete path,
                     1
            /       \  ....  \
           10       12       19
         /   \
       102...109
   
4 try all the scenarios
*/
// TC: 10^n
// SC: n for used set, n for recursion call stack
class Solution {
    Set<Integer> used;
    public int countNumbersWithUniqueDigits(int n) {
        used = new HashSet<>(); // track used digit to make sure no duplication usage
        return backtracking(n, 0);
    }
    
    private int backtracking(int n, int index) {
        // base case
        // once there is a way to reach to the end, we count 1
        if (index == n) { 
            return 1;
        }

        // so at each node(sub problem), we count start from 1 NOT 0
        int result = 1; 
        for (int i = index == 0 ? 1 : 0; i <= 9; i++) { // avoid 01, 02....
            if (used.contains(i)) continue; // avoid duplication digit usage
            
            used.add(i);
            result+= backtracking(n, index + 1);
            used.remove(i);
        }
        
        return result;
    }
}