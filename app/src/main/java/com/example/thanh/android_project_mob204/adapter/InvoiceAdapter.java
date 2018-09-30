package com.example.thanh.android_project_mob204.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thanh.android_project_mob204.R;
import com.example.thanh.android_project_mob204.model.Invoice;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class InvoiceAdapter extends ArrayAdapter<Invoice> {
    private List<Invoice> listInvoice;
    private LayoutInflater inflater;
    public InvoiceAdapter(@NonNull Context context, int resource, @NonNull List<Invoice> objects) {
        super(context, resource, objects);
        listInvoice = objects;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_invoice, parent, false);
            holder.imgInvoice = convertView.findViewById(R.id.imgInvoice);
            holder.tvTitleInvoice = convertView.findViewById(R.id.tvIvoiceName);
            holder.tvInvoiceDate = convertView.findViewById(R.id.tvIvoiceDate);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        Invoice invoice = listInvoice.get(position);
        holder.tvTitleInvoice.setText(invoice.getTitle());
        holder.tvInvoiceDate.setText(invoice.getDate());
        return convertView;
    }

    public class ViewHolder{
        public CircleImageView imgInvoice;
        public TextView tvTitleInvoice, tvInvoiceDate;
        public ImageView imgDelete;
    }
}
