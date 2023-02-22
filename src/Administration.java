import java.util.*;

public class Administration {
    private PublishingRetailer[] publishingRetailers;

    public Administration() {
        this.publishingRetailers = Initialization.initializePubRetComplete();
    }

    public PublishingRetailer[] getPublishingRetailers() {
        return publishingRetailers;
    }

    /* This method will return a String with the metadata for all the
     * books in the given list. It is used for the Publish() method
     * of EditorialGroup and PublishingBrand instances.
     */
    public static String publishAllBooks (List<Book> books){
        String result = "";
        for (Book book: books){
            result = result + "        <book>\n" +
                    "            <title>" + book.getName() + "</title>\n" +
                    "            <subtitle>" + book.getSubtitle() + "</subtitle>\n" +
                    "            <isbn>" + book.getISBN() + "</isbn>\n" +
                    "            <pageCount>" + book.getPageCount() + "</pageCount>\n" +
                    "            <keywords>" + book.getKeywords() + "</keywords>\n" +
                    "            <languageID>" + book.getLanguageID() + "</languageID>\n" +
                    "            <createdOn" + book.getCreatedOn() + "</createdOn>\n" +
                    "            <authors>";

            result = result + book.getAuthors().get(0).getFirstName() +
                    " " + book.getAuthors().get(0).getLastName();

            // If there are more authors for a book, we separate them with ';'
            for (int i = 1; i < book.getAuthors().size(); i++){
                result = result + "; " + book.getAuthors().get(i).getFirstName() +
                        " " + book.getAuthors().get(i).getLastName();
            }

            result = result + "</authors>\n" +
                    "        </book>\n";
        }

        return result;
    }

    /* The method searches through the retailers and then adds all its books
     * in a hashtable. This way, in the hashtable will be no duplicates, given
     * the fact that each book has a unique ID. After that, we go through the
     * hashtable and add the elements in a list. This way, the method will have
     * O(n) complexity.
     */
    public List<Book> getBooksForPublishingRetailerID (int publishingRetailerID){
        List<Book> uniqueBooks = new ArrayList<>();
        Book currentBook;
        Hashtable hashtable = new Hashtable();
        Enumeration keys;
        PublishingRetailer searchedPubRetailer = null;

        // We verify if the initialization failed or not.
        if (this.publishingRetailers == null)
            return null;

        for (PublishingRetailer publishingRetailer: this.publishingRetailers){
            if (publishingRetailer.getID() == publishingRetailerID){
                searchedPubRetailer = publishingRetailer;
                break;
            }
        }

        for (IPublishingArtifact artifact: searchedPubRetailer.getPublishingArtifact()){
            if (artifact instanceof Book){
                hashtable.put(((Book) artifact).getID(), artifact);
            }
            else if (artifact instanceof EditorialGroup){
                EditorialGroup edGroup = (EditorialGroup) artifact;
                for (Book book: edGroup.getBooks()){
                    hashtable.put(book.getID(), book);
                }
            }
            else if (artifact instanceof PublishingBrand){
                PublishingBrand pubBrand = (PublishingBrand) artifact;
                for (Book book: pubBrand.getBooks()){
                    hashtable.put(book.getID(), book);
                }
            }
        }

        keys = hashtable.keys();
        while (keys.hasMoreElements()){
            int currentKey = (int) keys.nextElement();
            currentBook = (Book) hashtable.get(currentKey);
            uniqueBooks.add(currentBook);
        }

        return uniqueBooks;
    }

    /* The method uses a hashtable to get all the languages of a retailer and
     * works similarly as the publishAllBooks method.
     */
    public List<Language> getLanguagesForPublishingRetailerID (int publishingRetailerID){
        List<Language> uniqueLanguages = new ArrayList<>();
        Hashtable hashtable = new Hashtable();
        Enumeration keys;

        // We use the unique books of the retailer.
        List<Book> booksOfRetailer =
                this.getBooksForPublishingRetailerID(publishingRetailerID);
        Language[] languages = Initialization.initializeLanguage();

        // We verify if the initialization failed or not.
        if (booksOfRetailer == null || languages == null)
            return null;

        // We search through the books and add their languages in a hashtable.
        for (Book book: booksOfRetailer) {
            int searchedLanguageID = book.getLanguageID();
            Language searchedLanguage = null;

            for (Language language : languages) {
                if (language.getID() == searchedLanguageID) {
                    searchedLanguage = language;
                    break;
                }
            }

            hashtable.put(searchedLanguageID, searchedLanguage);
        }

        keys = hashtable.keys();
        while (keys.hasMoreElements()){
            int currentKey = (int) keys.nextElement();
            Language currentLanguage = (Language) hashtable.get(currentKey);
            uniqueLanguages.add(currentLanguage);
        }

        return uniqueLanguages;
    }

    /* The list searches for the retailers that have a specific book and adds
     * their countries to the list.
     */
    public List<Country> getCountriesForBookID (int bookID){
        List<Country> uniqueCountries = new ArrayList<>();
        int index;
        int lengthOfCountries;

        // We verify if the initialization failed or not.
        if (this.publishingRetailers == null)
            return null;

        for (PublishingRetailer publishingRetailer: this.publishingRetailers){
            List<Book> books =
                    getBooksForPublishingRetailerID(publishingRetailer.getID());

            for (Book book: books){
                if (book.getID() == bookID){
                    uniqueCountries.addAll(publishingRetailer.getCountries());
                    break;
                }
            }
        }

        /* We add all the countries in a list which we sort and then
         * eliminate the duplicates.
         */
        Collections.sort(uniqueCountries);

        index = 1;
        lengthOfCountries = uniqueCountries.size();
        while (index < lengthOfCountries){
            if (uniqueCountries.get(index).compareTo(uniqueCountries.get(index - 1)) == 0){
                uniqueCountries.remove (index);
                index--;
                lengthOfCountries--;
            }
            index++;
        }

        return uniqueCountries;
    }

    /* We use a hashtable to get the intersection of books, which has O(n+m)
     * complexity.
     */
    public List<Book> getCommonBooksForRetailerIDs (int retailerID1, int retailerID2){
        List<Book> commonBooks = new ArrayList<>();
        List<Book> booksOfRetailer1;
        List<Book> booksOfRetailer2;
        Hashtable hashtable = new Hashtable();
        int listInHashTable = 0;
        // Remembers which list was put in the hashtable.

        // We verify if the initialization failed or not.
        if (this.publishingRetailers == null)
            return null;

        /* The lists of books can be found easily, and the method that finds
         * them also has complexity O(n), so in the end the whole algorithm
         * for this method will have O(n+m) complexity.
         */
        booksOfRetailer1 = getBooksForPublishingRetailerID(retailerID1);
        booksOfRetailer2 = getBooksForPublishingRetailerID(retailerID2);

        // We add in the hashtable the smaller list.
        if (booksOfRetailer1.size() < booksOfRetailer2.size()){
            for (Book book: booksOfRetailer1){
                hashtable.put(book.getID(), book);
                listInHashTable = 1;
            }
        }
        else{
            for (Book book: booksOfRetailer2){
                hashtable.put(book.getID(), book);
                listInHashTable = 2;
            }
        }

        /* We see if the elements from the list that is not in the hashtable
         * have a correspondent in the hashtable. This would mean that the same
         * book was in the first list as well.
         */
        if (listInHashTable == 1){
            for (Book book: booksOfRetailer2){
                if (hashtable.get(book.getID()) != null){
                    commonBooks.add(book);
                }
            }
        }
        else{
            for (Book book: booksOfRetailer1){
                if (hashtable.get(book.getID()) != null){
                    commonBooks.add(book);
                }
            }
        }

        return commonBooks;
    }

    /* We use a hashtable to get the union of the retailers books. This approach
     * has O(n+m) complexity.
     */
    public List<Book> getAllBooksForRetailersIDs (int retailerID1, int retailerID2){
        List<Book> allBooks = new ArrayList<>();
        List<Book> booksOfRetailer1;
        List<Book> booksOfRetailer2;
        Hashtable hashtable = new Hashtable();
        Enumeration keys;

        // We verify if the initialization failed or not.
        if (this.publishingRetailers == null)
            return null;

        booksOfRetailer1 = getBooksForPublishingRetailerID(retailerID1);
        booksOfRetailer2 = getBooksForPublishingRetailerID(retailerID2);

        for (Book book: booksOfRetailer1){
            hashtable.put(book.getID(), book);
        }

        for (Book book: booksOfRetailer2){
            hashtable.put(book.getID(), book);
        }

        keys = hashtable.keys();
        while (keys.hasMoreElements()){
            int currentKey = (int) keys.nextElement();
            Book currentBook = (Book) hashtable.get(currentKey);
            allBooks.add(currentBook);
        }

        return allBooks;
    }
}
