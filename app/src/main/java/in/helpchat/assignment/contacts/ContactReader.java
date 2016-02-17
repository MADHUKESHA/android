package in.helpchat.assignment.contacts;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

/**
 * Created by admin on 15/02/16.
 */
public class ContactReader extends Activity{

    public void createContacts(final Context context) {
        DatabaseHandler db = new DatabaseHandler(context);
        db.deleteDb();
        ContentResolver cr = context.getContentResolver();
        Cursor phones=cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while (phones.moveToNext()) {
            final String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            final String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            final String path=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI));
            //Log.d("Image Path",path);
            db.addContact(new Contacts(name, phoneNumber,path));
            }
        phones.close();
    }
}