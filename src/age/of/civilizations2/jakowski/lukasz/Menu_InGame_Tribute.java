package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import java.util.ArrayList;
import java.util.List;

class Menu_InGame_Tribute extends SliderMenu {
   protected Menu_InGame_Tribute() {
      List menuElements = new ArrayList();
      int tempWidth = CFG.CIV_INFO_MENU_WIDTH * 2;
      int tempMenuPosY = ImageManager.getImage(Images.top_left2).getHeight() + CFG.PADDING * 2 + CFG.BUTTON_HEIGHT * 3 / 5 + CFG.PADDING * 2;
      if (tempWidth > CFG.GAME_WIDTH) {
         tempWidth = CFG.GAME_WIDTH - CFG.PADDING * 2;
      }

      this.initMenu((SliderMenuTitle)null, CFG.GAME_WIDTH / 2 - tempWidth / 2, tempMenuPosY, tempWidth, CFG.GAME_HEIGHT * 3 / 5, menuElements, false, false);
   }

   protected Menu_InGame_Tribute(int onCivID) {
      List menuElements = new ArrayList();
      int tempWidth = CFG.CIV_INFO_MENU_WIDTH * 2;
      int tY = 0;
      menuElements.add(new Button_Build(CFG.langManager.get("ManageVassalsMenu"), Images.diplo_lord, 0,0, 0, tY, CFG.BUTTON_WIDTH * 2, true, false, 0 ,0) {
         protected int getWidth() {
            return Menu_InGame_Tribute.this.getElementW() * 2;
         }
      });
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.size() > 0 ? CFG.PADDING : 0);

      int tempMenuPosY;
      for(tempMenuPosY = 0; tempMenuPosY < CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.size(); ++tempMenuPosY) {
         menuElements.add(new Button_Tribute_Vassal("", 2, tY, ((Vassal_GameData)CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(tempMenuPosY)).iCivID));

         //if eco control, add tribute slider, else add player-custom control menu
         if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(tempMenuPosY).autonomyStatus.isEconomicControl()) {
            menuElements.add(new Slider_FlagAction_Clear_Flag_Tribute(((Vassal_GameData) CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(tempMenuPosY)).iCivID, CFG.game.getCiv(((Vassal_GameData) CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(tempMenuPosY)).iCivID).getCivName(), CFG.PADDING * 2, tY, (tempWidth - CFG.PADDING * 3 - CFG.BUTTON_WIDTH) / 2, CFG.TEXT_HEIGHT + CFG.PADDING * 2 + CFG.PADDING * 4, 0, 20, ((Vassal_GameData) CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(tempMenuPosY)).iTribute) {
               protected int getWidth() {
                  return Math.max(Menu_InGame_Tribute.this.getElementW() * 2 - Button_Diplomacy.iDiploWidth - CFG.PADDING * 2 - ((int) ((float) CFG.BUTTON_HEIGHT * 0.6F) * 2), 0);
               }

               protected int getPosX() {
                  return 2 + Button_Diplomacy.iDiploWidth + CFG.PADDING;
               }

               protected int getSliderHeight() {
                  return CFG.PADDING + CFG.PADDING / 2;
               }

               protected Color getColorLEFT() {
                  return new Color((float) CFG.game.getCiv(this.iCivID).getR() / 255.0F, (float) CFG.game.getCiv(this.iCivID).getG() / 255.0F, (float) CFG.game.getCiv(this.iCivID).getB() / 255.0F, 0.65F);
               }

               protected void actionElement(int iID) {
                  super.actionElement(iID);
                  CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).setVassal_Tribute(this.iCivID, this.getCurrent());
                  Menu_InGame_Tribute.this.updateIncomeFromVassals();
                  Menu_InGame.updateOverBudget();
               }

               protected String getDrawText() {
                  return super.getDrawText() + "%";
               }

               protected boolean getClickable() {
                  return !CFG.SPECTATOR_MODE;
               }
            });
         } else {
            //civid through textpos x (rachet)
            menuElements.add(new Button_New_Game_Players(CFG.langManager.get("DirectVassalEconomy"), ((Vassal_GameData)CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(tempMenuPosY)).iCivID, CFG.PADDING * 2, tY, tempWidth - CFG.PADDING * 3 - CFG.BUTTON_WIDTH, true) {
               protected int getWidth() {
                  return 2 * (Math.max(Menu_InGame_Tribute.this.getElementW() * 2 - Button_Diplomacy.iDiploWidth - CFG.PADDING * 2 - ((int) ((float) CFG.BUTTON_HEIGHT * 0.6F) * 2), 0) / 3);
               }

               protected int getPosX() {
                  return 2 + Button_Diplomacy.iDiploWidth + CFG.PADDING + (Math.max(Menu_InGame_Tribute.this.getElementW() * 2 - Button_Diplomacy.iDiploWidth - CFG.PADDING * 2 - ((int) ((float) CFG.BUTTON_HEIGHT * 0.6F) * 2), 0) / 3);
               }

               protected int getSliderHeight() {
                  return CFG.PADDING + CFG.PADDING / 2;
               }

               @Override
               protected int getTextPos() {
                  return -1;
               }

               protected void actionElement(int iID) {
                  super.actionElement(iID);
                  CFG.menuManager.setVisible_InGame_Budget_Vassals(true, this.iTextPositionX);
               }

               protected boolean getClickable() {
                  return !CFG.SPECTATOR_MODE;
               }
            });
            menuElements.add(new Slider_FlagAction_Clear_Flag(((Vassal_GameData) CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(tempMenuPosY)).iCivID, CFG.game.getCiv(((Vassal_GameData) CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(tempMenuPosY)).iCivID).getCivName(), CFG.PADDING * 2, tY, tempWidth - CFG.PADDING * 3 - CFG.BUTTON_WIDTH, CFG.TEXT_HEIGHT + CFG.PADDING * 2 + CFG.PADDING * 4, 0, 20, 0) {
               protected int getWidth() {
                  return (Math.max(Menu_InGame_Tribute.this.getElementW() * 2 - Button_Diplomacy.iDiploWidth - CFG.PADDING * 2 - ((int) ((float) CFG.BUTTON_HEIGHT * 0.6F) * 2), 0))/3;
               }

               protected int getPosX() {
                  return 2 + Button_Diplomacy.iDiploWidth + CFG.PADDING;
               }

               protected int getSliderHeight() {
                  return CFG.PADDING + CFG.PADDING / 2;
               }

               protected Color getColorLEFT() {
                  return new Color((float) CFG.game.getCiv(this.iCivID).getR() / 255.0F, (float) CFG.game.getCiv(this.iCivID).getG() / 255.0F, (float) CFG.game.getCiv(this.iCivID).getB() / 255.0F, 0.65F);
               }

               @Override
               protected void actionElement(int iID) {
                  return;
               }

               @Override
               protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive, boolean scrollableY) {
                  this.drawSliderBG_UpdateAnimation();

                  try {
                     CFG.game.getCiv(this.iCivID).getFlag().draw(oSB, this.getPosX() + CFG.PADDING + iTranslateX, this.getPosY() + this.getHeight() - CFG.PADDING * 2 - this.getSliderHeight() - (int)((float)CFG.TEXT_HEIGHT * 0.8F) / 2 - (int)((float)ImageManager.getImage(Images.flag_rect).getHeight() * this.getImageScale(ImageManager.getImage(Images.flag_rect).getHeight())) / 2 - CFG.game.getCiv(this.iCivID).getFlag().getHeight() + iTranslateY, (int)((float)ImageManager.getImage(Images.flag_rect).getWidth() * this.getImageScale(ImageManager.getImage(Images.flag_rect).getHeight())), (int)((float)ImageManager.getImage(Images.flag_rect).getHeight() * this.getImageScale(ImageManager.getImage(Images.flag_rect).getHeight())));
                     ImageManager.getImage(Images.flag_rect).draw(oSB, this.getPosX() + CFG.PADDING + iTranslateX, this.getPosY() + this.getHeight() - CFG.PADDING * 2 - this.getSliderHeight() - (int)((float)CFG.TEXT_HEIGHT * 0.8F) / 2 - (int)((float)ImageManager.getImage(Images.flag_rect).getHeight() * this.getImageScale(ImageManager.getImage(Images.flag_rect).getHeight())) / 2 - ImageManager.getImage(Images.flag_rect).getHeight() + iTranslateY, (int)((float)ImageManager.getImage(Images.flag_rect).getWidth() * this.getImageScale(ImageManager.getImage(Images.flag_rect).getHeight())), (int)((float)ImageManager.getImage(Images.flag_rect).getHeight() * this.getImageScale(ImageManager.getImage(Images.flag_rect).getHeight())));
                  } catch (IndexOutOfBoundsException var7) {
                  }

                  CFG.fontMain.getData().setScale(0.8F);
                  CFG.drawTextWithShadow(oSB, this.getText(), this.getPosX() + CFG.PADDING * 2 + (int)((float)ImageManager.getImage(Images.flag_rect).getWidth() * this.getImageScale(ImageManager.getImage(Images.flag_rect).getHeight())) + iTranslateX, this.getPosY() + this.getHeight() - CFG.PADDING * 2 - this.getSliderHeight() - (int)((float)CFG.TEXT_HEIGHT * 0.8F) + iTranslateY, this.getColor(isActive));
                  CFG.fontMain.getData().setScale(1.0F);
               }

               private final float getImageScale(int nImageHeight) {
                  return Math.min((float) CFG.TEXT_HEIGHT / (float) nImageHeight, 1.0F);
               }

               protected boolean getClickable() {
                  return false;
               }
            });

         }

         menuElements.add(new Button_New_Game_Players("" + ((Vassal_GameData)CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(tempMenuPosY)).iCivID, -1, 2, tY, ((int)((float)CFG.BUTTON_HEIGHT * 0.6F)), true) {
            protected int getPosX() {
               return 2 + Button_Diplomacy.iDiploWidth + Math.max(Menu_InGame_Tribute.this.getElementW() * 2 - Button_Diplomacy.iDiploWidth - CFG.PADDING * 2 - ((int)((float)CFG.BUTTON_HEIGHT * 0.6F) * 2), 0) + (CFG.PADDING * 2);
            }

            @Override
            protected boolean getClickable() {
               return super.getClickable()
               && !(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getVassal_AutonomyStatus(Integer.parseInt(this.getText())).getIndexAfter() == CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getVassal_AutonomyStatus(Integer.parseInt(this.getText())).getIndexOf());
            }

            protected void buildElementHover() {
               int iAutonomyID = CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getVassal_AutonomyStatus(Integer.parseInt(this.getText())).getIndexAfter();
               if (iAutonomyID == CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getVassal_AutonomyStatus(Integer.parseInt(this.getText())).getIndexOf()) {
                  List nElements = new ArrayList();
                  List nData = new ArrayList();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("VassalAutonomyNoChange"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE_HOVER));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover_v2(nElements);
                  return;
               }

               List nElements = new ArrayList();
               List nData = new ArrayList();
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("ChangeAutonomy") + ": ", CFG.COLOR_TEXT_MODIFIER_POSITIVE_HOVER));
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.gameAutonomy.lAutonomy.get(iAutonomyID).getName(), CFG.COLOR_INGAME_GOLD));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();

               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("DiplomacyPoints") + ": ", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(" " + (float)CFG.gameAutonomy.lAutonomy.get(iAutonomyID).getDiploCost() / 10.0F, CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getDiplomacyPoints() < (float)CFG.gameAutonomy.lAutonomy.get(iAutonomyID).getDiploCost() ? CFG.COLOR_TEXT_MODIFIER_NEGATIVE_HOVER : CFG.COLOR_INGAME_DIPLOMACY_POINTS_HOVER));
               nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_diplomacy_points, CFG.PADDING, 0));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();

               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("AdministrationCost") + ": ", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(" " + CFG.gameAutonomy.changeAutonomyCost(Integer.parseInt(this.getText())), CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoney() < CFG.gameAutonomy.changeAutonomyCost(Integer.parseInt(this.getText())) ? CFG.COLOR_TEXT_MODIFIER_NEGATIVE_HOVER : CFG.COLOR_INGAME_DIPLOMACY_POINTS_HOVER));
               nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_gold, CFG.PADDING, 0));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();

               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Stability") + ": ", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
               nData.add(new MenuElement_Hover_v2_Element_Type_Text("> 0.85%", CFG.game.getCiv(Integer.parseInt(this.getText())).getStability() < 0.85F ? CFG.COLOR_TEXT_MODIFIER_NEGATIVE_HOVER : CFG.COLOR_INGAME_DIPLOMACY_POINTS_HOVER));
               nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_popstability, CFG.PADDING, 0));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();

               nData.add(new MenuElement_Hover_v2_Element_Type_Space());
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();

               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("AutoJoinWars") + ": "));
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.gameAutonomy.lAutonomy.get(iAutonomyID).isAutoJoinWarPerms() ? CFG.langManager.get("Yes") : CFG.langManager.get("No"), CFG.COLOR_TEXT_MODIFIER_NEUTRAL2));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("HasIndependentMilitary") + ": "));
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.gameAutonomy.lAutonomy.get(iAutonomyID).isMilitaryControl() ? CFG.langManager.get("Yes") : CFG.langManager.get("No"), CFG.COLOR_TEXT_MODIFIER_NEUTRAL2));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("HasIndependentEconomy") + ": "));
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.gameAutonomy.lAutonomy.get(iAutonomyID).isEconomicControl() ? CFG.langManager.get("Yes") : CFG.langManager.get("No"), CFG.COLOR_TEXT_MODIFIER_NEUTRAL2));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("AutoAcceptsTrades") + ": "));
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.gameAutonomy.lAutonomy.get(iAutonomyID).isTradeIntegration() ? CFG.langManager.get("Yes") : CFG.langManager.get("No"), CFG.COLOR_TEXT_MODIFIER_NEUTRAL2));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();

               this.menuElementHover = new MenuElement_Hover_v2(nElements);
            }

            @Override
            protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
               ImageManager.getImage(Images.development).draw(oSB, this.getPosX() + this.getWidth()/2 - ImageManager.getImage(Images.development).getHeight()/2 + iTranslateX, this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(Images.development).getHeight() / 2 - ImageManager.getImage(Images.development).getHeight() + iTranslateY, ImageManager.getImage(Images.development).getWidth(), ImageManager.getImage(Images.development).getHeight());

               Rectangle clipBounds = new Rectangle((float)(this.getPosX() + this.getWidth()/2 + CFG.PADDING * 3 + iTranslateX), (float)(CFG.GAME_HEIGHT - this.getPosY() - iTranslateY), (float)(this.getWidth() - (ImageManager.getImage(Images.development).getWidth() + CFG.PADDING * 4)), (float)(-this.getHeight()));
               oSB.flush();
               ScissorStack.pushScissors(clipBounds);

               try {
                  oSB.flush();
                  ScissorStack.popScissors();
               } catch (IllegalStateException var7) {
               }
            }

            @Override
            protected void actionElement(int iID) {
               if (DiplomacyManager.changeAutonomyType(Integer.parseInt(this.getText()), CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getVassal_AutonomyStatus(Integer.parseInt(this.getText())).getIndexAfter())) {
                  CFG.toast.setInView(CFG.langManager.get("UpdatedAutonomy") + "!", CFG.COLOR_TEXT_MODIFIER_POSITIVE);
                  CFG.toast.setTimeInView(4500);
                  CFG.updateActiveCivInfo_InGame();
                  CFG.menuManager.updateInGame_TOP_All(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
                  Menu_InGame_Tribute.this.setVisible(false);
               }
            }
         });

         menuElements.add(new Button_New_Game_Players("" + ((Vassal_GameData)CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(tempMenuPosY)).iCivID, -1, 2, tY, ((int)((float)CFG.BUTTON_HEIGHT * 0.6F)), true) {
            protected int getPosX() {
               return 2 + Button_Diplomacy.iDiploWidth + Math.max(Menu_InGame_Tribute.this.getElementW() * 2 - Button_Diplomacy.iDiploWidth - CFG.PADDING * 2 - ((int)((float)CFG.BUTTON_HEIGHT * 0.6F)), 0) + (CFG.PADDING * 2);
            }

            @Override
            protected boolean getClickable() {
               return super.getClickable() && !(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getVassal_AutonomyStatus(Integer.parseInt(this.getText())).getIndexBefore() == CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getVassal_AutonomyStatus(Integer.parseInt(this.getText())).getIndexOf());
            }

            protected void buildElementHover() {
               int iAutonomyID = CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getVassal_AutonomyStatus(Integer.parseInt(this.getText())).getIndexBefore();
               if (iAutonomyID == CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getVassal_AutonomyStatus(Integer.parseInt(this.getText())).getIndexOf()) {
                  List nElements = new ArrayList();
                  List nData = new ArrayList();
                  nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("VassalAutonomyNoChange"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE_HOVER));
                  nElements.add(new MenuElement_Hover_v2_Element2(nData));
                  nData.clear();
                  this.menuElementHover = new MenuElement_Hover_v2(nElements);
                  return;
               }

               List nElements = new ArrayList();
               List nData = new ArrayList();
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("ChangeAutonomy") + ": ", CFG.COLOR_TEXT_MODIFIER_NEGATIVE2));
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.gameAutonomy.lAutonomy.get(iAutonomyID).getName(), CFG.COLOR_INGAME_GOLD));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();

               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("DiplomacyPoints") + ": ", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(" " + (float)CFG.gameAutonomy.lAutonomy.get(iAutonomyID).getDiploCost() / 10.0F, CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getDiplomacyPoints() < (float)CFG.gameAutonomy.lAutonomy.get(iAutonomyID).getDiploCost() ? CFG.COLOR_TEXT_MODIFIER_NEGATIVE_HOVER : CFG.COLOR_INGAME_DIPLOMACY_POINTS_HOVER));
               nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_diplomacy_points, CFG.PADDING, 0));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();

               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("AdministrationCost") + ": ", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(" " + CFG.gameAutonomy.changeAutonomyCost(Integer.parseInt(this.getText())), CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoney() < CFG.gameAutonomy.changeAutonomyCost(Integer.parseInt(this.getText())) ? CFG.COLOR_TEXT_MODIFIER_NEGATIVE_HOVER : CFG.COLOR_INGAME_DIPLOMACY_POINTS_HOVER));
               nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.top_gold, CFG.PADDING, 0));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();

               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Stability") + ": ", CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
               nData.add(new MenuElement_Hover_v2_Element_Type_Text("> 0.85%", CFG.game.getCiv(Integer.parseInt(this.getText())).getStability() < 0.85F ? CFG.COLOR_TEXT_MODIFIER_NEGATIVE_HOVER : CFG.COLOR_INGAME_DIPLOMACY_POINTS_HOVER));
               nData.add(new MenuElement_Hover_v2_Element_Type_Image(Images.diplo_popstability, CFG.PADDING, 0));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();

               nData.add(new MenuElement_Hover_v2_Element_Type_Space());
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();

               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("AutoJoinWars") + ": "));
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.gameAutonomy.lAutonomy.get(iAutonomyID).isAutoJoinWarPerms() ? CFG.langManager.get("Yes") : CFG.langManager.get("No"), CFG.COLOR_TEXT_MODIFIER_NEUTRAL2));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("HasIndependentMilitary") + ": "));
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.gameAutonomy.lAutonomy.get(iAutonomyID).isMilitaryControl() ? CFG.langManager.get("Yes") : CFG.langManager.get("No"), CFG.COLOR_TEXT_MODIFIER_NEUTRAL2));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("HasIndependentEconomy") + ": "));
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.gameAutonomy.lAutonomy.get(iAutonomyID).isEconomicControl() ? CFG.langManager.get("Yes") : CFG.langManager.get("No"), CFG.COLOR_TEXT_MODIFIER_NEUTRAL2));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("AutoAcceptsTrades") + ": "));
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.gameAutonomy.lAutonomy.get(iAutonomyID).isTradeIntegration() ? CFG.langManager.get("Yes") : CFG.langManager.get("No"), CFG.COLOR_TEXT_MODIFIER_NEUTRAL2));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();

               this.menuElementHover = new MenuElement_Hover_v2(nElements);
            }

            @Override
            protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
               ImageManager.getImage(Images.development_down).draw(oSB, this.getPosX() + this.getWidth()/2 - ImageManager.getImage(Images.development_down).getHeight()/2 + iTranslateX, this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(Images.development_down).getHeight() / 2 - ImageManager.getImage(Images.development_down).getHeight() + iTranslateY, ImageManager.getImage(Images.development_down).getWidth(), ImageManager.getImage(Images.development_down).getHeight());

               Rectangle clipBounds = new Rectangle((float)(this.getPosX() + this.getWidth()/2 + CFG.PADDING * 3 + iTranslateX), (float)(CFG.GAME_HEIGHT - this.getPosY() - iTranslateY), (float)(this.getWidth() - (ImageManager.getImage(Images.development_down).getWidth() + CFG.PADDING * 4)), (float)(-this.getHeight()));
               oSB.flush();
               ScissorStack.pushScissors(clipBounds);

               try {
                  oSB.flush();
                  ScissorStack.popScissors();
               } catch (IllegalStateException var7) {
               }
            }

            @Override
            protected void actionElement(int iID) {
               if (DiplomacyManager.changeAutonomyType(Integer.parseInt(this.getText()), CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getVassal_AutonomyStatus(Integer.parseInt(this.getText())).getIndexBefore())) {
                  CFG.toast.setInView(CFG.langManager.get("UpdatedAutonomy") + "!", CFG.COLOR_TEXT_MODIFIER_POSITIVE);
                  CFG.toast.setTimeInView(4500);
                  CFG.updateActiveCivInfo_InGame();
                  CFG.menuManager.updateInGame_TOP_All(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
                  Menu_InGame_Tribute.this.setVisible(false);
               }
            }
         });
         tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      }

      tY += CFG.PADDING;
      menuElements.add(new Button_FlagActionSliderStyle(CFG.langManager.get("Close"), -1, CFG.PADDING, tY, CFG.BUTTON_WIDTH, true) {
         protected int getWidth() {
            return Menu_InGame_Tribute.this.getElementW() * 2 - CFG.PADDING * 2;
         }

         protected void actionElement(int iID) {
            CFG.menuManager.setVisibleInGame_Tribute(false);
         }
      });
      tempMenuPosY = ImageManager.getImage(Images.top_left2).getHeight() + CFG.PADDING * 2 + CFG.BUTTON_HEIGHT * 3 / 5;
      this.initMenu(new SliderMenuTitle(CFG.langManager.get("Vassals"), CFG.BUTTON_HEIGHT * 3 / 5, true, true) {
         protected void draw(SpriteBatch oSB, int iTranslateX, int nPosX, int nPosY, int nWidth, boolean sliderMenuIsActive) {
            ImageManager.getImage(Images.dialog_title).draw2(oSB, nPosX - 2 + iTranslateX, nPosY - this.getHeight() - ImageManager.getImage(Images.dialog_title).getHeight(), nWidth + 4 - ImageManager.getImage(Images.dialog_title).getWidth(), this.getHeight());
            ImageManager.getImage(Images.dialog_title).draw2(oSB, nPosX + nWidth + 2 - ImageManager.getImage(Images.dialog_title).getWidth() + iTranslateX, nPosY - this.getHeight() - ImageManager.getImage(Images.dialog_title).getHeight(), ImageManager.getImage(Images.dialog_title).getWidth(), this.getHeight(), true, false);
            oSB.setColor(new Color(CFG.COLOR_TEXT_MODIFIER_POSITIVE_HOVER.r, CFG.COLOR_TEXT_MODIFIER_POSITIVE_HOVER.g, CFG.COLOR_TEXT_MODIFIER_POSITIVE_HOVER.b, 0.165F));
            ImageManager.getImage(Images.line_32_off1).draw(oSB, nPosX + iTranslateX, nPosY - this.getHeight() + 2 - ImageManager.getImage(Images.line_32_off1).getHeight(), nWidth, this.getHeight() - 2, false, true);
            oSB.setColor(new Color(CFG.COLOR_TEXT_MODIFIER_POSITIVE_HOVER.r, CFG.COLOR_TEXT_MODIFIER_POSITIVE_HOVER.g, CFG.COLOR_TEXT_MODIFIER_POSITIVE_HOVER.b, 0.375F));
            ImageManager.getImage(Images.gradient).draw(oSB, nPosX + iTranslateX, nPosY - this.getHeight() * 2 / 3 - ImageManager.getImage(Images.gradient).getHeight(), nWidth, this.getHeight() * 2 / 3, false, true);
            oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.65F));
            ImageManager.getImage(Images.gradient).draw(oSB, nPosX + iTranslateX, nPosY - CFG.PADDING - ImageManager.getImage(Images.gradient).getHeight(), nWidth, CFG.PADDING, false, true);
            oSB.setColor(CFG.COLOR_NEW_GAME_EDGE_LINE);
            ImageManager.getImage(Images.pix255_255_255).draw(oSB, nPosX + iTranslateX, nPosY - 1 - ImageManager.getImage(Images.pix255_255_255).getHeight(), nWidth, 1);
            oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.55F));
            ImageManager.getImage(Images.line_32_off1).draw(oSB, nPosX + iTranslateX, nPosY - 2 - ImageManager.getImage(Images.line_32_off1).getHeight(), nWidth, 1);
            oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.8F));
            ImageManager.getImage(Images.line_32_off1).draw(oSB, nPosX + iTranslateX, nPosY - 1 - ImageManager.getImage(Images.line_32_off1).getHeight(), nWidth, 1);
            oSB.setColor(new Color(CFG.COLOR_FLAG_FRAME.r, CFG.COLOR_FLAG_FRAME.g, CFG.COLOR_FLAG_FRAME.b, 0.45F));
            ImageManager.getImage(Images.slider_gradient).draw(oSB, nPosX + iTranslateX, nPosY - 1 - ImageManager.getImage(Images.slider_gradient).getHeight(), nWidth / 2, 1);
            ImageManager.getImage(Images.slider_gradient).draw(oSB, nPosX + nWidth - nWidth / 2 + iTranslateX, nPosY - 1 - ImageManager.getImage(Images.slider_gradient).getHeight(), nWidth / 2, 1, true, false);
            oSB.setColor(Color.WHITE);
            ImageManager.getImage(Images.diplo_lord).draw(oSB, nPosX + (int)((float)nWidth - ((float)this.getTextWidth() * 0.8F + (float)ImageManager.getImage(Images.diplo_lord).getWidth() + (float)CFG.PADDING)) / 2 + iTranslateX, Menu_InGame_Tribute.this.getPosY() - this.getHeight() / 2 - ImageManager.getImage(Images.diplo_lord).getHeight() / 2);
            CFG.fontMain.getData().setScale(0.8F);
            CFG.drawText(oSB, this.getText(), nPosX + (int)((float)nWidth - ((float)this.getTextWidth() * 0.8F + (float)ImageManager.getImage(Images.diplo_lord).getWidth() + (float)CFG.PADDING)) / 2 + ImageManager.getImage(Images.diplo_lord).getWidth() + CFG.PADDING + iTranslateX, 2 + nPosY - this.getHeight() + (int)((float)this.getHeight() - (float)this.getTextHeight() * 0.8F) / 2, Color.WHITE);
            CFG.fontMain.getData().setScale(1.0F);
         }
      }, CFG.GAME_WIDTH / 2 - tempWidth / 2, tempMenuPosY, tempWidth, ((MenuElement)menuElements.get(menuElements.size() - 1)).getPosY() + ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING + tempMenuPosY > CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING * 2 ? Math.max(CFG.GAME_HEIGHT - CFG.BUTTON_HEIGHT - CFG.PADDING * 2 - tempMenuPosY, (CFG.TEXT_HEIGHT + CFG.PADDING * 2) * 6) : ((MenuElement)menuElements.get(menuElements.size() - 1)).getPosY() + ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING, menuElements, true, true);
      this.updateLanguage();

      for(int i = 1; i < this.getMenuElementsSize() - 1; ++i) {
         this.getMenuElement(i).setCurrent(this.getMenuElement(i).getCurrent());
      }

      Menu_InGame_OfferAlliance.lTime = System.currentTimeMillis();
      this.updateIncomeFromVassals();
   }

   protected void updateLanguage() {
   }

   protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
      if (Menu_InGame_OfferAlliance.lTime + 200L >= System.currentTimeMillis()) {
         Rectangle clipBounds = new Rectangle((float)(this.getPosX() - 2), (float)(CFG.GAME_HEIGHT - this.getPosY()), (float)(this.getWidth() + 4), (float)(-((int)((float)(this.getHeight() + CFG.PADDING) * ((float)(System.currentTimeMillis() - Menu_InGame_OfferAlliance.lTime) / 200.0F)))));
         oSB.flush();
         ScissorStack.pushScissors(clipBounds);
         oSB.setColor(Color.WHITE);
         ImageManager.getImage(Images.new_game_top_edge).draw2(oSB, this.getPosX() - 2 + iTranslateX, this.getPosY() - ImageManager.getImage(Images.new_game_top_edge).getHeight() + iTranslateY, this.getWidth() - ImageManager.getImage(Images.new_game_top_edge).getWidth() + 4, this.getHeight() + CFG.PADDING, false, true);
         ImageManager.getImage(Images.new_game_top_edge).draw2(oSB, this.getPosX() + 2 + this.getWidth() - ImageManager.getImage(Images.new_game_top_edge).getWidth() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.new_game_top_edge).getHeight() + iTranslateY, ImageManager.getImage(Images.new_game_top_edge).getWidth(), this.getHeight() + CFG.PADDING, true, true);
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.45F));
         ImageManager.getImage(Images.gradient).draw(oSB, this.getPosX() + 2 + iTranslateX, this.getPosY() - ImageManager.getImage(Images.gradient).getHeight() + iTranslateY, this.getWidth() - 4, this.getHeight() / 4);
         ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + 2 + iTranslateX, this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() + iTranslateY, this.getWidth() - 4, 1);
         oSB.setColor(Color.WHITE);
         this.drawMenu(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
         oSB.setColor(Color.WHITE);
         oSB.setColor(Color.WHITE);
         CFG.setRender_3(true);
         this.endClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      } else {
         oSB.setColor(Color.WHITE);
         ImageManager.getImage(Images.new_game_top_edge).draw2(oSB, this.getPosX() - 2 + iTranslateX, this.getPosY() - ImageManager.getImage(Images.new_game_top_edge).getHeight() + iTranslateY, this.getWidth() - ImageManager.getImage(Images.new_game_top_edge).getWidth() + 4, this.getHeight() + CFG.PADDING, false, true);
         ImageManager.getImage(Images.new_game_top_edge).draw2(oSB, this.getPosX() + 2 + this.getWidth() - ImageManager.getImage(Images.new_game_top_edge).getWidth() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.new_game_top_edge).getHeight() + iTranslateY, ImageManager.getImage(Images.new_game_top_edge).getWidth(), this.getHeight() + CFG.PADDING, true, true);
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.45F));
         ImageManager.getImage(Images.gradient).draw(oSB, this.getPosX() + 2 + iTranslateX, this.getPosY() - ImageManager.getImage(Images.gradient).getHeight() + iTranslateY, this.getWidth() - 4, this.getHeight() / 4);
         ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + 2 + iTranslateX, this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() + iTranslateY, this.getWidth() - 4, 1);
         oSB.setColor(Color.WHITE);
         this.beginClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
         this.drawMenu(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
         oSB.setColor(Color.WHITE);
         this.endClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      }

   }

   protected void drawScrollPos(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
      if (sliderMenuIsActive) {
         super.drawScrollPos(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      }

   }

   protected final void updateIncomeFromVassals() {
      int tIncome = 0;

      for(int i = 0; i < CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.size(); ++i) {
         tIncome = (int)((float)tIncome + CFG.game_NextTurnUpdate.getIncome_Vassals(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID(), ((Vassal_GameData)CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.lVassals.get(i)).iCivID));
      }

      this.getMenuElement(0).setMin(tIncome);
   }

   protected final void actionElement(int iID) {
      try {
         this.getMenuElement(iID).actionElement(iID);
      } catch (IndexOutOfBoundsException var3) {
         this.setVisible(false);
      } catch (NullPointerException var4) {
         this.setVisible(false);
      }

   }

   protected final int getW() {
      return this.getWidth() - 4;
   }

   protected final int getElementW() {
      return this.getW() / 2;
   }

   protected void setVisible(boolean visible) {
      super.setVisible(visible);
      if (!visible) {
         for(int i = 0; i < this.getMenuElementsSize(); ++i) {
            this.getMenuElement(i).setVisible(false);
         }
      }

   }
}
