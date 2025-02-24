package presentation;

import dao.IDao;
import metier.IMetier;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Scanner;

public class PresDynamique {
    public static void main(String[] args) throws Exception {

        try {
            Scanner sc = new Scanner(new File("Config.txt"));
            /*
            Injection de dépendance par
            instanciation dynamique
            */

            // les trois premiére ligne équivalente à "IDao dao = new IDaoImpl();"
            String daoClassName = sc.nextLine();
            Class cDao = Class.forName(daoClassName);
            IDao dao = (IDao) cDao.getConstructor().newInstance();
            System.out.println("Résultat = "+dao.getData());

            // les trois premiére ligne équivalente à "IMetierImpl metier1 = new IMetierImpl(dao);"
            String metierClassName = sc.nextLine();
            Class cMetier = Class.forName(metierClassName);
            // instanciation via le constructeur
            IMetier metier = (IMetier) cMetier.getConstructor(IDao.class).newInstance(dao);

            System.out.println("Résultats = "+metier.calcul());

            // instanciation via le setter équivalente à "metier.setDao(dao);"
            //Method setDao = cMetier.getDeclaredMethod("setDao", IDao.class);
            //setDao.invoke(metier, dao);

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
