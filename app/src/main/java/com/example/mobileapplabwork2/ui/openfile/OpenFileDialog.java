package com.example.mobileapplabwork2.ui.openfile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.example.mobileapplabwork2.MainActivity.GlobalFolderName;
import static com.example.mobileapplabwork2.MainActivity.GlobalFilessName;

import com.example.mobileapplabwork2.MainActivity;
import com.example.mobileapplabwork2.MainActivity2;
import com.example.mobileapplabwork2.R;

public class OpenFileDialog extends AlertDialog.Builder{
    private String currentPath = Environment.getExternalStorageDirectory().getPath();
    private File directory;
    private List<File> fileList;
    private List<File> files = new ArrayList<File>();
    private ListView listView;
    private LinearLayout linearLayout;
    private TextView title;
    private TextView textView;
    private Drawable drawable;
    private FilenameFilter filenameFilter;
    private int selectedIndex = -1;
    private OpenDialogListener listener;
    private Drawable folderIcon;
    private Drawable fileIcon;
    private String accessDeniedMessage;
    private boolean isOnlyFoldersFilter;

    private String fileNameSt, fileExtSt;
    private int fileNameLength;

    public interface OpenDialogListener {
        public void OnSelectedFile(String fileName);
    }

    public OpenFileDialog(Context context) {
        super(context);

        folderIcon = context.getResources().getDrawable(R.drawable.file_icon_folder, null);
        fileIcon = context.getResources().getDrawable(R.drawable.file_icon_file, null);

        setPositiveButton(R.string.ok, (dialog, which) -> {
            if (selectedIndex > -1 && listener != null) {
                listener.OnSelectedFile(listView.getItemAtPosition(selectedIndex).toString());
            }

            if (listener != null && isOnlyFoldersFilter) {
                listener.OnSelectedFile(currentPath);
            }

            if (MainActivity.OFD_ButtonPress == 2) {
                MainActivity.fileRead_SD(GlobalFolderName, GlobalFilessName, this.getContext());
            }
            else if (MainActivity.OFD_ButtonPress == 4) {
                MainActivity.OFD_ButtonPress = 0;
                MainActivity.FragmentStart = 1;
                MainActivity.fileWrite_SD(currentPath, fileNameSt, getContext());
            }
            else {
                Intent intent;
                intent = new Intent(getContext(), MainActivity2.class);
                getContext().startActivity(intent);
            }
        });

        setNegativeButton(R.string.cancel, (dialog, which) -> {
        });

        title = createTitle(context);
        linearLayout = createMainLayout(context);
        linearLayout.addView(createBackItem(context));
        files.addAll(getFiles(currentPath));
        listView = createListView(context);
        listView.setAdapter(new FileAdapter(context, files));
        linearLayout.addView(listView);
        setCustomTitle(title);
        setView(linearLayout);
        changeTitle();
    }

    private List<File> getFiles(String directoryPath) {
        directory = new File(directoryPath);

        if (directory.listFiles() != null) {
            File[] list = directory.listFiles(filenameFilter);
            if (list == null) {
                list = new File[]{};
            }
            fileList = Arrays.asList(list);

            Collections.sort(fileList, (file, file2) -> {
                if (file.isDirectory() && file2.isFile()) {
                    return -1;
                } else if (file.isFile() && file2.isDirectory()) {
                    return 1;
                } else {
                    return file.getPath().compareTo(file2.getPath());
                }
            });

            return fileList;
        } else {
            return Collections.emptyList();
        }
    }

    private ListView createListView(Context context) {
        ListView listView = new ListView(context);
        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            final ArrayAdapter<File> adapter = (FileAdapter) adapterView.getAdapter();
            File file = adapter.getItem(position);

            if (file.isDirectory()) {
                currentPath = file.getPath();
                GlobalFolderName = currentPath;
                GlobalFilessName = "";
                RebuildFiles(adapter);
                changeTitle();
            } else {
                if (position != selectedIndex) {
                    selectedIndex = position;
                } else {
                    selectedIndex = -1;
                }
                adapter.notifyDataSetChanged();
                GlobalFilessName = file.getName();
            }
        });

        return listView;
    }

    private void RebuildFiles(ArrayAdapter<File> adapter) {
        try {
            fileList = getFiles(currentPath);
            files.clear();
            selectedIndex = -1;
            files.addAll(fileList);
            adapter.notifyDataSetChanged();
            changeTitle();
        } catch (NullPointerException e) {
            String message = getContext().getResources().getString(R.string.NullPointerEx);
            if (!accessDeniedMessage.equals("")) {
                message = accessDeniedMessage;
            }
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    private static Display getDefaultDisplay(Context context) {
        return ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
    }

    private static Point getScreenSize(Context context) {
        Point screenSize = new Point();
        getDefaultDisplay(context).getSize(screenSize);
        return screenSize;
    }

    private static int getLinearLayoutMinheight(Context context) {
        return getScreenSize(context).y;
    }

    private static int getItemHeight(Context context) {
        TypedValue value = new TypedValue();
        DisplayMetrics metrics = new DisplayMetrics();
        context.getTheme().resolveAttribute(android.R.attr.height, value, true);
        getDefaultDisplay(context).getMetrics(metrics);
        return (int) TypedValue.complexToDimension(value.data, metrics);
    }

    private LinearLayout createMainLayout(Context context) {
        linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setMinimumHeight(getLinearLayoutMinheight(context));
        return linearLayout;
    }

    private TextView createTextView(Context context, int style) {
        textView = new TextView(context);
        textView.setTextAppearance(context, android.R.style.TextAppearance_DeviceDefault_Widget_ActionBar_Title);
        int itemHeight = getItemHeight(context);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemHeight));
        textView.setMinHeight(itemHeight);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setPadding(15, 0, 0, 0);
        textView.setText(currentPath);
        return textView;
    }

    private TextView createTitle(Context context) {
        textView = createTextView(context, android.R.style.TextAppearance_DeviceDefault_Widget_ActionBar_Title);
        return textView;
    }

    private TextView createBackItem(Context context) {
        textView = createTextView(context, android.R.style.TextAppearance_DeviceDefault_Widget_ActionBar_Title);

        drawable = getContext().getResources().getDrawable(R.drawable.file_icon_back, null);
        drawable.setBounds(0, 0, 60, 60);
        textView.setCompoundDrawables(drawable, null, null, null);
        textView.setText(" . . . ");

        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        textView.setOnClickListener(v -> {
            File file = new File(currentPath);
            File parentDirectory = file.getParentFile();

            if (parentDirectory != null) {
                currentPath = parentDirectory.getPath();
                RebuildFiles(((FileAdapter) listView.getAdapter()));
                changeTitle();
            }
        });

        return textView;
    }

    public int getTextWidth(String text, Paint paint) {
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.left + bounds.width() + 80;
    }

    public OpenFileDialog setFolderIcon(Drawable drawable) {
        this.folderIcon = drawable;
        return this;
    }

    public OpenFileDialog setFileIcon(Drawable drawable) {
        this.fileIcon = drawable;
        return this;
    }

    public OpenFileDialog setAccessDeniedMessage(String message) {
        this.accessDeniedMessage = message;
        return this;
    }

    public OpenFileDialog setOpenDialogListener(OpenDialogListener listener) {
        this.listener = listener;
        return this;
    }

    public OpenFileDialog setFilter(final String filter) {
        filenameFilter = (dir, name) -> {
            File tempFile = new File(String.format("%s/%s", dir.getPath(), name));
            if (tempFile.isFile()) {
                return tempFile.getName().matches(filter);
            }
            return true;
        };

        return this;
    }

    public OpenFileDialog setOnlyFoldersFilter() {
        isOnlyFoldersFilter = true;
        filenameFilter = (dir, name) -> {
            File tempFile = new File(String.format("%s/%s", dir.getPath(), name));
            return tempFile.isDirectory();
        };

        return this;
    }

    @Override
    public AlertDialog show() {
        files.addAll(getFiles(currentPath));
        listView.setAdapter(new FileAdapter(getContext(), files));
        return super.show();
    }

    private void changeTitle() {
        String titleText = currentPath;
        GlobalFolderName = currentPath;
        String titleFragment = "";
        int textWidth = getTextWidth(titleText, title.getPaint());
        int maxWidth = (int) ((getScreenSize(getContext()).x) * 0.8);
        double textScreen = ((double) textWidth) / ((double) maxWidth);
        int textFragmentCount = (int) (Math.ceil(textScreen));
        int screenSymbolCount = (int) (Math.ceil(((double) titleText.length()) / textScreen));
        int i = 0;
        int j = 0;

        if (textWidth > maxWidth) {
            while (i <= textFragmentCount - 1) {
                if (i < textFragmentCount - 1) {
                    titleFragment = titleFragment + titleText.substring(j, (j + screenSymbolCount)) + "\n";
                    i++;
                    j = screenSymbolCount * i;
                }

                if (i == textFragmentCount - 1) {
                    titleFragment = titleFragment + titleText.substring(j, titleText.length()) + "\n";
                    i++;
                    j = screenSymbolCount * i;
                }
            }
            title.setText(titleFragment);
        } else {
            title.setText(titleText);
        }
    }

    private void setDrawable(TextView view, Drawable folderIcon) {
        if (view != null) {
            Drawable drawable = folderIcon;
            drawable.setBounds(0, 0, 40, 40);
            view.setCompoundDrawables(drawable, null, null, null);
        }
    }

    private void setDrawableF(TextView view, Drawable fileIcon) {
        if (view != null) {
            if (fileNameSt != null) {
                fileNameLength = fileNameSt.length();

                if (fileNameLength > 4 && fileNameSt.lastIndexOf(".") > 0) {
                    int dotIndex = fileNameSt.lastIndexOf(".");
                    if (dotIndex < fileNameLength - 5) {
                        drawable = getContext().getResources().getDrawable(R.drawable.file_icon_file, null);
                    } else {
                        fileExtSt = fileNameSt.substring(dotIndex + 1).toLowerCase();

                        switch (fileExtSt) {
                            case "avi":
                                drawable = getContext().getResources().getDrawable(R.drawable.file_icon_avi, null);
                                break;
                            case "doc":
                                drawable = getContext().getResources().getDrawable(R.drawable.file_icon_doc, null);
                                break;
                            case "flv":
                                drawable = getContext().getResources().getDrawable(R.drawable.file_icon_flv, null);
                                break;
                            case "gif":
                                drawable = getContext().getResources().getDrawable(R.drawable.file_icon_gif, null);
                                break;
                            case "jpg":
                                drawable = getContext().getResources().getDrawable(R.drawable.file_icon_jpg, null);
                                break;
                            case "jpeg":
                                drawable = getContext().getResources().getDrawable(R.drawable.file_icon_jpg, null);
                                break;
                            case "png":
                                drawable = getContext().getResources().getDrawable(R.drawable.file_icon_jpg, null);
                                break;
                            case "mov":
                                drawable = getContext().getResources().getDrawable(R.drawable.file_icon_mov, null);
                                break;
                            case "mp3":
                                drawable = getContext().getResources().getDrawable(R.drawable.file_icon_mp3, null);
                                break;
                            case "mpg":
                                drawable = getContext().getResources().getDrawable(R.drawable.file_icon_mpg, null);
                                break;
                            case "mpeg":
                                drawable = getContext().getResources().getDrawable(R.drawable.file_icon_mpg, null);
                                break;
                            case "pdf":
                                drawable = getContext().getResources().getDrawable(R.drawable.file_icon_pdf, null);
                                break;
                            case "ppt":
                                drawable = getContext().getResources().getDrawable(R.drawable.file_icon_ppt, null);
                                break;
                            case "rtf":
                                drawable = getContext().getResources().getDrawable(R.drawable.file_icon_rtf, null);
                                break;
                            case "txt":
                                drawable = getContext().getResources().getDrawable(R.drawable.file_icon_txt, null);
                                break;
                            case "wav":
                                drawable = getContext().getResources().getDrawable(R.drawable.file_icon_wav, null);
                                break;
                            case "wmv":
                                drawable = getContext().getResources().getDrawable(R.drawable.file_icon_wmv, null);
                                break;
                            case "xls":
                                drawable = getContext().getResources().getDrawable(R.drawable.file_icon_xls, null);
                                break;
                            case "zip":
                                drawable = getContext().getResources().getDrawable(R.drawable.file_icon_zip, null);
                                break;
                            case "rar":
                                drawable = getContext().getResources().getDrawable(R.drawable.file_icon_zip, null);
                                break;
                            default:
                                drawable = getContext().getResources().getDrawable(R.drawable.file_icon_file, null);
                                break;
                        }
                    }
                } else {
                    drawable = getContext().getResources().getDrawable(R.drawable.file_icon_file, null);
                }

                drawable.setBounds(0, 0, 40, 40);
                view.setCompoundDrawables(drawable, null, null, null);
            }
        }
    }

    private class FileAdapter extends ArrayAdapter<File> {
        private File file;
        private TextView view;
        private Context context = null;

        public FileAdapter(Context context, List<File> files) {
            super(context, android.R.layout.simple_list_item_1, files);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            view = (TextView) super.getView(position, convertView, parent);
            file = getItem(position);
            view.setText(file.getName());

            if (file.isDirectory()) {
                setDrawable(view, folderIcon);
            } else {
                fileNameSt = file.getName();
                GlobalFolderName = file.getParent();
                setDrawableF(view, fileIcon);
            }

            if (selectedIndex == position) {
                view.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.holo_green_light));
            } else {
                view.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.white));
            }

            return view;
        }
    }
}
