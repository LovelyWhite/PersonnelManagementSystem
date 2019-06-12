package form;

import model.Admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class MainForm {
    private JTabbedPane tab;
    private JPanel mainPanel;
    private JTable infTable;
    private JPanel infPanel;
    private JPanel statPanel;
    private JPanel manPanel;
    private JScrollPane scroll;
    private JButton refresh;
    private JButton submit;
    private JLabel currentLogin;
    private JLabel level;
    private Admin admin;

    //初始构造函数、初始化数据
    MainForm(Admin admin)
    {
        this.admin = admin;
        currentLogin.setText(admin.getAccount());
        level.setText(admin.getLevel());
        Object[] columnNames = {"姓名", "语文", "数学", "英语", "总分"};

        // 表格所有行数据
        Object[][] rowData = {
                {"张三", 80, 80, 80, 240},
                {"John", 70, 80, 90, 240},
                {"Sue", 70, 70, 70, 210},
                {"Jane", 80, 70, 60, 210},
                {"张三", 80, 80, 80, 240},
                {"John", 70, 80, 90, 240},
                {"Sue", 70, 70, 70, 210},
                {"Jane", 80, 70, 60, 210},
                {"张三", 80, 80, 80, 240},
                {"John", 70, 80, 90, 240},
                {"Sue", 70, 70, 70, 210},
                {"Jane", 80, 70, 60, 210},
                {"张三", 80, 80, 80, 240},
                {"John", 70, 80, 90, 240},
                {"Sue", 70, 70, 70, 210},
                {"Jane", 80, 70, 60, 210},
                {"Joe", 80, 70, 60, 210}
        };
        DefaultTableModel defaultTableModel = new DefaultTableModel(rowData,columnNames);
        infTable.setModel(defaultTableModel);
    }
    JPanel getMainPanel() {
        return mainPanel;
    }
}
