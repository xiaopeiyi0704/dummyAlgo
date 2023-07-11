/*
Observation rules
1 result length range - should be pattern's length + 1
2 each number can be only used once - need to track e.g Set
3 return the lexicographically smallest possible string num - always try the number from 1 to n order
4 follow the rule for I/D
*/
/*
Thought - backtracking - try all the scenarios from order 1 to n and follow above rules
1 start the 1st char and try it from 1 to need
2 for I, each time we look for the char from 1 to n, then we can make sure we keep the lexicographically order and no gap between numbers/chars
3 for D, we look for a smaller number than previous, only thing need to do is "increase the previous number" 
	- how? just redo/remove/backtracking the previous number and make it bigger. Not big enough? let it be bigger thru for loop(i to n)
	- how to do the backtracking. just return to finish current function call and back to previous number, and let previous function go to the next loop of the for iteration
*/

// TC: at each index, we have n choice and we have n numbers, so it is n^n
// SC: used set is n, result is n and recursion call stack is n so the total is 3n
class Solution {
    Set<Integer> used; // for track duplication
    String res; // for result
    public String smallestNumber(String pattern) {
        used = new HashSet<>();
        int n = pattern.length() + 1; // confirm value range  (1 to n]
		for (int i = 1; i <= n; i++) {
			StringBuilder sb = new StringBuilder();
			sb.append(i);
            used.add(i);
			if(res == null) backtracking(pattern, n, 0, sb);
			sb.deleteCharAt(sb.length() - 1); //backtracking
            used.remove(i); //backtracking
		}
        
        return res;
    }
    
    private void backtracking(String pattern, int n, int index, StringBuilder sb) {
		// base case - if we are able to handle/traverse all the chars in the pattern string by following the rule, then we are good to return.
        if (index >= pattern.length()) { 
            res = sb.toString();
            return;
        }
        
        for (int i = 1; i <= n; i++) {
            if (res != null) return; // once there is a solution, no need to keep running
			
            if (used.contains(i)) continue; // before processing each number, make sure it is not used

			char ch = pattern.charAt(index);
            
            if (ch == 'D' && (int)(sb.charAt(sb.length() - 1) - '0') < i) { // since each time we try from 1 to n, when meet "D", it may not follow the rule anymore. 
                 return; // so just return to let previous number/i be bigger thru the for loop iteration until meet the criteria
            }
            // if 'I', be confident to add it, since each time we try from 1, and guarateen it is lexicographically.
			// if 'D', when it reaches here that means the rule is followed(already increase the prev value, and it meets the criteria now)
            sb.append(i);
            used.add(i);
            backtracking(pattern, n, index + 1, sb);
            sb.deleteCharAt(sb.length() - 1);
            used.remove(i);
        }
    }
}