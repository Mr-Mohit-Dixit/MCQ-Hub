package sbdev.apk.mcqhub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.AppBarLayout;
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
import java.util.HashMap;
import java.util.ArrayList;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.os.Bundle;
import java.io.InputStream;
import android.content.Intent;
import android.net.Uri;
import java.util.Timer;
import java.util.TimerTask;
import android.app.AlertDialog;
import android.content.DialogInterface;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class StudentMyProfileActivity extends AppCompatActivity {
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private HashMap<String, Object> map = new HashMap<>();
	private String current_password_mapdata = "";
	
	private ArrayList<HashMap<String, Object>> templist = new ArrayList<>();
	
	private LinearLayout linear1;
	private ImageView imageview1;
	private TextView text1;
	private LinearLayout linear4;
	private LinearLayout linear5;
	private LinearLayout linear6;
	private LinearLayout linear7;
	private LinearLayout linear10;
	private TextView textview_idno;
	private LinearLayout linear13;
	private LinearLayout linear14;
	private LinearLayout linear15;
	private LinearLayout linear16;
	private TextView textview4;
	private TextView textview_name;
	private TextView textview_contact;
	private TextView textview_email;
	private TextView textview_department;
	private TextView textview1;
	private EditText current_password;
	private TextView textview2;
	private EditText new_password;
	private TextView textview3;
	private EditText re_new_password;
	private Button update;
	
	private Intent int1 = new Intent();
	private TimerTask t;
	private AlertDialog.Builder dialog;
	private RequestNetwork rpn;
	private RequestNetwork.RequestListener _rpn_request_listener;
	private DatabaseReference allusers = _firebase.getReference("all_users");
	private ChildEventListener _allusers_child_listener;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.student_myprofile);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		_app_bar = (AppBarLayout) findViewById(R.id._app_bar);
		_coordinator = (CoordinatorLayout) findViewById(R.id._coordinator);
		_toolbar = (Toolbar) findViewById(R.id._toolbar);
		setSupportActionBar(_toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) {
				onBackPressed();
			}
		});
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		text1 = (TextView) findViewById(R.id.text1);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		linear6 = (LinearLayout) findViewById(R.id.linear6);
		linear7 = (LinearLayout) findViewById(R.id.linear7);
		linear10 = (LinearLayout) findViewById(R.id.linear10);
		textview_idno = (TextView) findViewById(R.id.textview_idno);
		linear13 = (LinearLayout) findViewById(R.id.linear13);
		linear14 = (LinearLayout) findViewById(R.id.linear14);
		linear15 = (LinearLayout) findViewById(R.id.linear15);
		linear16 = (LinearLayout) findViewById(R.id.linear16);
		textview4 = (TextView) findViewById(R.id.textview4);
		textview_name = (TextView) findViewById(R.id.textview_name);
		textview_contact = (TextView) findViewById(R.id.textview_contact);
		textview_email = (TextView) findViewById(R.id.textview_email);
		textview_department = (TextView) findViewById(R.id.textview_department);
		textview1 = (TextView) findViewById(R.id.textview1);
		current_password = (EditText) findViewById(R.id.current_password);
		textview2 = (TextView) findViewById(R.id.textview2);
		new_password = (EditText) findViewById(R.id.new_password);
		textview3 = (TextView) findViewById(R.id.textview3);
		re_new_password = (EditText) findViewById(R.id.re_new_password);
		update = (Button) findViewById(R.id.update);
		dialog = new AlertDialog.Builder(this);
		rpn = new RequestNetwork(this);
		
		update.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (!("".equals(current_password.getText().toString()) || ("".equals(new_password.getText().toString()) || "".equals(re_new_password.getText().toString())))) {
					if (current_password_mapdata.equals(current_password.getText().toString())) {
						if (!current_password.getText().toString().equals(new_password.getText().toString())) {
							if (new_password.getText().toString().equals(re_new_password.getText().toString())) {
								if ((8 < new_password.getText().toString().length()) || (8 == new_password.getText().toString().length())) {
									if (13 > new_password.getText().toString().length()) {
										map.remove("password");
										map.put("password", new_password.getText().toString());
										allusers.child(map.get("idno").toString()).updateChildren(map);
										current_password.setText("");
										new_password.setText("");
										re_new_password.setText("");
										SketchwareUtil.showMessage(getApplicationContext(), "Password update Successfully ✅");
									}
else {
										SketchwareUtil.showMessage(getApplicationContext(), "Password is must he less than 12 character's⚠️");
									}
								}
else {
									SketchwareUtil.showMessage(getApplicationContext(), "Password must be atleast 8 character's⚠️");
								}
							}
else {
								SketchwareUtil.showMessage(getApplicationContext(), "New Password & Re-New Password are not matching ‼️");
							}
						}
else {
							SketchwareUtil.showMessage(getApplicationContext(), "Current Password can't set as new password ‼️");
						}
					}
else {
						SketchwareUtil.showMessage(getApplicationContext(), "Current Password is Wrong⚠️");
					}
				}
else {
					SketchwareUtil.showMessage(getApplicationContext(), "Field is empty");
				}
			}
		});
		
		_rpn_request_listener = new RequestNetwork.RequestListener() {
			@Override
			public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
				final String _tag = _param1;
				final String _response = _param2;
				final HashMap<String, Object> _responseHeaders = _param3;
				
			}
			
			@Override
			public void onErrorResponse(String _param1, String _param2) {
				final String _tag = _param1;
				final String _message = _param2;
				t.cancel();
				
dialog.setCancelable(false);
				dialog.setTitle("⚠️ Connection Error ⚠️");
				dialog.setMessage("Internet is not Connected. please check connection and try again!!!");
				dialog.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						
					}
				});
				dialog.create().show();
			}
		};
		
		_allusers_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childValue.get("idno").toString().equals(getIntent().getStringExtra("idno"))) {
					map.clear();
					templist.clear();
					templist.add(_childValue);
					map = templist.get((int)0);
					current_password_mapdata = _childValue.get("password").toString();
					textview_idno.setText("ID No : ".concat(_childValue.get("idno").toString()));
					textview_name.setText("Name            : ".concat(_childValue.get("name").toString()));
					textview_contact.setText("Contact No   : ".concat(_childValue.get("contactno").toString()));
					textview_email.setText("E-mail           : ".concat(_childValue.get("email").toString()));
					textview_department.setText("Department : ".concat(_childValue.get("department_name").toString()));
				}
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childValue.get("idno").toString().equals(getIntent().getStringExtra("idno"))) {
					map.clear();
					templist.clear();
					templist.add(_childValue);
					map = templist.get((int)0);
					current_password_mapdata = _childValue.get("password").toString();
					textview_idno.setText("ID No : ".concat(_childValue.get("idno").toString()));
					textview_name.setText("Name            : ".concat(_childValue.get("name").toString()));
					textview_contact.setText("Contact No   : ".concat(_childValue.get("contactno").toString()));
					textview_email.setText("E-mail           : ".concat(_childValue.get("email").toString()));
					textview_department.setText("Department : ".concat(_childValue.get("department_name").toString()));
				}
			}
			
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childValue.get("idno").toString().equals(getIntent().getStringExtra("idno"))) {
					map.clear();
					templist.clear();
					templist.add(_childValue);
					map = templist.get((int)0);
					current_password_mapdata = _childValue.get("password").toString();
					textview_idno.setText("ID No : ".concat(_childValue.get("idno").toString()));
					textview_name.setText("Name            : ".concat(_childValue.get("name").toString()));
					textview_contact.setText("Contact No   : ".concat(_childValue.get("contactno").toString()));
					textview_email.setText("E-mail           : ".concat(_childValue.get("email").toString()));
					textview_department.setText("Department : ".concat(_childValue.get("department_name").toString()));
				}
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		allusers.addChildEventListener(_allusers_child_listener);
	}
	
	private void initializeLogic() {
		t = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						rpn.startRequestNetwork(RequestNetworkController.GET, "https://www.google.com/?sa=X&ved=0ahUKEwisre_hqcjvAhXR4jgGHSmqCjoQOwgC", "A", _rpn_request_listener);
					}
				});
			}
		};
		_timer.scheduleAtFixedRate(t, (int)(0), (int)(1000));
		setTitle("My Profile");
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
		finish();
	}
	
	@Override
	public void onStop() {
		super.onStop();
		t.cancel();
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
	public float getDip(int _input) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels() {
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels() {
		return getResources().getDisplayMetrics().heightPixels;
	}
}
