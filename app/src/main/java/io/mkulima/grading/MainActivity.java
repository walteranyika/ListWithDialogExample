package io.mkulima.grading;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listGrades;
    ArrayList<Grade> data;
    CustomAdapter mCustomAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listGrades=findViewById(R.id.listGrades);
        data=new ArrayList<>();
        mCustomAdapter=new CustomAdapter(this,data);
        listGrades.setAdapter(mCustomAdapter);
    }
    private void dialogAdd() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Add A New Grade Range");
        dialog.setMessage("Please provide a valid range");
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View register_layout = layoutInflater.inflate(R.layout.dialog_layout, null);
        final MaterialEditText inputUpper = register_layout.findViewById(R.id.inputUpper);
        final MaterialEditText inpputLower =  register_layout.findViewById(R.id.inputLower);
        final MaterialEditText inputGrade =  register_layout.findViewById(R.id.inputGrade);
        dialog.setView(register_layout);
        dialog.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String upper = inputUpper.getText().toString().trim();
                final String lower = inpputLower.getText().toString().trim();
                final String grade = inputGrade.getText().toString().trim();
                int upper_val = Integer.parseInt(upper);
                int lower_val = Integer.parseInt(lower);
                Grade newGrade= new Grade(upper_val,lower_val,grade);
                if (data.isEmpty() && newGrade.getUpper()>=newGrade.getLower()){
                    data.add(newGrade);
                }else{
                    int last = data.size()-1;
                    Grade lastGrade = data.get(last);
                    int diff=lastGrade.getLower()-newGrade.getUpper();
                    if (newGrade.getUpper()<=newGrade.getLower() || newGrade.getUpper()>=lastGrade.getLower() || diff>1){
                        Toast.makeText(MainActivity.this, "Invalid Grade Range", Toast.LENGTH_LONG).show();
                    }
                    else {
                        data.add(newGrade);
                    }
                }
                mCustomAdapter.notifyDataSetChanged();
            }
        });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.item_add){
            dialogAdd();
        }
        return super.onOptionsItemSelected(item);
    }
}
