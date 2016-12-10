package aaq.com.expensemanager.Adapter;

import aaq.com.expensemanager.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    public ArrayList<String> itemlist;

    public static class ViewHolder {
        ImageView imageView;
        TextView up_tv;
    }

    public void remove(int i) {
        this.itemlist.remove(i);
    }

    public CustomAdapter(Context context, ArrayList<String> itmelist) {
        this.context = context;
        this.itemlist = itmelist;
        this.inflater = (LayoutInflater) context.getSystemService("layout_inflater");
    }

    public int getCount() {
        return this.itemlist.size();
    }

    public Object getItem(int position) {
        return this.itemlist.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            convertView = this.inflater.inflate(R.layout.singleitem, null);
            holder.up_tv = (TextView) convertView.findViewById(R.id.tv_up);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.up_tv.setText((CharSequence) this.itemlist.get(position));
        return convertView;
    }
}
