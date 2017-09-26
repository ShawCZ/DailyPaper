package com.shaw.daily.net.download;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import com.shaw.daily.app.Daily;
import com.shaw.daily.net.callback.IRequest;
import com.shaw.daily.net.callback.ISuccess;
import com.shaw.daily.util.file.FileUtil;

import java.io.File;
import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * Created by shaw on 2017/9/1.
 */

public class SaveFileTask extends AsyncTask<Object,Void,File>{

    private final IRequest REQUEST;
    private final ISuccess SUCCESS;

    public SaveFileTask(IRequest request, ISuccess success) {
        this.REQUEST = request;
        this.SUCCESS = success;
    }

    @Override
    protected File doInBackground(Object... params) {
        String downloadDir = (String) params[0];
        String extension = (String) params[1];
        final ResponseBody body = (ResponseBody) params[2];
        String name = (String) params[3];
        final InputStream is = body.byteStream();

        if (downloadDir == null || downloadDir.equals("")){
            downloadDir = "down_loads";
        }
        if (extension == null || extension.equals("")) {
            extension = "";
        }
        if (name == null){
            return FileUtil.writeToDisk(is,downloadDir,extension.toUpperCase(),extension);
        }else {
            return FileUtil.writeToDisk(is,downloadDir,name);
        }
    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if (SUCCESS != null){
            SUCCESS.onSuccess(file.getPath());
        }
        if (REQUEST != null){
            REQUEST.onRequestEnd();
        }

        autoInstallApk(file);
    }

    //下载apk，自动安装
    private void autoInstallApk(File file){
        if (FileUtil.getExtension(file.getPath()).equals("apk")){
            final Intent install = new Intent();
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.setAction(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
            Daily.getApplicationContext().startActivity(install);
        }
    }
}
