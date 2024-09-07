/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  age.of.civilizations2.jakowski.lukasz.CFG
 *  age.of.civilizations2.jakowski.lukasz.Game_NewGame
 *  age.of.civilizations2.jakowski.lukasz.Game_Render
 *  age.of.civilizations2.jakowski.lukasz.ImageManager
 *  age.of.civilizations2.jakowski.lukasz.Images
 *  age.of.civilizations2.jakowski.lukasz.Map_Scale
 *  age.of.civilizations2.jakowski.lukasz.Menu
 *  age.of.civilizations2.jakowski.lukasz.RTS
 *  age.of.civilizations2.jakowski.lukasz.SaveManager
 *  age.of.civilizations2.jakowski.lukasz.SliderMenu
 *  age.of.civilizations2.jakowski.lukasz.Start_The_Game_Data
 *  age.of.civilizations2.jakowski.lukasz.TechnologyManager
 *  com.badlogic.gdx.Gdx
 *  com.badlogic.gdx.files.FileHandle
 *  com.badlogic.gdx.graphics.Color
 *  com.badlogic.gdx.graphics.g2d.SpriteBatch
 */
package age.of.civilizations2.jakowski.lukasz;

import age.of.civilizations2.jakowski.lukasz.CFG;
import age.of.civilizations2.jakowski.lukasz.Game_NewGame;
import age.of.civilizations2.jakowski.lukasz.Game_Render;
import age.of.civilizations2.jakowski.lukasz.ImageManager;
import age.of.civilizations2.jakowski.lukasz.Images;
import age.of.civilizations2.jakowski.lukasz.Map_Scale;
import age.of.civilizations2.jakowski.lukasz.Menu;
import age.of.civilizations2.jakowski.lukasz.RTS;
import age.of.civilizations2.jakowski.lukasz.SaveManager;
import age.of.civilizations2.jakowski.lukasz.SliderMenu;
import age.of.civilizations2.jakowski.lukasz.Start_The_Game_Data;
import age.of.civilizations2.jakowski.lukasz.TechnologyManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;

public class Menu_LoadSave
extends SliderMenu {
    public static int iLoadID;
    public static int loadStepID;
    public static int tFileID;
    public String[] tSplted;

    protected Menu_LoadSave() {
        ArrayList menuElements = new ArrayList();
        this.initMenu(null, 0, 0, CFG.GAME_WIDTH, CFG.GAME_HEIGHT, menuElements);
        loadStepID = 0;
    }

    protected void draw(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean sliderMenuIsActive) {
        oSB.setColor(new Color(0.0f, 0.0f, 0.0f, 0.6f));
        ImageManager.getImage((int)Images.gradient).draw(oSB, iTranslateX, -ImageManager.getImage((int)Images.gradient).getHeight() + iTranslateY, CFG.GAME_WIDTH, CFG.PADDING * 3);
        ImageManager.getImage((int)Images.gradient).draw(oSB, iTranslateX, CFG.GAME_HEIGHT - ImageManager.getImage((int)Images.gradient).getHeight() - CFG.PADDING * 3 + iTranslateY, CFG.GAME_WIDTH, CFG.PADDING * 3, false, true);
        CFG.drawJakowskiGames_RIGHT_BOT((SpriteBatch)oSB, (int)iTranslateX);
        CFG.drawVersion_LEFT_BOT((SpriteBatch)oSB, (int)iTranslateX);
        CFG.setRender_3((boolean)true);
        this.loadSave();
    }

    public final void loadSave() {
        block60: {
            try {
                if (loadStepID == 0) {
                    FileHandle file2 = CFG.readLocalFiles() ? Gdx.files.local("saves/games/" + CFG.map.getFile_ActiveMap_Path() + "Age_of_Civilizations") : Gdx.files.internal("saves/games/" + CFG.map.getFile_ActiveMap_Path() + "Age_of_Civilizations");
                    String tempTags = file2.readString();
                    this.tSplted = tempTags.split(";");
                    break block60;
                }
                if (loadStepID == 1) {
                    CFG.game.loadSavedGame_NEW_1(iLoadID, this.tSplted);
                    break block60;
                }
                if (loadStepID == 2) {
                    CFG.game.loadSavedGame_NEW_2(iLoadID, this.tSplted);
                    break block60;
                }
                if (loadStepID == 3) {
                    CFG.game.loadSavedGame_NEW_3(iLoadID, this.tSplted);
                    break block60;
                }
                if (loadStepID == 4) {
                    CFG.game.loadSavedGame_NEW_4(iLoadID, this.tSplted);
                    break block60;
                }
                if (loadStepID == 5) {
                    CFG.game.loadSavedGame_NEW_5(iLoadID, this.tSplted);
                    break block60;
                }
                if (loadStepID == 6) {
                    CFG.game.loadSavedGame_NEW_6(iLoadID, this.tSplted);
                    break block60;
                }
                if (loadStepID == 7) {
                    CFG.game.loadSavedGame_NEW_7(iLoadID, this.tSplted);
                    break block60;
                }
                if (loadStepID == 8) {
                    CFG.game.loadSavedGame_NEW_8(iLoadID, this.tSplted);
                    break block60;
                }
                if (loadStepID == 9) {
                    CFG.game.loadSavedGame_NEW_9(iLoadID, this.tSplted);
                    break block60;
                }
                if (loadStepID == 10) {
                    CFG.game.loadSavedGame_NEW_10(iLoadID, this.tSplted);
                    break block60;
                }
                if (loadStepID == 11) {
                    CFG.game.loadSavedGame_NEW_11(iLoadID, this.tSplted);
                    break block60;
                }
                if (loadStepID == 12) {
                    CFG.game.loadSavedGame_NEW_12(iLoadID, this.tSplted);
                    break block60;
                }
                if (loadStepID == 13) {
                    CFG.game.loadSavedGame_NEW_13(iLoadID, this.tSplted);
                    break block60;
                }
                if (loadStepID == 14) {
                    CFG.game.loadSavedGame_NEW_14(iLoadID, this.tSplted);
                    break block60;
                }
                if (loadStepID == 15) {
                    CFG.game.loadSavedGame_NEW_15(iLoadID, this.tSplted);
                    break block60;
                }
                if (loadStepID == 16) {
                    CFG.game.loadSavedGame_NEW_16(iLoadID, this.tSplted);
                    break block60;
                }
                if (loadStepID == 17) {
                    CFG.game.loadSavedGame_NEW_17(iLoadID, this.tSplted);
                    break block60;
                }
                if (loadStepID == 18) {
                    CFG.game.loadSavedGame_NEW_18(iLoadID, this.tSplted);
                    break block60;
                }
                if (loadStepID == 19) {
                    CFG.game.loadSavedGame_NEW_19(iLoadID, this.tSplted);
                    break block60;
                }
                if (loadStepID == 20) {
                    CFG.game.loadSavedGame_NEW_20(iLoadID, this.tSplted);
                    break block60;
                }
                if (loadStepID == 21) {
                    CFG.game.loadSavedGame_NEW_21(iLoadID, this.tSplted);
                    break block60;
                }
                if (loadStepID == 22) {
                    CFG.game.sortCivilizationsAZ();
                    Game_NewGame.buildFormableCivilizations();
                    break block60;
                }
                if (loadStepID == 23) {
                    if (CFG.SPECTATOR_MODE) {
                        Game_NewGame.newGame_InitPlayers_SpectatorMode();
                    } else {
                        int i;
                        for (i = 0; i < CFG.game.getCivsSize(); ++i) {
                            CFG.game.getCiv(i).setControlledByPlayer(false);
                        }
                        for (i = 0; i < CFG.game.getPlayersSize(); ++i) {
                            CFG.game.getCiv(CFG.game.getPlayer(i).getCivID()).setControlledByPlayer(true);
                        }
                    }
                    break block60;
                }
                if (loadStepID == 24) {
                    CFG.oAI.updateExpand();
                    break block60;
                }
                if (loadStepID == 25) {
                    try {
                        CFG.map.getMapCoordinates().centerToProvinceID(CFG.game.getCiv(CFG.game.getPlayer(0).getCivID()).getCapitalProvinceID());
                    }
                    catch (IndexOutOfBoundsException i) {
                        // empty catch block
                    }
                    for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
                        CFG.game.getCiv(i).buildNumOfUnits();
                    }
                    break block60;
                }
                if (loadStepID == 26) {
                    CFG.map.getMapCoordinates().setDisableMovingMap(false);
                    if (CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCapitalProvinceID() >= 0) {
                        CFG.game.setActiveProvinceID(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCapitalProvinceID());
                    }
                } else if (loadStepID == 27) {
                    CFG.gameAction.updateCivsMovementPoints();
                    CFG.gameAction.updateIsSupplied();
                } else if (loadStepID == 28) {
                    Game_NewGame.build_ArmyInAnotherProvince();
                } else if (loadStepID == 29) {
                    if (CFG.FOG_OF_WAR > 0) {
                        if (CFG.FOG_OF_WAR == 2) {
                            int i;
                            for (i = 0; i < CFG.game.getPlayersSize(); ++i) {
                                CFG.PLAYER_TURNID = i;
                                CFG.gameAction.buildFogOfWar(i);
                            }
                            CFG.PLAYER_TURNID = 0;
                            for (i = 0; i < CFG.game.getProvincesSize(); ++i) {
                                CFG.game.getProvince(i).updateProvinceBorder();
                            }
                            Game_Render.updateDrawCivRegionNames_FogOfWar();
                        } else {
                            for (int i = 0; i < CFG.game.getPlayersSize(); ++i) {
                                CFG.PLAYER_TURNID = i;
                                CFG.gameAction.buildFogOfWar(i);
                            }
                            CFG.PLAYER_TURNID = 0;
                        }
                    }
                } else if (loadStepID == 30) {
                    for (int i = 0; i < CFG.game.getProvincesSize(); ++i) {
                        CFG.game.getProvince(i).updateDrawArmy();
                    }
                    CFG.gameAction.moveRegroupArmy();
                } else if (loadStepID == 31) {
                    CFG.gameAction.updateCivsHappiness();
                    CFG.game_NextTurnUpdate.updateProvinceStability();
                    Game_NewGame.updateBudgetSpendings();
                    CFG.game_NextTurnUpdate.updateInflationPeakValue();
                    CFG.game_NextTurnUpdate.updatePlayableProvinces();
                    TechnologyManager.updateAverageTechLevel();
                } else if (loadStepID == 32) {
                    if (CFG.SANDBOX_MODE && !CFG.SPECTATOR_MODE) {
                        Game_NewGame.sandboxMode();
                    }
                    CFG.setActiveCivInfo((int)0);
                    CFG.map.getMapBG().disposeMinimapOfCivilizations();
                    SaveManager.gameCanBeContinued = true;
                } else if (loadStepID == 33) {
                    RTS.reset();
                    CFG.game.disableDrawCivlizationsRegions_Players();
                    CFG.viewsManager.disableAllViews();
                    if (CFG.map.getMapScale().getCurrentScale() < Map_Scale.STANDARD_SCALE) {
                        CFG.map.getMapScale().setCurrentScale(Map_Scale.STANDARD_SCALE);
                    }
                    CFG.EDITOR_ACTIVE_GAMEDATA_TAG = CFG.langManager.get("SavedGame");
                    CFG.startTheGameData = new Start_The_Game_Data(false);
                    CFG.menuManager.setViewIDWithoutAnimation(Menu.eSTART_THE_GAME);
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        ++loadStepID;
    }

    static {
        loadStepID = 0;
        tFileID = 0;
    }
}
