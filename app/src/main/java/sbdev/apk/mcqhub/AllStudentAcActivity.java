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
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import java.io.InputStream;
import android.content.Intent;
import android.net.Uri;
import android.app.Activity;
import android.content.SharedPreferences;
import java.util.Timer;
import java.util.TimerTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AdapterView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class AllStudentAcActivity extends AppCompatActivity {
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private double map_length = 0;
	private double search_pos = 0;
	private String msg = "";
	private String map_temp = "";
	private String value1 = "";
	private HashMap<String, Object> map = new HashMap<>();
	private String old_net_status = "";
	private double count = 0;
	
	private ArrayList<HashMap<String, Object>> student_maplist = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> results_maplist = new ArrayList<>();
	private ArrayList<String> results_keylist = new ArrayList<>();
	
	private LinearLayout linear3;
	private LinearLayout linear6;
	private HorizontalScrollView hscroll5;
	private EditText search_text;
	private LinearLayout linear5;
	private LinearLayout linear1;
	private ListView listview1;
	private TextView idno;
	private TextView textview2;
	private TextView name;
	private TextView textview9;
	private TextView textview10;
	
	private DatabaseReference allusers = _firebase.getReference("all_users");
	private ChildEventListener _allusers_child_listener;
	private AlertDialog.Builder d;
	private AlertDialog.Builder d1;
	private Intent int1 = new Intent();
	private SharedPreferences sp1;
	private AlertDialog.Builder dialog;
	private TimerTask t;
	private RequestNetwork rpn;
	private RequestNetwork.RequestListener _rpn_request_listener;
	private DatabaseReference results_list = _firebase.getReference("results");
	private ChildEventListener _results_list_child_listener;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.all_student_ac);
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
		idno = (TextView) findViewById(R.id.idno);
		textview2 = (TextView) findViewById(R.id.textview2);
		name = (TextView) findViewById(R.id.name);
		textview9 = (TextView) findViewById(R.id.textview9);
		textview10 = (TextView) findViewById(R.id.textview10);
		d = new AlertDialog.Builder(this);
		d1 = new AlertDialog.Builder(this);
		sp1 = getSharedPreferences("sp1", Activity.MODE_PRIVATE);
		dialog = new AlertDialog.Builder(this);
		rpn = new RequestNetwork(this);
		
		search_text.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				if (!(0 == map_temp.length())) {
					student_maplist.clear();
					student_maplist = new Gson().fromJson(map_temp, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
					map_length = student_maplist.size();
					search_pos = map_length - 1;
					for(int _repeat18 = 0; _repeat18 < (int)(map_length); _repeat18++) {
						value1 = student_maplist.get((int)search_pos).get("name").toString();
						if (!(_charSeq.length() > value1.length()) && value1.toLowerCase().contains(_charSeq.toLowerCase())) {
							
						}else {
							student_maplist.remove((int)(search_pos));
						}
						search_pos--;
					}
					listview1.setAdapter(new Listview1Adapter(student_maplist));
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
				msg = "".concat("ID No.").concat(student_maplist.get((int)_position).get("idno").toString().concat("\n"));
				msg = msg.concat("Name : ").concat(student_maplist.get((int)_position).get("name").toString().concat("\n"));
				msg = msg.concat("Gender :").concat(student_maplist.get((int)_position).get("gender").toString().concat("\n"));
				msg = msg.concat("Email : ").concat(student_maplist.get((int)_position).get("email").toString().concat("\n"));
				msg = msg.concat("Password : ").concat(student_maplist.get((int)_position).get("password").toString().concat("\n"));
				msg = msg.concat("Contact No : ").concat(student_maplist.get((int)_position).get("contactno").toString().concat("\n"));
				msg = msg.concat("Department :").concat(student_maplist.get((int)_position).get("department_name").toString().concat("\n"));
				msg = msg.concat("What to do?");
				d.setTitle("User Profile");
				d.setMessage(msg);
				d.setPositiveButton("Deactivate", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						d1.setCancelable(false);
						d1.setTitle("⚠️ Alert ⚠️");
						d1.setMessage("Are you sure ?");
						d1.setPositiveButton("Deactivate", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface _dialog, int _which) {
								map.clear();
								map = student_maplist.get((int)_position);
								map.remove("status");
								map.put("status", "deactivate");
								allusers.child(student_maplist.get((int)_position).get("idno").toString()).updateChildren(map);
								msg = "";
								int1.setClass(getApplicationContext(), AllStudentAcActivity.class);
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
				d.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						d1.setCancelable(false);
						d1.setTitle("⚠️ Alert ⚠️");
						d1.setMessage("User Account & All records like results will Deleted.\nARE YOU SURE ?");
						d1.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface _dialog, int _which) {
								results_keylist.clear();
								count = 0;
								for(int _repeat192 = 0; _repeat192 < (int)(results_maplist.size()); _repeat192++) {
									if (student_maplist.get((int)_position).get("idno").toString().equals(results_maplist.get((int)count).get("idno").toString())) {
										results_keylist.add(results_maplist.get((int)count).get("key").toString());
									}
									count++;
								}
								count = 0;
								for(int _repeat205 = 0; _repeat205 < (int)(results_keylist.size()); _repeat205++) {
									results_list.child(results_keylist.get((int)(count))).removeValue();
									count++;
								}
								allusers.child(student_maplist.get((int)_position).get("idno").toString()).removeValue();
								SketchwareUtil.showMessage(getApplicationContext(), "Account is Deleted");
								int1.setClass(getApplicationContext(), AllStudentAcActivity.class);
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
		
		_allusers_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childValue.get("who_are_you").toString().equals("Student") && _childValue.get("status").toString().equals("active")) {
					student_maplist.add(_childValue);
				}
				map_temp = new Gson().toJson(student_maplist);
				listview1.setAdapter(new Listview1Adapter(student_maplist));
				((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childValue.get("who_are_you").toString().equals("Student") && _childValue.get("status").toString().equals("active")) {
					student_maplist.add(_childValue);
				}
				map_temp = new Gson().toJson(student_maplist);
				listview1.setAdapter(new Listview1Adapter(student_maplist));
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
				if (_childValue.get("who_are_you").toString().equals("Student") && _childValue.get("status").toString().equals("active")) {
					student_maplist.add(_childValue);
				}
				map_temp = new Gson().toJson(student_maplist);
				listview1.setAdapter(new Listview1Adapter(student_maplist));
				((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		allusers.addChildEventListener(_allusers_child_listener);
		
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
						int1.setClass(getApplicationContext(), AllStudentAcActivity.class);
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
				results_maplist.clear();
				results_list.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						results_maplist = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								results_maplist.add(_map);
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
		setTitle("All Student's Account List");
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
		finish();
	}
	
	@Override
	public void onStop() {
		super.onStop();
		t.cancel();
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
			
			idno.setText(_data.get((int)_position).get("idno").toString());
			name.setText(_data.get((int)_position).get("name").toString());
			subject_name.setText(_data.get((int)_position).get("department_name").toString());
			
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