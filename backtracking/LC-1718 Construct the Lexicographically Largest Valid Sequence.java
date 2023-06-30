/*
// observation
// 1 total result length should be n * 2 - 1
// 2 track the times of usage of each number
    each number i can be only use twice with i difference. So we do not need to track the times of usage anymore
    each time we use i at index, then we use i at index + i as well. Then each time we consume number i twice, then no need to track anymore
// 3 track the index usage of each time
    we can give a inital value such as -1 to indicate the index has not been used yet
// 4 track if the number already used
    either use an array or a set to check- I use Set here
// 5 try all the scenarios
// 6 how to keep the lexicographically? - try large number first
*/
class Solution {
    Set<Integer> used; // track if the number already used
    int[] arr; // contains the number we used at the index. e.g arr[5] means at index 0 we use 5
    boolean flag; // stop flag, once there is a solution we return, since it is the best solution if we try each number as dcsending order
    int[] res;
    public int[] constructDistancedSequence(int n) {
        int len = n * 2 - 1; // result length
        used = new HashSet<>();
        res = new int[len];
        arr = new int[len];
        Arrays.fill(arr, -1); // initial value, -1 means current index is not used
        backtracking(n, len, 0);
        if (flag) return res;
        
        return res;
    }
    
    private void backtracking(int n, int len, int index) {
        if (flag) return;  // stop flag, once there is a solution we return, since it is the best solution if we try each number as dcsending order
        
        if (len == index) {  // reach to the end, get result, set flag and return
            flag = true;
            System.arraycopy(arr, 0, res, 0, 2 * n - 1);
            return;
        }
        
        // if current index is already used, then we need to process next index
        // it guarateens the index have been used all follow the rule, so no need to do anything else, just move forward
        if (arr[index] != -1) { 
            backtracking(n, len, index + 1);
            return;
        }
        
        for (int i = n; i >= 1; i--) { // try large number first to get the lexicographically order
            if (used.contains(i)) continue;
            
            if (i == 1) { // if is 1, it is only used once, no need to consider about i + index position
                arr[index] = i;
                used.add(i); // mark this number i has been used
                backtracking(n, len, index + 1);
                used.remove(i);
                arr[index] = -1;
                
            } else {
                // if not 1, then we need to make sure index + i position is availabe to be used
                // otherwise there is confliction with others, then need to continue the loop to try other(smaller) number/i
                if (index + i < len && arr[index + i] == -1) {                       
                    arr[index] = i; // here each time process and backtrack two position/value, i and i + index
                    arr[index + i] = i;
                    used.add(i); // mark this number i has been used
                    backtracking(n, len, index + 1);
                    used.remove(i);
                    arr[index] = -1;
                    arr[index + i] = -1;
                }
            }
        }
    }
}