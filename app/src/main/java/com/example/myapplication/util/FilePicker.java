package com.example.myapplication.util;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import androidx.fragment.app.Fragment;
import com.example.myapplication.log.SpeedyLog;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 张磊  on  2023/05/25 at 16:25
 * Email: 913305160@qq.com
 */
public class FilePicker {
    private static final String TAG = "FilePicker";

    public static int PICK_FILE_REQUEST_CODE = 1000;

    private IPickResult iPickResult;

    private Fragment fragment;

    public FilePicker(Fragment fragment) {
        this.fragment = fragment;
    }

    /**
     * @param isMultipleChoose 是否多选
     * @param iPickResult      选择回调结果
     */
    public void launch(boolean isMultipleChoose, IPickResult iPickResult) {
        this.iPickResult = iPickResult;

        // vivio 手机上述方式不行
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // 是否多选
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, isMultipleChoose);
        intent.setType("*/*");

        this.fragment.startActivityForResult(intent, PICK_FILE_REQUEST_CODE);
    }


    public void onActivityResult(int resultCode, Intent data) {
        SpeedyLog.d(TAG, String.format("onActivityResult ----> resultCode == %s", resultCode));

        ArrayList<Uri> uris = new ArrayList<>();
        boolean isCancel = true;

        if (resultCode == Activity.RESULT_OK) {
            // 这里是针对从系统自带的文件选择器中选择文件

            if (data != null) {
                ClipData clipData = data.getClipData();

                if (clipData != null) {
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        uris.add(clipData.getItemAt(i).getUri());
                    }
                } else {
                    // 单选文件
                    Uri uri = data.getData();
                    uris.add(uri);
                }
            }

            isCancel = false;
        } else if (resultCode == Activity.RESULT_CANCELED) {
            isCancel = true;
        }

        if (iPickResult != null) {
            iPickResult.onPickResult(isCancel, uris);
        }
    }


    public interface IPickResult {
        /**
         * @param isCancel 是否取消选择文件
         * @param uris     选择完成后的文件 uri 集合， 单选时大小为 1
         */
        void onPickResult(boolean isCancel, List<Uri> uris);
    }
}
