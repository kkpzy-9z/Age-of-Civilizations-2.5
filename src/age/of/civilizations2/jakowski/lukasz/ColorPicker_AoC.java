package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class ColorPicker_AoC {
   private int iPosX = 150;
   private int iPosY = 150;
   private boolean visible = false;
   private int iSVHeight;
   private int iHUEWidth;
   private int iResizeHeight;
   private boolean activeHUE = false;
   private boolean activeSV = false;
   private boolean activeResize = false;
   private boolean activeMove = false;
   private boolean activeClose = false;
   protected static int activeRGB = -1;
   private int iActiveColorID = -1;
   private float fAlpha = 1.0F;
   private int iStartPosX;
   private int iStartPosY;
   private int iStartResizeHeight;
   private Color colorSVPos;
   private int iLastSVPosX;
   private int iLastSVPosY;
   private int iLastHUEPosY;
   private final float RGB_TEXT_SCALE;
   private int iRGBTextWidth;
   private int iRTextWidth;
   private int iGTextWidth;
   private int iBTextWidth;
   private List lRGBBoxes;
   private List lColorsBoxes;
   private List lColors;
   protected ColorPicker_AoC_Action ColorPicker_AoC_Action;
   private Color hueColor;
   private Color activeColor;
   private float[] hsv;
   private float hueVal;

   protected final void updateColorPicker_Action(PickerAction nAction) {
      switch (nAction) {
         case ACTIVE_CIVILIZATION_COLOR:
            this.ColorPicker_AoC_Action = new ColorPicker_AoC_Action() {
               public void update() {
                  CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).setR((int)(ColorPicker_AoC.this.activeColor.r * 255.0F));
                  CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).setG((int)(ColorPicker_AoC.this.activeColor.g * 255.0F));
                  CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).setB((int)(ColorPicker_AoC.this.activeColor.b * 255.0F));
               }

               public void setActiveProvince_Action() {
                  CFG.menuManager.getColorPicker().setActiveRGBColor(CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).getR(), CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).getG(), CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).getB());
                  CFG.menuManager.getColorPicker().updateColors();
               }
            };
            break;
         case CREATE_VASSAL_COLOR:
            this.ColorPicker_AoC_Action = new ColorPicker_AoC_Action() {
               public void update() {
                  CFG.createVassal_Data.oColor = new Color(ColorPicker_AoC.this.activeColor.r, ColorPicker_AoC.this.activeColor.g, ColorPicker_AoC.this.activeColor.b, 1.0F);
               }

               public void setActiveProvince_Action() {
               }
            };
            break;
         case CUSTOMIZE_ALLIANCE_COLOR:
            this.ColorPicker_AoC_Action = new ColorPicker_AoC_Action() {
               public void update() {
                  CFG.game.getAlliance(CFG.MANAGE_DIPLOMACY_CUSTOMIZE_ALLIANCE_ID).setColorOfAlliance(new Color_GameData(ColorPicker_AoC.this.activeColor.r, ColorPicker_AoC.this.activeColor.g, ColorPicker_AoC.this.activeColor.b));
               }

               public void setActiveProvince_Action() {
                  if (ColorPicker_AoC.this.getVisible() && CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).getAllianceID() > 0 && CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).getAllianceID() != CFG.MANAGE_DIPLOMACY_CUSTOMIZE_ALLIANCE_ID) {
                     CFG.MANAGE_DIPLOMACY_CUSTOMIZE_ALLIANCE_ID = CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).getAllianceID();
                     CFG.menuManager.getColorPicker().setActiveRGBColor(CFG.game.getAlliance(CFG.MANAGE_DIPLOMACY_CUSTOMIZE_ALLIANCE_ID).getColorOfAlliance().getR(), CFG.game.getAlliance(CFG.MANAGE_DIPLOMACY_CUSTOMIZE_ALLIANCE_ID).getColorOfAlliance().getG(), CFG.game.getAlliance(CFG.MANAGE_DIPLOMACY_CUSTOMIZE_ALLIANCE_ID).getColorOfAlliance().getB());
                  }

               }
            };
            break;
         case MAP_EDITOR_CONTINENT_COLOR:
            this.ColorPicker_AoC_Action = new ColorPicker_AoC_Action() {
               public void update() {
                  CFG.editor_Continent_GameData.setR(ColorPicker_AoC.this.activeColor.r);
                  CFG.editor_Continent_GameData.setG(ColorPicker_AoC.this.activeColor.g);
                  CFG.editor_Continent_GameData.setB(ColorPicker_AoC.this.activeColor.b);
               }

               public void setActiveProvince_Action() {
               }
            };
            break;
         case MAP_EDITOR_REGION_COLOR:
            this.ColorPicker_AoC_Action = new ColorPicker_AoC_Action() {
               public void update() {
                  CFG.editor_Region_GameData.setR(ColorPicker_AoC.this.activeColor.r);
                  CFG.editor_Region_GameData.setG(ColorPicker_AoC.this.activeColor.g);
                  CFG.editor_Region_GameData.setB(ColorPicker_AoC.this.activeColor.b);
               }

               public void setActiveProvince_Action() {
               }
            };
            break;
         case PALLET_OF_COLORS:
            this.ColorPicker_AoC_Action = new ColorPicker_AoC_Action() {
               public void update() {
                  CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).setR((int)(ColorPicker_AoC.this.activeColor.r * 255.0F));
                  CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).setG((int)(ColorPicker_AoC.this.activeColor.g * 255.0F));
                  CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).setB((int)(ColorPicker_AoC.this.activeColor.b * 255.0F));
                  if (CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() > 0) {
                     CFG.editorPalletOfCivsColors_Data.setCivColor(CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).getCivTag(), new Color_GameData(ColorPicker_AoC.this.activeColor.r, ColorPicker_AoC.this.activeColor.g, ColorPicker_AoC.this.activeColor.b));
                  }

               }

               public void setActiveProvince_Action() {
                  CFG.menuManager.getColorPicker().setActiveRGBColor(CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).getR(), CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).getG(), CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).getB());
                  CFG.menuManager.getColorPicker().updateColors();
               }
            };
            break;
         case COLOR_DIPLOMACY_OWN_PROVINCES:
            this.ColorPicker_AoC_Action = new ColorPicker_AoC_Action() {
               public void update() {
                  CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_OWN_PROVINCES.setR(ColorPicker_AoC.this.activeColor.r);
                  CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_OWN_PROVINCES.setG(ColorPicker_AoC.this.activeColor.g);
                  CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_OWN_PROVINCES.setB(ColorPicker_AoC.this.activeColor.b);
               }

               public void setActiveProvince_Action() {
               }
            };
            break;
         case COLOR_DIPLOMACY_ALLIANCE:
            this.ColorPicker_AoC_Action = new ColorPicker_AoC_Action() {
               public void update() {
                  CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_ALLIANCE.setR(ColorPicker_AoC.this.activeColor.r);
                  CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_ALLIANCE.setG(ColorPicker_AoC.this.activeColor.g);
                  CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_ALLIANCE.setB(ColorPicker_AoC.this.activeColor.b);
               }

               public void setActiveProvince_Action() {
               }
            };
            break;
         case COLOR_DIPLOMACY_AT_WAR:
            this.ColorPicker_AoC_Action = new ColorPicker_AoC_Action() {
               public void update() {
                  CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_AT_WAR.setR(ColorPicker_AoC.this.activeColor.r);
                  CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_AT_WAR.setG(ColorPicker_AoC.this.activeColor.g);
                  CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_AT_WAR.setB(ColorPicker_AoC.this.activeColor.b);
               }

               public void setActiveProvince_Action() {
               }
            };
            break;
         case COLOR_DIPLOMACY_VASSAL:
            this.ColorPicker_AoC_Action = new ColorPicker_AoC_Action() {
               public void update() {
                  CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_VASSAL.setR(ColorPicker_AoC.this.activeColor.r);
                  CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_VASSAL.setG(ColorPicker_AoC.this.activeColor.g);
                  CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_VASSAL.setB(ColorPicker_AoC.this.activeColor.b);
               }

               public void setActiveProvince_Action() {
               }
            };
            break;
         case COLOR_DIPLOMACY_PACT:
            this.ColorPicker_AoC_Action = new ColorPicker_AoC_Action() {
               public void update() {
                  CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_PACT.setR(ColorPicker_AoC.this.activeColor.r);
                  CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_PACT.setG(ColorPicker_AoC.this.activeColor.g);
                  CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_PACT.setB(ColorPicker_AoC.this.activeColor.b);
               }

               public void setActiveProvince_Action() {
               }
            };
            break;
         case COLOR_DIPLOMACY_PACT_MAX:
            this.ColorPicker_AoC_Action = new ColorPicker_AoC_Action() {
               public void update() {
                  CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_PACT_MAX.setR(ColorPicker_AoC.this.activeColor.r);
                  CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_PACT_MAX.setG(ColorPicker_AoC.this.activeColor.g);
                  CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_PACT_MAX.setB(ColorPicker_AoC.this.activeColor.b);
               }

               public void setActiveProvince_Action() {
               }
            };
            break;
         case COLOR_DIPLOMACY_INDEPENDENCE:
            this.ColorPicker_AoC_Action = new ColorPicker_AoC_Action() {
               public void update() {
                  CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_INDEPENDENCE.setR(ColorPicker_AoC.this.activeColor.r);
                  CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_INDEPENDENCE.setG(ColorPicker_AoC.this.activeColor.g);
                  CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_INDEPENDENCE.setB(ColorPicker_AoC.this.activeColor.b);
               }

               public void setActiveProvince_Action() {
               }
            };
            break;
         case COLOR_DIPLOMACY_MILITARY_ACCESS:
            this.ColorPicker_AoC_Action = new ColorPicker_AoC_Action() {
               public void update() {
                  CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_MILITARY_ACCESS.setR(ColorPicker_AoC.this.activeColor.r);
                  CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_MILITARY_ACCESS.setG(ColorPicker_AoC.this.activeColor.g);
                  CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_MILITARY_ACCESS.setB(ColorPicker_AoC.this.activeColor.b);
               }

               public void setActiveProvince_Action() {
               }
            };
            break;
         case COLOR_DIPLOMACY_DEFENSIVE_PACT:
            this.ColorPicker_AoC_Action = new ColorPicker_AoC_Action() {
               public void update() {
                  CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_DEFENSIVE_PACT.setR(ColorPicker_AoC.this.activeColor.r);
                  CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_DEFENSIVE_PACT.setG(ColorPicker_AoC.this.activeColor.g);
                  CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_DEFENSIVE_PACT.setB(ColorPicker_AoC.this.activeColor.b);
               }

               public void setActiveProvince_Action() {
               }
            };
            break;
         case COLOR_DIPLOMACY_NEUTRAL:
            this.ColorPicker_AoC_Action = new ColorPicker_AoC_Action() {
               public void update() {
                  CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.setR(ColorPicker_AoC.this.activeColor.r);
                  CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.setG(ColorPicker_AoC.this.activeColor.g);
                  CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEUTRAL.setB(ColorPicker_AoC.this.activeColor.b);
               }

               public void setActiveProvince_Action() {
               }
            };
            break;
         case CIV_NAMES_OVER_PROVINCES:
            this.ColorPicker_AoC_Action = new ColorPicker_AoC_Action() {
               public void update() {
                  CFG.settingsManager.civNamesFontColor.setR(ColorPicker_AoC.this.activeColor.r);
                  CFG.settingsManager.civNamesFontColor.setG(ColorPicker_AoC.this.activeColor.g);
                  CFG.settingsManager.civNamesFontColor.setB(ColorPicker_AoC.this.activeColor.b);
                  CFG.loadFontBorder();
               }

               public void setActiveProvince_Action() {
               }
            };
            break;
         case CIV_NAMES_OVER_PROVINCES_BORDER:
            this.ColorPicker_AoC_Action = new ColorPicker_AoC_Action() {
               public void update() {
                  CFG.settingsManager.civNamesFontColorBorder.setR(ColorPicker_AoC.this.activeColor.r);
                  CFG.settingsManager.civNamesFontColorBorder.setG(ColorPicker_AoC.this.activeColor.g);
                  CFG.settingsManager.civNamesFontColorBorder.setB(ColorPicker_AoC.this.activeColor.b);
                  CFG.loadFontBorder();
               }

               public void setActiveProvince_Action() {
               }
            };
            break;
         case PROVINCE_SETTINGS_WASTELAND_COLOR:
            this.ColorPicker_AoC_Action = new ColorPicker_AoC_Action() {
               public void update() {
                  CFG.settingsManager.COLOR_PROVINCE_BG_WASTELAND.setR(ColorPicker_AoC.this.activeColor.r);
                  CFG.settingsManager.COLOR_PROVINCE_BG_WASTELAND.setG(ColorPicker_AoC.this.activeColor.g);
                  CFG.settingsManager.COLOR_PROVINCE_BG_WASTELAND.setB(ColorPicker_AoC.this.activeColor.b);
               }

               public void setActiveProvince_Action() {
               }
            };
            break;
         case PROVINCE_SETTINGS_DISCOVERY_COLOR:
            this.ColorPicker_AoC_Action = new ColorPicker_AoC_Action() {
               public void update() {
                  CFG.settingsManager.COLOR_PROVINCE_DISCOVERY.setR(ColorPicker_AoC.this.activeColor.r);
                  CFG.settingsManager.COLOR_PROVINCE_DISCOVERY.setG(ColorPicker_AoC.this.activeColor.g);
                  CFG.settingsManager.COLOR_PROVINCE_DISCOVERY.setB(ColorPicker_AoC.this.activeColor.b);
               }

               public void setActiveProvince_Action() {
               }
            };
            break;
         case COLOR_DIPLOMACY_NEGATIVE:
            this.ColorPicker_AoC_Action = new ColorPicker_AoC_Action() {
               public void update() {
                  CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEGATIVE[CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID].setR(ColorPicker_AoC.this.activeColor.r);
                  CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEGATIVE[CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID].setG(ColorPicker_AoC.this.activeColor.g);
                  CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_NEGATIVE[CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID].setB(ColorPicker_AoC.this.activeColor.b);
               }

               public void setActiveProvince_Action() {
               }
            };
            break;
         case COLOR_DIPLOMACY_POSITIVE:
            this.ColorPicker_AoC_Action = new ColorPicker_AoC_Action() {
               public void update() {
                  CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_POSITIVE[CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID].setR(ColorPicker_AoC.this.activeColor.r);
                  CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_POSITIVE[CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID].setG(ColorPicker_AoC.this.activeColor.g);
                  CFG.diplomacyColors_GameData.COLOR_DIPLOMACY_POSITIVE[CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID].setB(ColorPicker_AoC.this.activeColor.b);
               }

               public void setActiveProvince_Action() {
               }
            };
            break;
         case EDITOR_TERRAIN_COLOR:
            this.ColorPicker_AoC_Action = new ColorPicker_AoC_Action() {
               public void update() {
                  CFG.editorTerrain_Data2.setColor(new Color_GameData(ColorPicker_AoC.this.activeColor.r, ColorPicker_AoC.this.activeColor.g, ColorPicker_AoC.this.activeColor.b));
               }

               public void setActiveProvince_Action() {
               }
            };
            break;
         case EDITOR_SERVICE_RIBBON_OVERLAY:
            this.ColorPicker_AoC_Action = new ColorPicker_AoC_Action() {
               public void update() {
                  CFG.editorServiceRibbon_Colors.set(CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID, new Color(ColorPicker_AoC.this.activeColor.r, ColorPicker_AoC.this.activeColor.g, ColorPicker_AoC.this.activeColor.b, 1.0F));
               }

               public void setActiveProvince_Action() {
               }
            };
            break;
         case EDITOR_CIV_GAME_COLOR:
            this.ColorPicker_AoC_Action = new ColorPicker_AoC_Action() {
               public void update() {
                  CFG.editorCivilization_GameData.setR((int)(ColorPicker_AoC.this.activeColor.r * 255.0F));
                  CFG.editorCivilization_GameData.setG((int)(ColorPicker_AoC.this.activeColor.g * 255.0F));
                  CFG.editorCivilization_GameData.setB((int)(ColorPicker_AoC.this.activeColor.b * 255.0F));
               }

               public void setActiveProvince_Action() {
               }
            };
            break;
         case EDITOR_CIV_GAME_COLOR_SR:
            this.ColorPicker_AoC_Action = new ColorPicker_AoC_Action() {
               public void update() {
                  CFG.editorCivilization_GameData.sr_GameData.getColor(CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID).setR(ColorPicker_AoC.this.activeColor.r);
                  CFG.editorCivilization_GameData.sr_GameData.getColor(CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID).setG(ColorPicker_AoC.this.activeColor.g);
                  CFG.editorCivilization_GameData.sr_GameData.getColor(CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID).setB(ColorPicker_AoC.this.activeColor.b);
               }

               public void setActiveProvince_Action() {
               }
            };
            break;
         case EDITOR_CIV_FLAG_DIVISION_COLOR:
            this.ColorPicker_AoC_Action = new ColorPicker_AoC_Action() {
               public void update() {
                  ((Color_GameData)CFG.flagManager.flagEdit.lDivisionColors.get(CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID)).setR(ColorPicker_AoC.this.activeColor.r);
                  ((Color_GameData)CFG.flagManager.flagEdit.lDivisionColors.get(CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID)).setG(ColorPicker_AoC.this.activeColor.g);
                  ((Color_GameData)CFG.flagManager.flagEdit.lDivisionColors.get(CFG.MANAGE_DIPLOMACY_CUSTOMIZE_RELATIONS_CIV_ID)).setB(ColorPicker_AoC.this.activeColor.b);
               }

               public void setActiveProvince_Action() {
               }
            };
            break;
         case EDITOR_CIV_FLAG_OVERLAY_COLOR:
            this.ColorPicker_AoC_Action = new ColorPicker_AoC_Action() {
               public void update() {
                  ((Flag_Overlay_GameData)CFG.flagManager.flagEdit.lOverlays.get(CFG.EDIT_ALLIANCE_NAMES_BUNDLE_ID)).oColor.setR(ColorPicker_AoC.this.activeColor.r);
                  ((Flag_Overlay_GameData)CFG.flagManager.flagEdit.lOverlays.get(CFG.EDIT_ALLIANCE_NAMES_BUNDLE_ID)).oColor.setG(ColorPicker_AoC.this.activeColor.g);
                  ((Flag_Overlay_GameData)CFG.flagManager.flagEdit.lOverlays.get(CFG.EDIT_ALLIANCE_NAMES_BUNDLE_ID)).oColor.setB(ColorPicker_AoC.this.activeColor.b);
               }

               public void setActiveProvince_Action() {
               }
            };
            break;
         case PROVINCE_BORDER_COLOR:
            this.ColorPicker_AoC_Action = new ColorPicker_AoC_Action() {
               public void update() {
                  CFG.settingsManager.PROVINCE_BORDER_COLOR.setR(ColorPicker_AoC.this.activeColor.r);
                  CFG.settingsManager.PROVINCE_BORDER_COLOR.setG(ColorPicker_AoC.this.activeColor.g);
                  CFG.settingsManager.PROVINCE_BORDER_COLOR.setB(ColorPicker_AoC.this.activeColor.b);
                  CFG.COLOR_PROVINCE_STRAIGHT.r = CFG.settingsManager.PROVINCE_BORDER_COLOR.getR();
                  CFG.COLOR_PROVINCE_STRAIGHT.g = CFG.settingsManager.PROVINCE_BORDER_COLOR.getG();
                  CFG.COLOR_PROVINCE_STRAIGHT.b = CFG.settingsManager.PROVINCE_BORDER_COLOR.getB();
               }

               public void setActiveProvince_Action() {
               }
            };
            break;
         default:
            this.ColorPicker_AoC_Action = new ColorPicker_AoC_Action() {
               public void update() {
               }

               public void setActiveProvince_Action() {
               }
            };
      }

   }

   protected ColorPicker_AoC() {
      this.colorSVPos = Color.WHITE;
      this.RGB_TEXT_SCALE = 0.9F;
      this.lRGBBoxes = new ArrayList();
      this.lColorsBoxes = new ArrayList();
      this.lColors = new ArrayList();
      this.hueColor = new Color(1.0F, 0.0F, 0.0F, 1.0F);
      this.activeColor = new Color(1.0F, 0.0F, 0.0F, 1.0F);
      this.hsv = new float[]{0.0F, 1.0F, 1.0F};
      this.hueVal = 1.0F;
      CFG.glyphLayout.setText(CFG.fontMain, "G 255");
      this.iRGBTextWidth = (int)CFG.glyphLayout.width;
      this.updateRGBWidth();
      this.lRGBBoxes.add(new Box(CFG.PADDING, ImageManager.getImage(Images.btn_close).getHeight() + ImageManager.getImage(Images.btn_close).getHeight() / 2, this.iRGBTextWidth + CFG.PADDING * 2, CFG.TEXT_HEIGHT + CFG.PADDING * 2));
      this.lRGBBoxes.add(new Box(CFG.PADDING, ImageManager.getImage(Images.btn_close).getHeight() + ImageManager.getImage(Images.btn_close).getHeight() / 2 + CFG.PADDING + CFG.TEXT_HEIGHT + CFG.PADDING * 2, this.iRGBTextWidth + CFG.PADDING * 2, CFG.TEXT_HEIGHT + CFG.PADDING * 2));
      this.lRGBBoxes.add(new Box(CFG.PADDING, ImageManager.getImage(Images.btn_close).getHeight() + ImageManager.getImage(Images.btn_close).getHeight() / 2 + CFG.PADDING * 2 + (CFG.TEXT_HEIGHT + CFG.PADDING * 2) * 2, this.iRGBTextWidth + CFG.PADDING * 2, CFG.TEXT_HEIGHT + CFG.PADDING * 2));
      this.updateColorPicker_Action(PickerAction.ACTIVE_CIVILIZATION_COLOR);
   }

   protected final void buildColors() {
      this.lColorsBoxes.add(new Box(0, CFG.PADDING, this.getColorBoxWidth(), CFG.TEXT_HEIGHT + CFG.PADDING * 2));
      this.lColors.add(new Color(this.activeColor.r, this.activeColor.g, this.activeColor.b, 1.0F));
      Random oR = new Random();

      for(int i = ((Box)this.lColorsBoxes.get(0)).getPosX() + ((Box)this.lColorsBoxes.get(0)).getWidth(); i < CFG.GAME_WIDTH; i += this.getColorBoxWidth()) {
         this.lColorsBoxes.add(new Box(i, CFG.PADDING, this.getColorBoxWidth(), CFG.TEXT_HEIGHT + CFG.PADDING * 2));
         this.addColor(oR.nextInt(Pallet_Manager.NUM_OF_COLORS));
      }

   }

   protected final void updateColors() {
      this.lColors.set(0, new Color(this.activeColor.r, this.activeColor.g, this.activeColor.b, 1.0F));

      for(int i = 1; i < this.lColors.size(); ++i) {
         this.lColors.set(i, new Color((float)CFG.oR.nextInt(256) / 255.0F, (float)CFG.oR.nextInt(256) / 255.0F, (float)CFG.oR.nextInt(256) / 255.0F, 1.0F));
      }

   }

   protected final void draw(SpriteBatch oSB, int iTranslateX) {
      oSB.setColor(1.0F, 1.0F, 1.0F, this.fAlpha);
      ImageManager.getImage(Images.new_game_top_edge).draw2(oSB, this.iPosX - CFG.PADDING * 2 + iTranslateX, this.iPosY - CFG.PADDING * 2 - ImageManager.getImage(Images.new_game_top_edge).getHeight(), this.getWidth() + CFG.PADDING * 4 - ImageManager.getImage(Images.new_game_top_edge).getWidth(), this.getHeight() + CFG.PADDING * 4 - ImageManager.getImage(Images.new_game_top_edge).getHeight());
      ImageManager.getImage(Images.new_game_top_edge).draw2(oSB, this.iPosX + this.getWidth() + CFG.PADDING * 2 - ImageManager.getImage(Images.new_game_top_edge).getWidth() + iTranslateX, this.iPosY - CFG.PADDING * 2 - ImageManager.getImage(Images.new_game_top_edge).getHeight(), ImageManager.getImage(Images.new_game_top_edge).getWidth(), this.getHeight() + CFG.PADDING * 4 - ImageManager.getImage(Images.new_game_top_edge).getHeight(), true);
      ImageManager.getImage(Images.new_game_top_edge).draw2(oSB, this.iPosX - CFG.PADDING * 2 + iTranslateX, this.iPosY + this.getHeight() + CFG.PADDING * 2 - ImageManager.getImage(Images.new_game_top_edge).getHeight() * 2, this.getWidth() + CFG.PADDING * 4 - ImageManager.getImage(Images.new_game_top_edge).getWidth(), ImageManager.getImage(Images.new_game_top_edge).getHeight(), false, true);
      ImageManager.getImage(Images.new_game_top_edge).draw(oSB, this.iPosX + this.getWidth() + CFG.PADDING * 2 - ImageManager.getImage(Images.new_game_top_edge).getWidth() + iTranslateX, this.iPosY + this.getHeight() + CFG.PADDING * 2 - ImageManager.getImage(Images.new_game_top_edge).getHeight(), true, true);
      ImageManager.getImage(Images.pickerHUE).draw(oSB, this.iPosX + this.iSVHeight + CFG.PADDING + iTranslateX, this.iPosY - ImageManager.getImage(Images.pickerHUE).getHeight(), this.iHUEWidth, this.iSVHeight);
      oSB.setColor(Color.WHITE);
      ImageManager.getImage(Images.pix255_255_255).draw(oSB, this.iPosX + iTranslateX, this.iPosY - ImageManager.getImage(Images.pix255_255_255).getHeight(), this.iSVHeight, this.iSVHeight);
      oSB.setColor(this.hueColor);
      ImageManager.getImage(Images.pickerSV).draw(oSB, this.iPosX + iTranslateX, this.iPosY - ImageManager.getImage(Images.pickerSV).getHeight(), this.iSVHeight, this.iSVHeight);
      if (!this.activeResize) {
         Rectangle clipBounds = new Rectangle((float)(this.iPosX + iTranslateX), (float)(CFG.GAME_HEIGHT - this.iPosY), (float)this.iSVHeight, (float)(-this.iSVHeight));
         oSB.flush();
         ScissorStack.pushScissors(clipBounds);
         oSB.setColor(this.colorSVPos);
         ImageManager.getImage(Images.pickerSVPos).draw(oSB, this.iPosX + this.iLastSVPosX - ImageManager.getImage(Images.pickerSVPos).getWidth() / 2 + iTranslateX, this.iPosY + this.iLastSVPosY - ImageManager.getImage(Images.pickerSVPos).getHeight() / 2);

         try {
            oSB.flush();
            ScissorStack.popScissors();
         } catch (IllegalStateException var5) {
            if (CFG.LOGS) {
               CFG.exceptionStack(var5);
            }
         }

         oSB.setColor(0.0F, 0.0F, 0.0F, this.fAlpha);
         ImageManager.getImage(Images.pix255_255_255).draw2(oSB, this.iPosX + this.iSVHeight + CFG.PADDING + CFG.PADDING + iTranslateX, this.iPosY + this.iLastHUEPosY - 1, this.iHUEWidth - CFG.PADDING * 2 + 1, 1);
      } else {
         oSB.setColor(Color.WHITE);
         ImageManager.getImage(Images.pickerEdge).draw(oSB, this.iPosX + this.iSVHeight - ImageManager.getImage(Images.pickerEdge).getWidth() + iTranslateX, this.iPosY + this.iSVHeight - ImageManager.getImage(Images.pickerEdge).getHeight() - 1);
      }

      if (this.activeMove) {
         oSB.setColor(Color.BLACK);
         ImageManager.getImage(Images.pickerEdge).draw(oSB, this.iPosX + 1 + iTranslateX, this.iPosY + 1, true, true);
      }

      oSB.setColor(Color.BLACK);
      CFG.drawRect(oSB, this.iPosX + iTranslateX, this.iPosY - 1, this.iSVHeight, this.iSVHeight);
      CFG.drawRect(oSB, this.iPosX + CFG.PADDING + this.iSVHeight + iTranslateX, this.iPosY - 1, this.iHUEWidth, this.iSVHeight);
      this.drawRGBText(oSB, 0, this.iPosX + this.iSVHeight + CFG.PADDING + this.iHUEWidth + ((Box)this.lRGBBoxes.get(0)).getPosX() + iTranslateX, this.iPosY + ((Box)this.lRGBBoxes.get(0)).getPosY(), ((Box)this.lRGBBoxes.get(0)).getWidth(), ((Box)this.lRGBBoxes.get(0)).getHeight(), "R", "" + (int)(this.activeColor.r * 255.0F), this.iRTextWidth);
      this.drawRGBText(oSB, 1, this.iPosX + this.iSVHeight + CFG.PADDING + this.iHUEWidth + ((Box)this.lRGBBoxes.get(1)).getPosX() + iTranslateX, this.iPosY + ((Box)this.lRGBBoxes.get(1)).getPosY(), ((Box)this.lRGBBoxes.get(1)).getWidth(), ((Box)this.lRGBBoxes.get(1)).getHeight(), "G", "" + (int)(this.activeColor.g * 255.0F), this.iGTextWidth);
      this.drawRGBText(oSB, 2, this.iPosX + this.iSVHeight + CFG.PADDING + this.iHUEWidth + ((Box)this.lRGBBoxes.get(2)).getPosX() + iTranslateX, this.iPosY + ((Box)this.lRGBBoxes.get(2)).getPosY(), ((Box)this.lRGBBoxes.get(2)).getWidth(), ((Box)this.lRGBBoxes.get(2)).getHeight(), "B", "" + (int)(this.activeColor.b * 255.0F), this.iBTextWidth);
      this.drawColors(oSB, this.iPosX + iTranslateX, this.iPosY + this.iSVHeight);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.175F));
      ImageManager.getImage(Images.gradient).draw(oSB, this.iPosX + ((Box)this.lColorsBoxes.get(0)).getPosX() + iTranslateX, this.iPosY + this.iSVHeight + ((Box)this.lColorsBoxes.get(0)).getPosY() - ImageManager.getImage(Images.gradient).getHeight(), this.getWidth(), ((Box)this.lColorsBoxes.get(this.lColorsBoxes.size() - 1)).getHeight(), false, true);
      if (this.iActiveColorID >= 0) {
         oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.3F));
         ImageManager.getImage(Images.gradient).draw(oSB, this.iPosX + ((Box)this.lColorsBoxes.get(this.iActiveColorID)).getPosX() + iTranslateX, this.iPosY + this.iSVHeight + ((Box)this.lColorsBoxes.get(0)).getPosY() - ImageManager.getImage(Images.gradient).getHeight(), ((Box)this.lColorsBoxes.get(this.iActiveColorID)).getWidth(), ((Box)this.lColorsBoxes.get(this.lColorsBoxes.size() - 1)).getHeight());
      }

      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, this.fAlpha));
      CFG.drawRect(oSB, this.iPosX + ((Box)this.lColorsBoxes.get(0)).getPosX() + iTranslateX, this.iPosY + this.iSVHeight + ((Box)this.lColorsBoxes.get(0)).getPosY(), this.getWidth(), ((Box)this.lColorsBoxes.get(this.lColorsBoxes.size() - 1)).getHeight());
      oSB.setColor(Color.WHITE);
      ImageManager.getImage(this.activeClose ? Images.btnh_close : Images.btn_close).draw(oSB, this.getPosX() + this.getWidth() - ImageManager.getImage(Images.btn_close).getWidth() + iTranslateX, this.getPosY());
   }

   protected final void drawRGBText(SpriteBatch oSB, int boxID, int nPosX, int nPosY, int nWidth, int nHeight, String sLeft, String sRight, int nRightWidth) {
      oSB.setColor(CFG.COLOR_COLOR_PICKER_RGB_BG);
      ImageManager.getImage(Images.pix255_255_255).draw(oSB, nPosX, nPosY, nWidth, nHeight);
      if (activeRGB != boxID && Keyboard.activeColor_RGB_ID != boxID) {
         oSB.setColor(CFG.COLOR_LOADING_SPLIT);
      } else {
         oSB.setColor(CFG.COLOR_LOADING_SPLIT_ACTIVE);
      }

      CFG.drawRect(oSB, nPosX, nPosY, nWidth, nHeight);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.25F));
      CFG.drawRect(oSB, nPosX - 1, nPosY - 1, nWidth + 2, nHeight + 2);
      oSB.setColor(Color.WHITE);
      CFG.drawText(oSB, sLeft, nPosX + CFG.PADDING, nPosY + nHeight / 2 - CFG.TEXT_HEIGHT / 2, new Color(0.84F, 0.84F, 0.88F, 1.0F));
      CFG.fontMain.getData().setScale(0.9F);
      CFG.drawText(oSB, sRight, nPosX + nWidth - CFG.PADDING - (int)((float)nRightWidth * 0.9F), nPosY + nHeight / 2 - CFG.TEXT_HEIGHT / 2, CFG.COLOR_TEXT_RANK);
      CFG.fontMain.getData().setScale(1.0F);
   }

   protected final void drawColors(SpriteBatch oSB, int nPosX, int nPosY) {
      for(int i = this.lColorsBoxes.size() - 1; i >= 0; --i) {
         if (((Box)this.lColorsBoxes.get(i)).getVisible()) {
            oSB.setColor(((Color)this.lColors.get(i)).r, ((Color)this.lColors.get(i)).g, ((Color)this.lColors.get(i)).b, this.fAlpha);
            ImageManager.getImage(Images.pix255_255_255).draw(oSB, nPosX + ((Box)this.lColorsBoxes.get(i)).getPosX(), nPosY + ((Box)this.lColorsBoxes.get(i)).getPosY(), ((Box)this.lColorsBoxes.get(i)).getWidth(), ((Box)this.lColorsBoxes.get(i)).getHeight());
            oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.75F));
            ImageManager.getImage(Images.gradient).draw(oSB, nPosX + ((Box)this.lColorsBoxes.get(i)).getPosX() + ((Box)this.lColorsBoxes.get(i)).getWidth(), nPosY + ((Box)this.lColorsBoxes.get(i)).getPosY() - ImageManager.getImage(Images.gradient).getHeight(), 1, ((Box)this.lColorsBoxes.get(i)).getHeight());
         }
      }

   }

   protected final void touchUp() {
      if (this.activeResize) {
         this.iLastSVPosX = (int)((float)(this.iLastSVPosX * this.iSVHeight) / (float)this.iStartResizeHeight);
         this.iLastSVPosY = (int)((float)(this.iLastSVPosY * this.iSVHeight) / (float)this.iStartResizeHeight);
         this.iLastHUEPosY = (int)((float)(this.iLastHUEPosY * this.iSVHeight) / (float)this.iStartResizeHeight);
      } else if (this.activeClose && this.iLastSVPosX >= this.getPosX() + this.getWidth() - ImageManager.getImage(Images.btn_close).getWidth() && this.iLastSVPosX <= this.getPosX() + this.getWidth() && this.iLastSVPosY >= this.iPosY && this.iLastSVPosY <= this.iPosY + ImageManager.getImage(Images.btn_close).getHeight()) {
         this.setVisible(false, (PickerAction)null);
      }

      this.activeSV = false;
      this.activeHUE = false;
      this.activeResize = false;
      this.activeMove = false;
      this.activeClose = false;
      this.iActiveColorID = -1;
      if (activeRGB >= 0) {
         Keyboard.activeColor_RGB_ID = activeRGB;
         CFG.showKeyboard_ColorPickerRGB(activeRGB == 0 ? "R: " + (int)(this.activeColor.r * 255.0F) : (activeRGB == 1 ? "G: " + (int)(this.activeColor.g * 255.0F) : "B: " + (int)(this.activeColor.b * 255.0F)));
         activeRGB = -1;
      }

      this.fAlpha = 1.0F;
   }

   protected final void touch(int screenX, int screenY) {
      if (this.activeHUE) {
         if (screenY <= this.iPosY) {
            screenY = this.iPosY + 1;
         } else if (screenY > this.iPosY + this.iSVHeight) {
            screenY = this.iPosY + this.iSVHeight;
         }

         if (screenX < this.iPosX + this.iSVHeight + CFG.PADDING) {
            screenX = this.iPosX + this.iSVHeight + CFG.PADDING;
         } else if (screenX > this.iPosX + this.iSVHeight + CFG.PADDING + this.iHUEWidth) {
            screenX = this.iPosX + this.iSVHeight + CFG.PADDING + this.iHUEWidth;
         }

         this.updateHUE(screenY);
         this.updateSV(this.iPosX + this.iLastSVPosX, this.iPosY + this.iLastSVPosY);
      } else if (this.activeSV) {
         if (screenY < this.iPosY) {
            screenY = this.iPosY;
         } else if (screenY > this.iPosY + this.iSVHeight) {
            screenY = this.iPosY + this.iSVHeight;
         }

         if (screenX < this.iPosX) {
            screenX = this.iPosX;
         } else if (screenX > this.iPosX + this.iSVHeight) {
            screenX = this.iPosX + this.iSVHeight;
         }

         this.updateSV(screenX, screenY);
         this.iLastSVPosX = screenX - this.iPosX;
         this.iLastSVPosY = screenY - this.iPosY;
      } else {
         if (this.activeResize) {
            this.setSVHeight(screenY - this.iPosY - this.iStartPosY);
            return;
         }

         if (this.activeMove) {
            this.setPosX(screenX - this.iStartPosX);
            this.setPosY(screenY - this.iStartPosY);
            this.fAlpha = 0.75F;
            return;
         }

         int i;
         if (this.iActiveColorID >= 0) {
            for(i = 0; i < this.lColorsBoxes.size(); ++i) {
               if (((Box)this.lColorsBoxes.get(i)).getVisible() && screenX >= this.iPosX + ((Box)this.lColorsBoxes.get(i)).getPosX() && screenX <= this.iPosX + ((Box)this.lColorsBoxes.get(i)).getPosX() + ((Box)this.lColorsBoxes.get(i)).getWidth()) {
                  this.iActiveColorID = i;
                  if ((int)(this.activeColor.r * 255.0F) != (int)(((Color)this.lColors.get(i)).r * 255.0F) && (int)(this.activeColor.r * 255.0F) != (int)(((Color)this.lColors.get(i)).g * 255.0F) && (int)(this.activeColor.r * 255.0F) != (int)(((Color)this.lColors.get(i)).b * 255.0F)) {
                     this.RGBtoHSV((int)(((Color)this.lColors.get(i)).r * 255.0F), (int)(((Color)this.lColors.get(i)).g * 255.0F), (int)(((Color)this.lColors.get(i)).b * 255.0F));
                  }
                  break;
               }
            }
         } else if (activeRGB >= 0) {
            this.setActiveRGB_Box(screenX, screenY);
         } else if (this.activeClose) {
            this.iLastSVPosX = screenX;
            this.iLastSVPosY = screenY;
         } else {
            if (screenX >= this.iPosX + this.iSVHeight - this.iResizeHeight && screenX <= this.iPosX + this.iSVHeight && screenY >= this.iPosY + this.iSVHeight - this.iResizeHeight && screenY <= this.iPosY + this.iSVHeight) {
               this.activeResize = true;
               this.iStartPosY = screenY - this.iPosY - this.iSVHeight;
               this.iStartResizeHeight = this.iSVHeight;
               this.fAlpha = 0.75F;
               return;
            }

            if (screenX >= this.iPosX && screenX <= this.iPosX + this.iResizeHeight && screenY >= this.iPosY && screenY <= this.iPosY + this.iResizeHeight) {
               this.activeMove = true;
               this.iStartPosX = screenX - this.iPosX;
               this.iStartPosY = screenY - this.iPosY;
               return;
            }

            if (screenX >= this.iPosX + this.iSVHeight + CFG.PADDING && screenX <= this.iPosX + this.iSVHeight + CFG.PADDING + this.iHUEWidth && screenY >= this.iPosY && screenY <= this.iPosY + this.iSVHeight) {
               this.updateHUE(screenY);
               this.updateSV(this.iPosX + this.iLastSVPosX, this.iPosY + this.iLastSVPosY);
               this.activeHUE = true;
               Keyboard.activeColor_RGB_ID = -1;
               CFG.menuManager.getKeyboard().setVisible(false);
            } else if (screenX >= this.iPosX && screenX <= this.iPosX + this.iSVHeight && screenY >= this.iPosY && screenY <= this.iPosY + this.iSVHeight) {
               this.updateSV(screenX, screenY);
               this.activeSV = true;
               this.iLastSVPosX = screenX - this.iPosX;
               this.iLastSVPosY = screenY - this.iPosY;
               Keyboard.activeColor_RGB_ID = -1;
               CFG.menuManager.getKeyboard().setVisible(false);
            } else if (screenX >= this.iPosX + ((Box)this.lColorsBoxes.get(0)).getPosX() && screenX <= this.iPosX + this.getWidth() && screenY >= this.iPosY + this.iSVHeight + ((Box)this.lColorsBoxes.get(0)).getPosY() && screenY <= this.iPosY + this.iSVHeight + ((Box)this.lColorsBoxes.get(0)).getPosY() + ((Box)this.lColorsBoxes.get(0)).getHeight()) {
               for(i = 0; i < this.lColorsBoxes.size(); ++i) {
                  if (((Box)this.lColorsBoxes.get(i)).getVisible() && screenX >= this.iPosX + ((Box)this.lColorsBoxes.get(i)).getPosX() && screenX <= this.iPosX + ((Box)this.lColorsBoxes.get(i)).getPosX() + ((Box)this.lColorsBoxes.get(i)).getWidth() && screenY >= this.iPosY + this.iSVHeight + ((Box)this.lColorsBoxes.get(i)).getPosY() && screenY <= this.iPosY + this.iSVHeight + ((Box)this.lColorsBoxes.get(i)).getPosY() + ((Box)this.lColorsBoxes.get(i)).getHeight()) {
                     this.iActiveColorID = i;
                     if ((int)(this.activeColor.r * 255.0F) != (int)(((Color)this.lColors.get(i)).r * 255.0F) && (int)(this.activeColor.r * 255.0F) != (int)(((Color)this.lColors.get(i)).g * 255.0F) && (int)(this.activeColor.r * 255.0F) != (int)(((Color)this.lColors.get(i)).b * 255.0F)) {
                        this.RGBtoHSV((int)(((Color)this.lColors.get(i)).r * 255.0F), (int)(((Color)this.lColors.get(i)).g * 255.0F), (int)(((Color)this.lColors.get(i)).b * 255.0F));
                     }
                     break;
                  }
               }

               Keyboard.activeColor_RGB_ID = -1;
               CFG.menuManager.getKeyboard().setVisible(false);
            } else if (screenX >= this.iPosX + this.iSVHeight + CFG.PADDING + this.iHUEWidth + CFG.PADDING + ((Box)this.lRGBBoxes.get(0)).getPosX() && screenX <= this.iPosX + this.iSVHeight + CFG.PADDING + this.iHUEWidth + CFG.PADDING + ((Box)this.lRGBBoxes.get(0)).getPosX() + ((Box)this.lRGBBoxes.get(0)).getWidth() && screenY >= this.iPosY + ((Box)this.lRGBBoxes.get(0)).getPosY() && screenY <= this.iPosY + ((Box)this.lRGBBoxes.get(2)).getPosY() + ((Box)this.lRGBBoxes.get(2)).getHeight()) {
               activeRGB = 0;
               this.setActiveRGB_Box(screenX, screenY);
               Keyboard.activeColor_RGB_ID = -1;
               CFG.menuManager.getKeyboard().setVisible(false);
            } else if (screenX >= this.getPosX() + this.getWidth() - ImageManager.getImage(Images.btn_close).getWidth() && screenX <= this.getPosX() + this.getWidth() && screenY >= this.iPosY && screenY <= this.iPosY + ImageManager.getImage(Images.btn_close).getHeight()) {
               this.activeClose = true;
            }
         }
      }

      this.ColorPicker_AoC_Action.update();
   }

   private final void setActiveRGB_Box(int screenX, int screenY) {
      for(int i = 0; i < this.lRGBBoxes.size(); ++i) {
         if (screenX >= this.iPosX + this.iSVHeight + CFG.PADDING + this.iHUEWidth + CFG.PADDING + ((Box)this.lRGBBoxes.get(i)).getPosX() && screenX <= this.iPosX + this.iSVHeight + CFG.PADDING + this.iHUEWidth + CFG.PADDING + ((Box)this.lRGBBoxes.get(i)).getPosX() + ((Box)this.lRGBBoxes.get(i)).getWidth() && screenY >= this.iPosY + ((Box)this.lRGBBoxes.get(i)).getPosY() && screenY <= this.iPosY + ((Box)this.lRGBBoxes.get(i)).getPosY() + ((Box)this.lRGBBoxes.get(i)).getHeight()) {
            activeRGB = i;
            break;
         }
      }

   }

   private final void updateHUE(int screenY) {
      float perc = 1.0F - (float)(screenY - this.iPosY) / (float)this.iSVHeight;
      this.hsv[0] = this.hueVal = perc * 360.0F;
      this.hsv[1] = this.hsv[2] = 1.0F;
      this.HSVtoRGB(this.hsv, this.hueColor);
      this.updateSV(this.iLastSVPosX, this.iLastSVPosY);
      this.iLastHUEPosY = screenY - this.iPosY;
   }

   private final void updateSV(int screenX, int screenY) {
      float sat = (float)(screenX - this.iPosX) / (float)this.iSVHeight;
      float val = 1.0F - (float)(screenY - this.iPosY) / (float)this.iSVHeight;
      this.hsv[0] = this.hueVal;
      this.hsv[1] = sat;
      this.hsv[2] = val;
      this.updateColorSVPos(screenY - this.iPosY);
      this.HSVtoRGB(this.hsv, this.activeColor);
      this.updateRGBWidth();
   }

   private final void updateColorSVPos(int nPosY) {
      if ((float)this.iSVHeight * 0.1F > (float)nPosY) {
         this.colorSVPos = Color.BLACK;
      } else {
         this.colorSVPos = Color.WHITE;
      }

   }

   protected final void setActiveRGBColor(float R, float G, float B) {
      this.setActiveRGBColor((int)(R * 255.0F), (int)(G * 255.0F), (int)(B * 255.0F));
   }

   protected final void setActiveRGBColor(int R, int G, int B) {
      if (CFG.menuManager.getKeyboard().getVisible() || Keyboard.activeColor_RGB_ID >= 0) {
         Keyboard.activeColor_RGB_ID = -1;
         CFG.menuManager.getKeyboard().setVisible(false);
      }

      this.RGBtoHSV(R, G, B);
   }

   protected final void RGBtoHSV(int R, int G, int B) {
      float x = (float)Math.min(Math.min(R, G), B);
      float val = (float)Math.max(Math.max(R, G), B);
      if (x == val) {
         this.hsv[0] = 0.0F;
         this.hsv[1] = 0.0F;
      } else {
         float f = R == (int)x ? (float)(G - B) : (float)(G == (int)x ? B - R : R - G);
         float i = R == (int)x ? 3.0F : (float)(G == (int)x ? 5 : 1);
         this.hsv[0] = (i - f / (val - x)) * 60.0F % 360.0F;
         this.hsv[1] = (val - x) / val;
      }

      this.hsv[2] = val / 255.0F;
      this.hueVal = this.hsv[0];
      this.iLastSVPosX = (int)(this.hsv[1] * (float)this.iSVHeight);
      this.iLastSVPosY = (int)(-this.hsv[2] * (float)this.iSVHeight + (float)this.iSVHeight);
      this.iLastHUEPosY = (int)((float)this.iSVHeight - this.hsv[0] / 360.0F * (float)this.iSVHeight);
      this.updateSV(this.iPosY + this.iLastSVPosX, this.iPosY + this.iLastSVPosY);
      this.updateHUE(this.iPosY + this.iLastHUEPosY);
      this.activeColor.r = (float)R / 255.0F;
      this.activeColor.g = (float)G / 255.0F;
      this.activeColor.b = (float)B / 255.0F;
      this.updateRGBWidth();
   }

   private final void HSVtoRGB(float[] hsv, Color rgbOut) {
      float h = hsv[0];
      float s = hsv[1];
      float v = hsv[2];
      float r;
      float g;
      float b;
      if (s == 0.0F) {
         b = v;
         g = v;
         r = v;
      } else {
         h /= 60.0F;
         int i = (int)h;
         float f = h - (float)i;
         float p = v * (1.0F - s);
         float q = v * (1.0F - s * f);
         float t = v * (1.0F - s * (1.0F - f));
         switch (i) {
            case 0:
               r = v;
               g = t;
               b = p;
               break;
            case 1:
               r = q;
               g = v;
               b = p;
               break;
            case 2:
               r = p;
               g = v;
               b = t;
               break;
            case 3:
               r = p;
               g = q;
               b = v;
               break;
            case 4:
               r = t;
               g = p;
               b = v;
               break;
            default:
               r = v;
               g = p;
               b = q;
         }
      }

      rgbOut.r = r;
      rgbOut.g = g;
      rgbOut.b = b;
      rgbOut.a = 1.0F;
   }

   protected final void updateRGBWidth() {
      CFG.glyphLayout.setText(CFG.fontMain, "" + (int)(this.activeColor.r * 255.0F));
      this.iRTextWidth = (int)CFG.glyphLayout.width;
      CFG.glyphLayout.setText(CFG.fontMain, "" + (int)(this.activeColor.g * 255.0F));
      this.iGTextWidth = (int)CFG.glyphLayout.width;
      CFG.glyphLayout.setText(CFG.fontMain, "" + (int)(this.activeColor.b * 255.0F));
      this.iBTextWidth = (int)CFG.glyphLayout.width;
   }

   protected final void setPosX(int iPosX) {
      if (iPosX > CFG.GAME_WIDTH - ImageManager.getImage(Images.pickerSV).getHeight() / 2) {
         iPosX = CFG.GAME_WIDTH - ImageManager.getImage(Images.pickerSV).getHeight() / 2;
      } else if (iPosX < CFG.PADDING * 2) {
         iPosX = CFG.PADDING * 2;
      }

      this.iPosX = iPosX;
   }

   protected final int getPosX() {
      return this.iPosX;
   }

   protected final void setPosY(int iPosY) {
      if (iPosY > CFG.GAME_HEIGHT - ImageManager.getImage(Images.pickerSV).getHeight() / 2) {
         iPosY = CFG.GAME_HEIGHT - ImageManager.getImage(Images.pickerSV).getHeight() / 2;
      } else if (iPosY < CFG.PADDING * 2) {
         iPosY = CFG.PADDING * 2;
      }

      this.iPosY = iPosY;
   }

   protected final int getPosY() {
      return this.iPosY;
   }

   protected final int getWidth() {
      return this.iSVHeight + this.iHUEWidth + CFG.PADDING + this.iRGBTextWidth + CFG.PADDING * 3;
   }

   protected final int getHeight() {
      return this.iSVHeight + CFG.TEXT_HEIGHT + CFG.PADDING * 3;
   }

   protected final Color getActiveColor() {
      return this.activeColor;
   }

   protected final boolean getVisible() {
      return this.visible;
   }

   protected final void setVisible(boolean visible, PickerAction nAction) {
      if (nAction != null) {
         this.updateColorPicker_Action(nAction);
      } else {
         this.ColorPicker_AoC_Action = new ColorPicker_AoC_Action() {
            public void update() {
            }

            public void setActiveProvince_Action() {
            }
         };
      }

      this.visible = visible;
      if (!visible && CFG.menuManager.getKeyboard().getVisible()) {
         Keyboard.activeColor_RGB_ID = -1;
         CFG.menuManager.getKeyboard().setVisible(false);
      }

   }

   protected ColorPicker_AoC_Action getColorPickerAction() {
      return this.ColorPicker_AoC_Action;
   }

   protected final void setHueWidth(int iHUEWidth) {
      this.iHUEWidth = iHUEWidth;
   }

   protected final void setSVHeight(int iSVHeight) {
      if (iSVHeight < ImageManager.getImage(Images.pickerSV).getHeight()) {
         iSVHeight = ImageManager.getImage(Images.pickerSV).getHeight();
      } else if (this.getPosY() + iSVHeight + CFG.TEXT_HEIGHT + CFG.PADDING * 5 > CFG.GAME_HEIGHT) {
         iSVHeight = CFG.GAME_HEIGHT - this.getPosY() - (CFG.TEXT_HEIGHT + CFG.PADDING * 5);
      }

      this.iSVHeight = iSVHeight;

      int i;
      for(i = 1; i < this.lColorsBoxes.size(); ++i) {
         ((Box)this.lColorsBoxes.get(i)).setWidth(((Box)this.lColorsBoxes.get(0)).getWidth());
         ((Box)this.lColorsBoxes.get(i)).setVisible(true);
      }

      for(i = this.lColorsBoxes.size() - 1; i > 0; --i) {
         if (((Box)this.lColorsBoxes.get(i)).getPosX() > this.getWidth()) {
            ((Box)this.lColorsBoxes.get(i)).setVisible(false);
         } else {
            ((Box)this.lColorsBoxes.get(i)).setVisible(true);
            if (((Box)this.lColorsBoxes.get(i)).getPosX() + ((Box)this.lColorsBoxes.get(i)).getWidth() > this.getWidth()) {
               ((Box)this.lColorsBoxes.get(i)).setWidth(this.getWidth() - ((Box)this.lColorsBoxes.get(i)).getPosX());
            }
         }
      }

   }

   protected final int getColorBoxWidth() {
      return CFG.TEXT_HEIGHT + CFG.PADDING * 4;
   }

   protected final void addColor(int nID) {
      this.lColors.add(new Color((float)CFG.oR.nextInt(256) / 255.0F, (float)CFG.oR.nextInt(256) / 255.0F, (float)CFG.oR.nextInt(256) / 255.0F, 1.0F));
   }

   protected final void setResizeHeight(int iResizeHeight) {
      this.iResizeHeight = iResizeHeight;
   }

   public static enum PickerAction {
      ACTIVE_CIVILIZATION_COLOR,
      CUSTOMIZE_ALLIANCE_COLOR,
      MAP_EDITOR_CONTINENT_COLOR,
      MAP_EDITOR_REGION_COLOR,
      CREATE_VASSAL_COLOR,
      COLOR_DIPLOMACY_OWN_PROVINCES,
      COLOR_DIPLOMACY_ALLIANCE,
      COLOR_DIPLOMACY_AT_WAR,
      COLOR_DIPLOMACY_VASSAL,
      COLOR_DIPLOMACY_PACT,
      COLOR_DIPLOMACY_PACT_MAX,
      COLOR_DIPLOMACY_INDEPENDENCE,
      COLOR_DIPLOMACY_NEGATIVE,
      COLOR_DIPLOMACY_POSITIVE,
      COLOR_DIPLOMACY_NEUTRAL,
      COLOR_DIPLOMACY_MILITARY_ACCESS,
      COLOR_DIPLOMACY_DEFENSIVE_PACT,
      EDITOR_RELIGION_COLOR,
      PALLET_OF_COLORS,
      CIV_NAMES_OVER_PROVINCES,
      CIV_NAMES_OVER_PROVINCES_BORDER,
      PROVINCE_SETTINGS_WASTELAND_COLOR,
      PROVINCE_SETTINGS_DISCOVERY_COLOR,
      EDITOR_TERRAIN_COLOR,
      EDITOR_SERVICE_RIBBON_OVERLAY,
      EDITOR_CIV_GAME_COLOR,
      EDITOR_CIV_GAME_COLOR_SR,
      EDITOR_CIV_FLAG_DIVISION_COLOR,
      EDITOR_CIV_FLAG_OVERLAY_COLOR,
      MAP_EDITOR_TRADE_ZONES,
      //new border color action
      PROVINCE_BORDER_COLOR;


      // $FF: synthetic method
      private static PickerAction[] $values() {
         return new PickerAction[]{ACTIVE_CIVILIZATION_COLOR, CUSTOMIZE_ALLIANCE_COLOR, MAP_EDITOR_CONTINENT_COLOR, MAP_EDITOR_REGION_COLOR, CREATE_VASSAL_COLOR, COLOR_DIPLOMACY_OWN_PROVINCES, COLOR_DIPLOMACY_ALLIANCE, COLOR_DIPLOMACY_AT_WAR, COLOR_DIPLOMACY_VASSAL, COLOR_DIPLOMACY_PACT, COLOR_DIPLOMACY_PACT_MAX, COLOR_DIPLOMACY_INDEPENDENCE, COLOR_DIPLOMACY_NEGATIVE, COLOR_DIPLOMACY_POSITIVE, COLOR_DIPLOMACY_NEUTRAL, COLOR_DIPLOMACY_MILITARY_ACCESS, COLOR_DIPLOMACY_DEFENSIVE_PACT, EDITOR_RELIGION_COLOR, PALLET_OF_COLORS, CIV_NAMES_OVER_PROVINCES, CIV_NAMES_OVER_PROVINCES_BORDER, PROVINCE_SETTINGS_WASTELAND_COLOR, PROVINCE_SETTINGS_DISCOVERY_COLOR, EDITOR_TERRAIN_COLOR, EDITOR_SERVICE_RIBBON_OVERLAY, EDITOR_CIV_GAME_COLOR, EDITOR_CIV_GAME_COLOR_SR, EDITOR_CIV_FLAG_DIVISION_COLOR, EDITOR_CIV_FLAG_OVERLAY_COLOR, MAP_EDITOR_TRADE_ZONES, PROVINCE_BORDER_COLOR};
      }
   }

   class Box {
      private int iPosX;
      private int iPosY;
      private int iWidth;
      private int iHeight;
      private boolean visible = true;

      protected Box(int iPosX, int iPosY, int iWidth, int iHeight) {
         this.iPosX = iPosX;
         this.iPosY = iPosY;
         this.iWidth = iWidth;
         this.iHeight = iHeight;
      }

      protected final int getPosX() {
         return this.iPosX;
      }

      protected final void setPosX(int iPosX) {
         this.iPosX = iPosX;
      }

      protected final int getPosY() {
         return this.iPosY;
      }

      protected final void setPosY(int iPosY) {
         this.iPosY = iPosY;
      }

      protected final int getWidth() {
         return this.iWidth;
      }

      protected final int getHeight() {
         return this.iHeight;
      }

      protected final void setWidth(int iWidth) {
         this.iWidth = iWidth;
      }

      protected final void setHeight(int iHeight) {
         this.iHeight = iHeight;
      }

      protected final void setVisible(boolean visible) {
         this.visible = visible;
      }

      protected final boolean getVisible() {
         return this.visible;
      }
   }
}
