package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

class Button_Diplomacy_AnnexTerritory extends Button_Statistics {
   protected static final float FONT_SCALE = 0.7F;
   protected int iCivID;
   protected String sPopulation;
   protected int iPopulationWidth;
   protected ArrayList<Integer> iProvinces;
   protected int iProvincesWidth;

   protected Button_Diplomacy_AnnexTerritory(int i, int iCivID, String name, List<Integer> iProvinces, int iPosX, int iPosY, int iWidth) {
      super(
         name,
         0,
         iPosX,
         iPosY,
         iWidth,
         CFG.isAndroid() ? (int)Math.max((float)CFG.BUTTON_HEIGHT * 0.6F, (float)(CFG.TEXT_HEIGHT + CFG.PADDING * 6)) : CFG.TEXT_HEIGHT + CFG.PADDING * 4,
         false
      );
      this.iCivID = iCivID;
      this.row = i % 2 == 0;

      this.iProvinces = new ArrayList<Integer>();
      int iPopulation = 0;

      for (int k = 0; k < iProvinces.size(); ++k) {
         if (CFG.game.getProvince(iProvinces.get(k)).getSeaProvince() || CFG.game.getProvince(iProvinces.get(k)).getWasteland() >= 1) continue;

         this.iProvinces.add(iProvinces.get(k));
         iPopulation += CFG.game.getProvince(iProvinces.get(k)).getPopulationData().getPopulation();
      }
      if (name == CFG.langManager.get("SelectCivilization") || name == CFG.langManager.get("SelectProvinces")) {
         this.sPopulation = name;
      } else {
         this.sPopulation = (iPopulation == 0 ? CFG.langManager.get("NoData") : CFG.getNumberWithSpaces("" + (iPopulation)));
      }

      CFG.glyphLayout.setText(CFG.fontMain, this.sPopulation);
      this.iPopulationWidth = (int)(CFG.glyphLayout.width * 0.7F);
      CFG.glyphLayout.setText(CFG.fontMain, "" + (this.iProvinces.size() == 0 ? CFG.langManager.get("None") : this.iProvinces.size()));
      this.iProvincesWidth = (int)(CFG.glyphLayout.width * 0.7F);
   }

   @Override
   protected void drawButtonBG(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      if (this.row) {
         oSB.setColor(new Color(CFG.COLOR_GRADIENT_TITLE_BLUE.r, CFG.COLOR_GRADIENT_TITLE_BLUE.g, CFG.COLOR_GRADIENT_TITLE_BLUE.b, 0.15F));
         ImageManager.getImage(Images.gradient)
            .draw(
               oSB,
               this.getPosX() + iTranslateX,
               this.getPosY() - ImageManager.getImage(Images.gradient).getHeight() + iTranslateY,
               this.getWidth(),
               this.getHeight()
            );
         ImageManager.getImage(Images.line_32_off1)
            .draw(
               oSB,
               this.getPosX() + iTranslateX,
               this.getPosY() - ImageManager.getImage(Images.line_32_off1).getHeight() + iTranslateY,
               this.getWidth(),
               this.getHeight()
            );
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.35F));
         ImageManager.getImage(Images.slider_gradient)
            .draw(
               oSB,
               this.getPosX() + iTranslateX,
               this.getPosY() - ImageManager.getImage(Images.slider_gradient).getHeight() + iTranslateY,
               this.getWidth() / 6,
               this.getHeight()
            );
         ImageManager.getImage(Images.slider_gradient)
            .draw(
               oSB,
               this.getPosX() + this.getWidth() - this.getWidth() / 6 + iTranslateX,
               this.getPosY() - ImageManager.getImage(Images.slider_gradient).getHeight() + iTranslateY,
               this.getWidth() / 6,
               this.getHeight(),
               true,
               false
            );
      } else {
         oSB.setColor(new Color(CFG.COLOR_GRADIENT_TITLE_BLUE.r, CFG.COLOR_GRADIENT_TITLE_BLUE.g, CFG.COLOR_GRADIENT_TITLE_BLUE.b, 0.05F));
         ImageManager.getImage(Images.gradient)
            .draw(
               oSB,
               this.getPosX() + iTranslateX,
               this.getPosY() - ImageManager.getImage(Images.gradient).getHeight() + iTranslateY,
               this.getWidth(),
               this.getHeight()
            );
         ImageManager.getImage(Images.line_32_off1)
            .draw(
               oSB,
               this.getPosX() + iTranslateX,
               this.getPosY() - ImageManager.getImage(Images.line_32_off1).getHeight() + iTranslateY,
               this.getWidth(),
               this.getHeight()
            );
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.3F));
         ImageManager.getImage(Images.slider_gradient)
            .draw(
               oSB,
               this.getPosX() + iTranslateX,
               this.getPosY() - ImageManager.getImage(Images.slider_gradient).getHeight() + iTranslateY,
               this.getWidth() / 6,
               this.getHeight()
            );
         ImageManager.getImage(Images.slider_gradient)
            .draw(
               oSB,
               this.getPosX() + this.getWidth() - this.getWidth() / 6 + iTranslateX,
               this.getPosY() - ImageManager.getImage(Images.slider_gradient).getHeight() + iTranslateY,
               this.getWidth() / 6,
               this.getHeight(),
               true,
               false
            );
      }

      if (isActive || this.getIsHovered()) {
         oSB.setColor(new Color(CFG.COLOR_GRADIENT_DIPLOMACY.r, CFG.COLOR_GRADIENT_DIPLOMACY.g, CFG.COLOR_GRADIENT_DIPLOMACY.b, isActive ? 0.345F : 0.265F));
         ImageManager.getImage(Images.line_32_off1)
            .draw(
               oSB,
               this.getPosX() + iTranslateX,
               this.getPosY() + 1 - ImageManager.getImage(Images.line_32_off1).getHeight() + iTranslateY,
               this.getWidth(),
               this.getHeight() - 2
            );
      }

      if (this.row) {
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.625F));
         ImageManager.getImage(Images.pix255_255_255)
            .draw(
               oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() + iTranslateY, this.getWidth(), 1
            );
         ImageManager.getImage(Images.pix255_255_255)
            .draw(
               oSB,
               this.getPosX() + iTranslateX,
               this.getPosY() + this.getHeight() - ImageManager.getImage(Images.pix255_255_255).getHeight() + iTranslateY,
               this.getWidth()
            );
      } else {
         oSB.setColor(new Color(CFG.COLOR_GRADIENT_TITLE_BLUE.r, CFG.COLOR_GRADIENT_TITLE_BLUE.g, CFG.COLOR_GRADIENT_TITLE_BLUE.b, 0.375F));
         ImageManager.getImage(Images.pix255_255_255)
            .draw(
               oSB, this.getPosX() + iTranslateX, this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() + iTranslateY, this.getWidth(), 1
            );
         ImageManager.getImage(Images.pix255_255_255)
            .draw(
               oSB,
               this.getPosX() + iTranslateX,
               this.getPosY() + this.getHeight() - ImageManager.getImage(Images.pix255_255_255).getHeight() + iTranslateY,
               this.getWidth()
            );
      }

      oSB.setColor(Color.WHITE);
   }

   @Override
   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      try {
         oSB.setColor(
                 new Color(
                         (float)CFG.game.getCiv(this.iCivID).getR() / 255.0F,
                         (float)CFG.game.getCiv(this.iCivID).getG() / 255.0F,
                         (float)CFG.game.getCiv(this.iCivID).getB() / 255.0F,
                         1.0F
                 )
         );
      } catch (IndexOutOfBoundsException var6) {
         oSB.setColor(
                 new Color(
                         CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_ALLIANCE.getR(),
                         CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_ALLIANCE.getG(),
                         CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_ALLIANCE.getB(),
                         1.0F
                 )
         );
      }

      ImageManager.getImage(Images.pix255_255_255)
              .draw(
                      oSB,
                      this.getPosX() + CFG.PADDING * 2 + iTranslateX,
                      this.getPosY()
                              + this.getHeight() / 2
                              - (int)((float)ImageManager.getImage(Images.flag_rect).getHeight() * this.getImageScale(Images.flag_rect)) / 2
                              - ImageManager.getImage(Images.pix255_255_255).getHeight()
                              + iTranslateY,
                      2,
                      (int)((float)ImageManager.getImage(Images.flag_rect).getHeight() * this.getImageScale(Images.flag_rect))
              );
      oSB.setColor(Color.WHITE);
      CFG.game
              .getCiv(this.iCivID)
              .getFlag()
              .draw(
                      oSB,
                      this.getPosX() + CFG.PADDING * 2 + 2 + iTranslateX,
                      this.getPosY()
                              + this.getHeight() / 2
                              - (int)((float)ImageManager.getImage(Images.flag_rect).getHeight() * this.getImageScale(Images.flag_rect)) / 2
                              - CFG.game.getCiv(this.iCivID).getFlag().getHeight()
                              + iTranslateY,
                      (int)((float)ImageManager.getImage(Images.flag_rect).getWidth() * this.getImageScale(Images.flag_rect)),
                      (int)((float)ImageManager.getImage(Images.flag_rect).getHeight() * this.getImageScale(Images.flag_rect))
              );
      ImageManager.getImage(Images.flag_rect)
              .draw(
                      oSB,
                      this.getPosX() + CFG.PADDING * 2 + 2 + iTranslateX,
                      this.getPosY()
                              + this.getHeight() / 2
                              - (int)((float)ImageManager.getImage(Images.flag_rect).getHeight() * this.getImageScale(Images.flag_rect)) / 2
                              - ImageManager.getImage(Images.flag_rect).getHeight()
                              + iTranslateY,
                      (int)((float)ImageManager.getImage(Images.flag_rect).getWidth() * this.getImageScale(Images.flag_rect)),
                      (int)((float)ImageManager.getImage(Images.flag_rect).getHeight() * this.getImageScale(Images.flag_rect))
              );
      /*ImageManager.getImage(Images.diplo_revolution)
              .draw(
                      oSB,
                      this.getPosX()
                              + this.getWidth()
                              - CFG.PADDING * 3
                              - this.iRevolutionaryRiskWidth
                              - (int)((float)ImageManager.getImage(Images.diplo_revolution).getWidth() * this.getImageScale(Images.diplo_revolution))
                              + iTranslateX,
                      this.getPosY()
                              + this.getHeight() / 2
                              - (int)((float)ImageManager.getImage(Images.diplo_revolution).getHeight() * this.getImageScale(Images.diplo_revolution)) / 2
                              - ImageManager.getImage(Images.diplo_revolution).getHeight()
                              + iTranslateY,
                      (int)((float)ImageManager.getImage(Images.diplo_revolution).getWidth() * this.getImageScale(Images.diplo_revolution)),
                      (int)((float)ImageManager.getImage(Images.diplo_revolution).getHeight() * this.getImageScale(Images.diplo_revolution))
              );*/
      ImageManager.getImage(Images.provinces)
              .draw(
                      oSB,
                      this.getPosX()
                              + this.getWidth()
                              - CFG.PADDING * 5
                              //- this.iRevolutionaryRiskWidth
                              - this.iProvincesWidth
                              - (int)((float)ImageManager.getImage(Images.provinces).getWidth() * this.getImageScale(Images.provinces))
                              //- (int)((float)ImageManager.getImage(Images.diplo_revolution).getWidth() * this.getImageScale(Images.diplo_revolution))
                              + iTranslateX,
                      this.getPosY()
                              + this.getHeight() / 2
                              - (int)((float)ImageManager.getImage(Images.provinces).getHeight() * this.getImageScale(Images.provinces)) / 2
                              - ImageManager.getImage(Images.provinces).getHeight()
                              + iTranslateY,
                      (int)((float)ImageManager.getImage(Images.provinces).getWidth() * this.getImageScale(Images.provinces)),
                      (int)((float)ImageManager.getImage(Images.provinces).getHeight() * this.getImageScale(Images.provinces))
              );
      ImageManager.getImage(Images.population)
              .draw(
                      oSB,
                      this.getPosX()
                              + this.getWidth()
                              - CFG.PADDING * 7
                              //- this.iRevolutionaryRiskWidth
                              - this.iProvincesWidth
                              - this.iPopulationWidth
                              - (int)((float)ImageManager.getImage(Images.provinces).getWidth() * this.getImageScale(Images.provinces))
                              //- (int)((float)ImageManager.getImage(Images.diplo_revolution).getWidth() * this.getImageScale(Images.diplo_revolution))
                              - (int)((float)ImageManager.getImage(Images.population).getWidth() * this.getImageScale(Images.population))
                              + iTranslateX,
                      this.getPosY()
                              + this.getHeight() / 2
                              - (int)((float)ImageManager.getImage(Images.population).getHeight() * this.getImageScale(Images.population)) / 2
                              - ImageManager.getImage(Images.population).getHeight()
                              + iTranslateY,
                      (int)((float)ImageManager.getImage(Images.population).getWidth() * this.getImageScale(Images.population)),
                      (int)((float)ImageManager.getImage(Images.population).getHeight() * this.getImageScale(Images.population))
              );
      CFG.fontMain.getData().setScale(0.7F);
      CFG.drawTextWithShadow(
              oSB,
              this.getText(),
              this.getPosX()
                      + CFG.PADDING * 3
                      + 2
                      + (int)((float)ImageManager.getImage(Images.flag_rect).getWidth() * this.getImageScale(Images.flag_rect))
                      + iTranslateX,
              this.getPosY() + this.getHeight() / 2 - (int)((float)CFG.TEXT_HEIGHT * 0.7F / 2.0F) + iTranslateY,
              this.getColor(isActive)
      );
      /*CFG.drawTextWithShadow(
              oSB,
              "" + this.iRevolutionaryRisk + "%",
              this.getPosX() + this.getWidth() - CFG.PADDING * 2 - this.iRevolutionaryRiskWidth + iTranslateX,
              this.getPosY() + this.getHeight() / 2 - (int)((float)CFG.TEXT_HEIGHT * 0.7F / 2.0F) + iTranslateY,
              CFG.getColorStep(CFG.COLOR_TEXT_REVOLUTION_MIN, CFG.COLOR_TEXT_REVOLUTION_MAX, this.iRevolutionaryRisk, 100, 1.0F)
      );*/
      CFG.drawTextWithShadow(
              oSB,
              "" + (this.iProvinces.size() == 0 ? CFG.langManager.get("None") : this.iProvinces.size()),
              this.getPosX()
                      + this.getWidth()
                      - CFG.PADDING * 4
                      //- this.iRevolutionaryRiskWidth
                      - this.iProvincesWidth
                      //- (int)((float)ImageManager.getImage(Images.diplo_revolution).getWidth() * this.getImageScale(Images.diplo_revolution))
                      + iTranslateX,
              this.getPosY() + this.getHeight() / 2 - (int)((float)CFG.TEXT_HEIGHT * 0.7F / 2.0F) + iTranslateY,
              CFG.COLOR_TEXT_NUM_OF_PROVINCES
      );
      CFG.drawTextWithShadow(
              oSB,
              "" + this.sPopulation,
              this.getPosX()
                      + this.getWidth()
                      - CFG.PADDING * 6
                      //- this.iRevolutionaryRiskWidth
                      - this.iProvincesWidth
                      - this.iPopulationWidth
                      //- (int)((float)ImageManager.getImage(Images.diplo_revolution).getWidth() * this.getImageScale(Images.diplo_revolution))
                      - (int)((float)ImageManager.getImage(Images.provinces).getWidth() * this.getImageScale(Images.provinces))
                      + iTranslateX,
              this.getPosY() + this.getHeight() / 2 - (int)((float)CFG.TEXT_HEIGHT * 0.7F / 2.0F) + iTranslateY,
              CFG.COLOR_TEXT_POPULATION
      );
      CFG.fontMain.getData().setScale(1.0F);
      oSB.setColor(Color.WHITE);
   }

   @Override
   protected Checkbox buildCheckbox() {
      return this.checkbox
         ? new Checkbox() {
            @Override
            public void drawCheckBox(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean scrollableY) {
               if (Button_Diplomacy_AnnexTerritory.this.getCheckboxState()) {
                  oSB.setColor(new Color(0.55F, 0.8F, 0.0F, 0.2F));
               } else {
                  oSB.setColor(new Color(0.8F, 0.137F, 0.0F, 0.15F));
               }

               ImageManager.getImage(Images.line_32_off1)
                  .draw(
                     oSB,
                          Button_Diplomacy_AnnexTerritory.this.getPosX() + iTranslateX,
                          Button_Diplomacy_AnnexTerritory.this.getPosY() - ImageManager.getImage(Images.line_32_off1).getHeight() + 1 + iTranslateY,
                          Button_Diplomacy_AnnexTerritory.this.getWidth(),
                          Button_Diplomacy_AnnexTerritory.this.getHeight() - 2,
                     true,
                     false
                  );
               oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.3F));
               ImageManager.getImage(Images.gradient)
                  .draw(
                     oSB,
                     Button_Diplomacy_AnnexTerritory.this.getPosX() + iTranslateX,
                     Button_Diplomacy_AnnexTerritory.this.getPosY() - ImageManager.getImage(Images.gradient).getHeight() + 1 + iTranslateY,
                     Button_Diplomacy_AnnexTerritory.this.getWidth(),
                     Button_Diplomacy_AnnexTerritory.this.getHeight() / 4,
                     false,
                     false
                  );
               ImageManager.getImage(Images.gradient)
                  .draw(
                     oSB,
                     Button_Diplomacy_AnnexTerritory.this.getPosX() + iTranslateX,
                     Button_Diplomacy_AnnexTerritory.this.getPosY()
                        - ImageManager.getImage(Images.gradient).getHeight()
                        + Button_Diplomacy_AnnexTerritory.this.getHeight()
                        - 1
                        + iTranslateY
                        - Button_Diplomacy_AnnexTerritory.this.getHeight() / 4,
                          Button_Diplomacy_AnnexTerritory.this.getWidth(),
                          Button_Diplomacy_AnnexTerritory.this.getHeight() / 4,
                     false,
                     true
                  );
               oSB.setColor(Color.WHITE);
            }
         }
         : new Checkbox() {
            @Override
            public void drawCheckBox(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean scrollableY) {
            }
         };
   }

   protected final float getImageScale(int nImageID) {
      return Math.min((float) CFG.TEXT_HEIGHT / (float) ImageManager.getImage(nImageID).getHeight(), 1.0F);
   }

   protected Color getColor(boolean isActive) {
      return isActive ? CFG.COLOR_TEXT_OPTIONS_NS_ACTIVE : (this.getIsHovered() ? CFG.COLOR_TEXT_OPTIONS_NS_HOVER : CFG.COLOR_TEXT_OPTIONS_NS);
   }

   @Override
   protected int getCurrent() {
      return this.iCivID;
   }

   @Override
   protected int getSFX() {
      return SoundsManager.SOUND_CLICK2;
   }
}
