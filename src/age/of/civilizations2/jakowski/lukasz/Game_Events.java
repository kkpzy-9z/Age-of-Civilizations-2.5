package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.files.*;
import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.xml.crypto.Data;
import java.util.*;

class Game_Events
{

    protected Game_Events() {
        super();
        this.loadEvents();
    }
    
    protected final void loadEvents() {
        try {
            final FileHandle fileList = Gdx.files.internal("game/Events.json");
            final String fileContent = fileList.readString();
            final Json json = new Json();

            json.setIgnoreUnknownFields(true);

            json.setElementType(ConfigDecisionData.class, "EconomyEvents", Data_Economy.class);
            json.setElementType(ConfigDecisionData.class, "ClassEvents", Data_Class.class);
            json.setElementType(ConfigDecisionData.class, "WarEvents", Data_War.class);
            json.setElementType(ConfigDecisionData.class, "LeaderEvents", Data_Leader.class);

            ConfigDecisionData data = new ConfigDecisionData();
            data = json.fromJson(ConfigDecisionData.class, fileContent);

            for (final Object e : data.EconomyEvents) {
                final Game_Events.Data_Economy tempData = (Game_Events.Data_Economy)e;

                DynamicEventManager_Economy.TURNS_UPPER_BOUND = tempData.TURNS_UPPER_BOUND;
                DynamicEventManager_Economy.TURNS_LOWER_BOUND = tempData.TURNS_LOWER_BOUND;
                DynamicEventManager_Economy.SCAN_TOPECONOMIES = tempData.SCAN_TOPECONOMIES;

                DynamicEventManager_Economy.CRASH_INVESTMENT_THRESHOLD = tempData.CRASH_INVESTMENT_THRESHOLD;
                DynamicEventManager_Economy.MIRACLE_INVESTMENT_THRESHOLD = tempData.MIRACLE_INVESTMENT_THRESHOLD;

                DynamicEventManager_Economy.CRASH_ECONOMY_DECREASE = tempData.CRASH_ECONOMY_DECREASE;
                DynamicEventManager_Economy.CRASH_ECONOMY_RIPPLE_DECREASE = tempData.CRASH_ECONOMY_RIPPLE_DECREASE;
                DynamicEventManager_Economy.CRASH_ECONOMY_RIPPLE_MIN = tempData.CRASH_ECONOMY_RIPPLE_MIN;

                DynamicEventManager_Economy.MIRACLE_ECONOMY_INCREASE = tempData.MIRACLE_ECONOMY_INCREASE;
                DynamicEventManager_Economy.MIRACLE_ECONOMY_RIPPLE_INCREASE = tempData.MIRACLE_ECONOMY_RIPPLE_INCREASE;
                DynamicEventManager_Economy.MIRACLE_ECONOMY_RIPPLE_MIN = tempData.MIRACLE_ECONOMY_RIPPLE_MIN;
            }

            for (Object e : data.ClassEvents) {
                final Game_Events.Data_Class tempData = (Game_Events.Data_Class)e;

                DynamicEventManager_Class.TURNS_UPPER_BOUND = tempData.TURNS_UPPER_BOUND;
                DynamicEventManager_Class.TURNS_LOWER_BOUND = tempData.TURNS_LOWER_BOUND;

                DynamicEventManager_Class.CHANGE_NEEDED_FOR_EVENT = tempData.CHANGE_NEEDED_FOR_EVENT;

                DynamicEventManager_Class.REVOLT_MAX_APPROVAL = tempData.REVOLT_MAX_APPROVAL;
                DynamicEventManager_Class.UNREST_MAX_APPROVAL = tempData.UNREST_MAX_APPROVAL;
                DynamicEventManager_Class.OBEY_MIN_APPROVAL = tempData.OBEY_MIN_APPROVAL;
                DynamicEventManager_Class.LOYAL_MIN_APPROVAL = tempData.LOYAL_MIN_APPROVAL;
            }

            for (Object e : data.WarEvents) {
                final Game_Events.Data_War tempData = (Game_Events.Data_War)e;

                DynamicEventManager_War.SCAN_TOPCIVILIZATIONS = tempData.SCAN_TOPCIVILIZATIONS;

                DynamicEventManager_CivilWar.MAX_ARMY_DISSENTERS = tempData.MAX_ARMY_DISSENTERS;
                DynamicEventManager_CivilWar.MIN_ARMY_DISSENTERS = tempData.MIN_ARMY_DISSENTERS;

                DynamicEventManager_CivilWar.MAX_ARMY_DISSENTERS_BUFFER = tempData.MAX_ARMY_DISSENTERS_BUFFER;
                DynamicEventManager_CivilWar.MIN_ARMY_DISSENTERS_BUFFER = tempData.MIN_ARMY_DISSENTERS_BUFFER;

                DynamicEventManager_CivilWar.MAX_TREASURY_TRANSFER = tempData.MAX_TREASURY_TRANSFER;
                DynamicEventManager_CivilWar.MIN_TREASURY_TRANSFER = tempData.MIN_TREASURY_TRANSFER;

                DynamicEventManager_CivilWar.MAX_PERC_PROVINCES_TO_TAKE = tempData.MAX_PERC_PROVINCES_TO_TAKE;
                DynamicEventManager_CivilWar.MIN_PROVINCES_WAR = tempData.MIN_PROVINCES_WAR;
            }

            for (Object e : data.LeaderEvents) {
                final Game_Events.Data_Leader tempData = (Game_Events.Data_Leader)e;

                DynamicEventManager_Leader.TURNS_UPPER_BOUND = tempData.TURNS_UPPER_BOUND;
                DynamicEventManager_Leader.TURNS_LOWER_BOUND = tempData.TURNS_LOWER_BOUND;
            }
        }
        catch (final GdxRuntimeException ex) {
            Gdx.app.log("AoC2.5", "Events JSON not detected!");
        }
    }

    protected final void updateLanguage() {
        this.loadEvents();
    }


    protected static class ConfigDecisionData
    {
        protected ArrayList EconomyEvents;
        protected ArrayList ClassEvents;
        protected ArrayList WarEvents;
        protected ArrayList LeaderEvents;
        
        protected ConfigDecisionData() {
            super();
        }
    }
    
    protected static class Data_Economy
    {
        protected int TURNS_UPPER_BOUND;
        protected int TURNS_LOWER_BOUND;
        protected int SCAN_TOPECONOMIES;

        protected float CRASH_INVESTMENT_THRESHOLD;
        protected float MIRACLE_INVESTMENT_THRESHOLD;

        protected int CRASH_ECONOMY_DECREASE;
        protected int CRASH_ECONOMY_RIPPLE_DECREASE;
        protected int CRASH_ECONOMY_RIPPLE_MIN;

        protected int MIRACLE_ECONOMY_INCREASE;
        protected int MIRACLE_ECONOMY_RIPPLE_INCREASE;
        protected int MIRACLE_ECONOMY_RIPPLE_MIN;

        protected Data_Economy() {
            super();
        }
    }

    protected static class Data_Class
    {
        protected int TURNS_UPPER_BOUND;
        protected int TURNS_LOWER_BOUND;

        protected float CHANGE_NEEDED_FOR_EVENT;

        protected float REVOLT_MAX_APPROVAL;
        protected float UNREST_MAX_APPROVAL;
        protected float OBEY_MIN_APPROVAL;
        protected float LOYAL_MIN_APPROVAL;

        protected Data_Class() {
            super();
        }
    }

    protected static class Data_War
    {
        protected int SCAN_TOPCIVILIZATIONS;

        protected float MAX_ARMY_DISSENTERS;
        protected float MIN_ARMY_DISSENTERS;

        protected float MAX_ARMY_DISSENTERS_BUFFER;
        protected float MIN_ARMY_DISSENTERS_BUFFER;

        protected float MAX_TREASURY_TRANSFER;
        protected float MIN_TREASURY_TRANSFER;

        protected float MAX_PERC_PROVINCES_TO_TAKE;
        protected int MIN_PROVINCES_WAR = 5;

        protected Data_War() {
            super();
        }
    }

    protected static class Data_Leader
    {
        protected int TURNS_UPPER_BOUND;
        protected int TURNS_LOWER_BOUND;

        protected Data_Leader() {
            super();
        }
    }
}
