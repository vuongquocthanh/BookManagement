package com.example.thanh.android_project_mob204.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.thanh.android_project_mob204.R;
import com.example.thanh.android_project_mob204.database.DatabaseHelper;
import com.example.thanh.android_project_mob204.model.Invoice;
import com.example.thanh.android_project_mob204.model.InvoiceDetail;
import com.example.thanh.android_project_mob204.sqlitedao.DAOBook;
import com.example.thanh.android_project_mob204.sqlitedao.DAOInvoice;
import com.example.thanh.android_project_mob204.sqlitedao.DAOInvoiceDetail;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EarningFragment extends Fragment {
    private TextView tvMonthValue, textViewTodayAmount, tvYearValue, tvToday, tvMonth, tvYear;
    private DatabaseHelper databaseHelper;
    private DAOInvoice daoInvoice;
    private DAOBook daoBook;
    private DAOInvoiceDetail daoInvoiceDetail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewEarning = inflater.inflate(R.layout.fragment_statistics_earning, container, false);

        tvToday = viewEarning.findViewById(R.id.tvToday);
        tvMonth = viewEarning.findViewById(R.id.tvMonth);
        tvYear = viewEarning.findViewById(R.id.tvYear);
        tvMonthValue = viewEarning.findViewById(R.id.tvMonthValue);
        textViewTodayAmount = viewEarning.findViewById(R.id.textViewTodayAmount);
        tvYearValue = viewEarning.findViewById(R.id.tvYearValue);
        databaseHelper = new DatabaseHelper(getContext());
        daoInvoice = new DAOInvoice(databaseHelper);
        daoInvoiceDetail = new DAOInvoiceDetail(databaseHelper);
        daoBook = new DAOBook(databaseHelper);
        Log.e("CURRENTTIME", System.currentTimeMillis() + "");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDay = simpleDateFormat.format(System.currentTimeMillis());
        Log.e("day", currentDay);
        String currentMonth = currentDay.substring(0, 7);
        Log.e("month", currentMonth);
        String currentYear = currentDay.substring(0, 4);
        Log.e("year", currentYear);
        List<Invoice> listInvoice = new ArrayList<>();
        listInvoice.addAll(daoInvoice.getAllInvoice());
        DecimalFormat formatter = new DecimalFormat("###,###,###");

//        Day Earning Statistic
        List<Invoice> listInvoiceByDay = new ArrayList<>();
        for (int i = 0; i < listInvoice.size(); i++) {
            String dateInvoiceConvert = simpleDateFormat.format(listInvoice.get(i).getInvoice_date());
            if (currentDay.equalsIgnoreCase(dateInvoiceConvert)) {
                listInvoiceByDay.add(listInvoice.get(i));
            }
        }
        List<InvoiceDetail> listInvoiceDetailByDay = new ArrayList<>();
        for (int i = 0; i < listInvoiceByDay.size(); i++) {
            listInvoiceDetailByDay.addAll(daoInvoiceDetail.getAllInvoiceDetailByInvoiceID(listInvoiceByDay.get(i).getInvoice_ID()));
        }
        Log.e("InvoiceDetailByDay", listInvoiceDetailByDay.size() + "");
        double amountDay = 0;
        for (int i = 0; i < listInvoiceDetailByDay.size(); i++) {
            amountDay += listInvoiceDetailByDay.get(i).getPurchaseNumber() * Double.parseDouble(daoBook.getBook(listInvoiceDetailByDay.get(i).getBookID()).getBookPrice());
        }

        textViewTodayAmount.setText(formatter.format(amountDay)+" VND");
        Log.e("amountToday", amountDay + "");

//       Month Earning Statistic
        List<Invoice> listInvoiceByMonth = new ArrayList<>();
        for (int i = 0; i < listInvoice.size(); i++) {
            String dateInvoiceConvert = simpleDateFormat.format(listInvoice.get(i).getInvoice_date());
            String monthInvoiceCOnvert = dateInvoiceConvert.substring(0, 7);
            if (currentMonth.equalsIgnoreCase(monthInvoiceCOnvert)) {
                listInvoiceByMonth.add(listInvoice.get(i));
            }
        }
        List<InvoiceDetail> listInvoiceDetailByMonth = new ArrayList<>();
        for (int i = 0; i < listInvoiceByMonth.size(); i++) {
            listInvoiceDetailByMonth.addAll(daoInvoiceDetail.getAllInvoiceDetailByInvoiceID(listInvoiceByMonth.get(i).getInvoice_ID()));
        }
        double amountMonth = 0;
        for (int i = 0; i < listInvoiceDetailByMonth.size(); i++) {
            amountMonth += listInvoiceDetailByMonth.get(i).getPurchaseNumber() * Double.parseDouble(daoBook.getBook(listInvoiceDetailByMonth.get(i).getBookID()).getBookPrice());
        }
        tvMonthValue.setText(formatter.format(amountMonth)+" VND");

//        Year Earning Statistic
        List<Invoice> listInvoiceByYear = new ArrayList<>();
        for(int i=0; i<listInvoice.size(); i++){
            String dateInvoiceConvert = simpleDateFormat.format(listInvoice.get(i).getInvoice_date());
            String yearInvoiceConvert = dateInvoiceConvert.substring(0, 4);
            if(currentYear.equalsIgnoreCase(yearInvoiceConvert)){
                listInvoiceByYear.add(listInvoice.get(i));
            }
        }
        List<InvoiceDetail> listInvoiceDetailByYear = new ArrayList<>();
        for(int i=0; i<listInvoiceByYear.size(); i++){
            listInvoiceDetailByYear.addAll(daoInvoiceDetail.getAllInvoiceDetailByInvoiceID(listInvoiceByYear.get(i).getInvoice_ID()));
        }
        double amountYear = 0;
        for(int i=0; i<listInvoiceDetailByYear.size(); i++){
            amountYear += listInvoiceDetailByYear.get(i).getPurchaseNumber() * Double.parseDouble(daoBook.getBook(listInvoiceDetailByYear.get(i).getBookID()).getBookPrice());
        }
        tvYearValue.setText(formatter.format(amountYear)+" VND");
        return viewEarning;
    }
}
