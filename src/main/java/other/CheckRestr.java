package other;

import java.util.*;

public class CheckRestr {

    public static List<Pair> checkInput(int n, List<Pair> restrictions, List<Pair> input) {
        List<Pair> result = new ArrayList<>();
        Solver solver = new Solver(n, restrictions);
        for (Pair p : input) {

            if (solver.check(p)) {
                solver.join(p);
                result.add(p);
            }
        }
        return result;
    }
}

class Pair {
    int first;
    int second;

    public Pair(int first, int second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", this.first, this.second);
    }
}

class Solver {
    int n;
    int[] componentMap;
    List<Set<Integer>> components = new ArrayList<>();
    List<Set<Integer>> conflicts = new ArrayList<>();

    public Solver(int n, List<Pair> restrictions) {
        this.n = n;
        componentMap = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            componentMap[i] = i;
            Set<Integer> s = new HashSet<>();
            s.add(i);
            components.add(s);
            conflicts.add(new HashSet<>());
        }
        for (Pair r : restrictions) {
            conflicts.get(r.first).add(r.second);
            conflicts.get(r.second).add(r.first);
        }
    }

    public void join(Pair p) {
        int a = p.first;
        int b = p.second;
        int repA = componentMap[a];
        int repB = componentMap[b];
        if (repA == repB) return;
        Set<Integer> setA = components.get(repA);
        Set<Integer> setB = components.get(repB);
        if (setA.size() > setB.size()) {
            setA.addAll(setB);
            conflicts.get(repA).addAll(conflicts.get(repB));
            componentMap[b] = repA;
        } else {
            setB.addAll(setA);
            conflicts.get(repB).addAll(conflicts.get(repA));
            componentMap[a] = repB;
        }
    }

    public boolean check(Pair p) {
        int repA = componentMap[p.first];
        int repB = componentMap[p.second];
        return !(conflicts.get(repA).contains(p.second) || conflicts.get(repB).contains(p.first));
    }
/*
1-2     3-4
p: 1-3
restr: 2-4
*/
}