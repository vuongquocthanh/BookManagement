package com.example.thanh.android_project_mob204.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thanh.android_project_mob204.InvoiceDetailActivity;
import com.example.thanh.android_project_mob204.R;
import com.example.thanh.android_project_mob204.activity.AddDetailInvoiceActivity;
import com.example.thanh.android_project_mob204.database.DatabaseHelper;
import com.example.thanh.android_project_mob204.model.Invoice;
import com.example.thanh.android_project_mob204.sqlitedao.DAOInvoice;
import com.example.thanh.android_project_mob204.sqlitedao.DAOInvoiceDetail;

import java.util.List;

public class AdapterInvoice extends RecyclerView.Adapter<AdapterInvoice.ViewHolder>{
    private List<Invoice> listInvoice;
    private DatabaseHelper databaseHelper;
    private DAOInvoice daoInvoice;

    private DAOInvoiceDetail daoInvoiceDetail;
    public AdapterInvoice(List<Invoice> listInvoice) {
        this.listInvoice = listInvoice;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View viewInvoice = inflater.inflate(R.layout.item_invoice, parent, false);
        databaseHelper = new DatabaseHelper(parent.getContext());
        daoInvoice = new DAOInvoice(databaseHelper);
        daoInvoiceDetail = new DAOInvoiceDetail(databaseHelper);
        return new ViewHolder(viewInvoice);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Invoice invoice = listInvoice.get(position);
        holder.tvInvoiceID.setText(invoice.getInvoice_ID());
        holder.tvInvoiceDate.setText(invoice.toStringInvoiceDate());
    }

    @Override
    public int getItemCount() {
        return listInvoice.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        public ImageView imgInvoiceAvatar, imgInvoiceDelete;
        public TextView tvInvoiceID, tvInvoiceDate;
        public ViewHolder(final View itemView) {
            super(itemView);
            imgInvoiceAvatar = itemView.findViewById(R.id.imgInvoiceAvatar);
            imgInvoiceDelete = itemView.findViewById(R.id.imgInvoiceDelete);
            tvInvoiceID = itemView.findViewById(R.id.tvInvoiceID);
            tvInvoiceDate = itemView.findViewById(R.id.tvInvoiceDate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            imgInvoiceDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext(), R.style.Theme_AppCompat_DayNight_Dialog_MinWidth);
                    builder.setTitle("Delete Invoice");
                    builder.setMessage("Do you want delete this invoice?");
                    builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(daoInvoiceDetail.getAllInvoiceDetailByInvoiceID(tvInvoiceID.getText().toString()).size()>0){
                                Toast.makeText(itemView.getContext(), "Phai xoa HDCT truoc!", Toast.LENGTH_SHORT).show();
                            }else{
                                daoInvoice.deleteInvoice(listInvoice.get(getAdapterPosition()).getInvoice_ID());
                                listInvoice.remove(getAdapterPosition());
                                notifyDataSetChanged();
                            }
//                            daoInvoice.deleteInvoice(listInvoice.get(getAdapterPosition()).getInvoice_ID());
//                            listInvoice.remove(getAdapterPosition());
//                            notifyDataSetChanged();
                        }
                    });

                    builder.setPositiveButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    builder.show();
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("sizeInvoiceDetail", daoInvoiceDetail.getAllInvoiceDetailByInvoiceID(tvInvoiceID.getText().toString()).size()+"");
                    Intent intent = new Intent(itemView.getContext(), InvoiceDetailActivity.class);
                    intent.putExtra("invoiceID", daoInvoice.getAllInvoice().get(getAdapterPosition()).getInvoice_ID());
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
