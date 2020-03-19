package com.e.uploadfile;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Upload> mUploads;
    private OnItemClickListener mListener;

    public ImageAdapter(Context context,List<Upload> uploads){
        mContext = context;
        mUploads = uploads;
        //notifyDataSetChanged(); // 데이터 업데이트
    }


    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.image_item,parent,false);

        return new ImageViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Upload uploadCurrent = mUploads.get(position);
        holder.textViewName.setText(uploadCurrent.getmName());
      /*  Picasso.with(mContext)
                .load(uploadCurrent.getmImageUrl())
                .fit()
                .centerCrop()
                //.centerCrop()
                .into(holder.imageView_loadImage);*/

        Glide.with(mContext).load(uploadCurrent.getmImageUrl()).placeholder(R.mipmap.ic_launcher).
                circleCrop().into(holder.imageView_loadImage); //override(600,200).into(holder.imageView_loadImage);
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        public TextView textViewName;
        public ImageView imageView_loadImage;

        @Override
        public void onClick(View v) {
            if(mListener != null){
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    mListener.onItemClick(position);

                }

            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Action");
            MenuItem doWhatever = menu.add(Menu.NONE,1,1,"Do Whatever");
            MenuItem delete = menu.add(Menu.NONE,2,2,"Delete");

            doWhatever.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);



        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if(mListener != null){
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    switch (item.getItemId()){
                        case 1:
                            mListener.onWhateverClick(position);
                            return true;
                        case 2:
                            mListener.onDeleteClick(position);
                            return true;

                    }


                }

            }
            return false;
        }

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_item_name);
            imageView_loadImage = itemView.findViewById(R.id.image_view_item_upload);
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);

        }
    }
    public interface OnItemClickListener{
        void onItemClick(int position);

        void onWhateverClick(int position);

        void onDeleteClick(int position);

    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;

    }
}
