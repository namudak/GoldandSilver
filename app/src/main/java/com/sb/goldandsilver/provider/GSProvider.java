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

public class GsProvider extends ContentProvider{

    private SQLiteDatabase db;

    //private GsUrlHelper mDbHelper;
    private GsOpenHelper mDbHelper;

    public static final UriMatcher uriMatcher= new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(GsContract.AUTHORITY, GsContract.TABLE, GsContract.TASKS_LIST);
        uriMatcher.addURI(GsContract.AUTHORITY, GsContract.TABLE+ "/#", GsContract.TASKS_ITEM);
    }

    @Override
    public boolean onCreate() {
        boolean ret= true;

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
        qb.setTables(GsContract.TABLE);

        switch (uriMatcher.match(uri)) {
            case GsContract.TASKS_LIST:
                break;

            case GsContract.TASKS_ITEM:
                qb.appendWhere(GsContract.Columns._ID+ "= "+ uri.getLastPathSegment());
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
            case GsContract.TASKS_LIST:
                return GsContract.CONTENT_TYPE;

            case GsContract.TASKS_ITEM:
                return GsContract.CONTENT_ITEM_TYPE;

            default:
                throw new IllegalArgumentException("Invalid URI: "+uri);
        }

    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {

        if (uriMatcher.match(uri) != GsContract.TASKS_LIST) {
            throw new IllegalArgumentException("Invalid URI: "+uri);
        }

        long id= db.insert(GsContract.TABLE,null,contentValues);

        if (id>0) {
            return ContentUris.withAppendedId(uri, id);
        }
        throw new SQLException("Error inserting into table: "+ GsContract.TABLE);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        int deleted= 0;

        switch (uriMatcher.match(uri)) {
            case GsContract.TASKS_LIST:
                db.delete(GsContract.TABLE,selection,selectionArgs);
                break;

            case GsContract.TASKS_ITEM:
                String where= GsContract.Columns._ID+ "= "+ uri.getLastPathSegment();
                if (!selection.isEmpty()) {
                    where += " AND "+selection;
                }

                deleted= db.delete(GsContract.TABLE,where,selectionArgs);
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
            case GsContract.TASKS_LIST:
                db.update(GsContract.TABLE,contentValues,s,strings);
                break;

            case GsContract.TASKS_ITEM:
                String where= GsContract.Columns._ID+ "= "+ uri.getLastPathSegment();
                if (!s.isEmpty()) {
                    where += " AND "+s;
                }
                updated= db.update(GsContract.TABLE,contentValues,where,strings);
                break;

            default:
                throw new IllegalArgumentException("Invalid URI: "+uri);
        }

        return updated;
    }
}
