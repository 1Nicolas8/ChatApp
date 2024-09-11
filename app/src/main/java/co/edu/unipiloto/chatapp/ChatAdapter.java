package co.edu.unipiloto.chatapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<String> messages;
    private boolean isPlannerActivity;

    // Constructor
    public ChatAdapter(ArrayList<String> messages, boolean isPlannerActivity) {
        this.messages = messages;
        this.isPlannerActivity = isPlannerActivity;
    }

    // Tipos de vista: Emisor (planeador) y Receptor (ciudadano)
    private static final int VIEW_TYPE_SENDER = 1;
    private static final int VIEW_TYPE_RECEIVER = 2;

    @Override
    public int getItemViewType(int position) {
        String message = messages.get(position);
        if (isPlannerActivity) {
            // En la actividad del planeador, los mensajes del planeador van a la derecha y los del ciudadano a la izquierda
            return message.startsWith("Planeador:") ? VIEW_TYPE_SENDER : VIEW_TYPE_RECEIVER;
        } else {
            // En la actividad del ciudadano, los mensajes del ciudadano van a la derecha y los del planeador a la izquierda
            return message.startsWith("Ciudadano:") ? VIEW_TYPE_SENDER : VIEW_TYPE_RECEIVER;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_SENDER) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_sender, parent, false);
            return new SenderViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_receiver, parent, false);
            return new ReceiverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String message = messages.get(position);
        if (holder instanceof SenderViewHolder) {
            ((SenderViewHolder) holder).tvMessage.setText(message.replace(isPlannerActivity ? "Planeador: " : "Ciudadano: ", ""));
        } else {
            ((ReceiverViewHolder) holder).tvMessage.setText(message.replace(isPlannerActivity ? "Ciudadano: " : "Planeador: ", ""));
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    // ViewHolder para el emisor
    public static class SenderViewHolder extends RecyclerView.ViewHolder {
        public TextView tvMessage;
        public SenderViewHolder(View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.tvMessage);
        }
    }

    // ViewHolder para el receptor
    public static class ReceiverViewHolder extends RecyclerView.ViewHolder {
        public TextView tvMessage;
        public ReceiverViewHolder(View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.tvMessage);
        }
    }
}
