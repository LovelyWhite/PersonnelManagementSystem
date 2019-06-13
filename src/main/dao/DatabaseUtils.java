package dao;

import model.Admin;
import model.People;

import java.sql.*;
import java.util.ArrayList;
import java.util.Stack;

public class DatabaseUtils {
    private static Connection con  = null;
    static  Statement stat = null;
    static ResultSet rs = null;
    static String sqlFac;
    static Stack<String> sqls = new Stack<>();
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
        con.setAutoCommit(false);
    }
    public static People findPeopleById(long id)
    {
        People people = new People();
        return people;
    }
    public static ArrayList<People> getAllPeople() throws SQLException {
        ArrayList<People> peopleArrayList = new ArrayList<>();
        stat = con.createStatement();
        String sql = "select * from people;";
        rs = stat.executeQuery(sql);
        while (rs.next()) {
            People people = new People();
            people.setId(rs.getLong("id"));
            people.setName(rs.getString("name"));
            people.setSex(rs.getString("sex"));
            people.setAge(rs.getLong("age"));
            people.setTitle(rs.getString("title"));
            people.setPoliticalstatus(rs.getString("politicalstatus"));
            people.setHighestdegree(rs.getString("highestdegree"));
            people.setTermtime(rs.getTimestamp("termTime"));
            people.setArrivetime(rs.getTimestamp("arrivetime"));
            peopleArrayList.add(people);
        }
        if (peopleArrayList.size() != 0)
            return peopleArrayList;
        else
            return null;
    }
    public static Admin findAdminByAccountAndPassword(String account, String password) throws SQLException {
        stat = con.createStatement();
        String sql = "select * from admin where account='"+account+"'and password='"+password+"';";
        rs = stat.executeQuery(sql);
        if (rs.next())
        {
            Admin admin  = new Admin();
            admin.setId(rs.getLong("id"));
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
    public static void insertPeople(People people)
    {
        String sql = "insert into people values (null,'"+people.getName()+
                "','"+people.getSex()+
                "',"+people.getAge()+
                ",'"+people.getTitle()+
                "','"+people.getPoliticalstatus()+
                "','"+people.getHighestdegree()+
                "','"+people.getTermtime()+
                "','" +people.getArrivetime()+"');";
        sqls.push(sql);
    }
    public static boolean insertAdmin(Admin admin)
    {
        return true;
    }
    public static void deletePeople(long peopleId){
        String sql = "delete from people where id="+peopleId+";";
        sqls.push(sql);
    }
    public static int commit() {

        try {
            if (con == null || con.isClosed()) {
                linkDataBase();
            }
            stat = con.createStatement();
            int result = 0;
            int size = sqls.size();
            while (sqls.size() != 0) {
                stat.executeUpdate(sqls.pop());
            }
            if (con != null)
                con.commit();
            return size;
        } catch (SQLException e) {
            rollBack();
            e.printStackTrace();
            return 0;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                clearSqls();
                closeCon();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
    public static void closeRsAndStat() throws SQLException {
        if(rs!=null)
        {
            rs.close();
        }
        if(stat!=null)
        {
            stat.close();
        }
    }
    public static void closeCon() throws SQLException {
         closeRsAndStat();
         if(con!=null)
         {
             con.close();
         }
    }
    public static void rollBack(){
       if(sqls.size()>0)
       {
           sqls.pop();
       }
   }
   public static void clearSqls()
   {
       sqls.clear();
   }
}
