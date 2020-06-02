package org.gmfbilu.neteasycloudcourse.ui.补充课程.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.gmfbilu.neteasycloudcourse.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MessageAdapter extends RecyclerView.Adapter<MessageViewHolder> {

    private interface ViewType {
        int TEXT = 10;
        int IMAGE = 20;
    }

    private List<Message> messageList;

    public MessageAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ViewType.TEXT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_message_text, parent, false);
            return new TextViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_message_image, parent, false);
            return new ImageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        if (getItemViewType(position) == ViewType.TEXT) {
            TextViewHolder textViewHolder = (TextViewHolder) holder;
            TextMessage textMessage = (TextMessage) messageList.get(position);
            textViewHolder.time.setText(textMessage.time);
            textViewHolder.text.setText(textMessage.text);
        } else {
            ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
            ImageMessage imageMessage = (ImageMessage) messageList.get(position);
            imageViewHolder.time.setText(imageMessage.time);
            imageViewHolder.image.setImageResource(imageMessage.imageRes);
        }
        if (mListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(holder, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messageList.get(position);
        if (message instanceof TextMessage) {
            return ViewType.TEXT;
        } else {
            return ViewType.IMAGE;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(RecyclerView.ViewHolder viewHolder, int position);
    }

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }
}
