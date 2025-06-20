package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.util.ArrayList;
import java.util.List;

class Menu_Random_Leader_Edit_AIType extends SliderMenu {
   protected Menu_Random_Leader_Edit_AIType() {
      super();
      final int tempW = CFG.CIV_INFO_MENU_WIDTH;
      final int tempElemH = CFG.BUTTON_HEIGHT;
      final List<MenuElement> menuElements = new ArrayList<MenuElement>();
      int tY = CFG.PADDING;

      menuElements.add(new Button_New_Game_Players((String)null, -1, CFG.PADDING + 2, tY, tempW - CFG.PADDING * 2 - 2, true));
      tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;

      for(int i = 0; i < CFG.leader_Random_GameData.getAITypesSize(); ++i) {
         menuElements.add(new Button_NewGameStyle_Left(CFG.langManager.get(CFG.leader_Random_GameData.getAIType(i)), CFG.PADDING * 3, CFG.PADDING + 2, tY, tempW - 2 - CFG.PADDING * 2 - (int)((float)CFG.BUTTON_HEIGHT * 0.6F), (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true));
         menuElements.add(new Button_New_Game_Players_Players_RIGHT("", -1, tempW - 2 - CFG.PADDING - (int)((float)CFG.BUTTON_HEIGHT * 0.6F), tY, (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true) {
            protected void buildElementHover() {
               List<MenuElement_Hover_v2_Element2> nElements = new ArrayList<>();
               List<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<>();
               nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Delete"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
               nElements.add(new MenuElement_Hover_v2_Element2(nData));
               nData.clear();
               this.menuElementHover = new MenuElement_Hover_v2(nElements);
            }
         });
         tY += ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
      }

      this.initMenu(new SliderMenuTitle((String)null, CFG.BUTTON_HEIGHT * 3 / 4, false, false) {
         protected void draw(SpriteBatch oSB, int iTranslateX, int nPosX, int nPosY, int nWidth, boolean sliderMenuIsActive) {
            ImageManager.getImage(Images.new_game_top_edge_title).draw2(oSB, Menu_Random_Leader_Edit_AIType.this.getPosX() - 2 + iTranslateX, Menu_Random_Leader_Edit_AIType.this.getPosY() - ImageManager.getImage(Images.new_game_top_edge_title).getHeight() - this.getHeight(), Menu_Random_Leader_Edit_AIType.this.getWidth() + 2, this.getHeight(), false, false);
            oSB.setColor(new Color(CFG.COLOR_GRADIENT_TITLE_BLUE_LIGHT_ALLIANCE.r, CFG.COLOR_GRADIENT_TITLE_BLUE_LIGHT_ALLIANCE.g, CFG.COLOR_GRADIENT_TITLE_BLUE_LIGHT_ALLIANCE.b, 0.225F));
            ImageManager.getImage(Images.gradient).draw(oSB, Menu_Random_Leader_Edit_AIType.this.getPosX() + iTranslateX, Menu_Random_Leader_Edit_AIType.this.getPosY() - ImageManager.getImage(Images.gradient).getHeight() - this.getHeight() * 3 / 4, Menu_Random_Leader_Edit_AIType.this.getWidth(), this.getHeight() * 3 / 4, false, true);
            oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.65F));
            ImageManager.getImage(Images.gradient).draw(oSB, Menu_Random_Leader_Edit_AIType.this.getPosX() + iTranslateX, Menu_Random_Leader_Edit_AIType.this.getPosY() - ImageManager.getImage(Images.gradient).getHeight() - CFG.PADDING, Menu_Random_Leader_Edit_AIType.this.getWidth(), CFG.PADDING, false, true);
            oSB.setColor(new Color(0.451F, 0.329F, 0.11F, 1.0F));
            ImageManager.getImage(Images.pix255_255_255).draw(oSB, Menu_Random_Leader_Edit_AIType.this.getPosX() + iTranslateX, Menu_Random_Leader_Edit_AIType.this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight(), Menu_Random_Leader_Edit_AIType.this.getWidth());
            oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.9F));
            ImageManager.getImage(Images.line_32_off1).draw(oSB, Menu_Random_Leader_Edit_AIType.this.getPosX() + iTranslateX, Menu_Random_Leader_Edit_AIType.this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() - ImageManager.getImage(Images.line_32_off1).getHeight(), Menu_Random_Leader_Edit_AIType.this.getWidth(), 1);
            oSB.setColor(Color.WHITE);
            CFG.fontMain.getData().setScale(0.75F);
            CFG.drawText(oSB, this.getText(), nPosX + nWidth / 2 - (int)((float)this.getTextWidth() * 0.75F / 2.0F) + iTranslateX, nPosY - this.getHeight() + this.getHeight() / 2 + 1 - (int)((float)this.getTextHeight() * 0.75F / 2.0F), CFG.COLOR_TEXT_MODIFIER_NEUTRAL);
            CFG.fontMain.getData().setScale(1.0F);
         }
      }, CFG.GAME_WIDTH - (tempW * 2), CFG.BUTTON_HEIGHT * 2 + CFG.PADDING * 4 + CFG.PADDING * 2 + CFG.BUTTON_HEIGHT * 3 / 4, tempW, Math.min(((MenuElement)menuElements.get(menuElements.size() - 1)).getPosY() + ((MenuElement)menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING, CFG.GAME_HEIGHT - (CFG.BUTTON_HEIGHT * 2 + CFG.PADDING * 4 + CFG.PADDING * 2 + CFG.BUTTON_HEIGHT * 3 / 4) - CFG.BUTTON_HEIGHT - CFG.PADDING * 3), menuElements);
      this.updateLanguage();
   }

   protected void updateLanguage() {
      this.getMenuElement(0).setText(CFG.langManager.get("AddAIType"));
      this.getTitle().setText(CFG.langManager.get("AITypes"));
   }

   protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
      ImageManager.getImage(Images.new_game_top_edge_line).draw2(oSB, this.getPosX() - 2 + iTranslateX, this.getPosY() - ImageManager.getImage(Images.new_game_top_edge_line).getHeight() + iTranslateY, this.getWidth() + 2, this.getHeight(), false, true);
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
      CFG.menuManager.saveRandom_Leader_Edit_Data();
      if (iID == 0) {
         CFG.menuManager.setViewID(Menu.eGAME_RANDOM_LEADERS_EDIT_SELECT_AITYPE);
      }

      if ((iID - 1) % 2 == 1) {
         CFG.leader_Random_GameData.removeAIType((iID - 1) / 2);
         CFG.menuManager.rebuildRandom_Leaders_Edit_AIType();
      } else {
         CFG.toast.setInView(this.getMenuElement(iID).getText(), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
      }

   }

   protected void onBackPressed() {
   }
}
