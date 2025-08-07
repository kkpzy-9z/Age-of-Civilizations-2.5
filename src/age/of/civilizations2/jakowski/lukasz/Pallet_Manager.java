package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Pallet_Manager {
   protected static int NUM_OF_COLORS = 500;
   private int iActivePalletID = 0;
   private List<String> lPalletsTags;
   private List<Boolean> isInternal;
   private int iNumOfPallets = 0;
   private List<List<Color>> lSampleColors;
   private List<Integer> lColorsInPallet;
   private final int SAMPLE_COLORS_SIZE = 10;

   protected Pallet_Manager() {
      this.updatePalletsOfCivsColorsTags();
   }

   protected final void updatePalletsOfCivsColorsTags() {
      try {
         FileHandle tempFileT = Gdx.files.internal("game/pallets_of_civs_colors/Age_of_Civilizations");
         String tempT = tempFileT.readString();
         String[] tagsSPLITED = tempT.split(";");
         this.lPalletsTags = new ArrayList<>();
         this.isInternal = new ArrayList<>();

         for(int i = 0; i < tagsSPLITED.length; ++i) {
            this.lPalletsTags.add(tagsSPLITED[i]);
            this.isInternal.add(true);
         }

         FileHandle tempFileT_Local;
         if (CFG.readLocalFiles()) {
            try {
               tempFileT_Local = Gdx.files.local("game/pallets_of_civs_colors/Age_of_Civilizations");
               String tempT_Local = tempFileT_Local.readString();
               String[] tagsSPLITED_Local = tempT_Local.split(";");

               for(int i = 0; i < tagsSPLITED_Local.length; ++i) {
                  this.lPalletsTags.add(tagsSPLITED_Local[i]);
                  this.isInternal.add(false);
               }
            } catch (GdxRuntimeException var15) {
            }
         }

         this.lSampleColors = new ArrayList<>();
         this.lColorsInPallet = new ArrayList<>();
         this.iNumOfPallets = this.lPalletsTags.size();

         for(int i = 0; i < this.iNumOfPallets; ++i) {
            FileHandle tempFileT2;
            if ((Boolean)this.isInternal.get(i)) {
               tempFileT2 = Gdx.files.internal("game/pallets_of_civs_colors/" + (String)this.lPalletsTags.get(i) + "/" + "Age_of_Civilizations");
            } else {
               tempFileT2 = Gdx.files.local("game/pallets_of_civs_colors/" + (String)this.lPalletsTags.get(i) + "/" + "Age_of_Civilizations");
            }

            String tempT2 = tempFileT2.readString();
            String[] tagsSPLITED2 = tempT2.split(";");
            this.lColorsInPallet.add(tagsSPLITED2.length);
            List tempSampleColors = new ArrayList<>();

            for(int j = 0; j < 10 && j < tagsSPLITED2.length; ++j) {
               try {
                  try {
                     PalletOfCivsColors_Civ_GameData tempColor;
                     if ((Boolean)this.isInternal.get(i)) {
                        tempColor = (PalletOfCivsColors_Civ_GameData)CFG.deserialize(Gdx.files.internal("game/pallets_of_civs_colors/" + (String)this.lPalletsTags.get(i) + "/" + tagsSPLITED2[j]).readBytes());
                     } else {
                        tempColor = (PalletOfCivsColors_Civ_GameData)CFG.deserialize(Gdx.files.local("game/pallets_of_civs_colors/" + (String)this.lPalletsTags.get(i) + "/" + tagsSPLITED2[j]).readBytes());
                     }

                     tempSampleColors.add(new Color(tempColor.getColor().getR(), tempColor.getColor().getG(), tempColor.getColor().getB(), 1.0F));
                  } catch (ClassNotFoundException var12) {
                  } catch (IOException var13) {
                  }
               } catch (GdxRuntimeException var14) {
               }
            }

            this.lSampleColors.add(tempSampleColors);
         }

         tempFileT_Local = null;
      } catch (GdxRuntimeException var16) {
      }

   }

   protected final void loadCivilizationsPaletteOfColors(int nPaletteID) {
      if (nPaletteID == 0) {
         this.loadCivilizationStandardColors();
      } else {
         for(int i = 1; i < CFG.game.getCivsSize(); ++i) {
            FileHandle file = null;

            try {
               if ((Boolean)this.isInternal.get(nPaletteID - 1)) {
                  file = Gdx.files.internal("game/pallets_of_civs_colors/" + (String)this.lPalletsTags.get(nPaletteID - 1) + "/" + CFG.game.getCiv(i).getCivTag());
               } else {
                  file = Gdx.files.local("game/pallets_of_civs_colors/" + (String)this.lPalletsTags.get(nPaletteID - 1) + "/" + CFG.game.getCiv(i).getCivTag());
               }

               try {
                  PalletOfCivsColors_Civ_GameData nCivColor = (PalletOfCivsColors_Civ_GameData)CFG.deserialize(file.readBytes());
                  CFG.game.getCiv(i).setR((int)(nCivColor.getColor().getR() * 255.0F));
                  CFG.game.getCiv(i).setG((int)(nCivColor.getColor().getG() * 255.0F));
                  CFG.game.getCiv(i).setB((int)(nCivColor.getColor().getB() * 255.0F));
               } catch (ClassNotFoundException var5) {
                  this.loadCivilizationStandardColor(i);
               } catch (IOException var6) {
                  this.loadCivilizationStandardColor(i);
               }
            } catch (GdxRuntimeException var7) {
               this.loadCivilizationStandardColor(i);
            }
         }
          for(int i = 1; i < CFG.game.getCivsSize(); ++i) {
              CFG.game.getCiv(i).updateVassalCivilizationsColor();
          }
      }

   }

   protected final void loadCivilizationPalletColor(int nPaletteID, int nCivID) {
      try {
         FileHandle file;
         if ((Boolean)this.isInternal.get(nPaletteID - 1)) {
            file = Gdx.files.internal("game/pallets_of_civs_colors/" + (String)this.lPalletsTags.get(nPaletteID - 1) + "/" + CFG.game.getCiv(nCivID).getCivTag());
         } else {
            file = Gdx.files.local("game/pallets_of_civs_colors/" + (String)this.lPalletsTags.get(nPaletteID - 1) + "/" + CFG.game.getCiv(nCivID).getCivTag());
         }

         try {
            PalletOfCivsColors_Civ_GameData nCivColor = (PalletOfCivsColors_Civ_GameData)CFG.deserialize(file.readBytes());
            CFG.game.getCiv(nCivID).setR((int)(nCivColor.getColor().getR() * 255.0F));
            CFG.game.getCiv(nCivID).setG((int)(nCivColor.getColor().getG() * 255.0F));
            CFG.game.getCiv(nCivID).setB((int)(nCivColor.getColor().getB() * 255.0F));
         } catch (ClassNotFoundException var5) {
            this.loadCivilizationStandardColor(nCivID);
         } catch (IOException var6) {
            this.loadCivilizationStandardColor(nCivID);
         }
      } catch (GdxRuntimeException var7) {
         this.loadCivilizationStandardColor(nCivID);
      }
   }

   protected final void loadCivilizationStandardColors() {
      for(int i = 1; i < CFG.game.getCivsSize(); ++i) {
         this.loadCivilizationStandardColor(i);
      }
      for(int i = 1; i < CFG.game.getCivsSize(); ++i) {
          CFG.game.getCiv(i).updateVassalCivilizationsColor();
      }
   }

   protected final void loadCivilizationStandardColor(int nCivID) {
      try {
         try {
            FileHandle fileCiv = Gdx.files.internal("game/civilizations/" + CFG.game.getCiv(nCivID).getCivTag());
            Civilization_GameData3 tempCivGameData = (Civilization_GameData3)CFG.deserialize(fileCiv.readBytes());
            CFG.game.getCiv(nCivID).setR(tempCivGameData.getR());
            CFG.game.getCiv(nCivID).setG(tempCivGameData.getG());
            CFG.game.getCiv(nCivID).setB(tempCivGameData.getB());
         } catch (GdxRuntimeException var18) {
            try {
               FileHandle fileCiv = Gdx.files.local("game/civilizations/" + CFG.game.getCiv(nCivID).getCivTag());
               Civilization_GameData3 tempCivGameData = (Civilization_GameData3)CFG.deserialize(fileCiv.readBytes());
               CFG.game.getCiv(nCivID).setR(tempCivGameData.getR());
               CFG.game.getCiv(nCivID).setG(tempCivGameData.getG());
               CFG.game.getCiv(nCivID).setB(tempCivGameData.getB());
            } catch (GdxRuntimeException var17) {
               try {
                  FileHandle fileCiv = Gdx.files.internal("game/civilizations/" + CFG.ideologiesManager.getRealTag(CFG.game.getCiv(nCivID).getCivTag()));
                  Civilization_GameData3 tempCivGameData = (Civilization_GameData3)CFG.deserialize(fileCiv.readBytes());
                  CFG.game.getCiv(nCivID).setR(tempCivGameData.getR());
                  CFG.game.getCiv(nCivID).setG(tempCivGameData.getG());
                  CFG.game.getCiv(nCivID).setB(tempCivGameData.getB());
               } catch (GdxRuntimeException var16) {
                  try {
                     FileHandle fileCiv = Gdx.files.local("game/civilizations/" + CFG.ideologiesManager.getRealTag(CFG.game.getCiv(nCivID).getCivTag()));
                     Civilization_GameData3 tempCivGameData = (Civilization_GameData3)CFG.deserialize(fileCiv.readBytes());
                     CFG.game.getCiv(nCivID).setR(tempCivGameData.getR());
                     CFG.game.getCiv(nCivID).setG(tempCivGameData.getG());
                     CFG.game.getCiv(nCivID).setB(tempCivGameData.getB());
                  } catch (GdxRuntimeException var15) {
                     try {
                        FileHandle fileCiv = Gdx.files.internal("game/civilizations_editor/" + CFG.game.getCiv(nCivID).getCivTag() + "/" + CFG.game.getCiv(nCivID).getCivTag());
                        Civilization_GameData3 tempCivGameData = (Civilization_GameData3)CFG.deserialize(fileCiv.readBytes());
                        CFG.game.getCiv(nCivID).setR(tempCivGameData.getR());
                        CFG.game.getCiv(nCivID).setG(tempCivGameData.getG());
                        CFG.game.getCiv(nCivID).setB(tempCivGameData.getB());
                     } catch (GdxRuntimeException var14) {
                        try {
                           FileHandle fileCiv = Gdx.files.local("game/civilizations_editor/" + CFG.game.getCiv(nCivID).getCivTag() + "/" + CFG.game.getCiv(nCivID).getCivTag());
                           Civilization_GameData3 tempCivGameData = (Civilization_GameData3)CFG.deserialize(fileCiv.readBytes());
                           CFG.game.getCiv(nCivID).setR(tempCivGameData.getR());
                           CFG.game.getCiv(nCivID).setG(tempCivGameData.getG());
                           CFG.game.getCiv(nCivID).setB(tempCivGameData.getB());
                        } catch (GdxRuntimeException var13) {
                           try {
                              FileHandle fileCiv = Gdx.files.internal("game/civilizations_editor/" + CFG.ideologiesManager.getRealTag(CFG.game.getCiv(nCivID).getCivTag()) + "/" + CFG.ideologiesManager.getRealTag(CFG.game.getCiv(nCivID).getCivTag()));
                              Civilization_GameData3 tempCivGameData = (Civilization_GameData3)CFG.deserialize(fileCiv.readBytes());
                              CFG.game.getCiv(nCivID).setR(tempCivGameData.getR());
                              CFG.game.getCiv(nCivID).setG(tempCivGameData.getG());
                              CFG.game.getCiv(nCivID).setB(tempCivGameData.getB());
                           } catch (GdxRuntimeException var12) {
                              try {
                                 FileHandle fileCiv = Gdx.files.local("game/civilizations_editor/" + CFG.ideologiesManager.getRealTag(CFG.game.getCiv(nCivID).getCivTag()) + "/" + CFG.ideologiesManager.getRealTag(CFG.game.getCiv(nCivID).getCivTag()));
                                 Civilization_GameData3 tempCivGameData = (Civilization_GameData3)CFG.deserialize(fileCiv.readBytes());
                                 CFG.game.getCiv(nCivID).setR(tempCivGameData.getR());
                                 CFG.game.getCiv(nCivID).setG(tempCivGameData.getG());
                                 CFG.game.getCiv(nCivID).setB(tempCivGameData.getB());
                              } catch (GdxRuntimeException var11) {
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      } catch (ClassNotFoundException var19) {
      } catch (IOException var20) {
      }

   }

   protected final void drawSampleColors(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, int nHeight, int nPalletID, boolean isActive) {
      try {
         oSB.setColor((Color)((List)this.lSampleColors.get(nPalletID)).get(0));
         ImageManager.getImage(Images.pix255_255_255).draw(oSB, nPosX, nPosY - ImageManager.getImage(Images.pix255_255_255).getHeight(), nWidth, nHeight);
         int i = 1;

         for(int iSize = ((List)this.lSampleColors.get(nPalletID)).size(); i < iSize; ++i) {
            oSB.setColor((Color)((List)this.lSampleColors.get(nPalletID)).get(i));
            ImageManager.getImage(Images.pix255_255_255).draw(oSB, nPosX + nWidth / iSize * i, nPosY - ImageManager.getImage(Images.pix255_255_255).getHeight(), nWidth / iSize, nHeight);
         }

         this.drawSampleColors_BORDER(oSB, nPosX, nPosY, nWidth, nHeight, nPalletID, isActive);
      } catch (IndexOutOfBoundsException var10) {
      } catch (NullPointerException var11) {
      } catch (GdxRuntimeException var12) {
      } catch (ArithmeticException var13) {
      }

   }

   protected final void drawSampleColors_Standard(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, int nHeight, int nPalletID, boolean isActive) {
      oSB.setColor(new Color(0.35675678F, 0.0F, 0.28F, 1.0F));
      ImageManager.getImage(Images.pix255_255_255).draw(oSB, nPosX, nPosY - ImageManager.getImage(Images.pix255_255_255).getHeight(), nWidth, nHeight);

      for(int i = 1; i < 10; ++i) {
         oSB.setColor(new Color(0.032432433F * (float)(10 - i + 1), 0.0F, 0.28F, 1.0F));
         ImageManager.getImage(Images.pix255_255_255).draw(oSB, nPosX + nWidth / 10 * i, nPosY - ImageManager.getImage(Images.pix255_255_255).getHeight(), nWidth / 10, nHeight);
      }

      this.drawSampleColors_BORDER(oSB, nPosX, nPosY, nWidth, nHeight, nPalletID, isActive);
   }

   protected final void drawSampleColors_BORDER(SpriteBatch oSB, int nPosX, int nPosY, int nWidth, int nHeight, int nPalletID, boolean isActive) {
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, isActive ? 0.95F : 0.7F));
      ImageManager.getImage(Images.gradient).draw(oSB, nPosX, nPosY - ImageManager.getImage(Images.gradient).getHeight(), nWidth, nHeight / 4);
      ImageManager.getImage(Images.gradient).draw(oSB, nPosX, nPosY - ImageManager.getImage(Images.gradient).getHeight() + nHeight - nHeight / 4, nWidth, nHeight / 4, false, true);
      oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.55F));
      CFG.drawRect(oSB, nPosX, nPosY - 1, nWidth, nHeight);
      oSB.setColor(isActive ? CFG.COLOR_FLAG_FRAME : CFG.COLOR_CREATE_NEW_GAME_BOX_PLAYERS);
      CFG.drawRect(oSB, nPosX - 1, nPosY - 2, nWidth + 2, nHeight + 2);
      oSB.setColor(Color.WHITE);
   }

   protected final String getPalletTag(int i) {
      return (String)this.lPalletsTags.get(i);
   }

   protected final boolean getIsInternal(int i) {
      return (Boolean)this.isInternal.get(i);
   }

   protected final int getNumOfPallets() {
      return this.iNumOfPallets;
   }

   protected final int getActivePalletID() {
      return this.iActivePalletID;
   }

   protected final void setActivePalletID(int iActivePalletID) {
      this.iActivePalletID = iActivePalletID;
   }

   protected final int getNumOfColorsInPallet(int i) {
      return (Integer)this.lColorsInPallet.get(i);
   }
}
