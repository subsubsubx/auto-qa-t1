import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

    }

    private static boolean hasDuplicates(int[] arr) {
        Set<Integer> set = new HashSet<>();
        for (int j : arr) {
            if (!set.add(j)) return true;
        }
        return false;
    }

    private static boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) return false;
        Map<Character, Integer> map = new HashMap<>();
        Map<Character, Integer> map2 = new HashMap<>();
        char[] charArray = s.toCharArray();
        char[] charArray2 = t.toCharArray();
        extracted(charArray, map);
        extracted(charArray2, map2);
        return map.equals(map2);
    }

    private static void extracted(char[] charArray, Map<Character, Integer> map) {
        for (char c : charArray) {
            map.compute(c, (k,v)-> !map.containsKey(k) ? 1 : map.get(k) + 1);
        }
    }
}
