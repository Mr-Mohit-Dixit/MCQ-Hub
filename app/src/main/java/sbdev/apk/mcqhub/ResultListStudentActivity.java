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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
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


public class ResultListStudentActivity extends AppCompatActivity {
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private double count1 = 0;
	private HashMap<String, Object> temp_map = new HashMap<>();
	private String map_temp = "";
	private double map_length = 0;
	private double search_pos = 0;
	private String value1 = "";
	private double exist_1 = 0;
	
	private ArrayList<String> test_type = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> results_maplist = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> temp_results_maplist = new ArrayList<>();
	
	private LinearLayout linear3;
	private LinearLayout linear6;
	private HorizontalScrollView hscroll5;
	private LinearLayout linear15;
	private EditText search_text;
	private TextView textview12;
	private LinearLayout linear16;
	private Spinner spinner_select_test;
	private LinearLayout linear5;
	private LinearLayout linear1;
	private ListView listview1;
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
	private DatabaseReference results_list = _firebase.getReference("results");
	private ChildEventListener _results_list_child_listener;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.result_list_student);
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
		search_text = (EditText) findViewById(R.id.search_text);
		textview12 = (TextView) findViewById(R.id.textview12);
		linear16 = (LinearLayout) findViewById(R.id.linear16);
		spinner_select_test = (Spinner) findViewById(R.id.spinner_select_test);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		listview1 = (ListView) findViewById(R.id.listview1);
		name = (TextView) findViewById(R.id.name);
		textview9 = (TextView) findViewById(R.id.textview9);
		textview10 = (TextView) findViewById(R.id.textview10);
		textview13 = (TextView) findViewById(R.id.textview13);
		textview16 = (TextView) findViewById(R.id.textview16);
		dialog = new AlertDialog.Builder(this);
		rpn = new RequestNetwork(this);
		
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
						value1 = results_maplist.get((int)search_pos).get("subject(code)").toString();
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
				results_maplist.clear();
				listview1.setAdapter(new Listview1Adapter(results_maplist));
				((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
				if (!(spinner_select_test.getSelectedItemPosition() == 0)) {
					results_list.addChildEventListener(_results_list_child_listener);
				}else {
					SketchwareUtil.showMessage(getApplicationContext(), "⚠️Please 1st Select Test to see Result⚠️");
					map_temp = "";
				}
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
						int1.setClass(getApplicationContext(), MainActivity.class);
						startActivity(int1);
						finish();
					}
				});
				dialog.create().show();
			}
		};
		
		_results_list_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (!(spinner_select_test.getSelectedItemPosition() == 0)) {
					if (getIntent().getStringExtra("idno").equals(_childValue.get("idno").toString()) && test_type.get((int)(spinner_select_test.getSelectedItemPosition())).equals(_childValue.get("test_type").toString())) {
						exist_1 = 0;
						count1 = 0;
						for(int _repeat44 = 0; _repeat44 < (int)(results_maplist.size()); _repeat44++) {
							if (results_maplist.get((int)count1).get("key").toString().equals(_childValue.get("key").toString())) {
								exist_1 = 1;
							}
							count1++;
						}
						if (exist_1 == 0) {
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
					if (getIntent().getStringExtra("idno").equals(_childValue.get("idno").toString()) && test_type.get((int)(spinner_select_test.getSelectedItemPosition())).equals(_childValue.get("test_type").toString())) {
						exist_1 = 0;
						count1 = 0;
						for(int _repeat29 = 0; _repeat29 < (int)(results_maplist.size()); _repeat29++) {
							if (results_maplist.get((int)count1).get("key").toString().equals(_childValue.get("key").toString())) {
								exist_1 = 1;
							}
							count1++;
						}
						if (exist_1 == 0) {
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
		setTitle("Result List");
		test_type.clear();
		test_type.add("Select Test");
		test_type.add("Practice Test");
		test_type.add("Unit Test 1");
		test_type.add("Unit Test 2");
		test_type.add("Semester Test");
		spinner_select_test.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, test_type));
		((ArrayAdapter)spinner_select_test.getAdapter()).notifyDataSetChanged();
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		switch (_requestCode) {
			
			default:
			break;
		}
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
				_view = _inflater.inflate(R.layout.teacher_list1, null);
			}
			
			final LinearLayout linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
			final TextView idno = (TextView) _view.findViewById(R.id.idno);
			final TextView textview2 = (TextView) _view.findViewById(R.id.textview2);
			final TextView name = (TextView) _view.findViewById(R.id.name);
			final TextView textview4 = (TextView) _view.findViewById(R.id.textview4);
			final TextView subject_name = (TextView) _view.findViewById(R.id.subject_name);
			
			idno.setText(_data.get((int)_position).get("subject(code)").toString());
			name.setText(_data.get((int)_position).get("obtain_marks").toString().concat("/").concat(_data.get((int)_position).get("total_marks").toString()));
			subject_name.setText(_data.get((int)_position).get("time").toString());
			
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