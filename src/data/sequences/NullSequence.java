package data.sequences;

import models.Drum;
import models.Sequence;

/**
 * Represents the move input sequence data.
 * @author Daniel Jin
 * @version 1.0
 */
public class NullSequence extends Sequence {
    public Drum[] sequence = {};
    public Integer[] timings = {};

    @Override
    public int getLength() {
        return sequence.length;
    }

    @Override
    public Drum getDrum(int index) {
        return sequence[index];
    }

    @Override
    public long getTiming(int index) {
        return timings[index];
    }
}
