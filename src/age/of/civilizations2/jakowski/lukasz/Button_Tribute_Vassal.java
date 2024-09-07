package age.of.civilizations2.jakowski.lukasz;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

class Button_Tribute_Vassal extends Button_Build {
   private int iIdelogyID;
   protected int iCivID;

   protected Button_Tribute_Vassal(String sText, int iPosX, int iPosY, int nCivID) {
      super(sText, Images.diplo_vassal, 0, 0, iPosX, iPosY, Button_Diplomacy.iDiploWidth, true, false, 0, 0.0F);
      this.setHeight(CFG.TEXT_HEIGHT + CFG.PADDING * 2 + CFG.PADDING * 4);
      this.iIdelogyID = CFG.game.getCiv(this.iCivID).getIdeologyID();
      this.iCivID = nCivID;
   }

   protected void drawText(SpriteBatch oSB, int iTranslateX, int iTranslateY, boolean isActive) {
      try {
         CFG.ideologiesManager.getIdeology(this.iIdelogyID).getCrownImageScaled().draw(oSB, this.getPosX() + Button_Diplomacy.iDiploWidth / 2 - CFG.ideologiesManager.getIdeology(this.iIdelogyID).getCrownImageScaled().getWidth() / 2 + iTranslateX, this.getPosY() + this.getHeight() / 2 - CFG.ideologiesManager.getIdeology(this.iIdelogyID).getCrownImageScaled().getHeight() / 2 + iTranslateY);
      } catch (IndexOutOfBoundsException var6) {
         ImageManager.getImage(this.iImageID).draw(oSB, this.getPosX() + Button_Diplomacy.iDiploWidth / 2 - ImageManager.getImage(this.iImageID).getWidth() / 2 + iTranslateX, this.getPosY() + this.getHeight() / 2 - ImageManager.getImage(this.iImageID).getHeight() / 2 + iTranslateY);
      }

   }

   protected void buildElementHover() {
      int iAutonomyID = CFG.game.getCiv(CFG.game.getPlayer(CFG.PLAYER_TURNID).getCivID()).getVassal_AutonomyStatus(this.iCivID).getIndexOf();

      List nElements = new ArrayList();
      List nData = new ArrayList();
      nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("Autonomy") + ": ", CFG.COLOR_TEXT_NUM_OF_PROVINCES));
      nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.gameAutonomy.lAutonomy.get(iAutonomyID).getName(), CFG.COLOR_INGAME_GOLD));
      nElements.add(new MenuElement_Hover_v2_Element2(nData));
      nData.clear();

      nData.add(new MenuElement_Hover_v2_Element_Type_Space());
      nElements.add(new MenuElement_Hover_v2_Element2(nData));
      nData.clear();

      nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("AutoJoinWars") + ": "));
      nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.gameAutonomy.lAutonomy.get(iAutonomyID).isAutoJoinWarPerms() ? CFG.langManager.get("Yes") : CFG.langManager.get("No"), CFG.COLOR_TEXT_MODIFIER_NEUTRAL2));
      nElements.add(new MenuElement_Hover_v2_Element2(nData));
      nData.clear();
      nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("HasIndependentMilitary") + ": "));
      nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.gameAutonomy.lAutonomy.get(iAutonomyID).isMilitaryControl() ? CFG.langManager.get("Yes") : CFG.langManager.get("No"), CFG.COLOR_TEXT_MODIFIER_NEUTRAL2));
      nElements.add(new MenuElement_Hover_v2_Element2(nData));
      nData.clear();
      nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("HasIndependentEconomy") + ": "));
      nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.gameAutonomy.lAutonomy.get(iAutonomyID).isEconomicControl() ? CFG.langManager.get("Yes") : CFG.langManager.get("No"), CFG.COLOR_TEXT_MODIFIER_NEUTRAL2));
      nElements.add(new MenuElement_Hover_v2_Element2(nData));
      nData.clear();
      nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.langManager.get("AutoAcceptsTrades") + ": "));
      nData.add(new MenuElement_Hover_v2_Element_Type_Text(CFG.gameAutonomy.lAutonomy.get(iAutonomyID).isTradeIntegration() ? CFG.langManager.get("Yes") : CFG.langManager.get("No"), CFG.COLOR_TEXT_MODIFIER_NEUTRAL2));
      nElements.add(new MenuElement_Hover_v2_Element2(nData));
      nData.clear();

      this.menuElementHover = new MenuElement_Hover_v2(nElements);
   }

   protected void actionElement(int iID) {
      if (CFG.game.getCiv(this.iCivID).getCapitalProvinceID() >= 0) {
         CFG.map.getMapCoordinates().centerToProvinceID(CFG.game.getCiv(this.iCivID).getCapitalProvinceID());
         CFG.toast.setInView(CFG.game.getProvince(CFG.game.getCiv(this.iCivID).getCapitalProvinceID()).getName(), CFG.COLOR_TEXT_NUM_OF_PROVINCES);
      }

   }
}
