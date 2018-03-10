package io.github.skywalkerdarren.simpleaccounting.UI;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.List;

import co.ceryle.segmentedbutton.SegmentedButtonGroup;
import io.github.skywalkerdarren.simpleaccounting.R;
import io.github.skywalkerdarren.simpleaccounting.adapter.TypeAdapter;
import io.github.skywalkerdarren.simpleaccounting.model.BaseType;
import io.github.skywalkerdarren.simpleaccounting.model.Bill;
import io.github.skywalkerdarren.simpleaccounting.model.BillLab;
import io.github.skywalkerdarren.simpleaccounting.model.ExpenseType;
import io.github.skywalkerdarren.simpleaccounting.model.IncomeType;

/**
 * 账单编辑或创建的fragment
 */
public class BillEditFragment extends BaseFragment {
    private static final String ARG_BILL = "bill";
    private static final int REQUEST_DATE = 0;
    private Bill mBill;
    private ImageView mTypeImageView;
    private ImageView mDateImageView;
    private TextView mTitleTextView;
    private EditText mBalanceEditText;
    private EditText mRemarkEditText;
    private RecyclerView mTypeRecyclerView;
    private DateTime mDateTime;
    private SegmentedButtonGroup mTypeSbg;
    private boolean mIsExpense = true;
    private NumPad mNumPad;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param bill 要编辑的账单
     * @return A new instance of fragment BillEditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BillEditFragment newInstance(Bill bill) {
        BillEditFragment fragment = new BillEditFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_BILL, bill);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBill = (Bill) getArguments().getSerializable(ARG_BILL);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_edit, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_save_item:
                saveBill();
                Toast.makeText(getActivity(), "点击保存并退出", Toast.LENGTH_SHORT).show();
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * 保存账单
     */
    private void saveBill() {
        if (TextUtils.isEmpty(mBalanceEditText.getText())) {
            return;
        }
        mBill.setName(mTitleTextView.getText().toString());
        mBill.setDate(mDateTime);
        mBill.setRemark(mRemarkEditText.getText().toString());
        mBill.setBalance(new BigDecimal(mBalanceEditText.getText().toString()));
        mBill.setType(mTitleTextView.getText().toString());
        List<BaseType> types = mIsExpense ? ExpenseType.getTypeList() : IncomeType.getTypeList();
        for (BaseType type : types) {
            if (type.getName().equals(mBill.getTypeName())) {
                mBill.setExpense(type);
            }
        }

        BillLab lab = BillLab.getInstance(getActivity());
        if (lab.getBill(mBill.getId()) == null) {
            lab.addBill(mBill);
        } else {
            lab.updateBill(mBill);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bill_edit, container, false);
        mTitleTextView = view.findViewById(R.id.title_text_view);
        mTypeImageView = view.findViewById(R.id.type_image_view);
        mDateImageView = view.findViewById(R.id.date_image_view);
        mRemarkEditText = view.findViewById(R.id.remark_edit_text);
        mBalanceEditText = view.findViewById(R.id.balance_edit_text);
        mTypeRecyclerView = view.findViewById(R.id.type_list_recycler_view);
        mNumPad = view.findViewById(R.id.num_key_view);
        mTypeSbg = view.findViewById(R.id.type_sbg);

        // 自定义导航栏
        ActionBar actionBar = initToolbar(R.id.toolbar, view);
        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(android.support.design.R.drawable.abc_ic_ab_back_material);

        TypeAdapter adapter = new TypeAdapter(ExpenseType.getTypeList());
        adapter.openLoadAnimation(view14 -> new Animator[]{
                        ObjectAnimator.ofFloat(view14, "scaleY", 1, 1.1f, 1),
                        ObjectAnimator.ofFloat(view14, "scaleX", 1, 1.1f, 1),
                        ObjectAnimator.ofFloat(view14, "alpha", 0f, 1f)
                }

        );
        adapter.setOnItemClickListener((adapter1, view12, position) -> {
            Toast.makeText(getContext(), "expense", Toast.LENGTH_SHORT).show();
            BaseType type = (BaseType) adapter1.getData().get(position);
            mTitleTextView.setText(type.getName());
            mTypeImageView.setImageResource(type.getTypeId());
        });
        mTypeRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        mTypeRecyclerView.setAdapter(adapter);


        mTypeSbg.setOnClickedButtonListener(position -> {
            List<BaseType> types;
            switch (position) {
                case 0:
                    types = IncomeType.getTypeList();
                    mIsExpense = false;
                    adapter.setNewData(types);
                    mTitleTextView.setText(types.get(0).getName());
                    mTypeImageView.setImageResource(types.get(0).getTypeId());
                    mBalanceEditText.setTextColor(getResources().getColor(R.color.greenyellow));
                    break;
                case 1:
                    types = ExpenseType.getTypeList();
                    mIsExpense = true;
                    mTitleTextView.setText(types.get(0).getName());
                    mTypeImageView.setImageResource(types.get(0).getTypeId());
                    adapter.setNewData(ExpenseType.getTypeList());
                    mBalanceEditText.setTextColor(getResources().getColor(R.color.orangered));
                    break;
                default:
                    break;
            }
            adapter.notifyDataSetChanged();
        });


        if (mBill.getDate() == null) {
            mDateTime = DateTime.now();
        } else {
            mDateTime = mBill.getDate();
        }

        if (!TextUtils.isEmpty(mBill.getName())) {
            mTitleTextView.setText(mBill.getName());
        } else {
            mTitleTextView.setText(adapter.getData().get(0).getName());
        }

        if (!TextUtils.isEmpty(mBill.getTypeName())) {
            mTypeImageView.setImageResource(mBill.getTypeResId());
        } else {
            mTypeImageView.setImageResource(adapter.getData().get(0).getTypeId());
        }

        if (!TextUtils.isEmpty(mBill.getRemark())) {
            mRemarkEditText.setText(mBill.getRemark());
        }

        mNumPad.setStrReceiver(mBalanceEditText);

        mBalanceEditText.setOnTouchListener((View view16, MotionEvent motionEvent) -> {
            mRemarkEditText.clearFocus();
            mNumPad.hideSysKeyboard();
            mNumPad.showKeyboard();
            return true;
        });
        mBalanceEditText.setOnFocusChangeListener((view15, b) -> {
            if (!b) {
                mNumPad.hideKeyboard();
            }
        });

        mRemarkEditText.setOnClickListener((view1) -> {
            mNumPad.hideKeyboard();
        });

        mDateImageView.setOnClickListener(view13 -> {
            DatePickerFragment datePicker = DatePickerFragment.newInstance(mDateTime);
            datePicker.setTargetFragment(this, REQUEST_DATE);
            datePicker.show(getFragmentManager(), "datePicker");
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQUEST_DATE:
                mDateTime = (DateTime) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
                break;
            default:
                break;
        }
    }
}
