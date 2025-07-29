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
import java.util.ArrayList;
import java.util.HashMap;
import android.widget.LinearLayout;
import android.widget.HorizontalScrollView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
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


public class AllQuestionsListActivity extends AppCompatActivity {
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private String msg = "";
	private String map_temp = "";
	private double map_length = 0;
	private double search_pos = 0;
	private String value1 = "";
	private String semester_key = "";
	private String subject_keyname = "";
	private double sem_sub_level = 0;
	
	private ArrayList<HashMap<String, Object>> allmcq_maplist = new ArrayList<>();
	
	private LinearLayout linear3;
	private LinearLayout linear6;
	private HorizontalScrollView hscroll5;
	private EditText search_text;
	private LinearLayout linear5;
	private LinearLayout linear1;
	private ListView listview1;
	private TextView subject_code;
	private TextView textview2;
	private TextView name;
	
	private Intent int1 = new Intent();
	private TimerTask t;
	private AlertDialog.Builder dialog;
	private RequestNetwork rpn;
	private RequestNetwork.RequestListener _rpn_request_listener;
	private DatabaseReference allmcq = _firebase.getReference("All MCQ");
	private ChildEventListener _allmcq_child_listener;
	private AlertDialog.Builder d;
	private AlertDialog.Builder d1;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.all_questions_list);
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
		search_text = (EditText) findViewById(R.id.search_text);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		listview1 = (ListView) findViewById(R.id.listview1);
		subject_code = (TextView) findViewById(R.id.subject_code);
		textview2 = (TextView) findViewById(R.id.textview2);
		name = (TextView) findViewById(R.id.name);
		dialog = new AlertDialog.Builder(this);
		rpn = new RequestNetwork(this);
		d = new AlertDialog.Builder(this);
		d1 = new AlertDialog.Builder(this);
		
		search_text.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				if (!(0 == map_temp.length())) {
					allmcq_maplist.clear();
					allmcq_maplist = new Gson().fromJson(map_temp, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
					map_length = allmcq_maplist.size();
					search_pos = map_length - 1;
					for(int _repeat18 = 0; _repeat18 < (int)(map_length); _repeat18++) {
						value1 = allmcq_maplist.get((int)search_pos).get("question").toString();
						if (!(_charSeq.length() > value1.length()) && value1.toLowerCase().contains(_charSeq.toLowerCase())) {
							
						}else {
							allmcq_maplist.remove((int)(search_pos));
						}
						search_pos--;
					}
					listview1.setAdapter(new Listview1Adapter(allmcq_maplist));
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
		
		listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				msg = "";
				msg = "".concat("U. Q. No : ").concat(allmcq_maplist.get((int)_position).get("uqno").toString().concat("\n"));
				msg = msg.concat("Question : ").concat(allmcq_maplist.get((int)_position).get("question").toString().concat("\n"));
				msg = msg.concat("Option 1 : ").concat(allmcq_maplist.get((int)_position).get("op1").toString().concat("\n"));
				msg = msg.concat("Option 2 : ").concat(allmcq_maplist.get((int)_position).get("op2").toString().concat("\n"));
				msg = msg.concat("Option 3 : ").concat(allmcq_maplist.get((int)_position).get("op3").toString().concat("\n"));
				msg = msg.concat("Option 4 : ").concat(allmcq_maplist.get((int)_position).get("op4").toString().concat("\n"));
				msg = msg.concat("Answer : ").concat("Option ".concat(allmcq_maplist.get((int)_position).get("ans").toString()).concat("\n"));
				if ("All Subject's".equals(subject_keyname)) {
					msg = msg.concat("Subject : ").concat(allmcq_maplist.get((int)_position).get("subject_name").toString().concat(" (".concat(allmcq_maplist.get((int)_position).get("subject_code").toString())).concat(")\n"));
				}
				msg = msg.concat("MCQ Added by : ").concat(allmcq_maplist.get((int)_position).get("added_by").toString().concat("\n"));
				if ("on".equals(getIntent().getStringExtra("wrong"))) {
					msg = msg.concat("Reported by : ").concat(allmcq_maplist.get((int)_position).get("reported_by").toString().concat("\n"));
					msg = msg.concat("Remark : ").concat(allmcq_maplist.get((int)_position).get("remark").toString().concat("\n"));
				}
				msg = msg.concat("\nWhat to do?");
				d.setTitle("Question Details");
				d.setMessage(msg);
				d.setPositiveButton("Update", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						int1.putExtra("uqno", allmcq_maplist.get((int)_position).get("uqno").toString());
						int1.putExtra("wrong", getIntent().getStringExtra("wrong"));
						int1.putExtra("action", "update");
						int1.putExtra("semester", getIntent().getStringExtra("semester"));
						int1.putExtra("subject", getIntent().getStringExtra("subject"));
						int1.putExtra("idno", getIntent().getStringExtra("idno"));
						int1.putExtra("name", getIntent().getStringExtra("name"));
						int1.putExtra("department", getIntent().getStringExtra("department"));
						int1.setClass(getApplicationContext(), AddUpdateQuestionsActivity.class);
						startActivity(int1);
						finish();
					}
				});
				d.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						d1.setTitle("⚠️ Alert ⚠️");
						d1.setMessage("Are you sure ?");
						d1.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface _dialog, int _which) {
								allmcq.child(allmcq_maplist.get((int)_position).get("uqno").toString()).removeValue();
								SketchwareUtil.showMessage(getApplicationContext(), "Question is Deleted");
								int1.putExtra("wrong", getIntent().getStringExtra("wrong"));
								int1.putExtra("semester", getIntent().getStringExtra("semester"));
								int1.putExtra("subject", getIntent().getStringExtra("subject"));
								int1.putExtra("idno", getIntent().getStringExtra("idno"));
								int1.putExtra("name", getIntent().getStringExtra("name"));
								int1.putExtra("department", getIntent().getStringExtra("department"));
								int1.setClass(getApplicationContext(), AllQuestionsListActivity.class);
								startActivity(int1);
								finish();
							}
						});
						d1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface _dialog, int _which) {
								
							}
						});
						d1.create().show();
					}
				});
				d.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						
					}
				});
				d.create().show();
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
						int1.putExtra("wrong", getIntent().getStringExtra("wrong"));
						int1.putExtra("semester", getIntent().getStringExtra("semester"));
						int1.putExtra("subject", getIntent().getStringExtra("subject"));
						int1.putExtra("idno", getIntent().getStringExtra("idno"));
						int1.putExtra("name", getIntent().getStringExtra("name"));
						int1.putExtra("department", getIntent().getStringExtra("department"));
						int1.setClass(getApplicationContext(), AllQuestionsListActivity.class);
						startActivity(int1);
						finish();
					}
				});
				dialog.create().show();
			}
		};
		
		_allmcq_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childValue.get("subject_code").toString().equals(getIntent().getStringExtra("subject"))) {
					if (_childValue.get("wrong_flag").toString().equals(getIntent().getStringExtra("wrong"))) {
						allmcq_maplist.add(_childValue);
					}
				}
				map_temp = new Gson().toJson(allmcq_maplist);
				listview1.setAdapter(new Listview1Adapter(allmcq_maplist));
				((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childValue.get("subject_code").toString().equals(getIntent().getStringExtra("subject"))) {
					if (_childValue.get("wrong_flag").toString().equals(getIntent().getStringExtra("wrong"))) {
						allmcq_maplist.add(_childValue);
					}
				}
				map_temp = new Gson().toJson(allmcq_maplist);
				listview1.setAdapter(new Listview1Adapter(allmcq_maplist));
				((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
			}
			
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childValue.get("subject_code").toString().equals(getIntent().getStringExtra("subject"))) {
					if (_childValue.get("wrong_flag").toString().equals("off")) {
						allmcq_maplist.add(_childValue);
					}
				}
				map_temp = new Gson().toJson(allmcq_maplist);
				listview1.setAdapter(new Listview1Adapter(allmcq_maplist));
				((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		allmcq.addChildEventListener(_allmcq_child_listener);
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
		if ("off".equals(getIntent().getStringExtra("wrong"))) {
			setTitle("All MCQ List");
		}else {
			setTitle("Wrong Reported Questions List");
		}
		semester_key = getIntent().getStringExtra("semester");
		subject_keyname = getIntent().getStringExtra("subject");
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
				_view = _inflater.inflate(R.layout.all_ques_clist, null);
			}
			
			final LinearLayout linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
			final TextView uqno = (TextView) _view.findViewById(R.id.uqno);
			final TextView textview2 = (TextView) _view.findViewById(R.id.textview2);
			final TextView question = (TextView) _view.findViewById(R.id.question);
			
			uqno.setText(_data.get((int)_position).get("uqno").toString());
			question.setText(_data.get((int)_position).get("question").toString());
			
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