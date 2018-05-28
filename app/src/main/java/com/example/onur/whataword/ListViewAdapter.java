package com.example.onur.whataword;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by Onur on 10.11.2017.
 */

public class ListViewAdapter extends BaseAdapter implements Filterable {

    private WordsFilter wordsFilter;
    private ArrayList<Words> mOrginialValues;
    private ArrayList<Words> mDisplayValues;
    Context context;

    public ListViewAdapter(Context context,ArrayList<Words> mWordArrayList){

        this.context=context; this.mOrginialValues=mWordArrayList;
        this.mDisplayValues=mWordArrayList;

        getFilter();
    }

    @Override
    public int getCount() {
        return mDisplayValues.size();
    }

    @Override
    public Words getItem(int position) {
        return mDisplayValues.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v=layoutInflater.inflate(R.layout.list_item_layout,parent,false);

        Words words=(Words)getItem(position);

        TextView txtId= (TextView) v.findViewById(R.id.textId); TextView txtEn= (TextView) v.findViewById(R.id.textEn);
        TextView txtTr= (TextView) v.findViewById(R.id.textTr);

        txtEn.setText(words.getEn().toString()); txtTr.setText(words.getTr().toString()); txtId.setText(String.valueOf(words.getId()));

        v.setTag(Integer.parseInt(txtId.getText().toString()));
        return v;
    }

    @Override
    public Filter getFilter() {

        if(wordsFilter==null){
            wordsFilter=new WordsFilter();
        }
        return wordsFilter;
    }
    private class WordsFilter extends Filter{


        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults filterResults=new FilterResults();
            if(constraint!=null && constraint.length()>0)
            {
                ArrayList<Words> templist=new ArrayList<Words>();

                for(Words words:mOrginialValues){
                    if(words.getEn().toLowerCase().contains((constraint.toString().toLowerCase()))){
                        templist.add(words);
                    }
                }
                filterResults.count=templist.size();
                filterResults.values=templist;
            }
            else
            {
                filterResults.count=mOrginialValues.size();
                filterResults.values=mOrginialValues;
            }

            return filterResults;
        }
        @SuppressWarnings("unchecked")

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            mDisplayValues=(ArrayList<Words>)results.values;
            notifyDataSetChanged();

        }
    }
}
