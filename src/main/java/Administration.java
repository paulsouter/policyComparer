/*
 * Author Paul Souter
 * 
 */
import dao.PartyDao;
import dao.PolicyDao;
import dao.TagsDao;
import gui.MainMenu;
/**
 *
 * @author soupa868
 */
public class Administration {
    public static void main(String[] args) {
        MainMenu main = new MainMenu( new PolicyDao(), new PartyDao(), new TagsDao());
        main.setLocationRelativeTo(null);
        main.setVisible(true);
    }
}