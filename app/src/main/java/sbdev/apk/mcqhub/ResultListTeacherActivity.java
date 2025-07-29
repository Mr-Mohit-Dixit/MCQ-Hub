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
import android.widget.HorizontalScrollView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.BaseAdapter;
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
import android.text.Editable;
import android.text.TextWatcher;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class ResultListTeacherActivity extends AppCompatActivity {
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private double exist_1 = 0;
	private double count1 = 0;
	private HashMap<String, Object> map = new HashMap<>();
	private double count2 = 0;
	private double count3 = 0;
	private HashMap<String, Object> temp_map = new HashMap<>();
	private String str_map = "";
	private double map_length = 0;
	private double search_pos = 0;
	private String value1 = "";
	private double exist_2 = 0;
	private double count_2 = 0;
	private String map_temp = "";
	
	private ArrayList<HashMap<String, Object>> subject_lists = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> results_maplist = new ArrayList<>();
	private ArrayList<String> temp_subject_code_strlist = new ArrayList<>();
	private ArrayList<String> spinner_semester_list = new ArrayList<>();
	private ArrayList<String> test_liststr = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> temp_subjects_list = new ArrayList<>();
	private ArrayList<String> subject_code_list = new ArrayList<>();
	
	private LinearLayout linear3;
	private LinearLayout linear6;
	private HorizontalScrollView hscroll5;
	private LinearLayout linear15;
	private Spinner spinner_subject_name;
	private EditText search_text;
	private TextView textview12;
	private LinearLayout linear16;
	private Spinner spinner_select_test;
	private Spinner spinner_semester;
	private LinearLayout linear5;
	private LinearLayout linear1;
	private ListView listview1;
	private TextView idno;
	private TextView textview2;
	private TextView name;
	private TextView textview9;
	private TextView textview10;
	private TextView textview13;
	private TextView textview16;
	
	private Intent int1 = new Intent();
	private TimerTask t;
	private AlertDialog.Builder dialog;
	private RequestNetwork rpn;
	private RequestNetwork.RequestListener _rpn_request_listener;
	private DatabaseReference subjectlist = _firebase.getReference("subject_list");
	private ChildEventListener _subjectlist_child_listener;
	private DatabaseReference results_list = _firebase.getReference("results");
	private ChildEventListener _results_list_child_listener;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.result_list_teacher);
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
		linear6 = (LinearLayout) findViewById(R.id.linear6);
		hscroll5 = (HorizontalScrollView) findViewById(R.id.hscroll5);
		linear15 = (LinearLayout) findViewById(R.id.linear15);
		spinner_subject_name = (Spinner) findViewById(R.id.spinner_subject_name);
		search_text = (EditText) findViewById(R.id.search_text);
		textview12 = (TextView) findViewById(R.id.textview12);
		linear16 = (LinearLayout) findViewById(R.id.linear16);
		spinner_select_test = (Spinner) findViewById(R.id.spinner_select_test);
		spinner_semester = (Spinner) findViewById(R.id.spinner_semester);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		listview1 = (ListView) findViewById(R.id.listview1);
		idno = (TextView) findViewById(R.id.idno);
		textview2 = (TextView) findViewById(R.id.textview2);
		name = (TextView) findViewById(R.id.name);
		textview9 = (TextView) findViewById(R.id.textview9);
		textview10 = (TextView) findViewById(R.id.textview10);
		textview13 = (TextView) findViewById(R.id.textview13);
		textview16 = (TextView) findViewById(R.id.textview16);
		dialog = new AlertDialog.Builder(this);
		rpn = new RequestNetwork(this);
		
		spinner_subject_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				results_maplist.clear();
				listview1.setAdapter(new Listview1Adapter(results_maplist));
				((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
				if (!(_position == 0)) {
					results_list.addChildEventListener(_results_list_child_listener);
				}else {
					SketchwareUtil.showMessage(getApplicationContext(), "Please Select the spacific subject to see Result's⚠️");
				}
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> _param1) {
				
			}
		});
		
		search_text.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				if (!(0 == map_temp.length())) {
					results_maplist.clear();
					results_maplist = new Gson().fromJson(map_temp, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
					map_length = results_maplist.size();
					search_pos = map_length - 1;
					for(int _repeat23 = 0; _repeat23 < (int)(map_length); _repeat23++) {
						value1 = results_maplist.get((int)search_pos).get("name").toString();
						if (!(_charSeq.length() > value1.length()) && value1.toLowerCase().contains(_charSeq.toLowerCase())) {
							
						}else {
							results_maplist.remove((int)(search_pos));
						}
						search_pos--;
					}
					listview1.setAdapter(new Listview1Adapter(results_maplist));
					((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		spinner_select_test.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				if (_position == 0) {
					spinner_semester.setEnabled(false);
					spinner_subject_name.setEnabled(false);
				}else {
					spinner_semester.setEnabled(true);
					spinner_subject_name.setEnabled(true);
				}
				spinner_subject_name.setSelection((int)(0));
				spinner_semester.setSelection((int)(0));
				results_maplist.clear();
				listview1.setAdapter(new Listview1Adapter(results_maplist));
				((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> _param1) {
				
			}
		});
		
		spinner_semester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				temp_subjects_list.clear();
				temp_subject_code_strlist.clear();
				subject_code_list.clear();
				temp_subject_code_strlist.add("Select Subject");
				subject_code_list.add("select");
				if (!(_position == 0)) {
					count2 = 0;
					for(int _repeat18 = 0; _repeat18 < (int)(subject_lists.size()); _repeat18++) {
						if (subject_lists.get((int)count2).get("semester").toString().equals(String.valueOf((long)(_position)))) {
							temp_subject_code_strlist.add(subject_lists.get((int)count2).get("subject_name").toString().concat(" (".concat(subject_lists.get((int)count2).get("subject_code").toString().concat(")"))));
							subject_code_list.add(subject_lists.get((int)count2).get("subject_code").toString());
						}
						count2++;
					}
				}else {
					SketchwareUtil.showMessage(getApplicationContext(), "Please Select Subject⚠️");
				}
				spinner_subject_name.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, temp_subject_code_strlist));
				((ArrayAdapter)spinner_subject_name.getAdapter()).notifyDataSetChanged();
				results_maplist.clear();
				spinner_subject_name.setSelection((int)(0));
				listview1.setAdapter(new Listview1Adapter(results_maplist));
				((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> _param1) {
				
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
						int1.setClass(getApplicationContext(), ResultListTeacherActivity.class);
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
		
		_results_list_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (!(spinner_select_test.getSelectedItemPosition() == 0)) {
					if (subject_code_list.get((int)(spinner_subject_name.getSelectedItemPosition())).equals(_childValue.get("subject_code").toString()) && test_liststr.get((int)(spinner_select_test.getSelectedItemPosition())).equals(_childValue.get("test_type").toString())) {
						exist_2 = 0;
						count_2 = 0;
						for(int _repeat61 = 0; _repeat61 < (int)(results_maplist.size()); _repeat61++) {
							if (results_maplist.get((int)count_2).get("key").toString().equals(_childValue.get("key").toString())) {
								exist_2 = 1;
							}
							count_2++;
						}
						if (exist_2 == 0) {
							results_maplist.add(_childValue);
						}
					}
					map_temp = new Gson().toJson(results_maplist);
					listview1.setAdapter(new Listview1Adapter(results_maplist));
					((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
				}else {
					
				}
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (!(spinner_select_test.getSelectedItemPosition() == 0)) {
					if (subject_code_list.get((int)(spinner_subject_name.getSelectedItemPosition())).equals(_childValue.get("subject_code").toString()) && test_liststr.get((int)(spinner_select_test.getSelectedItemPosition())).equals(_childValue.get("test_type").toString())) {
						exist_2 = 0;
						count_2 = 0;
						for(int _repeat31 = 0; _repeat31 < (int)(results_maplist.size()); _repeat31++) {
							if (results_maplist.get((int)count_2).get("key").toString().equals(_childValue.get("key").toString())) {
								exist_2 = 1;
							}
							count_2++;
						}
						if (exist_2 == 0) {
							results_maplist.add(_childValue);
						}
					}
					map_temp = new Gson().toJson(results_maplist);
					listview1.setAdapter(new Listview1Adapter(results_maplist));
					((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
				}else {
					
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
				if (!(spinner_select_test.getSelectedItemPosition() == 0)) {
					if (subject_code_list.get((int)(spinner_subject_name.getSelectedItemPosition())).equals(_childValue.get("subject_code").toString()) && test_liststr.get((int)(spinner_select_test.getSelectedItemPosition())).equals(_childValue.get("test_type").toString())) {
						exist_2 = 0;
						count_2 = 0;
						for(int _repeat31 = 0; _repeat31 < (int)(results_maplist.size()); _repeat31++) {
							if (results_maplist.get((int)count_2).get("key").toString().equals(_childValue.get("key").toString())) {
								exist_2 = 1;
							}
							count_2++;
						}
						if (exist_2 == 0) {
							results_maplist.add(_childValue);
						}
					}
					map_temp = new Gson().toJson(results_maplist);
					listview1.setAdapter(new Listview1Adapter(results_maplist));
					((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
				}else {
					
				}
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		results_list.addChildEventListener(_results_list_child_listener);
	}
	
	private void initializeLogic() {
		setTitle("Results List");
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
		temp_subject_code_strlist.clear();
		spinner_semester_list.clear();
		test_liststr.clear();
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
		spinner_subject_name.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, temp_subject_code_strlist));
		((ArrayAdapter)spinner_subject_name.getAdapter()).notifyDataSetChanged();
		test_liststr.add("Select Test");
		test_liststr.add("Unit Test 1");
		test_liststr.add("Unit Test 2");
		test_liststr.add("Semester Test");
		spinner_select_test.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, test_liststr));
		((ArrayAdapter)spinner_select_test.getAdapter()).notifyDataSetChanged();
		spinner_semester.setEnabled(false);
		spinner_subject_name.setEnabled(false);
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
	public void onStop() {
		super.onStop();
		t.cancel();
	}
	
	@Override
	public void onBackPressed() {
		finish();
	}
	public class Listview1Adapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public Listview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public int getCount() {
			return _data.size();
		}
		
		@Override
		public HashMap<String, Object> getItem(int _index) {
			return _data.get(_index);
		}
		
		@Override
		public long getItemId(int _index) {
			return _index;
		}
		@Override
		public View getView(final int _position, View _v, ViewGroup _container) {
			LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _view = _v;
			if (_view == null) {
				_view = _inflater.inflate(R.layout.subject_clist, null);
			}
			
			final LinearLayout linear7 = (LinearLayout) _view.findViewById(R.id.linear7);
			final TextView subject_code = (TextView) _view.findViewById(R.id.subject_code);
			final TextView textview2 = (TextView) _view.findViewById(R.id.textview2);
			final TextView subject_name = (TextView) _view.findViewById(R.id.subject_name);
			final TextView textview4 = (TextView) _view.findViewById(R.id.textview4);
			final TextView semester = (TextView) _view.findViewById(R.id.semester);
			final TextView textview6 = (TextView) _view.findViewById(R.id.textview6);
			final TextView department_name = (TextView) _view.findViewById(R.id.department_name);
			
			subject_code.setText(_data.get((int)_position).get("idno").toString());
			subject_name.setText(_data.get((int)_position).get("name").toString());
			semester.setText(_data.get((int)_position).get("obtain_marks").toString().concat("/".concat(_data.get((int)_position).get("total_marks").toString())));
			department_name.setText(_data.get((int)_position).get("time").toString());
			
			return _view;
		}
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