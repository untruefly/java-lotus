import java.text.*;
import java.util.*;


import de.bea.domingo.*;
import de.bea.domingo.util.*;


public class LotusUtil
{
    public static final String INBOX = "($All)";
    public static final String OUTBOX = "($Sent)";
    private static DNotesFactory factory;
    private static DSession session;
    private static DDatabase database;
    private static final String PARAM_TITLE = "title";
    private static final String PARAM_AUTHOR = "author";
    private static final String PARAM_STARTDATE = "startDate";
    private static final String PARAM_ENDDATE = "endDate";
    static{
        factory = DNotesFactory.getInstance();
        session = factory.getSession();
        try
        {
            database = session.getMailDatabase();
        } catch (DNotesException e)
        {
            e.printStackTrace();
        }
    }
    public static List<DDocument> search(String folder, Map<String, String> params)
    {
        DView view = database.getView(folder);
        Iterator<DDocument> it = view.getAllDocuments();
        List<DDocument> emails = new ArrayList<DDocument>();
        while(it.hasNext())
        {
            DDocument doc = it.next();
            if(params == null || params.size() == 0)
            {
                emails.add(doc);
            }
            else
            {
                String title = params.get(PARAM_TITLE); 
                String author = params.get(PARAM_AUTHOR);
                String strStartDate = params.get(PARAM_STARTDATE);
                String strEndDate = params.get(PARAM_ENDDATE);
                if(checkTitle(doc, title) && 
                   checkAuthor(doc, author) && 
                   checkDate(doc, strStartDate, strEndDate))
                {
                    emails.add(doc);
                }
            }
        }
        return emails;
    }
    private static boolean checkTitle(DDocument doc, String title)
    {
        String emailTitle = (String)doc.getColumnValues().get(5); 
        return title == null ? true : emailTitle.indexOf(title) != -1;
    }
    private static boolean checkAuthor(DDocument doc, String author)
    {
        String emailAuthor = (String)doc.getColumnValues().get(1);
        return author == null ? true : emailAuthor.indexOf(author) != -1;
    }
    private static boolean checkDate(DDocument doc, String strStartDate, String strEndDate)
    {
        if(strStartDate == null && strEndDate == null)
        {
            return true;
        }
        Date d = ((GregorianDateTime)doc.getColumnValues().get(2)).getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            Date startDate = null;
            Date endDate = null;
            if(strStartDate != null)
            {
                startDate = sdf.parse(strStartDate);
            }
            if(strEndDate != null)
            {
                endDate = sdf.parse(strEndDate);
            }
            if((startDate == null || d.after(startDate)) && (endDate == null || d.before(endDate)))
            {
                return true;
            }
        }catch(ParseException e)
        {
            e.printStackTrace();
        }
        return false;
    }
    public static void main(String[] args) throws DNotesException
    {
        Map<String, String> params = new HashMap<String, String>();
//        params.put(LotusUtil.PARAM_TITLE, "");
        params.put(LotusUtil.PARAM_AUTHOR, "csr");
        params.put(LotusUtil.PARAM_STARTDATE, "2013-01-01");
        List<DDocument> emails = search(INBOX, params);
        System.out.println(emails.size());
    }
}
