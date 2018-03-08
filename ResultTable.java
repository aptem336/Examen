package exam;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class ResultTable extends JTable {

    private static final Object[] HEADERS = {"№", "Время", " "};
    private static Object[][] data;

    ResultTable(Object[][] data, String[][] answers) {
        super(data, HEADERS);
        ResultTable.data = data;
        init();
    }

    private void init() {
        getColumnModel().getColumn(0).setMinWidth(40);
        getColumnModel().getColumn(0).setMaxWidth(40);
        getColumnModel().getColumn(0).setResizable(false);
        getColumnModel().getColumn(1).setMinWidth(130);
        getColumnModel().getColumn(1).setMaxWidth(130);
        getColumnModel().getColumn(1).setResizable(false);
        getColumnModel().getColumn(2).setMinWidth(40);
        getColumnModel().getColumn(2).setMaxWidth(40);
        getColumnModel().getColumn(2).setResizable(false);
        setAlignmentX(CENTER_ALIGNMENT);
        setRowHeight(30);
        setFont(new Font("", 0, 26));
        setEnabled(false);
    }

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
        Component component = super.prepareRenderer(renderer, row, col);
        component.setBackground(getBackground());
        if (data[row][2].equals("-")) {
            component.setBackground(new Color(Data.FAIL_COLOR, true));
        }
        return super.prepareRenderer(renderer, row, col);
    }

}
