/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package app.controller;

import app.model.DAOCustomer;
import app.entity.Customer;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.ResultSet;
import java.util.List;
import java.util.Vector;
import app.model.DAOCustomer;

/**
 *
 * @author Administrator
 */
public class CustomerController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        DAOCustomer dao = new DAOCustomer();
        try (PrintWriter out = response.getWriter()) {
            String service = request.getParameter("service");
            if (service == null) {
                service = ("listAll");
            }
            if (service.equals("listAll")) {
                Vector<Customer> vector = dao.getAll("select * from [User]");
                request.setAttribute("data", vector);
                request.setAttribute("titlePage", "UserManage");
                request.setAttribute("titleTable", "List of Registered User");
                //select view (jsp)
                RequestDispatcher dispath
                        = request.getRequestDispatcher("UserManage.jsp");
                //run
                dispath.forward(request, response);
            }

            if (service.equals("sortbyID")) {
                Vector<Customer> vector = dao.getAll("select * from [User] order by UserId");
                request.setAttribute("data", vector);
                request.setAttribute("titlePage", "UserManage");
                request.setAttribute("titleTable", "List of Registered User sorted by ID");
                //select view (jsp)
                RequestDispatcher dispath
                        = request.getRequestDispatcher("UserManage.jsp");
                //run
                dispath.forward(request, response);
            }
            if (service.equals("sortbyName")) {
                Vector<Customer> vector = dao.getAll("select * from [User] order by FullName");
                request.setAttribute("data", vector);
                request.setAttribute("titlePage", "UserManage");
                request.setAttribute("titleTable", "List of Registered User sorted by Name");
                //select view (jsp)
                RequestDispatcher dispath
                        = request.getRequestDispatcher("UserManage.jsp");
                //run
                dispath.forward(request, response);
            }
            if (service.equals("sortbyMail")) {
                Vector<Customer> vector = dao.getAll("select * from [User] order by Email");
                request.setAttribute("data", vector);
                request.setAttribute("titlePage", "UserManage");
                request.setAttribute("titleTable", "List of Registered User sorted by Email");
                //select view (jsp)
                RequestDispatcher dispath
                        = request.getRequestDispatcher("UserManage.jsp");
                //run
                dispath.forward(request, response);
            }
            if (service.equals("sortbyPhone")) {
                Vector<Customer> vector = dao.getAll("select * from [User] order by Mobile");
                request.setAttribute("data", vector);
                request.setAttribute("titlePage", "UserManage");
                request.setAttribute("titleTable", "List of Registered User sorted by MobilePhone");
                //select view (jsp)
                RequestDispatcher dispath
                        = request.getRequestDispatcher("UserManage.jsp");
                //run
                dispath.forward(request, response);
            }
            if (service.equals("searchByname")) {
                String input = request.getParameter("input");
                Vector<Customer> vector = dao.getAll("select * from [User] where FullName like '%" + input + "%'");
                request.setAttribute("data", vector);
                request.setAttribute("titlePage", "UserManage");
                request.setAttribute("titleTable", "List of Registered User");
                //select view (jsp)
                RequestDispatcher dispath
                        = request.getRequestDispatcher("UserManage.jsp");
                //run
                dispath.forward(request, response);
            }
            if (service.equals("searchByemail")) {
                String input2 = request.getParameter("input2");
                Vector<Customer> vector = dao.getAll("select * from [User] where Email like '%" + input2 + "%'");
                request.setAttribute("data", vector);
                request.setAttribute("titlePage", "UserManage");
                request.setAttribute("titleTable", "List of Registered User");
                //select view (jsp)
                RequestDispatcher dispath
                        = request.getRequestDispatcher("UserManage.jsp");
                //run
                dispath.forward(request, response);
            }
            if (service.equals("searchByphone")) {
                String input3 = request.getParameter("input3");
                Vector<Customer> vector = dao.getAll("select * from [User] where Mobile like '%" + input3 + "%'");
                request.setAttribute("data", vector);
                request.setAttribute("titlePage", "UserManage");
                request.setAttribute("titleTable", "List of Registered User");
                //select view (jsp)
                RequestDispatcher dispath
                        = request.getRequestDispatcher("UserManage.jsp");
                //run
                dispath.forward(request, response);
            }
            if (service.equals("sortbyGen")) {
                Vector<Customer> vector = dao.getAll("select * from [User] order by Gender");
                request.setAttribute("data", vector);
                request.setAttribute("titlePage", "UserManage");
                request.setAttribute("titleTable", "List of Registered User sorted by Gender");
                //select view (jsp)
                RequestDispatcher dispath
                        = request.getRequestDispatcher("UserManage.jsp");
                //run
                dispath.forward(request, response);
            }

            if (service.equals("addUser")) {
                //getdata
                int id = Integer.parseInt(request.getParameter("UserID"));
                String email = request.getParameter("Email");
                String password = request.getParameter("Password");
                String role = request.getParameter("Role");
                String fname = request.getParameter("FullName");
                String gender = request.getParameter("Gender");
                String mobile = request.getParameter("Mobile");
                boolean isactive
                        = ((Integer.parseInt(request.getParameter("isActive")) == 1) ? true : false);
                Customer cus = new Customer(id, email, password, role, fname, gender, mobile, isactive);
                dao.addCustomers(cus);
                response.sendRedirect("CustomerControllerURL?service=listAll");
            }
            if (service.equals("activefilter")) {
                Vector<Customer> vector = dao.getAll("select * from [User] where isActive = 1");
                request.setAttribute("data", vector);
                request.setAttribute("titlePage", "UserManage");
                request.setAttribute("titleTable", "List of Registered User that active");
                //select view (jsp)
                RequestDispatcher dispath
                        = request.getRequestDispatcher("UserManage.jsp");
                //run
                dispath.forward(request, response);
            }

            if (service.equals("notactivefilter")) {
                Vector<Customer> vector = dao.getAll("select * from [User] where isActive = 0");
                request.setAttribute("data", vector);
                request.setAttribute("titlePage", "UserManage");
                request.setAttribute("titleTable", "List of Registered User that not active");
                //select view (jsp)
                RequestDispatcher dispath
                        = request.getRequestDispatcher("UserManage.jsp");
                //run
                dispath.forward(request, response);
            }
            if (service.equals("malefilter")) {
                Vector<Customer> vector = dao.getAll("select * from [User] where Gender = 'Male'");
                request.setAttribute("data", vector);
                request.setAttribute("titlePage", "UserManage");
                request.setAttribute("titleTable", "List of Registered User that is Male");
                //select view (jsp)
                RequestDispatcher dispath
                        = request.getRequestDispatcher("UserManage.jsp");
                //run
                dispath.forward(request, response);
            }
            if (service.equals("femalefilter")) {
                Vector<Customer> vector = dao.getAll("select * from [User] where Gender = 'Female'");
                request.setAttribute("data", vector);
                request.setAttribute("titlePage", "UserManage");
                request.setAttribute("titleTable", "List of Registered User that is Female");
                //select view (jsp)
                RequestDispatcher dispath
                        = request.getRequestDispatcher("UserManage.jsp");
                //run
                dispath.forward(request, response);
            }
            if (service.equals("userfilter")) {
                Vector<Customer> vector = dao.getAll("select * from [User] where Role = 'User'");
                request.setAttribute("data", vector);
                request.setAttribute("titlePage", "UserManage");
                request.setAttribute("titleTable", "List of Registered User that is User");
                //select view (jsp)
                RequestDispatcher dispath
                        = request.getRequestDispatcher("UserManage.jsp");
                //run
                dispath.forward(request, response);
            }

            if (service.equals("update")) {
                //check submit
                String submit = request.getParameter("submit");
                if (submit == null) { // show data
                    //get id
                    int id = Integer.parseInt(request.getParameter("id"));
                    //get product with id
                    Vector<Customer> vector
                            = dao.getAll("select * from [User] where UserId=" + id);
                    request.setAttribute("vector", vector);
                    RequestDispatcher dispath
                            = request.getRequestDispatcher("EditUser.jsp");
                    //run
                    dispath.forward(request, response);
                } else { // update data
                    int id = Integer.parseInt(request.getParameter("UserID"));
                    String mail = request.getParameter("Email");
                    String pass = request.getParameter("Password");
                    String role = request.getParameter("Role");
                    String fname = request.getParameter("FullName");
                    String gen = request.getParameter("Gender");
                    String mb = request.getParameter("Mobile");
                    boolean isactive
                            = ((Integer.parseInt(request.getParameter("isActive")) == 1) ? true : false);
                    Customer cus = new Customer(id, mail, pass, role, fname, gen, role, isactive);
                    dao.updateUser(cus);
                    response.sendRedirect("CustomerControllerURL?service=listAll");
                }
            }
            if (service.equals("view")) {
                int id = Integer.parseInt(request.getParameter("id"));
                //get product with id
                Vector<Customer> vector
                        = dao.getAll("select * from [User] where UserId=" + id);
                request.setAttribute("data", vector);
                request.setAttribute("titlePage", "UserManage");
                request.setAttribute("titleTable", "List of Registered User");

                RequestDispatcher dispath
                        = request.getRequestDispatcher("viewUser.jsp");
                //run
                dispath.forward(request, response);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
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
     * Handles the HTTP <code>POST</code> method.
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
    }// </editor-fold>

}