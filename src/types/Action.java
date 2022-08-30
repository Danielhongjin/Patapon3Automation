package types;

/**
 * Different actions to be used by scripts.
 * @author Daniel Jin
 * @version 1.0
 */
public enum Action {
    TOGAMEPLAY,
    TOMISSIONSELECT,
    TOBONEDETHBRIGATE,
    TOBONEDETHONTHECLIFF,
    TOWIPETHEGRINOFFTHEGARGOYLE,
    TOUBERHEROESNEVERREST,
    TOHOME,
    TOARMORY,
    RESETTIMESCALE,
    ALTERNATETIMESCALE,
    
    // Combinable, will be collated and used at once as a list of valid targets.
    SELLALLNONENCHANTED,
    SELLALLFL,
    SELLALLST,
    SELLALLPO,
    SELLALLHP,
    SELLALLAR,
    SELLALL
}