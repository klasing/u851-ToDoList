package com.example.todolist.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.example.todolist.data.TaskContract.TaskEntry.TABLE_NAME;

public class TaskContentProvider extends ContentProvider {
    public static final int TASKS = 100;
    public static final int TASK_WITH_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {

        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(TaskContract.AUTHORITY, TaskContract.PATH_TASKS, TASKS);
        uriMatcher.addURI(TaskContract.AUTHORITY, TaskContract.PATH_TASKS + "/#", TASK_WITH_ID);

        return uriMatcher;
    }

    private TaskDbHelper mTaskDbHelper;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mTaskDbHelper = new TaskDbHelper(context);
        return true;
    }

   @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        //throw new UnsupportedOperationException("Not yet implemented");
       final SQLiteDatabase db = mTaskDbHelper.getWritableDatabase();

       int match = sUriMatcher.match(uri);
       Uri returnUri; // URI to be returned

       switch (match) {
           case TASKS:
              long id = db.insert(TABLE_NAME, null, values);
               if ( id > 0 ) {
                   returnUri = ContentUris.withAppendedId(TaskContract.TaskEntry.CONTENT_URI, id);
               } else {
                   throw new android.database.SQLException("Failed to insert row into " + uri);
               }
               break;
           default:
               throw new UnsupportedOperationException("Unknown uri: " + uri);
       }

       getContext().getContentResolver().notifyChange(uri, null);

       return returnUri;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}