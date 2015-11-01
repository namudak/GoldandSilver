package com.sb.goldandsilver.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class GSProvider extends ContentProvider{

    private SQLiteDatabase db;

    private GSUrlHelper mDbHelper;
    //private GSOpenHelper mDbHelper;

    public static final UriMatcher uriMatcher= new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(GSContract.AUTHORITY, GSContract.TABLE, GSContract.TASKS_LIST);
        uriMatcher.addURI(GSContract.AUTHORITY, GSContract.TABLE+ "/#", GSContract.TASKS_ITEM);
    }

    @Override
    public boolean onCreate() {
        boolean ret= true;
        //mDbHelper= new GSOpenHelper(getContext());
        mDbHelper = mDbHelper.getInstance(getContext());
        db= mDbHelper.getWritableDatabase();

        if (db== null) {
            ret= false;
        }

        if (db.isReadOnly()) {
            db.close();
            db= null;
            ret= false;
        }

        return ret;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteQueryBuilder qb= new SQLiteQueryBuilder();
        qb.setTables(GSContract.TABLE);

        switch (uriMatcher.match(uri)) {
            case GSContract.TASKS_LIST:
                break;

            case GSContract.TASKS_ITEM:
                qb.appendWhere(GSContract.Columns._ID+ "= "+ uri.getLastPathSegment());
                break;

            default:
                throw new IllegalArgumentException("Invalid URI: "+ uri);
        }

        Cursor cursor= qb.query(db,projection,selection,selectionArgs,null,null,sortOrder);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {

        switch (uriMatcher.match(uri)) {
            case GSContract.TASKS_LIST:
                return GSContract.CONTENT_TYPE;

            case GSContract.TASKS_ITEM:
                return GSContract.CONTENT_ITEM_TYPE;

            default:
                throw new IllegalArgumentException("Invalid URI: "+uri);
        }

    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {

        if (uriMatcher.match(uri) != GSContract.TASKS_LIST) {
            throw new IllegalArgumentException("Invalid URI: "+uri);
        }

        long id= db.insert(GSContract.TABLE,null,contentValues);

        if (id>0) {
            return ContentUris.withAppendedId(uri, id);
        }
        throw new SQLException("Error inserting into table: "+ GSContract.TABLE);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        int deleted= 0;

        switch (uriMatcher.match(uri)) {
            case GSContract.TASKS_LIST:
                db.delete(GSContract.TABLE,selection,selectionArgs);
                break;

            case GSContract.TASKS_ITEM:
                String where= GSContract.Columns._ID+ "= "+ uri.getLastPathSegment();
                if (!selection.isEmpty()) {
                    where += " AND "+selection;
                }

                deleted= db.delete(GSContract.TABLE,where,selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Invalid URI: "+uri);
        }

        return deleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {

        int updated= 0;

        switch (uriMatcher.match(uri)) {
            case GSContract.TASKS_LIST:
                db.update(GSContract.TABLE,contentValues,s,strings);
                break;

            case GSContract.TASKS_ITEM:
                String where= GSContract.Columns._ID+ "= "+ uri.getLastPathSegment();
                if (!s.isEmpty()) {
                    where += " AND "+s;
                }
                updated= db.update(GSContract.TABLE,contentValues,where,strings);
                break;

            default:
                throw new IllegalArgumentException("Invalid URI: "+uri);
        }

        return updated;
    }
}
