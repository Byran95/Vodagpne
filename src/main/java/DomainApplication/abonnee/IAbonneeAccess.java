package DomainApplication.abonnee;

import DomainApplication.IAbonnee;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Bryan van Elden on 12/10/2016.
 */
public interface IAbonneeAccess {

    List<IAbonnee> getAllAbonnees();

    IAbonnee findAbonneeMetEmail(String email);
    IAbonnee findAbonneeById( int  findId );

    void createAbonnee(String naam, String achternaam, String emailadres);

}