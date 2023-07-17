import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {

  /**
   *     0 1 2  3 4 5
   * A = 3 5 7 11 5 8
   * B = 5 7 11 10 5 8
   * <p>
   * BS = [(1,0)(1,4)(2,1)(3,2)(4,0)(4,4)(5,5)]
   * Problematic = [(1,0)(1,4)(4,0)(4,4)] => B = [0, 4]
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
   */

  public static int beautifulPairs(List<Integer> A, List<Integer> B) {
    // Write your code here
    int result = 0;

    final Map<Integer, List<Pair<Integer>>> beautifulMap = new HashMap<>();

    for (int i = 0; i < B.size(); i++) {
      int currentB = B.get(i);
      populateBeautifulMap(A, currentB, i, beautifulMap);
    }
    int maxDisjointSize = calculateMaxDisjointSize(beautifulMap);


    Set<Integer> possibleValues = new HashSet<>(beautifulMap.keySet());
    for (int bIndex = 0 ; bIndex < B.size(); bIndex++) {
      int finalBIndex = bIndex;
      int currentB = B.get(bIndex);
      removeValueFromMap(beautifulMap, finalBIndex, currentB);


      for (int aValue : possibleValues) {
        populateBeautifulMap(A, aValue, bIndex, beautifulMap);
        int newDisjointSize = calculateMaxDisjointSize(beautifulMap);
        if (newDisjointSize > maxDisjointSize) {
          maxDisjointSize = newDisjointSize;
        }
        removeValueFromMap(beautifulMap, finalBIndex, aValue);
      }
    }



    return maxDisjointSize;
  }

  private static void removeValueFromMap(Map<Integer, List<Pair<Integer>>> beautifulMap, int index, int value) {
    if (!beautifulMap.containsKey(value)) return;


    //TODO put a b null value if we remove all the entries

    List<Pair<Integer>> filteredList = beautifulMap.get(value)
            .stream()
            .filter(integerPair -> integerPair.b != index)
            .collect(Collectors.toList());
    if (filteredList.isEmpty()) {
      beautifulMap.put(value, )
    } else {
      beautifulMap.put(value, filteredList);
    }
  }

  private static int calculateMaxDisjointSize(Map<Integer, List<Pair<Integer>>> beautifulMap) {

    final Set<Integer> aIndexes = new HashSet<>();
    final Set<Integer> bIndexes = new HashSet<>();
    List<Pair<Integer>> disjointPairs = beautifulMap
            .values()
            .stream()
            .flatMap(Collection::stream)
            .filter(pair -> {
              if (aIndexes.contains(pair.a) || bIndexes.contains(pair.b) || pair.b == null) {
                return false;
              }
              aIndexes.add(pair.a);
              bIndexes.add(pair.b);
              return true;
            })
            .toList();
    return disjointPairs.size();
  }

  static final class Pair<T> {
    private final T a;
    private final T b;

    Pair(T a, T b) {
      this.a = a;
      this.b = b;
    }

    public T a() {
      return a;
    }

    public T b() {
      return b;
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == this) return true;
      if (obj == null || obj.getClass() != this.getClass()) return false;
      var that = (Pair) obj;
      return Objects.equals(this.a, that.a) &&
              Objects.equals(this.b, that.b);
    }

    @Override
    public int hashCode() {
      return Objects.hash(a, b);
    }

    @Override
    public String toString() {
      return "Pair[" +
              "a=" + a + ", " +
              "b=" + b + ']';
    }
  }

  private static void populateBeautifulMap(List<Integer> A, int currentB, int bIndex, Map<Integer, List<Pair<Integer>>> beautifulMap) {
      if (beautifulMap.containsKey(currentB)) {
        List<Pair<Integer>> newBeatifulList = beautifulMap.get(currentB)
                .stream()
                .flatMap(pair -> Optional.ofNullable(pair.b)
                        .map(integer -> Stream.of(pair, new Pair<>(pair.a, bIndex)))
                        .orElse(Stream.of(new Pair<>(pair.a, bIndex))))
                .toList();
        beautifulMap.put(currentB, newBeatifulList);
      } else {
        if (!beautifulMap.isEmpty()) {
          return;
        }
        for (int j = 0; j < A.size(); j++) {
          final int currentA = A.get(j);
          if (currentA == currentB) {
            int finalJ = j;

            Optional.ofNullable(beautifulMap.get(currentA))
                            .ifPresentOrElse(pairs -> beautifulMap.put(currentA, new ArrayList<>(pairs) {{
                              add(new Pair<>(finalJ, bIndex));
                            }}), () -> beautifulMap.put(currentA, List.of(new Pair<>(finalJ, bIndex))));

          } else {
            beautifulMap.put(currentA, List.of(new Pair<>(j, null)));
          }
        }
      }
  }


  public static void main(String[] args) {
    System.out.println(beautifulPairs(List.of(3, 5, 7, 11, 5, 8), List.of(5, 7, 11, 10, 5, 8)));
  }
}
