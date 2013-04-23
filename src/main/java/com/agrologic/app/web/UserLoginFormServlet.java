
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package com.agrologic.app.web;

//~--- non-JDK imports --------------------------------------------------------

import com.agrologic.app.dao.UserDao;
import com.agrologic.app.dao.impl.UserDaoImpl;
import com.agrologic.app.model.UserDto;
import com.agrologic.app.utils.Base64;

import org.apache.log4j.Logger;

//~--- JDK imports ------------------------------------------------------------

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author JanL
 */
public class UserLoginFormServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ServletContext    ctx;

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /**
         * Logger for this class and subclasses
         */
        final Logger logger = Logger.getLogger(UserLoginFormServlet.class);
        String       name   = request.getParameter("name");
        String       pass   = request.getParameter("password");
        String       reme   = request.getParameter("remember");

        ctx = getServletContext();
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out     = response.getWriter();
        UserDao    userDao = new UserDaoImpl();

        try {
            String  encpsswd = Base64.encode(pass);
            UserDto user     = userDao.validate(name, encpsswd);

            user.setPassword(pass);
            logger.info("login user : " + name);

            if (user.getValidate() == false) {
                logger.error("username " + name + " and password: " + pass + " not found");
                request.setAttribute("errormessage", "incorrect user name and/or password");
                request.getRequestDispatcher("./login.jsp").forward(request, response);
            } else {
                if ((reme != null) && "ON".equals(reme.toUpperCase())) {
                    setCookies(true, name, pass, response);
                } else {
                    setCookies(false, name, pass, response);
                }

                logger.info(user + " , successfully logged in system .");

//              ctx.setAttribute("user", user);
//              ctx.setAttribute("access", "admin");
                logger.warn("User tried to bypass login. remote IP = " + request.getRemoteHost());
                request.getSession().setAttribute("user", user);
                request.getSession().setAttribute("access", "admin");
                logger.info(request.getServerName());

                switch (user.getRole()) {
                case UserRole.REGULAR :
                    response.sendRedirect(getURLWithContextPath(request) + "/my-farms.html?userId=" + user.getId());

                    break;

                case UserRole.ADVANCED :
                    response.sendRedirect(getURLWithContextPath(request) + "/overview.html");

                    break;

                case UserRole.ADMINISTRATOR :
                    response.sendRedirect(getURLWithContextPath(request) + "/overview.html");

                    break;

                default :
                    response.sendRedirect(getURLWithContextPath(request) + "/overview.html");

                    break;
                }
            }
        } catch (SQLException ex) {
            logger.error("Database error while loggin user!", ex);
            request.setAttribute("errormessage", "incorrect user name and/or password");
            request.getRequestDispatcher(getURLWithContextPath(request) + "/login.jsp").forward(request, response);
        } finally {
            out.close();
        }
    }

    public static String getURLWithContextPath(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
               + request.getContextPath();
    }

    public void setCookies(boolean remember, String user, String pass, HttpServletResponse response) {
        if (remember == true) {
            Cookie rememberCookie = new Cookie("remember", "true");

            rememberCookie.setMaxAge(60 * 60 * 24 * 365);
            response.addCookie(rememberCookie);

            Cookie userCookie = new Cookie("name", user);

            userCookie.setMaxAge(60 * 60 * 24 * 365);
            response.addCookie(userCookie);

            Cookie passCookie = new Cookie("password", pass);

            passCookie.setMaxAge(60 * 60 * 24 * 365);
            response.addCookie(passCookie);
        } else {
            Cookie cookie = new Cookie("remember", "false");

            cookie.setMaxAge(60 * 60 * 24 * 365);
            response.addCookie(cookie);
        }
    }

//  <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }    // </editor-fold>
}


//~ Formatted by Jindent --- http://www.jindent.com
