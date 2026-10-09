package com.aquarium.controller;

import com.aquarium.dao.WaterRecordDAO;
import com.aquarium.model.WaterRecord;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/tracker")
public class WaterTrackerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("dashboard".equals(action)) {
            String tankName = request.getParameter("tankName");
            WaterRecordDAO dao = new WaterRecordDAO();
            request.setAttribute("tankName", tankName);
            request.setAttribute("records", dao.getRecordsByTank(tankName));
            request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
            return;
        } else if ("export".equals(action)) {
            String tankName = request.getParameter("tankName");
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment; filename=\"water_records.csv\"");
            try (PrintWriter out = response.getWriter()) {
                out.print(WaterRecordDAO.exportToCSV(tankName));
            }
            return;
        }
        
        request.setAttribute("tanks", WaterRecordDAO.getTanks());
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("addRecord".equals(action)) {
            String tankName = request.getParameter("tankName");
            String date = request.getParameter("dateLogged");
            
            try {
                double ph = Double.parseDouble(request.getParameter("ph"));
                double ammonia = Double.parseDouble(request.getParameter("ammonia"));
                double nitrite = Double.parseDouble(request.getParameter("nitrite"));
                double nitrate = Double.parseDouble(request.getParameter("nitrate"));
                double temperature = Double.parseDouble(request.getParameter("temperature"));

                WaterRecord record = new WaterRecord(tankName, date, ph, ammonia, nitrite, nitrate, temperature);
                WaterRecordDAO dao = new WaterRecordDAO();
                dao.addRecord(record);

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            response.sendRedirect("tracker?action=dashboard&tankName=" + java.net.URLEncoder.encode(tankName, "UTF-8"));
        } else {
            response.sendRedirect("index.jsp");
        }
    }
}