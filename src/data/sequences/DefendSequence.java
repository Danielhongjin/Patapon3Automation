package data.sequences;

import models.Sequence;
import types.Drum;

/**
 * Represents the defend input sequence data.
 * @author Daniel Jin
 * @version 1.0
 */
public class DefendSequence extends Sequence {
    public Drum[] sequence = {Drum.CHAKA, Drum.CHAKA, Drum.PATA, Drum.PON};
    public Integer[] timings = {500, 500, 500, 500};
    
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
