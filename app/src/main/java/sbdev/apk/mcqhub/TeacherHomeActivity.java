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
import java.util.HashMap;
import java.util.ArrayList;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
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


public class TeacherHomeActivity extends AppCompatActivity {
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private HashMap<String, Object> map = new HashMap<>();
	private double count = 0;
	private String semester_selected = "";
	private String subject_selected = "";
	
	private ArrayList<HashMap<String, Object>> subjects_listmsp = new ArrayList<>();
	private ArrayList<String> semester = new ArrayList<>();
	private ArrayList<String> subjectlistStr = new ArrayList<>();
	private ArrayList<String> subject_code_list = new ArrayList<>();
	
	private LinearLayout linear12;
	private ImageView imageview3;
	private ImageView imageview1;
	private ImageView imageview2;
	private LinearLayout linear9;
	private LinearLayout linear5;
	private LinearLayout linear13;
	private LinearLayout linear14;
	private LinearLayout linear15;
	private LinearLayout linear7;
	private LinearLayout linear8;
	private TextView textview_name;
	private TextView textview_subject;
	private Button allmcq;
	private Button addmcq;
	private Button test_activation;
	private Button list_of_results;
	private Button my_profile;
	private Button button4;
	
	private AlertDialog.Builder dialog;
	private Intent int1 = new Intent();
	private SharedPreferences sp1;
	private TimerTask t;
	private RequestNetwork rpn;
	private RequestNetwork.RequestListener _rpn_request_listener;
	private AlertDialog custom_dialog1;
	private DatabaseReference subjectlist = _firebase.getReference("subject_list");
	private ChildEventListener _subjectlist_child_listener;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.teacher_home);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear12 = (LinearLayout) findViewById(R.id.linear12);
		imageview3 = (ImageView) findViewById(R.id.imageview3);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		imageview2 = (ImageView) findViewById(R.id.imageview2);
		linear9 = (LinearLayout) findViewById(R.id.linear9);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		linear13 = (LinearLayout) findViewById(R.id.linear13);
		linear14 = (LinearLayout) findViewById(R.id.linear14);
		linear15 = (LinearLayout) findViewById(R.id.linear15);
		linear7 = (LinearLayout) findViewById(R.id.linear7);
		linear8 = (LinearLayout) findViewById(R.id.linear8);
		textview_name = (TextView) findViewById(R.id.textview_name);
		textview_subject = (TextView) findViewById(R.id.textview_subject);
		allmcq = (Button) findViewById(R.id.allmcq);
		addmcq = (Button) findViewById(R.id.addmcq);
		test_activation = (Button) findViewById(R.id.test_activation);
		list_of_results = (Button) findViewById(R.id.list_of_results);
		my_profile = (Button) findViewById(R.id.my_profile);
		button4 = (Button) findViewById(R.id.button4);
		dialog = new AlertDialog.Builder(this);
		sp1 = getSharedPreferences("sp1", Activity.MODE_PRIVATE);
		rpn = new RequestNetwork(this);
		
		allmcq.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				final AlertDialog custom_dialog1 = new AlertDialog.Builder(TeacherHomeActivity.this).create();
				LayoutInflater custom_dialog1LI = getLayoutInflater();
				View custom_dialog1CV = (View) custom_dialog1LI.inflate(R.layout.custom_d_layout1, null);
				custom_dialog1.setView(custom_dialog1CV);
				final LinearLayout linear1 = (LinearLayout)
				custom_dialog1CV.findViewById(R.id.linear1);
				final LinearLayout linear9 = (LinearLayout)
				custom_dialog1CV.findViewById(R.id.linear9);
				final LinearLayout linear10 = (LinearLayout)
				custom_dialog1CV.findViewById(R.id.linear10);
				final LinearLayout linear11 = (LinearLayout)
				custom_dialog1CV.findViewById(R.id.linear11);
				final LinearLayout linear12 = (LinearLayout)
				custom_dialog1CV.findViewById(R.id.linear12);
				final TextView textview2 = (TextView)
				custom_dialog1CV.findViewById(R.id.textview2);
				final TextView textview5 = (TextView)
				custom_dialog1CV.findViewById(R.id.textview5);
				final TextView textview6 = (TextView)
				custom_dialog1CV.findViewById(R.id.textview6);
				final TextView cd_title = (TextView)
				custom_dialog1CV.findViewById(R.id.cd_title);
				final Spinner spinner_semester = (Spinner)
				custom_dialog1CV.findViewById(R.id.spinner_semester);
				final Spinner spinner_subjectname = (Spinner)
				custom_dialog1CV.findViewById(R.id.spinner_subjectname);
				final Button cd_show = (Button)
				custom_dialog1CV.findViewById(R.id.cd_show);
				final Button cd_cancel = (Button)
				custom_dialog1CV.findViewById(R.id.cd_cancel);
				final CheckBox checkbox_allmcq = (CheckBox)
				custom_dialog1CV.findViewById(R.id.checkbox_allmcq);
				final CheckBox checkbox_wrong_on = (CheckBox)
				custom_dialog1CV.findViewById(R.id.checkbox_wrong_on);
				cd_title.setText("Department :\n".concat(getIntent().getStringExtra("department")));
				spinner_semester.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, semester));
				((ArrayAdapter)spinner_semester.getAdapter()).notifyDataSetChanged();
				spinner_subjectname.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, subjectlistStr));
				((ArrayAdapter)spinner_subjectname.getAdapter()).notifyDataSetChanged();
				subjectlistStr.clear();
				subject_code_list.clear();
				subjectlistStr.add("Select Subject");
				subject_code_list.add("Select Subject");
				spinner_subjectname.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, subjectlistStr));
				((ArrayAdapter)spinner_subjectname.getAdapter()).notifyDataSetChanged();
				custom_dialog1.show();
				cd_cancel.setOnClickListener(new View.OnClickListener(){
					@Override
					public void onClick(View _view){
						custom_dialog1.dismiss();
					}
				});
				checkbox_allmcq.setOnClickListener(new View.OnClickListener() {
								@Override
								public void onClick(View _view) {
										if (checkbox_wrong_on.isChecked()) {
												checkbox_wrong_on.setChecked(false);
										}
										
								}
						});
						
				checkbox_wrong_on.setOnClickListener(new View.OnClickListener() {
								@Override
								public void onClick(View _view) {
										if (checkbox_allmcq.isChecked()) {
												checkbox_allmcq.setChecked(false);
										}
									
								}
						});
				spinner_semester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
								@Override
								public void onItemSelected(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
									
						if (!(0 == subjects_listmsp.size())) {
							subjectlistStr.clear();
							subject_code_list.clear();
							subjectlistStr.add("Select Subject");
							subject_code_list.add("Select Subject");
							spinner_subjectname.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, subjectlistStr));
							((ArrayAdapter)spinner_subjectname.getAdapter()).notifyDataSetChanged();
							count = 0;
							if (!"Select Semester".equals(semester.get((int)(spinner_semester.getSelectedItemPosition())))) {
								if (!(0 == subjects_listmsp.size())) {
									for(int _repeat267 = 0; _repeat267 < (int)(subjects_listmsp.size()); _repeat267++) {
										if (subjects_listmsp.get((int)count).get("semester").toString().equals(semester.get((int)(spinner_semester.getSelectedItemPosition())))) {
											subjectlistStr.add(subjects_listmsp.get((int)count).get("subject_name").toString().concat(" (".concat(subjects_listmsp.get((int)count).get("subject_code").toString().concat(")"))));
											subject_code_list.add(subjects_listmsp.get((int)count).get("subject_code").toString());
										}
										count++;
									}
								}
								if (subjectlistStr.size() == 1) {
									SketchwareUtil.showMessage(getApplicationContext(), "Subject not available ‼️");
								}
							}
							spinner_subjectname.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, subjectlistStr));
							((ArrayAdapter)spinner_subjectname.getAdapter()).notifyDataSetChanged();
						}else {
							
						}
					}
								
								@Override
								public void onNothingSelected(AdapterView<?> _param1) {
										
								}
						});
				cd_show.setOnClickListener(new View.OnClickListener(){
					@Override
					public void onClick(View _view){
						if (!"Select Semester".equals(semester.get((int)(spinner_semester.getSelectedItemPosition())))) {
							if (!"Select Subject".equals(subjectlistStr.get((int)(spinner_subjectname.getSelectedItemPosition())))) {
								if (checkbox_allmcq.isChecked() || checkbox_wrong_on.isChecked()) {
									semester_selected = semester.get((int)(spinner_semester.getSelectedItemPosition()));
									subject_selected = subject_code_list.get((int)(spinner_subjectname.getSelectedItemPosition()));
									if (checkbox_wrong_on.isChecked()) {
										int1.putExtra("wrong", "on");
									}
									if (checkbox_allmcq.isChecked()) {
										int1.putExtra("wrong", "off");
									}
									int1.putExtra("semester", semester_selected);
									int1.putExtra("subject", subject_selected);
									int1.putExtra("idno", getIntent().getStringExtra("idno"));
									int1.putExtra("name", getIntent().getStringExtra("name"));
									int1.putExtra("department", getIntent().getStringExtra("department"));
									int1.setClass(getApplicationContext(), AllQuestionsListActivity.class);
									custom_dialog1.dismiss();
									startActivity(int1);
								}else {
									SketchwareUtil.showMessage(getApplicationContext(), "Fill any check 1st to see MCQ List");
								}
							}else {
								SketchwareUtil.showMessage(getApplicationContext(), "Select Subject");
							}
						}else {
							SketchwareUtil.showMessage(getApplicationContext(), "Select Semester");
						}
					}
				});
			}
		});
		
		addmcq.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				int1.putExtra("idno", getIntent().getStringExtra("idno"));
				int1.putExtra("name", getIntent().getStringExtra("name"));
				int1.putExtra("department", getIntent().getStringExtra("department"));
				int1.putExtra("action", "add");
				int1.putExtra("from", "home");
				int1.setClass(getApplicationContext(), AddUpdateQuestionsActivity.class);
				startActivity(int1);
			}
		});
		
		test_activation.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				int1.setClass(getApplicationContext(), TestActivationActivity.class);
				int1.putExtra("department_name", getIntent().getStringExtra("department"));
				startActivity(int1);
			}
		});
		
		list_of_results.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				int1.setClass(getApplicationContext(), ResultListTeacherActivity.class);
				int1.putExtra("department_name", getIntent().getStringExtra("department"));
				startActivity(int1);
			}
		});
		
		my_profile.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				int1.putExtra("idno", getIntent().getStringExtra("idno"));
				int1.setClass(getApplicationContext(), TeacherMyProfileActivity.class);
				startActivity(int1);
			}
		});
		
		button4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				dialog.setTitle("⚠️ Alert ⚠️");
				dialog.setMessage("Are you sure ?");
				dialog.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						int1.setClass(getApplicationContext(), LoginPageActivity.class);
						startActivity(int1);
						finish();
					}
				});
				dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						
					}
				});
				dialog.create().show();
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
						int1.putExtra("idno", getIntent().getStringExtra("idno"));
						int1.putExtra("name", getIntent().getStringExtra("name"));
						int1.putExtra("department", getIntent().getStringExtra("department"));
						int1.setClass(getApplicationContext(), TeacherHomeActivity.class);
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
				if (getIntent().getStringExtra("department").equals(_childValue.get("department_name").toString())) {
					subjects_listmsp.add(_childValue);
				}
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (getIntent().getStringExtra("department").equals(_childValue.get("department_name").toString())) {
					subjects_listmsp.add(_childValue);
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
				if (getIntent().getStringExtra("department").equals(_childValue.get("department_name").toString())) {
					subjects_listmsp.add(_childValue);
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
		textview_name.setText("Teacher Name :\n".concat(getIntent().getStringExtra("name")));
		textview_subject.setText("Department :\n".concat(getIntent().getStringExtra("department")));
		semester.clear();
		subjectlistStr.clear();
		count = 1;
		semester.add("Select Semester");
		for(int _repeat26 = 0; _repeat26 < (int)(8); _repeat26++) {
			semester.add(String.valueOf((long)(count)));
			count++;
		}
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
		dialog.setTitle("⚠️ Alert ⚠️");
		dialog.setMessage("Are you sure ?");
		dialog.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface _dialog, int _which) {
				int1.setClass(getApplicationContext(), LoginPageActivity.class);
				startActivity(int1);
				finish();
			}
		});
		dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface _dialog, int _which) {
				
			}
		});
		dialog.create().show();
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