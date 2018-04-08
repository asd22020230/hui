package com.javaweb.servlet;

import com.javaweb.dao.CarDao;
import com.javaweb.pojo.Car;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * restful 风格
 */
public class CarServlet extends HttpServlet {


    @Override
    //新增
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();

        String name=request.getParameter("name");
        Double price=Double.parseDouble(request.getParameter("price"));
        String screateDate=request.getParameter("createDate");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-mm-dd");
        Date createDate=new Date();

        try {
           createDate= sdf.parse(screateDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Car car=new Car();
        car.setName(name);
        car.setPrice(price);
        car.setCreateDate(createDate);


        int count =new CarDao().add(car);
        JSONObject jo=new JSONObject();
        jo.put("count",count);

        out.println(jo.toString());

        out.flush();
        out.close();
    }

    @Override
    //查询    查询所有|根据ID查询

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        String id=request.getParameter("id");

        JsonConfig jc=new JsonConfig();
        jc.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
        JSON json=null;
        CarDao carDao =new CarDao();
        if (id==null||"".equals(id)){
            //查询所有
            List<Car> cars=carDao.find();
            json= JSONSerializer.toJSON(cars,jc);
        }else {
            //根据ID查询
            Car car=carDao.find(Integer.parseInt(id));
            json=JSONSerializer.toJSON(car,jc);
        }
        out.println(json.toString());

        out.flush();
        out.close();
    }

    @Override
    //删除
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();

        Integer id=Integer.parseInt(request.getParameter("id"));

        int count =new CarDao().remove(id);
        JSONObject jo=new JSONObject();
        jo.put("count",count);
        out.println(jo.toString());

        out.flush();
        out.close();
    }

    @Override
    //修改
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        Integer id=Integer.parseInt(request.getParameter("id"));
        String name=request.getParameter("name");
        Double price=Double.parseDouble(request.getParameter("price"));
        String screateDate=request.getParameter("createDate");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-mm-dd");
        Date createDate=new Date();

        try {
            createDate= sdf.parse(screateDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Car car=new Car();
        car.setId(id);
        car.setName(name);
        car.setPrice(price);
        car.setCreateDate(createDate);


        int count =new CarDao().modify(car);
        JSONObject jo=new JSONObject();
        jo.put("count",count);

        out.println(jo.toString());
        out.flush();
        out.close();
    }
}
