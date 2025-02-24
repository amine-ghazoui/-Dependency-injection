package metier;

import dao.IDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class IMetierImpl implements IMetier {


    public IDao dao;
    @Override
    public double calcul() {
        double valeur = dao.getData();
        double result = valeur * 12;
        return result;
    }

    public IMetierImpl(@Qualifier("dao2") IDao dao) {
        this.dao = dao;
    }


    /*
        pour injecter dans la variable dao
        un objet de la classe qui impléments
        l'interface IDao
    */
    public void setDao(IDao dao) {

        this.dao = dao;
    }
}
