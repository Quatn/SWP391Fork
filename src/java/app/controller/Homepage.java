/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package app.controller;

import java.util.List;
import java.util.ArrayList;
import app.entity.Slide;
import app.dal.DAOSlide;
import app.entity.Subject;
import app.dal.DAOSubject;
import app.dal.DAOBlog;
import app.dal.DAOBlogCategory;
import app.entity.Blog;
import app.dal.DAOUser;
import app.entity.User;
import app.utils.Config;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 *
 * @author quatn
 */
public class Homepage extends HttpServlet {

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

        HttpSession session = request.getSession();
        ServletContext ctx = request.getServletContext();

        String service = request.getParameter("service");

        Config cfg = new Config(ctx);
        int pageSize = cfg.getIntOrDefault("homepage.pagination.size", 5);
        request.setAttribute("pageSize", pageSize);

        DAOBlog daoBlog = new DAOBlog();
        session.setAttribute("sidebarLatestPosts", daoBlog.getNewpostsForDisplay(8, 0));

        if (service != null) {

            if (service.equals("hotposts") || service.equals("newposts")) {
                int offSet = 0;
                //Get session offset
                if (request.getParameter("resetOffset") == null || !request.getParameter("resetOffset").equals("true")) try {
                    offSet = Integer.parseInt(session.getAttribute("hotpostOffset").toString());
                } catch (Exception e) {
                }

                int ammount = -1;
                try {
                    ammount = Integer.parseInt(request.getParameter("ammount"));
                } catch (Exception e) {
                }

                List<Blog> fetchPost = null;
                //idk why i wanted to add this either, but here we are
                if (ammount > 0) {
                    if (service.equals("hotposts")) {
                        fetchPost = daoBlog.getHotpostsForDisplay(ammount, offSet);
                    } else if (service.equals("newposts")) {
                        fetchPost = daoBlog.getNewpostsForDisplay(ammount, offSet);
                    }
                } else {
                    if (service.equals("hotposts")) {
                        fetchPost = daoBlog.getHotpostsForDisplay(pageSize, offSet);
                    } else if (service.equals("newposts")) {
                        fetchPost = daoBlog.getNewpostsForDisplay(pageSize, offSet);
                    }
                }

                if (fetchPost == null) {
                    //Even more redundancies
                    try (PrintWriter out = response.getWriter()) {
                        out.print("{}");
                    }
                    return;
                }

                //Get category map, this is currently not used for anything
                ConcurrentHashMap<Integer, String> catMap = null;
                try {
                    catMap = (ConcurrentHashMap<Integer, String>) session.getAttribute("blogCategoryMap");
                } catch (Exception e) {
                    DAOBlogCategory daoBlogC = new DAOBlogCategory();
                    catMap = daoBlogC.getMap();
                    session.setAttribute("blogCategoryMap", catMap);
                }

                //Get a map of user id -> user full name
                DAOUser daoUser = new DAOUser();
                final ConcurrentHashMap<Integer, String> fullNameMap = daoUser.idArrayToNameMap(
                        fetchPost.stream()
                                .map((post) -> post.getUserId())
                                .mapToInt(i -> i).toArray());

                //fullNameMap check null
                if (!(fullNameMap == null || fullNameMap.isEmpty())) {
                    //increase hotpost offset by the ammount of posts that was returned by DAO
                    session.setAttribute("hotpostOffset", offSet + fetchPost.size());
                    //return fecthed posts in the form of json
                    response.setContentType("application/json");
                    try (PrintWriter out = response.getWriter()) {
                        out.print(Arrays.stream(fetchPost.toArray())
                                .map(obj -> (Blog) obj)
                                .map(blog -> String.format("{\"BlogId\": %d, \"UserId\": %d, \"FullName\": \"%s\", \"BlogCategoryId\": %d, \"BlogTitle\": \"%s\", \"UpdatedTime\": \"%s\", \"CardContent\": \"%s\", \"Thumbnail\": \"%s\"}",
                                blog.getBlogId(),
                                blog.getUserId(),
                                fullNameMap.get(blog.getUserId()),
                                blog.getBlogCategoryId(),
                                blog.getBlogTitle(),
                                blog.getUpdatedTime(),
                                blog.getPostBrief(),
                                blog.getPostThumbnail()
                        ))
                                .collect(Collectors.toList())
                        );
                    }
                }
                return;
            }
        }

        DAOSlide daoSlide = new DAOSlide();
        List<Slide> sliders = daoSlide.getAllSlide();
        request.setAttribute("homeSliders", sliders);

        DAOSubject daoSubject = new DAOSubject();
        List<Subject> featuredSubjects = daoSubject.getFeaturedSubjects(10);
        if (!featuredSubjects.isEmpty()) {
            request.setAttribute("featuredSubjects", featuredSubjects);
        }

        request.getRequestDispatcher("home.jsp").forward(request, response);
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
