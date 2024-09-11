package co.edu.unipiloto.chatapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class CitizenActivity extends AppCompatActivity {

    private ArrayList<String> messages;
    private ChatAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView rvChatHistory = findViewById(R.id.rvChatHistory);
        EditText etMessage = findViewById(R.id.etMessage);
        Button btnSend = findViewById(R.id.btnSend);

        // Inicializa la lista de mensajes desde el Intent o crea una nueva
        messages = getIntent().getStringArrayListExtra("messages");
        if (messages == null) {
            messages = new ArrayList<>();
        }
        adapter = new ChatAdapter(messages, false);
        rvChatHistory.setAdapter(adapter);
        rvChatHistory.setLayoutManager(new LinearLayoutManager(this));

        btnSend.setOnClickListener(v -> {
            String message = etMessage.getText().toString();
            if (!message.isEmpty()) {
                String formattedMessage = "Ciudadano: " + message;
                messages.add(formattedMessage);
                adapter.notifyItemInserted(messages.size() - 1);
                rvChatHistory.scrollToPosition(messages.size() - 1);
                etMessage.setText("");

                // Enviar el mensaje a PlannerActivity
                Intent intent = new Intent(CitizenActivity.this, PlannerActivity.class);
                intent.putStringArrayListExtra("messages", new ArrayList<>(messages)); // Envía los mensajes
                startActivity(intent);
            }
        });
    }
}