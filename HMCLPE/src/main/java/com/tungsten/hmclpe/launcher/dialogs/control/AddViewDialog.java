package com.tungsten.hmclpe.launcher.dialogs.control;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;

import com.tungsten.hmclpe.R;
import com.tungsten.hmclpe.control.bean.BaseButtonInfo;
import com.tungsten.hmclpe.control.bean.BaseRockerViewInfo;
import com.tungsten.hmclpe.control.bean.button.ButtonPosition;
import com.tungsten.hmclpe.control.bean.button.ButtonSize;
import com.tungsten.hmclpe.control.bean.button.ButtonStyle;
import com.tungsten.hmclpe.launcher.dialogs.tools.ColorSelectorDialog;
import com.tungsten.hmclpe.launcher.setting.SettingUtils;

import java.util.ArrayList;

public class AddViewDialog extends Dialog implements View.OnClickListener, AdapterView.OnItemSelectedListener, SeekBar.OnSeekBarChangeListener, CompoundButton.OnCheckedChangeListener, TextWatcher {

    private int screenWidth;
    private int screenHeight;

    private BaseButtonInfo baseButtonInfo;
    private BaseRockerViewInfo baseRockerViewInfo;

    private Button showButton;
    private Button showRocker;
    private LinearLayout editButton;
    private LinearLayout editRocker;

    private Button positive;
    private Button negative;

    private int viewType;

    private ArrayAdapter<String> sizeTypeAdapter;
    private ArrayAdapter<String> positionTypeAdapter;
    private ArrayAdapter<String> sizeObjectAdapter;
    private ArrayAdapter<String> functionTypeAdapter;

    private EditText editButtonText;
    private Spinner buttonSizeTypeSpinner;
    private Spinner buttonPositionTypeSpinner;
    private LinearLayout widthObjectLayout;
    private Spinner widthObjectSpinner;
    private LinearLayout heightObjectLayout;
    private Spinner heightObjectSpinner;
    private SeekBar buttonWidthSeekbar;
    private SeekBar buttonHeightSeekbar;
    private SeekBar buttonXSeekbar;
    private SeekBar buttonYSeekbar;
    private TextView buttonWidthText;
    private TextView buttonHeightText;
    private TextView buttonXText;
    private TextView buttonYText;
    private Spinner functionTypeSpinner;
    private SwitchCompat checkViewMove;
    private SwitchCompat checkAutoKeep;
    private SwitchCompat checkAutoClick;
    private SwitchCompat checkOpenMenu;
    private SwitchCompat checkMovable;
    private SwitchCompat checkTouchMode;
    private SwitchCompat checkSensor;
    private SwitchCompat checkLeftPad;
    private SwitchCompat checkOpenInput;
    private Button childVisibility;
    private EditText editOutputText;
    private Button outputKeycode;
    private Spinner selectExist;
    private SwitchCompat checkUsingExist;
    private Button createButtonStyle;
    private LinearLayout buttonStyleLayout;
    private SeekBar textSizeSeekbar;
    private SeekBar cornerRadiusSeekbar;
    private SeekBar strokeWidthSeekbar;
    private SeekBar textSizePressedSeekbar;
    private SeekBar cornerRadiusPressedSeekbar;
    private SeekBar strokeWidthPressedSeekbar;
    private TextView textSizeText;
    private TextView cornerRadiusText;
    private TextView strokeWidthText;
    private TextView textSizePressedText;
    private TextView cornerRadiusPressedText;
    private TextView strokeWidthPressedText;
    private Button selectTextColor;
    private Button selectStrokeColor;
    private Button selectFillColor;
    private Button selectTextColorPressed;
    private Button selectStrokeColorPressed;
    private Button selectFillColorPressed;
    private View textColorPre;
    private View strokeColorPre;
    private View fillColorPre;
    private View textColorPressedPre;
    private View strokeColorPressedPre;
    private View fillColorPressedPre;
    private TextView textColorText;
    private TextView strokeColorText;
    private TextView fillColorText;
    private TextView textColorPressedText;
    private TextView strokeColorPressedText;
    private TextView fillColorPressedText;

    public AddViewDialog(@NonNull Context context,int screenWidth,int screenHeight) {
        super(context);
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        viewType = 0;
        setContentView(R.layout.dialog_add_view);
        setCancelable(false);
        init();
    }

    private void init(){
        baseButtonInfo = new BaseButtonInfo("",
                BaseButtonInfo.SIZE_TYPE_ABSOLUTE,
                new ButtonSize(50,0.06f,BaseButtonInfo.SIZE_OBJECT_WIDTH),
                new ButtonSize(50,0.06f,BaseButtonInfo.SIZE_OBJECT_WIDTH),
                BaseButtonInfo.POSITION_TYPE_PERCENT,
                new ButtonPosition(0,0),
                new ButtonPosition(0,0),
                BaseButtonInfo.FUNCTION_TYPE_TOUCH,
                false,false,false,false,false,false,false,false,false,
                new ArrayList<>(),
                "",
                new ArrayList<>(),
                true,
                new ButtonStyle());

        showButton = findViewById(R.id.add_button);
        showRocker = findViewById(R.id.add_rocker);
        editButton = findViewById(R.id.add_button_layout);
        editRocker = findViewById(R.id.add_rocker_layout);

        positive = findViewById(R.id.add_current_view);
        negative = findViewById(R.id.exit);

        showButton.setOnClickListener(this);
        showRocker.setOnClickListener(this);
        positive.setOnClickListener(this);
        negative.setOnClickListener(this);

        ArrayList<String> sizeType = new ArrayList<>();
        ArrayList<String> positionType = new ArrayList<>();
        ArrayList<String> sizeObject = new ArrayList<>();
        ArrayList<String> functionType = new ArrayList<>();
        sizeType.add(getContext().getString(R.string.dialog_add_view_size_type_percent));
        sizeType.add(getContext().getString(R.string.dialog_add_view_size_type_absolute));
        positionType.add(getContext().getString(R.string.dialog_add_view_position_type_percent));
        positionType.add(getContext().getString(R.string.dialog_add_view_position_type_absolute));
        sizeObject.add(getContext().getString(R.string.dialog_add_view_size_type_percent_object_width));
        sizeObject.add(getContext().getString(R.string.dialog_add_view_size_type_percent_object_height));
        functionType.add(getContext().getString(R.string.dialog_add_view_button_function_type_click));
        functionType.add(getContext().getString(R.string.dialog_add_view_button_function_type_double_click));
        sizeTypeAdapter = new ArrayAdapter<>(getContext(),R.layout.item_spinner, sizeType);
        positionTypeAdapter = new ArrayAdapter<>(getContext(),R.layout.item_spinner, positionType);
        sizeObjectAdapter = new ArrayAdapter<>(getContext(),R.layout.item_spinner, sizeObject);
        functionTypeAdapter = new ArrayAdapter<>(getContext(),R.layout.item_spinner, functionType);

        initButtonLayout();
    }

    @SuppressLint("SetTextI18n")
    private void initButtonLayout(){
        editButtonText = findViewById(R.id.edit_button_text);
        buttonSizeTypeSpinner = findViewById(R.id.button_size_type);
        buttonPositionTypeSpinner = findViewById(R.id.button_position_type);
        widthObjectLayout = findViewById(R.id.width_object_layout);
        widthObjectSpinner = findViewById(R.id.width_object);
        heightObjectLayout = findViewById(R.id.height_object_layout);
        heightObjectSpinner = findViewById(R.id.height_object);
        buttonWidthSeekbar = findViewById(R.id.button_width_seekbar);
        buttonHeightSeekbar = findViewById(R.id.button_height_seekbar);
        buttonXSeekbar = findViewById(R.id.button_x_seekbar);
        buttonYSeekbar = findViewById(R.id.button_y_seekbar);
        buttonWidthText = findViewById(R.id.width_text);
        buttonHeightText = findViewById(R.id.height_text);
        buttonXText = findViewById(R.id.button_x_text);
        buttonYText = findViewById(R.id.button_y_text);
        functionTypeSpinner = findViewById(R.id.function_type);
        checkViewMove = findViewById(R.id.function_extra_perspective);
        checkAutoKeep = findViewById(R.id.function_extra_keep);
        checkAutoClick = findViewById(R.id.function_extra_auto_click);
        checkOpenMenu = findViewById(R.id.function_open_menu);
        checkMovable = findViewById(R.id.function_movable);
        checkTouchMode = findViewById(R.id.function_touch_mode);
        checkSensor = findViewById(R.id.function_sensor);
        checkLeftPad = findViewById(R.id.function_left_touch);
        checkOpenInput = findViewById(R.id.function_open_input);
        childVisibility = findViewById(R.id.function_child_visibility);
        editOutputText = findViewById(R.id.function_output_keychar);
        outputKeycode = findViewById(R.id.function_output_keycode);
        selectExist = findViewById(R.id.exterior_use_exist_button);
        checkUsingExist = findViewById(R.id.switch_exterior_use_exist_button);
        createButtonStyle = findViewById(R.id.exterior_add_button_style);
        buttonStyleLayout = findViewById(R.id.button_style_layout);
        textSizeSeekbar = findViewById(R.id.exterior_text_size_seekbar);
        cornerRadiusSeekbar = findViewById(R.id.exterior_corner_radius_seekbar);
        strokeWidthSeekbar = findViewById(R.id.exterior_stroke_width_seekbar);
        textSizePressedSeekbar = findViewById(R.id.exterior_text_size_seekbar_pressed);
        cornerRadiusPressedSeekbar = findViewById(R.id.exterior_corner_radius_seekbar_pressed);
        strokeWidthPressedSeekbar = findViewById(R.id.exterior_stroke_width_seekbar_pressed);
        textSizeText = findViewById(R.id.text_size_text);
        cornerRadiusText = findViewById(R.id.corner_radius_text);
        strokeWidthText = findViewById(R.id.stroke_width_text);
        textSizePressedText = findViewById(R.id.text_size_pressed_text);
        cornerRadiusPressedText = findViewById(R.id.corner_radius_pressed_text);
        strokeWidthPressedText = findViewById(R.id.stroke_width_pressed_text);
        selectTextColor = findViewById(R.id.exterior_text_color);
        selectStrokeColor = findViewById(R.id.exterior_stroke_color);
        selectFillColor = findViewById(R.id.exterior_fill_color);
        selectTextColorPressed = findViewById(R.id.exterior_text_color_pressed);
        selectStrokeColorPressed = findViewById(R.id.exterior_stroke_color_pressed);
        selectFillColorPressed = findViewById(R.id.exterior_fill_color_pressed);
        textColorPre = findViewById(R.id.text_color_preview);
        strokeColorPre = findViewById(R.id.stroke_color_preview);
        fillColorPre = findViewById(R.id.fill_color_preview);
        textColorPressedPre = findViewById(R.id.text_color_pressed_preview);
        strokeColorPressedPre = findViewById(R.id.stroke_color_pressed_preview);
        fillColorPressedPre = findViewById(R.id.fill_color_pressed_preview);
        textColorText = findViewById(R.id.text_color_text);
        strokeColorText = findViewById(R.id.stroke_color_text);
        fillColorText = findViewById(R.id.fill_color_text);
        textColorPressedText = findViewById(R.id.text_color_pressed_text);
        strokeColorPressedText = findViewById(R.id.stroke_color_pressed_text);
        fillColorPressedText = findViewById(R.id.fill_color_pressed_text);

        editButtonText.setText(baseButtonInfo.text);
        editOutputText.setText(baseButtonInfo.outputText);
        buttonSizeTypeSpinner.setAdapter(sizeTypeAdapter);
        buttonSizeTypeSpinner.setSelection(baseButtonInfo.sizeType);
        buttonPositionTypeSpinner.setAdapter(positionTypeAdapter);
        buttonPositionTypeSpinner.setSelection(baseButtonInfo.positionType);
        if (baseButtonInfo.sizeType == BaseButtonInfo.SIZE_TYPE_ABSOLUTE) {
            widthObjectLayout.setVisibility(View.GONE);
            heightObjectLayout.setVisibility(View.GONE);
        }
        widthObjectSpinner.setAdapter(sizeObjectAdapter);
        widthObjectSpinner.setSelection(baseButtonInfo.width.object);
        heightObjectSpinner.setAdapter(sizeObjectAdapter);
        heightObjectSpinner.setSelection(baseButtonInfo.height.object);
        functionTypeSpinner.setAdapter(functionTypeAdapter);
        functionTypeSpinner.setSelection(baseButtonInfo.functionType);
        if (baseButtonInfo.sizeType == BaseButtonInfo.SIZE_TYPE_PERCENT) {
            buttonWidthSeekbar.setProgress((int) (100 * baseButtonInfo.width.percentSize));
            buttonHeightSeekbar.setProgress((int) (100 * baseButtonInfo.height.percentSize));
            buttonWidthText.setText((int) (100 * baseButtonInfo.width.percentSize) + " %");
            buttonHeightText.setText((int) (100 * baseButtonInfo.height.percentSize) + " %");
        }
        else {
            buttonWidthSeekbar.setProgress(baseButtonInfo.width.absoluteSize);
            buttonHeightSeekbar.setProgress(baseButtonInfo.height.absoluteSize);
            buttonWidthText.setText(baseButtonInfo.width.absoluteSize + " dp");
            buttonHeightText.setText(baseButtonInfo.height.absoluteSize + " dp");
        }
        if (baseButtonInfo.positionType == BaseButtonInfo.POSITION_TYPE_PERCENT) {
            buttonXSeekbar.setProgress((int) (100 * baseButtonInfo.xPosition.percentPosition));
            buttonYSeekbar.setProgress((int) (100 * baseButtonInfo.yPosition.percentPosition));
            buttonXText.setText((int) (100 * baseButtonInfo.xPosition.percentPosition) + " %");
            buttonYText.setText((int) (100 * baseButtonInfo.yPosition.percentPosition) + " %");
        }
        else {
            buttonXSeekbar.setProgress(baseButtonInfo.xPosition.absolutePosition);
            buttonYSeekbar.setProgress(baseButtonInfo.yPosition.absolutePosition);
            buttonXText.setText(baseButtonInfo.xPosition.absolutePosition + " dp");
            buttonYText.setText(baseButtonInfo.yPosition.absolutePosition + " dp");
        }
        checkViewMove.setChecked(baseButtonInfo.viewMove);
        checkAutoKeep.setChecked(baseButtonInfo.autoKeep);
        checkAutoClick.setChecked(baseButtonInfo.autoClick);
        checkOpenMenu.setChecked(baseButtonInfo.openMenu);
        checkMovable.setChecked(baseButtonInfo.movable);
        checkTouchMode.setChecked(baseButtonInfo.switchTouchMode);
        checkSensor.setChecked(baseButtonInfo.switchSensor);
        checkLeftPad.setChecked(baseButtonInfo.switchLeftPad);
        checkOpenInput.setChecked(baseButtonInfo.showInputDialog);
        checkUsingExist.setChecked(baseButtonInfo.usingExist);
        if (baseButtonInfo.usingExist) {
            buttonStyleLayout.setVisibility(View.GONE);
        }
        textSizeSeekbar.setProgress(baseButtonInfo.buttonStyle.textSize);
        cornerRadiusSeekbar.setProgress(baseButtonInfo.buttonStyle.cornerRadius);
        strokeWidthSeekbar.setProgress((int) (baseButtonInfo.buttonStyle.strokeWidth * 10));
        textSizePressedSeekbar.setProgress(baseButtonInfo.buttonStyle.textSizePress);
        cornerRadiusPressedSeekbar.setProgress(baseButtonInfo.buttonStyle.cornerRadiusPress);
        strokeWidthPressedSeekbar.setProgress((int) (baseButtonInfo.buttonStyle.strokeWidthPress * 10));
        textSizeText.setText(baseButtonInfo.buttonStyle.textSize + " sp");
        cornerRadiusText.setText(baseButtonInfo.buttonStyle.cornerRadius + " dp");
        strokeWidthText.setText(baseButtonInfo.buttonStyle.strokeWidth + " dp");
        textSizePressedText.setText(baseButtonInfo.buttonStyle.textSizePress + " sp");
        cornerRadiusPressedText.setText(baseButtonInfo.buttonStyle.cornerRadiusPress + " dp");
        strokeWidthPressedText.setText(baseButtonInfo.buttonStyle.strokeWidthPress + " dp");
        textColorPre.setBackgroundColor(Color.parseColor(baseButtonInfo.buttonStyle.textColor));
        strokeColorPre.setBackgroundColor(Color.parseColor(baseButtonInfo.buttonStyle.strokeColor));
        fillColorPre.setBackgroundColor(Color.parseColor(baseButtonInfo.buttonStyle.fillColor));
        textColorPressedPre.setBackgroundColor(Color.parseColor(baseButtonInfo.buttonStyle.textColorPress));
        strokeColorPressedPre.setBackgroundColor(Color.parseColor(baseButtonInfo.buttonStyle.strokeColorPress));
        fillColorPressedPre.setBackgroundColor(Color.parseColor(baseButtonInfo.buttonStyle.fillColorPress));
        textColorText.setText(baseButtonInfo.buttonStyle.textColor);
        strokeColorText.setText(baseButtonInfo.buttonStyle.strokeColor);
        fillColorText.setText(baseButtonInfo.buttonStyle.fillColor);
        textColorPressedText.setText(baseButtonInfo.buttonStyle.textColorPress);
        strokeColorPressedText.setText(baseButtonInfo.buttonStyle.strokeColorPress);
        fillColorPressedText.setText(baseButtonInfo.buttonStyle.fillColorPress);

        buttonSizeTypeSpinner.setOnItemSelectedListener(this);
        buttonPositionTypeSpinner.setOnItemSelectedListener(this);
        widthObjectSpinner.setOnItemSelectedListener(this);
        heightObjectSpinner.setOnItemSelectedListener(this);
        functionTypeSpinner.setOnItemSelectedListener(this);
        selectExist.setOnItemSelectedListener(this);
        buttonWidthSeekbar.setOnSeekBarChangeListener(this);
        buttonHeightSeekbar.setOnSeekBarChangeListener(this);
        buttonXSeekbar.setOnSeekBarChangeListener(this);
        buttonYSeekbar.setOnSeekBarChangeListener(this);
        textSizeSeekbar.setOnSeekBarChangeListener(this);
        cornerRadiusSeekbar.setOnSeekBarChangeListener(this);
        strokeWidthSeekbar.setOnSeekBarChangeListener(this);
        textSizePressedSeekbar.setOnSeekBarChangeListener(this);
        cornerRadiusPressedSeekbar.setOnSeekBarChangeListener(this);
        strokeWidthPressedSeekbar.setOnSeekBarChangeListener(this);
        checkViewMove.setOnCheckedChangeListener(this);
        checkAutoKeep.setOnCheckedChangeListener(this);
        checkAutoClick.setOnCheckedChangeListener(this);
        checkOpenMenu.setOnCheckedChangeListener(this);
        checkMovable.setOnCheckedChangeListener(this);
        checkTouchMode.setOnCheckedChangeListener(this);
        checkSensor.setOnCheckedChangeListener(this);
        checkLeftPad.setOnCheckedChangeListener(this);
        checkOpenInput.setOnCheckedChangeListener(this);
        checkUsingExist.setOnCheckedChangeListener(this);
        childVisibility.setOnClickListener(this);
        outputKeycode.setOnClickListener(this);
        createButtonStyle.setOnClickListener(this);
        selectTextColor.setOnClickListener(this);
        selectStrokeColor.setOnClickListener(this);
        selectFillColor.setOnClickListener(this);
        selectTextColorPressed.setOnClickListener(this);
        selectStrokeColorPressed.setOnClickListener(this);
        selectFillColorPressed.setOnClickListener(this);
        editButtonText.addTextChangedListener(this);
        editOutputText.addTextChangedListener(this);
    }

    public void refreshButtonStyleList(){
        ArrayList<ButtonStyle> styles = SettingUtils.getButtonStyleList();
        ArrayList<String> names = new ArrayList<>();
        for (ButtonStyle style : styles){
            names.add(style.name);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),R.layout.item_spinner,names);
        selectExist.setAdapter(adapter);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {
        if (view == showButton){
            viewType = 0;
            showButton.setBackgroundColor(getContext().getColor(R.color.colorPureWhite));
            showRocker.setBackgroundColor(getContext().getColor(R.color.colorLightGray));
            editButton.setVisibility(View.VISIBLE);
            editRocker.setVisibility(View.GONE);
        }
        if (view == showRocker){
            viewType = 1;
            showButton.setBackgroundColor(getContext().getColor(R.color.colorLightGray));
            showRocker.setBackgroundColor(getContext().getColor(R.color.colorPureWhite));
            editButton.setVisibility(View.GONE);
            editRocker.setVisibility(View.VISIBLE);
        }
        if (view == positive){
            if (viewType == 0){

            }
            if (viewType == 1){

            }
        }
        if (view == negative){
            dismiss();
        }

        if (view == childVisibility) {

        }
        if (view == outputKeycode) {

        }
        if (view == createButtonStyle) {

        }
        if (view == selectTextColor) {
            ColorSelectorDialog colorSelectorDialog = new ColorSelectorDialog(getContext(),false,Color.parseColor(baseButtonInfo.buttonStyle.textColor));
            colorSelectorDialog.setColorSelectorDialogListener(new ColorSelectorDialog.ColorSelectorDialogListener() {
                @Override
                public void onColorSelected(int color) {

                }

                @Override
                public void onPositive(int destColor) {
                    textColorPre.setBackgroundColor(destColor);
                    textColorText.setText("#" + Integer.toHexString(destColor));
                    baseButtonInfo.buttonStyle.textColor = "#" + Integer.toHexString(destColor);
                }

                @Override
                public void onNegative(int initColor) {

                }
            });
            colorSelectorDialog.show();
        }
        if (view == selectStrokeColor) {
            ColorSelectorDialog colorSelectorDialog = new ColorSelectorDialog(getContext(),false,Color.parseColor(baseButtonInfo.buttonStyle.strokeColor));
            colorSelectorDialog.setColorSelectorDialogListener(new ColorSelectorDialog.ColorSelectorDialogListener() {
                @Override
                public void onColorSelected(int color) {

                }

                @Override
                public void onPositive(int destColor) {
                    strokeColorPre.setBackgroundColor(destColor);
                    strokeColorText.setText("#" + Integer.toHexString(destColor));
                    baseButtonInfo.buttonStyle.strokeColor = "#" + Integer.toHexString(destColor);
                }

                @Override
                public void onNegative(int initColor) {

                }
            });
            colorSelectorDialog.show();
        }
        if (view == selectFillColor) {
            ColorSelectorDialog colorSelectorDialog = new ColorSelectorDialog(getContext(),false,Color.parseColor(baseButtonInfo.buttonStyle.fillColor));
            colorSelectorDialog.setColorSelectorDialogListener(new ColorSelectorDialog.ColorSelectorDialogListener() {
                @Override
                public void onColorSelected(int color) {

                }

                @Override
                public void onPositive(int destColor) {
                    fillColorPre.setBackgroundColor(destColor);
                    fillColorText.setText("#" + Integer.toHexString(destColor));
                    baseButtonInfo.buttonStyle.fillColor = "#" + Integer.toHexString(destColor);
                }

                @Override
                public void onNegative(int initColor) {

                }
            });
            colorSelectorDialog.show();
        }
        if (view == selectTextColorPressed) {
            ColorSelectorDialog colorSelectorDialog = new ColorSelectorDialog(getContext(),false,Color.parseColor(baseButtonInfo.buttonStyle.textColorPress));
            colorSelectorDialog.setColorSelectorDialogListener(new ColorSelectorDialog.ColorSelectorDialogListener() {
                @Override
                public void onColorSelected(int color) {

                }

                @Override
                public void onPositive(int destColor) {
                    textColorPressedPre.setBackgroundColor(destColor);
                    textColorPressedText.setText("#" + Integer.toHexString(destColor));
                    baseButtonInfo.buttonStyle.textColorPress = "#" + Integer.toHexString(destColor);
                }

                @Override
                public void onNegative(int initColor) {

                }
            });
            colorSelectorDialog.show();
        }
        if (view == selectStrokeColorPressed) {
            ColorSelectorDialog colorSelectorDialog = new ColorSelectorDialog(getContext(),false,Color.parseColor(baseButtonInfo.buttonStyle.strokeColorPress));
            colorSelectorDialog.setColorSelectorDialogListener(new ColorSelectorDialog.ColorSelectorDialogListener() {
                @Override
                public void onColorSelected(int color) {

                }

                @Override
                public void onPositive(int destColor) {
                    strokeColorPressedPre.setBackgroundColor(destColor);
                    strokeColorPressedText.setText("#" + Integer.toHexString(destColor));
                    baseButtonInfo.buttonStyle.strokeColorPress = "#" + Integer.toHexString(destColor);
                }

                @Override
                public void onNegative(int initColor) {

                }
            });
            colorSelectorDialog.show();
        }
        if (view == selectFillColorPressed) {
            ColorSelectorDialog colorSelectorDialog = new ColorSelectorDialog(getContext(),false,Color.parseColor(baseButtonInfo.buttonStyle.fillColorPress));
            colorSelectorDialog.setColorSelectorDialogListener(new ColorSelectorDialog.ColorSelectorDialogListener() {
                @Override
                public void onColorSelected(int color) {

                }

                @Override
                public void onPositive(int destColor) {
                    fillColorPressedPre.setBackgroundColor(destColor);
                    fillColorPressedText.setText("#" + Integer.toHexString(destColor));
                    baseButtonInfo.buttonStyle.fillColorPress = "#" + Integer.toHexString(destColor);
                }

                @Override
                public void onNegative(int initColor) {

                }
            });
            colorSelectorDialog.show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (adapterView == buttonSizeTypeSpinner) {

        }
        if (adapterView == buttonPositionTypeSpinner) {

        }
        if (adapterView == widthObjectSpinner) {

        }
        if (adapterView == heightObjectSpinner) {

        }
        if (adapterView == functionTypeSpinner) {

        }
        if (adapterView == selectExist) {

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        if (seekBar == buttonWidthSeekbar) {

        }
        if (seekBar == buttonHeightSeekbar) {

        }
        if (seekBar == buttonXSeekbar) {

        }
        if (seekBar == buttonYSeekbar) {

        }
        if (seekBar == textSizeSeekbar) {
            textSizeText.setText(i + " sp");
            baseButtonInfo.buttonStyle.textSize = i;
        }
        if (seekBar == cornerRadiusSeekbar) {
            cornerRadiusText.setText(i + " dp");
            baseButtonInfo.buttonStyle.cornerRadius = i;
        }
        if (seekBar == strokeWidthSeekbar) {
            strokeWidthText.setText((float) i / 10f + " dp");
            baseButtonInfo.buttonStyle.strokeWidth = (float) i / 10f;
        }
        if (seekBar == textSizePressedSeekbar) {
            textSizePressedText.setText(i + " sp");
            baseButtonInfo.buttonStyle.textSizePress = i;
        }
        if (seekBar == cornerRadiusPressedSeekbar) {
            cornerRadiusPressedText.setText(i + " dp");
            baseButtonInfo.buttonStyle.cornerRadiusPress = i;
        }
        if (seekBar == strokeWidthPressedSeekbar) {
            strokeWidthPressedText.setText((float) i / 10f + " dp");
            baseButtonInfo.buttonStyle.strokeWidthPress = (float) i / 10f;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (compoundButton == checkViewMove) {
            baseButtonInfo.viewMove = b;
        }
        if (compoundButton == checkAutoKeep) {
            baseButtonInfo.autoKeep = b;
        }
        if (compoundButton == checkAutoClick) {
            baseButtonInfo.autoClick = b;
        }
        if (compoundButton == checkOpenMenu) {
            baseButtonInfo.openMenu = b;
        }
        if (compoundButton == checkMovable) {
            baseButtonInfo.movable = b;
        }
        if (compoundButton == checkTouchMode) {
            baseButtonInfo.switchTouchMode = b;
        }
        if (compoundButton == checkSensor) {
            baseButtonInfo.switchSensor = b;
        }
        if (compoundButton == checkLeftPad) {
            baseButtonInfo.switchLeftPad = b;
        }
        if (compoundButton == checkOpenInput) {
            baseButtonInfo.showInputDialog = b;
        }
        if (compoundButton == checkUsingExist) {
            baseButtonInfo.usingExist = b;
            if (b) {
                buttonStyleLayout.setVisibility(View.GONE);
            }
            else {
                buttonStyleLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}