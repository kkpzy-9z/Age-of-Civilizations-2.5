package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

class Menu_InGame_Build extends SliderMenu {
   protected static final int ANIMATION_TIME = 175;
   protected static long lTime = 0L;
   protected static boolean hideAnimation = true;

   protected Menu_InGame_Build() {
      int tempW = CFG.CIV_INFO_MENU_WIDTH;
      int tPosY = 0;
      List menuElements = new ArrayList();
      int tRow;
      if (BuildingsManager.iBuildInProvinceID >= 0) {
         boolean canDestroy = false;
         canDestroy = CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfFort() > 0;
         tRow = 0;

         for(int i = 0; i < CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getWonderSize(); ++i) {
            menuElements.add(new Button_NS_Wonder(new Color(0.09411765F, 0.3137255F, 0.43137255F, 1.0F), BuildingsManager.iBuildInProvinceID, i, 0, tPosY, tempW) {
            });
            tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
         }

         menuElements.add(new Button_Build_Level(BuildingsManager.getFort_Name(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfFort() + 1), Images.b_fort, "" + CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfFort(), BuildingsManager.getFort_BuildCost(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfFort() + 1, BuildingsManager.iBuildInProvinceID), BuildingsManager.getFort_BuildMovementCost(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfFort() + 1), 0, tPosY, canDestroy ? tempW - Button_Build_Destroy.getButtonWidth() : tempW, true, CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfFort() == BuildingsManager.getFort_MaxLevel(), CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isInConstruction(BuildingsManager.iBuildInProvinceID, ConstructionType.FORT), BuildingsManager.getFort_TechLevel(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfFort() + 1)) {
            protected void actionElement(int iID) {
               if (CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfFort() == BuildingsManager.getFort_MaxLevel()) {
                  CFG.toast.setInView(CFG.langManager.get("Built"), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
               } else if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isInConstruction(BuildingsManager.iBuildInProvinceID, ConstructionType.FORT) > 0) {
                  CFG.toast.setInView(CFG.langManager.get("InConstruction"), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
               } else {
                  CFG.menuManager.rebuildInGame_BuildFort(BuildingsManager.iBuildInProvinceID);
               }

            }

            protected void buildElementHover() {
               List nElements = new ArrayList();
               List nData = new ArrayList();
               if (CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfFort() == BuildingsManager.getFort_MaxLevel()) {
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Fortress") + ": ", CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName().length() > 0 ? CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName() : CFG.langManager.get("Province")));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getCivID(), CFG.PADDING, 0));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.icon_check_true, CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(" - "));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("HidesTheArmyFromTheSightOfViewOfWatchTower"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(" - "));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("DefenseBonus") + ": "));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text("+" + BuildingsManager.getFort_DefenseBonus(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfFort()) + "%", CFG.COLOR_TEXT_MODIFIER_POSITIVE));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
               } else if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isInConstruction(BuildingsManager.iBuildInProvinceID, ConstructionType.FORT) > 0) {
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("InConstruction"), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(Game_Calendar.getDate_ByTurnID(Game_Calendar.TURN_ID + CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isInConstruction(BuildingsManager.iBuildInProvinceID, ConstructionType.FORT))));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(" [" + CFG.langManager.get("TurnsX", CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isInConstruction(BuildingsManager.iBuildInProvinceID, ConstructionType.FORT)) + "]", CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.time, CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Space());
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfFort() == 0 ? "Castle" : "Fortress") + ": ", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName().length() > 0 ? CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName() : CFG.langManager.get("Province")));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getCivID(), CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
               } else {
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfFort() == 0 ? "BuildCastleIn" : "BuildFortressIn") + ": ", CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName().length() > 0 ? CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName() : CFG.langManager.get("Province")));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getCivID(), CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(" - "));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("HidesTheArmyFromTheSightOfViewOfWatchTower"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(" - "));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("DefenseBonus") + ": "));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text("+" + BuildingsManager.getFort_DefenseBonus(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfFort() + 1) + "%", CFG.COLOR_TEXT_MODIFIER_POSITIVE));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Space());
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Cost") + ": "));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + BuildingsManager.getFort_BuildCost(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfFort() + 1, BuildingsManager.iBuildInProvinceID), CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoney() >= (long)BuildingsManager.getFort_BuildCost(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfFort() + 1, BuildingsManager.iBuildInProvinceID) ? CFG.COLOR_INGAME_GOLD : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_gold, CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("MovementPoints") + ": "));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (float)BuildingsManager.getFort_BuildMovementCost(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfFort() + 1) / 10.0F, CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMovePoints() >= BuildingsManager.getFort_BuildMovementCost(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfFort() + 1) ? CFG.COLOR_INGAME_MOVEMENT : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_movement_points, CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("ConstructionWillTakeXurns", BuildingsManager.getFort_Construction(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfFort() + 1))));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.time, CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("RequiredTechnologyLevel") + ": "));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (float)((int)(BuildingsManager.getFort_TechLevel(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfFort() + 1) * 100.0F)) / 100.0F, CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getTechnologyLevel() >= BuildingsManager.getFort_TechLevel(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfFort() + 1) ? CFG.COLOR_TEXT_TECHNOLOGY : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.technology, CFG.PADDING, 0));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getTechnologyLevel() >= BuildingsManager.getFort_TechLevel(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfFort() + 1) ? Images.icon_check_true : Images.icon_check_false, CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
               }

               this.menuElementHover = new MenuElement_Hover_v2(nElements);
            }
         });
         ((MenuElement)menuElements.get(menuElements.size() - 1)).setHeight(CFG.BUTTON_HEIGHT * 3 / 5);
         ((MenuElement)menuElements.get(menuElements.size() - 1)).setCurrent(tRow);
         if (canDestroy) {
            menuElements.add(new Button_Build_Destroy(tempW - Button_Build_Destroy.getButtonWidth(), tPosY, Button_Build_Destroy.getButtonWidth(), CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfFort() > 0) {
               protected void actionElement(int iID) {
                  CFG.menuManager.rebuildInGame_DestroyFort(BuildingsManager.iBuildInProvinceID);
               }
            });
            ((MenuElement)menuElements.get(menuElements.size() - 1)).setHeight(CFG.BUTTON_HEIGHT * 3 / 5);
            ((MenuElement)menuElements.get(menuElements.size() - 1)).setCurrent(tRow);
         }

         tRow = (tRow + 1) % 2;
         tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
         canDestroy = CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfWatchTower() > 0;
         menuElements.add(new Button_Build(BuildingsManager.getTower_Name(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfWatchTower() + 1), Images.b_tower, BuildingsManager.getTower_BuildCost(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfWatchTower() + 1, BuildingsManager.iBuildInProvinceID), BuildingsManager.getTower_BuildMovementCost(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfWatchTower() + 1), 0, tPosY, canDestroy ? tempW - Button_Build_Destroy.getButtonWidth() : tempW, true, CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfWatchTower() == BuildingsManager.getTower_MaxLevel(), CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isInConstruction(BuildingsManager.iBuildInProvinceID, ConstructionType.TOWER), BuildingsManager.getTower_TechLevel(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfWatchTower() + 1)) {
            protected void actionElement(int iID) {
               if (CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfWatchTower() == BuildingsManager.getTower_MaxLevel()) {
                  CFG.toast.setInView(CFG.langManager.get("Built"), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
               } else if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isInConstruction(BuildingsManager.iBuildInProvinceID, ConstructionType.TOWER) > 0) {
                  CFG.toast.setInView(CFG.langManager.get("InConstruction"), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
               } else {
                  CFG.menuManager.rebuildInGame_BuildTower(BuildingsManager.iBuildInProvinceID);
               }

            }

            protected void buildElementHover() {
               List nElements = new ArrayList();
               List nData = new ArrayList();
               if (CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfWatchTower() == BuildingsManager.getTower_MaxLevel()) {
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("WatchTower") + ": ", CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName().length() > 0 ? CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName() : CFG.langManager.get("Province")));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getCivID(), CFG.PADDING, 0));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.icon_check_true, CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(" - "));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("AllowsToSeeTheArmyInNeighboringProvinces"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(" - "));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("DefenseBonus") + ": "));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text("+" + BuildingsManager.getTower_DefenseBonus(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfWatchTower()) + "%", CFG.COLOR_TEXT_MODIFIER_POSITIVE));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
               } else if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isInConstruction(BuildingsManager.iBuildInProvinceID, ConstructionType.TOWER) > 0) {
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("InConstruction"), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(Game_Calendar.getDate_ByTurnID(Game_Calendar.TURN_ID + CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isInConstruction(BuildingsManager.iBuildInProvinceID, ConstructionType.TOWER))));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(" [" + CFG.langManager.get("TurnsX", CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isInConstruction(BuildingsManager.iBuildInProvinceID, ConstructionType.TOWER)) + "]", CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.time, CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Space());
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("WatchTower") + ": ", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName().length() > 0 ? CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName() : CFG.langManager.get("Province")));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getCivID(), CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
               } else {
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("BuildWatchTowerIn") + ": ", CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName().length() > 0 ? CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName() : CFG.langManager.get("Province")));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getCivID(), CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(" - "));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("AllowsToSeeTheArmyInNeighboringProvinces"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(" - "));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("DefenseBonus") + ": "));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text("+" + BuildingsManager.getTower_DefenseBonus(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfWatchTower() + 1) + "%", CFG.COLOR_TEXT_MODIFIER_POSITIVE));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Space());
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Cost") + ": "));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + BuildingsManager.getTower_BuildCost(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfWatchTower() + 1, BuildingsManager.iBuildInProvinceID), CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoney() >= (long)BuildingsManager.getTower_BuildCost(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfWatchTower() + 1, BuildingsManager.iBuildInProvinceID) ? CFG.COLOR_INGAME_GOLD : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_gold, CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("MovementPoints") + ": "));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (float)BuildingsManager.getTower_BuildMovementCost(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfWatchTower() + 1) / 10.0F, CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMovePoints() >= BuildingsManager.getTower_BuildMovementCost(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfWatchTower() + 1) ? CFG.COLOR_INGAME_MOVEMENT : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_movement_points, CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("ConstructionWillTakeXurns", BuildingsManager.getTower_Construction(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfWatchTower() + 1))));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.time, CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("RequiredTechnologyLevel") + ": "));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (float)((int)(BuildingsManager.getTower_TechLevel(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfWatchTower() + 1) * 100.0F)) / 100.0F, CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getTechnologyLevel() >= BuildingsManager.getTower_TechLevel(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfWatchTower() + 1) ? CFG.COLOR_TEXT_TECHNOLOGY : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.technology, CFG.PADDING, 0));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getTechnologyLevel() >= BuildingsManager.getTower_TechLevel(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfWatchTower() + 1) ? Images.icon_check_true : Images.icon_check_false, CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
               }

               this.menuElementHover = new MenuElement_Hover_v2(nElements);
            }
         });
         ((MenuElement)menuElements.get(menuElements.size() - 1)).setHeight(CFG.BUTTON_HEIGHT * 3 / 5);
         ((MenuElement)menuElements.get(menuElements.size() - 1)).setCurrent(tRow);
         if (canDestroy) {
            menuElements.add(new Button_Build_Destroy(tempW - Button_Build_Destroy.getButtonWidth(), tPosY, Button_Build_Destroy.getButtonWidth(), CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfWatchTower() > 0) {
               protected void actionElement(int iID) {
                  CFG.menuManager.rebuildInGame_DestroyTower(BuildingsManager.iBuildInProvinceID);
               }
            });
            ((MenuElement)menuElements.get(menuElements.size() - 1)).setHeight(CFG.BUTTON_HEIGHT * 3 / 5);
            ((MenuElement)menuElements.get(menuElements.size() - 1)).setCurrent(tRow);
         }

         tRow = (tRow + 1) % 2;
         tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
         if (CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfPort() >= 0) {
            canDestroy = CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfPort() > 0;
            menuElements.add(new Button_Build(BuildingsManager.getPort_Name(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfPort() + 1), Images.b_port, BuildingsManager.getPort_BuildCost(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfPort() + 1, BuildingsManager.iBuildInProvinceID), BuildingsManager.getPort_BuildMovementCost(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfPort() + 1), 0, tPosY, canDestroy ? tempW - Button_Build_Destroy.getButtonWidth() : tempW, true, CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfPort() == BuildingsManager.getPort_MaxLevel(), CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isInConstruction(BuildingsManager.iBuildInProvinceID, ConstructionType.PORT), BuildingsManager.getPort_TechLevel(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfPort() + 1)) {
               protected void actionElement(int iID) {
                  if (CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfPort() == BuildingsManager.getPort_MaxLevel()) {
                     CFG.toast.setInView(CFG.langManager.get("Built"), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
                  } else if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isInConstruction(BuildingsManager.iBuildInProvinceID, ConstructionType.PORT) > 0) {
                     CFG.toast.setInView(CFG.langManager.get("InConstruction"), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
                  } else {
                     CFG.menuManager.rebuildInGame_BuildPort(BuildingsManager.iBuildInProvinceID);
                  }

               }

               protected void buildElementHover() {
                  List nElements = new ArrayList();
                  List nData = new ArrayList();
                  if (CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfPort() == BuildingsManager.getPort_MaxLevel()) {
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Port") + ": ", CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName().length() > 0 ? CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName() : CFG.langManager.get("Province")));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getCivID(), CFG.PADDING, 0));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.icon_check_true, CFG.PADDING, 0));
                     nElements.add(new MenuElement_Hover_v2_Element2(nData));
                     nData.clear();
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(" - "));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("AllowsYourArmyGoToTheSea"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.icon_move_sea, CFG.PADDING, 0));
                     nElements.add(new MenuElement_Hover_v2_Element2(nData));
                     nData.clear();
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(" - "));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("IncomeProduction") + ": "));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text("+" + (int)(BuildingsManager.getPort_IncomeProduction(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfPort()) * 100.0F) + "%", CFG.COLOR_TEXT_MODIFIER_POSITIVE));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.economy, CFG.PADDING, 0));
                     nElements.add(new MenuElement_Hover_v2_Element2(nData));
                     nData.clear();
                  } else if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isInConstruction(BuildingsManager.iBuildInProvinceID, ConstructionType.PORT) > 0) {
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("InConstruction"), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                     nElements.add(new MenuElement_Hover_v2_Element2(nData));
                     nData.clear();
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(Game_Calendar.getDate_ByTurnID(Game_Calendar.TURN_ID + CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isInConstruction(BuildingsManager.iBuildInProvinceID, ConstructionType.PORT))));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(" [" + CFG.langManager.get("TurnsX", CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isInConstruction(BuildingsManager.iBuildInProvinceID, ConstructionType.PORT)) + "]", CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.time, CFG.PADDING, 0));
                     nElements.add(new MenuElement_Hover_v2_Element2(nData));
                     nData.clear();
                     nData.add(new MenuElement_Hover_v2_Element_Type_Space());
                     nElements.add(new MenuElement_Hover_v2_Element2(nData));
                     nData.clear();
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Port") + ": ", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName().length() > 0 ? CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName() : CFG.langManager.get("Province")));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getCivID(), CFG.PADDING, 0));
                     nElements.add(new MenuElement_Hover_v2_Element2(nData));
                     nData.clear();
                  } else {
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("BuildPortIn") + ": ", CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName().length() > 0 ? CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName() : CFG.langManager.get("Province")));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getCivID(), CFG.PADDING, 0));
                     nElements.add(new MenuElement_Hover_v2_Element2(nData));
                     nData.clear();
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(" - "));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("AllowsYourArmyGoToTheSea"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.icon_move_sea, CFG.PADDING, 0));
                     nElements.add(new MenuElement_Hover_v2_Element2(nData));
                     nData.clear();
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(" - "));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("IncomeProduction") + ": "));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text("+" + (int)(BuildingsManager.getPort_IncomeProduction(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfPort() + 1) * 100.0F) + "%", CFG.COLOR_TEXT_MODIFIER_POSITIVE));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.economy, CFG.PADDING, 0));
                     nElements.add(new MenuElement_Hover_v2_Element2(nData));
                     nData.clear();
                     nData.add(new MenuElement_Hover_v2_Element_Type_Space());
                     nElements.add(new MenuElement_Hover_v2_Element2(nData));
                     nData.clear();
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Cost") + ": "));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + BuildingsManager.getPort_BuildCost(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfPort() + 1, BuildingsManager.iBuildInProvinceID), CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoney() >= (long)BuildingsManager.getPort_BuildCost(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfPort() + 1, BuildingsManager.iBuildInProvinceID) ? CFG.COLOR_INGAME_GOLD : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_gold, CFG.PADDING, 0));
                     nElements.add(new MenuElement_Hover_v2_Element2(nData));
                     nData.clear();
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("MovementPoints") + ": "));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (float)BuildingsManager.getPort_BuildMovementCost(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfPort() + 1) / 10.0F, CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMovePoints() >= BuildingsManager.getPort_BuildMovementCost(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfPort() + 1) ? CFG.COLOR_INGAME_MOVEMENT : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_movement_points, CFG.PADDING, 0));
                     nElements.add(new MenuElement_Hover_v2_Element2(nData));
                     nData.clear();
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("ConstructionWillTakeXurns", BuildingsManager.getPort_Construction(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfPort() + 1))));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.time, CFG.PADDING, 0));
                     nElements.add(new MenuElement_Hover_v2_Element2(nData));
                     nData.clear();
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("RequiredTechnologyLevel") + ": "));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (float)((int)(BuildingsManager.getPort_TechLevel(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfPort() + 1) * 100.0F)) / 100.0F, CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getTechnologyLevel() >= BuildingsManager.getPort_TechLevel(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfPort() + 1) ? CFG.COLOR_TEXT_TECHNOLOGY : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.technology, CFG.PADDING, 0));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Image(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getTechnologyLevel() >= BuildingsManager.getPort_TechLevel(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfPort() + 1) ? Images.icon_check_true : Images.icon_check_false, CFG.PADDING, 0));
                     nElements.add(new MenuElement_Hover_v2_Element2(nData));
                     nData.clear();
                  }

                  this.menuElementHover = new MenuElement_Hover_v2(nElements);
               }
            });
            ((MenuElement)menuElements.get(menuElements.size() - 1)).setHeight(CFG.BUTTON_HEIGHT * 3 / 5);
            ((MenuElement)menuElements.get(menuElements.size() - 1)).setCurrent(tRow);
            if (canDestroy) {
               menuElements.add(new Button_Build_Destroy(tempW - Button_Build_Destroy.getButtonWidth(), tPosY, Button_Build_Destroy.getButtonWidth(), CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfPort() > 0) {
                  protected void actionElement(int iID) {
                     CFG.menuManager.rebuildInGame_DestroyPort(BuildingsManager.iBuildInProvinceID);
                  }
               });
               ((MenuElement)menuElements.get(menuElements.size() - 1)).setHeight(CFG.BUTTON_HEIGHT * 3 / 5);
               ((MenuElement)menuElements.get(menuElements.size() - 1)).setCurrent(tRow);
            }

            tRow = (tRow + 1) % 2;
            tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
         }

         canDestroy = CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfFarm() > 0;
         if (CFG.terrainTypesManager.getPopulationGrowth(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getTerrainTypeID()) >= 0.0F) {
            menuElements.add(new Button_Build_Level(BuildingsManager.getFarm_Name(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfFarm() + 1), Images.b_farm, "" + CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfFarm(), BuildingsManager.getFarm_BuildCost(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfFarm() + 1, BuildingsManager.iBuildInProvinceID), BuildingsManager.getFarm_BuildMovementCost(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfFarm() + 1), 0, tPosY, canDestroy ? tempW - Button_Build_Destroy.getButtonWidth() : tempW, true, CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfFarm() == BuildingsManager.getFarm_MaxLevel(), CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isInConstruction(BuildingsManager.iBuildInProvinceID, ConstructionType.FARM), BuildingsManager.getFarm_TechLevel(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfFarm() + 1)) {
               protected void actionElement(int iID) {
                  if (CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfFarm() == BuildingsManager.getFarm_MaxLevel()) {
                     CFG.toast.setInView(CFG.langManager.get("Built"), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
                  } else if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isInConstruction(BuildingsManager.iBuildInProvinceID, ConstructionType.FARM) > 0) {
                     CFG.toast.setInView(CFG.langManager.get("InConstruction"), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
                  } else {
                     CFG.menuManager.rebuildInGame_BuildFarm(BuildingsManager.iBuildInProvinceID);
                  }

               }

               protected void buildElementHover() {
                  List nElements = new ArrayList();
                  List nData = new ArrayList();
                  if (CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfFarm() == BuildingsManager.getFarm_MaxLevel()) {
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Farm") + ": ", CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName().length() > 0 ? CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName() : CFG.langManager.get("Province")));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getCivID(), CFG.PADDING, 0));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.icon_check_true, CFG.PADDING, 0));
                     nElements.add(new MenuElement_Hover_v2_Element2(nData));
                     nData.clear();
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(" - "));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("GrowthRate") + ": "));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text("+" + (int)(BuildingsManager.getFarm_GrowthRateBonus(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfFarm()) * 100.0F) + "%", CFG.COLOR_TEXT_MODIFIER_POSITIVE));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.population_growth, CFG.PADDING, 0));
                     nElements.add(new MenuElement_Hover_v2_Element2(nData));
                     nData.clear();
                  } else if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isInConstruction(BuildingsManager.iBuildInProvinceID, ConstructionType.FARM) > 0) {
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("InConstruction"), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                     nElements.add(new MenuElement_Hover_v2_Element2(nData));
                     nData.clear();
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(Game_Calendar.getDate_ByTurnID(Game_Calendar.TURN_ID + CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isInConstruction(BuildingsManager.iBuildInProvinceID, ConstructionType.FARM))));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(" [" + CFG.langManager.get("TurnsX", CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isInConstruction(BuildingsManager.iBuildInProvinceID, ConstructionType.FARM)) + "]", CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.time, CFG.PADDING, 0));
                     nElements.add(new MenuElement_Hover_v2_Element2(nData));
                     nData.clear();
                     nData.add(new MenuElement_Hover_v2_Element_Type_Space());
                     nElements.add(new MenuElement_Hover_v2_Element2(nData));
                     nData.clear();
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Farm") + ": ", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName().length() > 0 ? CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName() : CFG.langManager.get("Province")));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getCivID(), CFG.PADDING, 0));
                     nElements.add(new MenuElement_Hover_v2_Element2(nData));
                     nData.clear();
                  } else {
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("BuildFarmIn") + ": ", CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName().length() > 0 ? CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName() : CFG.langManager.get("Province")));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getCivID(), CFG.PADDING, 0));
                     nElements.add(new MenuElement_Hover_v2_Element2(nData));
                     nData.clear();
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(" - "));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("GrowthRate") + ": "));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text("+" + (int)(BuildingsManager.getFarm_GrowthRateBonus(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfFarm() + 1) * 100.0F) + "%", CFG.COLOR_TEXT_MODIFIER_POSITIVE));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.population_growth, CFG.PADDING, 0));
                     nElements.add(new MenuElement_Hover_v2_Element2(nData));
                     nData.clear();
                     nData.add(new MenuElement_Hover_v2_Element_Type_Space());
                     nElements.add(new MenuElement_Hover_v2_Element2(nData));
                     nData.clear();
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Cost") + ": "));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + BuildingsManager.getFarm_BuildCost(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfFarm() + 1, BuildingsManager.iBuildInProvinceID), CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoney() >= (long)BuildingsManager.getFarm_BuildCost(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfFarm() + 1, BuildingsManager.iBuildInProvinceID) ? CFG.COLOR_INGAME_GOLD : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_gold, CFG.PADDING, 0));
                     nElements.add(new MenuElement_Hover_v2_Element2(nData));
                     nData.clear();
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("MovementPoints") + ": "));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (float)BuildingsManager.getFarm_BuildMovementCost(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfFarm() + 1) / 10.0F, CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMovePoints() >= BuildingsManager.getFarm_BuildMovementCost(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfFarm() + 1) ? CFG.COLOR_INGAME_MOVEMENT : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_movement_points, CFG.PADDING, 0));
                     nElements.add(new MenuElement_Hover_v2_Element2(nData));
                     nData.clear();
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("ConstructionWillTakeXurns", BuildingsManager.getFarm_Construction(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfFarm() + 1))));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.time, CFG.PADDING, 0));
                     nElements.add(new MenuElement_Hover_v2_Element2(nData));
                     nData.clear();
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("RequiredTechnologyLevel") + ": "));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (float)((int)(BuildingsManager.getFarm_TechLevel(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfFarm() + 1) * 100.0F)) / 100.0F, CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getTechnologyLevel() >= BuildingsManager.getFarm_TechLevel(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfFarm() + 1) ? CFG.COLOR_TEXT_TECHNOLOGY : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.technology, CFG.PADDING, 0));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Image(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getTechnologyLevel() >= BuildingsManager.getFarm_TechLevel(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfFarm() + 1) ? Images.icon_check_true : Images.icon_check_false, CFG.PADDING, 0));
                     nElements.add(new MenuElement_Hover_v2_Element2(nData));
                     nData.clear();
                  }

                  this.menuElementHover = new MenuElement_Hover_v2(nElements);
               }
            });
            ((MenuElement)menuElements.get(menuElements.size() - 1)).setHeight(CFG.BUTTON_HEIGHT * 3 / 5);
            ((MenuElement)menuElements.get(menuElements.size() - 1)).setCurrent(tRow);
            if (canDestroy) {
               menuElements.add(new Button_Build_Destroy(tempW - Button_Build_Destroy.getButtonWidth(), tPosY, Button_Build_Destroy.getButtonWidth(), CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfFarm() > 0) {
                  protected void actionElement(int iID) {
                     CFG.menuManager.rebuildInGame_DestroyFarm(BuildingsManager.iBuildInProvinceID);
                  }
               });
               ((MenuElement)menuElements.get(menuElements.size() - 1)).setHeight(CFG.BUTTON_HEIGHT * 3 / 5);
               ((MenuElement)menuElements.get(menuElements.size() - 1)).setCurrent(tRow);
            }

            tRow = (tRow + 1) % 2;
            tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
         }

         canDestroy = CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfWorkshop() > 0;
         menuElements.add(new Button_Build_Level(BuildingsManager.getWorkshop_Name(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfWorkshop() + 1), Images.b_workshop, "" + CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfWorkshop(), BuildingsManager.getWorkshop_BuildCost(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfWorkshop() + 1, BuildingsManager.iBuildInProvinceID), BuildingsManager.getWorkshop_BuildMovementCost(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfWorkshop() + 1), 0, tPosY, canDestroy ? tempW - Button_Build_Destroy.getButtonWidth() : tempW, true, CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfWorkshop() == BuildingsManager.getWorkshop_MaxLevel(), CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isInConstruction(BuildingsManager.iBuildInProvinceID, ConstructionType.WORKSHOP), BuildingsManager.getWorkshop_TechLevel(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfWorkshop() + 1)) {
            protected void actionElement(int iID) {
               if (CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfWorkshop() == BuildingsManager.getWorkshop_MaxLevel()) {
                  CFG.toast.setInView(CFG.langManager.get("Built"), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
               } else if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isInConstruction(BuildingsManager.iBuildInProvinceID, ConstructionType.WORKSHOP) > 0) {
                  CFG.toast.setInView(CFG.langManager.get("InConstruction"), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
               } else {
                  CFG.menuManager.rebuildInGame_BuildWorkshop(BuildingsManager.iBuildInProvinceID);
               }

            }

            protected void buildElementHover() {
               List nElements = new ArrayList();
               List nData = new ArrayList();
               if (CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfWorkshop() == BuildingsManager.getWorkshop_MaxLevel()) {
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get(BuildingsManager.getWorkshop_Name(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfWorkshop())) + ": ", CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName().length() > 0 ? CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName() : CFG.langManager.get("Province")));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getCivID(), CFG.PADDING, 0));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.icon_check_true, CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(" - "));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("IncomeProduction") + ": "));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text("+" + (int)(BuildingsManager.getWorkshop_IncomeProduction(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfWorkshop()) * 100.0F) + "%", CFG.COLOR_TEXT_MODIFIER_POSITIVE));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
               } else if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isInConstruction(BuildingsManager.iBuildInProvinceID, ConstructionType.WORKSHOP) > 0) {
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("InConstruction"), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(Game_Calendar.getDate_ByTurnID(Game_Calendar.TURN_ID + CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isInConstruction(BuildingsManager.iBuildInProvinceID, ConstructionType.WORKSHOP))));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(" [" + CFG.langManager.get("TurnsX", CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isInConstruction(BuildingsManager.iBuildInProvinceID, ConstructionType.WORKSHOP)) + "]", CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.time, CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Space());
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get(BuildingsManager.getWorkshop_Name(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfWorkshop() + 1)) + ": ", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName().length() > 0 ? CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName() : CFG.langManager.get("Province")));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getCivID(), CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
               } else {
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("BuildWorkshopIn") + ": ", CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName().length() > 0 ? CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName() : CFG.langManager.get("Province")));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getCivID(), CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(" - "));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("IncomeProduction") + ": "));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text("+" + (int)(BuildingsManager.getWorkshop_IncomeProduction(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfWorkshop() + 1) * 100.0F) + "%", CFG.COLOR_TEXT_MODIFIER_POSITIVE));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Space());
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Cost") + ": "));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + BuildingsManager.getWorkshop_BuildCost(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfWorkshop() + 1, BuildingsManager.iBuildInProvinceID), CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoney() >= (long)BuildingsManager.getWorkshop_BuildCost(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfWorkshop() + 1, BuildingsManager.iBuildInProvinceID) ? CFG.COLOR_INGAME_GOLD : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_gold, CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("MovementPoints") + ": "));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (float)BuildingsManager.getWorkshop_BuildMovementCost(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfWorkshop() + 1) / 10.0F, CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMovePoints() >= BuildingsManager.getWorkshop_BuildMovementCost(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfWorkshop() + 1) ? CFG.COLOR_INGAME_MOVEMENT : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_movement_points, CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("ConstructionWillTakeXurns", BuildingsManager.getWorkshop_Construction(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfWorkshop() + 1))));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.time, CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("RequiredTechnologyLevel") + ": "));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (float)((int)(BuildingsManager.getWorkshop_TechLevel(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfWorkshop() + 1) * 100.0F)) / 100.0F, CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getTechnologyLevel() >= BuildingsManager.getWorkshop_TechLevel(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfWorkshop() + 1) ? CFG.COLOR_TEXT_TECHNOLOGY : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.technology, CFG.PADDING, 0));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getTechnologyLevel() >= BuildingsManager.getWorkshop_TechLevel(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfWorkshop() + 1) ? Images.icon_check_true : Images.icon_check_false, CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
               }

               this.menuElementHover = new MenuElement_Hover_v2(nElements);
            }
         });
         ((MenuElement)menuElements.get(menuElements.size() - 1)).setHeight(CFG.BUTTON_HEIGHT * 3 / 5);
         ((MenuElement)menuElements.get(menuElements.size() - 1)).setCurrent(tRow);
         if (canDestroy) {
            menuElements.add(new Button_Build_Destroy(tempW - Button_Build_Destroy.getButtonWidth(), tPosY, Button_Build_Destroy.getButtonWidth(), CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfWorkshop() > 0) {
               protected void actionElement(int iID) {
                  CFG.menuManager.rebuildInGame_DestroyWorkshop(BuildingsManager.iBuildInProvinceID);
               }
            });
            ((MenuElement)menuElements.get(menuElements.size() - 1)).setHeight(CFG.BUTTON_HEIGHT * 3 / 5);
            ((MenuElement)menuElements.get(menuElements.size() - 1)).setCurrent(tRow);
         }

         tRow = (tRow + 1) % 2;
         tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
         canDestroy = CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfLibrary() > 0;
         menuElements.add(new Button_Build_Level(BuildingsManager.getLibrary_Name(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfLibrary() + 1), Images.b_library, "" + CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfLibrary(), BuildingsManager.getLibrary_BuildCost(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfLibrary() + 1, BuildingsManager.iBuildInProvinceID), BuildingsManager.getLibrary_BuildMovementCost(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfLibrary() + 1), 0, tPosY, canDestroy ? tempW - Button_Build_Destroy.getButtonWidth() : tempW, true, CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfLibrary() == BuildingsManager.getLibrary_MaxLevel(), CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isInConstruction(BuildingsManager.iBuildInProvinceID, ConstructionType.LIBRARY), BuildingsManager.getLibrary_TechLevel(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfLibrary() + 1)) {
            protected void actionElement(int iID) {
               if (CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfLibrary() == BuildingsManager.getLibrary_MaxLevel()) {
                  CFG.toast.setInView(CFG.langManager.get("Built"), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
               } else if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isInConstruction(BuildingsManager.iBuildInProvinceID, ConstructionType.LIBRARY) > 0) {
                  CFG.toast.setInView(CFG.langManager.get("InConstruction"), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
               } else {
                  CFG.menuManager.rebuildInGame_BuildLibrary(BuildingsManager.iBuildInProvinceID);
               }

            }

            protected void buildElementHover() {
               List nElements = new ArrayList();
               List nData = new ArrayList();
               if (CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfLibrary() == BuildingsManager.getLibrary_MaxLevel()) {
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get(BuildingsManager.getLibrary_Name(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfLibrary())) + ": ", CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName().length() > 0 ? CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName() : CFG.langManager.get("Province")));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getCivID(), CFG.PADDING, 0));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.icon_check_true, CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(" - "));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("+1"), CFG.COLOR_TEXT_RESEARCH));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.research, CFG.PADDING, CFG.PADDING));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("ResearchPerTurnForEveryXPeopleInProvince", BuildingsManager.getLibrary_ResearchPerPopulation(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfLibrary())), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
               } else if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isInConstruction(BuildingsManager.iBuildInProvinceID, ConstructionType.LIBRARY) > 0) {
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("InConstruction"), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(Game_Calendar.getDate_ByTurnID(Game_Calendar.TURN_ID + CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isInConstruction(BuildingsManager.iBuildInProvinceID, ConstructionType.LIBRARY))));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(" [" + CFG.langManager.get("TurnsX", CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isInConstruction(BuildingsManager.iBuildInProvinceID, ConstructionType.LIBRARY)) + "]", CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.time, CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Space());
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get(BuildingsManager.getLibrary_Name(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfLibrary() + 1)) + ": ", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName().length() > 0 ? CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName() : CFG.langManager.get("Province")));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getCivID(), CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
               } else {
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfLibrary() == 0 ? "BuildLibraryIn" : (CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfLibrary() == 1 ? "BuildUniversityIn" : "BuildResearchLabIn")) + ": ", CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName().length() > 0 ? CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName() : CFG.langManager.get("Province")));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getCivID(), CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(" - "));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("+1"), CFG.COLOR_TEXT_RESEARCH));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.research, CFG.PADDING, CFG.PADDING));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("ResearchPerTurnForEveryXPeopleInProvince", BuildingsManager.getLibrary_ResearchPerPopulation(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfLibrary() + 1)), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Space());
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Cost") + ": "));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + BuildingsManager.getLibrary_BuildCost(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfLibrary() + 1, BuildingsManager.iBuildInProvinceID), CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoney() >= (long)BuildingsManager.getLibrary_BuildCost(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfLibrary() + 1, BuildingsManager.iBuildInProvinceID) ? CFG.COLOR_INGAME_GOLD : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_gold, CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("MovementPoints") + ": "));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (float)BuildingsManager.getLibrary_BuildMovementCost(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfLibrary() + 1) / 10.0F, CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMovePoints() >= BuildingsManager.getLibrary_BuildMovementCost(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfLibrary() + 1) ? CFG.COLOR_INGAME_MOVEMENT : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_movement_points, CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("ConstructionWillTakeXurns", BuildingsManager.getLibrary_Construction(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfLibrary() + 1))));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.time, CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("RequiredTechnologyLevel") + ": "));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (float)((int)(BuildingsManager.getLibrary_TechLevel(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfLibrary() + 1) * 100.0F)) / 100.0F, CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getTechnologyLevel() >= BuildingsManager.getLibrary_TechLevel(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfLibrary() + 1) ? CFG.COLOR_TEXT_TECHNOLOGY : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.technology, CFG.PADDING, 0));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getTechnologyLevel() >= BuildingsManager.getLibrary_TechLevel(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfLibrary() + 1) ? Images.icon_check_true : Images.icon_check_false, CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
               }

               this.menuElementHover = new MenuElement_Hover_v2(nElements);
            }
         });
         ((MenuElement)menuElements.get(menuElements.size() - 1)).setHeight(CFG.BUTTON_HEIGHT * 3 / 5);
         ((MenuElement)menuElements.get(menuElements.size() - 1)).setCurrent(tRow);
         if (canDestroy) {
            menuElements.add(new Button_Build_Destroy(tempW - Button_Build_Destroy.getButtonWidth(), tPosY, Button_Build_Destroy.getButtonWidth(), CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfLibrary() > 0) {
               protected void actionElement(int iID) {
                  CFG.menuManager.rebuildInGame_DestroyLibrary(BuildingsManager.iBuildInProvinceID);
               }
            });
            ((MenuElement)menuElements.get(menuElements.size() - 1)).setHeight(CFG.BUTTON_HEIGHT * 3 / 5);
            ((MenuElement)menuElements.get(menuElements.size() - 1)).setCurrent(tRow);
         }

         tRow = (tRow + 1) % 2;
         tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
         canDestroy = CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfArmoury() > 0;
         menuElements.add(new Button_Build(BuildingsManager.getArmoury_Name(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfArmoury() + 1), Images.b_armoury, BuildingsManager.getArmoury_BuildCost(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfArmoury() + 1, BuildingsManager.iBuildInProvinceID), BuildingsManager.getArmoury_BuildMovementCost(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfArmoury() + 1), 0, tPosY, canDestroy ? tempW - Button_Build_Destroy.getButtonWidth() : tempW, true, CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfArmoury() == BuildingsManager.getArmoury_MaxLevel(), CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isInConstruction(BuildingsManager.iBuildInProvinceID, ConstructionType.ARMOURY), BuildingsManager.getArmoury_TechLevel(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfArmoury() + 1)) {
            protected void actionElement(int iID) {
               if (CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfArmoury() == BuildingsManager.getArmoury_MaxLevel()) {
                  CFG.toast.setInView(CFG.langManager.get("Built"), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
               } else if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isInConstruction(BuildingsManager.iBuildInProvinceID, ConstructionType.ARMOURY) > 0) {
                  CFG.toast.setInView(CFG.langManager.get("InConstruction"), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
               } else {
                  CFG.menuManager.rebuildInGame_BuildArmoury(BuildingsManager.iBuildInProvinceID);
               }

            }

            protected void buildElementHover() {
               List nElements = new ArrayList();
               List nData = new ArrayList();
               if (CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfArmoury() == BuildingsManager.getArmoury_MaxLevel()) {
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get(BuildingsManager.getArmoury_Name(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfArmoury())) + ": ", CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName().length() > 0 ? CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName() : CFG.langManager.get("Province")));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getCivID(), CFG.PADDING, 0));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.icon_check_true, CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(" - "));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("ReducesTheCostOfRecruitmentPerUnitByOneGold"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
               } else if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isInConstruction(BuildingsManager.iBuildInProvinceID, ConstructionType.ARMOURY) > 0) {
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("InConstruction"), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(Game_Calendar.getDate_ByTurnID(Game_Calendar.TURN_ID + CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isInConstruction(BuildingsManager.iBuildInProvinceID, ConstructionType.ARMOURY))));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(" [" + CFG.langManager.get("TurnsX", CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isInConstruction(BuildingsManager.iBuildInProvinceID, ConstructionType.ARMOURY)) + "]", CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.time, CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Space());
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get(BuildingsManager.getArmoury_Name(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfArmoury() + 1)) + ": ", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName().length() > 0 ? CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName() : CFG.langManager.get("Province")));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getCivID(), CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
               } else {
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("BuildArmouryIn") + ": ", CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName().length() > 0 ? CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName() : CFG.langManager.get("Province")));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getCivID(), CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(" - "));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("ReducesTheCostOfRecruitmentPerUnitByOneGold"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Space());
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Cost") + ": "));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + BuildingsManager.getArmoury_BuildCost(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfArmoury() + 1, BuildingsManager.iBuildInProvinceID), CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoney() >= (long)BuildingsManager.getArmoury_BuildCost(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfArmoury() + 1, BuildingsManager.iBuildInProvinceID) ? CFG.COLOR_INGAME_GOLD : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_gold, CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("MovementPoints") + ": "));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (float)BuildingsManager.getArmoury_BuildMovementCost(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfArmoury() + 1) / 10.0F, CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMovePoints() >= BuildingsManager.getArmoury_BuildMovementCost(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfArmoury() + 1) ? CFG.COLOR_INGAME_MOVEMENT : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_movement_points, CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("ConstructionWillTakeXurns", BuildingsManager.getArmoury_Construction(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfArmoury() + 1))));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.time, CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("RequiredTechnologyLevel") + ": "));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (float)((int)(BuildingsManager.getArmoury_TechLevel(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfArmoury() + 1) * 100.0F)) / 100.0F, CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getTechnologyLevel() >= BuildingsManager.getArmoury_TechLevel(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfArmoury() + 1) ? CFG.COLOR_TEXT_TECHNOLOGY : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.technology, CFG.PADDING, 0));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getTechnologyLevel() >= BuildingsManager.getArmoury_TechLevel(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfArmoury() + 1) ? Images.icon_check_true : Images.icon_check_false, CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
               }

               this.menuElementHover = new MenuElement_Hover_v2(nElements);
            }
         });
         ((MenuElement)menuElements.get(menuElements.size() - 1)).setHeight(CFG.BUTTON_HEIGHT * 3 / 5);
         ((MenuElement)menuElements.get(menuElements.size() - 1)).setCurrent(tRow);
         if (canDestroy) {
            menuElements.add(new Button_Build_Destroy(tempW - Button_Build_Destroy.getButtonWidth(), tPosY, Button_Build_Destroy.getButtonWidth(), CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfArmoury() > 0) {
               protected void actionElement(int iID) {
                  CFG.menuManager.rebuildInGame_DestroyArmoury(BuildingsManager.iBuildInProvinceID);
               }
            });
            ((MenuElement)menuElements.get(menuElements.size() - 1)).setHeight(CFG.BUTTON_HEIGHT * 3 / 5);
            ((MenuElement)menuElements.get(menuElements.size() - 1)).setCurrent(tRow);
         }

         tRow = (tRow + 1) % 2;
         tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
         canDestroy = CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfSupply() > 0;
         menuElements.add(new Button_Build(BuildingsManager.getSupply_Name(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfSupply() + 1), Images.b_supply, BuildingsManager.getSupply_BuildCost(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfSupply() + 1, BuildingsManager.iBuildInProvinceID), BuildingsManager.getSupply_BuildMovementCost(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfSupply() + 1), 0, tPosY, canDestroy ? tempW - Button_Build_Destroy.getButtonWidth() : tempW, true, CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfSupply() == BuildingsManager.getSupply_MaxLevel(), CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isInConstruction(BuildingsManager.iBuildInProvinceID, ConstructionType.SUPPLY), BuildingsManager.getSupply_TechLevel(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfSupply() + 1)) {
            protected void actionElement(int iID) {
               if (CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfSupply() == BuildingsManager.getSupply_MaxLevel()) {
                  CFG.toast.setInView(CFG.langManager.get("Built"), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
               } else if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isInConstruction(BuildingsManager.iBuildInProvinceID, ConstructionType.SUPPLY) > 0) {
                  CFG.toast.setInView(CFG.langManager.get("InConstruction"), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
               } else {
                  CFG.menuManager.rebuildInGame_BuildSupply(BuildingsManager.iBuildInProvinceID);
               }

            }

            protected void buildElementHover() {
               List nElements = new ArrayList();
               List nData = new ArrayList();
               if (CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfSupply() == BuildingsManager.getSupply_MaxLevel()) {
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get(BuildingsManager.getSupply_Name(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfSupply())) + ": ", CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName().length() > 0 ? CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName() : CFG.langManager.get("Province")));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getCivID(), CFG.PADDING, 0));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.icon_check_true, CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(" - "));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("MilitaryUpkeep") + ": "));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text("-" + (int)(BuildingsManager.getSupply_Bonus(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfSupply()) * 100.0F) + "%", CFG.COLOR_TEXT_MODIFIER_POSITIVE));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
               } else if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isInConstruction(BuildingsManager.iBuildInProvinceID, ConstructionType.SUPPLY) > 0) {
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("InConstruction"), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(Game_Calendar.getDate_ByTurnID(Game_Calendar.TURN_ID + CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isInConstruction(BuildingsManager.iBuildInProvinceID, ConstructionType.SUPPLY))));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(" [" + CFG.langManager.get("TurnsX", CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isInConstruction(BuildingsManager.iBuildInProvinceID, ConstructionType.SUPPLY)) + "]", CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.time, CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Space());
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get(BuildingsManager.getSupply_Name(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfSupply() + 1)) + ": ", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName().length() > 0 ? CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName() : CFG.langManager.get("Province")));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getCivID(), CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
               } else {
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("BuildSupplyCampIn") + ": ", CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName().length() > 0 ? CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName() : CFG.langManager.get("Province")));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getCivID(), CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(" - "));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("MilitaryUpkeep") + ": "));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text("-" + (int)(BuildingsManager.getSupply_Bonus(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfSupply() + 1) * 100.0F) + "%", CFG.COLOR_TEXT_MODIFIER_POSITIVE));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Space());
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Cost") + ": "));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + BuildingsManager.getSupply_BuildCost(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfSupply() + 1, BuildingsManager.iBuildInProvinceID), CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoney() >= (long)BuildingsManager.getSupply_BuildCost(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfSupply() + 1, BuildingsManager.iBuildInProvinceID) ? CFG.COLOR_INGAME_GOLD : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_gold, CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("MovementPoints") + ": "));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (float)BuildingsManager.getSupply_BuildMovementCost(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfSupply() + 1) / 10.0F, CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMovePoints() >= BuildingsManager.getSupply_BuildMovementCost(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfSupply() + 1) ? CFG.COLOR_INGAME_MOVEMENT : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_movement_points, CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("ConstructionWillTakeXurns", BuildingsManager.getSupply_Construction(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfSupply() + 1))));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.time, CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("RequiredTechnologyLevel") + ": "));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (float)((int)(BuildingsManager.getSupply_TechLevel(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfSupply() + 1) * 100.0F)) / 100.0F, CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getTechnologyLevel() >= BuildingsManager.getSupply_TechLevel(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfSupply() + 1) ? CFG.COLOR_TEXT_TECHNOLOGY : CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.technology, CFG.PADDING, 0));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Image(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getTechnologyLevel() >= BuildingsManager.getSupply_TechLevel(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfSupply() + 1) ? Images.icon_check_true : Images.icon_check_false, CFG.PADDING, 0));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
               }

               this.menuElementHover = new MenuElement_Hover_v2(nElements);
            }
         });
         ((MenuElement)menuElements.get(menuElements.size() - 1)).setHeight(CFG.BUTTON_HEIGHT * 3 / 5);
         ((MenuElement)menuElements.get(menuElements.size() - 1)).setCurrent(tRow);
         if (canDestroy) {
            menuElements.add(new Button_Build_Destroy(tempW - Button_Build_Destroy.getButtonWidth(), tPosY, Button_Build_Destroy.getButtonWidth(), CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getLevelOfSupply() > 0) {
               protected void actionElement(int iID) {
                  CFG.menuManager.rebuildInGame_DestroySupply(BuildingsManager.iBuildInProvinceID);
               }
            });
            ((MenuElement)menuElements.get(menuElements.size() - 1)).setHeight(CFG.BUTTON_HEIGHT * 3 / 5);
            ((MenuElement)menuElements.get(menuElements.size() - 1)).setCurrent(tRow);
         }

         tRow = (tRow + 1) % 2;
         tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
      }

      int tempBuildings = menuElements.size();
      menuElements.add(new Text_BuildTitle(CFG.langManager.get("Decrees"), -1, 0, tPosY, tempW, CFG.TEXT_HEIGHT + CFG.PADDING * 4) {
      });
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
      menuElements.add(new Button_Build(CFG.langManager.get("Festival"), Images.diplo_festival, DiplomacyManager.festivalCost(BuildingsManager.iBuildInProvinceID), 8, 0, tPosY, tempW, true, false, CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isFestivalOrganized_TurnsLeft(BuildingsManager.iBuildInProvinceID), 0.0F) {
         protected void actionElement(int iID) {
            if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isFestivalOrganized_TurnsLeft(BuildingsManager.iBuildInProvinceID) > 0) {
               CFG.toast.setInView(CFG.langManager.get("InConstruction"), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
            } else {
               CFG.menuManager.rebuildInGame_Festival(BuildingsManager.iBuildInProvinceID);
            }

         }

         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("OrganizeAFestivalIn") + ": ", CFG.COLOR_TEXT_NUM_OF_PROVINCES));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName().length() > 0 ? CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName() : CFG.langManager.get("Province")));
            nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getCivID(), CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Happiness") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("+" + (float)((int)(DiplomacyManager.festivalHappinessPerTurn(BuildingsManager.iBuildInProvinceID) * 10000.0F)) / 100.0F, CFG.COLOR_TEXT_MODIFIER_POSITIVE));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.happiness, CFG.PADDING, CFG.PADDING));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("PerTurn"), CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("NeighboringProvinces") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("+" + (float)((int)(DiplomacyManager.festivalHappinessPerTurn_NeighboringProvinces() * 10000.0F)) / 100.0F, CFG.COLOR_TEXT_MODIFIER_POSITIVE));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.happiness, CFG.PADDING, CFG.PADDING));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("PerTurn"), CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Space());
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Cost") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + DiplomacyManager.festivalCost(BuildingsManager.iBuildInProvinceID), CFG.COLOR_INGAME_GOLD));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_gold, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("MovementPoints") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("-0.8", CFG.COLOR_INGAME_MOVEMENT));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_movement_points, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      ((MenuElement)menuElements.get(menuElements.size() - 1)).setHeight(CFG.BUTTON_HEIGHT * 3 / 5);
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
      menuElements.add(new Button_Build_DiplomacyCost(CFG.langManager.get("Assimilate"), Images.diplo_popstability, DiplomacyManager.assimilateCost(BuildingsManager.iBuildInProvinceID, 1), 6, 0, tPosY, tempW, !CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).isOccupied(), false, CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isAssimialateOrganized_TurnsLeft(BuildingsManager.iBuildInProvinceID), 0.0F) {
         protected void actionElement(int iID) {
            if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isAssimilateOrganized(BuildingsManager.iBuildInProvinceID)) {
               CFG.toast.setInView(CFG.langManager.get("InConstruction"), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
            } else {
               CFG.menuManager.rebuildInGame_Assimilate(BuildingsManager.iBuildInProvinceID);
            }

         }

         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            if (!this.getClickable() && CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).isOccupied()) {
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("OccupiedProvince"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
               nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getCivID(), CFG.PADDING, 0));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
               nData.add(new MenuElement_Hover_v2_Element_Type_Space());
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
            }

            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Assimilate") + ": ", CFG.COLOR_TEXT_NUM_OF_PROVINCES));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName().length() > 0 ? CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName() : CFG.langManager.get("Province")));
            nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getCivID(), CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("PromoteOurTraditionsAndCulturesInThisProvince")));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("APercentageOfTheLocalsWillConvertToOurNationality")));
            nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("ProvinceStabilityWillBeIncreased"), CFG.COLOR_TEXT_MODIFIER_POSITIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Space());
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Cost") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + DiplomacyManager.assimilateCost(BuildingsManager.iBuildInProvinceID, 1), CFG.COLOR_INGAME_GOLD));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_gold, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("DiplomacyPoints") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("-0.6", CFG.COLOR_INGAME_DIPLOMACY_POINTS));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_diplomacy_points, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      ((MenuElement)menuElements.get(menuElements.size() - 1)).setHeight(CFG.BUTTON_HEIGHT * 3 / 5);
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
      menuElements.add(new Button_Build(CFG.langManager.get("Invest"), Images.economy, 0, 12, 0, tPosY, tempW, true, false, CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isInvestOrganized_TurnsLeft(BuildingsManager.iBuildInProvinceID), 0.0F) {
         protected void actionElement(int iID) {
            if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isInvestOrganized_TurnsLeft(BuildingsManager.iBuildInProvinceID) > 0) {
               CFG.toast.setInView(CFG.langManager.get("InConstruction"), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
            } else {
               CFG.menuManager.rebuildInGame_Invest(BuildingsManager.iBuildInProvinceID);
            }

         }

         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("InvestIn") + ": ", CFG.COLOR_TEXT_NUM_OF_PROVINCES));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName().length() > 0 ? CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName() : CFG.langManager.get("Province")));
            nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getCivID(), CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Economy") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.getNumberWithSpaces("" + CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getEconomy()), CFG.COLOR_TEXT_ECONOMY));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.economy, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Space());
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("MovementPoints") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("-1.2", CFG.COLOR_INGAME_MOVEMENT));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_movement_points, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("ConstructionWillTakeXurns", 4)));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.time, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      ((MenuElement)menuElements.get(menuElements.size() - 1)).setHeight(CFG.BUTTON_HEIGHT * 3 / 5);
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
      menuElements.add(new Button_Build(CFG.langManager.get("Invest"), Images.development, 0, 8, 0, tPosY, tempW, true, false, CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isInvestOrganized_TurnsLeft_Development(BuildingsManager.iBuildInProvinceID), 0.0F) {
         protected void actionElement(int iID) {
            if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isInvestOrganized_TurnsLeft_Development(BuildingsManager.iBuildInProvinceID) > 0) {
               CFG.toast.setInView(CFG.langManager.get("InConstruction"), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
            } else {
               CFG.menuManager.rebuildInGame_InvestDevelopment(BuildingsManager.iBuildInProvinceID);
            }

         }

         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("InvestIn") + ": ", CFG.COLOR_TEXT_NUM_OF_PROVINCES));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName().length() > 0 ? CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName() : CFG.langManager.get("Province")));
            nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getCivID(), CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Development") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + String.format("%.2f", CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getDevelopmentLevel()).replace(',', '.'), CFG.COLOR_TEXT_MODIFIER_NEUTRAL2));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.development, CFG.PADDING, 0));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(" / "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (float)((int)(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getTechnologyLevel() * 100.0F)) / 100.0F, CFG.COLOR_TEXT_TECHNOLOGY));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.technology, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Space());
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("MovementPoints") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("-0.8", CFG.COLOR_INGAME_MOVEMENT));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_movement_points, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("ConstructionWillTakeXurns", 4)));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.time, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getTechnologyLevel() - 0.01F <= CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getDevelopmentLevel()) {
               nData.add(new MenuElement_Hover_v2_Element_Type_Space());
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Tech5"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
               nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()));
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("TechnologyLevel") + ": "));
               nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (float)((int)(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getTechnologyLevel() * 100.0F)) / 100.0F, CFG.COLOR_TEXT_TECHNOLOGY));
               nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.technology, CFG.PADDING, 0));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
            } else {
               nData.add(new MenuElement_Hover_v2_Element_Type_Space());
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
               nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()));
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Tech5"), CFG.COLOR_TEXT_MODIFIER_NEUTRAL));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
            }

            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      ((MenuElement)menuElements.get(menuElements.size() - 1)).setHeight(CFG.BUTTON_HEIGHT * 3 / 5);
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
      if (CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).isOccupied()) {
         menuElements.add(new Button_Build_DiplomacyCost(CFG.langManager.get("TransferControl"), Images.transfer_control, 0, 4, 0, tPosY, tempW, true, false, 0, 0.0F) {
            protected void actionElement(int iID) {
               CFG.menuManager.rebuildInGame_TransferControl(BuildingsManager.iBuildInProvinceID);
            }

            protected void buildElementHover() {
               List nElements = new ArrayList();
               List nData = new ArrayList();
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("TransferControlOverProvince") + ": ", CFG.COLOR_TEXT_NUM_OF_PROVINCES));
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName().length() > 0 ? CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName() : CFG.langManager.get("Province")));
               nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getCivID(), CFG.PADDING, 0));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
               nData.add(new MenuElement_Hover_v2_Element_Type_Space());
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("DiplomacyPoints") + ": "));
               nData.add(new MenuElement_Hover_v2_Element_Type_Text("-0.4", CFG.COLOR_INGAME_DIPLOMACY_POINTS));
               nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_diplomacy_points, CFG.PADDING, 0));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover_v2(nElements);
            }
         });
         ((MenuElement)menuElements.get(menuElements.size() - 1)).setHeight(CFG.BUTTON_HEIGHT * 3 / 5);
         tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
      }

      menuElements.add(new Text_BuildTitle(CFG.langManager.get("Army"), -1, 0, tPosY, tempW, CFG.TEXT_HEIGHT + CFG.PADDING * 4) {
         protected Color getColor(boolean isActive) {
            return isActive ? CFG.COLOR_TEXT_OPTIONS_NS_HOVER : (this.getClickable() ? (this.getIsHovered() ? CFG.COLOR_TEXT_OPTIONS_NS : Color.WHITE) : new Color(0.78F, 0.78F, 0.78F, 0.7F));
         }
      });
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
      menuElements.add(new Button_Build(CFG.langManager.get("Conscript"), Images.diplo_army, 0, 0, 0, tPosY, tempW, true, false, 0, 0.0F) {
         protected void actionElement(int iID) {
            if (BuildingsManager.iBuildInProvinceID >= 0) {
               if (CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getTrueOwnerOfProvince() == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) {
                  CFG.game.setActiveProvinceID(BuildingsManager.iBuildInProvinceID);
                  if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMovePoints() < CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getIdeologyID()).COST_OF_RECRUIT) {
                     CFG.menuManager.setVisible_InGame_ActionInfo_NoMovementPoints();
                  } else if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoney() < (long)CFG.getCostOfRecruitArmyMoney_Instantly(BuildingsManager.iBuildInProvinceID)) {
                     CFG.menuManager.setVisible_InGame_ActionInfo_TreasuryIsEmpty();
                  } else {
                     CFG.game.resetChooseProvinceData();
                     CFG.menuManager.setVisible_InGame_ProvinceAction(false);
                     CFG.gameAction.updateRecruitSlider_Instantly();
                     CFG.menuManager.setVisible_InGame_ProvinceRecruitInstantly(true);
                     CFG.menuManager.setVisible_InGame_ActionInfo_RecruitInstantly();
                     Menu_InGame_Build.this.setVisible(false);
                  }
               } else {
                  CFG.menuManager.setVisible_InGame_ActionInfo_RecruitOccupied();
               }
            }

         }

         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("RecruitArmyInstantly"), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
            nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("CostOfRecruitingWillBeDoubled"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Space());
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("OneUnitCostsXGold", CFG.getCostOfRecruitArmyMoney_Instantly(BuildingsManager.iBuildInProvinceID)), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_gold, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Cost") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (float)CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getIdeologyID()).COST_OF_RECRUIT / 10.0F, CFG.COLOR_INGAME_MOVEMENT));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_movement_points, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }

         protected int getSFX() {
            try {
               return CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getTrueOwnerOfProvince() != CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() ? super.getSFX() : SoundsManager.SOUND_RECRUIT;
            } catch (IndexOutOfBoundsException var2) {
               return super.getSFX();
            }
         }

         protected boolean getClickable() {
            try {
               if (BuildingsManager.iBuildInProvinceID >= 0) {
                  return CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getTrueOwnerOfProvince() == CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID();
               } else {
                  return super.getClickable();
               }
            } catch (IndexOutOfBoundsException var2) {
               return super.getClickable();
            }
         }
      });
      ((MenuElement)menuElements.get(menuElements.size() - 1)).setHeight(CFG.BUTTON_HEIGHT * 3 / 5);
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
      menuElements.add(new Button_Build(CFG.langManager.get("DisbandArmy"), Images.diplo_army, 0, 0, 0, tPosY, tempW, true, false, 0, 0.0F) {
         protected void actionElement(int iID) {
            if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMovePoints() < CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getIdeologyID()).COST_OF_DISBAND) {
               CFG.toast.setInView(CFG.langManager.get("NoMovementPoints") + ".", CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
            } else {
               CFG.game.resetChooseProvinceData();
               CFG.menuManager.setVisible_InGame_ProvinceAction(false);
               CFG.activeCivilizationArmyID = 0;
               CFG.gameAction.updateDisbandSlider();
               CFG.menuManager.setVisible_InGame_ProvinceDisband(true);
               CFG.menuManager.setVisible_InGame_ActionInfo_Disband();
               CFG.menuManager.setVisible_InGame_ProvinceBuild(false, false);
            }

         }

         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            if (CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getArmyCivID(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) > 0) {
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Army") + ": "));
               nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getArmyCivID(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
               nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.PADDING, 0));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
            } else {
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("NoUnits"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
               nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), CFG.PADDING, 0));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
            }

            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("MovementPoints") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (float)CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getIdeologyID()).COST_OF_DISBAND / 10.0F, CFG.COLOR_INGAME_MOVEMENT));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_movement_points, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }

         protected boolean getClickable() {
            return CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getArmyCivID(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()) > 0;
         }
      });
      ((MenuElement)menuElements.get(menuElements.size() - 1)).setHeight(CFG.BUTTON_HEIGHT * 3 / 5);
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
      menuElements.add(new Button_Build(CFG.langManager.get("Plunder"), Images.diplo_plunder, 0, 0, 0, tPosY, tempW, true, false, CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).isPlundred(BuildingsManager.iBuildInProvinceID) ? 1 : 0, 0.0F) {
         protected void actionElement(int iID) {
            CFG.menuManager.rebuildInGame_Plunder(BuildingsManager.iBuildInProvinceID);
         }

         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            if (CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).isOccupied()) {
               if (CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName().length() > 0) {
                  nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getTrueOwnerOfProvince()));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Plunder") + ": ", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName()));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
               } else {
                  nData.add(new MenuElement_Hover_v2_Element_Type_Flag(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getTrueOwnerOfProvince()));
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Plunder"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
               }

               nData.add(new MenuElement_Hover_v2_Element_Type_Space());
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("OccupiedProvince")));
               nData.add(new MenuElement_Hover_v2_Element_Type_Image(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).isOccupied() ? Images.icon_check_true : Images.icon_check_false, CFG.PADDING, 0));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Cost") + ": "));
               nData.add(new MenuElement_Hover_v2_Element_Type_Text("" + (float)CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getIdeologyID()).COST_OF_PLUNDER / 10.0F, CFG.COLOR_INGAME_MOVEMENT));
               nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_movement_points, CFG.PADDING, 0));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
            } else {
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("OnlyOccupiedProvinceCanBePlundered"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
            }

            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }

         protected boolean getClickable() {
            return CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).isOccupied();
         }
      });
      ((MenuElement)menuElements.get(menuElements.size() - 1)).setHeight(CFG.BUTTON_HEIGHT * 3 / 5);
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
      menuElements.add(new Text_BuildTitle(CFG.langManager.get("Province"), -1, 0, tPosY, tempW, CFG.TEXT_HEIGHT + CFG.PADDING * 4) {
         protected Color getColor(boolean isActive) {
            return isActive ? CFG.COLOR_TEXT_OPTIONS_NS_HOVER : (this.getClickable() ? (this.getIsHovered() ? CFG.COLOR_TEXT_OPTIONS_NS : Color.WHITE) : new Color(0.78F, 0.78F, 0.78F, 0.7F));
         }
      });
      tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();

      try {
         menuElements.add(new Button_Build(CFG.langManager.get("Abandon"), Images.provinces, 0, 0, 0, tPosY, tempW, BuildingsManager.iBuildInProvinceID != CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCapitalProvinceID() && !CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).isOccupied(), false, 0, 0.0F) {
            protected void actionElement(int iID) {
               if (BuildingsManager.iBuildInProvinceID >= 0) {
                  CFG.menuManager.rebuildInGame_Abadon(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), BuildingsManager.iBuildInProvinceID);
               }

            }
         });
         ((MenuElement)menuElements.get(menuElements.size() - 1)).setHeight(CFG.BUTTON_HEIGHT * 3 / 5);
         tPosY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight();
      } catch (IndexOutOfBoundsException var7) {
         CFG.exceptionStack(var7);
      }

      for(tRow = tempBuildings; tRow < menuElements.size(); ++tRow) {
         ((MenuElement)menuElements.get(tRow)).setCurrent(tRow % 2);
      }

      this.initMenu(new SliderMenuTitle((String)null, CFG.BUTTON_HEIGHT * 3 / 5, false, false) {
         protected void draw(SpriteBatch oSB, int iTranslateX, int nPosX, int nPosY, int nWidth, boolean sliderMenuIsActive) {
            ImageManager.getImage(Images.new_game_top_edge_title).draw2(oSB, Menu_InGame_Build.this.getPosX() + iTranslateX, Menu_InGame_Build.this.getPosY() - ImageManager.getImage(Images.new_game_top_edge_title).getHeight() - this.getHeight(), Menu_InGame_Build.this.getWidth() + 2, this.getHeight(), true, false);
            oSB.setColor(new Color((float)CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getR() / 255.0F, (float)CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getG() / 255.0F, (float)CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getB() / 255.0F, 0.165F));
            ImageManager.getImage(Images.line_32_off1).draw(oSB, nPosX + iTranslateX, nPosY - this.getHeight() + 2 - ImageManager.getImage(Images.line_32_off1).getHeight(), nWidth, this.getHeight() - 2, false, true);
            oSB.setColor(new Color((float)CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getR() / 255.0F, (float)CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getG() / 255.0F, (float)CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getB() / 255.0F, 0.375F));
            ImageManager.getImage(Images.gradient).draw(oSB, nPosX + iTranslateX, nPosY - this.getHeight() * 2 / 3 - ImageManager.getImage(Images.gradient).getHeight(), nWidth, this.getHeight() * 2 / 3, false, true);
            oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.6F));
            ImageManager.getImage(Images.line_32_off1).draw(oSB, Menu_InGame_Build.this.getPosX() + iTranslateX, Menu_InGame_Build.this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() - ImageManager.getImage(Images.line_32_off1).getHeight(), Menu_InGame_Build.this.getWidth(), 1);
            oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.8F));
            ImageManager.getImage(Images.slider_gradient).draw(oSB, Menu_InGame_Build.this.getPosX() + iTranslateX, Menu_InGame_Build.this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() - ImageManager.getImage(Images.slider_gradient).getHeight(), Menu_InGame_Build.this.getWidth() / 4, 1);
            ImageManager.getImage(Images.slider_gradient).draw(oSB, Menu_InGame_Build.this.getPosX() + Menu_InGame_Build.this.getWidth() - Menu_InGame_Build.this.getWidth() / 4 + iTranslateX, Menu_InGame_Build.this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() - ImageManager.getImage(Images.slider_gradient).getHeight(), Menu_InGame_Build.this.getWidth() / 4, 1, true, false);
            if (AoCGame.LEFT != 0) {
               oSB.setColor(CFG.COLOR_CREATE_NEW_GAME_BOX_PLAYERS);
               ImageManager.getImage(Images.pix255_255_255).draw2(oSB, Menu_InGame_Build.this.getPosX() + iTranslateX, Menu_InGame_Build.this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() - this.getHeight(), 1, this.getHeight(), true, false);
            }

            oSB.setColor(Color.WHITE);
            CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getFlag().draw(oSB, Menu_InGame_Build.this.getPosX() + CFG.PADDING * 2 + iTranslateX, Menu_InGame_Build.this.getPosY() - this.getHeight() / 2 - CFG.CIV_FLAG_HEIGHT / 2 - CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getFlag().getHeight(), CFG.CIV_FLAG_WIDTH, CFG.CIV_FLAG_HEIGHT);
            ImageManager.getImage(Images.flag_rect).draw(oSB, Menu_InGame_Build.this.getPosX() + CFG.PADDING * 2 + iTranslateX, Menu_InGame_Build.this.getPosY() - this.getHeight() / 2 - CFG.CIV_FLAG_HEIGHT / 2);
            CFG.fontMain.getData().setScale(0.8F);
            CFG.drawText(oSB, this.getText(), nPosX + nWidth / 2 - (int)((float)this.getTextWidth() * 0.8F / 2.0F) + iTranslateX, nPosY - this.getHeight() + this.getHeight() / 2 + 1 - (int)((float)this.getTextHeight() * 0.8F / 2.0F), Color.WHITE);
            CFG.fontMain.getData().setScale(1.0F);
         }
      }, 0 + AoCGame.LEFT, ImageManager.getImage(Images.top_left).getHeight() + CFG.PADDING * 3 + CFG.BUTTON_HEIGHT * 3 / 5, tempW, Math.min(tPosY, CFG.GAME_HEIGHT - (ImageManager.getImage(Images.top_left).getHeight() + CFG.PADDING * 3 + CFG.BUTTON_HEIGHT * 3 / 4 + (CFG.PADDING * 2 + CFG.BUTTON_HEIGHT) * 2)), menuElements, false, true);
      if (BuildingsManager.iBuildInProvinceID < 0) {
         this.setVisible(false);
      }

      this.updateLanguage();
   }

   protected void updateLanguage() {
      this.getTitle().setText(CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName().length() > 0 ? CFG.game.getProvince(BuildingsManager.iBuildInProvinceID).getName() : CFG.langManager.get("More"));
   }

   protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
      if (lTime + 175L >= System.currentTimeMillis()) {
         if (hideAnimation) {
            iTranslateX -= (int)((float)this.getWidth() * ((float)(System.currentTimeMillis() - lTime) / 175.0F));
         } else {
            iTranslateX += -this.getWidth() + (int)((float)this.getWidth() * ((float)(System.currentTimeMillis() - lTime) / 175.0F));
         }

         CFG.setRender_3(true);
      } else if (hideAnimation) {
         super.setVisible(false);
         return;
      }

      ImageManager.getImage(Images.new_game_top_edge_line).draw2(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.new_game_top_edge_line).getHeight() + iTranslateY, this.getWidth() + 2, this.getHeight() + CFG.PADDING, true, true);
      oSB.setColor(new Color(0.09803922F, 0.05882353F, 0.37254903F, 0.25F));
      ImageManager.getImage(Images.gradient).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.gradient).getHeight() + iTranslateY, this.getWidth(), CFG.PADDING * 4);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.65F));
      ImageManager.getImage(Images.gradient).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.gradient).getHeight() + iTranslateY, this.getWidth(), CFG.PADDING * 2);
      oSB.setColor(Color.WHITE);
      super.draw(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      oSB.setColor(CFG.COLOR_CREATE_NEW_GAME_BOX_PLAYERS);
      ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() + this.getHeight() + CFG.PADDING, this.getWidth());
      oSB.setColor(new Color(CFG.COLOR_FLAG_FRAME.r, CFG.COLOR_FLAG_FRAME.g, CFG.COLOR_FLAG_FRAME.b, 0.4F));
      ImageManager.getImage(Images.slider_gradient).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() - ImageManager.getImage(Images.slider_gradient).getHeight() + this.getHeight() + CFG.PADDING, this.getWidth(), 1);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F));
      ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() + CFG.PADDING, this.getWidth() + 2);
      if (AoCGame.LEFT != 0) {
         oSB.setColor(CFG.COLOR_CREATE_NEW_GAME_BOX_PLAYERS);
         ImageManager.getImage(Images.pix255_255_255).draw2(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() + iTranslateY, 1, this.getHeight() + CFG.PADDING, true, true);
      }

      oSB.setColor(Color.WHITE);
   }

   protected void drawScrollPos(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
      if (sliderMenuIsActive) {
         super.drawScrollPos(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      }

   }

   protected void actionElement(int iID) {
      if (!CFG.SPECTATOR_MODE) {
         this.getMenuElement(iID).actionElement(iID);
      }
   }

   protected void setVisible(boolean visible) {
      if (visible) {
         super.setVisible(visible);
         this.setHideAnimation(false);
      } else {
         this.setHideAnimation(true);
      }

   }

   protected final void setHideAnimation(boolean nHideAnimation) {
      if (nHideAnimation != hideAnimation) {
         if (lTime > System.currentTimeMillis() - 175L) {
            lTime = System.currentTimeMillis() - (175L - (System.currentTimeMillis() - lTime));
         } else {
            lTime = System.currentTimeMillis();
         }

         CFG.setRender_3(true);
      }

      hideAnimation = nHideAnimation;
   }
}
