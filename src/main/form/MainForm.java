package form;

import dao.DatabaseUtils;
import javafx.scene.control.ComboBox;
import model.Admin;
import model.People;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

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
    public MainForm(JFrame frame) {
        initInfPanel();
        infTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(e.getButton()==MouseEvent.BUTTON3)
                {
                    try {
                         if(DatabaseUtils.deletePeople(infTable.rowAtPoint(e.getPoint())))
                         {
                             JOptionPane.showMessageDialog(mainPanel, "本地提交成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                         }
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initInfPanel();
            }
        });
    }

    JPanel getMainPanel() {
        return mainPanel;
    }

    void initManPanel(Admin admin) {
        this.admin = admin;
        currentLogin.setText(admin.getAccount());
        level.setText(admin.getLevel());
    }

    class EditComboBox extends JComboBox<String> {
        EditComboBox(String[] str) {
            for (String s : str) {
                addItem(s);
            }
        }
    }

    private void initInfPanel() {
        refresh.setEnabled(false);
        new Thread() {
            @Override
            public void run() {
                super.run();
                DefaultTableModel defaultTableModel = new DefaultTableModel();
                String[] columnNames = {"编号", "姓名", "性别", "年龄", "职称", "政治面貌", "最高学历", "任职时间", "到达时间"};
                defaultTableModel.setColumnIdentifiers(columnNames);
                try {
                    DatabaseUtils.linkDataBase();
                    ArrayList<People> arrayList = DatabaseUtils.getAllPeople();
                    if (arrayList != null) {
                        defaultTableModel.setRowCount(arrayList.size());
                        for (int i = 0; i < arrayList.size(); i++) {
                            defaultTableModel.setValueAt(arrayList.get(i).getId(), i, 0);
                            defaultTableModel.setValueAt(arrayList.get(i).getName(), i, 1);
                            defaultTableModel.setValueAt(arrayList.get(i).getSex(), i, 2);
                            defaultTableModel.setValueAt(arrayList.get(i).getAge(), i, 3);
                            defaultTableModel.setValueAt(arrayList.get(i).getTitle(), i, 4);
                            defaultTableModel.setValueAt(arrayList.get(i).getPoliticalstatus(), i, 5);
                            defaultTableModel.setValueAt(arrayList.get(i).getHighestdegree(), i, 6);
                            defaultTableModel.setValueAt(arrayList.get(i).getTermtime().toString(), i, 7);
                            defaultTableModel.setValueAt(arrayList.get(i).getArrivetime().toString(), i, 8);
                        }
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                EditComboBox sexCom = new EditComboBox(new String[]{"男", "女"});
                EditComboBox titleCom = new EditComboBox(new String[]{"行政人员", "教师", "一般员工", "退休人员", "返聘人员", "临时工"});
                EditComboBox poCom = new EditComboBox(new String[]{"中共党员", "中共预备党员", "共青团员", "民革党员", "民盟盟员", "民建会员", "民进会员", "农工党党员", "致公党党员", "九三学社社员", "台盟盟员", "无党派人士", "群众"});
                EditComboBox hiCom = new EditComboBox(new String[]{"初中", "中专", "高中", "大专", "本科", "硕士", "博士"});
                infTable.setModel(defaultTableModel);
                infTable.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(sexCom));
                infTable.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(titleCom));
                infTable.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(poCom));
                infTable.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(hiCom));
                refresh.setEnabled(true);
            }
        }.start();

    }
}
