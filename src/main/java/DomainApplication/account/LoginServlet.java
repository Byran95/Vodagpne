package DomainApplication.account;

import DomainApplication.IAbonnee;
import DomainApplication.abonnee.AbonneeService;
import jersey.AbonneeJerseyService;

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
        urlPatterns = { "/login" }
)
public class LoginServlet extends HttpServlet {
    private AbonneeService abonneeService = new AbonneeService();
    AbonneeJerseyService jerseyService = new AbonneeJerseyService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ( null == req.getSession().getAttribute( "loggedInUser" ) ) {
            String userEmail = req.getParameter( "email" );
            if ( null == userEmail ) {
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
            } else {
                System.out.println("userEmail: " + userEmail);
                IAbonnee foundUser = jerseyService.getAbonneeByEmail(userEmail);
                System.out.println("foundUser: " + foundUser);
                if ( null == foundUser ) {
                    //Error
                    System.out.println( "User not found!" );
                    req.setAttribute( "errorMsg" , "No such user" );
                    req.getRequestDispatcher("/login.jsp").forward(req, resp);
                } else {
                    //Succes
                    req.getSession().setAttribute("loggedInUser", foundUser);
                    req.getRequestDispatcher("/abonnementen").forward(req, resp);
                }
            }
        } else {
            resp.getWriter().write( "You are already logged in!");
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost( req , resp );
    }
}