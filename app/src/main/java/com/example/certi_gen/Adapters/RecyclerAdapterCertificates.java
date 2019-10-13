package com.example.certi_gen.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.certi_gen.Classes.Certificate;
import com.example.certi_gen.R;

import java.util.List;

public class RecyclerAdapterCertificates extends RecyclerView.Adapter<RecyclerAdapterCertificates.ViewHolderCertificates> {

        public List<Certificate> certificateList;

        public class ViewHolderCertificates extends RecyclerView.ViewHolder {
            public TextView certificate_holder_name;
            public TextView certificate_holder_emailid;
            public TextView certificate_holder_phone_number;
            public TextView certificate_size;
            public TextView certificate_holder_rank;
            public TextView certificate_holder_rollnumber;



            public ViewHolderCertificates(@NonNull View itemView) {
                super(itemView);

                certificate_holder_name = itemView.findViewById(R.id.certificate_holder_name);
                certificate_holder_emailid = itemView.findViewById(R.id.certificate_holder_emailid);
                certificate_holder_phone_number = itemView.findViewById(R.id.certificate_holder_phone_number);
                certificate_size = itemView.findViewById(R.id.certificate_size);
                certificate_holder_rank = itemView.findViewById(R.id.certificate_holder_rank);
                certificate_holder_rollnumber = itemView.findViewById(R.id.certificate_holder_rollnumber);


            }
        }
    public RecyclerAdapterCertificates(List<Certificate> certificateList) {

            this.certificateList = certificateList;

        }


        @NonNull
        @Override
        public ViewHolderCertificates onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.certificate_recycler_item,viewGroup,false);
            return new ViewHolderCertificates(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolderCertificates viewHolder, int i) {
            final Certificate certificate = certificateList.get(i);
            viewHolder. certificate_holder_name.setText(certificate.getName());
            viewHolder. certificate_holder_emailid.setText(certificate.getEmailId());
            viewHolder. certificate_holder_phone_number.setText(certificate.getPhoneNumber());
            viewHolder. certificate_size.setText(certificate.getSize());
            viewHolder. certificate_holder_rank.setText(certificate.getPosition());
            viewHolder. certificate_holder_rollnumber.setText(certificate.getRollNo());

            //viewHolder.textViewNoOfFollows.setText(String.valueOf(request.getNUMBER_OF_FOLLOWERS()));


        }

        @Override
        public int getItemCount() {

            return certificateList.size();
        }

}
