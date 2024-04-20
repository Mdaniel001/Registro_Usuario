    package co.edu.actividad4;

    import android.content.Context;
    import android.database.sqlite.SQLiteDatabase;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.ArrayAdapter;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.ListView;

    import androidx.activity.EdgeToEdge;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.core.graphics.Insets;
    import androidx.core.view.ViewCompat;
    import androidx.core.view.WindowInsetsCompat;

    import com.google.android.material.snackbar.Snackbar;

    import java.util.ArrayList;

    import co.edu.actividad4.entity.User;
    import co.edu.actividad4.model.UserDao;

    public class MainActivity extends AppCompatActivity {

        public  static final  String TAG = "VALIDATE ";
    //1.Declaramos Objetos
        private EditText etDocumento;
        private EditText etUsuario;
        private EditText etNombres;
        private EditText etApellidos;
        private EditText etContra;
        private ListView listUsers;
        private EditText etBuscarPorUsuario;
        private EditText etBuscarPorDocumento;
        private EditText  etActualizarUsuario;
        private EditText  etLimpiar;

        private int documento;

        private Context context;
        private Button btnSave;
        private Button  btnListUsers;
        private Button btnBuscarPorDocumento;
        private Button btnBuscarPorUsuario;
        private Button btnActualizarUsuario;
        private Button btnLimpiar;
        private Button btnCargarUsuario;
        private String usuario;

        private String nombres;

        private String apellidos;
        private String contra;
        SQLiteDatabase sqLiteDatabase;


         //Metodo OnCrete Principal
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_main);
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });

            context=this;
            initObject();
            //Enlace Botones con sus metodos
            btnSave.setOnClickListener(this::createUser);
            btnListUsers.setOnClickListener(this::listUserShow);
            btnLimpiar.setOnClickListener(this::limpiarUsuario);
            btnBuscarPorDocumento.setOnClickListener(this::buscarPorDocumento);
            btnBuscarPorUsuario.setOnClickListener(this::buscarPorUsuario);
            btnActualizarUsuario.setOnClickListener(this::actulizarUsuario);
            btnCargarUsuario.setOnClickListener(this::cargarUsuario);
        }


         //Metodo Cargar Usuario para elimnar
        private void cargarUsuario(View view) {
            int document = Integer.parseInt(etLimpiar.getText().toString());
            UserDao dao = new UserDao(context, view);
            User user = dao.getUserByDocument(document);

            if (user != null) {
                etUsuario.setText(user.getUser());
                etNombres.setText(user.getNames());
                etApellidos.setText(user.getLastNames());
                etContra.setText(user.getPassword());
            } else {
                Snackbar.make(view, "Usuario no encontrado", Snackbar.LENGTH_LONG).show();
            }
        }


        //Metodo Actulizar Usuario
        private void actulizarUsuario(View view) {
            User user = new User(
                    Integer.parseInt(etActualizarUsuario.getText().toString()),
                    etNombres.getText().toString(),
                    etApellidos.getText().toString(),
                    etUsuario.getText().toString(),
                    etContra.getText().toString()
            );
            UserDao dao = new UserDao(context, view);
            dao.updateUser(user);

            Snackbar.make(view, "Usuario actualizado", Snackbar.LENGTH_LONG).show();
        }


           //Metodo Buscar Usuario.
        private void buscarPorUsuario(View view) {
            String username = etBuscarPorUsuario.getText().toString();
            UserDao dao = new UserDao(context, view);
            User user = dao.getUserByUserName(username);

            if (user != null) {
                // Aquí puedes mostrar los datos del usuario encontrado, por ejemplo:
                etDocumento.setText(String.valueOf(user.getDocument()));
                etNombres.setText(user.getNames());
                etApellidos.setText(user.getLastNames());
                etContra.setText(user.getPassword());
            } else {
                Snackbar.make(view, "Usuario no encontrado", Snackbar.LENGTH_LONG).show();
            }
        }

        //metodo buscar Usuario por Documento.
        private void buscarPorDocumento(View view) {

            int document = Integer.parseInt(etBuscarPorDocumento.getText().toString());
            UserDao dao = new UserDao(context, view);
            User user = dao.getUserByDocument(document);

            if (user != null) {
                // Aquí puedes mostrar los datos del usuario encontrado, por ejemplo:
                etUsuario.setText(user.getUser());
                etNombres.setText(user.getNames());
                etApellidos.setText(user.getLastNames());
                etContra.setText(user.getPassword());
            } else {
                Snackbar.make(view, "Usuario no encontrado", Snackbar.LENGTH_LONG).show();
            }
        }


      //metodo Para eliminar Usuario
        private void limpiarUsuario(View view) {
            int document = Integer.parseInt(etLimpiar.getText().toString());
            UserDao dao = new UserDao(context, view);
            dao.deleteUser(document);

            Snackbar.make(view, "Usuario eliminado", Snackbar.LENGTH_LONG).show();
        }
        
        


        //Metodo Registar Usuario 
        private void createUser(View view) {
            getData();
            User user = new User(documento, nombres, apellidos,usuario,contra);//instanciamos un objeto de tipo user
            UserDao dao = new UserDao(context, view);
            dao.insertUser(user);
            //invoco el metodo de listrar nuevamente
            listUserShow(view);


        }
        
        
        //Listar Usuarios  Ingresados

        private void listUserShow(View view) {

            UserDao dao = new UserDao(context, findViewById(R.id.lvLista));
            ArrayList<User> users = dao.getUserList();
            //creacion de adaptador para mostrar los objetos
            ArrayAdapter<User> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,users);
            listUsers.setAdapter(adapter);
        }


        //capturamos datos
        private void getData(){
            documento= Integer.parseInt(etDocumento.getText().toString()); // casteo
            usuario=  etUsuario.getText().toString();
            nombres=  etNombres.getText().toString();
            apellidos=  etApellidos.getText().toString();
            contra=  etContra.getText().toString();

        }

        //Enlace Objetos.
        private void  initObject(){

            //Botones
            btnSave=findViewById(R.id.btnRegistar);
            btnListUsers=findViewById(R.id.btnListar);
            btnLimpiar=findViewById(R.id.btnLimpiar);
            btnBuscarPorDocumento =findViewById(R.id.btnBuscarPorDocumento);
            btnBuscarPorUsuario =findViewById(R.id.btnBuscarPorUsuario);
            btnActualizarUsuario=findViewById(R.id.btnActualizarUsuario);
            btnCargarUsuario=findViewById(R.id.btnCargarUsuario);
            //Plain Text
            etActualizarUsuario=findViewById(R.id.etActualizarUsuario);
            etLimpiar=findViewById(R.id.etLimpiar);
            etBuscarPorDocumento=findViewById(R.id.etBuscarPorDocumento);
            etBuscarPorUsuario=findViewById(R.id.etBuscarPorUsuario);
            etNombres= findViewById(R.id.etNombres);
            etApellidos= findViewById(R.id.etApellidos);
            etDocumento= findViewById(R.id.etDocumento);
            etUsuario= findViewById(R.id.etUsuario);
            etContra= findViewById(R.id.etContra);
            listUsers= findViewById(R.id.lvLista);
        }




    }