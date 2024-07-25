package app.controller;

import app.entity.BlogCategory;
import app.dal.DAOBlog;
import app.dal.DAOBlogCategory;
import app.dal.QueryResult;
import app.utils.Config;
import app.utils.Parsers;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.Optional;

@WebServlet(name="BlogListController", urlPatterns={"/blogs/list"})
public class BlogListController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        Config cfg = new Config(getServletContext());
        int pageSize = cfg.getIntOrDefault("pagination.size", 5);

        int page = Parsers.parseIntOrDefault(request.getParameter("page"), 1);
        int categoryId = Parsers.parseIntOrDefault(request.getParameter("categoryId"), -1);
        String query = request.getParameter("q");
        
        DAOBlog daoBlog = new DAOBlog();
        QueryResult queryResult = daoBlog.searchBlogListingsPaginated(
                query,
                categoryId,
                page,
                pageSize
        );

        request.setAttribute("pagesCount", queryResult.getTotalPages());
        request.setAttribute("blogs", queryResult.getResults());
        
        DAOBlogCategory daoBlogCategory = new DAOBlogCategory();
        List<BlogCategory> categories = daoBlogCategory.getAllCategories();
        request.setAttribute("categories", categories);

        QueryResult latestPosts = daoBlog.getRecentBlogs(4);
        request.setAttribute("recents", latestPosts.getResults());

        if (categoryId != -1) {
            Optional<BlogCategory> matchingCategory = categories
                       .stream().filter((BlogCategory cate) -> cate.getCategoryId() == categoryId).findFirst();

            if (matchingCategory.isPresent()) {
                request.setAttribute("blogCategory", matchingCategory.get());
            }
        }
        
        daoBlog.close();
        daoBlogCategory.close();

        request.getRequestDispatcher("BlogList.jsp").forward(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        request.getRequestDispatcher("BlogList.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "BlogListController Servlet";
    }
}
