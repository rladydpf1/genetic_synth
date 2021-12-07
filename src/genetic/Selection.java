package genetic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import synth.SynthNode;

public class Selection {
    
    public static ArrayList<SynthNode> tournamentSelect(ArrayList<Integer> ranking, ArrayList<SynthNode> pop) {
        ArrayList<SynthNode> parents = new ArrayList<>();
        HashSet<Integer> sublist = new HashSet<>();
        Random random = new Random();
        int size = ranking.size();
        int k = 5;
        if (size <= k) {
            k = size - 1;
        }
        while (sublist.size() < k) {
            sublist.add(random.nextInt(size));
        }
        Integer parent1 = ranking.size() - 1;
        for (Integer rank : sublist) {
            if (parent1 > rank) parent1 = rank;
        }

        sublist.clear();
        while (sublist.size() < k) {
            sublist.add(random.nextInt(size));
        }
        Integer parent2 = ranking.size() - 1;
        for (Integer rank : sublist) {
            if (rank == parent1) continue;
            if (parent2 > rank) parent2 = rank;
        }

        parents.add(pop.get(ranking.get(parent1)));
        parents.add(pop.get(ranking.get(parent2)));
        return parents;
    }
}
