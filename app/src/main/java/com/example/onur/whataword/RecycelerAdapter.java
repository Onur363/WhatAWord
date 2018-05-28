package com.example.onur.whataword;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Onur on 19.11.2017.
 */

public class RecycelerAdapter extends RecyclerView.Adapter<RecycelerAdapter.MyViewHolder> {

    ArrayList<Words> words=new ArrayList<Words>();
    CustomItemClickListener ıtemClickListener;
    CustomItemLongClickListenr ıtemLongClickListenr;


    RecycelerAdapter(ArrayList<Words> words,CustomItemClickListener ıtemClickListener,CustomItemLongClickListenr ıtemLongClickListenr)
    {
        this.words=words;
        this.ıtemClickListener=ıtemClickListener;
        this.ıtemLongClickListenr=ıtemLongClickListenr;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_layout,parent,false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ıtemClickListener.onItemClick(v,new MyViewHolder(v).getPosition());
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ıtemLongClickListenr.onItemLongClick(v,new MyViewHolder(v).getPosition(),new MyViewHolder(v).getItemId());
                return true;
            }
        });
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.txtId.setText(String.valueOf(words.get(position).getId()));
        holder.txtEn.setText(words.get(position).getEn());
        holder.txtTr.setText(words.get(position).getTr());
        holder.txtCen.setText(words.get(position).getCen());
        holder.txtCtr.setText(words.get(position).getCtr());
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView txtId,txtEn,txtTr,txtCen,txtCtr;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtId= (TextView) itemView.findViewById(R.id.txtId);txtEn= (TextView) itemView.findViewById(R.id.textEn);
            txtTr= (TextView) itemView.findViewById(R.id.textTr);
            txtCen= (TextView) itemView.findViewById(R.id.txtCen);txtCtr= (TextView) itemView.findViewById(R.id.txtCtr);
            itemView.setTag(txtId.getText());

        }
    }
    public void setFilter(ArrayList<Words> newList){
        words=new ArrayList<>();
        words.addAll(newList);
        notifyDataSetChanged();
    }

}
