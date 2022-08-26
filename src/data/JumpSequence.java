package data;

import types.Drum;
import types.Sequence;

/*
 * Represents the jump input sequence data.
 * @author Daniel Jin
 * @version 1.0
 */
public class JumpSequence extends Sequence {
    public Drum[] sequence = { Drum.DON, Drum.DON, Drum.CHAKA, Drum.CHAKA };
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
