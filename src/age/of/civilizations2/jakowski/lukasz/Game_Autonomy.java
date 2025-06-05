package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.files.*;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.*;

class Game_Autonomy
{
    protected List<AutonomyStatus> lAutonomy;
    private String sBC;
    
    protected Game_Autonomy() {
        super();
        this.loadAutonomy();
    }
    
    protected final void loadAutonomy() {
        this.lAutonomy = new ArrayList<AutonomyStatus>();
        try {
            final FileHandle fileList = Gdx.files.internal("game/Vassals.json");
            final String fileContent = fileList.readString();
            final Json json = new Json();
            //ignore unknownfields true so compatible with BE2 coding
            json.setIgnoreUnknownFields(true);
            json.setElementType(ConfigAutonomyData.class, "Vassals", Data_Autonomy.class);
            ConfigAutonomyData data = new ConfigAutonomyData();
            data = json.fromJson(ConfigAutonomyData.class, fileContent);

            int i = 0;
            for (final Object e : data.Vassals) {
                final Data_Autonomy tempData = (Data_Autonomy)e;
                this.lAutonomy.add(new AutonomyStatus(i, tempData.Name, tempData.AUTO_JOIN_WAR, tempData.CAN_CONTROL_MILITARY, tempData.CAN_CONTROL_ECONOMY, tempData.AUTO_ACCEPT_TRADE, tempData.COLOR_STATUS, tempData.FLAG_STATUS, tempData.AVAILABLE_SINCE_AGE_ID, tempData.DIPLOMACY_COST, tempData.PRECEDING, tempData.FOLLOWING));
                i++;
            }
        }
        catch (final GdxRuntimeException ex) {
            Gdx.app.log("AoC2.5", "Vassal JSON not detected!");
            this.lAutonomy.add(new AutonomyStatus(0,"DependantState", true, true, true, true, 0.5, 1, 0, 10, 3, 4));
            this.lAutonomy.add(new AutonomyStatus(1,"DirectlyControlledZone", true, false, false, true, 0.0, 3, 1, 25, 1, 2));
            this.lAutonomy.add(new AutonomyStatus(2,"AutonomousRegion", true, false, true, true, 0.25, 2, 0, 20, 1, 3));
            this.lAutonomy.add(new AutonomyStatus(3,"ColonialMandate", true, true, false, false, -0.25, 1, 5, 16, 2, 4));
            this.lAutonomy.add(new AutonomyStatus(4,"Dominion", false, true, true, false, 2.0, 1, 6, 10, 0, 4));
        }
        for (AutonomyStatus autonomyStatus : this.lAutonomy) {
            autonomyStatus.setName(CFG.langManager.get(autonomyStatus.getName()));
        }
    }
    
    protected final void updateLanguage() {
        this.loadAutonomy();
    }

    protected final AutonomyStatus getAutonomy(final int i) {
        return this.lAutonomy.get(i);
    }


    protected final ArrayList<Boolean> canChangeToAutonomy(int iCivID) {
        if (CFG.menuManager.getInManageDiplomacy() || CFG.SPECTATOR_MODE) {
            //if in editor, auto accept
            ArrayList<Boolean> ret = new ArrayList<Boolean>();
            for (int i = 0; i < this.lAutonomy.size(); i++) {
                if (i == CFG.game.getCiv(CFG.game.getCiv(iCivID).getPuppetOfCivID()).getVassal_AutonomyStatus(iCivID).getIndexOf()) {
                    ret.add(false);
                } else {
                    ret.add(true);
                }
            }
            return ret;
        }

        final ArrayList<Boolean> out = new ArrayList<Boolean>();
        for (int i = 0; i < this.lAutonomy.size(); ++i) {
            if (i == CFG.game.getCiv(CFG.game.getCiv(iCivID).getPuppetOfCivID()).getVassal_AutonomyStatus(iCivID).getIndexOf()) {
                out.add(false);
            } else if (CFG.game.getCiv(CFG.game.getCiv(iCivID).getPuppetOfCivID()).getDiplomacyPoints() < this.lAutonomy.get(i).getDiploCost()) {
                out.add(false);
            } else if (Game_Calendar.CURRENT_AGEID < this.lAutonomy.get(i).getAge()) {
                out.add(false);
            } else {
                out.add(true);
            }
        }
        return out;
    }

    protected final int changeAutonomyCost(int nCivID) {
        return (int)((double)CFG.game.getGameScenarios().getScenario_StartingPopulation() * ((double)(0.165F + 0.115F * CFG.game.getCiv(nCivID).getTechnologyLevel()) + 0.0015 * (double)Math.min(CFG.game.getCiv(nCivID).getNumOfProvinces(), 100)));
    }

    protected static class ConfigAutonomyData
    {
        protected String Age_of_Civilizations;
        protected ArrayList Vassals;
        
        protected ConfigAutonomyData() {
            super();
        }
    }
    
    protected static class Data_Autonomy
    {
        protected String Name;
        protected boolean AUTO_JOIN_WAR;
        protected boolean CAN_CONTROL_MILITARY;
        protected boolean CAN_CONTROL_ECONOMY;
        protected boolean AUTO_ACCEPT_TRADE;
        protected double COLOR_STATUS;
        protected int FLAG_STATUS;
        protected int AVAILABLE_SINCE_AGE_ID;
        protected int DIPLOMACY_COST;
        protected int PRECEDING;
        protected int FOLLOWING;

        protected Data_Autonomy() {
            super();
        }
    }
}
