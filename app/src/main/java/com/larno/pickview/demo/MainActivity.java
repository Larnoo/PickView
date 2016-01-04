package com.larno.pickview.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.larno.pickview.OptionsPickerView;
import com.larno.pickview.TimePickerView;
import com.larno.pickview.adapter.ArrayWheelAdapter;
import com.larno.pickview.demo.bean.City;
import com.larno.pickview.demo.dao.CityDao;
import com.larno.pickview.demo.util.LogUtil;
import com.larno.pickview.listener.OnItemSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private Button button_time_picker, button_content_picker;
    private View view_masker;

    /**
     * pickerView 设置的集合对象的---toString方法的返回值---为pickerView展示的内容
     */
    private ArrayList<City> levelOnes = new ArrayList<City>();
    private ArrayList<City> levelTwos = new ArrayList<City>();
    private ArrayList<City> levelThrees = new ArrayList<City>();

    private TimePickerView timePickerView;
    private OptionsPickerView optionsPickerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        button_time_picker = (Button) findViewById(R.id.button_time_picker);
        button_content_picker = (Button) findViewById(R.id.button_content_picker);
        view_masker = findViewById(R.id.view_masker);
        initTimePicker();
        initOptionPicker();


        //弹出时间选择器
        button_time_picker.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                timePickerView.show();
            }
        });

        //点击弹出选项选择器
        button_content_picker.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                optionsPickerView.show();
            }
        });
    }

    private void initOptionPicker() {
        //选项选择器
        optionsPickerView = new OptionsPickerView(this);
        
        //初始化picker数据
        ArrayList<City> provinces = new CityDao().getProvinces();
        levelOnes.clear();
        levelOnes.addAll(provinces);

        ArrayList<City> cities = new CityDao().getCities(levelOnes.get(0).area_code);
        levelTwos.clear();
        levelTwos.addAll(cities);

        if(levelTwos.size() > 0){
            ArrayList<City> counties = new CityDao().getCounties(levelTwos.get(0).area_code);
            levelThrees.clear();
            levelThrees.addAll(counties);
        }else {
            ArrayList<City> counties = new ArrayList<City>();
            levelThrees.clear();
            levelThrees.addAll(counties);
        }


        //三级联动效果
        optionsPickerView.setPicker(levelOnes, levelTwos, levelThrees, false);
        //设置选中监听
        optionsPickerView.getWheelOptions().getWv_option1().setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                ArrayList<City> temp = new CityDao().getCities(levelOnes.get(index).area_code);
                ArrayWheelAdapter adapter = new ArrayWheelAdapter(temp);
                levelTwos.clear();
                levelTwos.addAll(temp);
                optionsPickerView.getWheelOptions().getWv_option2().setAdapter(adapter);

                if (levelTwos.size() == 0) {
                    ArrayList<City> temp1 = new ArrayList<>();
                    ArrayWheelAdapter adapter1 = new ArrayWheelAdapter(temp1);
                    levelThrees.clear();
                    levelThrees.addAll(temp1);
                    optionsPickerView.getWheelOptions().getWv_option3().setAdapter(adapter1);
                    return;
                }else {
                    ArrayList<City> temp1 = new CityDao().getCounties(levelTwos.get(0).area_code);
                    ArrayWheelAdapter adapter1 = new ArrayWheelAdapter(temp1);
                    levelThrees.clear();
                    levelThrees.addAll(temp1);
                    optionsPickerView.getWheelOptions().getWv_option3().setAdapter(adapter1);
                }


            }
        });
        optionsPickerView.getWheelOptions().getWv_option2().setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                ArrayList<City> temp = new CityDao().getCounties(levelTwos.get(index).area_code);
                ArrayWheelAdapter adapter = new ArrayWheelAdapter(temp);
                levelThrees.clear();
                levelThrees.addAll(temp);
                optionsPickerView.getWheelOptions().getWv_option3().setAdapter(adapter);
            }
        });
        //设置选择的三级单位
//        optionsPickerView.setLabels("省", "市", "区");
//        optionsPickerView.setTitle("选择城市");
        optionsPickerView.setCyclic(false, false, false);
        optionsPickerView.setCancelable(false);
        //设置默认选中的三级项目
        //监听确定选择按钮
        optionsPickerView.setSelectOptions(0, 0, 0);
        optionsPickerView.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3) {
                if(levelThrees.size()>0){
                    LogUtil.e(MainActivity.this, "levelThrees.size()="+levelThrees.size());
                    button_content_picker.setText(levelOnes.get(options1).toString() + levelTwos.get(options2) + levelThrees.get(options3));
                }else if(levelTwos.size()>0){
                    button_content_picker.setText(levelOnes.get(options1).toString()+levelTwos.get(options2));
                }else {
                    button_content_picker.setText(levelOnes.get(options1).toString());
                }

                //返回的分别是三个级别的选中位置

                view_masker.setVisibility(View.GONE);
            }
        });
    }

    private void initTimePicker() {
        //时间选择器
        timePickerView = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        //控制时间范围
//        Calendar calendar = Calendar.getInstance();
//        timePickerView.setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR));
        timePickerView.setTime(new Date());
        timePickerView.setCyclic(false);
        timePickerView.setCancelable(true);
        //时间选择后回调
        timePickerView.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                button_time_picker.setText(getTime(date));
            }
        });
    }

    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }

}
