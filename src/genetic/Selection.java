package genetic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import synth.SynthNode;

public class Selection {

    final static int k = 10;
    
    public static ArrayList<SynthNode> tournamentSelect(ArrayList<Integer> ranking, ArrayList<SynthNode> pop) {
        ArrayList<SynthNode> parents = new ArrayList<>();
        HashSet<Integer> sublist = new HashSet<>();
        Random random = new Random();
        int size = ranking.size();
        while (sublist.size() < k) {
            sublist.add(random.nextInt(size));
        }
        Integer parent1 = ranking.size();
        for (Integer rank : sublist) {
            if (parent1 > rank) parent1 = rank;
        }

        sublist.clear();
        while (sublist.size() < k) {
            sublist.add(random.nextInt(size));
        }
        Integer parent2 = ranking.size();
        for (Integer rank : sublist) {
            if (rank == parent1) continue;
            if (parent1 > rank) parent1 = rank;
        }

        parents.add(pop.get(ranking.get(parent1)));
        parents.add(pop.get(ranking.get(parent2)));
        return parents;
    }
}
