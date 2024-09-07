/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  age.of.civilizations2.jakowski.lukasz.Color_GameData
 *  age.of.civilizations2.jakowski.lukasz.Flag_Overlay_GameData
 */
package age.of.civilizations2.jakowski.lukasz;

import age.of.civilizations2.jakowski.lukasz.Color_GameData;
import age.of.civilizations2.jakowski.lukasz.Flag_Overlay_GameData;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Flag_GameData
implements Serializable {
    private static final long serialVersionUID = 0L;
    protected int iDivisionID;
    protected String overlayTag = "";
    protected List<Color_GameData> lDivisionColors = new ArrayList<Color_GameData>();
    protected List<Flag_Overlay_GameData> lOverlays = new ArrayList<Flag_Overlay_GameData>();
}
