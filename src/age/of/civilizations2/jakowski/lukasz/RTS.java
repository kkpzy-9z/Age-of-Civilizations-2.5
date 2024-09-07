package age.of.civilizations2.jakowski.lukasz;

class RTS {
   protected static final int MAX_SPEED = 6;
   protected static int SPEED = 1;
   protected static boolean PAUSE = true;
   protected static boolean PAUSED_BY_NEXT_TURN = false;
   //new toggle battles var
   protected static boolean SHOW_BATTLE_RESULTS = true;
   protected static int SOURCE = 0;
   protected static final int[] TIME_REQUIRED_TO_ACTION = new int[]{1, 6500, 4000, 3000, 2000, 1250, 250};
   protected static final int[] TIME_REQUIRED_TO_ACTION_MOVEUNITS = new int[]{1, 1750, 1250, 1000, 1000, 750, 500};
   protected static final int[] TIME_REQUIRED_TO_ACTION_REPORT = new int[]{1, 2250, 2000, 1750, 1650, 1500, 500};
   protected static final int[] TIME_REQUIRED_TO_ACTION_LOADAI = new int[]{1, 750, 500, 1, 1, 1, 1};
   protected static final boolean[] SHOW_REPORT = new boolean[]{true, true, true, true, true, true, false};
   protected static long TIME_PAST = 0L;
   protected static long TIME_LAST_UPDATE = 0L;

   protected static void updateTimePast_AfterAction(float fPerc) {
      TIME_PAST -= (long)((int)((float)TIME_REQUIRED_TO_ACTION[SPEED] * fPerc));
      if (TIME_PAST < 0L) {
         TIME_PAST = 0L;
      }

   }

   protected static final void updateTime() {
      if (addTime()) {
         TIME_PAST += System.currentTimeMillis() - TIME_LAST_UPDATE;
         TIME_LAST_UPDATE = System.currentTimeMillis();
      } else {
         TIME_LAST_UPDATE = System.currentTimeMillis();
      }

      if (timePasted()) {
         TIME_PAST = 0L;
         if (CFG.gameAction.getActiveTurnState() == Game_Action.TurnStates.INPUT_ORDERS) {
            CFG.gameAction.tryToTakeNexTurn();
         } else if (CFG.gameAction.getActiveTurnState() == Game_Action.TurnStates.TURN_ACTIONS) {
            CFG.gameAction.tryToTakeNexTurn();
         } else if ((CFG.gameAction.getActiveTurnState() == Game_Action.TurnStates.LOAD_AI_RTO || CFG.gameAction.getActiveTurnState() == Game_Action.TurnStates.LOADING_NEXT_TURN) && CFG.menuManager.getInGame_ProvinceInfo().getMenuElement(0).getClickable()) {
            CFG.gameAction.tryToTakeNexTurn();
            resetTime();
         }
      }

   }

   private static final boolean timePasted() {
      return TIME_PAST > (long)getRequiredTime();
   }

   protected static final int getRequiredTime() {
      if (CFG.gameAction.getActiveTurnState() == Game_Action.TurnStates.INPUT_ORDERS) {
         return TIME_REQUIRED_TO_ACTION[SPEED];
      } else if (CFG.gameAction.getActiveTurnState() == Game_Action.TurnStates.TURN_ACTIONS) {
         return CFG.gameAction.getCurrentMoveunits() != null ? TIME_REQUIRED_TO_ACTION_MOVEUNITS[SPEED] : TIME_REQUIRED_TO_ACTION_REPORT[SPEED];
      } else {
         return CFG.gameAction.getActiveTurnState() != Game_Action.TurnStates.LOAD_AI_RTO && CFG.gameAction.getActiveTurnState() != Game_Action.TurnStates.LOADING_NEXT_TURN ? TIME_REQUIRED_TO_ACTION[SPEED] : TIME_REQUIRED_TO_ACTION_LOADAI[SPEED];
      }
   }

   protected static final boolean showReport() {
      return SHOW_REPORT[SPEED];
   }

   private static final boolean addTime() {
      if (CFG.gameAction.getActiveTurnState() == Game_Action.TurnStates.INPUT_ORDERS) {
         if (CFG.menuManager.getInGameView_Options() || CFG.menuManager.getInGameView_EndOfGame()) {
            resetTime();
            return false;
         }

         if (!CFG.SPECTATOR_MODE && (CFG.viewsManager.getActiveViewID() >= 0 && !CFG.viewsManager.getActiveView().canMoveArmy && CFG.viewsManager.getActiveViewID() != ViewsManager.VIEW_DISEASES_MODE || CFG.chooseProvinceMode || CFG.menuManager.getInGame_ProvinceMoveUnits_Visible() || CFG.menuManager.getInGame_ProvinceRecruit_Visible() || CFG.menuManager.getInGame_ProvinceRegroupArmy_Visible() || CFG.menuManager.getInGame_ProvinceDisband_Visible() || CFG.menuManager.getInGame_ProvinceBuild_Visible() || CFG.menuManager.getInGame_ProvinceChooseProvince_Visible() || CFG.menuManager.getVisible_InGame_FlagAction() || CFG.menuManager.getVisible_InGame_Budget() || CFG.menuManager.getVisibleInGame_Event())) {
            return false;
         }
      }

      return true;
   }

   protected static final void resetTime() {
      TIME_PAST = 0L;
      TIME_LAST_UPDATE = System.currentTimeMillis();
   }

   protected static final float getTimePerc() {
      return Math.min((float)TIME_PAST / (float)getRequiredTime(), 1.0F);
   }

   protected static final void reset() {
      PAUSE = true;
      PAUSED_BY_NEXT_TURN = false;
      resetTime();
   }

   protected static boolean isEnabled() {
      return CFG.game.getPlayersSize() == 1;
   }

   protected static void pauseUnpause() {
      PAUSE = !PAUSE;
      PAUSED_BY_NEXT_TURN = false;
      if (!PAUSE) {
         TIME_LAST_UPDATE = System.currentTimeMillis();
      }

      CFG.toast.setInView(PAUSE ? CFG.langManager.get("Paused") : CFG.langManager.get("Unpaused"));
   }

   protected static void updateSpeed(int nDiff) {
      float tempTimePastPerc = getTimePerc();
      SPEED += nDiff;
      if (SPEED < 1) {
         SPEED = 1;
      } else if (SPEED > 6) {
         SPEED = 6;
      }

      TIME_PAST = (long)((float)TIME_REQUIRED_TO_ACTION[SPEED] * tempTimePastPerc);
      CFG.toast.setInView(CFG.langManager.get("Speed") + ": " + SPEED);
   }

   //update new toggle battles var
   protected static void updateShowBattles() {
      SHOW_BATTLE_RESULTS =! SHOW_BATTLE_RESULTS;
      CFG.toast.setInView(CFG.langManager.get("ShowBattles") + ": " + (SHOW_BATTLE_RESULTS ? "On" : "Off"));
   }
}
