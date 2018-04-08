package com.javaweb.servlet;

import com.google.gson.JsonObject;
import com.javaweb.dao.CarDao;
import com.javaweb.pojo.Car;
import com.javaweb.pojo.CarUser;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置响应的字符编码
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String action = request.getParameter("action");
        HttpSession session=request.getSession();
        CarDao carDao = new CarDao();
        CarUser car = carDao.login(username, password);
        JSONObject jo = new JSONObject();
        if (car != null) {
            JsonConfig jsonConfig = new JsonConfig();
//            jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
            jo = (JSONObject) JSONSerializer.toJSON(car, jsonConfig);
            session.setAttribute("jo",jo);
        } else {
            jo.put("code", 400);
            jo.put("message", "错误的用户名或密码");
        }
        out.println(jo.toString());
        out.flush();
        out.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
