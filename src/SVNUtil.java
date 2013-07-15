import java.io.File;

import org.tmatesoft.svn.core.SVNCommitInfo;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNStatus;
import org.tmatesoft.svn.core.wc.SVNWCUtil;


public class SVNUtil
{
    private static final String URL = "http://128.160.97.1:80/svn/800csi/";
    private static final String USERNAME = "guopeng";
    private static final String PASSWORD = "guopeng";
    private static SVNClientManager ourClientManager;
    static
    {
        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager( USERNAME , PASSWORD );
        ourClientManager = SVNClientManager.newInstance(null, authManager);
    }
    public static SVNCommitInfo commit(File wcPath) throws SVNException {
        return commit(wcPath, false, "");
    }
    public static SVNCommitInfo commit(File wcPath , boolean keepLocks , String commitMessage ) throws SVNException {
        return ourClientManager.getCommitClient().doCommit( new File[] { wcPath } , keepLocks , commitMessage , false , true );
    }
    public static void add(File wcPath ) throws SVNException {
        ourClientManager.getWCClient().doAdd( wcPath , false , false , false , true );
    }
    public static void delete(File wcPath , boolean force ) throws SVNException {
        ourClientManager.getWCClient().doDelete( wcPath , force , false );
    }
    public static boolean isExist(File wcPath)
    {
        try
        {
            SVNStatus status = ourClientManager.getStatusClient().doStatus(wcPath, true);
            if(status != null)
                return true;
            
        } catch (SVNException e)
        {
            e.printStackTrace();
        }
        return false;
    }
    public static void main(String[] args)
    {
        System.out.println(SVNUtil.isExist(new File("D:\\docs\\项目\\800_new\\800csi\\document\\18 系统运行\\北数运维\\日报\\800CSR日报201130612.xls")));
    }
}
