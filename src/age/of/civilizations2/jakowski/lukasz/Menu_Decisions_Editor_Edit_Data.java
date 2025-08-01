package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

class Menu_Decisions_Editor_Edit_Data extends SliderMenu {
    private String sName;
    private String sImage;
    private String sBorn;

    protected Menu_Decisions_Editor_Edit_Data() {
        super();
        final int tempW = (int) (CFG.CIV_INFO_MENU_WIDTH * 1.5f);
        final int tempElemH = CFG.BUTTON_HEIGHT;
        final List<MenuElement> menuElements = new ArrayList<MenuElement>();
        int tY = CFG.PADDING;

        menuElements.add(new Button_New_Game_Players_Special(CFG.civDecision_GameData.getName(), CFG.PADDING * 2, CFG.PADDING + 2, tY, tempW - CFG.PADDING * 2 - 2, true) {
            protected String getTextToDraw() {
                return Menu_Decisions_Editor_Edit_Data.this.sName + ": " + super.getText();
            }
        });
        tY += ((MenuElement) menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
        menuElements.add(new Button_New_Game_Players_Special(CFG.civDecision_GameData.getDesc(), CFG.PADDING * 2, CFG.PADDING + 2, tY, tempW - CFG.PADDING * 2 - 2, true) {
            protected String getTextToDraw() {
                return Menu_Decisions_Editor_Edit_Data.this.sImage + ": " + super.getText();
            }
        });
        tY += ((MenuElement) menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
        menuElements.add(new Button_New_Game_Players_Special(CFG.civDecision_GameData.getDay() + " " + Game_Calendar.getMonthName(CFG.civDecision_GameData.getMonth()) + " " + CFG.gameAges.getYear(CFG.civDecision_GameData.getYear()), CFG.PADDING * 2, CFG.PADDING + 2, tY, tempW - CFG.PADDING * 2 - 2, true) {
            protected String getTextToDraw() {
                return Menu_Decisions_Editor_Edit_Data.this.sBorn + ": " + super.getText();
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
        tY += ((MenuElement) menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;

        menuElements.add(new Button_NewGameStyle((String) null, -1, CFG.PADDING, tY, tempW - CFG.PADDING * 2 - 2, (int) ((float) CFG.BUTTON_HEIGHT * 0.6F), true));
        tY += ((MenuElement) menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
        menuElements.add(new Button_NewGameStyle((String) null, -1, CFG.PADDING, tY, tempW - CFG.PADDING * 2 - 2, (int) ((float) CFG.BUTTON_HEIGHT * 0.6F), true));
        tY += ((MenuElement) menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;

        menuElements.add(new Button_NewGameStyle_Left("-", -1, CFG.PADDING, tY, (int) ((float) CFG.BUTTON_HEIGHT * 0.8F), (int) ((float) CFG.BUTTON_HEIGHT * 0.6F), true));
        menuElements.add(new Button_NewGameStyle_Middle((String) null, -1, CFG.PADDING + (int) ((float) CFG.BUTTON_HEIGHT * 0.8F), tY, tempW - CFG.PADDING * 2 - (int) ((float) CFG.BUTTON_HEIGHT * 0.8F) * 2, (int) ((float) CFG.BUTTON_HEIGHT * 0.6F), true));
        menuElements.add(new Button_NewGameStyle_Right("+", -1, CFG.PADDING + (int) ((float) CFG.BUTTON_HEIGHT * 0.8F) + (tempW - CFG.PADDING * 2 - (int) ((float) CFG.BUTTON_HEIGHT * 0.8F) * 2), tY, (int) ((float) CFG.BUTTON_HEIGHT * 0.8F), (int) ((float) CFG.BUTTON_HEIGHT * 0.6F), true));
        tY += ((MenuElement) menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
        menuElements.add(new Button_NewGameStyle_Left("-", -1, CFG.PADDING, tY, (int) ((float) CFG.BUTTON_HEIGHT * 0.8F), (int) ((float) CFG.BUTTON_HEIGHT * 0.6F), true));
        menuElements.add(new Button_NewGameStyle_Middle((String) null, -1, CFG.PADDING + (int) ((float) CFG.BUTTON_HEIGHT * 0.8F), tY, tempW - CFG.PADDING * 2 - (int) ((float) CFG.BUTTON_HEIGHT * 0.8F) * 2, (int) ((float) CFG.BUTTON_HEIGHT * 0.6F), true));
        menuElements.add(new Button_NewGameStyle_Right("+", -1, CFG.PADDING + (int) ((float) CFG.BUTTON_HEIGHT * 0.8F) + (tempW - CFG.PADDING * 2 - (int) ((float) CFG.BUTTON_HEIGHT * 0.8F) * 2), tY, (int) ((float) CFG.BUTTON_HEIGHT * 0.8F), (int) ((float) CFG.BUTTON_HEIGHT * 0.6F), true));
        tY += ((MenuElement) menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;

        menuElements.add(new Button_NewGameStyle_Left("-", -1, CFG.PADDING, tY, (int) ((float) CFG.BUTTON_HEIGHT * 0.8F), (int) ((float) CFG.BUTTON_HEIGHT * 0.6F), true));
        menuElements.add(new Button_NewGameStyle_Middle((String) null, -1, CFG.PADDING + (int) ((float) CFG.BUTTON_HEIGHT * 0.8F), tY, tempW - CFG.PADDING * 2 - (int) ((float) CFG.BUTTON_HEIGHT * 0.8F) * 2, (int) ((float) CFG.BUTTON_HEIGHT * 0.6F), true));
        menuElements.add(new Button_NewGameStyle_Right("+", -1, CFG.PADDING + (int) ((float) CFG.BUTTON_HEIGHT * 0.8F) + (tempW - CFG.PADDING * 2 - (int) ((float) CFG.BUTTON_HEIGHT * 0.8F) * 2), tY, (int) ((float) CFG.BUTTON_HEIGHT * 0.8F), (int) ((float) CFG.BUTTON_HEIGHT * 0.6F), true));
        tY += ((MenuElement) menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
        menuElements.add(new Button_NewGameStyle_Left("-", -1, CFG.PADDING, tY, (int) ((float) CFG.BUTTON_HEIGHT * 0.8F), (int) ((float) CFG.BUTTON_HEIGHT * 0.6F), true));
        menuElements.add(new Button_NewGameStyle_Middle((String) null, -1, CFG.PADDING + (int) ((float) CFG.BUTTON_HEIGHT * 0.8F), tY, tempW - CFG.PADDING * 2 - (int) ((float) CFG.BUTTON_HEIGHT * 0.8F) * 2, (int) ((float) CFG.BUTTON_HEIGHT * 0.6F), true));
        menuElements.add(new Button_NewGameStyle_Right("+", -1, CFG.PADDING + (int) ((float) CFG.BUTTON_HEIGHT * 0.8F) + (tempW - CFG.PADDING * 2 - (int) ((float) CFG.BUTTON_HEIGHT * 0.8F) * 2), tY, (int) ((float) CFG.BUTTON_HEIGHT * 0.8F), (int) ((float) CFG.BUTTON_HEIGHT * 0.6F), true));
        tY += ((MenuElement) menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
        menuElements.add(new Button_NewGameStyle_Left("-", -1, CFG.PADDING, tY, (int) ((float) CFG.BUTTON_HEIGHT * 0.8F), (int) ((float) CFG.BUTTON_HEIGHT * 0.6F), true));
        menuElements.add(new Button_NewGameStyle_Middle((String) null, -1, CFG.PADDING + (int) ((float) CFG.BUTTON_HEIGHT * 0.8F), tY, tempW - CFG.PADDING * 2 - (int) ((float) CFG.BUTTON_HEIGHT * 0.8F) * 2, (int) ((float) CFG.BUTTON_HEIGHT * 0.6F), true));
        menuElements.add(new Button_NewGameStyle_Right("+", -1, CFG.PADDING + (int) ((float) CFG.BUTTON_HEIGHT * 0.8F) + (tempW - CFG.PADDING * 2 - (int) ((float) CFG.BUTTON_HEIGHT * 0.8F) * 2), tY, (int) ((float) CFG.BUTTON_HEIGHT * 0.8F), (int) ((float) CFG.BUTTON_HEIGHT * 0.6F), true));
        tY += ((MenuElement) menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
        menuElements.add(new Button_NewGameStyle_Left("-", -1, CFG.PADDING, tY, (int) ((float) CFG.BUTTON_HEIGHT * 0.8F), (int) ((float) CFG.BUTTON_HEIGHT * 0.6F), true));
        menuElements.add(new Button_NewGameStyle_Middle((String) null, -1, CFG.PADDING + (int) ((float) CFG.BUTTON_HEIGHT * 0.8F), tY, tempW - CFG.PADDING * 2 - (int) ((float) CFG.BUTTON_HEIGHT * 0.8F) * 2, (int) ((float) CFG.BUTTON_HEIGHT * 0.6F), true));
        menuElements.add(new Button_NewGameStyle_Right("+", -1, CFG.PADDING + (int) ((float) CFG.BUTTON_HEIGHT * 0.8F) + (tempW - CFG.PADDING * 2 - (int) ((float) CFG.BUTTON_HEIGHT * 0.8F) * 2), tY, (int) ((float) CFG.BUTTON_HEIGHT * 0.8F), (int) ((float) CFG.BUTTON_HEIGHT * 0.6F), true));
        tY += ((MenuElement) menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
        menuElements.add(new Button_NewGameStyle_Left("-", -1, CFG.PADDING, tY, (int) ((float) CFG.BUTTON_HEIGHT * 0.8F), (int) ((float) CFG.BUTTON_HEIGHT * 0.6F), true));
        menuElements.add(new Button_NewGameStyle_Middle((String) null, -1, CFG.PADDING + (int) ((float) CFG.BUTTON_HEIGHT * 0.8F), tY, tempW - CFG.PADDING * 2 - (int) ((float) CFG.BUTTON_HEIGHT * 0.8F) * 2, (int) ((float) CFG.BUTTON_HEIGHT * 0.6F), true));
        menuElements.add(new Button_NewGameStyle_Right("+", -1, CFG.PADDING + (int) ((float) CFG.BUTTON_HEIGHT * 0.8F) + (tempW - CFG.PADDING * 2 - (int) ((float) CFG.BUTTON_HEIGHT * 0.8F) * 2), tY, (int) ((float) CFG.BUTTON_HEIGHT * 0.8F), (int) ((float) CFG.BUTTON_HEIGHT * 0.6F), true));
        tY += ((MenuElement) menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;

        menuElements.add(new Button_NewGameStyle_Left("-", -1, CFG.PADDING, tY, (int) ((float) CFG.BUTTON_HEIGHT * 0.8F), (int) ((float) CFG.BUTTON_HEIGHT * 0.6F), true));
        menuElements.add(new Button_NewGameStyle_Middle((String) null, -1, CFG.PADDING + (int) ((float) CFG.BUTTON_HEIGHT * 0.8F), tY, tempW - CFG.PADDING * 2 - (int) ((float) CFG.BUTTON_HEIGHT * 0.8F) * 2, (int) ((float) CFG.BUTTON_HEIGHT * 0.6F), true));
        menuElements.add(new Button_NewGameStyle_Right("+", -1, CFG.PADDING + (int) ((float) CFG.BUTTON_HEIGHT * 0.8F) + (tempW - CFG.PADDING * 2 - (int) ((float) CFG.BUTTON_HEIGHT * 0.8F) * 2), tY, (int) ((float) CFG.BUTTON_HEIGHT * 0.8F), (int) ((float) CFG.BUTTON_HEIGHT * 0.6F), true));
        tY += ((MenuElement) menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
        menuElements.add(new Button_NewGameStyle_Left("-", -1, CFG.PADDING, tY, (int) ((float) CFG.BUTTON_HEIGHT * 0.8F), (int) ((float) CFG.BUTTON_HEIGHT * 0.6F), true));
        menuElements.add(new Button_NewGameStyle_Middle((String) null, -1, CFG.PADDING + (int) ((float) CFG.BUTTON_HEIGHT * 0.8F), tY, tempW - CFG.PADDING * 2 - (int) ((float) CFG.BUTTON_HEIGHT * 0.8F) * 2, (int) ((float) CFG.BUTTON_HEIGHT * 0.6F), true));
        menuElements.add(new Button_NewGameStyle_Right("+", -1, CFG.PADDING + (int) ((float) CFG.BUTTON_HEIGHT * 0.8F) + (tempW - CFG.PADDING * 2 - (int) ((float) CFG.BUTTON_HEIGHT * 0.8F) * 2), tY, (int) ((float) CFG.BUTTON_HEIGHT * 0.8F), (int) ((float) CFG.BUTTON_HEIGHT * 0.6F), true));
        tY += ((MenuElement) menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
        menuElements.add(new Button_NewGameStyle_Left("-", -1, CFG.PADDING, tY, (int) ((float) CFG.BUTTON_HEIGHT * 0.8F), (int) ((float) CFG.BUTTON_HEIGHT * 0.6F), true));
        menuElements.add(new Button_NewGameStyle_Middle((String) null, -1, CFG.PADDING + (int) ((float) CFG.BUTTON_HEIGHT * 0.8F), tY, tempW - CFG.PADDING * 2 - (int) ((float) CFG.BUTTON_HEIGHT * 0.8F) * 2, (int) ((float) CFG.BUTTON_HEIGHT * 0.6F), true));
        menuElements.add(new Button_NewGameStyle_Right("+", -1, CFG.PADDING + (int) ((float) CFG.BUTTON_HEIGHT * 0.8F) + (tempW - CFG.PADDING * 2 - (int) ((float) CFG.BUTTON_HEIGHT * 0.8F) * 2), tY, (int) ((float) CFG.BUTTON_HEIGHT * 0.8F), (int) ((float) CFG.BUTTON_HEIGHT * 0.6F), true));
        tY += ((MenuElement) menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
        menuElements.add(new Button_NewGameStyle_Left("-", -1, CFG.PADDING, tY, (int) ((float) CFG.BUTTON_HEIGHT * 0.8F), (int) ((float) CFG.BUTTON_HEIGHT * 0.6F), true));
        menuElements.add(new Button_NewGameStyle_Middle((String) null, -1, CFG.PADDING + (int) ((float) CFG.BUTTON_HEIGHT * 0.8F), tY, tempW - CFG.PADDING * 2 - (int) ((float) CFG.BUTTON_HEIGHT * 0.8F) * 2, (int) ((float) CFG.BUTTON_HEIGHT * 0.6F), true));
        menuElements.add(new Button_NewGameStyle_Right("+", -1, CFG.PADDING + (int) ((float) CFG.BUTTON_HEIGHT * 0.8F) + (tempW - CFG.PADDING * 2 - (int) ((float) CFG.BUTTON_HEIGHT * 0.8F) * 2), tY, (int) ((float) CFG.BUTTON_HEIGHT * 0.8F), (int) ((float) CFG.BUTTON_HEIGHT * 0.6F), true));
        tY += ((MenuElement) menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
        menuElements.add(new Button_NewGameStyle_Left("-", -1, CFG.PADDING, tY, (int) ((float) CFG.BUTTON_HEIGHT * 0.8F), (int) ((float) CFG.BUTTON_HEIGHT * 0.6F), true));
        menuElements.add(new Button_NewGameStyle_Middle((String) null, -1, CFG.PADDING + (int) ((float) CFG.BUTTON_HEIGHT * 0.8F), tY, tempW - CFG.PADDING * 2 - (int) ((float) CFG.BUTTON_HEIGHT * 0.8F) * 2, (int) ((float) CFG.BUTTON_HEIGHT * 0.6F), true));
        menuElements.add(new Button_NewGameStyle_Right("+", -1, CFG.PADDING + (int) ((float) CFG.BUTTON_HEIGHT * 0.8F) + (tempW - CFG.PADDING * 2 - (int) ((float) CFG.BUTTON_HEIGHT * 0.8F) * 2), tY, (int) ((float) CFG.BUTTON_HEIGHT * 0.8F), (int) ((float) CFG.BUTTON_HEIGHT * 0.6F), true));
        tY += ((MenuElement) menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
        menuElements.add(new Button_NewGameStyle_Left("-", -1, CFG.PADDING, tY, (int) ((float) CFG.BUTTON_HEIGHT * 0.8F), (int) ((float) CFG.BUTTON_HEIGHT * 0.6F), true));
        menuElements.add(new Button_NewGameStyle_Middle((String) null, -1, CFG.PADDING + (int) ((float) CFG.BUTTON_HEIGHT * 0.8F), tY, tempW - CFG.PADDING * 2 - (int) ((float) CFG.BUTTON_HEIGHT * 0.8F) * 2, (int) ((float) CFG.BUTTON_HEIGHT * 0.6F), true));
        menuElements.add(new Button_NewGameStyle_Right("+", -1, CFG.PADDING + (int) ((float) CFG.BUTTON_HEIGHT * 0.8F) + (tempW - CFG.PADDING * 2 - (int) ((float) CFG.BUTTON_HEIGHT * 0.8F) * 2), tY, (int) ((float) CFG.BUTTON_HEIGHT * 0.8F), (int) ((float) CFG.BUTTON_HEIGHT * 0.6F), true));
        tY += ((MenuElement) menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
        menuElements.add(new Button_NewGameStyle_Left("-", -1, CFG.PADDING, tY, (int) ((float) CFG.BUTTON_HEIGHT * 0.8F), (int) ((float) CFG.BUTTON_HEIGHT * 0.6F), true));
        menuElements.add(new Button_NewGameStyle_Middle((String) null, -1, CFG.PADDING + (int) ((float) CFG.BUTTON_HEIGHT * 0.8F), tY, tempW - CFG.PADDING * 2 - (int) ((float) CFG.BUTTON_HEIGHT * 0.8F) * 2, (int) ((float) CFG.BUTTON_HEIGHT * 0.6F), true));
        menuElements.add(new Button_NewGameStyle_Right("+", -1, CFG.PADDING + (int) ((float) CFG.BUTTON_HEIGHT * 0.8F) + (tempW - CFG.PADDING * 2 - (int) ((float) CFG.BUTTON_HEIGHT * 0.8F) * 2), tY, (int) ((float) CFG.BUTTON_HEIGHT * 0.8F), (int) ((float) CFG.BUTTON_HEIGHT * 0.6F), true));
        tY += ((MenuElement) menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
        menuElements.add(new Button_NewGameStyle_Left("-", -1, CFG.PADDING, tY, (int) ((float) CFG.BUTTON_HEIGHT * 0.8F), (int) ((float) CFG.BUTTON_HEIGHT * 0.6F), true));
        menuElements.add(new Button_NewGameStyle_Middle((String) null, -1, CFG.PADDING + (int) ((float) CFG.BUTTON_HEIGHT * 0.8F), tY, tempW - CFG.PADDING * 2 - (int) ((float) CFG.BUTTON_HEIGHT * 0.8F) * 2, (int) ((float) CFG.BUTTON_HEIGHT * 0.6F), true));
        menuElements.add(new Button_NewGameStyle_Right("+", -1, CFG.PADDING + (int) ((float) CFG.BUTTON_HEIGHT * 0.8F) + (tempW - CFG.PADDING * 2 - (int) ((float) CFG.BUTTON_HEIGHT * 0.8F) * 2), tY, (int) ((float) CFG.BUTTON_HEIGHT * 0.8F), (int) ((float) CFG.BUTTON_HEIGHT * 0.6F), true));
        int var10000 = tY + ((MenuElement) menuElements.get(menuElements.size() - 1)).getHeight() + CFG.PADDING;
        this.initMenu(new SliderMenuTitle((String) null, CFG.BUTTON_HEIGHT * 3 / 4, false, false) {
            protected void draw(SpriteBatch oSB, int iTranslateX, int nPosX, int nPosY, int nWidth, boolean sliderMenuIsActive) {
                ImageManager.getImage(Images.new_game_top_edge_title).draw2(oSB, Menu_Decisions_Editor_Edit_Data.this.getPosX() - 2 + iTranslateX, Menu_Decisions_Editor_Edit_Data.this.getPosY() - ImageManager.getImage(Images.new_game_top_edge_title).getHeight() - this.getHeight(), Menu_Decisions_Editor_Edit_Data.this.getWidth() + 2, this.getHeight(), true, false);
                oSB.setColor(new Color(CFG.COLOR_GRADIENT_TITLE_BLUE.r, CFG.COLOR_GRADIENT_TITLE_BLUE.g, CFG.COLOR_GRADIENT_TITLE_BLUE.b, 0.425F));
                ImageManager.getImage(Images.gradient).draw(oSB, Menu_Decisions_Editor_Edit_Data.this.getPosX() + iTranslateX, Menu_Decisions_Editor_Edit_Data.this.getPosY() - ImageManager.getImage(Images.gradient).getHeight() - this.getHeight() * 3 / 4, Menu_Decisions_Editor_Edit_Data.this.getWidth(), this.getHeight() * 3 / 4, false, true);
                oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.65F));
                ImageManager.getImage(Images.gradient).draw(oSB, Menu_Decisions_Editor_Edit_Data.this.getPosX() + iTranslateX, Menu_Decisions_Editor_Edit_Data.this.getPosY() - ImageManager.getImage(Images.gradient).getHeight() - CFG.PADDING, Menu_Decisions_Editor_Edit_Data.this.getWidth(), CFG.PADDING, false, true);
                oSB.setColor(new Color(0.451F, 0.329F, 0.11F, 1.0F));
                ImageManager.getImage(Images.pix255_255_255).draw(oSB, Menu_Decisions_Editor_Edit_Data.this.getPosX() + iTranslateX, Menu_Decisions_Editor_Edit_Data.this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight(), Menu_Decisions_Editor_Edit_Data.this.getWidth());
                oSB.setColor(new Color(0.0F, 0.0F, 0.0F, 0.9F));
                ImageManager.getImage(Images.line_32_off1).draw(oSB, Menu_Decisions_Editor_Edit_Data.this.getPosX() + iTranslateX, Menu_Decisions_Editor_Edit_Data.this.getPosY() - ImageManager.getImage(Images.pix255_255_255).getHeight() - ImageManager.getImage(Images.line_32_off1).getHeight(), Menu_Decisions_Editor_Edit_Data.this.getWidth(), 1);
                oSB.setColor(Color.WHITE);
                CFG.fontMain.getData().setScale(0.75F);
                CFG.drawText(oSB, this.getText(), nPosX + nWidth / 2 - (int) ((float) this.getTextWidth() * 0.75F / 2.0F) + iTranslateX, nPosY - this.getHeight() + this.getHeight() / 2 + 1 - (int) ((float) this.getTextHeight() * 0.75F / 2.0F), CFG.COLOR_TEXT_MODIFIER_NEUTRAL);
                CFG.fontMain.getData().setScale(1.0F);
            }
        }, 0, CFG.BUTTON_HEIGHT * 2 + CFG.PADDING * 4 + CFG.PADDING * 2 + CFG.BUTTON_HEIGHT / 4, tempW, CFG.GAME_HEIGHT - (CFG.PADDING * 2 + CFG.BUTTON_HEIGHT * 2) - CFG.BUTTON_HEIGHT, menuElements);
        this.updateLanguage();
    }

    protected void updateLanguage() {
        this.sName = CFG.langManager.get("Name");
        this.sImage = CFG.langManager.get("Description");
        this.sBorn = CFG.langManager.get("Date");
        this.getTitle().setText(CFG.langManager.get("Leader"));
        this.getMenuElement(2).setText(Game_Calendar.currentDay + " " + Game_Calendar.getMonthName(Game_Calendar.currentMonth) + " " + CFG.gameAges.getYear(Game_Calendar.currentYear));

        this.getMenuElement(3).setText(CFG.langManager.get("Repeatable") + ": " + (CFG.civDecision_GameData.isRepeatable() ? CFG.langManager.get("Yes") : CFG.langManager.get("No")));
        this.getMenuElement(4).setText(CFG.langManager.get("ChargedEveryTurn") + ": " + (CFG.civDecision_GameData.isCostEveryTurn() ? CFG.langManager.get("Yes") : CFG.langManager.get("No")));

        this.getMenuElement(6).setText(CFG.langManager.get("Gold") + ": " + (CFG.civDecision_GameData.getGoldCost() >= 0.0F ? "-" : "+") + Math.abs(CFG.civDecision_GameData.getGoldCost()));
        this.getMenuElement(9).setText(CFG.langManager.get("DiplomacyPoints") + ": " + (CFG.civDecision_GameData.getDiploCost() >= 0.0F ? "-" : "+") + Math.abs(((int) (CFG.civDecision_GameData.getDiploCost() * 1000.0F)) / 10000.0F));

        this.getMenuElement(12).setText(CFG.langManager.get("UpperClass") + ": " + (CFG.civDecision_GameData.fModifier_UpperClass > 0.0F ? "+" : "") + (int) (CFG.civDecision_GameData.fModifier_UpperClass * 100.0F) + "%");
        this.getMenuElement(15).setText(CFG.langManager.get("MiddleClass") + ": " + (CFG.civDecision_GameData.fModifier_MiddleClass > 0.0F ? "+" : "") + (int) (CFG.civDecision_GameData.fModifier_MiddleClass * 100.0F) + "%");
        this.getMenuElement(18).setText(CFG.langManager.get("LowerClass") + ": " + (CFG.civDecision_GameData.fModifier_LowerClass > 0.0F ? "+" : "") + (int) (CFG.civDecision_GameData.fModifier_LowerClass * 100.0F) + "%");

        this.getMenuElement(21).setText(CFG.langManager.get("PopulationGrowthModifier") + ": " + (CFG.civDecision_GameData.fModifier_PopGrowth > 0.0F ? "+" : "") + (int) (CFG.civDecision_GameData.fModifier_PopGrowth * 100.0F) + "%");
        this.getMenuElement(24).setText(CFG.langManager.get("EconomyGrowthModifier") + ": " + (CFG.civDecision_GameData.fModifier_EconomyGrowth > 0.0F ? "+" : "") + (int) (CFG.civDecision_GameData.fModifier_EconomyGrowth * 100.0F) + "%");
        this.getMenuElement(27).setText(CFG.langManager.get("IncomeTaxation") + ": " + (CFG.civDecision_GameData.fModifier_IncomeTaxation > 0.0F ? "+" : "") + (int) (CFG.civDecision_GameData.fModifier_IncomeTaxation * 100.0F) + "%");
        this.getMenuElement(30).setText(CFG.langManager.get("IncomeProduction") + ": " + (CFG.civDecision_GameData.fModifier_IncomeProduction > 0.0F ? "+" : "") + (int) (CFG.civDecision_GameData.fModifier_IncomeProduction * 100.0F) + "%");
        this.getMenuElement(33).setText(CFG.langManager.get("Administration") + ": " + (CFG.civDecision_GameData.fModifier_Administration > 0.0F ? "+" : "") + (int) (CFG.civDecision_GameData.fModifier_Administration * 100.0F) + "%");
        this.getMenuElement(36).setText(CFG.langManager.get("Research") + ": " + (CFG.civDecision_GameData.fModifier_Research > 0.0F ? "+" : "") + (int) (CFG.civDecision_GameData.fModifier_Research * 100.0F) + "%");
        this.getMenuElement(39).setText(CFG.langManager.get("MilitaryUpkeep") + ": " + (CFG.civDecision_GameData.fModifier_MilitaryUpkeep > 0.0F ? "+" : "") + (int) (CFG.civDecision_GameData.fModifier_MilitaryUpkeep * 100.0F) + "%");
        this.getMenuElement(42).setText(CFG.langManager.get("AttackBonus") + ": " + (CFG.civDecision_GameData.fModifier_AttackBonus > 0.0F ? "+" : "") + (int) (CFG.civDecision_GameData.fModifier_AttackBonus * 100.0F) + "%");
        this.getMenuElement(45).setText(CFG.langManager.get("DefenseBonus") + ": " + (CFG.civDecision_GameData.fModifier_DefenseBonus > 0.0F ? "+" : "") + (int) (CFG.civDecision_GameData.fModifier_DefenseBonus * 100.0F) + "%");
        this.getMenuElement(48).setText(CFG.langManager.get("MovementPoints") + ": " + (CFG.civDecision_GameData.fModifier_MovementPoints > 0.0F ? "+" : "") + (int) (CFG.civDecision_GameData.fModifier_MovementPoints * 100.0F) + "%");
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
                CFG.menuManager.saveDecisions_Edit_Data();
                CFG.backToMenu = Menu.eGAME_DECISIONS_EDITOR_EDIT;
                CFG.menuManager.setViewID(Menu.eSCENARIO_AGE);
                CFG.menuManager.updateSelecetScenarioAge_Slider();
                return;
            }
            case 3: {
                CFG.civDecision_GameData.setRepeatable(!CFG.civDecision_GameData.isRepeatable());
                this.updateLanguage();
                return;
            }
            case 4: {
                CFG.civDecision_GameData.setCostEveryTurn(!CFG.civDecision_GameData.isCostEveryTurn());
                this.updateLanguage();
                return;
            }
            case 6:
            case 9:
            case 12:
            case 15:
            case 18:
            case 21:
            case 24:
            case 27:
            case 30:
            case 33:
            case 36:
            case 39:
            case 42:
            case 45:
            case 48: {
                CFG.toast.setInView(this.getMenuElement(iID).getText(), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
                return;
            }
            case 5: {
                if (AoCGame.isShiftDown) {
                    CFG.civDecision_GameData.setGoldCost(CFG.civDecision_GameData.getGoldCost() - 100.0F);
                } else {
                    CFG.civDecision_GameData.setGoldCost(CFG.civDecision_GameData.getGoldCost() - 10.0F);
                }
                this.updateLanguage();
                return;
            }
            case 7: {
                if (AoCGame.isShiftDown) {
                    CFG.civDecision_GameData.setGoldCost(CFG.civDecision_GameData.getGoldCost() + 100.0F);
                } else {
                    CFG.civDecision_GameData.setGoldCost(CFG.civDecision_GameData.getGoldCost() + 10.0F);
                }
                this.updateLanguage();
                return;
            }
            case 8: {
                if (AoCGame.isShiftDown) {
                    CFG.civDecision_GameData.setDiploCost(CFG.civDecision_GameData.getDiploCost() - 10.0F);
                } else {
                    CFG.civDecision_GameData.setDiploCost(CFG.civDecision_GameData.getDiploCost() - 1.00F);
                }
                this.updateLanguage();
                return;
            }
            case 10: {
                if (AoCGame.isShiftDown) {
                    CFG.civDecision_GameData.setDiploCost(CFG.civDecision_GameData.getDiploCost() + 10.0F);
                } else {
                    CFG.civDecision_GameData.setDiploCost(CFG.civDecision_GameData.getDiploCost() + 1.00F);
                }
                this.updateLanguage();
                return;
            }
            case 11: {
                if (AoCGame.isShiftDown) {
                    CFG.civDecision_GameData.fModifier_UpperClass -= 0.1f;
                } else {
                    CFG.civDecision_GameData.fModifier_UpperClass -= 0.01f;
                }
                this.updateLanguage();
                return;
            }
            case 13: {
                if (AoCGame.isShiftDown) {
                    CFG.civDecision_GameData.fModifier_UpperClass += 0.1f;
                } else {
                    CFG.civDecision_GameData.fModifier_UpperClass += 0.01f;
                }
                this.updateLanguage();
                return;
            }
            case 14: {
                if (AoCGame.isShiftDown) {
                    CFG.civDecision_GameData.fModifier_MiddleClass -= 0.1f;
                } else {
                    CFG.civDecision_GameData.fModifier_MiddleClass -= 0.01f;
                }
                this.updateLanguage();
                return;
            }
            case 16: {
                if (AoCGame.isShiftDown) {
                    CFG.civDecision_GameData.fModifier_MiddleClass += 0.1f;
                } else {
                    CFG.civDecision_GameData.fModifier_MiddleClass += 0.01f;
                }
                this.updateLanguage();
                return;
            }
            case 17: {
                if (AoCGame.isShiftDown) {
                    CFG.civDecision_GameData.fModifier_LowerClass -= 0.1f;
                } else {
                    CFG.civDecision_GameData.fModifier_LowerClass -= 0.01f;
                }
                this.updateLanguage();
                return;
            }
            case 19: {
                if (AoCGame.isShiftDown) {
                    CFG.civDecision_GameData.fModifier_LowerClass += 0.1f;
                } else {
                    CFG.civDecision_GameData.fModifier_LowerClass += 0.01f;
                }
                this.updateLanguage();
                return;
            }
            case 20: {
                if (AoCGame.isShiftDown) {
                    CFG.civDecision_GameData.fModifier_PopGrowth -= 0.1f;
                } else {
                    CFG.civDecision_GameData.fModifier_PopGrowth -= 0.01f;
                }
                this.updateLanguage();
                return;
            }
            case 22: {
                if (AoCGame.isShiftDown) {
                    CFG.civDecision_GameData.fModifier_PopGrowth += 0.1f;
                } else {
                    CFG.civDecision_GameData.fModifier_PopGrowth += 0.01f;
                }
                this.updateLanguage();
                return;
            }
            case 23: {
                if (AoCGame.isShiftDown) {
                    CFG.civDecision_GameData.fModifier_EconomyGrowth -= 0.1f;
                } else {
                    CFG.civDecision_GameData.fModifier_EconomyGrowth -= 0.01f;
                }
                this.updateLanguage();
                return;
            }
            case 25: {
                if (AoCGame.isShiftDown) {
                    CFG.civDecision_GameData.fModifier_EconomyGrowth += 0.1f;
                } else {
                    CFG.civDecision_GameData.fModifier_EconomyGrowth += 0.01f;
                }
                this.updateLanguage();
                return;
            }
            case 26: {
                if (AoCGame.isShiftDown) {
                    CFG.civDecision_GameData.fModifier_IncomeTaxation -= 0.1f;
                } else {
                    CFG.civDecision_GameData.fModifier_IncomeTaxation -= 0.01f;
                }
                this.updateLanguage();
                return;
            }
            case 28: {
                if (AoCGame.isShiftDown) {
                    CFG.civDecision_GameData.fModifier_IncomeTaxation += 0.1f;
                } else {
                    CFG.civDecision_GameData.fModifier_IncomeTaxation += 0.01f;
                }
                this.updateLanguage();
                return;
            }
            case 29: {
                if (AoCGame.isShiftDown) {
                    CFG.civDecision_GameData.fModifier_IncomeProduction -= 0.1f;
                } else {
                    CFG.civDecision_GameData.fModifier_IncomeProduction -= 0.01f;
                }
                this.updateLanguage();
                return;
            }
            case 31: {
                if (AoCGame.isShiftDown) {
                    CFG.civDecision_GameData.fModifier_IncomeProduction += 0.1f;
                } else {
                    CFG.civDecision_GameData.fModifier_IncomeProduction += 0.01f;
                }
                this.updateLanguage();
                return;
            }
            case 32: {
                if (AoCGame.isShiftDown) {
                    CFG.civDecision_GameData.fModifier_Administration -= 0.1f;
                } else {
                    CFG.civDecision_GameData.fModifier_Administration -= 0.01f;
                }
                this.updateLanguage();
                return;
            }
            case 34: {
                if (AoCGame.isShiftDown) {
                    CFG.civDecision_GameData.fModifier_Administration += 0.1f;
                } else {
                    CFG.civDecision_GameData.fModifier_Administration += 0.01f;
                }
                this.updateLanguage();
                return;
            }
            case 35: {
                if (AoCGame.isShiftDown) {
                    CFG.civDecision_GameData.fModifier_Research -= 0.1f;
                } else {
                    CFG.civDecision_GameData.fModifier_Research -= 0.01f;
                }
                this.updateLanguage();
                return;
            }
            case 37: {
                if (AoCGame.isShiftDown) {
                    CFG.civDecision_GameData.fModifier_Research += 0.1f;
                } else {
                    CFG.civDecision_GameData.fModifier_Research += 0.01f;
                }
                this.updateLanguage();
                return;
            }
            case 38: {
                if (AoCGame.isShiftDown) {
                    CFG.civDecision_GameData.fModifier_MilitaryUpkeep -= 0.1f;
                } else {
                    CFG.civDecision_GameData.fModifier_MilitaryUpkeep -= 0.01f;
                }
                this.updateLanguage();
                return;
            }
            case 40: {
                if (AoCGame.isShiftDown) {
                    CFG.civDecision_GameData.fModifier_MilitaryUpkeep += 0.1f;
                } else {
                    CFG.civDecision_GameData.fModifier_MilitaryUpkeep += 0.01f;
                }
                this.updateLanguage();
                return;
            }
            case 41: {
                if (AoCGame.isShiftDown) {
                    CFG.civDecision_GameData.fModifier_AttackBonus -= 0.1f;
                } else {
                    CFG.civDecision_GameData.fModifier_AttackBonus -= 0.01f;
                }
                this.updateLanguage();
                return;
            }
            case 43: {
                if (AoCGame.isShiftDown) {
                    CFG.civDecision_GameData.fModifier_AttackBonus += 0.1f;
                } else {
                    CFG.civDecision_GameData.fModifier_AttackBonus += 0.01f;
                }
                this.updateLanguage();
                return;
            }
            case 44: {
                if (AoCGame.isShiftDown) {
                    CFG.civDecision_GameData.fModifier_DefenseBonus -= 0.1f;
                } else {
                    CFG.civDecision_GameData.fModifier_DefenseBonus -= 0.01f;
                }
                this.updateLanguage();
                return;
            }
            case 46: {
                if (AoCGame.isShiftDown) {
                    CFG.civDecision_GameData.fModifier_DefenseBonus += 0.1f;
                } else {
                    CFG.civDecision_GameData.fModifier_DefenseBonus += 0.01f;
                }
                this.updateLanguage();
                return;
            }
            case 47: {
                if (AoCGame.isShiftDown) {
                    CFG.civDecision_GameData.fModifier_MovementPoints -= 0.1f;
                } else {
                    CFG.civDecision_GameData.fModifier_MovementPoints -= 0.01f;
                }
                this.updateLanguage();
                return;
            }
            case 49: {
                if (AoCGame.isShiftDown) {
                    CFG.civDecision_GameData.fModifier_MovementPoints += 0.1f;
                } else {
                    CFG.civDecision_GameData.fModifier_MovementPoints += 0.01f;
                }
                this.updateLanguage();
                return;
            }
            case 50: {
                //CFG.civDecision_GameData.setCanAppearNaturally(!CFG.civDecision_GameData.canAppearNaturally());
                this.updateLanguage();
                return;
            }
        }
    }
}
