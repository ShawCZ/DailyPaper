package com.shaw.daily.dp.login;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;

import com.shaw.daily.delegates.DailyDelegate;
import com.shaw.daily.dp.R;
import com.shaw.daily.dp.R2;
import com.shaw.daily.util.storage.DailyPreference;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.exception.BmobException;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.sms.listener.VerifySMSCodeListener;

/**
 * Created by shaw on 2017/10/8.
 */

public class LoginDelegate extends DailyDelegate {
	private static final String TAG = "LoginDelegate";
	public static final String PHONENUMBER = "PHONENUMBER";

	@BindView(R2.id.phone_number)
	AppCompatEditText mPhoneNumber = null;
	@BindView(R2.id.password)
	AppCompatEditText mPassword = null;
	@BindView(R2.id.request_password)
	AppCompatButton mRequestPassword = null;
	@BindView(R2.id.request_password_success)
	AppCompatTextView mRequestPasswordSuccess = null;
	@BindView(R2.id.login)
	AppCompatButton mLogin = null;

	@OnClick(R2.id.icon_login_back)
	void onClickLoginBack() {
		getProxyActivity().getSupportDelegate().pop();
	}

	@OnClick(R2.id.request_password)
	void onClickRequestPassword() {
		checkForm();

		BmobSMS.requestSMSCode(getContext(), mPhoneNumber.getText().toString(), "DailyPaper", new RequestSMSCodeListener() {
			@Override
			public void done(Integer smsId, BmobException ex) {
				//验证码发送成功
				if (ex == null) {
					mRequestPasswordSuccess.setVisibility(View.VISIBLE);
					//用于查询本次短信发送详情
					Log.i("bmob", "短信id：" + smsId);
				}else {
					mRequestPasswordSuccess.setVisibility(View.VISIBLE);
					mRequestPasswordSuccess.setText("发送验证码失败，请检查网络");
					mRequestPasswordSuccess.setTextColor(Color.RED);
					Log.i("bmob", "失败");
					ex.printStackTrace();
				}
			}
		});
	}

	@OnClick(R2.id.login)
	void onClickLogin() {
		checkForm();

		BmobSMS.verifySmsCode(getContext(), mPhoneNumber.getText().toString(), mPassword.getText().toString(), new VerifySMSCodeListener() {
			@Override
			public void done(BmobException ex) {
				//短信验证码已验证成功
				if (ex == null) {
					getProxyActivity().getSupportDelegate().pop();
					Log.i("bmob", "验证通过");
					DailyPreference.addPhoneNumber(PHONENUMBER,mPhoneNumber.getText().toString());
				} else {
					mRequestPasswordSuccess.setVisibility(View.VISIBLE);
					mRequestPasswordSuccess.setText("请输入正确的验证码");
					mRequestPasswordSuccess.setTextColor(Color.RED);

					Log.i("bmob", "验证失败：code =" + ex.getErrorCode() + ",msg = " + ex.getLocalizedMessage());
				}
			}
		});
	}

	@Override
	public Object setLayout() {
		return R.layout.delegate_login;
	}

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }

	private boolean checkForm(){
		mRequestPasswordSuccess.setVisibility(View.INVISIBLE);

		final String phone = mPhoneNumber.getText().toString();
		final String password = mPassword.getText().toString();

		boolean isPass = true;

		if (phone.isEmpty() || phone.length()!=11){
			mPhoneNumber.setError("手机号码格式错误");
			isPass = false;
		}else {
			mPhoneNumber.setError(null);
		}
		return isPass;
	}
}
