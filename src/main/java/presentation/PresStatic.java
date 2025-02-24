package presentation;

import dao.IDao;
import dao.IDaoImpl;
import metier.IMetierImpl;

public class PresStatic {
    public static void main(String[] args) {

        /*
        Injection de dépendance par
        instanciation statique ==> new
         */
        IDao dao = new IDaoImpl();
        //IMetierImpl metier1 = new IMetierImpl(dao); // injection via le constructeur

        IMetierImpl metier = new IMetierImpl();
        metier.setDao(dao); // Injection via le setter

        System.out.println("Resultats = "+metier.calcul());
    }
}
