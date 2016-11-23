package DomainApplication.abonnement.servlets;

import DomainApplication.*;
import DomainApplication.abonnement.Abonnement;
import DomainApplication.abonnement.AbonnementService;
import DomainApplication.abonnement.AbonnementSoort;
import DomainApplication.abonnement.AbonnementStatus;
import DomainApplication.dienst.DienstService;
import jersey.AbonnementJerseyService;
import jersey.DienstJerseyService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Anders Egberts on 20/10/2016.
 */
@WebServlet(
        value = "/tryService"
)
public class ProbeerDienstServlet extends HttpServlet {
    AbonnementService service = new AbonnementService();
    AbonnementJerseyService jerseyService = new AbonnementJerseyService();

    DienstService dienstService = new DienstService();
    DienstJerseyService dienstJerseyService = new DienstJerseyService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IAbonnee loggedInUser = (IAbonnee) req.getSession().getAttribute( "loggedInUser" );
        if ( null == loggedInUser ) {
            req.getRequestDispatcher( "/login.jsp" ).forward( req , resp );
            return;
        }

        String companyName = req.getParameter( "cn" );
        String serviceName = req.getParameter( "sn" );
        IDienst serviceToTryout = dienstJerseyService.getServiceByCompanyAndName( companyName , serviceName );

        int userId = loggedInUser.getAbonneeId();

        IAbonnement abonnement = new Abonnement( userId , "" , false , AbonnementSoort.MAAND , AbonnementStatus.PROEF );
        abonnement.setDienst( serviceToTryout );

        jerseyService.createAbonnement( abonnement );

        System.out.println( "serviceToTryout: " + serviceToTryout );
        req.getRequestDispatcher("/abonnementen").forward( req, resp);
    }
}