package DomainApplication.abonnement.servlets;

import DomainApplication.IAbonnee;
import DomainApplication.IAbonnement;
import DomainApplication.abonnee.AbonneeService;
import DomainApplication.abonnement.AbonnementService;
import jersey.AbonneeJerseyService;
import jersey.AbonnementJerseyService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Haalt de users op waarmee de dienst gedeeld kan worden.
 * Created by Anders Egberts on 20/10/2016.
 */
@WebServlet(
        value = "/startSharingService"
)
public class DienstDelenStartServlet extends HttpServlet {
    AbonnementService service = new AbonnementService();
    AbonnementJerseyService jerseyService = new AbonnementJerseyService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IAbonnee loggedInUser = (IAbonnee) req.getSession().getAttribute( "loggedInUser" );
        if ( null == loggedInUser ) {
            req.getRequestDispatcher( "/login.jsp" ).forward( req , resp );
            return;
        }

        int abonneeId = loggedInUser.getAbonneeId();
        String bedrijf = req.getParameter("bedrijf");
        String naam = req.getParameter("naam");

        if ( null == bedrijf || null == naam ) {
            return;
        }

        IAbonnement abonnementToShare = jerseyService.getByOwnerCompanyandName( abonneeId , bedrijf , naam );
        List<IAbonnee> ignoreList = abonnementToShare.getGedeeldMet();
        ignoreList.add( loggedInUser );

        req.setAttribute( "abonnement" , abonnementToShare );
        req.setAttribute( "users" , new AbonneeService().getAllWithFilter( ignoreList ) );
        req.getRequestDispatcher( "/dienstDelen.jsp" ).forward( req , resp );
    }
}