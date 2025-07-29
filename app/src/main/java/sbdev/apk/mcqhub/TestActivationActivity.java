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
import android.widget.TextView;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
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
import android.widget.AdapterView;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class TestActivationActivity extends AppCompatActivity {
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private HashMap<String, Object> map = new HashMap<>();
	private double exist_1 = 0;
	private double count1 = 0;
	private double count2 = 0;
	private double pos = 0;
	private double count = 0;
	
	private ArrayList<HashMap<String, Object>> subject_lists = new ArrayList<>();
	private ArrayList<String> spinner_semester_list = new ArrayList<>();
	private ArrayList<String> temp_subject_code_strlist = new ArrayList<>();
	private ArrayList<String> subject_code_list = new ArrayList<>();
	
	private LinearLayout linear3;
	private LinearLayout linear1;
	private TextView textview11;
	private CheckBox checkbox_practice_test;
	private CheckBox checkbox_unit_test_1;
	private CheckBox checkbox_unit_test_2;
	private CheckBox checkbox_semester_test;
	private LinearLayout linear11;
	private TextView textview2;
	private TextView department_name;
	private LinearLayout linear9;
	private LinearLayout linear10;
	private TextView textview5;
	private Spinner spinner_semester;
	private TextView textview6;
	private Spinner spinner_subjectname;
	private Button update;
	
	private Intent int1 = new Intent();
	private TimerTask t;
	private AlertDialog.Builder dialog;
	private RequestNetwork rpn;
	private RequestNetwork.RequestListener _rpn_request_listener;
	private DatabaseReference subjectlist = _firebase.getReference("subject_list");
	private ChildEventListener _subjectlist_child_listener;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.test_activation);
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
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		textview11 = (TextView) findViewById(R.id.textview11);
		checkbox_practice_test = (CheckBox) findViewById(R.id.checkbox_practice_test);
		checkbox_unit_test_1 = (CheckBox) findViewById(R.id.checkbox_unit_test_1);
		checkbox_unit_test_2 = (CheckBox) findViewById(R.id.checkbox_unit_test_2);
		checkbox_semester_test = (CheckBox) findViewById(R.id.checkbox_semester_test);
		linear11 = (LinearLayout) findViewById(R.id.linear11);
		textview2 = (TextView) findViewById(R.id.textview2);
		department_name = (TextView) findViewById(R.id.department_name);
		linear9 = (LinearLayout) findViewById(R.id.linear9);
		linear10 = (LinearLayout) findViewById(R.id.linear10);
		textview5 = (TextView) findViewById(R.id.textview5);
		spinner_semester = (Spinner) findViewById(R.id.spinner_semester);
		textview6 = (TextView) findViewById(R.id.textview6);
		spinner_subjectname = (Spinner) findViewById(R.id.spinner_subjectname);
		update = (Button) findViewById(R.id.update);
		dialog = new AlertDialog.Builder(this);
		rpn = new RequestNetwork(this);
		
		spinner_semester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				temp_subject_code_strlist.clear();
				subject_code_list.clear();
				temp_subject_code_strlist.add("Select Subject");
				subject_code_list.add("Select Subject");
				spinner_subjectname.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, temp_subject_code_strlist));
				((ArrayAdapter)spinner_subjectname.getAdapter()).notifyDataSetChanged();
				count = 0;
				if (!"Select Semester".equals(spinner_semester_list.get((int)(spinner_semester.getSelectedItemPosition())))) {
					if (!(0 == subject_lists.size())) {
						for(int _repeat105 = 0; _repeat105 < (int)(subject_lists.size()); _repeat105++) {
							if (subject_lists.get((int)count).get("semester").toString().equals(spinner_semester_list.get((int)(spinner_semester.getSelectedItemPosition())))) {
								temp_subject_code_strlist.add(subject_lists.get((int)count).get("subject_name").toString().concat(" (".concat(subject_lists.get((int)count).get("subject_code").toString().concat(")"))));
								subject_code_list.add(subject_lists.get((int)count).get("subject_code").toString());
							}
							count++;
						}
					}
					if (temp_subject_code_strlist.size() == 1) {
						SketchwareUtil.showMessage(getApplicationContext(), "Subject not available ‼️");
					}
				}else {
					SketchwareUtil.showMessage(getApplicationContext(), "Select Semester");
				}
				spinner_subjectname.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, temp_subject_code_strlist));
				((ArrayAdapter)spinner_subjectname.getAdapter()).notifyDataSetChanged();
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> _param1) {
				
			}
		});
		
		spinner_subjectname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				if (!(spinner_subjectname.getSelectedItemPosition() == 0)) {
					checkbox_practice_test.setChecked(false);
					checkbox_unit_test_1.setChecked(false);
					checkbox_unit_test_2.setChecked(false);
					checkbox_semester_test.setChecked(false);
					pos = 0;
					for(int _repeat63 = 0; _repeat63 < (int)(subject_lists.size()); _repeat63++) {
						if (subject_code_list.get((int)(spinner_subjectname.getSelectedItemPosition())).equals(subject_lists.get((int)pos).get("subject_code").toString())) {
							if (subject_lists.get((int)pos).get("practice_test_flag").toString().equals("1")) {
								checkbox_practice_test.setChecked(true);
							}
							if (subject_lists.get((int)pos).get("unit_test_1_flag").toString().equals("1")) {
								checkbox_unit_test_1.setChecked(true);
							}
							if (subject_lists.get((int)pos).get("unit_test_2_flag").toString().equals("1")) {
								checkbox_unit_test_2.setChecked(true);
							}
							if (subject_lists.get((int)pos).get("semester_test_flag").toString().equals("1")) {
								checkbox_semester_test.setChecked(true);
							}
						}
						pos++;
					}
				}else {
					SketchwareUtil.showMessage(getApplicationContext(), "Please Select Semester & Subject");
				}
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> _param1) {
				
			}
		});
		
		update.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				count2 = 0;
				if (!"Select Subject".equals(subject_code_list.get((int)(spinner_subjectname.getSelectedItemPosition())))) {
					for(int _repeat45 = 0; _repeat45 < (int)(subject_lists.size()); _repeat45++) {
						if (subject_lists.get((int)count2).get("subject_code").toString().equals(subject_code_list.get((int)(spinner_subjectname.getSelectedItemPosition())))) {
							map.clear();
							map = subject_lists.get((int)count2);
							map.remove("practice_test_flag");
							map.remove("unit_test_1_flag");
							map.remove("unit_test_2_flag");
							map.remove("semester_test_flag");
							if (checkbox_practice_test.isChecked()) {
								map.put("practice_test_flag", "1");
							}else {
								map.put("practice_test_flag", "0");
							}
							if (checkbox_unit_test_1.isChecked()) {
								map.put("unit_test_1_flag", "1");
							}else {
								map.put("unit_test_1_flag", "0");
							}
							if (checkbox_unit_test_2.isChecked()) {
								map.put("unit_test_2_flag", "1");
							}else {
								map.put("unit_test_2_flag", "0");
							}
							if (checkbox_semester_test.isChecked()) {
								map.put("semester_test_flag", "1");
							}else {
								map.put("semester_test_flag", "0");
							}
							subjectlist.child(map.get("subject_code").toString()).updateChildren(map);
							SketchwareUtil.showMessage(getApplicationContext(), "UPDATE SUCCESSFUL ✅");
						}
						count2++;
					}
				}else {
					SketchwareUtil.showMessage(getApplicationContext(), "1st Select Any Subject");
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
						int1.setClass(getApplicationContext(), TestActivationActivity.class);
						int1.putExtra("department_name", getIntent().getStringExtra("department_name"));
						startActivity(int1);
						finish();
					}
				});
				dialog.create().show();
			}
		};
		
		_subjectlist_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childValue.get("department_name").toString().equals(getIntent().getStringExtra("department_name"))) {
					exist_1 = 0;
					count1 = 0;
					for(int _repeat18 = 0; _repeat18 < (int)(subject_lists.size()); _repeat18++) {
						if (subject_lists.get((int)count1).get("subject_code").toString().equals(_childValue.get("subject_code").toString())) {
							exist_1 = 1;
						}
						count1++;
					}
					if (exist_1 == 0) {
						subject_lists.add(_childValue);
					}
				}
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childValue.get("department_name").toString().equals(getIntent().getStringExtra("department_name"))) {
					exist_1 = 0;
					count1 = 0;
					for(int _repeat17 = 0; _repeat17 < (int)(subject_lists.size()); _repeat17++) {
						if (subject_lists.get((int)count1).get("subject_code").toString().equals(_childValue.get("subject_code").toString())) {
							exist_1 = 1;
						}
						count1++;
					}
					if (exist_1 == 0) {
						subject_lists.add(_childValue);
					}
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
				if (_childValue.get("department_name").toString().equals(getIntent().getStringExtra("department_name"))) {
					exist_1 = 0;
					count1 = 0;
					for(int _repeat17 = 0; _repeat17 < (int)(subject_lists.size()); _repeat17++) {
						if (subject_lists.get((int)count1).get("subject_code").toString().equals(_childValue.get("subject_code").toString())) {
							exist_1 = 1;
						}
						count1++;
					}
					if (exist_1 == 0) {
						subject_lists.add(_childValue);
					}
				}
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		subjectlist.addChildEventListener(_subjectlist_child_listener);
	}
	
	private void initializeLogic() {
		setTitle("Test Activation");
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
		department_name.setText(getIntent().getStringExtra("department_name"));
		spinner_semester_list.add("Select Semester");
		spinner_semester_list.add("1");
		spinner_semester_list.add("2");
		spinner_semester_list.add("3");
		spinner_semester_list.add("4");
		spinner_semester_list.add("5");
		spinner_semester_list.add("6");
		spinner_semester_list.add("7");
		spinner_semester_list.add("8");
		spinner_semester.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, spinner_semester_list));
		((ArrayAdapter)spinner_semester.getAdapter()).notifyDataSetChanged();
		temp_subject_code_strlist.add("Select Subject");
		spinner_subjectname.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, temp_subject_code_strlist));
		((ArrayAdapter)spinner_subjectname.getAdapter()).notifyDataSetChanged();
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