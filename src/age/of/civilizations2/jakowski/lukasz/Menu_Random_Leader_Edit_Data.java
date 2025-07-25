package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

class Menu_Random_Leader_Edit_Data extends SliderMenu {
   private String sName;
   private String sImage;
   private String sBorn;

   protected Menu_Random_Leader_Edit_Data() {
      super();
      final int tempW = (int)(CFG.CIV_INFO_MENU_WIDTH * 1.5f);
      final int tempElemH = CFG.BUTTON_HEIGHT;
      final List<MenuElement> menuElements = new ArrayList<MenuElement>();
      int tY = CFG.PADDING;

      menuElements.add(new Button_New_Game_Players_Special(CFG.leader_Random_GameData.getLeaderOfCiv().getName(), CFG.PADDING * 2, CFG.PADDING + 2, tY, tempW - CFG.PADDING * 2 - 2, true) {
         protected String getTextToDraw() {
            return Menu_Random_Leader_Edit_Data.this.sName + ": " + super.getText();
         }
      });
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_New_Game_Players_Special(CFG.leader_Random_GameData.getLeaderOfCiv().getImage(), CFG.PADDING * 2, CFG.PADDING + 2, tY, tempW - CFG.PADDING * 2 - 2, true) {
         protected String getTextToDraw() {
            return Menu_Random_Leader_Edit_Data.this.sImage + ": " + super.getText();
         }
      });
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_New_Game_Players_Special(CFG.leader_Random_GameData.getLeaderOfCiv().getDay() + " " + Game_Calendar.getMonthName(CFG.leader_Random_GameData.getLeaderOfCiv().getMonth()) + " " + CFG.gameAges.getYear(CFG.leader_Random_GameData.getLeaderOfCiv().getYear()), CFG.PADDING * 2, CFG.PADDING + 2, tY, tempW - CFG.PADDING * 2 - 2, true) {
         protected String getTextToDraw() {
            return Menu_Random_Leader_Edit_Data.this.sBorn + ": " + super.getText();
         }

          protected void buildElementHover() {
              ArrayList<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
              ArrayList<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();

              nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("InOfficeTooltip1"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
              nElements.add(new MenuElement_Hover_v2_Element2(nData));
              nData.clear();

              nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("InOfficeTooltip2"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
              nElements.add(new MenuElement_Hover_v2_Element2(nData));
              nData.clear();

              this.menuElementHover = new MenuElement_Hover_v2(nElements);
          }
      });
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;

      menuElements.add(new Button_NewGameStyle_Left("-", -1, CFG.PADDING, tY, (int)((float)CFG.BUTTON_HEIGHT * 0.8F), (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      menuElements.add(new Button_NewGameStyle_Middle((String)null, -1, CFG.PADDING + (int)((float)CFG.BUTTON_HEIGHT * 0.8F), tY, tempW - CFG.PADDING * 2 - (int)((float)CFG.BUTTON_HEIGHT * 0.8F) * 2, (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      menuElements.add(new Button_NewGameStyle_Right("+", -1, CFG.PADDING + (int)((float)CFG.BUTTON_HEIGHT * 0.8F) + (tempW - CFG.PADDING * 2 - (int)((float)CFG.BUTTON_HEIGHT * 0.8F) * 2), tY, (int)((float)CFG.BUTTON_HEIGHT * 0.8F), (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_NewGameStyle_Left("-", -1, CFG.PADDING, tY, (int)((float)CFG.BUTTON_HEIGHT * 0.8F), (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      menuElements.add(new Button_NewGameStyle_Middle((String)null, -1, CFG.PADDING + (int)((float)CFG.BUTTON_HEIGHT * 0.8F), tY, tempW - CFG.PADDING * 2 - (int)((float)CFG.BUTTON_HEIGHT * 0.8F) * 2, (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      menuElements.add(new Button_NewGameStyle_Right("+", -1, CFG.PADDING + (int)((float)CFG.BUTTON_HEIGHT * 0.8F) + (tempW - CFG.PADDING * 2 - (int)((float)CFG.BUTTON_HEIGHT * 0.8F) * 2), tY, (int)((float)CFG.BUTTON_HEIGHT * 0.8F), (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_NewGameStyle_Left("-", -1, CFG.PADDING, tY, (int)((float)CFG.BUTTON_HEIGHT * 0.8F), (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      menuElements.add(new Button_NewGameStyle_Middle((String)null, -1, CFG.PADDING + (int)((float)CFG.BUTTON_HEIGHT * 0.8F), tY, tempW - CFG.PADDING * 2 - (int)((float)CFG.BUTTON_HEIGHT * 0.8F) * 2, (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      menuElements.add(new Button_NewGameStyle_Right("+", -1, CFG.PADDING + (int)((float)CFG.BUTTON_HEIGHT * 0.8F) + (tempW - CFG.PADDING * 2 - (int)((float)CFG.BUTTON_HEIGHT * 0.8F) * 2), tY, (int)((float)CFG.BUTTON_HEIGHT * 0.8F), (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_NewGameStyle_Left("-", -1, CFG.PADDING, tY, (int)((float)CFG.BUTTON_HEIGHT * 0.8F), (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      menuElements.add(new Button_NewGameStyle_Middle((String)null, -1, CFG.PADDING + (int)((float)CFG.BUTTON_HEIGHT * 0.8F), tY, tempW - CFG.PADDING * 2 - (int)((float)CFG.BUTTON_HEIGHT * 0.8F) * 2, (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      menuElements.add(new Button_NewGameStyle_Right("+", -1, CFG.PADDING + (int)((float)CFG.BUTTON_HEIGHT * 0.8F) + (tempW - CFG.PADDING * 2 - (int)((float)CFG.BUTTON_HEIGHT * 0.8F) * 2), tY, (int)((float)CFG.BUTTON_HEIGHT * 0.8F), (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_NewGameStyle_Left("-", -1, CFG.PADDING, tY, (int)((float)CFG.BUTTON_HEIGHT * 0.8F), (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      menuElements.add(new Button_NewGameStyle_Middle((String)null, -1, CFG.PADDING + (int)((float)CFG.BUTTON_HEIGHT * 0.8F), tY, tempW - CFG.PADDING * 2 - (int)((float)CFG.BUTTON_HEIGHT * 0.8F) * 2, (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      menuElements.add(new Button_NewGameStyle_Right("+", -1, CFG.PADDING + (int)((float)CFG.BUTTON_HEIGHT * 0.8F) + (tempW - CFG.PADDING * 2 - (int)((float)CFG.BUTTON_HEIGHT * 0.8F) * 2), tY, (int)((float)CFG.BUTTON_HEIGHT * 0.8F), (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_NewGameStyle_Left("-", -1, CFG.PADDING, tY, (int)((float)CFG.BUTTON_HEIGHT * 0.8F), (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      menuElements.add(new Button_NewGameStyle_Middle((String)null, -1, CFG.PADDING + (int)((float)CFG.BUTTON_HEIGHT * 0.8F), tY, tempW - CFG.PADDING * 2 - (int)((float)CFG.BUTTON_HEIGHT * 0.8F) * 2, (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      menuElements.add(new Button_NewGameStyle_Right("+", -1, CFG.PADDING + (int)((float)CFG.BUTTON_HEIGHT * 0.8F) + (tempW - CFG.PADDING * 2 - (int)((float)CFG.BUTTON_HEIGHT * 0.8F) * 2), tY, (int)((float)CFG.BUTTON_HEIGHT * 0.8F), (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_NewGameStyle_Left("-", -1, CFG.PADDING, tY, (int)((float)CFG.BUTTON_HEIGHT * 0.8F), (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      menuElements.add(new Button_NewGameStyle_Middle((String)null, -1, CFG.PADDING + (int)((float)CFG.BUTTON_HEIGHT * 0.8F), tY, tempW - CFG.PADDING * 2 - (int)((float)CFG.BUTTON_HEIGHT * 0.8F) * 2, (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      menuElements.add(new Button_NewGameStyle_Right("+", -1, CFG.PADDING + (int)((float)CFG.BUTTON_HEIGHT * 0.8F) + (tempW - CFG.PADDING * 2 - (int)((float)CFG.BUTTON_HEIGHT * 0.8F) * 2), tY, (int)((float)CFG.BUTTON_HEIGHT * 0.8F), (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_NewGameStyle_Left("-", -1, CFG.PADDING, tY, (int)((float)CFG.BUTTON_HEIGHT * 0.8F), (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      menuElements.add(new Button_NewGameStyle_Middle((String)null, -1, CFG.PADDING + (int)((float)CFG.BUTTON_HEIGHT * 0.8F), tY, tempW - CFG.PADDING * 2 - (int)((float)CFG.BUTTON_HEIGHT * 0.8F) * 2, (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      menuElements.add(new Button_NewGameStyle_Right("+", -1, CFG.PADDING + (int)((float)CFG.BUTTON_HEIGHT * 0.8F) + (tempW - CFG.PADDING * 2 - (int)((float)CFG.BUTTON_HEIGHT * 0.8F) * 2), tY, (int)((float)CFG.BUTTON_HEIGHT * 0.8F), (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_NewGameStyle_Left("-", -1, CFG.PADDING, tY, (int)((float)CFG.BUTTON_HEIGHT * 0.8F), (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      menuElements.add(new Button_NewGameStyle_Middle((String)null, -1, CFG.PADDING + (int)((float)CFG.BUTTON_HEIGHT * 0.8F), tY, tempW - CFG.PADDING * 2 - (int)((float)CFG.BUTTON_HEIGHT * 0.8F) * 2, (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      menuElements.add(new Button_NewGameStyle_Right("+", -1, CFG.PADDING + (int)((float)CFG.BUTTON_HEIGHT * 0.8F) + (tempW - CFG.PADDING * 2 - (int)((float)CFG.BUTTON_HEIGHT * 0.8F) * 2), tY, (int)((float)CFG.BUTTON_HEIGHT * 0.8F), (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_NewGameStyle_Left("-", -1, CFG.PADDING, tY, (int)((float)CFG.BUTTON_HEIGHT * 0.8F), (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      menuElements.add(new Button_NewGameStyle_Middle((String)null, -1, CFG.PADDING + (int)((float)CFG.BUTTON_HEIGHT * 0.8F), tY, tempW - CFG.PADDING * 2 - (int)((float)CFG.BUTTON_HEIGHT * 0.8F) * 2, (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      menuElements.add(new Button_NewGameStyle_Right("+", -1, CFG.PADDING + (int)((float)CFG.BUTTON_HEIGHT * 0.8F) + (tempW - CFG.PADDING * 2 - (int)((float)CFG.BUTTON_HEIGHT * 0.8F) * 2), tY, (int)((float)CFG.BUTTON_HEIGHT * 0.8F), (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_NewGameStyle((String)null, -1, CFG.PADDING, tY, tempW - CFG.PADDING * 2 - 2, (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
      int var10000 = tY + ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      this.initMenu(new SliderMenuTitle((String)null, CFG.BUTTON_HEIGHT * 3 / 4, false, false) {
         protected void draw(SpriteBatch oSB, int iTranslateX, int nPosX, int nPosY, int nWidth, boolean sliderMenuIsActive) {
            ImageManager.getImage(Images.new_game_top_edge_title).draw2(oSB, Menu_Random_Leader_Edit_Data.this.getPosX() - 2 + iTranslateX, Menu_Random_Leader_Edit_Data.this.getPosY() - ImageManager.getImage(Images.new_game_top_edge_title).getHeight() - this.getHeight(), Menu_Random_Leader_Edit_Data.this.getWidth() + 2, this.getHeight(), true, false);
            oSB.setColor(new Color(CFG.COLOR_GRADIENT_TITLE_BLUE.r, CFG.COLOR_GRADIENT_TITLE_BLUE.g, CFG.COLOR_GRADIENT_TITLE_BLUE.b, 0.425F));
            ImageManager.getImage(Images.gradient).draw(oSB, Menu_Random_Leader_Edit_Data.this.getPosX() + iTranslateX, Menu_Random_Leader_Edit_Data.this.getPosY() - ImageManager.getImage(Images.gradient).getHeight() - this.getHeight() * 3 / 4, Menu_Random_Leader_Edit_Data.this.getWidth(), this.getHeight() * 3 / 4, false, true);
            oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.65F));
            ImageManager.getImage(Images.gradient).draw(oSB, Menu_Random_Leader_Edit_Data.this.getPosX() + iTranslateX, Menu_Random_Leader_Edit_Data.this.getPosY() - ImageManager.getImage(Images.gradient).getHeight() - CFG.PADDING, Menu_Random_Leader_Edit_Data.this.getWidth(), CFG.PADDING, false, true);
            oSB.setColor(new Color(0.451F, 0.329F, 0.11F, 1.0F));
            ImageManager.getImage(Images.pix255_255_255).draw(oSB, Menu_Random_Leader_Edit_Data.this.getPosX() + iTranslateX, Menu_Random_Leader_Edit_Data.this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight(), Menu_Random_Leader_Edit_Data.this.getWidth());
            oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.9F));
            ImageManager.getImage(Images.line_32_off1).draw(oSB, Menu_Random_Leader_Edit_Data.this.getPosX() + iTranslateX, Menu_Random_Leader_Edit_Data.this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() - ImageManager.getImage(Images.line_32_off1).getHeight(), Menu_Random_Leader_Edit_Data.this.getWidth(), 1);
            oSB.setColor(Color.WHITE);
            CFG.fontMain.getData().setScale(0.75F);
            CFG.drawText(oSB, this.getText(), nPosX + nWidth / 2 - (int)((float)this.getTextWidth() * 0.75F / 2.0F) + iTranslateX, nPosY - this.getHeight() + this.getHeight() / 2 + 1 - (int)((float)this.getTextHeight() * 0.75F / 2.0F), CFG.COLOR_TEXT_MODIFIER_NEUTRAL);
            CFG.fontMain.getData().setScale(1.0F);
         }
      }, 0, CFG.BUTTON_HEIGHT * 2 + CFG.PADDING * 4 + CFG.PADDING * 2 + CFG.BUTTON_HEIGHT / 4, tempW, CFG.GAME_HEIGHT - (CFG.PADDING * 2 + CFG.BUTTON_HEIGHT * 2) - CFG.BUTTON_HEIGHT, menuElements);
      this.updateLanguage();
   }

   protected void updateLanguage() {
      this.sName = CFG.langManager.get("Name");
      this.sImage = CFG.langManager.get("ImageName");
      this.sBorn = CFG.langManager.get("InOffice");
      this.getTitle().setText(CFG.langManager.get("Leader"));
      this.getMenuElement(2).setText(Game_Calendar.currentDay + " " + Game_Calendar.getMonthName(Game_Calendar.currentMonth) + " " + CFG.gameAges.getYear(Game_Calendar.currentYear));
      this.getMenuElement(4).setText(CFG.langManager.get("PopulationGrowthModifier") + ": " + (CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_PopGrowth > 0.0F ? "+" : "") + (int)(CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_PopGrowth * 100.0F) + "%");
      this.getMenuElement(7).setText(CFG.langManager.get("EconomyGrowthModifier") + ": " + (CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_EconomyGrowth > 0.0F ? "+" : "") + (int)(CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_EconomyGrowth * 100.0F) + "%");
      this.getMenuElement(10).setText(CFG.langManager.get("IncomeTaxation") + ": " + (CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_IncomeTaxation > 0.0F ? "+" : "") + (int)(CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_IncomeTaxation * 100.0F) + "%");
      this.getMenuElement(13).setText(CFG.langManager.get("IncomeProduction") + ": " + (CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_IncomeProduction > 0.0F ? "+" : "") + (int)(CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_IncomeProduction * 100.0F) + "%");
      this.getMenuElement(16).setText(CFG.langManager.get("Administration") + ": " + (CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_Administration > 0.0F ? "+" : "") + (int)(CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_Administration * 100.0F) + "%");
      this.getMenuElement(19).setText(CFG.langManager.get("Research") + ": " + (CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_Research > 0.0F ? "+" : "") + (int)(CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_Research * 100.0F) + "%");
      this.getMenuElement(22).setText(CFG.langManager.get("MilitaryUpkeep") + ": " + (CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_MilitaryUpkeep > 0.0F ? "+" : "") + (int)(CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_MilitaryUpkeep * 100.0F) + "%");
      this.getMenuElement(25).setText(CFG.langManager.get("AttackBonus") + ": " + (CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_AttackBonus > 0.0F ? "+" : "") + (int)(CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_AttackBonus * 100.0F) + "%");
      this.getMenuElement(28).setText(CFG.langManager.get("DefenseBonus") + ": " + (CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_DefenseBonus > 0.0F ? "+" : "") + (int)(CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_DefenseBonus * 100.0F) + "%");
      this.getMenuElement(31).setText(CFG.langManager.get("MovementPoints") + ": " + (CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_MovementPoints > 0.0F ? "+" : "") + (int)(CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_MovementPoints * 100.0F) + "%");
      this.getMenuElement(33).setText(CFG.langManager.get("AppearsAtStart") + ": " + (CFG.leader_Random_GameData.getLeaderOfCiv().canAppearNaturally() ? CFG.langManager.get("Yes") : CFG.langManager.get("No")));
   }

   protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
      ImageManager.getImage(Images.new_game_top_edge_line).draw2(oSB, this.getPosX() - 2 + iTranslateX, this.getPosY() - ImageManager.getImage(Images.new_game_top_edge_line).getHeight() + iTranslateY, this.getWidth() + 2, this.getHeight(), true, true);
      super.beginClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      super.drawMenu(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      super.endClip(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      oSB.setColor(CFG.COLOR_CREATE_NEW_GAME_BOX_PLAYERS);
      ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() + this.getHeight(), this.getWidth());
      oSB.setColor(new Color(CFG.COLOR_FLAG_FRAME.r, CFG.COLOR_FLAG_FRAME.g, CFG.COLOR_FLAG_FRAME.b, 0.4F));
      ImageManager.getImage(Images.line_32_off1).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() - ImageManager.getImage(Images.line_32_off1).getHeight() + this.getHeight(), this.getWidth(), 1);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F));
      ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() - 2 + iTranslateX, this.getPosY() + this.getHeight(), this.getWidth() + 2);
      oSB.setColor(Color.WHITE);
   }

   protected void actionElement(int iID) {
      switch (iID) {
         case 0:
          case 1: {
            CFG.showKeyboard();
            return;
         }
          case 2: {
            CFG.menuManager.saveRandom_Leader_Edit_Data();
            CFG.backToMenu = Menu.eGAME_RANDOM_LEADERS_EDIT;
            CFG.menuManager.setViewID(Menu.eSCENARIO_AGE);
            CFG.menuManager.updateSelecetScenarioAge_Slider();
            return;
         }
          case 4:
          case 7:
          case 10:
          case 13:
          case 16:
          case 19:
          case 22:
          case 25:
          case 28:
          case 31: {
            CFG.toast.setInView(this.getMenuElement(iID).getText(), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
            return;
         }
         case 3: {
             if (AoCGame.isShiftDown) {
                 CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_PopGrowth -= 0.1f;
             } else {
                 CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_PopGrowth -= 0.01f;
             }
             this.updateLanguage();
             return;
          }
         case 5: {
             if (AoCGame.isShiftDown) {
                 CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_PopGrowth += 0.1f;
             } else {
                 CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_PopGrowth += 0.01f;
             }
             this.updateLanguage();
             return;
         }
         case 6: {
             if (AoCGame.isShiftDown) {
                 CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_EconomyGrowth -= 0.1f;
             } else {
                 CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_EconomyGrowth -= 0.01f;
             }
             this.updateLanguage();
             return;
         }
         case 8: {
             if (AoCGame.isShiftDown) {
                 CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_EconomyGrowth += 0.1f;
             } else {
                 CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_EconomyGrowth += 0.01f;
             }
             this.updateLanguage();
             return;
         }
         case 9: {
             if (AoCGame.isShiftDown) {
                 CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_IncomeTaxation -= 0.1f;
             } else {
                 CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_IncomeTaxation -= 0.01f;
             }
             this.updateLanguage();
             return;
         }
         case 11: {
             if (AoCGame.isShiftDown) {
                 CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_IncomeTaxation += 0.1f;
             } else {
                 CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_IncomeTaxation += 0.01f;
             }
             this.updateLanguage();
             return;
         }
         case 12: {
            if (AoCGame.isShiftDown) {
                CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_IncomeProduction -= 0.1f;
            } else {
                CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_IncomeProduction -= 0.01f;
            }
            this.updateLanguage();
            return;
         }
         case 14: {
             if (AoCGame.isShiftDown) {
                 CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_IncomeProduction += 0.1f;
             } else {
                 CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_IncomeProduction += 0.01f;
             }
             this.updateLanguage();
             return;
         }
         case 15: {
             if (AoCGame.isShiftDown) {
                 CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_Administration -= 0.1f;
             } else {
                 CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_Administration -= 0.01f;
             }
             this.updateLanguage();
             return;
         }
         case 17: {
             if (AoCGame.isShiftDown) {
                 CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_Administration += 0.1f;
             } else {
                 CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_Administration += 0.01f;
             }
             this.updateLanguage();
             return;
         }
         case 18: {
             if (AoCGame.isShiftDown) {
                 CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_Research -= 0.1f;
             } else {
                 CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_Research -= 0.01f;
             }
             this.updateLanguage();
             return;
         }
          case 20: {
              if (AoCGame.isShiftDown) {
                  CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_Research += 0.1f;
              } else {
                  CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_Research += 0.01f;
              }
              this.updateLanguage();
              return;
         }
         case 21: {
            if (AoCGame.isShiftDown) {
                CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_MilitaryUpkeep -= 0.1f;
            } else {
                CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_MilitaryUpkeep -= 0.01f;
            }
            this.updateLanguage();
            return;
         }
         case 23: {
             if (AoCGame.isShiftDown) {
                 CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_MilitaryUpkeep += 0.1f;
             } else {
                 CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_MilitaryUpkeep += 0.01f;
             }
             this.updateLanguage();
             return;
         }
         case 24: {
             if (AoCGame.isShiftDown) {
                 CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_AttackBonus -= 0.1f;
             } else {
                 CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_AttackBonus -= 0.01f;
             }
             this.updateLanguage();
             return;
         }
          case 26: {
              if (AoCGame.isShiftDown) {
                  CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_AttackBonus += 0.1f;
              } else {
                  CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_AttackBonus += 0.01f;
              }
              this.updateLanguage();
              return;
         }
         case 27: {
             if (AoCGame.isShiftDown) {
                 CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_DefenseBonus -= 0.1f;
             } else {
                 CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_DefenseBonus -= 0.01f;
             }
             this.updateLanguage();
             return;
         }
          case 29: {
              if (AoCGame.isShiftDown) {
                  CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_DefenseBonus += 0.1f;
              } else {
                  CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_DefenseBonus += 0.01f;
              }
              this.updateLanguage();
              return;
         }
         case 30: {
             if (AoCGame.isShiftDown) {
                 CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_MovementPoints -= 0.1f;
             } else {
                 CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_MovementPoints -= 0.01f;
             }
             this.updateLanguage();
             return;
         }
         case 32: {
             if (AoCGame.isShiftDown) {
                 CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_MovementPoints += 0.1f;
             } else {
                 CFG.leader_Random_GameData.getLeaderOfCiv().fModifier_MovementPoints += 0.01f;
             }
             this.updateLanguage();
             return;
         }
          case 33: {
              CFG.leader_Random_GameData.getLeaderOfCiv().setCanAppearNaturally(!CFG.leader_Random_GameData.getLeaderOfCiv().canAppearNaturally());
              this.updateLanguage();
              return;
          }
      }
   }
}
