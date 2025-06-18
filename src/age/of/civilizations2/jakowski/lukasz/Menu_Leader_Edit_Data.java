package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

class Menu_Leader_Edit_Data extends SliderMenu {
   private String sName;
   private String sImage;
   private String sBorn;
   private String sWiki;

   protected Menu_Leader_Edit_Data() {
      int tempW = (int)((float)CFG.CIV_INFO_MENU_WIDTH * 1.25F);
      int tempElemH = CFG.BUTTON_HEIGHT;
      List menuElements = new ArrayList();
      int tY = CFG.PADDING;
      menuElements.add(new Button_New_Game_Players_Special(CFG.leader_GameData.getLeaderOfCiv().getName(), CFG.PADDING * 2, CFG.PADDING + 2, tY, tempW - CFG.PADDING * 2 - 2, true) {
         protected String getTextToDraw() {
            return Menu_Leader_Edit_Data.this.sName + ": " + super.getText();
         }
      });
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_New_Game_Players_Special(CFG.leader_GameData.getLeaderOfCiv().getImage(), CFG.PADDING * 2, CFG.PADDING + 2, tY, tempW - CFG.PADDING * 2 - 2, true) {
         protected String getTextToDraw() {
            return Menu_Leader_Edit_Data.this.sImage + ": " + super.getText();
         }
      });
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_New_Game_Players_Special(CFG.leader_GameData.getLeaderOfCiv().getDay() + " " + Game_Calendar.getMonthName(CFG.leader_GameData.getLeaderOfCiv().getMonth()) + " " + CFG.gameAges.getYear(CFG.leader_GameData.getLeaderOfCiv().getYear()), CFG.PADDING * 2, CFG.PADDING + 2, tY, tempW - CFG.PADDING * 2 - 2, true) {
         protected String getTextToDraw() {
            return Menu_Leader_Edit_Data.this.sBorn + ": " + super.getText();
         }
      });
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      menuElements.add(new Button_New_Game_Players_Special(CFG.leader_GameData.getLeaderOfCiv().getWiki(), CFG.PADDING * 2, CFG.PADDING + 2, tY, tempW - CFG.PADDING * 2 - 2, true) {
         protected String getTextToDraw() {
            return Menu_Leader_Edit_Data.this.sWiki + ": " + super.getText();
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
      int var10000 = tY + ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      this.initMenu(new SliderMenuTitle((String)null, CFG.BUTTON_HEIGHT * 3 / 4, false, false) {
         protected void draw(SpriteBatch oSB, int iTranslateX, int nPosX, int nPosY, int nWidth, boolean sliderMenuIsActive) {
            ImageManager.getImage(Images.new_game_top_edge_title).draw2(oSB, Menu_Leader_Edit_Data.this.getPosX() - 2 + iTranslateX, Menu_Leader_Edit_Data.this.getPosY() - ImageManager.getImage(Images.new_game_top_edge_title).getHeight() - this.getHeight(), Menu_Leader_Edit_Data.this.getWidth() + 2, this.getHeight(), true, false);
            oSB.setColor(new Color(CFG.COLOR_GRADIENT_TITLE_BLUE.r, CFG.COLOR_GRADIENT_TITLE_BLUE.g, CFG.COLOR_GRADIENT_TITLE_BLUE.b, 0.425F));
            ImageManager.getImage(Images.gradient).draw(oSB, Menu_Leader_Edit_Data.this.getPosX() + iTranslateX, Menu_Leader_Edit_Data.this.getPosY() - ImageManager.getImage(Images.gradient).getHeight() - this.getHeight() * 3 / 4, Menu_Leader_Edit_Data.this.getWidth(), this.getHeight() * 3 / 4, false, true);
            oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.65F));
            ImageManager.getImage(Images.gradient).draw(oSB, Menu_Leader_Edit_Data.this.getPosX() + iTranslateX, Menu_Leader_Edit_Data.this.getPosY() - ImageManager.getImage(Images.gradient).getHeight() - CFG.PADDING, Menu_Leader_Edit_Data.this.getWidth(), CFG.PADDING, false, true);
            oSB.setColor(new Color(0.451F, 0.329F, 0.11F, 1.0F));
            ImageManager.getImage(Images.pix255_255_255).draw(oSB, Menu_Leader_Edit_Data.this.getPosX() + iTranslateX, Menu_Leader_Edit_Data.this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight(), Menu_Leader_Edit_Data.this.getWidth());
            oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.9F));
            ImageManager.getImage(Images.line_32_off1).draw(oSB, Menu_Leader_Edit_Data.this.getPosX() + iTranslateX, Menu_Leader_Edit_Data.this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() - ImageManager.getImage(Images.line_32_off1).getHeight(), Menu_Leader_Edit_Data.this.getWidth(), 1);
            oSB.setColor(Color.WHITE);
            CFG.fontMain.getData().setScale(0.75F);
            CFG.drawText(oSB, this.getText(), nPosX + nWidth / 2 - (int)((float)this.getTextWidth() * 0.75F / 2.0F) + iTranslateX, nPosY - this.getHeight() + this.getHeight() / 2 + 1 - (int)((float)this.getTextHeight() * 0.75F / 2.0F), CFG.COLOR_TEXT_MODIFIER_NEUTRAL);
            CFG.fontMain.getData().setScale(1.0F);
         }
      }, 0, CFG.BUTTON_HEIGHT * 2 + CFG.PADDING * 4 + CFG.PADDING * 2 + CFG.BUTTON_HEIGHT / 4, tempW, Math.min(((MenuElement)menuElements.get(menuElements.size() - 1)).getPosY() + ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING, CFG.GAME_HEIGHT - (CFG.BUTTON_HEIGHT * 2 + CFG.PADDING * 4 + CFG.PADDING * 2 + CFG.BUTTON_HEIGHT * 3 / 4) - CFG.BUTTON_HEIGHT - CFG.PADDING * 3), menuElements);
      this.updateLanguage();
   }

   protected void updateLanguage() {
      this.sName = CFG.langManager.get("Name");
      this.sImage = CFG.langManager.get("ImageName");
      this.sBorn = CFG.langManager.get("InOffice");
      this.sWiki = CFG.langManager.get("Wiki");
      this.getTitle().setText(CFG.langManager.get("Leader"));
      this.getMenuElement(2).setText(Game_Calendar.currentDay + " " + Game_Calendar.getMonthName(Game_Calendar.currentMonth) + " " + CFG.gameAges.getYear(Game_Calendar.currentYear));
      this.getMenuElement(5).setText(CFG.langManager.get("PopulationGrowthModifier") + ": " + (CFG.leader_GameData.getLeaderOfCiv().fModifier_PopGrowth > 0.0F ? "+" : "") + (int)(CFG.leader_GameData.getLeaderOfCiv().fModifier_PopGrowth * 100.0F) + "%");
      this.getMenuElement(8).setText(CFG.langManager.get("EconomyGrowthModifier") + ": " + (CFG.leader_GameData.getLeaderOfCiv().fModifier_EconomyGrowth > 0.0F ? "+" : "") + (int)(CFG.leader_GameData.getLeaderOfCiv().fModifier_EconomyGrowth * 100.0F) + "%");
      this.getMenuElement(11).setText(CFG.langManager.get("IncomeTaxation") + ": " + (CFG.leader_GameData.getLeaderOfCiv().fModifier_IncomeTaxation > 0.0F ? "+" : "") + (int)(CFG.leader_GameData.getLeaderOfCiv().fModifier_IncomeTaxation * 100.0F) + "%");
      this.getMenuElement(14).setText(CFG.langManager.get("IncomeProduction") + ": " + (CFG.leader_GameData.getLeaderOfCiv().fModifier_IncomeProduction > 0.0F ? "+" : "") + (int)(CFG.leader_GameData.getLeaderOfCiv().fModifier_IncomeProduction * 100.0F) + "%");
      this.getMenuElement(17).setText(CFG.langManager.get("Administration") + ": " + (CFG.leader_GameData.getLeaderOfCiv().fModifier_Administration > 0.0F ? "+" : "") + (int)(CFG.leader_GameData.getLeaderOfCiv().fModifier_Administration * 100.0F) + "%");
      this.getMenuElement(20).setText(CFG.langManager.get("Research") + ": " + (CFG.leader_GameData.getLeaderOfCiv().fModifier_Research > 0.0F ? "+" : "") + (int)(CFG.leader_GameData.getLeaderOfCiv().fModifier_Research * 100.0F) + "%");
      this.getMenuElement(23).setText(CFG.langManager.get("MilitaryUpkeep") + ": " + (CFG.leader_GameData.getLeaderOfCiv().fModifier_MilitaryUpkeep > 0.0F ? "+" : "") + (int)(CFG.leader_GameData.getLeaderOfCiv().fModifier_MilitaryUpkeep * 100.0F) + "%");
      this.getMenuElement(26).setText(CFG.langManager.get("AttackBonus") + ": " + (CFG.leader_GameData.getLeaderOfCiv().fModifier_AttackBonus > 0.0F ? "+" : "") + (int)(CFG.leader_GameData.getLeaderOfCiv().fModifier_AttackBonus * 100.0F) + "%");
      this.getMenuElement(29).setText(CFG.langManager.get("DefenseBonus") + ": " + (CFG.leader_GameData.getLeaderOfCiv().fModifier_DefenseBonus > 0.0F ? "+" : "") + (int)(CFG.leader_GameData.getLeaderOfCiv().fModifier_DefenseBonus * 100.0F) + "%");
      this.getMenuElement(32).setText(CFG.langManager.get("MovementPoints") + ": " + (CFG.leader_GameData.getLeaderOfCiv().fModifier_MovementPoints > 0.0F ? "+" : "") + (int)(CFG.leader_GameData.getLeaderOfCiv().fModifier_MovementPoints * 100.0F) + "%");
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

   @Override
   protected void actionElement(final int iID) {
      switch (iID) {
         case 0:
          case 1:
          case 3: {
            CFG.showKeyboard();
            return;
         }
          case 2: {
            CFG.menuManager.saveLeader_Edit_Data();
            CFG.backToMenu = Menu.eGAME_LEADERS_EDIT;
            CFG.menuManager.setViewID(Menu.eSCENARIO_AGE);
            CFG.menuManager.updateSelecetScenarioAge_Slider();
            return;
         }
          case 4: {
            final LeaderOfCiv_GameData leaderOfCiv = CFG.leader_GameData.getLeaderOfCiv();
            leaderOfCiv.fModifier_PopGrowth -= 0.01f;
            this.updateLanguage();
            return;
         }
          case 5:
          case 8:
          case 11:
          case 14:
          case 17:
          case 20:
          case 23:
          case 26:
          case 29:
          case 32: {
            CFG.toast.setInView(this.getMenuElement(iID).getText(), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
            return;
         }
         case 6: {
            final LeaderOfCiv_GameData leaderOfCiv2 = CFG.leader_GameData.getLeaderOfCiv();
            leaderOfCiv2.fModifier_PopGrowth += 0.01f;
            this.updateLanguage();
            return;
         }
         case 7: {
            final LeaderOfCiv_GameData leaderOfCiv3 = CFG.leader_GameData.getLeaderOfCiv();
            leaderOfCiv3.fModifier_EconomyGrowth -= 0.01f;
            this.updateLanguage();
            return;
         }
          case 9: {
            final LeaderOfCiv_GameData leaderOfCiv4 = CFG.leader_GameData.getLeaderOfCiv();
            leaderOfCiv4.fModifier_EconomyGrowth += 0.01f;
            this.updateLanguage();
            return;
         }
         case 10: {
            final LeaderOfCiv_GameData leaderOfCiv5 = CFG.leader_GameData.getLeaderOfCiv();
            leaderOfCiv5.fModifier_IncomeTaxation -= 0.01f;
            this.updateLanguage();
            return;
         }
          case 12: {
            final LeaderOfCiv_GameData leaderOfCiv6 = CFG.leader_GameData.getLeaderOfCiv();
            leaderOfCiv6.fModifier_IncomeTaxation += 0.01f;
            this.updateLanguage();
            return;
         }
         case 13: {
            final LeaderOfCiv_GameData leaderOfCiv7 = CFG.leader_GameData.getLeaderOfCiv();
            leaderOfCiv7.fModifier_IncomeProduction -= 0.01f;
            this.updateLanguage();
            return;
         }
          case 15: {
            final LeaderOfCiv_GameData leaderOfCiv8 = CFG.leader_GameData.getLeaderOfCiv();
            leaderOfCiv8.fModifier_IncomeProduction += 0.01f;
            this.updateLanguage();
            return;
         }
         case 16: {
            final LeaderOfCiv_GameData leaderOfCiv9 = CFG.leader_GameData.getLeaderOfCiv();
            leaderOfCiv9.fModifier_Administration -= 0.01f;
            this.updateLanguage();
            return;
         }
          case 18: {
            final LeaderOfCiv_GameData leaderOfCiv10 = CFG.leader_GameData.getLeaderOfCiv();
            leaderOfCiv10.fModifier_Administration += 0.01f;
            this.updateLanguage();
            return;
         }
         case 19: {
            final LeaderOfCiv_GameData leaderOfCiv11 = CFG.leader_GameData.getLeaderOfCiv();
            leaderOfCiv11.fModifier_Research -= 0.01f;
            this.updateLanguage();
            return;
         }
          case 21: {
            final LeaderOfCiv_GameData leaderOfCiv12 = CFG.leader_GameData.getLeaderOfCiv();
            leaderOfCiv12.fModifier_Research += 0.01f;
            this.updateLanguage();
            return;
         }
         case 22: {
            final LeaderOfCiv_GameData leaderOfCiv13 = CFG.leader_GameData.getLeaderOfCiv();
            leaderOfCiv13.fModifier_MilitaryUpkeep -= 0.01f;
            this.updateLanguage();
            return;
         }
          case 24: {
            final LeaderOfCiv_GameData leaderOfCiv14 = CFG.leader_GameData.getLeaderOfCiv();
            leaderOfCiv14.fModifier_MilitaryUpkeep += 0.01f;
            this.updateLanguage();
            return;
         }
         case 25: {
            final LeaderOfCiv_GameData leaderOfCiv15 = CFG.leader_GameData.getLeaderOfCiv();
            leaderOfCiv15.fModifier_AttackBonus -= 0.01f;
            this.updateLanguage();
            return;
         }
          case 27: {
            final LeaderOfCiv_GameData leaderOfCiv16 = CFG.leader_GameData.getLeaderOfCiv();
            leaderOfCiv16.fModifier_AttackBonus += 0.01f;
            this.updateLanguage();
            return;
         }
         case 28: {
            final LeaderOfCiv_GameData leaderOfCiv17 = CFG.leader_GameData.getLeaderOfCiv();
            leaderOfCiv17.fModifier_DefenseBonus -= 0.01f;
            this.updateLanguage();
            return;
         }
          case 30: {
            final LeaderOfCiv_GameData leaderOfCiv18 = CFG.leader_GameData.getLeaderOfCiv();
            leaderOfCiv18.fModifier_DefenseBonus += 0.01f;
            this.updateLanguage();
            return;
         }
         case 31: {
            final LeaderOfCiv_GameData leaderOfCiv19 = CFG.leader_GameData.getLeaderOfCiv();
            leaderOfCiv19.fModifier_MovementPoints -= 0.01f;
            this.updateLanguage();
            return;
         }
          case 33: {
            final LeaderOfCiv_GameData leaderOfCiv20 = CFG.leader_GameData.getLeaderOfCiv();
            leaderOfCiv20.fModifier_MovementPoints += 0.01f;
            this.updateLanguage();
         }
         default: {}
      }
   }

}
