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

    final HashMap<List<Integer>, Map<Integer, Set<Integer>>> beautifulMap = new HashMap<>();
    List<Integer> bCopy = new ArrayList<>(B);
    final Map<Integer, Set<Integer>> integerSetMap = calculatePairs(A, B, beautifulMap);

    for (int i = 0; i < B.size(); i++) {
      int bValue = B.get(i);
      for (int j = 0; j < A.size(); j++) {
        int aValue = A.get(j);
        if (aValue != bValue) {
          bCopy.remove(i);
          bCopy.add(i, aValue);
          calculatePairs(A, bCopy.subList(0, i), beautifulMap, aIndexes, bIndexes);
          final int newSize = calculatePairs(A, bCopy, beautifulMap, aIndexes, bIndexes);
          if (newSize > maxSize) {
            maxSize = newSize;
          }

        }
      }
    }
    return maxSize;
  }

  private static Map<Integer, Set<Integer>> calculatePairs(List<Integer> a, List<Integer> b, Map<List<Integer>, Map<Integer, Set<Integer>>> beautifulMap) {

    if (beautifulMap.containsKey(b)) {
      return beautifulMap.get(b);
    }
    if (b.size() == 0) {
      return Map.of();
    }

    final int bIndex = b.size() - 1;
    int bValue = b.get(bIndex);
    for (int i = 0; i < a.size(); i++) {
      int aValue = a.get(i);
      if (bValue == aValue) {
        final Map<Integer, Set<Integer>> partialPairMap = calculatePairs(a, b.subList(0, bIndex),
            beautifulMap);
        final int finalI = i;
        final Boolean isPairPresent = Optional.ofNullable(partialPairMap.get(bIndex))
            .map(set -> set.contains(finalI))
            .orElse(false);
        if (!isPairPresent) {
          partialPairMap.computeIfPresent(bIndex, (integer,integers) -> {
            integers.add(finalI);
            return integers;
          });
        }
        beautifulMap.put(b, partialPairMap);
        return partialPairMap;
      }
    }
    final Map<Integer, Set<Integer>> calculatePairs = calculatePairs(a, b.subList(0, bIndex),
        beautifulMap);
    beautifulMap.put(b, calculatePairs);
    return calculatePairs;
  }




  public static void main(String[] args) {
    System.out.println(beautifulPairs(List.of(3, 5, 7, 11, 5, 8), List.of(5, 7, 11, 10, 5, 8)));
  }
}
