package dao;

import model.Admin;
import model.People;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseUtils {
    private static Connection con  = null;
    static  Statement stat = null;
   public static void linkDataBase() throws SQLException, ClassNotFoundException {
        //驱动程序名
        String driver = "com.mysql.cj.jdbc.Driver";
        //URL指向要访问的数据库名mydata
        String url = "jdbc:mysql://localhost:3306/pmsystem?serverTimezone=UTC";
        //MySQL配置时的用户名
        String user = "root";
        //MySQL配置时的密码
        String password = "Mishiweilai123";
        //遍历查询结果集
        //加载驱动程序
        Class.forName(driver);
        //1.getConnection()方法，连接MySQL数据库！！
        con = DriverManager.getConnection(url, user, password);
    }
    public static People findPeopleById(long id)
    {
        People people = new People();
        return people;
    }
    public static ArrayList<People> getAllPeople()
    {
        ArrayList<People> peopleArrayList = new ArrayList<>();
        return peopleArrayList;
    }
    public static Admin findAdminByAccountAndPassword(String account, String password) throws SQLException {

        stat = con.createStatement();
        String sql = "select * from admin where account='"+account+"'and password='"+password+"';";
        ResultSet rs = stat.executeQuery(sql);
        if (rs.next())
        {
            Admin admin  = new Admin();
            admin.setId( Long.parseLong(rs.getString("id")));
            admin.setAccount(rs.getString("account"));
            admin.setPassword(rs.getString("password"));
            admin.setLevel(rs.getString("level"));
            return admin;
        }
        return  null;
    }
    public static boolean alertPeopleById(People people)
    {
        return true;
    }
    public static boolean alertAdminById(Admin admin)
    {
        return true;
    }
    public static boolean insertPeople(People people)
    {
        return true;
    }
    public static boolean insertAdmin(Admin admin)
    {
        return true;
    }
}
