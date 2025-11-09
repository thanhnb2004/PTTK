package com.tkht.dao;

import com.tkht.config.DatabaseConfig;
import java.sql.Connection;
import java.sql.SQLException;

public class DaoBase {
    
    protected Connection connect() throws SQLException {
        return DatabaseConfig.getConnection();
    }
}
