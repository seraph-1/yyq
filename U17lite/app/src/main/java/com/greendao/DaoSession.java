package com.greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.example.u17lite.Manhua;

import com.greendao.ManhuaDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig manhuaDaoConfig;

    private final ManhuaDao manhuaDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        manhuaDaoConfig = daoConfigMap.get(ManhuaDao.class).clone();
        manhuaDaoConfig.initIdentityScope(type);

        manhuaDao = new ManhuaDao(manhuaDaoConfig, this);

        registerDao(Manhua.class, manhuaDao);
    }
    
    public void clear() {
        manhuaDaoConfig.clearIdentityScope();
    }

    public ManhuaDao getManhuaDao() {
        return manhuaDao;
    }

}
