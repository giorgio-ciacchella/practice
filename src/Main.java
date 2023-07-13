import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Main {

  /**
   *     0 1 2  3 4 5
   * A = 3 5 7 11 5 8
   * B = 5 7 11 10 5 8
   *
   *     3 5 5 7 8 11
   *     5 5 7 8 10 11
   * BS = [(1,0)(1,4)(2,1)(3,2)(4,0)(4,4)(5,5)]
   * Problematic = [(1,0)(1,4)(4,0)(4,4)] => B = [0, 4]
   * Disjoint = [(2,1)(3,2)(5,5)]
   *     0 1 2 3  4 5
   * A = 3 5 7 11 5 8
   * B = 5 7 11 10 3 8
   * BS = [(0,4) (1,0) (2,1) (3,2) (4,0) (5,5)]
   *
   *
   *
   * [null, null, null, null, null, null, null]
   *
   */

  record Pair(int index, int value){}
  public static int beautifulPairs(List<Integer> A, List<Integer> B) {
    // Write your code here
    int result = 0;
    final ArrayList<Pair> aPairs = toPairs(A);
    aPairs.sort(Comparator.comparingInt(o -> o.value));
    final ArrayList<Pair> bPairs = toPairs(B);
    bPairs.sort(Comparator.comparingInt(o -> o.value));

    ArrayList<Pair> nonDisjointBeatifulPairs = new ArrayList<>();
    calculateNonDisjoint(aPairs, bPairs, nonDisjointBeatifulPairs);
    final List<Pair> beautifulDisjoint = caclulateBeautifulDisjoint(nonDisjointBeatifulPairs);

    return result;
  }

  private static List<Pair> caclulateBeautifulDisjoint(ArrayList<Pair> nonDisjointBeatifulPairs) {
    Set<Integer> aIndexes = new HashSet<>();
    HashSet<Integer> bIndexes = new HashSet<>();
    return nonDisjointBeatifulPairs
            .stream()
            .filter(pair -> {
              if (aIndexes.contains(pair.index) || bIndexes.contains(pair.value)) {
                return false;
              }
              aIndexes.add(pair.index);
              bIndexes.add(pair.value);
              return true;
            })
            .collect(Collectors.toList());
  }

  private static void calculateNonDisjoint(ArrayList<Pair> aPairs, ArrayList<Pair> bPairs, ArrayList<Pair> nonDisjointBeatifulPairs) {
    int i = 0;
    int j = 0;
    int jMatch = -1;

    while (i < aPairs.size() && j < bPairs.size()) {
      final int aValue = aPairs.get(i).value;
      final int bValue = bPairs.get(j).value;
      if (aValue == bValue) {
        nonDisjointBeatifulPairs.add(new Pair(aPairs.get(i).index, bPairs.get(j).index));
        if (jMatch == -1) {
          jMatch = j;
        }
        j++;
        continue;
      }
      if (bValue > aValue) {
        i++;
        j = Math.max(0, jMatch);
        continue;
      }
      jMatch = -1;
      j++;
    }
  }

  private static ArrayList<Pair> toPairs(final List<Integer> A) {
    return A.stream()
        .collect(Collector.of(
            ArrayList::new
            , (pairs, integer) -> {
              if (pairs.isEmpty()) {
                pairs.add(new Pair(0, integer));
              } else {
                pairs.add(new Pair(pairs.get(pairs.size() - 1).index + 1, integer));
              }
            }, (pairs, pairs2) -> {
              pairs.addAll(pairs2);
              return pairs;
            }));
  }


  public static void main(String[] args) {
    System.out.println(beautifulPairs(List.of(3, 5, 7, 11, 5, 8), List.of(5, 7, 11, 10, 5, 8)));
  }
}
