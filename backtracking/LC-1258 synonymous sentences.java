```
/*obversation
1 find all the synonyms words in the sentence and try all the scenarios.
2 how to try all the scenarios? - backtrack.
3 how to find all the synonyms? - DFS
    build a "graph" for each synonym set - goal-> by given any word in the synonym set, you should get all the other synonym words. why?
    the test case in the example is straightforward, "I am happy" ["happy","joy"], but in the real test case, it could be ["joy", "happy"]. So
    you have to get joy by happy and get happy by joy. SO YOU HAVE BUILD A GRAPH IN BIDIRECTION.
    To avoid duplicated usage for the word, we need a set to track if a synonym is already used.
    same synonym can appear in the text e.g "I am happy, because I am happy", so we need to make sure we do backtrack on the "synonym branch"
*/
//TC: assume text has s words, each word has n synonyms. find all synonyms s(o + e), backtrack is s^n. total is s^n + s(o + e)
//SC: result list max s^n, map is s * n, backtrack recursion is s, find all sysnonyms each time is n. total is s^n + s*n + s + n
class Solution {
    List<String> res;
    public List<String> generateSentences(List<List<String>> synonyms, String text) {
        res = new ArrayList<>();
        Map<String, List<String>> map = new HashMap<>();
        
        // build a graph, by given a word, we know if exists synonym
        // if synonym, by given a word, we know its all synonyms
        for(List<String> list : synonyms) {
            map.putIfAbsent(list.get(0), new ArrayList<>());
            map.putIfAbsent(list.get(1), new ArrayList<>());
            map.get(list.get(0)).add(list.get(1)); // bidirection since we do not know which word will appear in the text
            map.get(list.get(1)).add(list.get(0)); // bidirection since we do not know which word will appear in the text
        }
        
        String[] words = text.split(" "); // split the words to check one by one easily
        backtracking(map, words, 0, new LinkedList<String>()); 
        Collections.sort(res); // sort the result based on lexicographically
        
        return res;
    }
    
    private void backtracking(Map<String, List<String>> map, String[] words, int index, LinkedList<String> list) {
        //base case, once we check all the words in the text, then we get a result
        if (index == words.length) {
            res.add(String.join(" ", list));
            return;
        }
        // get current word in the text to check
        String w = words[index];
        
        if (map.containsKey(w)) { // if there is synonym
            List<String> synonyms = new ArrayList<>(); // to hold all current word synonyms
            Set<String> visited = new HashSet<>(); // to track the duplication of synonyms. Because same synonym could appear multi time in text, we need "new" this tracker at each occurance.
            getAllTheSynonyms(map, w, synonyms, visited); // dfs to get all the synonyms
            for (String synonym : synonyms) { // try each synonym of current word
                list.add(synonym);
                backtracking(map, words, index + 1, list); 
                list.removeLast(); // after try, need to backtrack to try another one
            }
           
        } else { // if current word does not have synonym, then just use it directly
            list.add(w);
            backtracking(map, words, index + 1, list);
            list.removeLast();
        }
    }
    // DFS get all the synonyms for a word. visited tracks the duplication.
    // O(E + V) where E are edges and V are vertices in the map
    private void getAllTheSynonyms(Map<String, List<String>> map, String curWord, List<String> synonyms, Set<String> visited) {
        if (visited.contains(curWord)) return;
        
        synonyms.add(curWord);
        visited.add(curWord);
        for (String w : map.get(curWord)) {
            getAllTheSynonyms(map, w, synonyms, visited);
        }
    }
}
```