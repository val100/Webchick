
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package com.agrologic.app.web;

//~--- non-JDK imports --------------------------------------------------------

import com.agrologic.app.dao.ControllerDao;
import com.agrologic.app.dao.FlockDao;
import com.agrologic.app.dao.impl.ControllerDaoImpl;
import com.agrologic.app.dao.impl.FlockDaoImpl;
import com.agrologic.app.model.ControllerDto;
import com.agrologic.app.model.FlockDto;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author JanL
 */
public class SaveBeginEndForm extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /** Logger for this class and subclasses */
        final Logger logger = Logger.getLogger(SaveBeginEndForm.class);

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        try {
            Long   cellinkId    = Long.parseLong(request.getParameter("cellinkId"));
            Long   controllerId = Long.parseLong(request.getParameter("controllerId"));
            Long   flockId      = Long.parseLong(request.getParameter("flockId"));
            String maleQuant    = request.getParameter("maleQuant");
            String femaleQuant  = request.getParameter("femaleQuant");
            String maleCost     = request.getParameter("maleCost");
            String femaleCost   = request.getParameter("femaleCost");

            try {
                FlockDao flockDao = new FlockDaoImpl();
                FlockDto  flock    = flockDao.getById(flockId);
                flock.setQuantityMale(Integer.parseInt(maleQuant));
                flock.setQuantityFemale(Integer.parseInt(femaleQuant));
                flock.setCostChickMale(Float.parseFloat(maleCost));
                flock.setCostChickFemale(Float.parseFloat(femaleCost));
                flock.setTotalChicks(flock.calcTotalChicksCost());
                flockDao.update(flock);

                ControllerDao      controllerDao = new ControllerDaoImpl();
                List<ControllerDto> controllers   = controllerDao.getAllByCellinkId(cellinkId);

                for (ControllerDto controller : controllers) {
                    List<FlockDto> flocks = flockDao.getAllFlocksByController(controller.getId());

                    controller.setFlocks(flocks);
                }

                logger.info("retreive user and user cellinks and all controllers of each cellink");
                request.getSession().setAttribute("controllers", controllers);
                request.getRequestDispatcher("./rmctrl-flock-management.jsp?celinkId=" + cellinkId + "&controllerId="
                                             + controllerId + "&flockId=" + flockId).forward(request, response);
            } catch (SQLException ex) {

            }
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
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
     * Handles the HTTP <code>POST</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }    // </editor-fold>
}


//~ Formatted by Jindent --- http://www.jindent.com
