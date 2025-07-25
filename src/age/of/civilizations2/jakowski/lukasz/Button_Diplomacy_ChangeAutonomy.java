package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class Button_Diplomacy_ChangeAutonomy extends Button_Statistics {
   private static final float FONT_SCALE = 0.7F;
   private static final float FONT_SCALE2 = 0.6F;
   private int iAutonomyID;
   private String sAgeName;
   private int iAgeNameWidth;
   private Image civFlag;

   protected Button_Diplomacy_ChangeAutonomy(int i, int iCivA, int nAutonomyID, int iPosX, int iPosY, int iWidth) {
      super(CFG.gameAutonomy.lAutonomy.get(nAutonomyID).getName(), 0, iPosX, iPosY, iWidth, CFG.isAndroid() ? (int)Math.max((float)CFG.BUTTON_HEIGHT * 0.6F, (float)(CFG.TEXT_HEIGHT + CFG.PADDING * 6)) : CFG.TEXT_HEIGHT + CFG.PADDING * 4, false);
      this.iAutonomyID = nAutonomyID;
      this.row = i % 2 == 0;
      this.sAgeName = CFG.gameAges.getAge(CFG.gameAutonomy.getAutonomy(this.iAutonomyID).getAge()).getName();
      CFG.glyphLayout.setText(CFG.fontMain, this.sAgeName);
      this.iAgeNameWidth = (int)(CFG.glyphLayout.width * 0.6F);
      this.loadFlag(CFG.ideologiesManager.getRealTag(CFG.game.getCiv(iCivA).getCivTag())); //change to load vassal flag later
   }

   protected Button_Diplomacy_ChangeAutonomy(int i, String civATag, int nAutonomyID, int iPosX, int iPosY, int iWidth) {
      super(CFG.gameAutonomy.lAutonomy.get(nAutonomyID).getName(), 0, iPosX, iPosY, iWidth, CFG.isAndroid() ? (int)Math.max((float)CFG.BUTTON_HEIGHT * 0.6F, (float)(CFG.TEXT_HEIGHT + CFG.PADDING * 6)) : CFG.TEXT_HEIGHT + CFG.PADDING * 4, false);
      this.iAutonomyID = nAutonomyID;
      this.row = i % 2 == 0;
      this.sAgeName = CFG.gameAges.getAge(CFG.gameAutonomy.getAutonomy(this.iAutonomyID).getAge()).getName();
      CFG.glyphLayout.setText(CFG.fontMain, this.sAgeName);
      this.iAgeNameWidth = (int)(CFG.glyphLayout.width * 0.6F);
      this.loadFlag(CFG.ideologiesManager.getRealTag(civATag)); //change to load vassal flag later
   }

   protected final void loadFlag(String nTag) {
      try {
         try {
            this.civFlag = new Image(new Texture(Gdx.files.internal("game/flags/" + nTag + ".png")), Texture.TextureFilter.Nearest);
         } catch (GdxRuntimeException var7) {
            try {
               this.civFlag = new Image(new Texture(Gdx.files.internal("game/flags/" + CFG.ideologiesManager.getRealTag(nTag) + ".png")), Texture.TextureFilter.Nearest);
            } catch (GdxRuntimeException var6) {
               if (CFG.isAndroid()) {
                  try {
                     this.civFlag = new Image(new Texture(Gdx.files.local("game/civilizations_editor/" + CFG.ideologiesManager.getRealTag(nTag) + "/" + CFG.ideologiesManager.getRealTag(nTag) + "_FL.png")), Texture.TextureFilter.Nearest);
                  } catch (GdxRuntimeException var5) {
                     this.civFlag = new Image(new Texture(Gdx.files.internal("game/civilizations_editor/" + CFG.ideologiesManager.getRealTag(nTag) + "/" + CFG.ideologiesManager.getRealTag(nTag) + "_FL.png")), Texture.TextureFilter.Nearest);
                  }
               } else {
                  this.civFlag = new Image(new Texture(Gdx.files.internal("game/civilizations_editor/" + CFG.ideologiesManager.getRealTag(nTag) + "/" + CFG.ideologiesManager.getRealTag(nTag) + "_FL.png")), Texture.TextureFilter.Nearest);
               }
            }
         }
      } catch (GdxRuntimeException var8) {
         this.civFlag = new Image(new Texture(Gdx.files.internal("game/flags/ran.png")), Texture.TextureFilter.Nearest);
      }

   }

   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      if (this.row) {
         oSB.setColor(new Color(CFG.COLOR_GRADIENT_TITLE_BLUE.r, CFG.COLOR_GRADIENT_TITLE_BLUE.g, CFG.COLOR_GRADIENT_TITLE_BLUE.b, 0.15F));
         ImageManager.getImage(Images.gradient).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.gradient).getHeight() + iTranslateY, this.getWidth(), this.getHeight());
         ImageManager.getImage(Images.line_32_off1).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.line_32_off1).getHeight() + iTranslateY, this.getWidth(), this.getHeight());
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.35F));
         ImageManager.getImage(Images.slider_gradient).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.slider_gradient).getHeight() + iTranslateY, this.getWidth() / 6, this.getHeight());
         ImageManager.getImage(Images.slider_gradient).draw(oSB, this.getPosX() + this.getWidth() - this.getWidth() / 6 + iTranslateX, this.getPosY() - ImageManager.getImage(Images.slider_gradient).getHeight() + iTranslateY, this.getWidth() / 6, this.getHeight(), true, false);
      } else {
         oSB.setColor(new Color(CFG.COLOR_GRADIENT_TITLE_BLUE.r, CFG.COLOR_GRADIENT_TITLE_BLUE.g, CFG.COLOR_GRADIENT_TITLE_BLUE.b, 0.05F));
         ImageManager.getImage(Images.gradient).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.gradient).getHeight() + iTranslateY, this.getWidth(), this.getHeight());
         ImageManager.getImage(Images.line_32_off1).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.line_32_off1).getHeight() + iTranslateY, this.getWidth(), this.getHeight());
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.3F));
         ImageManager.getImage(Images.slider_gradient).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.slider_gradient).getHeight() + iTranslateY, this.getWidth() / 6, this.getHeight());
         ImageManager.getImage(Images.slider_gradient).draw(oSB, this.getPosX() + this.getWidth() - this.getWidth() / 6 + iTranslateX, this.getPosY() - ImageManager.getImage(Images.slider_gradient).getHeight() + iTranslateY, this.getWidth() / 6, this.getHeight(), true, false);
      }

      if (isActive || this.getIsHovered()) {
         oSB.setColor(new Color(CFG.COLOR_GRADIENT_DIPLOMACY.r, CFG.COLOR_GRADIENT_DIPLOMACY.g, CFG.COLOR_GRADIENT_DIPLOMACY.b, isActive ? 0.345F : 0.265F));
         ImageManager.getImage(Images.line_32_off1).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + 1 - ImageManager.getImage(Images.line_32_off1).getHeight() + iTranslateY, this.getWidth(), this.getHeight() - 2);
      }

      if (this.row) {
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.625F));
         ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() + iTranslateY, this.getWidth(), 1);
         ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - ImageManager.getImage(Images.pix255_255_255).getHeight() + iTranslateY, this.getWidth());
      } else {
         oSB.setColor(new Color(CFG.COLOR_GRADIENT_TITLE_BLUE.r, CFG.COLOR_GRADIENT_TITLE_BLUE.g, CFG.COLOR_GRADIENT_TITLE_BLUE.b, 0.375F));
         ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() + iTranslateY, this.getWidth(), 1);
         ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.getPosX() + iTranslateX, this.getPosY() + this.getHeight() - ImageManager.getImage(Images.pix255_255_255).getHeight() + iTranslateY, this.getWidth());
      }

      oSB.setColor(Color.WHITE);
   }

   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      this.civFlag.draw(oSB, this.getPosX() + CFG.PADDING * 2 + iTranslateX, this.getPosY() + this.getHeight() / 2 - (int)((float)ImageManager.getImage(Images.flag_rect).getHeight() * this.getImageScale(Images.flag_rect)) / 2 - this.civFlag.getHeight() + iTranslateY, (int)((float)ImageManager.getImage(Images.flag_rect).getWidth() * this.getImageScale(Images.flag_rect)), (int)((float)ImageManager.getImage(Images.flag_rect).getHeight() * this.getImageScale(Images.flag_rect)));
      ImageManager.getImage(Images.flag_rect).draw(oSB, this.getPosX() + CFG.PADDING * 2 + iTranslateX, this.getPosY() + this.getHeight() / 2 - (int)((float)ImageManager.getImage(Images.flag_rect).getHeight() * this.getImageScale(Images.flag_rect)) / 2 - ImageManager.getImage(Images.flag_rect).getHeight() + iTranslateY, (int)((float)ImageManager.getImage(Images.flag_rect).getWidth() * this.getImageScale(Images.flag_rect)), (int)((float)ImageManager.getImage(Images.flag_rect).getHeight() * this.getImageScale(Images.flag_rect)));
      if (!this.getClickable()) {
         oSB.setColor(new Color(CFG.COLOR_TEXT_MODIFIER_NEGATIVE2.r, CFG.COLOR_TEXT_MODIFIER_NEGATIVE2.g, CFG.COLOR_TEXT_MODIFIER_NEGATIVE2.b, 0.675F));
      }

      ImageManager.getImage(Images.time).draw(oSB, this.getPosX() + this.getWidth() - CFG.PADDING * 2 - (int)((float)ImageManager.getImage(Images.time).getWidth() * this.getImageScale(Images.time)) + iTranslateX, this.getPosY() + this.getHeight() / 2 - (int)((float)ImageManager.getImage(Images.time).getHeight() * this.getImageScale(Images.time)) / 2 - ImageManager.getImage(Images.time).getHeight() + iTranslateY, (int)((float)ImageManager.getImage(Images.time).getWidth() * this.getImageScale(Images.time)), (int)((float)ImageManager.getImage(Images.time).getHeight() * this.getImageScale(Images.time)));
      oSB.setColor(Color.WHITE);
      if (!this.getClickable()) {
         oSB.setColor(new Color(1.0F, 1.0F, 1.0F, 0.65F));
      }

      //CFG.ideologiesManager.getIdeology(this.iAutonomyID).getCrownImageScaled().draw(oSB, this.getPosX() + (Ideologies_Manager.MAX_CROWN_WIDTH + CFG.PADDING * 2) / 2 - (int)((float)CFG.ideologiesManager.getIdeology(this.iAutonomyID).getCrownImageScaled().getWidth() * this.getImageScale_Ideology()) / 2 + CFG.PADDING * 3 + (int)((float)ImageManager.getImage(Images.flag_rect).getWidth() * this.getImageScale(Images.flag_rect)) + iTranslateX, this.getPosY() + this.getHeight() / 2 - (int)((float)CFG.ideologiesManager.getIdeology(this.iAutonomyID).getCrownImageScaled().getHeight() * this.getImageScale_Ideology()) / 2 - CFG.ideologiesManager.getIdeology(this.iAutonomyID).getCrownImageScaled().getHeight() + iTranslateY, (int)((float)CFG.ideologiesManager.getIdeology(this.iAutonomyID).getCrownImageScaled().getWidth() * this.getImageScale_Ideology()), (int)((float)CFG.ideologiesManager.getIdeology(this.iAutonomyID).getCrownImageScaled().getHeight() * this.getImageScale_Ideology()));
      oSB.setColor(Color.WHITE);
      CFG.fontMain.getData().setScale(0.6F);
      CFG.drawTextWithShadow(oSB, "" + this.sAgeName, this.getPosX() + this.getWidth() - CFG.PADDING * 3 - (int)((float)ImageManager.getImage(Images.time).getWidth() * this.getImageScale(Images.time)) - this.iAgeNameWidth + iTranslateX, this.getPosY() + this.getHeight() / 2 - (int)((float)CFG.TEXT_HEIGHT * 0.6F / 2.0F) + iTranslateY, CFG.COLOR_TEXT_OPTIONS_NS_HOVER);
      CFG.fontMain.getData().setScale(0.7F);
      CFG.drawTextWithShadow(oSB, this.getText(), this.getPosX() + CFG.PADDING * 4 + (int)((float)ImageManager.getImage(Images.flag_rect).getWidth() * this.getImageScale(Images.flag_rect)) + Ideologies_Manager.MAX_CROWN_WIDTH + CFG.PADDING * 2 + iTranslateX, this.getPosY() + this.getHeight() / 2 - (int)((float)CFG.TEXT_HEIGHT * 0.7F / 2.0F) + iTranslateY, this.getClickable() ? CFG.COLOR_INGAME_GOLD : new Color(CFG.COLOR_TEXT_MODIFIER_NEGATIVE2.r, CFG.COLOR_TEXT_MODIFIER_NEGATIVE2.g, CFG.COLOR_TEXT_MODIFIER_NEGATIVE2.b, 0.35F));
      CFG.fontMain.getData().setScale(1.0F);
      oSB.setColor(Color.WHITE);
   }

   protected Checkbox buildCheckbox() {
      return this.checkbox ? new Checkbox() {
         public void drawCheckBox(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean scrollableY) {
            if (Button_Diplomacy_ChangeAutonomy.this.getCheckboxState()) {
               oSB.setColor(new Color(0.55F, 0.8F, 0.0F, 0.2F));
            } else {
               oSB.setColor(new Color(0.8F, 0.137F, 0.0F, 0.15F));
            }

            ImageManager.getImage(Images.line_32_off1).draw(oSB, Button_Diplomacy_ChangeAutonomy.this.getPosX() + iTranslateX, Button_Diplomacy_ChangeAutonomy.this.getPosY() - ImageManager.getImage(Images.line_32_off1).getHeight() + 1 + iTranslateY, Button_Diplomacy_ChangeAutonomy.this.getWidth(), Button_Diplomacy_ChangeAutonomy.this.getHeight() - 2, true, false);
            oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.3F));
            ImageManager.getImage(Images.gradient).draw(oSB, Button_Diplomacy_ChangeAutonomy.this.getPosX() + iTranslateX, Button_Diplomacy_ChangeAutonomy.this.getPosY() - ImageManager.getImage(Images.gradient).getHeight() + 1 + iTranslateY, Button_Diplomacy_ChangeAutonomy.this.getWidth(), Button_Diplomacy_ChangeAutonomy.this.getHeight() / 4, false, false);
            ImageManager.getImage(Images.gradient).draw(oSB, Button_Diplomacy_ChangeAutonomy.this.getPosX() + iTranslateX, Button_Diplomacy_ChangeAutonomy.this.getPosY() - ImageManager.getImage(Images.gradient).getHeight() + Button_Diplomacy_ChangeAutonomy.this.getHeight() - 1 + iTranslateY - Button_Diplomacy_ChangeAutonomy.this.getHeight() / 4, Button_Diplomacy_ChangeAutonomy.this.getWidth(), Button_Diplomacy_ChangeAutonomy.this.getHeight() / 4, false, true);
            oSB.setColor(Color.WHITE);
         }
      } : new Checkbox() {
         public void drawCheckBox(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean scrollableY) {
         }
      };
   }

   private final float getImageScale(int nImageID) {
      return (float)CFG.TEXT_HEIGHT / (float)ImageManager.getImage(nImageID).getHeight() < 1.0F ? (float)CFG.TEXT_HEIGHT / (float)ImageManager.getImage(nImageID).getHeight() : 1.0F;
   }

   private final float getImageScale_Ideology() {
      //FIX
      return (float)CFG.TEXT_HEIGHT / (float)CFG.ideologiesManager.getIdeology(0).getCrownImageScaled().getHeight() < 1.0F ? (float)CFG.TEXT_HEIGHT / (float)CFG.ideologiesManager.getIdeology(this.iAutonomyID).getCrownImageScaled().getHeight() : 1.0F;
   }

   protected Color getColor(boolean isActive) {
      return isActive ? CFG.COLOR_TEXT_OPTIONS_NS_ACTIVE : (this.getIsHovered() ? CFG.COLOR_TEXT_OPTIONS_NS_HOVER : CFG.COLOR_TEXT_OPTIONS_NS);
   }

   protected void buildElementHover() {
      List nElements = new ArrayList();
      List nData = new ArrayList();
      nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Autonomy") + ": ", CFG.COLOR_TEXT_NUM_OF_PROVINCES));
      nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.gameAutonomy.lAutonomy.get(this.iAutonomyID).getName(), CFG.COLOR_INGAME_GOLD));
      nElements.add(new MenuElement_Hover_v2_Element2(nData));
      nData.clear();

      nData.add(new MenuElement_Hover_v2_Element_Type_Space());
      nElements.add(new MenuElement_Hover_v2_Element2(nData));
      nData.clear();

      nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("AutoJoinWars") + ": "));
      nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.gameAutonomy.lAutonomy.get(this.iAutonomyID).isAutoJoinWarPerms() ? CFG.langManager.get("Yes") : CFG.langManager.get("No"), CFG.COLOR_TEXT_MODIFIER_NEUTRAL2));
      nElements.add(new MenuElement_Hover_v2_Element2(nData));
      nData.clear();
      nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("HasIndependentMilitary") + ": "));
      nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.gameAutonomy.lAutonomy.get(this.iAutonomyID).isMilitaryControl() ? CFG.langManager.get("Yes") : CFG.langManager.get("No"), CFG.COLOR_TEXT_MODIFIER_NEUTRAL2));
      nElements.add(new MenuElement_Hover_v2_Element2(nData));
      nData.clear();
      nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("HasIndependentEconomy") + ": "));
      nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.gameAutonomy.lAutonomy.get(this.iAutonomyID).isEconomicControl() ? CFG.langManager.get("Yes") : CFG.langManager.get("No"), CFG.COLOR_TEXT_MODIFIER_NEUTRAL2));
      nElements.add(new MenuElement_Hover_v2_Element2(nData));
      nData.clear();
      nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("AutoAcceptsTrades") + ": "));
      nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.gameAutonomy.lAutonomy.get(this.iAutonomyID).isTradeIntegration() ? CFG.langManager.get("Yes") : CFG.langManager.get("No"), CFG.COLOR_TEXT_MODIFIER_NEUTRAL2));
      nElements.add(new MenuElement_Hover_v2_Element2(nData));
      nData.clear();

      this.menuElementHover = new MenuElement_Hover_v2(nElements);
   }

   protected int getCurrent() {
      return this.iAutonomyID;
   }

   protected int getSFX() {
      return SoundsManager.SOUND_CLICK2;
   }

   protected void setVisible(boolean isVisible) {
      super.setVisible(isVisible);
      if (!isVisible && this.civFlag != null) {
         this.civFlag.getTexture().dispose();
         this.civFlag = null;
      }

   }
}
