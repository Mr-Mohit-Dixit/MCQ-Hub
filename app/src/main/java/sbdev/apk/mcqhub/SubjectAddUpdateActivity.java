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
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Button;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import android.os.Bundle;
import java.io.InputStream;
import android.content.Intent;
import android.net.Uri;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.app.Activity;
import android.content.SharedPreferences;
import java.util.Timer;
import java.util.TimerTask;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class SubjectAddUpdateActivity extends AppCompatActivity {
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private HashMap<String, Object> map = new HashMap<>();
	private double counts = 0;
	private double exist_flag = 0;
	private double ok_field = 0;
	private String old_net_status = "";
	
	private ArrayList<String> semester_list = new ArrayList<>();
	private ArrayList<String> department_list = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> subject_maplist = new ArrayList<>();
	
	private LinearLayout linear1;
	private ImageView imageview1;
	private LinearLayout linear4;
	private LinearLayout linear5;
	private LinearLayout linear8;
	private LinearLayout linear9;
	private LinearLayout linear10;
	private TextView textview_name;
	private EditText subject_code;
	private TextView textview_contact;
	private EditText subject_name;
	private TextView textviwe_;
	private Spinner spinner_semester;
	private TextView textview9;
	private Spinner spinner_department;
	private Button add_update;
	
	private DatabaseReference subjectlist = _firebase.getReference("subject_list");
	private ChildEventListener _subjectlist_child_listener;
	private Intent int1 = new Intent();
	private AlertDialog.Builder d;
	private SharedPreferences sp1;
	private AlertDialog.Builder dialog;
	private RequestNetwork rpn;
	private RequestNetwork.RequestListener _rpn_request_listener;
	private TimerTask t;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.subject_add_update);
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
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		linear8 = (LinearLayout) findViewById(R.id.linear8);
		linear9 = (LinearLayout) findViewById(R.id.linear9);
		linear10 = (LinearLayout) findViewById(R.id.linear10);
		textview_name = (TextView) findViewById(R.id.textview_name);
		subject_code = (EditText) findViewById(R.id.subject_code);
		textview_contact = (TextView) findViewById(R.id.textview_contact);
		subject_name = (EditText) findViewById(R.id.subject_name);
		textviwe_ = (TextView) findViewById(R.id.textviwe_);
		spinner_semester = (Spinner) findViewById(R.id.spinner_semester);
		textview9 = (TextView) findViewById(R.id.textview9);
		spinner_department = (Spinner) findViewById(R.id.spinner_department);
		add_update = (Button) findViewById(R.id.add_update);
		d = new AlertDialog.Builder(this);
		sp1 = getSharedPreferences("sp1", Activity.MODE_PRIVATE);
		dialog = new AlertDialog.Builder(this);
		rpn = new RequestNetwork(this);
		
		add_update.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				map = new HashMap<>();
				ok_field = 0;
				if (subject_code.getText().toString().equals("")) {
					SketchwareUtil.showMessage(getApplicationContext(), "Subject Code Is Empty⚠️");
				}
else {
					counts = 0;
					exist_flag = 0;
					if (!(subject_maplist.size() == 0)) {
						for(int _repeat20 = 0; _repeat20 < (int)(subject_maplist.size()); _repeat20++) {
							if (subject_code.getText().toString().equals(subject_maplist.get((int)counts).get("subject_code").toString())) {
								SketchwareUtil.showMessage(getApplicationContext(), "Subject Code is Already exist🚫 Please check and Try again⚠️");
								exist_flag = 1;
								break;
							}
							counts++;
						}
					}
else {
						
					}
					if (exist_flag == 0) {
						map.put("subject_code", subject_code.getText().toString());
						ok_field++;
					}
				}
				if (subject_code.getText().toString().equals("")) {
					SketchwareUtil.showMessage(getApplicationContext(), "Subject Name Is Empty⚠️");
				}
else {
					if (exist_flag == 0) {
						map.put("subject_name", subject_name.getText().toString());
						ok_field++;
					}
				}
				if (semester_list.get((int)(spinner_semester.getSelectedItemPosition())).equals("Select Semester")) {
					SketchwareUtil.showMessage(getApplicationContext(), "Semester is not Selected⚠️");
				}
else {
					if (exist_flag == 0) {
						map.put("semester", semester_list.get((int)(spinner_semester.getSelectedItemPosition())));
						ok_field++;
					}
				}
				if (department_list.get((int)(spinner_department.getSelectedItemPosition())).equals("Select Department")) {
					SketchwareUtil.showMessage(getApplicationContext(), "Department is not Selected⚠️");
				}
else {
					if (exist_flag == 0) {
						map.put("department_name", department_list.get((int)(spinner_department.getSelectedItemPosition())));
						ok_field++;
					}
				}
				if (ok_field == 4) {
					map.put("practice_test_flag", "0");
					map.put("unit_test_1_flag", "0");
					map.put("unit_test_2_flag", "0");
					map.put("semester_test_flag", "0");
					subjectlist.child(subject_code.getText().toString()).updateChildren(map);
					SketchwareUtil.showMessage(getApplicationContext(), "Subject Added");
					subject_code.setText("");
					subject_name.setText("");
					spinner_semester.setSelection((int)(0));
					spinner_department.setSelection((int)(0));
					map.clear();
					subject_maplist.clear();
				}
else {
					SketchwareUtil.showMessage(getApplicationContext(), "Empty or Unselected Field please re-check and try again⚠️");
				}
			}
		});
		
		_subjectlist_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				subject_maplist.clear();
				subjectlist.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						subject_maplist = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								subject_maplist.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				subject_maplist.clear();
				subjectlist.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						subject_maplist = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								subject_maplist.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
			}
			
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				subject_maplist.clear();
				subjectlist.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						subject_maplist = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								subject_maplist.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		subjectlist.addChildEventListener(_subjectlist_child_listener);
		
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
						int1.setClass(getApplicationContext(), SubjectAddUpdateActivity.class);
						startActivity(int1);
						finish();
					}
				});
				dialog.create().show();
			}
		};
	}
	
	private void initializeLogic() {
		setTitle("Add New Subject");
		counts = 1;
		semester_list.clear();
		department_list.clear();
		semester_list.add("Select Semester");
		for(int _repeat10 = 0; _repeat10 < (int)(8); _repeat10++) {
			semester_list.add(String.valueOf((long)(counts)));
			counts++;
		}
		spinner_semester.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, semester_list));
		((ArrayAdapter)spinner_semester.getAdapter()).notifyDataSetChanged();
		department_list.add("Select Department");
		department_list.add("Computer Engineering");
		department_list.add("Mechanical Engineering");
		department_list.add("Automobile Engineering");
		department_list.add("E & TC Engineering");
		spinner_department.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, department_list));
		((ArrayAdapter)spinner_department.getAdapter()).notifyDataSetChanged();
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
		int1.setClass(getApplicationContext(), SubjectsListActivity.class);
		startActivity(int1);
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
