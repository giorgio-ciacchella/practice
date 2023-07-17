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

    final Map<Pair<Integer>, List<Pair<Integer>>> beautifulMap = new HashMap<>();

    List<Pair<Integer>> beautifulPairs = new ArrayList<>();
    for (int i = 0; i < B.size(); i++) {
      int bValue = B.get(i);
      beautifulPairs.addAll(calculatePairs(A, new Pair<>(i, bValue), beautifulMap));
    }

    



    return beautifulPairs.size();
  }

  private static List<Pair<Integer>> calculatePairs(List<Integer> a, Pair<Integer> bPair, Map<Pair<Integer>, List<Pair<Integer>>> beautifulMap) {

    if (beautifulMap.containsKey(bPair)) {
      return beautifulMap.get(bPair);
    }

    List<Pair<Integer>> beautifulPairs = new ArrayList<>();

    for (int i = 0; i < a.size(); i++) {
      int aValue = a.get(i);
      if (aValue == bPair.b) {
        beautifulPairs.add(new Pair<>(i, bPair.a));
      }
    }
    beautifulMap.put(bPair, beautifulPairs);
    return beautifulPairs;
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



  public static void main(String[] args) {
    System.out.println(beautifulPairs(List.of(3, 5, 7, 11, 5, 8), List.of(5, 7, 11, 10, 5, 8)));
  }
}
