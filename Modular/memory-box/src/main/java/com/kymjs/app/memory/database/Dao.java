package com.kymjs.app.memory.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kymjs.app.memory.model.BoxItem;
import com.kymjs.common.LogUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangTao on 10/13/16.
 */

public class Dao {

    private final DatabaseHelper dbHelper;

    public Dao() {
        super();
        dbHelper = new DatabaseHelper();
    }

    public DatabaseHelper getDbHelper() {
        return dbHelper;
    }

    /**
     * 保存一条数据到本地(若已存在则直接覆盖)
     *
     * @param data
     */
    public boolean save(BoxItem data) {
        if (data == null) {
            return false;
        }
        try {
            StringBuilder stringBuilder = new StringBuilder("replace into " + DatabaseHelper.TABLE_NAME);
            stringBuilder.append("(");

            Field[] fields = BoxItem.class.getDeclaredFields();
            for (Field field : fields) {
                stringBuilder.append(field.getName()).append(", ");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            stringBuilder.append(") values(");
            for (Field it : fields) {
                stringBuilder.append("?, ");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            stringBuilder.append(")");

            String sql = stringBuilder.toString();

            SQLiteDatabase sqlite = dbHelper.getWritableDatabase();

            String[] parameters = new String[fields.length];
            for (int i = 0; i < fields.length; i++) {
                parameters[i] = fields[i].get(data).toString();
            }
            sqlite.execSQL(sql, parameters);
            sqlite.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 查
     */
    public List<BoxItem> query(String where) {
        SQLiteDatabase sqlite = dbHelper.getReadableDatabase();
        Cursor cursor = sqlite.rawQuery("select * from " + DatabaseHelper.TABLE_NAME + where, null);
        List<BoxItem> datas = getListFromCursor(cursor);
        if (!cursor.isClosed()) {
            cursor.close();
        }
        sqlite.close();
        return datas;
    }

    private List<BoxItem> getListFromCursor(Cursor cursor) {
        ArrayList<BoxItem> datas = new ArrayList<>();
        Field[] fields = BoxItem.class.getDeclaredFields();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            BoxItem data = new BoxItem();
            for (Field field : fields) {
                if (field.getType() == int.class) {
                    int param = cursor.getInt(cursor.getColumnIndex(field.getName()));
                    try {
                        field.set(data, param);
                    } catch (IllegalAccessException e) {
                        LogUtils.e(e.getMessage());
                    }
                } else {
                    String param = cursor.getString(cursor.getColumnIndex(field.getName()));
                    try {
                        field.set(data, param);
                    } catch (IllegalAccessException e) {
                        LogUtils.e(e.getMessage());
                    }
                }
            }

            datas.add(data);
        }
        return datas;
    }

}
