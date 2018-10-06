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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thanh.android_project_mob204.InvoiceDetailActivity;
import com.example.thanh.android_project_mob204.R;
import com.example.thanh.android_project_mob204.database.DatabaseHelper;
import com.example.thanh.android_project_mob204.model.InvoiceDetail;
import com.example.thanh.android_project_mob204.sqlitedao.DAOBook;
import com.example.thanh.android_project_mob204.sqlitedao.DAOInvoiceDetail;

import java.util.List;

public class AdapterInvoiceDetail extends RecyclerView.Adapter<AdapterInvoiceDetail.ViewHolder> {

    private List<InvoiceDetail> listInvoiceDetail;
    private DatabaseHelper databaseHelper;
    private DAOInvoiceDetail daoInvoiceDetail;
    private DAOBook daoBook;
    EditText edBookID, edCount;

    public AdapterInvoiceDetail(List<InvoiceDetail> listInvoiceDetail) {
        this.listInvoiceDetail = listInvoiceDetail;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View viewInvoiceDetail = inflater.inflate(R.layout.item_invoice_detail, parent, false);
        databaseHelper = new DatabaseHelper(parent.getContext());
        daoBook = new DAOBook(databaseHelper);
        daoInvoiceDetail = new DAOInvoiceDetail(databaseHelper);
        return new ViewHolder(viewInvoiceDetail);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        InvoiceDetail invoiceDetail = listInvoiceDetail.get(position);
        String book_price = daoBook.getBook(invoiceDetail.getBookID()).getBookPrice();
        holder.tvBookID.setText(invoiceDetail.getBookID());
        holder.tvCount.setText(invoiceDetail.getPurchaseNumber() + "");
        holder.tvPrice.setText(book_price);
        double price = Double.parseDouble(book_price);
        double amount = price * invoiceDetail.getPurchaseNumber();
        holder.tvAmount.setText(amount + "");
    }

    @Override
    public int getItemCount() {
        return listInvoiceDetail.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgInvoiceDetailDelete;
        public TextView tvBookID, tvCount, tvPrice, tvAmount;

        public ViewHolder(final View itemView) {
            super(itemView);
            tvBookID = (TextView) itemView.findViewById(R.id.tvBookID);
            tvCount = (TextView) itemView.findViewById(R.id.tvCount);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            tvAmount = (TextView) itemView.findViewById(R.id.tvAmount);
            imgInvoiceDetailDelete = (ImageView) itemView.findViewById(R.id.imgInvoiceDetailDelete);
            imgInvoiceDetailDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext(), R.style.Theme_AppCompat_DayNight_Dialog_MinWidth);
                    builder.setTitle("Delete Invoice");
                    builder.setMessage("Do you want delete?");
                    builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            daoInvoiceDetail.deleteInvoice(listInvoiceDetail.get(getAdapterPosition()).getInvoiceDetailId());
                            daoInvoiceDetail.deleteAllInvoiceDetail(listInvoiceDetail.get(getAdapterPosition()).getInvoiceID());
                            listInvoiceDetail.remove(getAdapterPosition());
                            notifyDataSetChanged();
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
                    InvoiceDetail invoiceDetail = listInvoiceDetail.get(getAdapterPosition());
                    Log.e("AdapterPosition", getAdapterPosition() + "");
                    Log.e("DB_BOOKID", daoInvoiceDetail.getAllInvoice().get(getAdapterPosition()).getBookID());
                    Log.e("DB_COUNT", daoInvoiceDetail.getAllInvoice().get(getAdapterPosition()).getPurchaseNumber() + "");
                    AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext(), R.style.Theme_AppCompat_DayNight_Dialog_MinWidth);
                    builder.setTitle("Invoice Detail");
                    LayoutInflater inflater = (LayoutInflater) itemView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View viewDialogInvoiceDetail = inflater.inflate(R.layout.dialog_invoice_detail, null);

                    edBookID = viewDialogInvoiceDetail.findViewById(R.id.edBookID);
                    edCount = viewDialogInvoiceDetail.findViewById(R.id.edCount);
                    edBookID.setText(daoInvoiceDetail.getAllInvoiceDetailByInvoiceID(invoiceDetail.getInvoiceID()).get(getAdapterPosition()).getBookID());
                    edCount.setText(daoInvoiceDetail.getAllInvoiceDetailByInvoiceID(invoiceDetail.getInvoiceID()).get(getAdapterPosition()).getPurchaseNumber() + "");

                    builder.setView(viewDialogInvoiceDetail);
                    builder.setNegativeButton("SAVE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String book_ID = edBookID.getText().toString();
                            String count = edCount.getText().toString();
                            if (book_ID.equalsIgnoreCase("")) {
                                edBookID.setError("Please do not empty!");
                            } else if (count.equalsIgnoreCase("")) {
                                edCount.setError("Please do not empty!");
                            } else {
                                boolean check = true;
                                for (int j = 0; j < daoBook.getAllBook().size(); j++) {
                                    if (!(daoBook.getAllBook().get(j).getBookId().equalsIgnoreCase(book_ID))) {
                                        check = false;
                                    } else {
                                        check = true;
                                        break;
                                    }
                                }
                                if (check == true) {
                                    Log.e("position", getAdapterPosition()+"");
                                    InvoiceDetail invoiceDetail = listInvoiceDetail.get(getAdapterPosition());
                                    invoiceDetail.setBookID(book_ID);
                                    Log.e("after", invoiceDetail.getBookID());
                                    invoiceDetail.setPurchaseNumber(Integer.parseInt(count));
                                    Log.e("aftercount", invoiceDetail.getPurchaseNumber()+"");
                                    daoInvoiceDetail.updateInvoiceDetail(invoiceDetail);
                                    listInvoiceDetail.set(getAdapterPosition(), invoiceDetail);
                                    notifyDataSetChanged();
//                                    notifyDataSetChanged();
//                                    listInvoiceDetail.get(getAdapterPosition()).setBookID(book_ID);
//                                    listInvoiceDetail.get(getAdapterPosition()).setPurchaseNumber(Integer.parseInt(count));
//                                    daoInvoiceDetail.updateInvoice(new InvoiceDetail(getAdapterPosition()+1, daoInvoiceDetail.getAllInvoice().get(getAdapterPosition()).getInvoiceID(), book_ID, Integer.parseInt(count)));
//                                    notifyDataSetChanged();
                                } else {
                                    Toast.makeText(itemView.getContext(), "Book ID not match!!!", Toast.LENGTH_SHORT).show();
                                }
                            }
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
        }
    }
}
