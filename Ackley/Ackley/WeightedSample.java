package Ackley;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Set;

public class WeightedSample {
    private PriorityQueue<Chromosome> H;
    private List<Chromosome> C;
    double threshold;
    private double wl;
    private int sampleSize;

    public WeightedSample(int sampleSize) {
        this.sampleSize = sampleSize;
        this.H = new PriorityQueue<>(Collections.reverseOrder());
        this.C = new ArrayList<>();
        this.threshold = 0.0;
        this.wl = 0.0;
    }

    public void update(Chromosome element) {
        if (element.getFitness() == 0)
            return;
        Set<Chromosome> N = new HashSet<>();
        if (this.H.size() + this.C.size() < this.sampleSize) {
            this.H.add(element);
            return;
        }
        if (element.getFitness() < this.threshold) {
            N.add(element);
            this.wl += element.getFitness();
        } else {
            H.add(element);
        }
        while (!this.H.isEmpty() && (this.wl >= (this.sampleSize - this.H.size()) * this.H.peek().getFitness())) {
            Chromosome el = this.H.poll();
            N.add(el);
            this.wl += el.getFitness();
        }
        this.threshold = this.wl / (this.sampleSize - this.H.size());
        double r = Math.random();
        Chromosome remove = null;
        for (Chromosome el : N) {
            r -= 1 - (el.getFitness() / this.threshold);
            if (r < 0) {
                remove = el;
                break;
            }
        }
        if (remove != null) {
            N.remove(remove);
        } else {
            removeRandom(this.C, r);
        }
        for (Chromosome el : N) {
            this.C.add(el);
        }
    }

    public List<Chromosome> getParents() {
        List<Chromosome> parents = new ArrayList<>();
        parents.addAll(this.H);
        parents.addAll(this.C);
        return parents;
    }

    private void removeRandom(List<Chromosome> list, double r) {
        Random rnd = new Random();
        int pos = rnd.nextInt(list.size());
        swap(list, pos, list.size() - 1);
        list.remove(list.size() - 1);
    }

    private void swap(List<Chromosome> list, int a, int b) {
        Chromosome aux = list.get(a);
        list.set(a, list.get(b));
        list.set(b, aux);
    }

}
