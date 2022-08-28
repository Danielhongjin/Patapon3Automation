package data.sequences;

import models.Drum;
import models.Sequence;

/**
 * Represents the attack input sequence data.
 * @author Daniel Jin
 * @version 1.0
 */
public class AttackSequence extends Sequence {
    public Drum[] sequence = { Drum.PON, Drum.PON, Drum.PATA, Drum.PON };
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
