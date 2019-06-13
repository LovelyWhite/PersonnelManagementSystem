package form;

import dao.DatabaseUtils;
import javafx.scene.control.ComboBox;
import model.Admin;
import model.People;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.*;

public class MainForm {
    private JTabbedPane tab;
    private JPanel mainPanel;
    private JTable infTable;
    private JPanel infPanel;
    private JPanel manPanel;
    private JScrollPane scroll;
    private JButton refresh;
    private JButton submit;
    private JLabel currentLogin;
    private JLabel level;
    private JButton rollback;
    private JButton add;
    private JComboBox screen;
    private Admin admin;
    private ArrayList<Point> points;
    private HashMap<String,Object> history;
    private boolean initLock;
    private static DefaultTableCellRenderer clickColorC;
    private ArrayList<People> arrayList = null;
    Object[][] backUp;
    //初始构造函数、初始化数据
    public MainForm(JFrame frame) {
        points = new ArrayList<>();
        history = new HashMap<>();
        Color red = new Color(0xFF0020);
        Color gray = new Color(254,254,254,5);
        clickColorC= new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

                if(history.containsKey(row+""+-1)||history.containsKey(row+""+column))
                {

                    setBackground(red);
                    infTable.setSelectionBackground(red);
                }
                else
                {
                    setBackground(gray);
                    infTable.setSelectionBackground(gray);
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        };
        initInfPanel();
        infTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getButton() == MouseEvent.BUTTON3) {
                    int clickRow = infTable.rowAtPoint(e.getPoint());
                    if (!history.containsKey(clickRow+""+-1)) {
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                try {
                                    DatabaseUtils.linkDataBase();
                                    DatabaseUtils.deletePeople((long) infTable.getValueAt(clickRow, 0));
                                    Point p = new Point(clickRow,-1);
                                    points.add(p);
                                    history.put(p.x+""+p.y,null);
                                    rollback.setEnabled(true);
                                    submit.setEnabled(true);
                                    infTable.repaint();
                                } catch (SQLException | ClassNotFoundException e) {
                                    e.printStackTrace();
                                }

                            }
                        }.start();
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
        submit.setEnabled(false);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result =DatabaseUtils.commit();
                if (result== history.size()) {
                DefaultTableModel defaultTableModel =  (DefaultTableModel)infTable.getModel();
                    points.forEach((p)->
                    {

                        if(history.get(p.x+""+p.y)==null)
                        {
                            infTable.removeRowSelectionInterval(p.x,p.x);
                        }
                    });
                    JOptionPane.showMessageDialog(mainPanel, "修改/删除成功", "提示", JOptionPane.INFORMATION_MESSAGE);
                    history.clear();
                    points.clear();
                    infTable.repaint();
                    submit.setEnabled(false);
                    rollback.setEnabled(false);
                }
            }
        });
        rollback.setEnabled(false);
        rollback.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatabaseUtils.rollBack();
                Point p = points.get(points.size()-1);
                if(history.get(p.x+""+p.y)==null)
                {
                    infTable.removeRowSelectionInterval(p.x,p.x);
                }
                else
                {
                    infTable.setValueAt(history.get(p.x+""+p.y),p.x,p.y);
                }
                points.remove(points.size()-1);
                history.remove(p.x+""+p.y);
                infTable.repaint();
                if(points.size()==0)
                {
                    submit.setEnabled(false);
                    rollback.setEnabled(false);
                }
            }
        });
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startAdd();
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
        initLock = false;
        refresh.setEnabled(false);
        String screenSelect = (String)screen.getSelectedItem();
        new Thread() {
            @Override
            public void run() {
                super.run();
                DefaultTableModel defaultTableModel = new DefaultTableModel()
                {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        if(column==0||column==7||column==8)
                            return false;
                        else if (points.contains(new Point(row,-1)))
                        {
                            return false;
                        }
                        else
                            return true ;
                    }
                };
                defaultTableModel.addTableModelListener(new TableModelListener() {
                    @Override
                    public void tableChanged(TableModelEvent e) {
                        int row = e.getFirstRow();
                        int col = e.getColumn();
                        if(initLock&&row!=-1&&col!=-1&&e.getType()==TableModelEvent.UPDATE)
                        {
                            Object o = infTable.getValueAt(row,col);
                            if(!o.equals(backUp[row][col]))
                            {
                                Point p = new Point(row,col);
                                points.add(p);
                                history.put(p.x+""+p.y,backUp[row][col]);
                                People people = arrayList.get(row);
                                switch (col)
                                {
                                    case 1:people.setName((String)o);break;
                                    case 2:people.setSex((String)o);break;
                                    case 3:people.setAge(Long.parseLong((String)o));break;
                                    case 4:people.setTitle((String)o);break;
                                    case 5:;people.setPoliticalstatus((String)o);break;
                                    case 6:people.setHighestdegree((String)o);break;
                                }
                                DatabaseUtils.alertPeopleById(people);
                                submit.setEnabled(true);
                                rollback.setEnabled(true);
                            }
                        }
                    }
                });
                String[] columnNames = {"编号", "姓名", "性别", "年龄", "职称", "政治面貌", "最高学历", "任职时间", "到达时间"};
                defaultTableModel.setColumnIdentifiers(columnNames);
                try {
                    DatabaseUtils.linkDataBase();
                    arrayList = DatabaseUtils.getAllPeople(screenSelect);
                    DatabaseUtils.closeCon();
                    if (arrayList != null) {
                        backUp= new Object[arrayList.size()][9];
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
                        for(int x = 0;x<arrayList.size();x++)
                        {
                            for(int z = 0;z<9;z++)
                            {
                                backUp[x][z] = defaultTableModel.getValueAt(x,z);
                            }
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

                int columnCount = infTable.getColumnCount();
                for (int i = 0; i < columnCount; i++) {
                    infTable.getColumn(infTable.getColumnName(i)).setCellRenderer(clickColorC);
                }
                refresh.setEnabled(true);
                initLock = true;
            }
        }.start();

    }
    private void startAdd()
    {
        JFrame addFrame  = new JFrame("添加")
        {
            @Override
            public void setVisible(boolean b) {
                super.setVisible(b);
                if(!b)
                {
                    initInfPanel();
                }
            }
        };
        AddForm addForm = new AddForm(addFrame);
        addFrame.setContentPane(addForm.getAddPanel());
        addFrame.pack();
        addFrame.setVisible(true);
    }
}
