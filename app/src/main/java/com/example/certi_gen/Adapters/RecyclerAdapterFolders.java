package com.example.certi_gen.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.certi_gen.Activities.FoldersDetailActivity;
import com.example.certi_gen.Activities.MainActivity;
import com.example.certi_gen.Classes.Folder;
import com.example.certi_gen.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapterFolders extends RecyclerView.Adapter<RecyclerAdapterFolders.ViewHolderFolders> {

    public ArrayList<Folder> folderList;
    Context context;
    MainActivity mainActivity;

    public class ViewHolderFolders extends RecyclerView.ViewHolder {
        public TextView folder_name;
        public TextView folder_description;
        public TextView folder_size;
        public TextView folder_date;
        public TextView folder_no_of_file;
        public ImageView folder_share_button;
        public ImageView folder_delete;
        public CheckBox checkBox;
        public LinearLayout rightlinearlayout;



        public ViewHolderFolders(@NonNull View itemView) {
            super(itemView);

            folder_name = itemView.findViewById(R.id.folder_name);
            folder_description = itemView.findViewById(R.id.folder_description);
            folder_size = itemView.findViewById(R.id.folder_size);
            folder_date = itemView.findViewById(R.id.folder_date);
            folder_no_of_file = itemView.findViewById(R.id.folder_no_of_file);
            folder_share_button = itemView.findViewById(R.id.folder_share_button);
            folder_delete = itemView.findViewById(R.id.folder_delete);
            checkBox = itemView.findViewById(R.id.checkbox_selected);
            rightlinearlayout = itemView.findViewById(R.id.rightlinearlayout);

        }
    }
    public RecyclerAdapterFolders(ArrayList<Folder> folderList,MainActivity mainActivity) {

        this.folderList = folderList;
        this.mainActivity = mainActivity;

    }

    public void setFolderList(ArrayList<Folder> folderList) {
        this.folderList = folderList;
    }

    @NonNull
    @Override
    public ViewHolderFolders onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.folder_recycler_item,viewGroup,false);
        return new ViewHolderFolders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderFolders viewHolder, int i) {
        final Folder folder = folderList.get(i);
        viewHolder. folder_name.setText(folder.getName());
        viewHolder. folder_description.setText(folder.getDescription());
        viewHolder. folder_size.setText(folder.getSize());
        viewHolder. folder_date.setText(folder.getDateCreated());
        viewHolder. folder_no_of_file.setText(folder.getNoOfItem());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainActivity, FoldersDetailActivity.class);
                intent.putExtra("FOLDER_ID", viewHolder.folder_name.toString());
                mainActivity.startActivity(intent);
            }
        });

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                viewHolder.rightlinearlayout.setVisibility(View.GONE);
                viewHolder.checkBox.setVisibility(View.VISIBLE);
                MainActivity.showOptions(viewHolder.getAdapterPosition(),mainActivity.getApplicationContext());
                if(viewHolder.checkBox.isChecked()==false) {
                    viewHolder.checkBox.setChecked(true);
                }else{
                    viewHolder.checkBox.setChecked(false);
                }

                return true;
            }
        });

        viewHolder.folder_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                folderList.remove(viewHolder.getAdapterPosition());
                notifyItemRemoved(viewHolder.getAdapterPosition());
                notifyItemRangeChanged(viewHolder.getAdapterPosition(),folderList.size());
            }
        });
        viewHolder.folder_share_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        //viewHolder.textViewNoOfFollows.setText(String.valueOf(request.getNUMBER_OF_FOLLOWERS()));


    }

    @Override
    public int getItemCount() {

        return folderList.size();
    }

}
