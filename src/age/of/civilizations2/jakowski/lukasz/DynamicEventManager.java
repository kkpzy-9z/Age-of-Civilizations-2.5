package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.Gdx;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

class DynamicEventManager implements Serializable {
    private static final long serialVersionUID = 0L;
    protected DynamicEventManager_Economy eventManagerEconomy;
    protected DynamicEventManager_CivilWar eventManagerCivilWar;
    protected DynamicEventManager_War eventManagerWar;
    protected DynamicEventManager_Leader eventManagerLeader;
    protected DynamicEventManager_Class eventManagerClass;
    protected ArrayList<Integer> lEventIndex;

    protected DynamicEventManager() {
        this.eventManagerEconomy = new DynamicEventManager_Economy();
        this.eventManagerCivilWar = new DynamicEventManager_CivilWar();
        this.eventManagerWar = new DynamicEventManager_War();
        this.eventManagerLeader = new DynamicEventManager_Leader();
        this.eventManagerClass = new DynamicEventManager_Class();
        this.lEventIndex = new ArrayList<Integer>();
    }

    protected void newTurnInvokeEvents() {
        //sort from high to low so no indexoutofbounds when removing lowest
        this.lEventIndex.sort(Collections.reverseOrder());
        for (int i: this.lEventIndex) {
            CFG.eventsManager.removeEvent(i);
        }
        this.lEventIndex.clear();

        this.eventManagerCivilWar.invokeCivilWarEvents();
        this.eventManagerWar.invokeWarEvents();
        this.eventManagerEconomy.invokeEconomicEvents();
        this.eventManagerLeader.invokeLeaderEvents();
        this.eventManagerClass.invokeClassEvents();
        //etc etc
    }

    protected void addEventIndex(Event_GameData nEvent) {
        Gdx.app.log("AoC2.5", nEvent.getEventTag() + " firing");
        try {
            CFG.eventsManager.eventsGD.lEvents.add(nEvent);
            CFG.eventsManager.eventsGD.iEventsSize = CFG.eventsManager.eventsGD.lEvents.size();

            int i = CFG.eventsManager.eventsGD.iEventsSize - 1;

            CFG.game.getCiv(((Event_GameData)CFG.eventsManager.eventsGD.lEvents.get(i)).getCivID()).addEventToRunID(i);
            ((Event_GameData)CFG.eventsManager.eventsGD.lEvents.get(i)).setWasFired(!((Event_GameData)CFG.eventsManager.eventsGD.lEvents.get(i)).getRepeatable());

            this.lEventIndex.add(i);
        } catch (IndexOutOfBoundsException e) {
            CFG.exceptionStack(e);
            Gdx.app.log("AoC2.5", nEvent.getEventTag() + " failure");
        }
    }

    protected float randomF(float max, float min, boolean round) {
        //if round is false, min inclusive, max exclusive
        //otherwise both inclusive bc rounding
        return (float)(round ? Math.round((Math.random() * (max - min)) + min) : (Math.random() * (max - min)) + min);
    }

    protected final void clearData() {
        this.lEventIndex.clear();
        this.lEventIndex = null;
        this.eventManagerEconomy = null;
        this.eventManagerCivilWar = null;
        this.eventManagerWar = null;
        this.eventManagerLeader = null;
    }
}
