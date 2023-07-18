import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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
    beautifulPairs = getOnlyDisjoinPairs(beautifulPairs);
    int maxSize = beautifulPairs.size();

    for (int aValue : A) {
      for (int i = 0; i < B.size(); i++) {
        beautifulPairs.addAll(calculatePairs(A, new Pair<>(i, aValue), beautifulMap));
        for (int j = 0; j < B.size(); j++) {
          if (i==j) continue;

          final Integer bValue = B.get(j);
          beautifulPairs.addAll(calculatePairs(A, new Pair<>(j, bValue), beautifulMap));

        }
        beautifulPairs = getOnlyDisjoinPairs(beautifulPairs);
        if (beautifulPairs.size() > maxSize) {
          maxSize = beautifulPairs.size();
        }
        beautifulPairs.clear();
      }
    }



    return maxSize;
  }

  private static List<Pair<Integer>> getOnlyDisjoinPairs(final List<Pair<Integer>> beautifulPairs) {
    final Set<Integer> aIndexes = new HashSet<>();
    final Set<Integer> bIndexes = new HashSet<>();

    return beautifulPairs.stream()
        .filter(integerPair -> {
          if (aIndexes.contains(integerPair.a) || bIndexes.contains(integerPair.b)) {
            return false;
          }
          aIndexes.add(integerPair.a);
          bIndexes.add(integerPair.b);
          return true;
        })
        .collect(Collectors.toList());
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
