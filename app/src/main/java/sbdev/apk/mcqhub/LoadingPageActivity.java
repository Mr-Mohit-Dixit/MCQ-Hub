package sbdev.apk.mcqhub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.webkit.*;
import android.animation.*;
import android.view.animation.*;
import java.util.*;
import java.util.regex.*;
import java.text.*;
import org.json.*;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import java.util.Timer;
import java.util.TimerTask;
import android.os.Bundle;
import java.io.InputStream;
import android.content.Intent;
import android.net.Uri;
import android.app.Activity;
import android.content.SharedPreferences;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class LoadingPageActivity extends  AppCompatActivity  { 
	
	private Timer _timer = new Timer();
	
	private double count = 0;
	
	private LinearLayout linear1;
	private ImageView imageview1;
	private ImageView imageview2;
	private LinearLayout linear2;
	private ProgressBar progressbar1;
	private TextView textview1;
	
	private AlertDialog.Builder d;
	private TimerTask t;
	private Intent int1 = new Intent();
	private RequestNetwork rqn;
	private RequestNetwork.RequestListener _rqn_request_listener;
	private SharedPreferences sp1;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.loading_page);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		imageview2 = (ImageView) findViewById(R.id.imageview2);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		progressbar1 = (ProgressBar) findViewById(R.id.progressbar1);
		textview1 = (TextView) findViewById(R.id.textview1);
		d = new AlertDialog.Builder(this);
		rqn = new RequestNetwork(this);
		sp1 = getSharedPreferences("sp1", Activity.MODE_PRIVATE);
		
		_rqn_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				t.cancel();
				SketchwareUtil.showMessage(getApplicationContext(), "Connected✅");
				sp1.edit().putString("net_status", "connected").commit();
				SketchwareUtil.showMessage(getApplicationContext(), sp1.getString("net_status", ""));
				int1.setClass(getApplicationContext(), MainActivity.class);
				startActivity(int1);
				finish();
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				t.cancel();
				d.setCancelable(false);
				d.setTitle("⚠️ Connection Error ⚠️");
				d.setMessage("Internet is not Connected. please check connection and try again!!!");
				d.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						int1.setClass(getApplicationContext(), LoadingPageActivity.class);
						startActivity(int1);
						finish();
					}
				});
				d.create().show();
			}
		};
	}
	
	private void initializeLogic() {
		count = 1;
		t = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (count == 1) {
							textview1.setText("Loading");
						}
						if (count == 2) {
							textview1.setText("Loading.");
						}
						if (count == 3) {
							textview1.setText("Loading..");
						}
						if (count == 4) {
							textview1.setText("Loading...");
						}
						if (count == 5) {
							textview1.setText("Loading....");
							count = 0;
						}
						count++;
					}
				});
			}
		};
		_timer.scheduleAtFixedRate(t, (int)(0), (int)(100));
		rqn.startRequestNetwork(RequestNetworkController.GET, "https://www.google.com/?sa=X&ved=0ahUKEwisre_hqcjvAhXR4jgGHSmqCjoQOwgC", "A", _rqn_request_listener);
		SketchwareUtil.showMessage(getApplicationContext(), "Connecting...");
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
		
	}
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input){
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels(){
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels(){
		return getResources().getDisplayMetrics().heightPixels;
	}
	
}
