package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;

import java.util.ArrayList;
import java.util.List;

class Menu_ManageDiplomacy_Vassals_List extends SliderMenu {
   protected Menu_ManageDiplomacy_Vassals_List() {
      int tempW = CFG.CIV_INFO_MENU_WIDTH;
      int tempElemH = (int)((float)CFG.BUTTON_HEIGHT * 0.6F);
      final List<MenuElement> menuElements = new ArrayList<MenuElement>();
      if (CFG.MANAGE_DIPLOMACY_CUSTOMIZE_ALLIANCE_ID > 0) {
         int i = 1;

         for(int multiplePosY = 0; i < CFG.game.getCivsSize(); ++i) {
            if (i != CFG.MANAGE_DIPLOMACY_CUSTOMIZE_ALLIANCE_ID && CFG.MANAGE_DIPLOMACY_CUSTOMIZE_ALLIANCE_ID == CFG.game.getCiv(i).getPuppetOfCivID()) {
               menuElements.add(new Button_New_Game_Players_CivID_LEFT(i, CFG.game.getCiv(i).getCivName(), -1, CFG.PADDING, CFG.PADDING * (multiplePosY + 1) + tempElemH * multiplePosY, (tempW - CFG.PADDING * 2 - ((int)((float)CFG.BUTTON_HEIGHT * 0.6F) * 2)), true));

               //new vassal autonomy button
               //pass civid through final variable
               int finalI = i;
               menuElements.add(new Button_New_Game_Players("", -1, tempW - CFG.PADDING - (2 * (int)((float)CFG.BUTTON_HEIGHT * 0.6F)), CFG.PADDING * (multiplePosY + 1) + tempElemH * multiplePosY, ((int)((float)CFG.BUTTON_HEIGHT * 0.6F)), true) {
                  protected void buildElementHover() {
                     ArrayList<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
                     ArrayList<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("ChangeAutonomyLevel"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                     nElements.add(new MenuElement_Hover_v2_Element2(nData));
                     nData.clear();

                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Autonomy") + ": ", CFG.COLOR_TEXT_NUM_OF_PROVINCES));
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.game.getCiv(CFG.game.getCiv(finalI).getPuppetOfCivID()).getVassal_AutonomyStatus(finalI).getName(), CFG.COLOR_INGAME_GOLD));
                     nElements.add(new MenuElement_Hover_v2_Element2(nData));
                     nData.clear();

                     this.menuElementHover = new MenuElement_Hover_v2(nElements);
                  }

                  @Override
                  protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
                     ImageManager.getImage(Images.diplo_vassal).draw(oSB, this.getPosX() + this.getWidth()/2 - ImageManager.getImage(Images.diplo_vassal).getHeight()/2 + iTranslateX, this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(Images.diplo_vassal).getHeight() / 2 - ImageManager.getImage(Images.diplo_vassal).getHeight() + iTranslateY, ImageManager.getImage(Images.diplo_vassal).getWidth(), ImageManager.getImage(Images.diplo_vassal).getHeight());

                     Rectangle clipBounds = new Rectangle((float)(this.getPosX() + this.getWidth()/2 + CFG.PADDING * 3 + iTranslateX), (float)(CFG.GAME_HEIGHT - this.getPosY() - iTranslateY), (float)(this.getWidth() - (ImageManager.getImage(Images.diplo_vassal).getWidth() + CFG.PADDING * 4)), (float)(-this.getHeight()));
                     oSB.flush();
                     ScissorStack.pushScissors(clipBounds);

                     try {
                        oSB.flush();
                        ScissorStack.popScissors();
                     } catch (IllegalStateException var7) {
                     }
                  }
               });

               menuElements.add(new Button_New_Game_Players_Players_RIGHT("", -1, tempW - CFG.PADDING - (int)((float)CFG.BUTTON_HEIGHT * 0.6F), CFG.PADDING * (multiplePosY + 1) + tempElemH * multiplePosY, (int)((float)CFG.BUTTON_HEIGHT * 0.6F), true) {
                  protected void buildElementHover() {
                     ArrayList<MenuElement_Hover_v2_Element2> nElements = new ArrayList<MenuElement_Hover_v2_Element2>();
                     ArrayList<MenuElement_Hover_v2_Element_Type> nData = new ArrayList<MenuElement_Hover_v2_Element_Type>();
                     nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Delete"), CFG.COLOR_BUTTON_GAME_TEXT_ACTIVE));
                     nElements.add(new MenuElement_Hover_v2_Element2(nData));
                     nData.clear();
                     this.menuElementHover = new MenuElement_Hover_v2(nElements);
                  }
               });
               multiplePosY += 1;
            }
         }
      }

      this.initMenu(new SliderMenuTitle((String)null, CFG.BUTTON_HEIGHT * 3 / 4, false, false) {
         protected void draw(SpriteBatch oSB, int iTranslateX, int nPosX, int nPosY, int nWidth, boolean sliderMenuIsActive) {
            ImageManager.getImage(Images.new_game_top_edge_title).draw2(oSB, Menu_ManageDiplomacy_Vassals_List.this.getPosX() + iTranslateX, Menu_ManageDiplomacy_Vassals_List.this.getPosY() - ImageManager.getImage(Images.new_game_top_edge_title).getHeight() - this.getHeight(), Menu_ManageDiplomacy_Vassals_List.this.getWidth() + 2, this.getHeight(), true, false);
            oSB.setColor(new Color(0.011F, 0.014F, 0.019F, 0.25F));
            ImageManager.getImage(Images.gradient).draw(oSB, Menu_ManageDiplomacy_Vassals_List.this.getPosX() + iTranslateX, Menu_ManageDiplomacy_Vassals_List.this.getPosY() - ImageManager.getImage(Images.gradient).getHeight() - this.getHeight() * 3 / 4, Menu_ManageDiplomacy_Vassals_List.this.getWidth(), this.getHeight() * 3 / 4, false, true);
            oSB.setColor(new Color(0.451F, 0.329F, 0.11F, 1.0F));
            ImageManager.getImage(Images.pix255_255_255).draw(oSB, Menu_ManageDiplomacy_Vassals_List.this.getPosX() + iTranslateX, Menu_ManageDiplomacy_Vassals_List.this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight(), Menu_ManageDiplomacy_Vassals_List.this.getWidth());
            oSB.setColor(new Color(CFG.COLOR_FLAG_FRAME.r, CFG.COLOR_FLAG_FRAME.g, CFG.COLOR_FLAG_FRAME.b, 0.4F));
            ImageManager.getImage(Images.line_32_off1).draw(oSB, Menu_ManageDiplomacy_Vassals_List.this.getPosX() + iTranslateX, Menu_ManageDiplomacy_Vassals_List.this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() - ImageManager.getImage(Images.line_32_off1).getHeight(), Menu_ManageDiplomacy_Vassals_List.this.getWidth(), 1);
            oSB.setColor(Color.WHITE);

            try {
               CFG.game.getCiv(CFG.MANAGE_DIPLOMACY_CUSTOMIZE_ALLIANCE_ID).getFlag().draw(oSB, nPosX + CFG.PADDING * 2 + iTranslateX, nPosY - this.getHeight() + this.getHeight() / 2 + 1 - CFG.CIV_FLAG_HEIGHT / 2 - CFG.game.getCiv(CFG.MANAGE_DIPLOMACY_CUSTOMIZE_ALLIANCE_ID).getFlag().getHeight(), CFG.CIV_FLAG_WIDTH, CFG.CIV_FLAG_HEIGHT);
               ImageManager.getImage(Images.flag_rect).draw(oSB, nPosX + CFG.PADDING * 2 + iTranslateX, nPosY - this.getHeight() + this.getHeight() / 2 + 1 - CFG.CIV_FLAG_HEIGHT / 2);
            } catch (IndexOutOfBoundsException var8) {
            }

            CFG.fontMain.getData().setScale(0.75F);
            CFG.drawText(oSB, this.getText(), nPosX + nWidth / 2 - (int)((float)this.getTextWidth() * 0.75F / 2.0F) + iTranslateX, nPosY - this.getHeight() + this.getHeight() / 2 + 1 - (int)((float)this.getTextHeight() * 0.75F / 2.0F), CFG.COLOR_TEXT_MODIFIER_NEUTRAL);
            CFG.fontMain.getData().setScale(1.0F);
         }
      }, 0, CFG.BUTTON_HEIGHT * 2 + CFG.PADDING * 4 + CFG.PADDING * 2 + CFG.BUTTON_HEIGHT * 3 / 4, tempW, Math.min((tempElemH + CFG.PADDING) * (menuElements.size() / 3) + CFG.PADDING, CFG.GAME_HEIGHT - (CFG.BUTTON_HEIGHT * 2 + CFG.PADDING * 4 + CFG.PADDING * 2 + CFG.BUTTON_HEIGHT * 3 / 4) - CFG.BUTTON_HEIGHT - CFG.PADDING * 2), menuElements, false, true);
      this.updateLanguage();
   }

   protected void updateLanguage() {
      this.getTitle().setText(CFG.langManager.get("Vassals"));
   }

   protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
      ImageManager.getImage(Images.new_game_top_edge_line).draw2(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.new_game_top_edge_line).getHeight() + iTranslateY, this.getWidth() + 2, this.getHeight(), true, true);
      super.draw(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      oSB.setColor(CFG.COLOR_CREATE_NEW_GAME_BOX_PLAYERS);
      ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() + this.getHeight(), this.getWidth());
      oSB.setColor(new Color(CFG.COLOR_FLAG_FRAME.r, CFG.COLOR_FLAG_FRAME.g, CFG.COLOR_FLAG_FRAME.b, 0.4F));
      ImageManager.getImage(Images.line_32_off1).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() - ImageManager.getImage(Images.line_32_off1).getHeight() + this.getHeight(), this.getWidth(), 1);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.5F));
      ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight(), this.getWidth() + 2);
      oSB.setColor(Color.WHITE);
   }

   protected void drawScrollPos(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
      if (sliderMenuIsActive) {
         super.drawScrollPos(oSB, iTranslateX, iTranslateY, sliderMenuIsActive);
      }

   }

   protected void actionElement(int iID) {
      if (iID % 3 == 0) {
         this.centerVassal(iID / 3);
      } else if (iID % 3 == 1) {
         this.centerVassal(iID / 3);
         CFG.menuManager.rebuildManageDiplomacy_Vassals_Autonomy(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID());
         //CFG.menuManager.rebuildManageDiplomacy_Vassals_List();
      } else if (iID % 3 == 2) {
         this.removeVassal(iID / 3);
         CFG.menuManager.rebuildManageDiplomacy_Vassals_List();
      }
   }

   private final void removeVassal(int vassalID) {
      if (CFG.MANAGE_DIPLOMACY_CUSTOMIZE_ALLIANCE_ID > 0) {
         int i = 1;

         for(int foundVassals = 0; i < CFG.game.getCivsSize(); ++i) {
            if (i != CFG.MANAGE_DIPLOMACY_CUSTOMIZE_ALLIANCE_ID && CFG.MANAGE_DIPLOMACY_CUSTOMIZE_ALLIANCE_ID == CFG.game.getCiv(i).getPuppetOfCivID()) {
               if (foundVassals == vassalID) {
                  CFG.game.ressetVassal_CivID(i);
                  return;
               }

               ++foundVassals;
            }
         }
      }

   }

   private final void centerVassal(int vassalID) {
      if (CFG.MANAGE_DIPLOMACY_CUSTOMIZE_ALLIANCE_ID > 0) {
         int i = 1;
         for(int foundVassals = 0; i < CFG.game.getCivsSize(); ++i) {
            if (i != CFG.MANAGE_DIPLOMACY_CUSTOMIZE_ALLIANCE_ID && CFG.MANAGE_DIPLOMACY_CUSTOMIZE_ALLIANCE_ID == CFG.game.getCiv(i).getPuppetOfCivID()) {
               if (foundVassals == vassalID) {
                  CFG.game.setActiveProvinceID(CFG.game.getCiv(i).getCapitalProvinceID());
                  CFG.map.getMapCoordinates().centerToProvinceID(CFG.game.getCiv(i).getCapitalProvinceID());
                  return;
               }

               ++foundVassals;
            }
         }
      }

   }
}
