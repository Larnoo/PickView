package com.larno.pickview.demo.db;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.media.audiofx.LoudnessEnhancer;
import android.util.Log;

import com.larno.pickview.demo.util.LogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by sks on 2016/1/4.
 */
public class DbManager {
    private static final String tag = "DbManager";
    private static final String assets_name= "default.db";
    private static final String packageName = "com.larno.pickview.demo";
    //数据库存储路径
    private static final String filePath = "data/data/"+packageName+"/"+assets_name;
    //数据库存放的文件夹 data/data/package 下面
    private static final String pathStr = "data/data/"+ packageName;

    public static SQLiteDatabase getDatabase(Context context) {
        LogUtil.e(tag, "filePath:" + filePath);
        File file = new File(filePath);
        //查看数据库文件是否存在
        if (file.exists()) {
            LogUtil.e(tag, "存在数据库");
            //存在则直接返回打开的数据库
            return SQLiteDatabase.openOrCreateDatabase(file, null);
        } else {
            //不存在先创建文件夹
            File path = new File(pathStr);
            LogUtil.e(tag, "pathStr=" + path);
            if (path.mkdir()) {
                LogUtil.e(tag, "创建成功");
            } else {
                LogUtil.e(tag, "创建失败");
            }
            ;
            try {
                //得到资源
                AssetManager am = context.getAssets();
                //得到数据库的输入流
                InputStream is = am.open(assets_name);
                LogUtil.e(tag, is + "");
                //用输出流写到SDcard上面
                FileOutputStream fos = new FileOutputStream(file);
                LogUtil.e(tag, "fos=" + fos);
                LogUtil.e(tag, "filePath=" + file);
                //创建byte数组  用于1KB写一次
                byte[] buffer = new byte[1024];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    LogUtil.e(tag, "得到");
                    fos.write(buffer, 0, count);
                }
                //最后关闭就可以了
                fos.flush();
                fos.close();
                is.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            }
            //如果没有这个数据库  我们已经把他写到SD卡上了，然后在执行一次这个方法 就可以返回数据库了
            return getDatabase(context);
        }
    }
}

