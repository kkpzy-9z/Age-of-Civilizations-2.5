package age.of.civilizations2.jakowski.lukasz;

import java.io.*;

class Scenario_GameData_Diplomacy_VassalsData implements Serializable
{
    private static final long serialVersionUID = 0L;
    private int iCivID;
    private int iCivLordID;
    private int iAutonomyStatus = -1;
    protected Scenario_GameData_Diplomacy_VassalsData(final int iCivID, final int iCivLordID, final int iAutonomyStatus) {
        super();
        this.setCivID(iCivID);
        this.setCivLordID(iCivLordID);
        this.setCivAutonomy(iAutonomyStatus);
    }
    
    protected final int getCivID() {
        return this.iCivID;
    }
    
    protected final void setCivID(final int iCivID) {
        this.iCivID = iCivID;
    }
    
    protected final int getCivLordID() {
        return this.iCivLordID;
    }
    
    protected final void setCivLordID(final int iCivLordID) {
        this.iCivLordID = iCivLordID;
    }

    protected final void setCivAutonomy(int iAutonomyStatus) {
        this.iAutonomyStatus = iAutonomyStatus;
    }

    protected final int getCivAutonomy() {
        return this.iAutonomyStatus;
    }
}
