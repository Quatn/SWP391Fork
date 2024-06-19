
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
import jakarta.servlet.http.HttpSession;

@WebServlet(name="SubjectPricePackageController", urlPatterns={"/admin/subjectdetail/pricepackage"})
public class SubjectPricePackageController extends HttpServlet {
    private final static String PRICE_PACKAGE = "/admin/subjectdetail/pricepackage/PricePackage.jsp";
    private final static String EDIT_PRICE_PACKAGE = "/admin/subjectdetail/pricepackage/PricePackageEdit.jsp";
    private final static String ADD_PRICE_PACKAGE = "/admin/subjectdetail/pricepackage/PricePackageAdd.jsp";
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        DAOPackage dpkg = new DAOPackage();

        int id = Parsers.parseIntOrDefault(request.getParameter("subjectId"), -1);
        
        if (id == -1) {
            response.sendRedirect(request.getContextPath() + "/admin/subjectlist");
            return;
        }
        
        if (request.getParameter("action") != null) {
            if (handleAction(request, response)) {
                return;
            } else {
                response.sendRedirect("pricepackage?subjectId=" + id);
            }
        }
        
        request.setAttribute("packages", dpkg.getPricePackagesBySubjectId(id));
        request.getRequestDispatcher(PRICE_PACKAGE).forward(request, response);
    } 


    private boolean handleAction(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String action = request.getParameter("action");
        HttpSession session = request.getSession();

        switch (action) {
            case "add" -> {
                request.getRequestDispatcher(ADD_PRICE_PACKAGE).forward(request, response);
            }
            case "edit" -> {
                DAOPackage dpkg = new DAOPackage();
                int id = Parsers.parseIntOrDefault(request.getParameter("id"), -1);
                
                if (id == -1) {
                    session.setAttribute("notyfErrorMessage", "Invalid id");
                    return false;
                }
                
                
                Package pkg = dpkg.getPricePackageByPackageId(id);
                
                if (pkg == null) {
                    session.setAttribute("notyfErrorMessage", "Could not find that package");
                    return false;
                }

                request.setAttribute("pkg", pkg);
                request.getRequestDispatcher(EDIT_PRICE_PACKAGE).forward(request, response);
            }
        };
        
        return true;
    }

    private boolean switchPackage(int id, boolean isActive) {
        if (id == -1) return false;

        DAOPackage dpkg = new DAOPackage();

        Package pkg = dpkg.getPricePackageByPackageId(id);
        if (pkg == null) return false;
        pkg.setActive(isActive);

        return dpkg.modifyPackage(pkg);
    }
    
    private boolean addPackage(int subjectId, HttpServletRequest req) {
        if (subjectId == -1) return false;
        
        DAOPackage dpkg = new DAOPackage();
        
        int price = Parsers.parseIntOrDefault(req.getParameter("price"), 1000);
        int duration = Parsers.parseIntOrDefault(req.getParameter("duration"), 1);
        int percentage = Parsers.parseIntOrDefault(req.getParameter("percentage"), 0);
        String name = req.getParameter("name");
        
        Package pkg = new Package();
        pkg.setActive(false);
        pkg.setDuration(duration);
        pkg.setPackageName(name);
        pkg.setListPriceVND(price);
        pkg.applySale(Math.max(0, Math.min(100, percentage)));
        pkg.setDescription(req.getParameter("description"));
        
        if (!pkg.isValid()) return false;
        
        return dpkg.createPackage(subjectId, pkg);
    }
    
    private boolean editPackage(int packageId, HttpServletRequest req) {
        if (packageId == -1) return false;
        
        DAOPackage dpkg = new DAOPackage();
        Package pkg = dpkg.getPricePackageByPackageId(packageId);
        
        if (pkg == null) return false;
        
        int price = Parsers.parseIntOrDefault(req.getParameter("price"), 1000);
        int duration = Parsers.parseIntOrDefault(req.getParameter("duration"), 1);
        int percentage = Parsers.parseIntOrDefault(req.getParameter("percentage"), 0);
        String name = req.getParameter("name");
        
        pkg.setDuration(duration);
        pkg.setPackageName(name);
        pkg.setListPriceVND(price);
        pkg.applySale(Math.max(0, Math.min(100, percentage)));
        pkg.setDescription(req.getParameter("description"));
        
        if (!pkg.isValid()) return false;
        
        return dpkg.modifyPackage(pkg);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String action = request.getParameter("action");
        int id = Parsers.parseIntOrDefault(request.getParameter("id"), -1);
        int subjectId = Parsers.parseIntOrDefault(request.getParameter("subjectId"), -1);
        
        HttpSession session = request.getSession();
        
        switch (action) {
            case "activate" -> {
                if (switchPackage(id, true)) {
                    session.setAttribute("notyfSuccessMessage", "Activated package ID " + id);
                } else {
                    session.setAttribute("notyfErrorMessage", "An error occured while activating package ID " + id);
                }
            }
            case "deactivate" -> {
                if (switchPackage(id, false)) {
                    session.setAttribute("notyfSuccessMessage", "Deactivated package ID " + id);
                } else {
                    session.setAttribute("notyfErrorMessage", "An error occured while deactivating package ID " + id);
                }
            }
            case "add" -> {
                if (addPackage(subjectId, request)) {
                    session.setAttribute("notyfSuccessMessage", "Added new package for subject ID " + subjectId);
                } else {
                    session.setAttribute("notyfErrorMessage", "An error occured while adding new package for subject ID " + subjectId);
                }
            }
            case "edit" -> {
                if (editPackage(id, request)) {
                    session.setAttribute("notyfSuccessMessage", "Edited package ID " + id);
                } else {
                    session.setAttribute("notyfErrorMessage", "An error occured while editing package ID " + id);
                }
            }
        }

        response.sendRedirect("pricepackage?subjectId=" + subjectId);
    }

    @Override
    public String getServletInfo() {
        return "PricePackageController Servlet";
    }
}
