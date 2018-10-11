package com.example.thanh.android_project_mob204.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.thanh.android_project_mob204.R;
import com.example.thanh.android_project_mob204.adapter.AdapterBook;
import com.example.thanh.android_project_mob204.adapter.AdapterBookTrending;
import com.example.thanh.android_project_mob204.database.DatabaseHelper;
import com.example.thanh.android_project_mob204.model.Book;
import com.example.thanh.android_project_mob204.model.BookTrending;
import com.example.thanh.android_project_mob204.model.Invoice;
import com.example.thanh.android_project_mob204.model.InvoiceDetail;
import com.example.thanh.android_project_mob204.sqlitedao.DAOBook;
import com.example.thanh.android_project_mob204.sqlitedao.DAOInvoice;
import com.example.thanh.android_project_mob204.sqlitedao.DAOInvoiceDetail;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TrendingFragment extends Fragment {
    private RecyclerView recyclerViewBookTrending;
    private DatabaseHelper databaseHelper;
    private DAOBook daoBook;
    private DAOInvoiceDetail daoInvoiceDetail;
    private DAOInvoice daoInvoice;
    private List<Book> listBook;
    private EditText edFindBookTrending;
    private AdapterBook adapterBook;
    private Button btFindTrending;
    private AdapterBookTrending adapterBookTrending;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewTrending = inflater.inflate(R.layout.fragment_statistics_trending, container, false);
        btFindTrending = viewTrending.findViewById(R.id.btFindTrending);
        recyclerViewBookTrending = viewTrending.findViewById(R.id.recyclerViewBookTrending);
        edFindBookTrending = viewTrending.findViewById(R.id.edFindTrending);

        btFindTrending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String month = edFindBookTrending.getText().toString();
                if(month.length()<2){
                    month = "0"+month;
                }
                if (month.equalsIgnoreCase("")){
                    edFindBookTrending.setError("Please enter month!!");
                }
                databaseHelper = new DatabaseHelper(getContext());
                daoInvoiceDetail = new DAOInvoiceDetail(databaseHelper);
                daoBook = new DAOBook(databaseHelper);
                List<InvoiceDetail> listInvoiceDetail = new ArrayList<>();
                daoInvoice = new DAOInvoice(databaseHelper);
                List<Invoice> listAllInvoice = new ArrayList<>();
                listAllInvoice.addAll(daoInvoice.getAllInvoice());
                List<Invoice> listMonthInvoice = new ArrayList<>();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                for (int i = 0; i < listAllInvoice.size(); i++) {
                    String dateConvert = simpleDateFormat.format(listAllInvoice.get(i).getInvoice_date());
                    String subDateConvert = dateConvert.substring(5, 7);
                    if (subDateConvert.equalsIgnoreCase(month)) {
                        listMonthInvoice.add(listAllInvoice.get(i));
                    }
                }
                List<Book> listBook = new ArrayList<>();
                for(int i=0; i<listMonthInvoice.size(); i++){
                    listInvoiceDetail.addAll(daoInvoiceDetail.getAllInvoiceDetailByInvoiceID(listMonthInvoice.get(i).getInvoice_ID()));
                }

                Map<String, Integer> map = new TreeMap<String, Integer>();
                for(int i=0; i<listInvoiceDetail.size(); i++){
                    addElement(map, listInvoiceDetail.get(i).getBookID());
                }
                Log.e("listMonthInvoice", listMonthInvoice.size() + "");
                Log.e("listInvoiceDetail", listInvoiceDetail.size()+"");

                for(int j = 0; j<map.size(); j++){
                    Log.e("value key",map.keySet().toArray()[j]+"");
                }
                List<BookTrending> listBookTrending = new ArrayList<>();
                for(int j = 0; j<map.size(); j++){
                    int count = 0;
                    for(int i=0; i<listInvoiceDetail.size(); i++){
                        if(listInvoiceDetail.get(i).getBookID().equalsIgnoreCase((String) map.keySet().toArray()[j])){
                            count += listInvoiceDetail.get(i).getPurchaseNumber();
                        }
                    }
                    BookTrending bookTrending = new BookTrending(map.keySet().toArray()[j]+"", daoBook.getBook(map.keySet().toArray()[j]+"").getBookName(), daoBook.getBook(map.keySet().toArray()[j]+"").getBookDescription(), daoBook.getBook(map.keySet().toArray()[j]+"").getBookAvatar(), count);
                    listBookTrending.add(bookTrending);

                }
                List<BookTrending> listBookTrendingSort = new ArrayList<>();

                Collections.sort(listBookTrending, new QuantityComparator());
                for(BookTrending bookTrending:listBookTrending){
                    listBookTrendingSort.add(bookTrending);
                }
                List<BookTrending> listBookTop10 = new ArrayList<>();
                for(int i=0; i<listBookTrendingSort.size(); i++){
                    if(i<10){
                        listBookTop10.add(listBookTrendingSort.get(i));
                    }
                }

                adapterBookTrending = new AdapterBookTrending(listBookTop10);
                recyclerViewBookTrending.setAdapter(adapterBookTrending);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyclerViewBookTrending.setLayoutManager(layoutManager);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), ((LinearLayoutManager) layoutManager).getOrientation());
                recyclerViewBookTrending.addItemDecoration(dividerItemDecoration);


            }
        });
        return viewTrending;
    }

    public class QuantityComparator implements Comparator<BookTrending>{

        @Override
        public int compare(BookTrending t1, BookTrending t2) {
            if(t1.getCountTrending()==t2.getCountTrending()){
                return 0;
            }else if(t1.getCountTrending()<t2.getCountTrending()){
                return 1;
            }else{
                return -1;
            }
        }
    }

    public void addElement(Map<String, Integer> map, String element){
        if(map.containsKey(element)){
            int count = map.get(element)+1;
            map.put(element, count);
        }else{
            map.put(element, 1);
        }
    }
}
