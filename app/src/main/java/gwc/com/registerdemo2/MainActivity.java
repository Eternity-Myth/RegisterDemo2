package gwc.com.registerdemo2;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText Password;
    AutoCompleteTextView EmailAddress;
    String[] stringArray = {"@qq.com", "@163.com", "@126.com", "@gmail.com", "@sina.com", "@hotmail.com",
            "@yahoo.cn", "@sohu.com", "@foxmail.com", "@139.com", "@yeah.net", "@vip.qq.com", "@vip.sina.com"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Password = (EditText) findViewById(R.id.password); //通过findViewById找到输入框控件对应的id并给它起一个名字
        EmailAddress = (AutoCompleteTextView) findViewById(R.id.emailaddress);//通过findViewById找到输入框控件对应的id并给它起一个名字
        Button SignIn = (Button) findViewById(R.id.SignIn);//通过findViewById找到按钮控件对应的id并给它起一个名字
        Button Register = (Button) findViewById(R.id.Register);//通过findViewById找到按钮控件对应的id并给它起一个名字
        SignIn.setOnClickListener(new View.OnClickListener()//监听有没有点击按钮控件 如果点击了就会执行onClick函数
        {
            public void onClick(View view) {
                check(EmailAddress.getText().toString().trim(), Password.getText().toString().trim());
            }
        });
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(register);
            }
        });
        final MyAdatper adapter = new MyAdatper(this);
        EmailAddress.setAdapter(adapter);
        EmailAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString();
                adapter.mList.clear();
                if (input.length() > 0) {
                    for (int i = 0; i < stringArray.length; ++i) {
                        adapter.mList.add(input + stringArray[i]);
                    }
                }
                adapter.notifyDataSetChanged();
                EmailAddress.showDropDown();

            }
        });

        // default=2
        EmailAddress.setThreshold(1);
    }

    public void check(String emailaddress, String password) {
        if (emailaddress.equals("") || password.equals(""))//判断输入的帐户密码是否为空
        {
            Toast.makeText(MainActivity.this, "Error!Empty EmailAddress or Password!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Success!Welcome", Toast.LENGTH_SHORT).show();
        }
    }

    class MyAdatper extends BaseAdapter implements Filterable {

        List<String> mList;
        private Context mContext;
        private MyFilter mFilter;

        public MyAdatper(Context context) {
            mContext = context;
            mList = new ArrayList<String>();
        }

        @Override
        public int getCount() {
            return mList == null ? 0 : mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList == null ? null : mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                TextView tv = new TextView(mContext);
                tv.setTextColor(Color.BLACK);
                tv.setTextSize(20);
                convertView = tv;
            }
            TextView txt = (TextView) convertView;
            txt.setText(mList.get(position));
            return txt;
        }

        @Override
        public Filter getFilter() {
            if (mFilter == null) {
                mFilter = new MyFilter();
            }
            return mFilter;
        }

        private class MyFilter extends Filter {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                if (mList == null) {
                    mList = new ArrayList<String>();
                }
                results.values = mList;
                results.count = mList.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }

        }

    }
}