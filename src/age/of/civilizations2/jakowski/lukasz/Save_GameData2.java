package age.of.civilizations2.jakowski.lukasz;

import java.io.Serializable;

class Save_GameData2 implements Serializable {
   private static final long serialVersionUID = 0L;
   protected float AI_AGGRESSIVNESS;
   //dynamicevents change//
   protected boolean DYNAMIC_EVENTS = true;
   protected boolean PLAYER_PEACE = false;
   protected boolean AI_VASSALS = false;
   protected boolean AI_DIPLOMACY = true;
   protected DynamicEventManager dynamicEventManager;

   protected final void buildData() {
      this.AI_AGGRESSIVNESS = Game_Calendar.AI_AGGRESSIVNESS;
      this.DYNAMIC_EVENTS = CFG.DYNAMIC_EVENTS;
      this.PLAYER_PEACE = CFG.PLAYER_PEACE;
      this.AI_VASSALS = CFG.AI_VASSALS;
      this.AI_DIPLOMACY = CFG.AI_DIPLOMACY;
      this.dynamicEventManager = CFG.dynamicEventManager;
   }
}
