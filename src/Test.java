import de.bea.domingo.groupware.*;
import de.bea.domingo.notes.*;
import java.util.*;

public class Test 
{
    // by untruefly
    public static void main(String[] args) throws GroupwareException
    {
        Groupware groupware = new Groupware("notes:///local!!mail/log.nsf");
        Mailbox mailbox = groupware.getMailbox();
        Iterator it = mailbox.getInbox();
        while(it.hasNext())
        {
            System.out.println(it.next());
        }
    }
}
