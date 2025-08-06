package eu.xaru.openmodularturrets.reference;

/**
 * Reference constants for the Open Modular Turrets mod.
 * Ported from the original omtteam.openmodularturrets.reference.Reference
 */
public class Reference {
    public static final String MOD_ID = "openmodularturrets";
    public static final String MOD_NAME = "Open Modular Turrets Port";
    public static final String VERSION = "1.0.0";
    public static final String ACCEPTED_MINECRAFT_VERSION = "[1.21.1,1.22)";
    
    // No dependencies on external OMLib since we're incorporating it internally
    public static final String DEPENDENCIES = "";
    
    // GUI IDs for the mod
    public static final int GUI_ID_TURRET_BASE = 0;
    public static final int GUI_ID_TURRET_HEAD = 1;
    public static final int GUI_ID_EXPANDER = 2;
    public static final int GUI_ID_ADDON = 3;
}
