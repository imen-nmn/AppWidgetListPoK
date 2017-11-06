package com.imen_nmn.widgetpok.greenDaoUtils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.List;

/**
 * Created by imen_nmn on 23/03/17.
 */

public class DaoManager {
    private static DaoManager INSTANCE = null;
    private SQLiteDatabase db = null ;
    public static DaoManager getInstance(){
        if(INSTANCE == null){
            INSTANCE = new DaoManager() ;
        }
        return INSTANCE ;
    }

    //Return the Configured LogDao Object
    public DaoSession getDaoSession(Context context){
        DaoMaster.DevOpenHelper masterHelper = new DaoMaster.DevOpenHelper(context, "wwo", null); //create database db file if not exist
        if(db == null)
         db = masterHelper.getWritableDatabase();  //get the created database db file
        DaoMaster master = new DaoMaster(db);//create masterDao
        DaoSession masterSession=master.newSession(); //Creates Session session
        Log.d("WListTag-DAO", "getDaoSession");

        return masterSession;
    }


    public boolean closeGreenDaoDB(){
        if(db != null){
            db.close();
            db = null ;
            return true;
        }
        return false;
    }

//    public void init(Context context){
//        Log.d("WListTag-DAO", "init");
//
//
//        // query all notes, sorted a-z by their text
//    }

    public synchronized void saveResultLocationList(List<ResultLocation> resultLocations, Context context){
        Log.d("WListTag-DAO", "saveResultLocationList");
        // get the note DAO
        DaoSession daoSession = getDaoSession(context);
        ResultLocationDao resultLocationDao = daoSession.getResultLocationDao();
        resultLocationDao.deleteAll();
        for(int i= 0 ; i<resultLocations.size(); i++){
            resultLocations.get(i).getResultCondition().setId((long)i);
            Log.d("WListTag-DAO", "saveResultLocationList "+resultLocations.get(i).getResultCondition());
            resultLocationDao.insert(resultLocations.get(i)) ;
        }
    }

    public synchronized void saveResultCondition( ResultCondition resultCondition, Context context){
        Log.e("WListTag-DAO", "saveResultCondition");
        // get the note DAO
        DaoSession daoSession = getDaoSession(context);
        ResultConditionDao resultConditionDao = daoSession.getResultConditionDao();
        resultConditionDao.deleteByKey(resultCondition.getId());
        resultConditionDao.insert(resultCondition) ;
    }

    public List<ResultLocation>  getResultLocationList(Context context){
        DaoSession daoSession = getDaoSession(context);
        ResultLocationDao resultLocationDao = daoSession.getResultLocationDao();
          List<ResultLocation> listItemList = resultLocationDao.queryBuilder().build().list();
        return listItemList ;
    }


}
