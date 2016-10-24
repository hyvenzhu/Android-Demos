package com.example.hiphonezhu.listview_edittext;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hiphonezhu@gmail.com
 * @version [ListView_EditText, 16/10/11 19:52]
 */

public class MyAdapter extends BaseAdapter {
    List<Bean> beens = new ArrayList<>();
    LayoutInflater inflater;

    public MyAdapter(Context context) {
        for (int i = 0; i < 30; i++) {
            Bean bean = new Bean();
            bean.setVaule("str" + i);
            beens.add(bean);
        }
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return beens.size();
    }

    @Override
    public Bean getItem(int position) {
        return beens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MyHolder myHolder = null;
        final Bean bean = getItem(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_et, parent, false);

            myHolder = new MyHolder();
            myHolder.et = (EditText) convertView.findViewById(R.id.et);

            myHolder.textWatcher = new MyTextWatcher();
            myHolder.et.addTextChangedListener(myHolder.textWatcher);
            convertView.setTag(myHolder);
        } else {
            myHolder = (MyHolder) convertView.getTag();
        }
        myHolder.textWatcher.position = position;
        myHolder.et.setText(bean.getVaule());
        return convertView;
    }

    class MyTextWatcher implements TextWatcher
    {
        int position;
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            beens.get(position).setVaule(s.toString());
        }
    }

    class MyHolder {
        EditText et;
        MyTextWatcher textWatcher;
    }

    class Bean {
        private String vaule;

        public void setVaule(String vaule) {
            this.vaule = vaule;
        }

        public String getVaule() {
            return vaule;
        }
    }
}
