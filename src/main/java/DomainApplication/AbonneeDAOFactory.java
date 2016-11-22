package DomainApplication;

import DomainApplication.MySQLDataAccess.AbonneeDAOMySQL;

/**
 * Created by Anders Egberts on 18/10/2016.
 */
public class AbonneeDAOFactory {

    /**
     * Gets the Data Acces Object based on the databasetype in settings.xml. (Eis: IM1)
     * @return the appropriate DAO or null if no DAO matching the database type was found.
     */
    public static IAbonneeAccess getAccessObject() {
        switch ( SettingsReader.getPropertyString( SettingsReader.DATABASE_TYPE_PROPERTY , "MySQL" ) ) {
            case "MySQL":
                return new AbonneeDAOMySQL();
        }
        return null;
    }
}