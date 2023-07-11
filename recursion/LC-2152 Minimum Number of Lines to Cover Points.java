/* observation
1 all the points should be crossed by a line
2 worst case is start from a point and connect all the other points
3 we need to know, when multi points can be crossed by single line - when same slope e.g [1,2],[2,3],[4,5] or [1,2],[2,2],[3,2]
4 we can start from #2, and try to remove unneccessary line, when #3 happens
*/
//TC: number of points n n^n*n
//SC: n!(map) + n!(newPoints)
class Solution {
    public int minimumLines(int[][] points) {
        // base case
        if (points.length == 0) return 0; 
        if (points.length == 1) return 1;
        
        // map for grouping points at the same slope - same slope points can be crossed by single line
        Map<Double, List<int[]>> map = new HashMap<>();
        int[] cur = points[0];
        // start to group - from one point, to connect other points, group the points have same slope
        for (int i = 1; i < points.length; i++) {
            double slope = getSlope(cur, points[i]);
            map.putIfAbsent(slope, new ArrayList<int[]>());
            map.get(slope).add(points[i]);
        }
        
        int min = Integer.MAX_VALUE;
        // now we know that one group is one line
        // let's try to keep the current group/line, and re-group other points(the points are not in current group)
        // re-group means try to check if there is a way make other points together has less groups than current
        for (Map.Entry<Double, List<int[]>> pair : map.entrySet()) {
            double key = pair.getKey();
            int size = pair.getValue().size();
            
            // total # of points that not in this group
            int remainingSize = points.length - 1 - size; // -1 is for cur
            
            if (remainingSize == 0) return 1;
            
            // construct new array of points to do the re-group
            int index = 0;
            int[][] newPoints = new int[remainingSize][2];
            for (int i = 1; i < points.length; i++) {
                if (getSlope(cur, points[i]) != key) { // points in current group do not need to re-group
                    newPoints[index][0] = points[i][0];
                    newPoints[index][1] = points[i][1];
                    index++;
                }
            }
            
            int count = minimumLines(newPoints) + 1; // minimumLines(newPoints) returns regroups number of group, +1 for current group
            
            min = Math.min(min, count); // get min group number
        }
        
        return min;
    }
    
    // calculate slope value
    private double getSlope(int[] p1, int[] p2) {
        if (p1[0] == p2[0]) return Double.MAX_VALUE; // same x in one group
        if (p1[1] == p2[1]) return 0; // same y in another group
        
        return (double)(p1[0] - p2[0]) / (double)(p1[1] - p2[1]);
    }
}