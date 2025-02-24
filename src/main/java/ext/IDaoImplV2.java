package ext;

import dao.IDao;
import org.springframework.stereotype.Component;

@Component("dao2")
public class IDaoImplV2 implements IDao {


    @Override
    public double getData() {
        System.out.println("Version capteur");
        double temp = 6000;
        return temp;
    }
}
