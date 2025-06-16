package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

class Menu_CreateScenario_Assign extends Menu_CreateScenario {
   private String assignProvinces;
   private int iStepWidth;
   private String assignProvinces2;
   private int iStepWidth2;

   protected Menu_CreateScenario_Assign() {
      List menuElements = new ArrayList();
      menuElements.add(new Button_Game((String)null, -1, CFG.PADDING, CFG.PADDING, true) {
         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("ManageCivilizations"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      menuElements.add(new Button_Game((String)null, -1, CFG.GAME_WIDTH - CFG.BUTTON_WIDTH - CFG.PADDING, CFG.PADDING, true) {
         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("ScenarioSettings"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      menuElements.add(new Minimap(CFG.GAME_WIDTH - CFG.map.getMapBG().getMinimapWidth(), CFG.GAME_HEIGHT - CFG.map.getMapBG().getMinimapHeight()));
      menuElements.add(new Button_Game((String)null, -1, CFG.PADDING, CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING, true, true) {
         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Tip") + ": "));
            nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("SelectCapital"), CFG.COLOR_TEXT_NUM_OF_PROVINCES));
            nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.editor_city, CFG.PADDING, 0));
            nElements.add(new MenuElement_Hover_v2_Element2(nData));
            nData.clear();
            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });
      menuElements.add(new Button_Game_Checkbox((String)null, -1, CFG.PADDING, CFG.BUTTON_HEIGHT + CFG.PADDING * 3, CFG.BUTTON_WIDTH * 2, true, CFG.brushTool) {
         protected boolean getCheckboxState() {
            return CFG.brushTool;
         }
      });
      menuElements.add(new Slide(CFG.GAME_WIDTH - CFG.PADDING - ImageManager.getImage(Images.slide_bg).getHeight() / 2 - ImageManager.getImage(Images.slide_bg).getHeight() * 2, CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING * 3 - ImageManager.getImage(Images.slide_bg).getHeight() * 2 - ImageManager.getImage(Images.slide_bg).getHeight() / 2, false));
      menuElements.add(new Button_Game((String)null, -1, CFG.BUTTON_WIDTH * 2 + CFG.PADDING * 2, CFG.BUTTON_HEIGHT + CFG.PADDING * 3, false));
      menuElements.add(new Button_Game_Checkbox((String)null, -1, CFG.PADDING, CFG.BUTTON_HEIGHT + CFG.PADDING * 3, CFG.BUTTON_WIDTH * 2, true, !CFG.VIEW_SHOW_VALUES) {
         protected boolean getCheckboxState() {
            return !CFG.VIEW_SHOW_VALUES;
         }
      });

      //occupy button
      menuElements.add(new Button_Game_Checkbox((String)null, -1, CFG.PADDING, CFG.BUTTON_HEIGHT * 2 + CFG.PADDING * 5, CFG.BUTTON_WIDTH * 2, true, CFG.occupyTool){
         @Override
         protected boolean getCheckboxState() {
            return CFG.occupyTool;
         }

         protected void buildElementHover() {
            List nElements = new ArrayList();
            List nData = new ArrayList();
            //iterate tooltip /n's
            for (String tooltip: CFG.langManager.get("OccupyToolTip").split("\n")) {
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(tooltip, CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
            }

            this.menuElementHover = new MenuElement_Hover_v2(nElements);
         }
      });

      this.initMenu((SliderMenuTitle)null, 0, 0, CFG.GAME_WIDTH, CFG.GAME_HEIGHT, menuElements);
      this.updateLanguage();
   }

   protected void updateLanguage() {
      super.updateLanguage();
      this.getMenuElement(3).setText(CFG.langManager.get("SelectCivilization"));
      this.updatedButtonsWidthFromToID(3, 4, CFG.PADDING, CFG.BUTTON_WIDTH);
      this.assignProvinces = CFG.langManager.get("AssignProvinces");
      CFG.glyphLayout.setText(CFG.fontMain, this.assignProvinces);
      this.iStepWidth = (int)CFG.glyphLayout.width;
      this.assignProvinces2 = CFG.langManager.get("ClickAProvinceOnTheMapToAssignProvinceToCivilization") + ".";
      CFG.glyphLayout.setText(CFG.fontMain, this.assignProvinces2);
      this.iStepWidth2 = (int)CFG.glyphLayout.width;
      this.getMenuElement(4).setText(CFG.langManager.get("Brush"));
      this.getMenuElement(6).setText(CFG.langManager.get("Undo"));
      this.getMenuElement(7).setText(CFG.langManager.get("Flags"));
      this.getMenuElement(8).setText(CFG.langManager.get("Occupation"));

      this.updatedButtonsWidthFromToID(4, 5, CFG.PADDING, CFG.BUTTON_WIDTH * 2);
      this.updatedButtonsWidthFromToID(6, 8, this.getMenuElement(4).getPosX() + this.getMenuElement(4).getWidth() + CFG.PADDING, CFG.BUTTON_WIDTH);

      int tempX = CFG.GAME_WIDTH - this.getMenuElement(7).getWidth() - CFG.PADDING;
      this.getMenuElement(7).setPosX(tempX);
      tempX = tempX - this.getMenuElement(4).getWidth() - CFG.PADDING;
      this.getMenuElement(4).setPosX(tempX);
      tempX = tempX - this.getMenuElement(6).getWidth() - CFG.PADDING;
      this.getMenuElement(6).setPosX(tempX);
      //move occupybutton x position to be under other buttons
      tempX = CFG.GAME_WIDTH - this.getMenuElement(8).getWidth() - CFG.PADDING;
      this.getMenuElement(8).setPosX(tempX);
   }

   protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
      CFG.drawEditorTitle_Edge_R_Reflected(oSB, iTranslateX, this.getMenuPosY() + iTranslateY, CFG.GAME_WIDTH, CFG.BUTTON_HEIGHT + CFG.PADDING * 2);
      CFG.drawEditorButtons_Top_Edge_R_Reflected(oSB, this.getMenuElement(6).getPosX() - CFG.PADDING + iTranslateX, this.getMenuPosY() + CFG.BUTTON_HEIGHT + CFG.PADDING * 2 + iTranslateY, CFG.GAME_WIDTH - (this.getMenuElement(6).getPosX() - CFG.PADDING), CFG.BUTTON_HEIGHT + CFG.PADDING * 2);
      CFG.drawEditorButtons_Bot_Edge_R(oSB, iTranslateX, this.getMenuPosY() + this.getMenuElement(3).getPosY() - CFG.PADDING + iTranslateY, this.getMenuElement(3).getPosX() + this.getMenuElement(3).getWidth() + CFG.PADDING, this.getMenuElement(3).getHeight() + CFG.PADDING * 2);
      CFG.drawTextWithShadow(oSB, this.assignProvinces, CFG.GAME_WIDTH / 2 - (this.iStepWidth + CFG.CIV_FLAG_WIDTH + CFG.PADDING) / 2 + CFG.PADDING + CFG.CIV_FLAG_WIDTH + iTranslateX, CFG.PADDING + CFG.BUTTON_HEIGHT / 2 - CFG.TEXT_HEIGHT - CFG.PADDING / 2 + this.getMenuPosY() + iTranslateY, new Color(CFG.COLOR_TEXT_CNG_TOP_SCENARIO_NAME.r, CFG.COLOR_TEXT_CNG_TOP_SCENARIO_NAME.g, CFG.COLOR_TEXT_CNG_TOP_SCENARIO_NAME.b, 0.95F));
      CFG.fontMain.getData().setScale(0.8F);
      CFG.drawTextWithShadow(oSB, this.assignProvinces2, CFG.GAME_WIDTH / 2 - (int)((float)this.iStepWidth2 * 0.8F / 2.0F) + iTranslateX, CFG.PADDING + CFG.BUTTON_HEIGHT / 2 + CFG.PADDING + this.getMenuPosY() + iTranslateY, new Color(CFG.COLOR_TEXT_CNG_TOP_SCENARIO_INFO.r, CFG.COLOR_TEXT_CNG_TOP_SCENARIO_INFO.g, CFG.COLOR_TEXT_CNG_TOP_SCENARIO_INFO.b, 0.75F));
      CFG.fontMain.getData().setScale(1.0F);
      if (CFG.iCreateScenario_AssignProvinces_Civ >= 0) {
         CFG.game.getCiv(CFG.iCreateScenario_AssignProvinces_Civ).getFlag().draw(oSB, CFG.GAME_WIDTH / 2 - (this.iStepWidth + CFG.CIV_FLAG_WIDTH + CFG.PADDING) / 2 + iTranslateX, CFG.PADDING + CFG.BUTTON_HEIGHT / 2 - CFG.CIV_FLAG_HEIGHT - CFG.PADDING / 2 + this.getMenuPosY() - CFG.game.getCiv(CFG.iCreateScenario_AssignProvinces_Civ).getFlag().getHeight() + iTranslateY, CFG.CIV_FLAG_WIDTH, CFG.CIV_FLAG_HEIGHT);
         ImageManager.getImage(Images.flag_rect).draw(oSB, CFG.GAME_WIDTH / 2 - (this.iStepWidth + CFG.CIV_FLAG_WIDTH + CFG.PADDING) / 2 + iTranslateX, CFG.PADDING + CFG.BUTTON_HEIGHT / 2 - CFG.CIV_FLAG_HEIGHT - CFG.PADDING / 2 + this.getMenuPosY() + iTranslateY);
      }

      super.draw(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
   }

   protected final void actionElement(int iID) {
      switch (iID) {
         case 1:
            CFG.game.disableDrawCivilizationRegions(CFG.iCreateScenario_AssignProvinces_Civ);
            CFG.brushTool = false;
            CFG.occupyTool = false;
            this.getMenuElement(4).setCheckboxState(CFG.brushTool);
            this.getMenuElement(5).setVisible(CFG.brushTool);
            this.getMenuElement(8).setCheckboxState(CFG.occupyTool);
            CFG.game.setActiveProvinceID(-1);
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_SETTINGS);
            CFG.menuManager.disposeFlagsCreate_Scenario_Assign();
            return;
         case 2:
            CFG.map.getMapCoordinates().centerToMinimapClick(Touch.getMousePosX() - this.getMenuElement(iID).getPosX() - this.getPosX(), Touch.getMousePosY() - this.getMenuElement(iID).getPosY() - this.getMenuPosY());
         case 3:
            CFG.game.setActiveProvinceID(-1);
            CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_ASSIGN_SELECT);
            CFG.menuManager.disposeFlagsCreate_Scenario_Assign();
            return;
         case 4:
            CFG.brushTool = !CFG.brushTool;
            this.getMenuElement(iID).setCheckboxState(CFG.brushTool);
            this.getMenuElement(iID + 1).setVisible(CFG.brushTool);
            return;
         case 5:

         default:
            super.actionElement(iID);
            return;
         case 6:
            if (CFG.lCreateScenario_UndoAssignProvincesCivID.size() > 0) {
               CFG.game.getProvince(((Undo_AssignProvinceCiv)CFG.lCreateScenario_UndoAssignProvincesCivID.get(CFG.lCreateScenario_UndoAssignProvincesCivID.size() - 1)).getProvinceID()).setCivID(((Undo_AssignProvinceCiv)CFG.lCreateScenario_UndoAssignProvincesCivID.get(CFG.lCreateScenario_UndoAssignProvincesCivID.size() - 1)).getCivID(), false);
               //revert true owner if occupy tool is off
               if (!CFG.occupyTool) {
                  CFG.game.getProvince(((Undo_AssignProvinceCiv)CFG.lCreateScenario_UndoAssignProvincesCivID.get(CFG.lCreateScenario_UndoAssignProvincesCivID.size() - 1)).getProvinceID()).setTrueOwnerOfProvince(((Undo_AssignProvinceCiv)CFG.lCreateScenario_UndoAssignProvincesCivID.get(CFG.lCreateScenario_UndoAssignProvincesCivID.size() - 1)).getCivID());
               }
               CFG.game.getProvince(((Undo_AssignProvinceCiv)CFG.lCreateScenario_UndoAssignProvincesCivID.get(CFG.lCreateScenario_UndoAssignProvincesCivID.size() - 1)).getProvinceID()).buildProvinceCore();
               CFG.province_Cores_GameData.clearCoresData(((Undo_AssignProvinceCiv)CFG.lCreateScenario_UndoAssignProvincesCivID.get(CFG.lCreateScenario_UndoAssignProvincesCivID.size() - 1)).getProvinceID());
               CFG.game.setActiveProvinceID(((Undo_AssignProvinceCiv)CFG.lCreateScenario_UndoAssignProvincesCivID.get(CFG.lCreateScenario_UndoAssignProvincesCivID.size() - 1)).getProvinceID());
               if (!CFG.game.getProvince(CFG.game.getActiveProvinceID()).getDrawProvince()) {
                  CFG.map.getMapCoordinates().centerToProvinceID(CFG.game.getActiveProvinceID());
               }

               CFG.removeUndoAssignProvinces();
            }

            return;
         case 7:
            CFG.VIEW_SHOW_VALUES = !CFG.VIEW_SHOW_VALUES;
            return;
         case 8:
            //toggle occupy
            CFG.occupyTool = !CFG.occupyTool;
            this.getMenuElement(iID).setCheckboxState(CFG.occupyTool);
            return;
      }
   }

   protected void onBackPressed() {
      CFG.game.disableDrawCivilizationRegions(CFG.iCreateScenario_AssignProvinces_Civ);
      CFG.brushTool = false;
      CFG.occupyTool = false;
      this.getMenuElement(4).setCheckboxState(CFG.brushTool);
      this.getMenuElement(5).setVisible(CFG.brushTool);
      this.getMenuElement(8).setCheckboxState(CFG.occupyTool);
      CFG.menuManager.setViewID(Menu.eCREATE_SCENARIO_CIVILIZATIONS);
      CFG.game.setActiveProvinceID(-1);
      CFG.updateCreateScenario_Civilizations();
      CFG.menuManager.disposeFlagsCreate_Scenario_Assign();
   }
}
