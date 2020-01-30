package org.mskcc.cbio.portal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import org.cbioportal.model.GeneticEntity;
import org.cbioportal.model.meta.GenericAssayMeta;
import org.mskcc.cbio.portal.util.ProgressMonitor;

public class DaoGenericAssay {

    public static void setGenericEntityProperty(
        int id,
        String name,
        String value
    )
        throws DaoException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = JdbcUtil.getDbConnection(DaoGeneticEntity.class);
            pstmt =
                con.prepareStatement(
                    "INSERT INTO generic_entity_properties (`GENETIC_ENTITY_ID`, `NAME`, `VALUE`) " +
                    "VALUES(?,?,?)"
                );
            GeneticEntity entity = DaoGeneticEntity.getGeneticEntityById(id);
            if (entity == null) {
                return;
            }
            pstmt.setInt(1, entity.getId());
            pstmt.setString(2, name);
            pstmt.setString(3, value);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.closeAll(DaoGeneticEntity.class, con, pstmt, rs);
        }
    }

    public static GenericAssayMeta getGenericAssayMetaByStableId(
        String stableId
    )
        throws DaoException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = JdbcUtil.getDbConnection(DaoGeneticEntity.class);
            pstmt =
                con.prepareStatement(
                    "SELECT * FROM generic_entity_properties WHERE GENETIC_ENTITY_ID=?"
                );
            GeneticEntity entity = DaoGeneticEntity.getGeneticEntityByStableId(
                stableId
            );
            if (entity == null) {
                return null;
            }
            pstmt.setInt(1, entity.getId());
            rs = pstmt.executeQuery();

            HashMap<String, String> map = new HashMap<>();
            while (rs.next()) {
                map.put(rs.getString("NAME"), rs.getString("VALUE"));
            }
            GenericAssayMeta genericAssayMeta = new GenericAssayMeta(
                entity.getEntityType(),
                entity.getStableId(),
                map
            );
            return genericAssayMeta;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.closeAll(DaoGeneticEntity.class, con, pstmt, rs);
        }
        return null;
    }
}
