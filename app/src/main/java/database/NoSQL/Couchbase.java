package database.NoSQL;

import android.content.Context;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Emitter;
import com.couchbase.lite.Manager;
import com.couchbase.lite.Mapper;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.QueryRow;
import com.couchbase.lite.Reducer;
import com.couchbase.lite.android.AndroidContext;
import com.couchbase.lite.util.Log;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by kristian on 15-5-12.
 */
public class Couchbase {

    // Get class name
    public static String TAG = Couchbase.class.getName();
    // Couchbase Manager Instance. The manager is used in the app to access the database.
    private Manager manager;
    /**
     * create a name for the database and make sure the name is legal
     * Rules:
     * - The database name must begin with a lowercase letter.
     * - The database name must contain only valid characters. The following characters are valid in database names:
     * - Lowercase letters: a-z
     * - Numbers: 0-9
     * - Special characters: _$()+-/
     * <p/>
     * Note: The file for the database has a .cblite extension.
     */
    private String dbname = "my_cinema_app";
    // Create a new couchbase database
    private Database database;

    public Couchbase(Context context) {
        // Create a manager
        try {
            manager = new Manager(new AndroidContext(context), Manager.DEFAULT_OPTIONS);
            Log.d(TAG, "Manager created");
        } catch (IOException e) {
            Log.e(TAG, "Cannot create manager object");
            return;
        }
        // If you database is invalid we exit from application.
        if (!Manager.isValidDatabaseName(dbname)) {
            Log.e(TAG, "Bad database name");
            return;
        }

        try {
            database = manager.getDatabase(dbname);
        } catch (CouchbaseLiteException e) {
            Log.e(TAG, "Cannot get database");
            e.printStackTrace();
        }
    }


    /**
     * This section of the HelloWord tutorial code generates some data for a new document,
     * and then creates the new document and writes it to the database. It also outputs some messages to the console.
     *
     * @return String documentID
     */
    public String createDocument(Map<String, Object> documentContent) {

        // get the current date and time
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        // Create a new Gregorian Calendar
        Calendar calendar = GregorianCalendar.getInstance();

        String currentTimeString = dateFormatter.format(calendar.getTime());

//        Map<String, Object> documentContent = new HashMap<String, Object>();
//        documentContent.put("message", "Hello Couchbase Lite");
        documentContent.put("creationDate", currentTimeString);

        // display the data for the new document
        Log.d(TAG, "docContent=" + String.valueOf(documentContent));

        // Create an empty document
        Document document = database.createDocument();

        // add content to document and write the document to the database
        try {
            // Put document context in our database document
            document.putProperties(documentContent);

            Log.d(TAG, "Document written to database named " + database.getName() + " with ID = " + document.getId());
        } catch (CouchbaseLiteException e) {
            Log.e(TAG, "Cannot write document to database", e);
        }

        /**
         * When a document is saved to the database, Couchbase Lite generates a document identifier
         * property named _id and a revision identifier property named _rev, which are added to the stored document.
         */
        String documentID = document.getId();

        return documentID;
    }

    /**
     * The retrieved document includes the _id and _rev properties created by Couchbase Lite,
     * in addition to the keys and values written by the HelloWorld code.
     *
     * @param documentID
     * @return
     */
    public Document retrieveDocument(String documentID) {
        // retrieve the document from the database
        Document retrievedDocument = database.getDocument(documentID);

        // display the retrieved document
        Log.d(TAG, "retrievedDocument=" + String.valueOf(retrievedDocument.getProperties()));

        return retrievedDocument;
    }


    /**
     * When a document is updated, Couchbase Lite creates a new revision of the document that
     * contains a new revision identifier in the _rev property. The document identifier in _id always remains the same.
     *
     * @param documentID , new map which contains new content
     */
    public void updateDocument(String documentID, Map<String, Object> updatedProperties) {
        // retrieve the document from the database
        Document retrievedDocument = retrieveDocument(documentID);
        deleteDocument(documentID);
        createDocument(updatedProperties);
    }

    /**
     * The document is deleted by calling the delete() method on retrievedDocument.
     *
     * @param documentID
     */
    public void deleteDocument(String documentID) {
        // retrieve the document from the database
        Document retrievedDocument = retrieveDocument(documentID);

        // delete the document
        try {
            retrievedDocument.delete();

            // Then to verify the deletion, it logs the value returned by the isDeleted() method.
            Log.d(TAG, "Deleted document, deletion status = " + retrievedDocument.isDeleted());
        } catch (CouchbaseLiteException e) {
            Log.e(TAG, "Cannot delete document", e);
        }
    }

    public ArrayList<Document> getAllDocuments() {
        ArrayList<Document> allDocuments = new ArrayList<>();
        Query query = database.createAllDocumentsQuery();
        query.setAllDocsMode(Query.AllDocsMode.ALL_DOCS);
        QueryEnumerator result = null;
        try {
            result = query.run();
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
        for (Iterator<QueryRow> it = result; it.hasNext(); ) {
            QueryRow row = it.next();
            allDocuments.add(row.getDocument());
        }
        return allDocuments;
    }

    public void getAllMovies() {

//        Query query = database.getView("movies").createQuery();
//        View document = database.getView("movies");
//        Log.d("GET ALL MOVIES", query.getView().getR);
//        query.setDescending(true);
//        query.setLimit(100);
//        query.setStartKeyDocId("movies");


        /**
         *
         */
        com.couchbase.lite.View view = database.getView("movies");
        final Document document = database.getDocument("movies");
        if (view.getMap() == null) {
            view.setMapReduce(new Mapper() {
                                  @Override
                                  public void map(Map<String, Object> map, Emitter emitter) {
                                      List<String> hours = (List) document.getProperty("hours");
                                      for (String hour : hours) {
                                          emitter.emit(hour, document.getProperty("name"));
                                          Log.d("MAPPER","Vliza");
                                      }
                                  }
                              },
                    new Reducer() {
                        @Override
                        public Object reduce(List<Object> list, List<Object> list1, boolean b) {
                            Log.d("REDUCE","Vliza");
                            return new Integer(list1.size());
                        }
                    }, "1");
        }


//            Mapper mapper = new Mapper() {
//                public void map(Map<String, Object> document, Emitter emitter) {
//
//
//                    String type = (String)document.get("rating");
//                    Log.d("LOG TYPE",type);
//                    if ("rating".equals(type)) {
//                        emitter.emit(document.get("rating"), document);
//                    }
//                }
//            };
//            view.setMap(mapper, "2");
//        }

//        Query query = view.createQuery();
//
//        /**
//         *
//         */
//
//
//        QueryEnumerator result = null;
//        try {
//            result = query.run();
//        } catch (CouchbaseLiteException e) {
//            e.printStackTrace();
//        }
//        for (Iterator<QueryRow> it = result; it.hasNext(); ) {
//            Log.d("GEL ALL MOVIES", it.next().toString());
//        }
    }

    public void deleteAllDocuments() {
        Query query = database.createAllDocumentsQuery();
        query.setAllDocsMode(Query.AllDocsMode.ALL_DOCS);
        QueryEnumerator result = null;
        try {
            result = query.run();
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
        for (Iterator<QueryRow> it = result; it.hasNext(); ) {
            QueryRow row = it.next();
            deleteDocument(row.getDocumentId());
        }
    }
}
