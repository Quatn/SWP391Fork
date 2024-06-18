
package app.controller;

import app.dal.DAOPackage;
import app.utils.Parsers;
import java.io.IOException;
import java.io.PrintWriter;
import app.entity.Package;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name="SubjectPricePackageController", urlPatterns={"/admin/subjectdetail/pricepackage"})
public class SubjectPricePackageController extends HttpServlet {
    private final static String PRICE_PACKAGE = "/admin/subjectdetail/pricepackage/PricePackage.jsp";
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        DAOPackage dpkg = new DAOPackage();
        
        int id = Parsers.parseIntOrDefault(request.getParameter("subjectId"), -1);
        
        if (id == -1) {
            response.sendRedirect(request.getContextPath() + "/admin/subjectlist");
            return;
        }
        
        request.setAttribute("packages", dpkg.getPricePackagesBySubjectId(id));
        request.getRequestDispatcher(PRICE_PACKAGE).forward(request, response);
    } 

    private void switchPackage(int id, boolean isActive) {
        if (id == -1) return;

        DAOPackage dpkg = new DAOPackage();

        Package pkg = dpkg.getPricePackageByPackageId(id);
        if (pkg == null) return;
        pkg.setActive(isActive);

        dpkg.modifyPackage(pkg);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String action = request.getParameter("action");
        int id = Parsers.parseIntOrDefault(request.getParameter("id"), -1);
        int subjectId = Parsers.parseIntOrDefault(request.getParameter("subjectId"), -1);

        switch (action) {
            case "activate" -> switchPackage(id, true);
            case "deactivate" -> switchPackage(id, false);
        }

        response.sendRedirect("pricepackage?subjectId=" + subjectId);
    }

    @Override
    public String getServletInfo() {
        return "PricePackageController Servlet";
    }
}
