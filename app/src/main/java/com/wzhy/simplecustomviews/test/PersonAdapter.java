package com.wzhy.simplecustomviews.test;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wzhy.simplecustomviews.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-11-15 0015.
 */

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ItemViewHolder> {

    private List<Person> personList = new ArrayList<>();

    public PersonAdapter() {}


    public PersonAdapter(List<Person> personList) {
        if (null != personList && !personList.isEmpty()) {
            this.personList.addAll(personList);
        }
    }


    public void setPersonList(List<Person> personList){
        this.personList.clear();
        this.personList.addAll(personList);
        notifyDataSetChanged();
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_person, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
//        Log.d("PersonAdapter", "onBindViewHolder # position: " + position + ", itemId: " + holder.getItemId());
        Person person = personList.get(position);
        holder.position = position;
//        holder.personNameTv.setText(person.getName());
//        holder.personAgeTv.setText(String.valueOf(person.getAge()).concat("岁"));

        holder.personNameTv.setText("wzhy_" + position);
        holder.personAgeTv.setText((15 + position) + "岁");
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    public void insertPerson(int index){
        Person person = new Person("wzhy_" + index, 15 + index);
        notifyItemInserted(index);
        personList.add(index, person);
        notifyItemRangeChanged(index, getItemCount());
    }

    public void deletePerson(int index){
        notifyItemRemoved(index);
        personList.remove(index);
        notifyItemRangeChanged(index, getItemCount());
    }


    public interface OnRvItemClickListener{
        void onItemClick(View itemView, int position);

        boolean onItemLongClick(View itemView, int position);

    }

    private OnRvItemClickListener mOnRvItemClickListener;

    public void setOnRvItemClickListener(OnRvItemClickListener onRvItemClickListener) {
        this.mOnRvItemClickListener = onRvItemClickListener;
    }


    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        public TextView personNameTv;
        public TextView personAgeTv;
        public int position;

        public ItemViewHolder(View itemView) {
            super(itemView);
            this.personNameTv = (TextView) itemView.findViewById(R.id.tv_person_name);
            this.personAgeTv = (TextView) itemView.findViewById(R.id.tv_person_age);
            this.itemView.setOnClickListener(this);
            this.itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (null != mOnRvItemClickListener) {
                mOnRvItemClickListener.onItemClick(this.itemView, position);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (null != mOnRvItemClickListener) {
                return mOnRvItemClickListener.onItemLongClick(this.itemView, position);
            }
            return false;
        }
    }



}
