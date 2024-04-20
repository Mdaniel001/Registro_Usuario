package co.edu.actividad4.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import co.edu.actividad4.entity.User;

public class UserDao {

    private  ManagerDataBase managerDataBase;
    Context context;
    View view;
    private User user;

    public UserDao(Context context, View view) {
        this.context = context;
        this.view = view;
        managerDataBase= new ManagerDataBase(this.context);

    }

    //Metodo insertar Usuario a Base de Datos
    public void insertUser(User user){
        try{//para crear excepciones
            SQLiteDatabase db = managerDataBase.getWritableDatabase();
            if (db !=null ){
                ContentValues values = new ContentValues();//instanciamos el objeto
                values.put("use_document", user.getDocument());
                values.put("use_user",user.getUser());
                values.put("use_names",user.getNames());
                values.put("use_lastNames",user.getLastNames());
                values.put("use_password",user.getPassword());
                values.put("use_status","1");
                //para  ejecutar
                long cod = db.insert("users", null, values);
                Snackbar.make(this.view, "Se ha Registrado el Usurio"+ cod,Snackbar.LENGTH_LONG).show();

            }else {

                Snackbar.make(this.view, "NO Se ha Registrado el Usurio", Snackbar.LENGTH_LONG).show();// retiro el metodo COD

            }
        }catch(SQLException e){
            Log.i("DB",""+e);
        }
    }
    //Metodo de consulta pára todos los usuarios en una lista
    public ArrayList<User> getUserList(){
        ArrayList<User> listUsers = new ArrayList<>();
        try {
            SQLiteDatabase db =managerDataBase.getReadableDatabase();
            String query ="select *  from users where use_status= 1;";
            //Ejecutamos el query a un cursor, para recorrer todos los tados
            Cursor cursor  = db.rawQuery(query, null);
            if(cursor.moveToFirst()){
                do{
                    User user1= new User();
                    user1.setDocument(cursor.getInt(0));
                    user1.setUser(cursor.getString(1));
                    user1.setNames(cursor.getString(2));
                    user1.setLastNames(cursor.getString(3));
                    user1.setPassword(cursor.getString(4));
                    listUsers.add(user1);
                }while (cursor.moveToNext());            }
            cursor.close();
            db.close();
        }catch (SQLException e) {
            Log.i("DB", "" +e);
        }
        return listUsers;
    }


    //Metodo ara consulta usuario por documento
    public User getUserByDocument(int document) {
        User user = null;
        try {
            SQLiteDatabase db = managerDataBase.getReadableDatabase();
            String query = "SELECT * FROM users WHERE use_document = ? AND use_status = 1";
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(document)});

            if (cursor.moveToFirst()) {
                user = new User();
                user.setDocument(cursor.getInt(0));
                user.setUser(cursor.getString(1));
                user.setNames(cursor.getString(2));
                user.setLastNames(cursor.getString(3));
                user.setPassword(cursor.getString(4));
            }

            cursor.close();
            db.close();
        } catch (SQLException e) {
            Log.i("DB", "" + e);
        }
        return user;
    }




    //Metodo patra buscar por usuario
    public User getUserByUserName(String username) {
        User user = null;
        try {
            SQLiteDatabase db = managerDataBase.getReadableDatabase();
            String query = "SELECT * FROM users WHERE use_user = ? AND use_status = 1";
            Cursor cursor = db.rawQuery(query, new String[]{username});

            if (cursor.moveToFirst()) {
                user = new User();
                user.setDocument(cursor.getInt(0));
                user.setUser(cursor.getString(1));
                user.setNames(cursor.getString(2));
                user.setLastNames(cursor.getString(3));
                user.setPassword(cursor.getString(4));
            }
            cursor.close();
            db.close();
        } catch (SQLException e) {
            Log.i("DB", "" + e);
        }
        return user;
    }


    //metodo para actulizar usurio agregado en lista
    public void updateUser(User user) {
        try {
            SQLiteDatabase db = managerDataBase.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("use_user", user.getUser());
            values.put("use_names", user.getNames());
            values.put("use_lastNames", user.getLastNames());
            values.put("use_password", user.getPassword());

            db.update("users", values, "use_document = ?",
                    new String[]{String.valueOf(user.getDocument())});

            db.close();
        } catch (SQLException e) {
            Log.i("DB", "" + e);
        }
    }

    // Metodo para eliminar (desactivar) Usuario.
    public void deleteUser(int document) {
        try {
            SQLiteDatabase db = managerDataBase.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("use_status", "0");

            db.update("users", values, "use_document = ?", new String[]{String.valueOf(document)});

            db.close();
        } catch (SQLException e) {
            Log.i("DB", "" + e);
        }
    }























}
