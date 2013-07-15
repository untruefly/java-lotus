import de.bea.domingo.*;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DownloadReport
{
    
    private static final String PATH = "D:\\docs\\项目\\800_new\\800csi\\document\\18 系统运行\\北数运维\\日报\\";
    public static void main(String[] args)
    {
        try
        {
            Map<String, String> params = new HashMap<String, String>();
            params.put("startDate", "2013-07-01");
            params.put("endDate", "2013-07-31");
            for(DDocument doc : LotusUtil.search("日报", params))
            {
                List<String> attachments = doc.getAttachments();
                  for(String attachment : attachments)
                  {
                      if(attachment.startsWith("800CSR"))
                      {
                          DEmbeddedObject obj = doc.getAttachment(attachment);
                          String filePath = PATH + attachment;
                          System.out.println("开始下载" + attachment);
                          obj.extractFile(filePath);
//                          File file = new File(filePath);
//                          if(!SVNUtil.isExist(file))
//                          {
//                              System.out.println("开始上传" + attachment);
//                              SVNUtil.add(file);
//                              SVNUtil.commit(file);
//                          }
//                          else
//                          {
//                              System.out.println("SVN中已经存在文件" + attachment);
//                          }
                      }
                  }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}