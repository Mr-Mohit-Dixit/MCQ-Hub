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
import java.util.ArrayList;
import java.util.HashMap;
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


public class StudentHomeActivity extends AppCompatActivity {
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private double sbj_exist = 0;
	private double sbj_count = 0;
	private double rlt_count = 0;
	private double count1 = 0;
	private double test_exist_flag = 0;
	private double count = 0;
	private double allmcq_count = 0;
	private double total_mcqreq = 0;
	private double count2 = 0;
	private double count3 = 0;
	
	private ArrayList<String> semester = new ArrayList<>();
	private ArrayList<String> subjectlistStr = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> subjects_listmsp = new ArrayList<>();
	private ArrayList<String> subject_code_list = new ArrayList<>();
	private ArrayList<String> test_type = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> results_listmap = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> allresult_map = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> allmcq_maplist = new ArrayList<>();
	private ArrayList<String> uqno_list = new ArrayList<>();
	
	private LinearLayout linear1;
	private ImageView imageview1;
	private ImageView imageview2;
	private LinearLayout linear9;
	private LinearLayout linear5;
	private LinearLayout linear8;
	private LinearLayout linear6;
	private LinearLayout linear7;
	private TextView textview_name;
	private TextView textview_department;
	private Button mcq_test;
	private Button results;
	private Button my_profile;
	private Button logout;
	
	private AlertDialog.Builder dialog;
	private Intent int1 = new Intent();
	private SharedPreferences sp1;
	private TimerTask t;
	private RequestNetwork rpn;
	private RequestNetwork.RequestListener _rpn_request_listener;
	private AlertDialog custom_dialog1;
	private DatabaseReference subjectlist = _firebase.getReference("subject_list");
	private ChildEventListener _subjectlist_child_listener;
	private DatabaseReference result_list = _firebase.getReference("results");
	private ChildEventListener _result_list_child_listener;
	private DatabaseReference allmcq = _firebase.getReference("All MCQ");
	private ChildEventListener _allmcq_child_listener;
	
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.student_home);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		imageview2 = (ImageView) findViewById(R.id.imageview2);
		linear9 = (LinearLayout) findViewById(R.id.linear9);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		linear8 = (LinearLayout) findViewById(R.id.linear8);
		linear6 = (LinearLayout) findViewById(R.id.linear6);
		linear7 = (LinearLayout) findViewById(R.id.linear7);
		textview_name = (TextView) findViewById(R.id.textview_name);
		textview_department = (TextView) findViewById(R.id.textview_department);
		mcq_test = (Button) findViewById(R.id.mcq_test);
		results = (Button) findViewById(R.id.results);
		my_profile = (Button) findViewById(R.id.my_profile);
		logout = (Button) findViewById(R.id.logout);
		dialog = new AlertDialog.Builder(this);
		sp1 = getSharedPreferences("sp1", Activity.MODE_PRIVATE);
		rpn = new RequestNetwork(this);
		
		mcq_test.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				final AlertDialog custom_dialog1 = new AlertDialog.Builder(StudentHomeActivity.this).create();
				LayoutInflater custom_dialog1LI = getLayoutInflater();
				View custom_dialog1CV = (View) custom_dialog1LI.inflate(R.layout.custom_d2_layout, null);
				custom_dialog1.setView(custom_dialog1CV);
				final LinearLayout linear1 = (LinearLayout)
				custom_dialog1CV.findViewById(R.id.linear1);
				final LinearLayout linear9 = (LinearLayout)
				custom_dialog1CV.findViewById(R.id.linear9);
				final LinearLayout linear10 = (LinearLayout)
				custom_dialog1CV.findViewById(R.id.linear10);
				final LinearLayout linear11 = (LinearLayout)
				custom_dialog1CV.findViewById(R.id.linear11);
				final LinearLayout linear15 = (LinearLayout)
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
				final TextView textview9 = (TextView)
				custom_dialog1CV.findViewById(R.id.textview9);
				final Spinner spinner_select_test = (Spinner)
				custom_dialog1CV.findViewById(R.id.spinner_select_test);
				cd_title.setText("Department :\n".concat(getIntent().getStringExtra("department")));
				spinner_semester.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, semester));
				((ArrayAdapter)spinner_semester.getAdapter()).notifyDataSetChanged();
				spinner_subjectname.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, subjectlistStr));
				((ArrayAdapter)spinner_subjectname.getAdapter()).notifyDataSetChanged();
				test_type.clear();
				subjectlistStr.clear();
				subject_code_list.clear();
				subjectlistStr.add("Select Subject");
				subject_code_list.add("Select Subject");
				test_type.add("Select Test");
				spinner_subjectname.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, subjectlistStr));
				((ArrayAdapter)spinner_subjectname.getAdapter()).notifyDataSetChanged();
				spinner_select_test.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, test_type));
				((ArrayAdapter)spinner_select_test.getAdapter()).notifyDataSetChanged();
				custom_dialog1.show();
				cd_cancel.setOnClickListener(new View.OnClickListener(){
					@Override
					public void onClick(View _view){
						custom_dialog1.dismiss();
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
									for(int _repeat81 = 0; _repeat81 < (int)(subjects_listmsp.size()); _repeat81++) {
										if (subjects_listmsp.get((int)count).get("semester").toString().equals(semester.get((int)(spinner_semester.getSelectedItemPosition())))) {
											subjectlistStr.add(subjects_listmsp.get((int)count).get("subject_name").toString().concat(" (".concat(subjects_listmsp.get((int)count).get("subject_code").toString().concat(")"))));
											subject_code_list.add(subjects_listmsp.get((int)count).get("subject_code").toString());
										}
										if ("All Semester's".equals(semester.get((int)(spinner_semester.getSelectedItemPosition())))) {
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
				spinner_subjectname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
								@Override
								public void onItemSelected(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
									
						if (!"Select Subject".equals(subjectlistStr.get((int)(spinner_subjectname.getSelectedItemPosition())))) {
							test_type.clear();
							test_type.add("Select Test");
							count3 = 0;
							for(int _repeat459 = 0; _repeat459 < (int)(subjects_listmsp.size()); _repeat459++) {
								if (subject_code_list.get((int)(spinner_subjectname.getSelectedItemPosition())).equals(subjects_listmsp.get((int)count3).get("subject_code").toString())) {
									if ("1".equals(subjects_listmsp.get((int)count3).get("practice_test_flag").toString())) {
										test_type.add("Practice Test");
									}
									if ("1".equals(subjects_listmsp.get((int)count3).get("unit_test_1_flag").toString())) {
										test_type.add("Unit Test 1");
									}
									if ("1".equals(subjects_listmsp.get((int)count3).get("unit_test_2_flag").toString())) {
										test_type.add("Unit Test 2");
									}
									if ("1".equals(subjects_listmsp.get((int)count3).get("semester_test_flag").toString())) {
										test_type.add("Semester Test");
									}
								}
								count3++;
							}
							spinner_select_test.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, test_type));
							((ArrayAdapter)spinner_select_test.getAdapter()).notifyDataSetChanged();
						}else {
							test_type.clear();
							test_type.add("Select Test");
							spinner_select_test.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, test_type));
							((ArrayAdapter)spinner_select_test.getAdapter()).notifyDataSetChanged();
						}
					}
								
								@Override
								public void onNothingSelected(AdapterView<?> _param1) {
										
								}
						});
				cd_show.setOnClickListener(new View.OnClickListener(){
					@Override
					public void onClick(View _view){
						if ("Practice Test".equals(test_type.get((int)(spinner_select_test.getSelectedItemPosition())))) {
							total_mcqreq = 10;
						}
						if ("Unit Test 1".equals(test_type.get((int)(spinner_select_test.getSelectedItemPosition()))) || "Unit Test 2".equals(test_type.get((int)(spinner_select_test.getSelectedItemPosition())))) {
							total_mcqreq = 30;
						}
						if ("Semester Test".equals(test_type.get((int)(spinner_select_test.getSelectedItemPosition())))) {
							total_mcqreq = 60;
						}
						if (allmcq_maplist.size() == 0) {
							SketchwareUtil.showMessage(getApplicationContext(), "MCQ is Not Available");
						}else {
							allmcq_count = 0;
							count2 = 0;
							uqno_list.clear();
							for(int _repeat342 = 0; _repeat342 < (int)(allmcq_maplist.size()); _repeat342++) {
								if (subject_code_list.get((int)(spinner_subjectname.getSelectedItemPosition())).equals(allmcq_maplist.get((int)count2).get("subject_code").toString()) && "off".equals(allmcq_maplist.get((int)count2).get("wrong_flag").toString())) {
									uqno_list.add(allmcq_maplist.get((int)count2).get("uqno").toString());
									allmcq_count++;
								}
								count2++;
							}
						}
						if (!"Select Semester".equals(semester.get((int)(spinner_semester.getSelectedItemPosition())))) {
							if (!"Select Subject".equals(subjectlistStr.get((int)(spinner_subjectname.getSelectedItemPosition())))) {
								if (!"Select Test".equals(test_type.get((int)(spinner_select_test.getSelectedItemPosition())))) {
									if (!"Practice Test".equals(test_type.get((int)(spinner_select_test.getSelectedItemPosition())))) {
										test_exist_flag = 0;
										count1 = 0;
										if (!(0 == results_listmap.size())) {
											for(int _repeat210 = 0; _repeat210 < (int)(results_listmap.size()); _repeat210++) {
												if (test_type.get((int)(spinner_select_test.getSelectedItemPosition())).equals(results_listmap.get((int)count1).get("test_type").toString())) {
													if (subject_code_list.get((int)(spinner_subjectname.getSelectedItemPosition())).equals(results_listmap.get((int)count1).get("subject_code").toString())) {
														test_exist_flag = 1;
													}
												}
												count1++;
											}
											if ((total_mcqreq < allmcq_count) && (0 == test_exist_flag)) {
												int1.putExtra("total mcq", String.valueOf((long)(allmcq_count)));
												int1.putExtra("random uqno start", uqno_list.get((int)(SketchwareUtil.getRandom((int)(0), (int)(uqno_list.size() - 1)))));
												int1.putExtra("test_type", test_type.get((int)(spinner_select_test.getSelectedItemPosition())));
												int1.putExtra("semester", semester.get((int)(spinner_semester.getSelectedItemPosition())));
												int1.putExtra("subject name code", subjectlistStr.get((int)(spinner_subjectname.getSelectedItemPosition())));
												int1.putExtra("subject code", subject_code_list.get((int)(spinner_subjectname.getSelectedItemPosition())));
												int1.putExtra("idno", getIntent().getStringExtra("idno"));
												int1.putExtra("name", getIntent().getStringExtra("name"));
												int1.putExtra("department", getIntent().getStringExtra("department"));
												int1.setClass(getApplicationContext(), TestActivity.class);
												startActivity(int1);
												custom_dialog1.dismiss();
											}else {
												SketchwareUtil.showMessage(getApplicationContext(), "⚠️Selected subject have not enough questions or Your not allowed to give same test multiple times⚠️");
											}
										}
									}else {
										if (total_mcqreq < allmcq_count) {
											int1.putExtra("total mcq", String.valueOf((long)(allmcq_count)));
											int1.putExtra("random uqno start", uqno_list.get((int)(SketchwareUtil.getRandom((int)(0), (int)(uqno_list.size() - 1)))));
											int1.putExtra("test_type", test_type.get((int)(spinner_select_test.getSelectedItemPosition())));
											int1.putExtra("semester", semester.get((int)(spinner_semester.getSelectedItemPosition())));
											int1.putExtra("subject name code", subjectlistStr.get((int)(spinner_subjectname.getSelectedItemPosition())));
											int1.putExtra("subject code", subject_code_list.get((int)(spinner_subjectname.getSelectedItemPosition())));
											int1.putExtra("idno", getIntent().getStringExtra("idno"));
											int1.putExtra("name", getIntent().getStringExtra("name"));
											int1.putExtra("department", getIntent().getStringExtra("department"));
											int1.setClass(getApplicationContext(), TestActivity.class);
											startActivity(int1);
											custom_dialog1.dismiss();
										}else {
											SketchwareUtil.showMessage(getApplicationContext(), "Not enough MCQ available for selected Test");
										}
									}
								}else {
									SketchwareUtil.showMessage(getApplicationContext(), "Select Test");
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
		
		results.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				int1.setClass(getApplicationContext(), ResultListStudentActivity.class);
				int1.putExtra("idno", getIntent().getStringExtra("idno"));
				startActivity(int1);
			}
		});
		
		my_profile.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				int1.setClass(getApplicationContext(), MyprofileStudentActivity.class);
				int1.putExtra("idno", getIntent().getStringExtra("idno"));
				startActivity(int1);
			}
		});
		
		logout.setOnClickListener(new View.OnClickListener() {
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
						int1.setClass(getApplicationContext(), StudentHomeActivity.class);
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
					if (!(0 == subjects_listmsp.size())) {
						sbj_exist = 0;
						sbj_count = 0;
						for(int _repeat26 = 0; _repeat26 < (int)(subjects_listmsp.size()); _repeat26++) {
							if (subjects_listmsp.get((int)sbj_count).get("subject_code").toString().equals(_childValue.get("subject_code").toString())) {
								sbj_exist = 1;
							}
							sbj_count++;
						}
						if (sbj_exist == 0) {
							subjects_listmsp.add(_childValue);
						}
					}else {
						subjects_listmsp.add(_childValue);
					}
				}
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (getIntent().getStringExtra("department").equals(_childValue.get("department_name").toString())) {
					if (!(0 == subjects_listmsp.size())) {
						sbj_exist = 0;
						sbj_count = 0;
						for(int _repeat23 = 0; _repeat23 < (int)(subjects_listmsp.size()); _repeat23++) {
							if (subjects_listmsp.get((int)sbj_count).get("subject_code").toString().equals(_childValue.get("subject_code").toString())) {
								sbj_exist = 1;
							}
							sbj_count++;
						}
						if (sbj_exist == 0) {
							subjects_listmsp.add(_childValue);
						}
					}else {
						subjects_listmsp.add(_childValue);
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
				if (getIntent().getStringExtra("department").equals(_childValue.get("department_name").toString())) {
					if (!(0 == subjects_listmsp.size())) {
						sbj_exist = 0;
						sbj_count = 0;
						for(int _repeat23 = 0; _repeat23 < (int)(subjects_listmsp.size()); _repeat23++) {
							if (subjects_listmsp.get((int)sbj_count).get("subject_code").toString().equals(_childValue.get("subject_code").toString())) {
								sbj_exist = 1;
							}
							sbj_count++;
						}
						if (sbj_exist == 0) {
							subjects_listmsp.add(_childValue);
						}
					}else {
						subjects_listmsp.add(_childValue);
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
		
		_result_list_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				allresult_map.clear();
				results_listmap.clear();
				result_list.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						allresult_map = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								allresult_map.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						rlt_count = 0;
						for(int _repeat43 = 0; _repeat43 < (int)(allresult_map.size()); _repeat43++) {
							if (getIntent().getStringExtra("idno").equals(allresult_map.get((int)rlt_count).get("idno").toString())) {
								results_listmap.add(_childValue);
							}
							rlt_count++;
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
				allresult_map.clear();
				results_listmap.clear();
				result_list.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						allresult_map = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								allresult_map.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						rlt_count = 0;
						for(int _repeat14 = 0; _repeat14 < (int)(allresult_map.size()); _repeat14++) {
							if (getIntent().getStringExtra("idno").equals(allresult_map.get((int)rlt_count).get("idno").toString())) {
								results_listmap.add(_childValue);
							}
							rlt_count++;
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
				allresult_map.clear();
				results_listmap.clear();
				result_list.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						allresult_map = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								allresult_map.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						rlt_count = 0;
						for(int _repeat14 = 0; _repeat14 < (int)(allresult_map.size()); _repeat14++) {
							if (getIntent().getStringExtra("idno").equals(allresult_map.get((int)rlt_count).get("idno").toString())) {
								results_listmap.add(_childValue);
							}
							rlt_count++;
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
		result_list.addChildEventListener(_result_list_child_listener);
		
		_allmcq_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				allmcq_maplist.clear();
				allmcq.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						allmcq_maplist = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								allmcq_maplist.add(_map);
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
				allmcq_maplist.clear();
				allmcq.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						allmcq_maplist = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								allmcq_maplist.add(_map);
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
				allmcq_maplist.clear();
				allmcq.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						allmcq_maplist = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								allmcq_maplist.add(_map);
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
		textview_name.setText("Student Name :\n".concat(getIntent().getStringExtra("name")));
		textview_department.setText("Department :\n".concat(getIntent().getStringExtra("department")));
		semester.clear();
		semester.add("Select Semester");
		semester.add("1");
		semester.add("2");
		semester.add("3");
		semester.add("4");
		semester.add("5");
		semester.add("6");
		semester.add("7");
		semester.add("8");
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