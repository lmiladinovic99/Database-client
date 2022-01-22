package model;

import resource.data.Row;

import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Vector;

public class TableModel extends DefaultTableModel {

    private List<Row> rows;

    private void updateModel() {
        if(rows.size()==0) {
            Vector dataVector = new Vector(0);
            setDataVector(dataVector, null);
        }
        else {
            int columnCount = rows.get(0).getFields().keySet().size();

            Vector columnVector = DefaultTableModel.convertToVector(rows.get(0).getFields().keySet().toArray());
            Vector dataVector = new Vector(columnCount);

            for (int i=0; i<rows.size(); i++){
                dataVector.add(DefaultTableModel.convertToVector(rows.get(i).getFields().values().toArray()));
            }
            setDataVector(dataVector, columnVector);
        }


    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
        updateModel();
    }

    public List<Row> getRows() {
        return rows;
    }
}
