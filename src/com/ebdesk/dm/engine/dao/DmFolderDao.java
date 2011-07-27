/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ebdesk.dm.engine.dao;

import com.ebdesk.dm.engine.domain.DmAccount;
import com.ebdesk.dm.engine.domain.DmDocumentFolder;
import com.ebdesk.dm.engine.domain.DmFolder;
import com.ebdesk.dm.engine.domain.DmFolderPermission;
import com.ebdesk.dm.engine.constant.ApplicationConstants;
import com.ebdesk.dm.engine.dto.DmFolderNode;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

/**
 *
 * @author designer
 */
@Repository("dmFolderDao")
public class DmFolderDao extends BaseDmEngineDaoImpl<DmFolder> {

    public DmFolderDao() {
        this.t = new DmFolder();
    }
    
    public List<DmFolder> getChildList(String parentId) {
        DmFolder folderParent = null;
        if (parentId != null) {
            folderParent = new DmFolder();
            folderParent.setId(parentId);
}
        
        List<DmFolder> folderList = null;
        if (folderParent != null) {
            Criteria crit = getSession().createCriteria(DmFolder.class);
    //        if (folderParent != null) {
                crit.add(Restrictions.eq("parent", folderParent));
    //        }
    //        else {
    //            crit.add(Restrictions.isNull("parent"));
    //        }
            crit.addOrder(Order.asc("name"));
            folderList = crit.list();

            // start - get children for each child (trigger lazy fetch)
            for (DmFolder folderTmp : folderList) {
                if (folderTmp.getChildList() != null) {
                    folderTmp.getChildList().size();
                }
            }
            // end - get children for each child (trigger lazy fetch)
        }

        return folderList;
    }

    public int getNumDocuments(String folderId) {
        DmFolder folder = null;
        if (folderId != null) {
            folder = new DmFolder();
            folder.setId(folderId);
        }
        
        if (folder != null) {
            Disjunction disjunction = Restrictions.disjunction();
            disjunction.add(Restrictions.isNull("docApproval.id"));
            disjunction.add(Restrictions.ne("docApproval.status", ApplicationConstants.APPROVAL_STATUS_REJECTED));

            Criteria crit = getSession().createCriteria(DmDocumentFolder.class);
            crit.createAlias("document", "docInfo");
            crit.createAlias("docInfo.approval", "docApproval", CriteriaSpecification.LEFT_JOIN);
            crit.add(Restrictions.eq("folder", folder));
//            crit.add(Restrictions.eq("docInfo.approved", true));
            crit.add(disjunction);
            
            return crit.list().size();
        }

        return 0;
    }

    public DmFolder getByName(String folderName, String accountId, String folderIdParent) {
        DmFolder folderParent = null;
        if ((folderIdParent != null) && (!folderIdParent.isEmpty())) {
            folderParent = new DmFolder();
            folderParent.setId(folderIdParent);
        }

        DmAccount accountOwner = null;
        if (folderParent == null) {
            if ((accountId != null) && (!accountId.isEmpty())) {
                accountOwner = new DmAccount();
                accountOwner.setId(accountId);
            }
        }

        Criteria crit = getSession().createCriteria(DmFolder.class);        
        crit.add(Restrictions.eq("name", folderName).ignoreCase());
        if (accountOwner != null) {
            crit.add(Restrictions.eq("owner", accountOwner));
        }
        if (folderParent != null) {
            crit.add(Restrictions.eq("parent", folderParent));
        }
        else {
            crit.add(Restrictions.isNull("parent"));
        }
        return (DmFolder) crit.uniqueResult();
    }

    public DmFolder getByNameExcludeById(String folderName, String folderId, String accountId, String folderIdParent) {
        DmFolder folderParent = null;
        if ((folderIdParent != null) && (!folderIdParent.isEmpty())) {
            folderParent = new DmFolder();
            folderParent.setId(folderIdParent);
        }

        DmAccount accountOwner = null;
        if (folderParent == null) {
            if ((accountId != null) && (!accountId.isEmpty())) {
                accountOwner = new DmAccount();
                accountOwner.setId(accountId);
            }
        }

        Criteria crit = getSession().createCriteria(DmFolder.class);
        crit.add(Restrictions.eq("name", folderName).ignoreCase());
        if (accountOwner != null) {
            crit.add(Restrictions.eq("owner", accountOwner));
        }
        if (folderParent != null) {
            crit.add(Restrictions.eq("parent", folderParent));
        }
        else {
            crit.add(Restrictions.isNull("parent"));
        }
        crit.add(Restrictions.ne("id", folderId));
        return (DmFolder) crit.uniqueResult();
    }

    public List<DmFolder> getFirstLevelList(String accountId) {
        DmAccount accountOwner = null;
        if (accountId != null) {
            accountOwner = new DmAccount();
            accountOwner.setId(accountId);
        }
        
        List<DmFolder> folderList = null;
        if (accountOwner != null) {
            Criteria crit = getSession().createCriteria(DmFolder.class);
            crit.add(Restrictions.eq("owner", accountOwner));
            crit.add(Restrictions.isNull("parent"));
            crit.addOrder(Order.asc("name"));
            folderList = crit.list();

            // start - get children for each child (trigger lazy fetch)
            for (DmFolder folderTmp : folderList) {
                if (folderTmp.getChildList() != null) {
                    folderTmp.getChildList().size();
                }
            }
            // end - get children for each child (trigger lazy fetch)
        }

        return folderList;
    }

    public List<DmAccount> getListOwnerByPermittedAccount(String accountId, Integer permissionLevelStart) {
        DmAccount permittedAccount = null;
        if (accountId != null) {
            permittedAccount = new DmAccount();
            permittedAccount.setId(accountId);
        }

        List<DmAccount> ownerList = null;
        if (permittedAccount != null) {
            Criteria crit = getSession().createCriteria(DmFolder.class);
            crit.setProjection(Projections.distinct(Projections.property("owner")));
            crit.add(Restrictions.ne("owner", permittedAccount));
//            crit.setProjection(Projections.property("owner"));

//            crit.add(Restrictions.eq("owner", accountOwner));
//            crit.add(Restrictions.isNull("parent"));
//            crit.addOrder(Order.asc("name"));
//            if (permissionLevelStart != null) {
//                crit.createCriteria("permissionList").add(Restrictions.ge("permissionType", permissionLevelStart));
//            }
//            Criterion permissionCrit = null;

            Conjunction conjunction = Restrictions.conjunction();            
            if (permissionLevelStart != null) {
                conjunction.add(Restrictions.ge("permissionType", permissionLevelStart));
            }
            conjunction.add(Restrictions.eq("account", permittedAccount));

            crit.createCriteria("permissionList").add(conjunction);
            
            ownerList = crit.list();

//            if (ownerList != null) {
//                for (DmAccount owner : ownerList) {
//                    System.out.println("name = " + owner.getName());
//                }
//            }
            // start - get children for each child (trigger lazy fetch)
//            for (DmFolder folderTmp : folderList) {
//                if (folderTmp.getChildList() != null) {
//                    folderTmp.getChildList().size();
//                }
//            }
            // end - get children for each child (trigger lazy fetch)
        }


        return ownerList;
//        return null;
    }

    public List<DmFolder> getSharedFirstLevelList(String accountSharedId, String accountOwnerId) {
        DmAccount permittedAccount = null;
        if (accountSharedId != null) {
            permittedAccount = new DmAccount();
            permittedAccount.setId(accountSharedId);
        }

        DmAccount ownerAccount = null;
        if (accountOwnerId != null) {
            ownerAccount = new DmAccount();
            ownerAccount.setId(accountOwnerId);
        }

        List<DmFolder> folderList = null;
        if (permittedAccount != null) {
            Criteria crit = getSession().createCriteria(DmFolderPermission.class);
            crit.setProjection(Projections.property("folder"));

            Conjunction conjunction = Restrictions.conjunction();
//            conjunction.add(Restrictions.ge("permissionType", ApplicationConstants.FOLDER_PERMISSION_MANAGER));
            conjunction.add(Restrictions.eq("account", permittedAccount));
            conjunction.add(Restrictions.ne("account", ownerAccount));
            crit.add(conjunction);

            crit.createAlias("folder", "folderShared", CriteriaSpecification.LEFT_JOIN);
            crit.add(Restrictions.eq("folderShared.owner", ownerAccount));

            crit.createAlias("folderShared.parent", "parentFolder", CriteriaSpecification.LEFT_JOIN);
//            crit.createAlias("parentFolder.permissionList", "parentPermission");
            crit.createAlias("parentFolder.permissionList", "parentPermission", CriteriaSpecification.LEFT_JOIN);

//            DetachedCriteria subquery = DetachedCriteria.forClass(DmFolder.class, "last_pos");
            DetachedCriteria subquery = DetachedCriteria.forClass(DmFolderPermission.class);
            subquery.setProjection(Projections.property("folder"));
            subquery.add(conjunction);

            Disjunction disjunction = Restrictions.disjunction();
            disjunction.add(Restrictions.isNull("folderShared.parent"));
//            disjunction.add(Restrictions.isNull("parentFolder.permissionList"));
//            disjunction.add(Restrictions.isNull("parentPermission.id"));
//            disjunction.add(conjunction2);
            disjunction.add(Subqueries.propertyNotIn("folderShared.parent", subquery));

//            crit.add(Subqueries.propertyNotIn("folderShared.parent", subquery));



            
//            Conjunction conjunction2 = Restrictions.conjunction();
//            conjunction2.add(Restrictions.eq("parentPermission.permissionType", ApplicationConstants.FOLDER_PERMISSION_MANAGER));
//            conjunction2.add(Restrictions.eq("parentPermission.account", permittedAccount));
//
//            Conjunction conjunction3 = Restrictions.conjunction();
//            conjunction3.add(Restrictions.eq("parentPermission.permissionType", ApplicationConstants.FOLDER_PERMISSION_MANAGER));
//            conjunction3.add(Restrictions.eq("parentPermission.account", permittedAccount));


//            disjunction.add(conjunction3);
//            disjunction.add(Restrictions.ne("parentPermission.account", permittedAccount));
//            disjunction.add(Restrictions.eq("parentPermission.permissionType", ApplicationConstants.FOLDER_PERMISSION_MANAGER));

//            disjunction.add(Restrictions.isNull("parentFolder.permissionList"));


//            disjunction.add(Restrictions.eq("parentPermission.permissionType", ApplicationConstants.FOLDER_PERMISSION_MANAGER));
//            disjunction.add(Restrictions.ne("parentPermission.account", permittedAccount));

            crit.add(disjunction);

            folderList = crit.list();

            // start - get children for each child (trigger lazy fetch)
            for (DmFolder folderTmp : folderList) {
                if (folderTmp.getChildList() != null) {
                    folderTmp.getChildList().size();
                }
            }
            // end - get children for each child (trigger lazy fetch)
        }


        return folderList;
    }

    public List<DmFolderNode> getSharedFirstLevelFolderNodeList(String accountSharedId, String accountOwnerId) {
        List<DmFolderNode> folderNodeList = null;
        
        String sqlSub = "SELECT"
            + " count(*)"
            + " from dm_folder fChild"
            + " left join dm_folder_permission fpChild"
            + " on fChild.df_id = fpChild.df_id"
            + " where fChild.df_parent_id = f.df_id"
            + " and fpChild.da_id = :accountSharedId"
            + " and fpChild.da_id <> :accountOwnerId"
//            + " and fpChild.dfp_permission_type >= " + ApplicationConstants.FOLDER_PERMISSION_MANAGER
            ;

//        String sqlSub2 = "''";
        String sqlSub2 = "SELECT"
            + " fpPrnt2.df_id"
            + " FROM dm_folder_permission fpPrnt2"
            + " WHERE fpPrnt2.da_id = :accountSharedId"
            + " and fpPrnt2.da_id <> :accountOwnerId"
//            + " and fpPrnt2.dfp_permission_type >= " + ApplicationConstants.FOLDER_PERMISSION_WRITER
            ;

        String sql = "SELECT"
            + " f.df_id as \"id\", f.df_name as \"name\", (" + sqlSub + ") as \"numChildren\""
            + ", f.da_id_owner as \"ownerAccountId\", fp.dfp_permission_type as \"permissionType\""
            + " FROM dm_folder_permission fp"
            + " LEFT JOIN dm_folder f"
            + " on fp.df_id = f.df_id"
            + " LEFT JOIN dm_folder fPrnt"
            + " on f.df_parent_id = fPrnt.df_id"
//            + " LEFT JOIN dm_folder_permission fpPrnt"
//            + " on fPrnt.df_id = fpPrnt.df_id"
            + " WHERE fp.da_id = :accountSharedId"
//            + " AND fp.dfp_permission_type >= " + ApplicationConstants.FOLDER_PERMISSION_WRITER
            + " AND fp.da_id <> :accountOwnerId"
            + " AND f.da_id_owner = :accountOwnerId"
//            + " AND ((fPrnt.df_id IS NULL) OR (fPrnt.df_id NOT IN (" + sqlSub2 + ")))"
            + " AND ((fPrnt.df_id IS NULL) OR (fPrnt.df_id NOT IN (" + sqlSub2 + ")))"
            + " ORDER BY f.df_name asc"
            ;

        SQLQuery query = getSession().createSQLQuery(sql);

        query.setString("accountSharedId", accountSharedId);
        query.setString("accountOwnerId", accountOwnerId);

        query.addScalar("id", Hibernate.STRING);
        query.addScalar("name", Hibernate.STRING);
        query.addScalar("numChildren", Hibernate.INTEGER);
        query.addScalar("ownerAccountId", Hibernate.STRING);
        query.addScalar("permissionType", Hibernate.INTEGER);

//                   .add( Projections.property("folderInfo.id"), "id" )
//                   .add( Projections.property("folderInfo.name"), "name" )
//                   .add( Projections.count("folderChildren.id"), "numChildren" )
////                   .add( Projections.property("folderChildren.id"), "numChildren" )
//                   .add( Projections.property("folderInfo.owner.id"), "ownerAccountId" )
//                   .add( Projections.property("permissionType"), "permissionType" )

        query.setResultTransformer(Transformers.aliasToBean(DmFolderNode.class));

        folderNodeList = query.list();

        return folderNodeList;
    }

    public List<DmFolder> getSharedChildList(String accountSharedId, String folderIdParent) {
        DmAccount permittedAccount = null;
        if (accountSharedId != null) {
            permittedAccount = new DmAccount();
            permittedAccount.setId(accountSharedId);
        }

        DmFolder parentFolder = null;
        if (folderIdParent != null) {
            parentFolder = findById(folderIdParent);
//            parentFolder = new DmFolder();
//            parentFolder.setId(folderIdParent);
        }

        List<DmFolder> folderList = null;

        if (parentFolder != null) {
            Criteria crit = getSession().createCriteria(DmFolderPermission.class);
            crit.setProjection(Projections.property("folder"));
            crit.add(Restrictions.eq("account", permittedAccount));
            crit.add(Restrictions.ne("account", parentFolder.getOwner()));
    //        crit.add(Restrictions.ge("permissionType", ApplicationConstants.FOLDER_PERMISSION_WRITER));

            crit.createAlias("folder", "folderInfo");
            crit.add(Restrictions.eq("folderInfo.parent", parentFolder));

            crit.addOrder(Order.asc("folderInfo.name"));

            folderList = crit.list();

            // start - get children for each child (trigger lazy fetch)
            for (DmFolder folderTmp : folderList) {
                if (folderTmp.getChildList() != null) {
                    folderTmp.getChildList().size();
                }
            }
            // end - get children for each child (trigger lazy fetch)
        }

        return folderList;
    }

    public List<DmFolderNode> getSharedChildFolderNodeList(String accountSharedId, String folderIdParent) {
//        DmAccount permittedAccount = null;
//        if (accountSharedId != null) {
//            permittedAccount = new DmAccount();
//            permittedAccount.setId(accountSharedId);
//        }
//
        DmFolder parentFolder = null;
        if (folderIdParent != null) {
            parentFolder = findById(folderIdParent);
//            parentFolder = new DmFolder();
//            parentFolder.setId(folderIdParent);
        }

        List<DmFolderNode> folderNodeList = null;

//        DetachedCriteria subquery = DetachedCriteria.forClass(DmFolder.class);
//        subquery.setProjection(Projections.count("id"));
//        subquery.add(Restrictions.eq("account", permittedAccount));
//        disjunction.add(Subqueries.propertyNotIn("folderParent.parent", subquery));

//        Conjunction conjunction = Restrictions.conjunction();
//        conjunction.add(Restrictions.ge("permissionType", ApplicationConstants.FOLDER_PERMISSION_WRITER));
//        conjunction.add(Restrictions.eq("account", permittedAccount));
//        crit.add(conjunction);

//        DetachedCriteria subquery = DetachedCriteria.forClass(DmFolderPermission.class);
//        subquery.setProjection(Projections.property("folder"));
//        subquery.add(conjunction);

//        Disjunction disjunction = Restrictions.disjunction();
//        disjunction.add(Restrictions.isNull("folderChildren.permissionList"));
//        disjunction.add(Subqueries.propertyIn("folderInfo.childList", subquery));

//        disjunction.add(Restrictions.eq("childPermission.account", permittedAccount));
//        disjunction.add(Subqueries.propertyIn("folderChildren", subquery));


//        Criteria crit = getSession().createCriteria(DmFolderPermission.class, "permission");
//        crit.createAlias("folder", "folderInfo");
//
//        crit.add(Restrictions.eq("account", permittedAccount));
//        crit.add(Restrictions.ge("permission.permissionType", ApplicationConstants.FOLDER_PERMISSION_WRITER));

//        crit.createAlias("folderInfo.childList", "folderChildren", CriteriaSpecification.LEFT_JOIN);
//        crit.createCriteria("folderInfo.childList", "folderChildren", CriteriaSpecification.LEFT_JOIN);

//        crit.createAlias("folderChildren.permissionList", "childPermission", CriteriaSpecification.LEFT_JOIN);
//        crit.add(disjunction);

//select fp.df_name, count(fc.df_id)
// from dm_folder fp
// left join dm_folder fc
// on fp.df_id = fc.df_parent_id
// -- where
// -- fp.df_id = '517243bd-3ea5-489a-9b87-feeabd8c4f4c'
// group by fp.df_name

//        crit.setProjection(
//            Projections.projectionList()
//                   .add( Projections.property("folderInfo.id"), "id" )
//                   .add( Projections.property("folderInfo.name"), "name" )
//                   .add( Projections.count("folderChildren.id"), "numChildren" )
////                   .add( Projections.property("folderChildren.id"), "numChildren" )
//                   .add( Projections.property("folderInfo.owner.id"), "ownerAccountId" )
//                   .add( Projections.property("permissionType"), "permissionType" )
//                   .add( Projections.groupProperty("id") )
//        );
        
//        crit.add(Restrictions.eq("folderInfo.parent", parentFolder));

//        crit.addOrder(Order.asc("folderInfo.name"));
        
//        crit.setResultTransformer(Transformers.aliasToBean(DmFolderNode.class));

//        folderNodeList = crit.list();

        if (parentFolder != null) {
            String sqlSub = "SELECT"
                + " count(*)"
                + " from dm_folder fChild"
                + " left join dm_folder_permission fChildPerm"
                + " on fChild.df_id = fChildPerm.df_id"
                + " where fChild.df_parent_id = f.df_id"
                + " and fChildPerm.da_id = :accountSharedId"
                + " and fChildPerm.da_id <> :accountOwnerId"
    //            + " and fChildPerm.dfp_permission_type >= " + ApplicationConstants.FOLDER_PERMISSION_WRITER
                ;

            String sql = "SELECT"
                + " f.df_id as \"id\", f.df_name as \"name\", (" + sqlSub + ") as \"numChildren\""
                + ", f.da_id_owner as \"ownerAccountId\", fp.dfp_permission_type as \"permissionType\""
                + " FROM dm_folder_permission fp"
                + " LEFT JOIN dm_folder f"
                + " on fp.df_id = f.df_id"
                + " WHERE fp.da_id = :accountSharedId"
                + " AND f.df_parent_id = :folderIdParent"
                + " ORDER BY f.df_name asc"
                ;

            SQLQuery query = getSession().createSQLQuery(sql);

            query.setString("accountSharedId", accountSharedId);
            query.setString("folderIdParent", folderIdParent);
            query.setString("accountOwnerId", parentFolder.getOwner().getId());

            query.addScalar("id", Hibernate.STRING);
            query.addScalar("name", Hibernate.STRING);
            query.addScalar("numChildren", Hibernate.INTEGER);
            query.addScalar("ownerAccountId", Hibernate.STRING);
            query.addScalar("permissionType", Hibernate.INTEGER);

    //                   .add( Projections.property("folderInfo.id"), "id" )
    //                   .add( Projections.property("folderInfo.name"), "name" )
    //                   .add( Projections.count("folderChildren.id"), "numChildren" )
    ////                   .add( Projections.property("folderChildren.id"), "numChildren" )
    //                   .add( Projections.property("folderInfo.owner.id"), "ownerAccountId" )
    //                   .add( Projections.property("permissionType"), "permissionType" )

            query.setResultTransformer(Transformers.aliasToBean(DmFolderNode.class));

            folderNodeList = query.list();
        }
        return folderNodeList;
    }

    public List<DmFolderNode> getFirstLevelFolderNodeList(String accountId) {
        List<DmFolderNode> folderNodeList = null;

        String sqlSub = "SELECT"
            + " count(*)"
            + " from dm_folder fChild"
//            + " left join dm_folder_permission fpChild"
//            + " on fChild.df_id = fpChild.df_id"
            + " where fChild.df_parent_id = f.df_id"
//            + " and fpChild.da_id = :accountSharedId"
//            + " and fpChild.da_id <> :accountOwnerId"
            ;

//        String sqlSub2 = "SELECT"
//            + " fpPrnt2.df_id"
//            + " FROM dm_folder_permission fpPrnt2"
//            + " WHERE fpPrnt2.da_id = :accountSharedId"
//            + " and fpPrnt2.da_id <> :accountOwnerId"
//            ;

        String sql = "SELECT"
            + " f.df_id as \"id\", f.df_name as \"name\", (" + sqlSub + ") as \"numChildren\""
            + ", f.da_id_owner as \"ownerAccountId\", fp.dfp_permission_type as \"permissionType\""
            + " FROM dm_folder_permission fp"
            + " LEFT JOIN dm_folder f"
            + " on fp.df_id = f.df_id and fp.da_id = f.da_id_owner"
//            + " LEFT JOIN dm_folder fPrnt"
//            + " on f.df_parent_id = fPrnt.df_id"
            + " WHERE f.da_id_owner = :accountId"
//            + " AND fp.da_id <> :accountOwnerId"
//            + " AND f.da_id_owner = :accountId"
            + " AND f.df_parent_id is null"
//            + " AND ((fPrnt.df_id IS NULL) OR (fPrnt.df_id NOT IN (" + sqlSub2 + ")))"
            + " ORDER BY f.df_name asc"
            ;

        SQLQuery query = getSession().createSQLQuery(sql);

        query.setString("accountId", accountId);

        query.addScalar("id", Hibernate.STRING);
        query.addScalar("name", Hibernate.STRING);
        query.addScalar("numChildren", Hibernate.INTEGER);
        query.addScalar("ownerAccountId", Hibernate.STRING);
        query.addScalar("permissionType", Hibernate.INTEGER);

        query.setResultTransformer(Transformers.aliasToBean(DmFolderNode.class));

        folderNodeList = query.list();

        return folderNodeList;
    }

    public List<DmFolderNode> getChildFolderNodeList(String folderIdParent) {
        DmFolder parentFolder = null;
        if (folderIdParent != null) {
            parentFolder = findById(folderIdParent);
        }

        List<DmFolderNode> folderNodeList = null;

        if (parentFolder != null) {
            String sqlSub = "SELECT"
                + " count(*)"
                + " from dm_folder fChild"
//                + " left join dm_folder_permission fChildPerm"
//                + " on fChild.df_id = fChildPerm.df_id"
                + " where fChild.df_parent_id = f.df_id"
//                + " and fChildPerm.da_id = :accountSharedId"
//                + " and fChildPerm.da_id <> :accountOwnerId"
                ;

            String sql = "SELECT"
                + " f.df_id as \"id\", f.df_name as \"name\", (" + sqlSub + ") as \"numChildren\""
                + ", f.da_id_owner as \"ownerAccountId\", fp.dfp_permission_type as \"permissionType\""
                + " FROM dm_folder_permission fp"
                + " LEFT JOIN dm_folder f"
                + " on fp.df_id = f.df_id and fp.da_id = f.da_id_owner"
                + " WHERE f.df_parent_id = :folderIdParent"
                + " ORDER BY f.df_name asc"
                ;

            SQLQuery query = getSession().createSQLQuery(sql);

            query.setString("folderIdParent", folderIdParent);
//            query.setString("accountOwnerId", parentFolder.getOwner().getId());

            query.addScalar("id", Hibernate.STRING);
            query.addScalar("name", Hibernate.STRING);
            query.addScalar("numChildren", Hibernate.INTEGER);
            query.addScalar("ownerAccountId", Hibernate.STRING);
            query.addScalar("permissionType", Hibernate.INTEGER);

            query.setResultTransformer(Transformers.aliasToBean(DmFolderNode.class));

            folderNodeList = query.list();
        }
        return folderNodeList;
    }

    public DmFolder getParent(String folderId, String accountId) {
        DmAccount account = null;
        if (accountId != null) {
//            folder = findById(folderId);
            account = new DmAccount();
            account.setId(accountId);
        }

        Criteria crit = getSession().createCriteria(DmFolder.class, "folder");
        crit.add(Restrictions.eq("folder.id", (folderId != null) ? folderId : ""));
        crit.setProjection(Projections.property("folder.parent"));
        crit.createAlias("parent", "folderParent");
        crit.createAlias("folderParent.permissionList", "permissions");
        crit.add(Restrictions.eq("permissions.account", account));
        
        return (DmFolder) crit.uniqueResult();
    }
}
