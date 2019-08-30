package com.kulya.lanzou.view;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.kulya.lanzou.MainActivity;
import com.kulya.lanzou.R;
import com.kulya.lanzou.http.HttpWorker;

import androidx.recyclerview.widget.RecyclerView;

/*
项目名称： lanzou
创建人：黄大神
类描述：
创建时间：2019/8/29 15:21
*/
public class fileInfoPop extends PopupWindow {
    public final static int DELETE=0;
    public final static int DOWNLAOD=1;
    private Activity mContext;
    private onClick click;
    private View contentView;
    private String file_id;

    public interface onClick {
        void onClick(int num,String file_id);
    }

    public fileInfoPop(Activity mContext, String file_id, onClick click) {
        this.mContext = mContext;
        this.file_id = file_id;
       this.click = click;
        contentView = LayoutInflater.from(mContext).inflate(R.layout.filetips, null);
        initPop();
        initView();
    }

    public void initView() {
        final Button href = contentView.findViewById(R.id.filehref);
        Button deltefile = contentView.findViewById(R.id.deletefile);
        Button downfile = contentView.findViewById(R.id.downloadfile);
        Button movefile = contentView.findViewById(R.id.movefile);
        Button pass = contentView.findViewById(R.id.pass);
        HttpWorker.getFileHref(file_id, new HttpWorker.FileHrefCallbackListener() {
            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onFinish(String file_href) {
                href.setText(file_href);
            }
        });
        deltefile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("2468", file_id);
                click.onClick(DELETE,file_id);
                dismiss();
            }
        });
        downfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("24687", file_id);
                click.onClick(DOWNLAOD,file_id);
                Log.d("24687", "22");
                dismiss();
            }
        });
    }

    private void initPop() {
        this.setContentView(contentView);
        this.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setAnimationStyle(R.style.contextMenuAnim);

        View rootview = LayoutInflater.from(mContext).inflate(R.layout.activity_main, null);
        this.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);

        ColorDrawable dw = new ColorDrawable(0x00FFFFFF);
        this.setBackgroundDrawable(dw);
        backgroundAlpha(mContext, 0.5f);

        this.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
                lp.alpha = 1f;
                mContext.getWindow().setAttributes(lp);
            }
        });

    }

    public void backgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }

}
