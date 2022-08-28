package models;

/**
 * Represents different action sequences to be used in the gameplay screen.
 * @author Daniel Jin
 * @version 1.0
 */
public class Sequence {
    // Drum sequence.
    public Drum[] sequence;
    // Timings per drum input.
    public Integer[] timings;
    
    public int getLength() {
        return sequence.length;
    }
    
    public Drum getDrum(int index) {
        return sequence[index];
    }
    
    public long getTiming(int index) {
        return timings[index];
    }
    
}
