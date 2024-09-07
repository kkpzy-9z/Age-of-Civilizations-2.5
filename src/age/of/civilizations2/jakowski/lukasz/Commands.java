/*
 * Decompiled with CFR 0.152.
 */
package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Commands {
    protected static final int CONSOLE_LIMIT = 650;
    protected static List<String> sConsole = new ArrayList<String>();
    protected static List<Point_XY> lShit = new ArrayList<Point_XY>();
    protected static long lShitTime = 0L;

    Commands() {
    }

    protected static final void addMessage(String nMess) {
        sConsole.add(nMess);
        if (sConsole.size() > 650) {
            sConsole.remove(0);
        }
    }

    protected static final void execute(String nCommand) {
        block120: {
            if (nCommand.length() == 0) {
                return;
            }
            Commands.addMessage("");
            Commands.addMessage("#" + nCommand);
            String[] tempCommand = nCommand.toLowerCase().split(" ");
            try {
                if (tempCommand.length <= 0) break block120;
                if (tempCommand[0].equals("console")) {
                    CFG.menuManager.setVisible_InGame_FlagAction_Console(!CFG.menuManager.getVisible_InGame_FlagAction_Console());
                    if (CFG.menuManager.getVisible_InGame_FlagAction_Console()) {
                        CFG.toast.setInView("Hello");
                    }
                    return;
                }
                if (tempCommand[0].equals("info")) {
                    Commands.addMessage("FramesPerSecond: " + Gdx.graphics.getFramesPerSecond());
                    Commands.addMessage("Width: " + Gdx.graphics.getWidth());
                    Commands.addMessage("Height: " + Gdx.graphics.getHeight());
                    Commands.addMessage("PpiX: " + Gdx.graphics.getPpiX());
                    Commands.addMessage("PpiY: " + Gdx.graphics.getPpiY());
                    Commands.addMessage("Density: " + Gdx.graphics.getDensity());
                    Commands.addMessage("XHDPI: " + CFG.XHDPI);
                    Commands.addMessage("XXHDPI: " + CFG.XXHDPI);
                    Commands.addMessage("XXXHDPI: " + CFG.XXXHDPI);
                    Commands.addMessage("XXXXHDPI: " + CFG.XXXXHDPI);
                    return;
                }
                if (tempCommand[0].equals("debug")) {
                    CFG.DEBUG_MODE = !CFG.DEBUG_MODE;
                    Commands.addMessage(CFG.langManager.get(CFG.langManager.get("DEBUG") + ": " + (CFG.DEBUG_MODE ? CFG.langManager.get("Enabled") : CFG.langManager.get("Disabled"))));
                    CFG.toast.setInView(CFG.langManager.get(CFG.langManager.get("DEBUG") + ": " + (CFG.DEBUG_MODE ? CFG.langManager.get("Enabled") : CFG.langManager.get("Disabled"))));
                    return;
                }
                if (tempCommand[0].equals("neutral")) {
                    for (int i = 0; i < CFG.game.getProvincesSize(); ++i) {
                        if (CFG.game.getProvince(i).getWasteland() >= 0 || CFG.game.getProvince(i).getCivID() != 0 || CFG.game.getProvince(i).getSeaProvince()) continue;
                        CFG.game.setActiveProvinceID(i);
                        CFG.map.getMapCoordinates().centerToProvinceID(CFG.game.getActiveProvinceID());
                        break;
                    }
                    return;
                }
                if (tempCommand[0].equals("center")) {
                    if (tempCommand.length > 1) {
                        try {
                            int tempID = Integer.parseInt(tempCommand[1]);
                            if (tempID < CFG.game.getProvincesSize()) {
                                CFG.map.getMapCoordinates().centerToProvinceID(tempID);
                                CFG.game.setActiveProvinceID(tempID);
                                CFG.toast.setInView(CFG.game.getProvince(tempID).getName());
                            } else {
                                Commands.IllegalCommand();
                            }
                            return;
                        }
                        catch (IllegalArgumentException ex) {
                            Commands.IllegalCommand();
                        }
                        catch (IndexOutOfBoundsException ex) {
                            Commands.IllegalCommand();
                        }
                    } else {
                        CFG.map.getMapScroll().stopScrollingTheMap();
                        CFG.map.getMapScale().setCurrentScale(Map_Scale.MINSCALE);
                        CFG.map.getMapCoordinates().setNewPosX(-((int)((float)(CFG.map.getMapBG().getWidth() / 2) - (float)CFG.GAME_WIDTH / Map_Scale.MINSCALE / 2.0f)));
                        CFG.map.getMapCoordinates().setNewPosY(-((int)((float)(CFG.map.getMapBG().getHeight() / 2) - (float)CFG.GAME_HEIGHT / Map_Scale.MINSCALE / 2.0f)));
                    }
                    return;
                }
                if (tempCommand[0].equals("centerciv")) {
                    if (tempCommand.length > 1) {
                        try {
                            int tempID = Integer.parseInt(tempCommand[1]);
                            if (tempID < CFG.game.getCivsSize() && tempID > 0) {
                                CFG.map.getMapCoordinates().centerToCivilizationBox(tempID, true);
                                CFG.toast.setInView(CFG.game.getCiv(tempID).getCivName());
                            }
                        }
                        catch (IllegalArgumentException ex) {
                            for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
                                if (!tempCommand[1].equals(CFG.game.getCiv(i).getCivName()) && !tempCommand[1].equals(CFG.game.getCiv(i).getCivTag())) continue;
                                CFG.map.getMapCoordinates().centerToCivilizationBox(i, true);
                                CFG.toast.setInView(CFG.game.getCiv(i).getCivName());
                                return;
                            }
                            Commands.IllegalCommand();
                        }
                        catch (IndexOutOfBoundsException ex) {
                            Commands.IllegalCommand();
                        }
                    } else {
                        Commands.IllegalCommand();
                    }
                    return;
                }
                if (tempCommand[0].equals("scale")) {
                    if (tempCommand.length > 1) {
                        try {
                            tempCommand[1] = tempCommand[1].replace(',', '.');
                            float tempS = Float.parseFloat(tempCommand[1]);
                            CFG.map.getMapScale().setCurrentScale(tempS);
                            return;
                        }
                        catch (IllegalArgumentException ex) {
                            Commands.IllegalCommand();
                        }
                    } else {
                        CFG.map.getMapScale().setCurrentScale(1.0f);
                    }
                    return;
                }
                if (tempCommand[0].equals("close") || tempCommand[0].equals("bye")) {
                    if (CFG.menuManager.getVisible_InGame_FlagAction_Console()) {
                        CFG.menuManager.setVisible_InGame_FlagAction_Console(false);
                    }
                    CFG.menuManager.getKeyboard().setVisible(false);
                    return;
                }
                if (tempCommand[0].equals("fps")) {
                    AoCGame.drawFPS = !AoCGame.drawFPS;
                    return;
                }
                if (tempCommand[0].equals("hi") || tempCommand[0].equals("f")) {
                    if (!CFG.menuManager.getVisible_InGame_FlagAction_Console()) {
                        CFG.menuManager.setVisible_InGame_FlagAction_Console(true);
                    }
                    CFG.toast.setInView(CFG.langManager.get("Hello") + "!");
                    Commands.addMessage(CFG.langManager.get("Hello") + "!");
                    return;
                }
                if (tempCommand[0].equals("spin") || tempCommand[0].equals("iss") || tempCommand[0].equals("wheee") || tempCommand[0].equals("whee")) {
                    CFG.map.getMapScroll().setScrollPos(125000, 10);
                    CFG.map.getMapScroll().setScrollPos(10, 10);
                    CFG.menuManager.getKeyboard().setVisible(false);
                    CFG.menuManager.setVisible_InGame_FlagAction(false);
                    CFG.map.getMapScroll().startScrollingTheMap();
                    CFG.toast.setInView(CFG.langManager.get("Wheee") + "!");
                    Commands.addMessage(CFG.langManager.get("Wheee") + "!");
                    return;
                }
                if (tempCommand[0].equals("help") || tempCommand[0].equals("commands")) {
                    if (!CFG.menuManager.getVisible_InGame_FlagAction_Console()) {
                        CFG.menuManager.setVisible_InGame_FlagAction_Console(true);
                    }
                    CFG.toast.setInView(CFG.langManager.get("Help"));
                    Commands.addMessage("#" + CFG.sVERSION + ": " + "1.01415_ELA");
                    Commands.addMessage("");
                    Commands.addMessage("console");
                    Commands.addMessage("close");
                    Commands.addMessage("civ");
                    Commands.addMessage("civs");
                    Commands.addMessage("province");
                    Commands.addMessage("center X");
                    Commands.addMessage("centerciv X");
                    Commands.addMessage("scale X");
                    return;
                }
                if (tempCommand[0].equals("party") || tempCommand[0].equals("fuck") || tempCommand[0].equals("fuk") || tempCommand[0].equals("flags")) {
                    if (!CFG.menuManager.getVisible_InGame_FlagAction_Console()) {
                        CFG.menuManager.setVisible_InGame_FlagAction_Console(true);
                    }
                    Random oR = new Random();
                    lShit.clear();
                    for (int i = 0; i < CFG.GAME_WIDTH + CFG.GAME_HEIGHT; ++i) {
                        lShit.add(new Point_XY(oR.nextInt(CFG.GAME_WIDTH), oR.nextInt(CFG.GAME_HEIGHT)));
                    }
                    lShitTime = System.currentTimeMillis();
                    CFG.toast.setInView(CFG.langManager.get("clear"));
                    CFG.menuManager.getKeyboard().setVisible(false);
                    return;
                }
                if (!CFG.menuManager.getVisible_InGame_FlagAction_Console()) break block120;
                if (tempCommand[0].equals("clear")) {
                    sConsole.clear();
                    lShit.clear();
                    return;
                }
                if (tempCommand[0].equals("Drew Durnil") || tempCommand[0].equals("drew durnil") || tempCommand[0].equals("drewdurnil") || tempCommand[0].equals("drew") || tempCommand[0].equals("Drew") || tempCommand[0].equals("Durnil") || tempCommand[0].equals("durnil") || tempCommand[0].equals("observe") || tempCommand[0].equals("noob") || tempCommand[0].equals("Spectator") || tempCommand[0].equals("spectator")) {
                    CFG.toast.setInView("Games -> New Game -> Options -> Spectactor Mode");
                    CFG.toast.setTimeInView(4500);
                    Commands.addMessage("Games -> New Game -> Options -> Spectator Mode");
                    return;
                }
                if (tempCommand[0].equals("civs") || tempCommand[0].equals("tags")) {
                    for (int i = 1; i < CFG.game.getCivsSize(); ++i) {
                        Commands.addMessage("CIV ID: " + i + ", TAG: " + CFG.game.getCiv(i).getCivTag() + ", " + CFG.game.getCiv(i).getCivName());
                    }
                    return;
                }
                if (tempCommand[0].equals("civ")) {
                    if (CFG.game.getActiveProvinceID() >= 0 && !CFG.game.getProvince(CFG.game.getActiveProvinceID()).getSeaProvince() && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getWasteland() < 0) {
                        Commands.addMessage("CIV ID: " + CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() + ", TAG: " + CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).getCivTag() + ", " + CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).getCivName());
                    } else {
                        Commands.IllegalCommand();
                        CFG.toast.setInView(CFG.langManager.get("ChooseAProvince"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
                        Commands.addMessage(CFG.langManager.get(CFG.langManager.get("ChooseAProvince")));
                        Commands.addMessage("");
                    }
                    return;
                }
                if (tempCommand[0].equals("province")) {
                    if (CFG.game.getActiveProvinceID() >= 0 && !CFG.game.getProvince(CFG.game.getActiveProvinceID()).getSeaProvince() && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getWasteland() < 0) {
                        Commands.addMessage("PROVINCE ID: " + CFG.game.getActiveProvinceID() + ", CIV TAG" + CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).getCivTag());
                        Commands.addMessage("POPULATION: " + CFG.game.getProvince(CFG.game.getActiveProvinceID()).getPopulationData().getPopulation() + ", ECONOMY" + CFG.game.getProvince(CFG.game.getActiveProvinceID()).getEconomy());
                    } else {
                        Commands.IllegalCommand();
                        CFG.toast.setInView(CFG.langManager.get("ChooseAProvince"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
                        Commands.addMessage(CFG.langManager.get(CFG.langManager.get("ChooseAProvince")));
                        Commands.addMessage("");
                    }
                    return;
                }
                if (tempCommand[0].equals("showids")) {
                    CFG.game.buildDrawArmy_ShowIDs();
                    CFG.toast.setInView("showarmy");
                    CFG.toast.setTimeInView(4500);
                    Commands.addMessage(CFG.langManager.get("Disable") + ": showarmy");
                    return;
                }
                if (tempCommand[0].equals("showarmy")) {
                    CFG.game.buildDrawArmy();
                    return;
                }
                if (!CFG.SPECTATOR_MODE && tempCommand[0].equals("addplayer")) {
                    if (CFG.game.getActiveProvinceID() >= 0 && !CFG.game.getProvince(CFG.game.getActiveProvinceID()).getSeaProvince() && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getWasteland() < 0 && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID() > 0 && !CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).getControlledByPlayer()) {
                        if (CFG.SPECTATOR_MODE) {
                            CFG.SPECTATOR_MODE = false;
                            if (CFG.game.getPlayersSize() == 1) {
                                CFG.game.removePlayer(0);
                            }
                        }
                        CFG.game.addPlayer(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID());
                        CFG.gameAction.buildFogOfWar(CFG.game.getPlayersSize() - 1);
                        if (CFG.FOG_OF_WAR == 2) {
                            CFG.game.getPlayer(CFG.game.getPlayersSize() - 1).buildMetProvincesAndCivs();
                        }
                        CFG.game.getPlayer(CFG.game.getPlayersSize() - 1).loadPlayersFlag();
                        Commands.addMessage(CFG.langManager.get("Added") + ": " + CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).getCivName());
                        return;
                    }
                    Commands.IllegalCommand();
                    CFG.toast.setInView(CFG.langManager.get("ChooseAProvince"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
                    Commands.addMessage(CFG.langManager.get(CFG.langManager.get("ChooseAProvince")));
                    Commands.addMessage("");
                    break block120;
                }
                if (tempCommand[0].equals("addciv")) {
                    if (tempCommand.length > 1) {
                        if (CFG.game.getActiveProvinceID() >= 0 && !CFG.game.getProvince(CFG.game.getActiveProvinceID()).getSeaProvince() && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getWasteland() < 0 && !CFG.game.getProvince(CFG.game.getActiveProvinceID()).getIsCapital()) {
                            int i;
                            for (i = 1; i < CFG.game.getCivsSize(); ++i) {
                                if (!CFG.game.getCiv(i).getCivTag().equals(tempCommand[1])) continue;
                                Commands.IllegalCommand();
                                Commands.addMessage(CFG.game.getCiv(i).getCivName() + ": IS IN THE GAME");
                                Commands.addMessage("");
                                return;
                            }
                            CFG.game.getProvince(CFG.game.getActiveProvinceID()).updateArmy(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID(0), 0);
                            CFG.game.createScenarioAddCivilization(tempCommand[1], CFG.game.getActiveProvinceID(), false, true, true);
                            if (CFG.FOG_OF_WAR == 2) {
                                for (i = 0; i < CFG.game.getPlayersSize(); ++i) {
                                    CFG.game.getPlayer(i).addMetCivilization(true);
                                }
                            }
                            int tempPop = CFG.game.getProvince(CFG.game.getActiveProvinceID()).getPopulationData().getPopulation();
                            CFG.game.getProvince(CFG.game.getActiveProvinceID()).getPopulationData().clearData();
                            CFG.game.getProvince(CFG.game.getActiveProvinceID()).getPopulationData().setPopulationOfCivID(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID(), tempPop);
                            CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).setMoney(100L);
                            CFG.gameAction.updateCivsMovementPoints(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID());
                            CFG.gameAction.updateCivsDiplomacyPoints(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID());
                            CFG.gameAction.buildRank_Score(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID());
                            int tActiveProvince = CFG.game.getActiveProvinceID();
                            CFG.game.setActiveProvinceID(-1);
                            CFG.game.setActiveProvinceID(tActiveProvince);
                            Commands.addMessage(CFG.langManager.get("Added") + ": " + CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).getCivName());
                        } else {
                            Commands.IllegalCommand();
                            CFG.toast.setInView(CFG.langManager.get("ChooseAProvince"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
                            Commands.addMessage(CFG.langManager.get(CFG.langManager.get("ChooseAProvince")));
                            Commands.addMessage("");
                        }
                    } else {
                        Commands.IllegalCommand();
                    }
                    return;
                }
                if (tempCommand[0].equals("technology")  || tempCommand[0].equals("tech")) {
                    if (CFG.game.getActiveProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getWasteland() < 0 && !CFG.game.getProvince(CFG.game.getActiveProvinceID()).getSeaProvince()) {
                        if (tempCommand.length > 1) {
                            try {
                                int tempTech = Integer.parseInt(tempCommand[1]);
                                if (tempTech > 100) {
                                    tempTech = 100;
                                } else if (tempTech < 1) {
                                    tempTech = 1;
                                }
                                CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).setTechnologyLevel((float)tempTech / 100.0f);
                                Commands.addMessage(Commands.cheatMess() + CFG.langManager.get("Technology") + ": " + CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).getTechnologyLevel() + ", " + CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).getCivName());
                                Commands.addMessage("");
                                int tActiveProvince = CFG.game.getActiveProvinceID();
                                CFG.game.setActiveProvinceID(-1);
                                CFG.game.setActiveProvinceID(tActiveProvince);
                                CFG.toast.setInView(Commands.cheatMess() + CFG.langManager.get("Technology"));
                            }
                            catch (IllegalArgumentException ex) {
                                Commands.IllegalCommand();
                            }
                        } else {
                            Commands.IllegalCommand();
                        }
                    } else {
                        Commands.IllegalCommand();
                        CFG.toast.setInView(CFG.langManager.get("ChooseAProvince"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
                        Commands.addMessage(CFG.langManager.get(CFG.langManager.get("ChooseAProvince")));
                        Commands.addMessage("");
                    }
                    return;
                }
                if (tempCommand[0].equals("population") || tempCommand[0].equals("pop")) {
                    int tPopulation = Integer.parseInt(tempCommand[1]);
                    if (tPopulation >= 0) {
                        if (CFG.game.getActiveProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getWasteland() < 0 && !CFG.game.getProvince(CFG.game.getActiveProvinceID()).getSeaProvince()) {
                            CFG.game.getProvince(CFG.game.getActiveProvinceID()).getPopulationData().setPopulationOfCivID(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID(), tPopulation + CFG.game.getProvince(CFG.game.getActiveProvinceID()).getPopulationData().getPopulationOfCivID(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()));
                            Commands.addMessage(Commands.cheatMess() + CFG.langManager.get("Population") + ": +" + tPopulation);
                            Commands.addMessage("");
                            int tActiveProvince = CFG.game.getActiveProvinceID();
                            CFG.game.setActiveProvinceID(-1);
                            CFG.game.setActiveProvinceID(tActiveProvince);
                            CFG.toast.setInView(Commands.cheatMess() + CFG.langManager.get("Population"));
                            if (CFG.menuManager.getVisibleInGame_CensusOfProvince()) {
                                CFG.menuManager.rebuildInGame_CensusOfProvince(CFG.game.getActiveProvinceID());
                            }
                        } else {
                            Commands.IllegalCommand();
                            CFG.toast.setInView(CFG.langManager.get("ChooseAProvince"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
                            Commands.addMessage(CFG.langManager.get(CFG.langManager.get("ChooseAProvince")));
                            Commands.addMessage("");
                        }
                    } else {
                        Commands.IllegalCommand();
                        CFG.toast.setInView(CFG.langManager.get("Error"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
                        Commands.addMessage(CFG.langManager.get(CFG.langManager.get("Error")));
                        Commands.addMessage("");
                    }
                    return;
                }
                if (tempCommand[0].equals("sar") || tempCommand[0].equals("setarmy") || tempCommand[0].equals("armyset")) {
                    int tArmy = Integer.parseInt(tempCommand[1]);
                    if (tArmy >= 0 && CFG.game.getActiveProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getWasteland() < 0 && !CFG.game.getProvince(CFG.game.getActiveProvinceID()).getSeaProvince()) {
                        CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).setNumOfUnits(CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).getNumOfUnits() - CFG.game.getProvince(CFG.game.getActiveProvinceID()).getArmyCivID(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()));
                        CFG.game.getProvince(CFG.game.getActiveProvinceID()).updateArmy(tArmy);
                        CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).setNumOfUnits(CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).getNumOfUnits() + tArmy);
                        Commands.addMessage(Commands.cheatMess() + CFG.langManager.get("Army") + ": " + tArmy);
                        Commands.addMessage("");
                        int tActiveProvince = CFG.game.getActiveProvinceID();
                        CFG.game.setActiveProvinceID(-1);
                        CFG.game.setActiveProvinceID(tActiveProvince);
                        CFG.toast.setInView(Commands.cheatMess() + CFG.langManager.get("Army"));
                    } else {
                        Commands.IllegalCommand();
                        CFG.toast.setInView(CFG.langManager.get("ChooseAProvince"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
                        Commands.addMessage(CFG.langManager.get(CFG.langManager.get("ChooseAProvince")));
                        Commands.addMessage("");
                    }
                    return;
                }
                if (tempCommand[0].equals("warweariness") || tempCommand[0].equals("ww")) {
                    int tWW = Integer.parseInt(tempCommand[1]);
                    if (tWW <= 100) {
                        CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).setWarWeariness(Float.valueOf(tWW/100));
                        Commands.addMessage(Commands.cheatMess() + CFG.langManager.get("War Weariness") + ": " + tWW);
                        Commands.addMessage("");
                        CFG.toast.setInView(Commands.cheatMess() + CFG.langManager.get("War Weariness"));
                        CFG.menuManager.updateInGame_TOP_All(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
                    } else {
                        Commands.IllegalCommand();
                        CFG.toast.setInView(CFG.langManager.get("Error"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
                        Commands.addMessage(CFG.langManager.get(CFG.langManager.get("Error")));
                        Commands.addMessage("");
                    }
                    return;
                }
                if (tempCommand[0].equals("annex")) {
                    if (CFG.game.getActiveProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getWasteland() < 0 && !CFG.game.getProvince(CFG.game.getActiveProvinceID()).getSeaProvince()) {
                        if (CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() != CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()) {
                            int iFromCivID = CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID();
                            int iToCivID = CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID();
                            int j;
                            int i;

                            CFG.game.getCiv(iFromCivID).clearMoveUnits();
                            CFG.game.getCiv(iFromCivID).clearMoveUnits_Plunder();
                            CFG.game.getCiv(iFromCivID).clearRegroupArmy();
                            CFG.game.getCiv(iFromCivID).clearRecruitArmy();
                            ArrayList<Integer> tempProvinces = new ArrayList<Integer>();
                            for (i = 0; i < CFG.game.getCiv(iFromCivID).getNumOfProvinces(); ++i) {
                                tempProvinces.add(CFG.game.getCiv(iFromCivID).getProvinceID(i));
                            }
                            for (i = 0; i < tempProvinces.size(); ++i) {
                                if (CFG.game.getProvince((Integer)tempProvinces.get(i)).getCivID() != iFromCivID || CFG.game.getProvince((Integer)tempProvinces.get(i)).getTrueOwnerOfProvince() != iFromCivID) continue;
                                int nArmyNewOwnerArmy = CFG.game.getProvince((Integer)tempProvinces.get(i)).getArmyCivID(iToCivID);
                                CFG.game.getProvince((Integer)tempProvinces.get(i)).updateArmy(0);
                                CFG.game.getProvince((Integer)tempProvinces.get(i)).updateArmy(iToCivID, 0);
                                CFG.game.getProvince((Integer)tempProvinces.get(i)).setTrueOwnerOfProvince(iToCivID);
                                CFG.game.getProvince((Integer)tempProvinces.get(i)).setCivID(iToCivID, false);
                                CFG.game.getProvince((Integer)tempProvinces.get(i)).updateArmy(iToCivID, nArmyNewOwnerArmy);
                                for (j = CFG.game.getProvince((Integer)tempProvinces.get(i)).getCivsSize() - 1; j >= 0; --j) {
                                    if (CFG.game.getCiv(CFG.game.getProvince((Integer)tempProvinces.get(i)).getCivID(j)).getPuppetOfCivID() == iToCivID || CFG.game.getCiv(iToCivID).getPuppetOfCivID() == CFG.game.getProvince((Integer)tempProvinces.get(i)).getCivID(j) || CFG.game.getCiv(CFG.game.getProvince((Integer)tempProvinces.get(i)).getCivID(j)).getAllianceID() > 0 && CFG.game.getCiv(CFG.game.getProvince((Integer)tempProvinces.get(i)).getCivID(j)).getAllianceID() == CFG.game.getCiv(iToCivID).getAllianceID() || CFG.game.getMilitaryAccess(CFG.game.getProvince((Integer)tempProvinces.get(i)).getCivID(j), iToCivID) > 0) continue;
                                    CFG.gameAction.accessLost_MoveArmyToClosetsProvince(CFG.game.getProvince((Integer)tempProvinces.get(i)).getCivID(j), (Integer)tempProvinces.get(i));
                                }
                            }
                            if (CFG.game.getCiv(iFromCivID).getCapitalProvinceID() >= 0) {
                                CFG.game.getProvince(CFG.game.getCiv(iFromCivID).getCapitalProvinceID()).setIsCapital(false);
                                for (i = 0; i < CFG.game.getProvince(CFG.game.getCiv(iFromCivID).getCapitalProvinceID()).getCitiesSize(); ++i) {
                                    if (CFG.game.getProvince(CFG.game.getCiv(iFromCivID).getCapitalProvinceID()).getCity(i).getCityLevel() != CFG.getEditorCityLevel(0)) continue;
                                    CFG.game.getProvince(CFG.game.getCiv(iFromCivID).getCapitalProvinceID()).getCity(i).setCityLevel(CFG.getEditorCityLevel(1));
                                }
                            }
                            CFG.game.getCiv(iFromCivID).buildNumOfUnits();
                            tempProvinces.clear();
                            CFG.game.buildCivilizationsRegions_TextOver(iFromCivID);
                            CFG.game.buildCivilizationsRegions_TextOver(iToCivID);
                            CFG.game.getCiv(iFromCivID).setPuppetOfCivID(iFromCivID);
                            CFG.historyManager.addHistoryLog(new HistoryLog_Annexation(iFromCivID, iToCivID));

                            Commands.addMessage(Commands.cheatMess() + CFG.langManager.get("Annexed") + ": " + CFG.game.getCiv(iFromCivID).getCivName());
                            Commands.addMessage("");
                            CFG.toast.setInView(Commands.cheatMess() + CFG.langManager.get("Annexation"));
                        } else {
                            Commands.IllegalCommand();
                            CFG.toast.setInView(CFG.langManager.get("Error"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
                            Commands.addMessage(CFG.langManager.get(CFG.langManager.get("CannotAnnexSelf")));
                            Commands.addMessage("");
                        }
                    } else {
                        Commands.IllegalCommand();
                        CFG.toast.setInView(CFG.langManager.get("ChooseAProvince"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
                        Commands.addMessage(CFG.langManager.get(CFG.langManager.get("ChooseAProvince")));
                        Commands.addMessage("");
                    }
                    return;
                }
                if (tempCommand[0].equals("occupy")) {
                    if (CFG.game.getActiveProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getWasteland() < 0 && !CFG.game.getProvince(CFG.game.getActiveProvinceID()).getSeaProvince()) {
                        if (CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() != CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()) {
                            int iFromCivID = CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID();
                            int iToCivID = CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID();
                            int j;
                            int i;

                            CFG.game.getCiv(iFromCivID).clearMoveUnits();
                            CFG.game.getCiv(iFromCivID).clearMoveUnits_Plunder();
                            CFG.game.getCiv(iFromCivID).clearRegroupArmy();
                            CFG.game.getCiv(iFromCivID).clearRecruitArmy();
                            ArrayList<Integer> tempProvinces = new ArrayList<Integer>();
                            for (i = 0; i < CFG.game.getCiv(iFromCivID).getNumOfProvinces(); ++i) {
                                tempProvinces.add(CFG.game.getCiv(iFromCivID).getProvinceID(i));
                            }
                            for (i = 0; i < tempProvinces.size(); ++i) {
                                if (CFG.game.getProvince((Integer)tempProvinces.get(i)).getCivID() != iFromCivID || CFG.game.getProvince((Integer)tempProvinces.get(i)).getTrueOwnerOfProvince() != iFromCivID) continue;
                                int nArmyNewOwnerArmy = CFG.game.getProvince((Integer)tempProvinces.get(i)).getArmyCivID(iToCivID);
                                CFG.game.getProvince((Integer)tempProvinces.get(i)).updateArmy(0);
                                CFG.game.getProvince((Integer)tempProvinces.get(i)).updateArmy(iToCivID, 0);
                                CFG.game.getProvince((Integer)tempProvinces.get(i)).setCivID(iToCivID, false);
                                CFG.game.getProvince((Integer)tempProvinces.get(i)).updateArmy(iToCivID, nArmyNewOwnerArmy);
                                for (j = CFG.game.getProvince((Integer)tempProvinces.get(i)).getCivsSize() - 1; j >= 0; --j) {
                                    if (CFG.game.getCiv(CFG.game.getProvince((Integer)tempProvinces.get(i)).getCivID(j)).getPuppetOfCivID() == iToCivID || CFG.game.getCiv(iToCivID).getPuppetOfCivID() == CFG.game.getProvince((Integer)tempProvinces.get(i)).getCivID(j) || CFG.game.getCiv(CFG.game.getProvince((Integer)tempProvinces.get(i)).getCivID(j)).getAllianceID() > 0 && CFG.game.getCiv(CFG.game.getProvince((Integer)tempProvinces.get(i)).getCivID(j)).getAllianceID() == CFG.game.getCiv(iToCivID).getAllianceID() || CFG.game.getMilitaryAccess(CFG.game.getProvince((Integer)tempProvinces.get(i)).getCivID(j), iToCivID) > 0) continue;
                                    CFG.gameAction.accessLost_MoveArmyToClosetsProvince(CFG.game.getProvince((Integer)tempProvinces.get(i)).getCivID(j), (Integer)tempProvinces.get(i));
                                }
                            }
                            CFG.game.getCiv(iFromCivID).buildNumOfUnits();
                            tempProvinces.clear();
                            CFG.game.buildCivilizationsRegions_TextOver(iFromCivID);
                            CFG.game.buildCivilizationsRegions_TextOver(iToCivID);

                            Commands.addMessage(Commands.cheatMess() + CFG.langManager.get("Occupied") + ": " + CFG.game.getCiv(iFromCivID).getCivName());
                            Commands.addMessage("");
                            CFG.toast.setInView(Commands.cheatMess() + CFG.langManager.get("Occupation"));
                        }
                    } else {
                        Commands.IllegalCommand();
                        CFG.toast.setInView(CFG.langManager.get("ChooseAProvince"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
                        Commands.addMessage(CFG.langManager.get(CFG.langManager.get("ChooseAProvince")));
                        Commands.addMessage("");
                    }
                    return;
                }
                if (tempCommand[0].equals("assim") || tempCommand[0].equals("assimilate")) {
                    int tTurns = Integer.parseInt(tempCommand[1]);
                    if (tTurns > 0 && CFG.game.getCiv((int)CFG.game.getPlayer((int)CFG.PLAYER_TURNID).getCivID()).lProvincesWithLowStability.size() > 0) {
                        for (int i = 0; i < CFG.game.getCiv((int)CFG.game.getPlayer((int)CFG.PLAYER_TURNID).getCivID()).lProvincesWithLowStability.size(); i++) {
                            int nProvinceID = CFG.game.getCiv((int)CFG.game.getPlayer((int)CFG.PLAYER_TURNID).getCivID()).lProvincesWithLowStability.get(i);

                            if (CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() == CFG.game.getProvince(nProvinceID).getCivID() && !CFG.game.getProvince(nProvinceID).isOccupied()) {
                                CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).addAssimilate(new CivFestival(nProvinceID, tTurns));
                            }
                        }
                        Commands.addMessage(Commands.cheatMess() + CFG.langManager.get("Assimilated") + ": " + CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCivName());
                        Commands.addMessage("");
                        CFG.toast.setInView(Commands.cheatMess() + CFG.langManager.get("Assimilate"));
                    } else {
                        Commands.IllegalCommand();
                        CFG.toast.setInView(CFG.langManager.get("Error"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
                        Commands.addMessage(CFG.langManager.get(CFG.langManager.get("NoUnassimilatedProvinces")));
                        Commands.addMessage("");
                    }
                    return;
                }
                if (tempCommand[0].equals("eco") || tempCommand[0].equals("economy")) {
                    int tTurns = Integer.parseInt(tempCommand[1]);
                    int tEco = Integer.parseInt(tempCommand[2]);
                    if (tTurns > 0 && tEco > 0 && CFG.game.getCiv((int)CFG.game.getPlayer((int)CFG.PLAYER_TURNID).getCivID()).getNumOfProvinces() > 0) {
                        for(int i = 0; i < CFG.game.getCiv((int)CFG.game.getPlayer((int)CFG.PLAYER_TURNID).getCivID()).getNumOfProvinces(); ++i) {
                            int nProvinceID = CFG.game.getCiv((int)CFG.game.getPlayer((int)CFG.PLAYER_TURNID).getCivID()).getProvinceID(i);

                            if (CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() == CFG.game.getProvince(nProvinceID).getCivID() && !CFG.game.getProvince(nProvinceID).isOccupied()) {
                                CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).addInvest(new CivInvest(nProvinceID, tTurns, tEco, (int) Math.ceil(tEco/tTurns)));
                            }
                        }

                        Commands.addMessage(Commands.cheatMess() + CFG.langManager.get("Economy") + ": " + CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCivName());
                        Commands.addMessage("");
                        CFG.toast.setInView(Commands.cheatMess() + CFG.langManager.get("Economy"));
                    } else {
                        Commands.IllegalCommand();
                        CFG.toast.setInView(CFG.langManager.get("Error"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
                        Commands.addMessage(CFG.langManager.get(CFG.langManager.get("Error")));
                        Commands.addMessage("");
                    }
                    return;
                }
                if (tempCommand[0].equals("dev") || tempCommand[0].equals("develop")) {
                    int tTurns = Integer.parseInt(tempCommand[1]);
                    int tEco = Integer.parseInt(tempCommand[2]);
                    if (tTurns > 0 && tEco > 0 && CFG.game.getCiv((int)CFG.game.getPlayer((int)CFG.PLAYER_TURNID).getCivID()).getNumOfProvinces() > 0) {
                        for(int i = 0; i < CFG.game.getCiv((int)CFG.game.getPlayer((int)CFG.PLAYER_TURNID).getCivID()).getNumOfProvinces(); ++i) {
                            int nProvinceID = CFG.game.getCiv((int)CFG.game.getPlayer((int)CFG.PLAYER_TURNID).getCivID()).getProvinceID(i);

                            if (CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() == CFG.game.getProvince(nProvinceID).getCivID() && !CFG.game.getProvince(nProvinceID).isOccupied()) {
                                CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).addInvest_Development(new CivInvest_Development(nProvinceID, tTurns, tEco, (int) Math.ceil(tEco/tTurns)));
                            }
                        }

                        Commands.addMessage(Commands.cheatMess() + CFG.langManager.get("Development") + ": " + CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCivName());
                        Commands.addMessage("");
                        CFG.toast.setInView(Commands.cheatMess() + CFG.langManager.get("Development"));
                    } else {
                        Commands.IllegalCommand();
                        CFG.toast.setInView(CFG.langManager.get("Error"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
                        Commands.addMessage(CFG.langManager.get(CFG.langManager.get("Error")));
                        Commands.addMessage("");
                    }
                    return;
                }
                if (tempCommand[0].equals("bld") || tempCommand[0].equals("build")) {
                    int tAll = Integer.parseInt(tempCommand[1]);

                    if (CFG.game.getCiv((int)CFG.game.getPlayer((int)CFG.PLAYER_TURNID).getCivID()).getNumOfProvinces() > 0) {
                        if (tAll == 0) {
                            int nProvinceID = CFG.game.getActiveProvinceID();

                            CFG.game.getProvince(nProvinceID).setLevelOfFort(BuildingsManager.getFort_MaxLevel());
                            CFG.game.getProvince(nProvinceID).setLevelOfWatchTower(BuildingsManager.getTower_MaxLevel());

                            CFG.game.getProvince(nProvinceID).setLevelOfFarm(BuildingsManager.getFarm_MaxLevel());
                            CFG.game.getProvince(nProvinceID).setLevelOfWorkshop(BuildingsManager.getWorkshop_MaxLevel());
                            CFG.game.getProvince(nProvinceID).setLevelOfLibrary(BuildingsManager.getLibrary_MaxLevel());
                            CFG.game.getProvince(nProvinceID).setLevelOfArmoury(BuildingsManager.getArmoury_MaxLevel());
                            CFG.game.getProvince(nProvinceID).setLevelOfSupply(BuildingsManager.getSupply_MaxLevel());

                            if (CFG.game.getProvince(nProvinceID).getLevelOfPort() >= 0) {
                                CFG.game.getProvince(nProvinceID).setLevelOfPort(BuildingsManager.getPort_MaxLevel());
                            }
                            return;
                        }
                        for(int i = 0; i < CFG.game.getCiv((int)CFG.game.getPlayer((int)CFG.PLAYER_TURNID).getCivID()).getNumOfProvinces(); ++i) {
                            int nProvinceID = CFG.game.getCiv((int)CFG.game.getPlayer((int)CFG.PLAYER_TURNID).getCivID()).getProvinceID(i);

                            if (CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID() == CFG.game.getProvince(nProvinceID).getCivID() && !CFG.game.getProvince(nProvinceID).isOccupied()) {
                                CFG.game.getProvince(nProvinceID).setLevelOfFort(BuildingsManager.getFort_MaxLevel());
                                CFG.game.getProvince(nProvinceID).setLevelOfWatchTower(BuildingsManager.getTower_MaxLevel());

                                CFG.game.getProvince(nProvinceID).setLevelOfFarm(BuildingsManager.getFarm_MaxLevel());
                                CFG.game.getProvince(nProvinceID).setLevelOfWorkshop(BuildingsManager.getWorkshop_MaxLevel());
                                CFG.game.getProvince(nProvinceID).setLevelOfLibrary(BuildingsManager.getLibrary_MaxLevel());
                                CFG.game.getProvince(nProvinceID).setLevelOfArmoury(BuildingsManager.getArmoury_MaxLevel());
                                CFG.game.getProvince(nProvinceID).setLevelOfSupply(BuildingsManager.getSupply_MaxLevel());

                                if (CFG.game.getProvince(nProvinceID).getLevelOfPort() >= 0) {
                                    CFG.game.getProvince(nProvinceID).setLevelOfPort(BuildingsManager.getPort_MaxLevel());
                                }
                            }
                        }
                        Commands.addMessage(Commands.cheatMess() + CFG.langManager.get("Built All") + ": " + CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getCivName());
                        Commands.addMessage("");
                        CFG.toast.setInView(Commands.cheatMess() + CFG.langManager.get("Build"));
                    } else {
                        Commands.IllegalCommand();
                        CFG.toast.setInView(CFG.langManager.get("Error"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
                        Commands.addMessage(CFG.langManager.get(CFG.langManager.get("Error")));
                        Commands.addMessage("");
                    }
                    return;
                }
                if (tempCommand[0].equals("tp") || tempCommand[0].equals("techpoints")) {
                    int tPoints = Integer.parseInt(tempCommand[1]);
                    if (CFG.game.getCiv((int)CFG.game.getPlayer((int)CFG.PLAYER_TURNID).getCivID()).getNumOfProvinces() > 0) {
                        CFG.game.getCiv((int)CFG.game.getPlayer((int)CFG.PLAYER_TURNID).getCivID()).civGameData.skills.POINTS_RESEARCH = (0 - tPoints);

                        Commands.addMessage(Commands.cheatMess() + CFG.langManager.get("Tech Points") + ": " + tPoints);
                        Commands.addMessage("");
                        CFG.toast.setInView(Commands.cheatMess() + CFG.langManager.get("TechPoints"));
                    } else {
                        Commands.IllegalCommand();
                        CFG.toast.setInView(CFG.langManager.get("Error"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
                        Commands.addMessage(CFG.langManager.get(CFG.langManager.get("Error")));
                        Commands.addMessage("");
                    }
                    return;
                }
                if (tempCommand[0].equals("noliberty")) {
                    CFG.NO_LIBERITY = !CFG.NO_LIBERITY;
                    Commands.addMessage(Commands.cheatMess() + CFG.langManager.get("Liberation") + ": " + (CFG.NO_LIBERITY ? CFG.langManager.get("Disabled") : CFG.langManager.get("Enabled")));
                    Commands.addMessage("");
                    CFG.toast.setInView(Commands.cheatMess() + CFG.langManager.get("Liberation") + ": " + (CFG.NO_LIBERITY ? CFG.langManager.get("Disabled") : CFG.langManager.get("Enabled")));
                    return;
                }
                if (tempCommand[0].equals("getgovernment") || tempCommand[0].equals("getgov")) {
                        if (CFG.game.getActiveProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getWasteland() < 0 && !CFG.game.getProvince(CFG.game.getActiveProvinceID()).getSeaProvince()) {
                            int tIdeologyID = CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).getIdeologyID();

                            Commands.addMessage(Commands.cheatMess() + CFG.langManager.get(CFG.ideologiesManager.getIdeology(CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).getIdeologyID()).getName()) + ": " + tIdeologyID);
                            Commands.addMessage("");
                            CFG.toast.setInView(Commands.cheatMess() + CFG.langManager.get("Government"));
                        } else {
                            Commands.IllegalCommand();
                            CFG.toast.setInView(CFG.langManager.get("ChooseAProvince"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
                            Commands.addMessage(CFG.langManager.get(CFG.langManager.get("ChooseAProvince")));
                            Commands.addMessage("");
                        }
                        return;
                }
                if (tempCommand[0].equals("setgovernment") || tempCommand[0].equals("setgov") || tempCommand[0].equals("gov")  || tempCommand[0].equals("ideology")) {
                    int tIdeologyID = Integer.parseInt(tempCommand[1]);
                    if (tIdeologyID >= 0) {
                        if (CFG.game.getActiveProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getWasteland() < 0 && !CFG.game.getProvince(CFG.game.getActiveProvinceID()).getSeaProvince() && CFG.ideologiesManager.getIdeology(tIdeologyID) != null) {
                            int tCivID = CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID();

                            CFG.game.updateCivilizationIdeology(tCivID, CFG.ideologiesManager.getRealTag(CFG.game.getCiv(tCivID).getCivTag()) + CFG.ideologiesManager.getIdeology(tIdeologyID).getExtraTag());
                            for (int i = 0; i < CFG.game.getCiv(tCivID).getCivRegionsSize(); ++i) {
                                CFG.game.getCiv(tCivID).getCivRegion(i).buildScaleOfText();
                            }
                            CFG.game.getCiv(tCivID).loadFlag();
                            CFG.setActiveCivInfo(CFG.getActiveCivInfo());
                            CFG.updateActiveCivInfo_InGame();

                            Commands.addMessage(Commands.cheatMess() + CFG.langManager.get("Government") + ": " + CFG.ideologiesManager.getIdeology(tIdeologyID).getName());
                            Commands.addMessage("");
                            CFG.toast.setInView(Commands.cheatMess() + CFG.langManager.get("Government"));
                        } else {
                            Commands.IllegalCommand();
                            CFG.toast.setInView(CFG.langManager.get("ChooseAProvince"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
                            Commands.addMessage(CFG.langManager.get(CFG.langManager.get("ChooseAProvince")));
                            Commands.addMessage("");
                        }
                    } else {
                        Commands.IllegalCommand();
                        CFG.toast.setInView(CFG.langManager.get("Error"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
                        Commands.addMessage(CFG.langManager.get(CFG.langManager.get("Error")));
                        Commands.addMessage("");
                    }
                    return;
                }
                if (tempCommand[0].equals("vassal") || tempCommand[0].equals("vassalize") || tempCommand[0].equals("puppet")) {
                    int tToCivID = Integer.parseInt(tempCommand[1]);
                    if (tToCivID > 0) {
                        if (CFG.game.getActiveProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getWasteland() < 0 && !CFG.game.getProvince(CFG.game.getActiveProvinceID()).getSeaProvince() && CFG.game.getCiv(CFG.game.getActiveProvinceID()).getCivID() != tToCivID) {
                            int tPlayerID;
                            int tFromCivID = CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID();
                            CFG.game.getCiv(tFromCivID).setPuppetOfCivID(tToCivID);
                            if (CFG.game.getCiv(tToCivID).getControlledByPlayer() && CFG.FOG_OF_WAR > 0 && (tPlayerID = CFG.game.getPlayerID_ByCivID(tToCivID)) >= 0) {
                                for (int i = 0; i < CFG.game.getCiv(tFromCivID).getNumOfProvinces(); ++i) {
                                    CFG.game.getProvince(CFG.game.getCiv(tFromCivID).getProvinceID(i)).updateFogOfWar(tPlayerID);
                                }
                            }
                            CFG.historyManager.addHistoryLog(new HistoryLog_IsVassal(tToCivID, tFromCivID));

                            Commands.addMessage(Commands.cheatMess() + CFG.langManager.get("Vassalized") + ": " + tFromCivID);
                            Commands.addMessage("");
                            CFG.toast.setInView(Commands.cheatMess() + CFG.langManager.get("Vassalization"));
                        } else {
                            Commands.IllegalCommand();
                            CFG.toast.setInView(CFG.langManager.get("ChooseAProvince"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
                            Commands.addMessage(CFG.langManager.get(CFG.langManager.get("ChooseAProvince")));
                            Commands.addMessage("");
                        }
                    } else {
                        Commands.IllegalCommand();
                        CFG.toast.setInView(CFG.langManager.get("Error"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
                        Commands.addMessage(CFG.langManager.get(CFG.langManager.get("Error")));
                        Commands.addMessage("");
                    }
                    return;
                }
                if (tempCommand[0].equals("ally") || tempCommand[0].equals("alliance")) {
                    int tToCivID = Integer.parseInt(tempCommand[1]);
                    if (tToCivID > 0) {
                        if (CFG.game.getActiveProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getWasteland() < 0 && !CFG.game.getProvince(CFG.game.getActiveProvinceID()).getSeaProvince() && CFG.game.getCiv(CFG.game.getActiveProvinceID()).getCivID() != tToCivID) {
                            int tFromCivID = CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID();
                            if (CFG.game.getCiv(tToCivID).getAllianceID() == 0) { // && CFG.game.getCiv(tFromCivID).getAllianceID() == 0
                                if (CFG.game.getCiv(tFromCivID).getAllianceID() > 0) {
                                    CFG.game.getAlliance(CFG.game.getCiv(tFromCivID).getAllianceID()).removeCivilization(tFromCivID);
                                }

                                CFG.game.addAlliance(CFG.getRandomAllianceName(0));
                                int tempAllianceID = CFG.game.getAlliancesSize() - 1;
                                if (CFG.game.getCiv(tToCivID).getControlledByPlayer()) {
                                    CFG.game.getAlliance(tempAllianceID).addCivilization(tToCivID);
                                    CFG.game.getAlliance(tempAllianceID).addCivilization(tFromCivID);
                                } else if (CFG.game.getCiv(tFromCivID).getControlledByPlayer()) {
                                    CFG.game.getAlliance(tempAllianceID).addCivilization(tFromCivID);
                                    CFG.game.getAlliance(tempAllianceID).addCivilization(tToCivID);
                                } else {
                                    CFG.game.getAlliance(tempAllianceID).addCivilization(tToCivID);
                                    CFG.game.getAlliance(tempAllianceID).addCivilization(tFromCivID);
                                }
                                CFG.game.getCiv(tToCivID).setAllianceID(tempAllianceID);
                                CFG.game.getCiv(tFromCivID).setAllianceID(tempAllianceID);
                                CFG.historyManager.addHistoryLog(new HistoryLog_JoinAlliance(tToCivID, tempAllianceID));
                                CFG.historyManager.addHistoryLog(new HistoryLog_JoinAlliance(tFromCivID, tempAllianceID));
                            } else if (CFG.game.getCiv(tFromCivID).getAllianceID() > 0 && CFG.game.getCiv(tToCivID).getAllianceID() == 0) {
                            //    CFG.game.getAlliance(CFG.game.getCiv(tFromCivID).getAllianceID()).addCivilization(tToCivID);
                            //    CFG.game.getCiv(tToCivID).setAllianceID(CFG.game.getCiv(tFromCivID).getAllianceID());
                            //    CFG.historyManager.addHistoryLog(new HistoryLog_JoinAlliance(tToCivID, CFG.game.getCiv(tFromCivID).getAllianceID()));
                            } else if (CFG.game.getCiv(tToCivID).getAllianceID() > 0 && CFG.game.getCiv(tFromCivID).getAllianceID() == 0) {
                                CFG.game.getAlliance(CFG.game.getCiv(tToCivID).getAllianceID()).addCivilization(tFromCivID);
                                CFG.game.getCiv(tFromCivID).setAllianceID(CFG.game.getCiv(tToCivID).getAllianceID());
                                CFG.historyManager.addHistoryLog(new HistoryLog_JoinAlliance(tFromCivID, CFG.game.getCiv(tToCivID).getAllianceID()));
                            } else {
                                CFG.game.getAlliance(CFG.game.getCiv(tFromCivID).getAllianceID()).removeCivilization(tFromCivID);
                                CFG.game.getAlliance(CFG.game.getCiv(tToCivID).getAllianceID()).addCivilization(tFromCivID);
                                CFG.game.getCiv(tFromCivID).setAllianceID(CFG.game.getCiv(tToCivID).getAllianceID());
                                CFG.game.getCiv(tFromCivID).setAllianceID(CFG.game.getCiv(tToCivID).getAllianceID());
                            }
                            if (CFG.game.getCiv(tToCivID).getControlledByPlayer()) {
                                CFG.gameAction.buildFogOfWar(CFG.game.getPlayerID_ByCivID(tToCivID));
                                CFG.game.getPlayer(CFG.game.getPlayerID_ByCivID(tToCivID)).buildMetProvincesAndCivs();
                            }
                            if (CFG.game.getCiv(tFromCivID).getControlledByPlayer()) {
                                CFG.gameAction.buildFogOfWar(CFG.game.getPlayerID_ByCivID(tFromCivID));
                                CFG.game.getPlayer(CFG.game.getPlayerID_ByCivID(tFromCivID)).buildMetProvincesAndCivs();
                            }

                            Commands.addMessage(Commands.cheatMess() + CFG.langManager.get("Allied") + ": " + tFromCivID);
                            Commands.addMessage("");
                            CFG.toast.setInView(Commands.cheatMess() + CFG.langManager.get("Ally"));
                        } else {
                            Commands.IllegalCommand();
                            CFG.toast.setInView(CFG.langManager.get("ChooseAProvince"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
                            Commands.addMessage(CFG.langManager.get(CFG.langManager.get("ChooseAProvince")));
                            Commands.addMessage("");
                        }
                    } else {
                        Commands.IllegalCommand();
                        CFG.toast.setInView(CFG.langManager.get("Error"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
                        Commands.addMessage(CFG.langManager.get(CFG.langManager.get("Error")));
                        Commands.addMessage("");
                    }
                    return;
                }
                if (tempCommand[0].equals("identification") || tempCommand[0].equals("id")) {
                    if (CFG.game.getActiveProvinceID() >= 0) {
                        Commands.addMessage(Commands.cheatMess() + CFG.langManager.get("Province") + ": " + CFG.game.getActiveProvinceID());
                        Commands.addMessage(Commands.cheatMess() + CFG.langManager.get("Civilization") + ": " + CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).getCivName() + ": " + CFG.game.getCiv(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID()).getCivID());
                        Commands.addMessage("");
                        int tActiveProvince = CFG.game.getActiveProvinceID();
                        CFG.game.setActiveProvinceID(-1);
                        CFG.game.setActiveProvinceID(tActiveProvince);
                        CFG.toast.setInView(Commands.cheatMess() + CFG.langManager.get("War"));
                    } else {
                        Commands.IllegalCommand();
                        CFG.toast.setInView(CFG.langManager.get("ChooseAProvince"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
                        Commands.addMessage(CFG.langManager.get(CFG.langManager.get("ChooseAProvince")));
                        Commands.addMessage("");
                    }
                    return;
                }
                if (tempCommand[0].equals("war")) {
                    int civA = Integer.parseInt(tempCommand[1]);
                    int civB = Integer.parseInt(tempCommand[2]);
                    if (civA >= 0 && civB >= 0 && CFG.game.getCiv(civA).getNumOfProvinces() > 0 && CFG.game.getCiv(civB).getNumOfProvinces() > 0) {
                        CFG.game.declareWar(civA, civB, true);
                        Commands.addMessage(Commands.cheatMess() + CFG.langManager.get("War") + ": " + CFG.game.getCiv(civA).getCivName() + " -> " + CFG.game.getCiv(civB).getCivName());
                        Commands.addMessage("");
                        int tActiveProvince = CFG.game.getActiveProvinceID();
                        CFG.game.setActiveProvinceID(-1);
                        CFG.game.setActiveProvinceID(tActiveProvince);
                        CFG.toast.setInView(Commands.cheatMess() + CFG.langManager.get("War"));
                    } else {
                        Commands.IllegalCommand();
                        CFG.toast.setInView(CFG.langManager.get("Error"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
                        Commands.addMessage(CFG.langManager.get(CFG.langManager.get("Error")));
                        Commands.addMessage("");
                    }
                    return;
                }
                if (tempCommand[0].equals("peace")) {
                    int civA = Integer.parseInt(tempCommand[1]);
                    int civB = Integer.parseInt(tempCommand[2]);
                    if (civA >= 0 && civB >= 0 && CFG.game.getCivsAtWar(civA, civB)) {
                        CFG.game.getCiv((int)civB).civGameData.civilization_Diplomacy_GameData.messageBox.addMessage(new Message_WeCanSignPeace(civA));
                        Commands.addMessage(Commands.cheatMess() + CFG.langManager.get("Added") + ": " + CFG.game.getCiv(civA).getCivName() + " -> " + CFG.game.getCiv(civB).getCivName());
                        Commands.addMessage("");
                        int tActiveProvince = CFG.game.getActiveProvinceID();
                        CFG.game.setActiveProvinceID(-1);
                        CFG.game.setActiveProvinceID(tActiveProvince);
                        CFG.toast.setInView(Commands.cheatMess() + CFG.langManager.get("Added"));
                    } else {
                        Commands.IllegalCommand();
                        CFG.toast.setInView(CFG.langManager.get("Error"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
                        Commands.addMessage(CFG.langManager.get(CFG.langManager.get("Error")));
                        Commands.addMessage("");
                    }
                    return;
                }
                if (tempCommand[0].equals("union") || tempCommand[0].equals("unite")) {
                    int civA = Integer.parseInt(tempCommand[1]);
                    int civB = Integer.parseInt(tempCommand[2]);
                    if (civA >= 0 && civB >= 0) {
                        //reset rel
                        if (CFG.game.getCivsAtWar(civA, civB)) {
                            CFG.game.setCivRelation_OfCivB(civB, civA, Math.max(CFG.game.getCivRelation_OfCivB(civB, civA), 0.0F));
                            CFG.game.setCivRelation_OfCivB(civA, civB, Math.max(CFG.game.getCivRelation_OfCivB(civA, civB), 0.0F));
                            CFG.historyManager.addHistoryLog(new HistoryLog_Peace(civB, civA));
                        }

                        ++CFG.game.getCiv(civB).civGameData.numOfUnions;
                        ++CFG.game.getCiv(civA).civGameData.numOfUnions;
                        CFG.createUnion(civB, civA);

                        Commands.addMessage(Commands.cheatMess() + CFG.langManager.get("Union") + ": " + CFG.game.getCiv(civA).getCivName() + " -> " + CFG.game.getCiv(civB).getCivName());
                        Commands.addMessage("");
                        CFG.toast.setInView(Commands.cheatMess() + CFG.langManager.get("Union"));
                    } else {
                        Commands.IllegalCommand();
                        CFG.toast.setInView(CFG.langManager.get("Error"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
                        Commands.addMessage(CFG.langManager.get(CFG.langManager.get("Error")));
                        Commands.addMessage("");
                    }
                    return;
                }
                if (tempCommand[0].equals("rel") || tempCommand[0].equals("relation")) {
                    int civA = Integer.parseInt(tempCommand[1]);
                    int civB = Integer.parseInt(tempCommand[2]);
                    int tRelation = Integer.parseInt(tempCommand[3]);
                    if (civA >= 0 && civB >= 0) {
                        CFG.game.getCiv((int)civA).setRelation((int)civB - 1, (float)tRelation);

                        Commands.addMessage(Commands.cheatMess() + CFG.langManager.get("Relation") + ": " + CFG.game.getCiv(civA).getCivName() + " -> " + CFG.game.getCiv(civB).getCivName());
                        Commands.addMessage("");
                        CFG.toast.setInView(Commands.cheatMess() + CFG.langManager.get("Relation"));
                    } else {
                        Commands.IllegalCommand();
                        CFG.toast.setInView(CFG.langManager.get("Error"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
                        Commands.addMessage(CFG.langManager.get(CFG.langManager.get("Error")));
                        Commands.addMessage("");
                    }
                    return;
                }
                if (tempCommand[0].equals("buildport")) {
                    if (CFG.game.getActiveProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getWasteland() < 0 && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getLevelOfPort() >= 0) {
                        CFG.game.getProvince(CFG.game.getActiveProvinceID()).setLevelOfPort(1);
                        Commands.addMessage(Commands.cheatMess() + "Port built");
                        Commands.addMessage("");
                        int tActiveProvince = CFG.game.getActiveProvinceID();
                        CFG.game.setActiveProvinceID(-1);
                        CFG.game.setActiveProvinceID(tActiveProvince);
                        CFG.toast.setInView(Commands.cheatMess() + CFG.langManager.get("Port built"));
                    } else {
                        Commands.IllegalCommand();
                        CFG.toast.setInView(CFG.langManager.get("ChooseAProvince"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
                        Commands.addMessage(CFG.langManager.get(CFG.langManager.get("ChooseAProvince")));
                        Commands.addMessage("");
                    }
                    return;
                }
                if (tempCommand[0].equals("buildfort")) {
                    if (CFG.game.getActiveProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getWasteland() < 0) {
                        CFG.game.getProvince(CFG.game.getActiveProvinceID()).setLevelOfFort(1);
                        CFG.game.getProvince(CFG.game.getActiveProvinceID()).updateDrawArmy();
                        Commands.addMessage(Commands.cheatMess() + "Fort built");
                        Commands.addMessage("");
                        int tActiveProvince = CFG.game.getActiveProvinceID();
                        CFG.game.setActiveProvinceID(-1);
                        CFG.game.setActiveProvinceID(tActiveProvince);
                        CFG.toast.setInView(Commands.cheatMess() + CFG.langManager.get("Fort built"));
                    } else {
                        Commands.IllegalCommand();
                        CFG.toast.setInView(CFG.langManager.get("ChooseAProvince"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
                        Commands.addMessage(CFG.langManager.get(CFG.langManager.get("ChooseAProvince")));
                        Commands.addMessage("");
                    }
                    return;
                }
                if (tempCommand[0].equals("buildtower")) {
                    if (CFG.game.getActiveProvinceID() >= 0 && CFG.game.getProvince(CFG.game.getActiveProvinceID()).getWasteland() < 0) {
                        CFG.game.getProvince(CFG.game.getActiveProvinceID()).setLevelOfWatchTower(1);
                        CFG.game.getProvince(CFG.game.getActiveProvinceID()).updateDrawArmy();
                        Commands.addMessage(Commands.cheatMess() + "Tower built");
                        Commands.addMessage("");
                        int tActiveProvince = CFG.game.getActiveProvinceID();
                        CFG.game.setActiveProvinceID(-1);
                        CFG.game.setActiveProvinceID(tActiveProvince);
                        CFG.toast.setInView(Commands.cheatMess() + CFG.langManager.get("Tower built"));
                    } else {
                        Commands.IllegalCommand();
                        CFG.toast.setInView(CFG.langManager.get("ChooseAProvince"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
                        Commands.addMessage(CFG.langManager.get(CFG.langManager.get("ChooseAProvince")));
                        Commands.addMessage("");
                    }
                    return;
                }
                if (tempCommand[0].equals("ar") || tempCommand[0].equals("army")) {
                    if (CFG.game.getActiveProvinceID() >= 0) {
                        CFG.game.getProvince(CFG.game.getActiveProvinceID()).updateArmy(CFG.game.getProvince(CFG.game.getActiveProvinceID()).getCivID(CFG.activeCivilizationArmyID), CFG.game.getProvince(CFG.game.getActiveProvinceID()).getArmy(CFG.activeCivilizationArmyID) + 12000);
                        Commands.addMessage(Commands.cheatMess() + CFG.langManager.get("Army") + ": +" + 12000);
                        Commands.addMessage("");
                        int tActiveProvince = CFG.game.getActiveProvinceID();
                        CFG.game.setActiveProvinceID(-1);
                        CFG.game.setActiveProvinceID(tActiveProvince);
                        CFG.toast.setInView(Commands.cheatMess() + CFG.langManager.get("Army"));
                        if (CFG.menuManager.getVisibleInGame_CensusOfProvince()) {
                            CFG.menuManager.rebuildInGame_CensusOfProvince(CFG.game.getActiveProvinceID());
                        }
                    } else {
                        Commands.IllegalCommand();
                        CFG.toast.setInView(CFG.langManager.get("ChooseAProvince"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
                        Commands.addMessage(CFG.langManager.get(CFG.langManager.get("ChooseAProvince")));
                        Commands.addMessage("");
                    }
                    return;
                }
                if (tempCommand[0].equals("money") || tempCommand[0].equals("cash")) {
                    int tMoney = Integer.parseInt(tempCommand[1]);
                    if (tMoney >= 0) {
                        CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).setMoney(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMoney() + Long.valueOf(tMoney));
                        Commands.addMessage(Commands.cheatMess() + CFG.langManager.get("Money") + ": +" + tMoney);
                        Commands.addMessage("");
                        CFG.toast.setInView(Commands.cheatMess() + CFG.langManager.get("Money"));
                        CFG.menuManager.updateInGame_TOP_All(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
                    } else {
                        Commands.IllegalCommand();
                        CFG.toast.setInView(CFG.langManager.get("Error"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
                        Commands.addMessage(CFG.langManager.get(CFG.langManager.get("Error")));
                        Commands.addMessage("");
                    }
                    return;
                }
                if (tempCommand[0].equals("bankrupt")) {
                    CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).setMoney(0L);
                    Commands.addMessage(Commands.cheatMess() + CFG.langManager.get("Money") + " reset");
                    Commands.addMessage("");
                    CFG.toast.setInView(Commands.cheatMess() + CFG.langManager.get("Money"));
                    CFG.menuManager.updateInGame_TOP_All(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
                    return;
                }
                if (tempCommand[0].equals("movement")) {
                    CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).setMovePoints(CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getMovePoints() + CFG.ideologiesManager.getIdeology((int)CFG.game.getCiv((int)CFG.game.getPlayer((int)CFG.PLAYER_TURNID).getCivID()).getIdeologyID()).COST_OF_MOVE / 2);
                    Commands.addMessage(Commands.cheatMess() + CFG.langManager.get("MovementPoints") + ": +" + CFG.ideologiesManager.getIdeology((int)CFG.game.getCiv((int)CFG.game.getPlayer((int)CFG.PLAYER_TURNID).getCivID()).getIdeologyID()).COST_OF_MOVE / 2);
                    Commands.addMessage("");
                    CFG.toast.setInView(Commands.cheatMess() + CFG.langManager.get("movement"));
                    CFG.menuManager.updateInGame_TOP_All(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
                    return;
                }
                if (tempCommand[0].equals("diplomacy") || tempCommand[0].equals("diplo")) {
                    CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).civGameData.iDiplomacyPoints = 999;
                    Commands.addMessage(Commands.cheatMess() + CFG.langManager.get("DiplomacyPoints"));
                    Commands.addMessage("");
                    CFG.toast.setInView(Commands.cheatMess() + CFG.langManager.get("Diplomacy"));
                    CFG.menuManager.updateInGame_TOP_All(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID());
                    return;
                }
                if (!tempCommand[0].equals("reloadprovince")) break block120;
                try {
                    int tempID = Integer.parseInt(tempCommand[1]);
                    if (tempID < CFG.game.getProvincesSize()) {
                        Editor_NeighboringProvinces.updateProvince(tempID);
                        CFG.game.setActiveProvinceID(tempID);
                        CFG.toast.setInView(CFG.game.getProvince(tempID).getName());
                    } else {
                        Commands.IllegalCommand();
                    }
                    return;
                }
                catch (IllegalArgumentException ex) {
                    Commands.IllegalCommand();
                }
                catch (IndexOutOfBoundsException ex) {
                    Commands.IllegalCommand();
                }
                return;
            }
            catch (IndexOutOfBoundsException ex) {
                CFG.exceptionStack(ex);
            }
            catch (NumberFormatException ex) {
                CFG.exceptionStack(ex);
            }
            catch (IllegalArgumentException ex) {
                CFG.exceptionStack(ex);
            }
        }
        Commands.IllegalCommand();
    }

    private static final String cheatMess() {
        return "[" + CFG.langManager.get("Cheat") + "] ";
    }

    private static final void IllegalCommand() {
        Commands.addMessage("# -- " + CFG.langManager.get("UnknownCommand"));
        CFG.toast.setInView("# -- " + CFG.langManager.get("UnknownCommand"), CFG.COLOR_TEXT_MODIFIER_NEGATIVE2);
        Commands.addMessage("");
    }
}

