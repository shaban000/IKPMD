package shaban.jama.eindopdracht;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class Toevoegen extends AppCompatActivity {
    TextView toevoegen;
    Button btn_toevoegen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toevoegen);
        toevoegen= (EditText) findViewById(R.id.subdoel_toevoegen);
        btn_toevoegen = (Button)findViewById(R.id.btn_toevoegen);

        btn_toevoegen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //save(toevoegen.getText().toString());
            }
        });
    }


}
