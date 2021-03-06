package tw.com.mobilogics.demo;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tw.com.mobilogics.demo.db.DBHelper;

/**
 * Created by xuyouren on 14/12/4.
 */
public class RecordActivity extends Activity {

  private static final String TAG = RecordActivity.class.getName();

  private ListView mListView;

  private HashMap<String, Integer> mMap = new HashMap();

  private List<View> mViewList = new ArrayList();

  private List<String> checkData = new ArrayList();


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_record);
    mListView = (ListView) findViewById(R.id.listView);
    Cursor cursor = DBHelper.Table.getAllData(this, DBHelper.Table.Personal.tableName);
    while (cursor.moveToNext()) {
      String time = cursor.getString(cursor.getColumnIndex(DBHelper.Table.Personal.time));
      int calories = cursor.getInt(cursor.getColumnIndex(DBHelper.Table.Personal.calories));
      String date = time.split(" ")[0];
      if (!checkData.contains(date)) {
        checkData.add(date);
        TextView textView = new TextView(this);
        textView.setTextSize(32);
        textView.setBackgroundColor(Color.BLUE);
        textView.setText(date);
        mViewList.add(textView);
      }
      TextView textView = new TextView(this);
      String msg = "時間" + time.split(" ")[1] + ",  熱量";
      if (calories <= 0) msg += "消耗 " + calories;
      else msg += "增加 " + calories;
      textView.setText(msg);
      mViewList.add(textView);
    }cursor.close();

    mListView.setAdapter(new RecordAdapter(mViewList));
  }

  public static class RecordAdapter extends BaseAdapter {

    private List<View> mViews;

    public RecordAdapter(List<View> views) {
      mViews = views;
    }

    @Override
    public int getCount() {
      return mViews.size();
    }

    @Override
    public Object getItem(int position) {
      return mViews.get(position);
    }

    @Override
    public long getItemId(int position) {
      return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      return mViews.get(position);
    }
  }
}
