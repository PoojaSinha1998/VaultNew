package info.vault.com.myvault.Activities;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import info.vault.com.myvault.ApplicationClass;
import info.vault.com.myvault.DatabaseHandler.DBHelper;
import info.vault.com.myvault.R;

public class DeleteDataActivity extends AppCompatActivity {
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(DeleteDataActivity.this);
        setContentView(R.layout.activity_delete_data);
        DeleteData();

    }
    public void DeleteData()
    {

        String title = ApplicationClass.getTitle();
        String username = ApplicationClass.getUserTitle();
        String password = ApplicationClass.getPassword();
        final String id = dbHelper.getDataToDelete(title, username, password);

        //Alert Box
        AlertDialog.Builder builder = new AlertDialog.Builder(DeleteDataActivity.this);
        builder.setCancelable(false);
        builder.setMessage("Data deleted successfully.");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application


                boolean a = dbHelper.deleteRow(id);


                if (a) {
                   // Toast.makeText(DeleteDataActivity.this, "Data Deleted Successfully", Toast.LENGTH_LONG).show();
                } else
                  //  Toast.makeText(DeleteDataActivity.this, "Data Not Deleted", Toast.LENGTH_LONG).show();
                dialog.cancel();
                finish();
            }
        });
//        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                //if user select "No", just cancel this dialog and continue with app
//                dialog.cancel();
//                finish();
//            }
//        });
        AlertDialog alert = builder.create();
        alert.show();



    }
}
