package com.chasemaster.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AjaxServlet extends HttpServlet {
  private static final long serialVersionUID = 5858980155696778823L;

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    processRequest(request, response);
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    processRequest(request, response);
  }

  private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException,
      IOException {
    String move1 = request.getParameter("move1");
    String move2 = request.getParameter("move2");

    System.out.println("AjaxServlet: [" + move1 + ", " + move2 + "]");
    
    HttpSession session = request.getSession();

    System.out.println("AjaxServlet -> User ID from session: " + session.getAttribute("userId"));
    
    // setup the response
    response.setContentType("text/xml");
    response.setHeader("Cache-Control", "no-cache");

    response.getWriter().write(move1 + ", " + move2);
  }
}