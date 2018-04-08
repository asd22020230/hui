package com.javaweb.servlet;

import com.javaweb.dao.CarDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ModifyServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        CarDao dao = new CarDao();
        String id = request.getParameter("mid");
        String newpwd = request.getParameter("newpwd");
        System.out.println(id+" "+newpwd);
        int count= dao.modifyuser(newpwd, id);
        if (count>0)
        out.println("修改成功，请妥善保管！");
        out.flush();
        out.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
