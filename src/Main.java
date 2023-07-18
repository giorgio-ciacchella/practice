import java.util.*;

public class Main {

  /**
   *     0 1 2  3 4 5
   * A = 3 5 7 11 5 8
   * B = 5 7 11 10 5 8
   * <p>
   * BS = [(1,0)(1,4)(2,1)(3,2)(4,0)(4,4)(5,5)]
   * Disjoint = [(2,1)(3,2)(5,5)]
   * <p>
   * <p>
   *
   *
   *
   * {
   *     3: [[0]],
   *     5: [[1,0], [4,0], [1,4], [4,4]],
   *     7: [[2,1]],
   *     11: [[3,2]],
   *     8: [[5,5]]
   * }
   *
   *    A = 3 5 7 11 5 8
   *    B = 3 7 11 10 5 8
   *
   *
   * {
   *    3: [[0,0]],
   *    5: [[1,4], [4,4]],
   *    7: [[2,1]],
   *    11: [[3,2]],
   *    8: [[5,5]]
   * }
   *
       *      0 1 2  3 4 5
   *    * A = 3 5 7 11 5 8
   *    * B = 5 7 11 3 5 8
   */

  public static int beautifulPairs(List<Integer> A, List<Integer> B) {
    // Write your code here




    return calculatePairs(A,B, new HashMap<>());
  }

  private static int calculatePairs(List<Integer> a, List<Integer> b, Map<List<Integer>, Integer> beautifulMap) {

//    if (beautifulMap.containsKey(b)) {
//      return beautifulMap.get(b);
//    }
    Set<Integer> aIndexes = new HashSet<>();
    Set<Integer> bIndexes = new HashSet<>();

    int size = 0;

    int bValue = b.get(b.size() - 1);
    for (int i = 0; i < a.size(); i++) {
      int aValue = a.get(i);
      if (bValue == aValue) {
        return 1;
      }
    }

//    beautifulMap.put(b, size);
    return size;
  }




  public static void main(String[] args) {
    System.out.println(beautifulPairs(List.of(3, 5, 7, 11, 5, 8), List.of(5, 7, 11, 10, 5, 8)));
  }
}
