package org.metachain.data;

import java.util.Properties;
import javax.mail.*;
import javax.mail.search.SubjectTerm;

public class GmailReader {
    public static String getOTPFromEmail(String email, String password) throws Exception {
        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imaps");
        properties.put("mail.imaps.host", "imap.gmail.com");
        properties.put("mail.imaps.port", "993");
        properties.put("mail.imaps.ssl.enable", "true");
        Session emailSession = Session.getDefaultInstance(properties);
        Store store = emailSession.getStore("imaps");
        store.connect("imap.gmail.com", email, password);
        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);
        Message[] messages = inbox.search(new SubjectTerm("Your OTP Code"));
        if (messages.length == 0) {
            throw new Exception("No OTP email found");
        }
        Message latestMessage = messages[messages.length - 1];
        String content = latestMessage.getContent().toString();
        String otp = content.replaceAll("[^0-9]", "").substring(0, 6);
        inbox.close(false);
        store.close();
        return otp;
    }
}
