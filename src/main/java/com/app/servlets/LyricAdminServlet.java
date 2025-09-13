package com.app.servlets;
import com.app.dao.AdminDAO;
import com.app.dao.LyricAdminDAO;
import com.app.dao.ParticipantDAO;
import com.app.model.Admin;
import com.app.model.LyricAdmin;
import com.app.model.Participant;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class LyricAdminServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Fetch lyrics
        LyricAdminDAO lyricDAO = new LyricAdminDAO();
        List<LyricAdmin> lyrics = lyricDAO.getAllLyrics();
        System.out.println("Lyrics fetched: " + lyrics.size());
        request.setAttribute("lyrics", lyrics);

        // Fetch participants (users)
        ParticipantDAO participantDAO = new ParticipantDAO();
        List<Participant> participants = participantDAO.getAllParticipants();
        System.out.println("Participants fetched: " + participants.size());
        request.setAttribute("participantsList", participants);
        
        // Fetch Admins
        AdminDAO adminDAO = new AdminDAO();
        List<Admin> admins = adminDAO.getAllAdmins();
        System.out.println("Admins fetched: " + admins.size());
        request.setAttribute("adminList", admins);


        // Forward to JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("adminDashboard.jsp");
        dispatcher.forward(request, response);
    }
}
