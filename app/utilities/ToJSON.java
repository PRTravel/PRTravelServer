package utilities;

import org.json.JSONObject;
import org.json.JSONArray;
import java.sql.ResultSet;

public class ToJSON{

    public static JSONObject convertToJSONObj(ResultSet resultSet) throws Exception{
        JSONObject obj = new JSONObject();
        while (resultSet.next()) {
            int total_rows = resultSet.getMetaData().getColumnCount();

            for (int i = 0; i < total_rows; i++) {
                String columnName = resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase();
                Object columnValue = resultSet.getObject(i + 1);

                if (columnValue == null){
                    columnValue = "null";
                }

                if (obj.has(columnName)){
                    columnName += "1";
                }
                obj.put(columnName, columnValue);
            }
        }
        return obj;
    }

    public static JSONArray convertToJSONArray(ResultSet resultSet) throws Exception {
        JSONArray jsonArray = new JSONArray();
        while (resultSet.next()) {
            int total_rows = resultSet.getMetaData().getColumnCount();
            JSONObject obj = new JSONObject();
            for (int i = 0; i < total_rows; i++) {
                String columnName = resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase();
                Object columnValue = resultSet.getObject(i + 1);

                if (columnValue == null){
                    columnValue = "null";
                }

                if (obj.has(columnName)){
                    columnName += "1";
                }
                obj.put(columnName, columnValue);
            }
            jsonArray.put(obj);
        }
        return jsonArray;
    }


}