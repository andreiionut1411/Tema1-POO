import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Initialization {

    // Initializes the books, but no author is attributed.
    public static Book[] initializeBook () {
        int numberOfBooks = 0;
        Book[] books;

        /* We count the number of rows from the file to know how many books
         * there are.
         */
        try (BufferedReader br = new BufferedReader(new FileReader("../books.in"))){
            String line;
            while ((line = br.readLine()) != null)
                numberOfBooks++;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("File \"books.in\" doesn't exist");
            return null;
        }

        // The first row doesn't contain book information, so we ignore it.
        numberOfBooks -= 1;
        books = new Book[numberOfBooks];

        try (BufferedReader br = new BufferedReader(new FileReader("../books.in"))){
            // The first line has only formatting information, so we skip it.
            String line = br.readLine();
            int index = 0;
            int ID;
            String name;
            String subtitle;
            String ISBN;
            int pageCount;
            String keywords;
            int languageID;
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            Date createdOn;

            while ((line = br.readLine()) != null){
                String[] tokens = line.split("###");
                ID = Integer.parseInt(tokens[0]);
                name = tokens[1];
                subtitle = tokens[2];
                ISBN = tokens[3];
                pageCount = Integer.parseInt(tokens[4]);
                keywords = tokens[5];
                languageID = Integer.parseInt(tokens[6]);
                createdOn = format.parse(tokens[7]);

                books[index++] = new Book(ID, name, subtitle, ISBN, pageCount,
                                    keywords, languageID, createdOn);
            }


        } catch (IOException | ParseException e){
            e.printStackTrace();
            System.out.println("File \"books.in\" doesn't exist");
            return null;
        }

        return books;
    }

    // Initializes the languages.
    public static Language[] initializeLanguage () {
        int numberOfLanguages = 0;
        Language[] languages;

        /* We count the number of rows from the file to know how many languages
         * there are.
         */
        try (BufferedReader br = new BufferedReader
                                (new FileReader("../languages.in"))){

            String line;
            while ((line = br.readLine()) != null)
                numberOfLanguages++;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("File \"languages.in\" doesn't exist");
            return null;
        }

        // The first row doesn't contain language information, so we ignore it.
        numberOfLanguages -= 1;
        languages = new Language[numberOfLanguages];

        try (BufferedReader br = new BufferedReader
                                (new FileReader("../languages.in"))){

            // The first line has only formatting information, so we skip it.
            String line = br.readLine();
            int index = 0;
            int ID;
            String code;
            String name;

            while ((line = br.readLine()) != null){
                String[] tokens = line.split("###");

                ID = Integer.parseInt(tokens[0]);
                code = tokens[1];
                name = tokens[2];

                languages[index++] = new Language(ID, code, name);
            }


        } catch (IOException e){
            e.printStackTrace();
            System.out.println("File \"languages.in\" doesn't exist");
            return null;
        }

        return languages;
    }

    // Initializes all the information needed for an author.
    public static Author[] initializeAuthor () {
        int numberOfAuthors = 0;
        Author[] authors;

        /* We count the number of rows from the file to know how many authors
         * there are.
         */
        try (BufferedReader br = new BufferedReader
                                (new FileReader("../authors.in"))){

            String line;
            while ((line = br.readLine()) != null)
                numberOfAuthors++;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("File \"authors.in\" doesn't exist");
            return null;
        }

        // The first row doesn't contain author information, so we ignore it.
        numberOfAuthors -= 1;
        authors = new Author[numberOfAuthors];

        try (BufferedReader br = new BufferedReader
                                (new FileReader("../authors.in"))){

            // The first line has only formatting information, so we skip it.
            String line = br.readLine();
            int index = 0;
            int ID;
            String firstName;
            String lastName;

            while ((line = br.readLine()) != null){
                String[] tokens = line.split("###");

                ID = Integer.parseInt(tokens[0]);
                firstName = tokens[1];
                lastName = tokens[2];

                authors[index++] = new Author(ID, firstName, lastName);
            }


        } catch (IOException e){
            e.printStackTrace();
            System.out.println("File \"authors.in\" doesn't exist");
            return null;
        }

        return authors;
    }

    // Initializes the books and their respective authors and links them.
    public static Book[] initializeCompleteBooks (){
        Book[] books = Initialization.initializeBook();
        Author[] authors = Initialization.initializeAuthor();

        // If the initialization failed previously, we don't continue.
        if (books == null || authors == null)
            return null;

        int noOfBooks = books.length;
        int noOfAuthors = authors.length;

        try (BufferedReader br = new BufferedReader
                                (new FileReader("../books-authors.in"))){

            String line;
            int authorID;
            int bookID;
            int found; //found will be 1 when we found the searched author/book
            Book currentBook = null;
            Author currentAuthor = null;

            // The first line contains information for formatting.
            line = br.readLine();
            while ((line = br.readLine()) != null){
                String[] tokens = line.split("###");
                bookID = Integer.parseInt(tokens[0]);
                authorID = Integer.parseInt(tokens[1]);

                found = 0;
                for (int i = 0; i < noOfBooks && found == 0; i++) {
                    if (bookID == books[i].getID()) {
                        currentBook = books[i];
                        found = 1;
                    }
                }

                found = 0;
                for (int i = 0; i < noOfAuthors && found == 0; i++) {
                    if (authorID == authors[i].getID()) {
                        currentAuthor = authors[i];
                        found = 1;
                    }
                }

                currentBook.addAuthors(currentAuthor);
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("File \"books-authors.in\" doesn't exist");
            return null;
        }

        return books;
    }

    // Initializes the EditorialGroups, but their book lists are empty.
    public static EditorialGroup[] initializeEdGroup () {
        int numberOfEdGroups = 0;
        EditorialGroup[] editorialGroups;

        /* We count the number of rows from the file to know how many editorial
         * groups there are.
         */
        try (BufferedReader br = new BufferedReader
                                (new FileReader("../editorial-groups.in"))){

            String line;
            while ((line = br.readLine()) != null)
                numberOfEdGroups++;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("File \"editorial-groups.in\" doesn't exist");
            return null;
        }

        /* The first row doesn't contain information about editorial groups,
         * so we ignore it.
         */
        numberOfEdGroups -= 1;
        editorialGroups = new EditorialGroup[numberOfEdGroups];

        try (BufferedReader br = new BufferedReader
                                (new FileReader("../editorial-groups.in"))){

            // The first line has only formatting information, so we skip it.
            String line = br.readLine();
            int index = 0;
            int ID;
            String name;

            while ((line = br.readLine()) != null){
                String[] tokens = line.split("###");

                ID = Integer.parseInt(tokens[0]);
                name = tokens[1];

                editorialGroups[index++] = new EditorialGroup(ID, name);
            }


        } catch (IOException e){
            e.printStackTrace();
            System.out.println("File \"editorial-groups.in\" doesn't exist");
            return null;
        }

        return editorialGroups;
    }

    /* Initializes the EditorialGroups and completes the book lists. The books
     * that are available are given as a parameter and need to be initialized
     * beforehand.
     */
    public static EditorialGroup[] initializeEdGroupComplete (Book[] books) {
        EditorialGroup[] editorialGroups = Initialization.initializeEdGroup();

        // If the initialization failed previously, then we don't go on with it.
        if (editorialGroups == null || books == null)
            return null;

        try (BufferedReader br = new BufferedReader
                (new FileReader("../editorial-groups-books.in"))){

            // The first line has only formatting information, so we skip it.
            String line = br.readLine();

            while ((line = br.readLine()) != null){
                String[] tokens = line.split("###");
                addArtifactToObject(tokens, editorialGroups, books, 1, 0);
            }

        } catch (IOException e){
            e.printStackTrace();
            System.out.println("File \"editorial-groups-books.in\" doesn't exist");
            return null;
        }

        return editorialGroups;
    }

    /* Initializes the PublishingBrands, but doesn't make the association
     * between them and books.
     */
    public static PublishingBrand[] initializePubBrand () {
        int numberOfPubBrands = 0;
        PublishingBrand[] publishingBrands;

        /* We count the number of rows from the file to know how many publishing
         * brands there are.
         */
        try (BufferedReader br = new BufferedReader
                                (new FileReader("../publishing-brands.in"))){

            String line;
            while ((line = br.readLine()) != null)
                numberOfPubBrands++;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("File \"publishing-brands.in\" doesn't exist");
            return null;
        }

        /* The first row doesn't contain information about publishing brands,
         * so we ignore it.
         */
        numberOfPubBrands -= 1;
        publishingBrands = new PublishingBrand[numberOfPubBrands];

        try (BufferedReader br = new BufferedReader
                                (new FileReader("../publishing-brands.in"))){

            // The first line has only formatting information, so we skip it.
            String line = br.readLine();
            int index = 0;
            int ID;
            String name;

            while ((line = br.readLine()) != null){
                String[] tokens = line.split("###");

                ID = Integer.parseInt(tokens[0]);
                name = tokens[1];

                publishingBrands[index++] = new PublishingBrand(ID, name);
            }


        } catch (IOException e){
            e.printStackTrace();
            System.out.println("File \"publishing-brands.in\" doesn't exist");
            return null;
        }

        return publishingBrands;
    }

    /* Initializes the PublishingBrands and link them to their respective
     * books. All the books are given as a parameter for the method and need to
     * be initialized beforehand.
     */
    public static PublishingBrand[] initializePubBrandComplete (Book[] books) {
        PublishingBrand[] publishingBrands = Initialization.initializePubBrand();

        // If the initialization failed previously, we don't go on with it.
        if (publishingBrands == null || books == null)
            return null;

        try (BufferedReader br = new BufferedReader
                (new FileReader("../publishing-brands-books.in"))){

            // The first line has only formatting information, so we skip it.
            String line = br.readLine();

            while ((line = br.readLine()) != null){
                String[] tokens = line.split("###");
                addArtifactToObject(tokens, publishingBrands, books, 2, 0);
            }

        } catch (IOException e){
            e.printStackTrace();
            System.out.println
                    ("File \"publishing-brands-books.in\" doesn't exist");
            return null;
        }

        return publishingBrands;
    }

    /* Initializes the retailers, but they don't have their artifacts or the
     * countries associated.
     */
    public static PublishingRetailer[] initializePubRetailer () {
        int numberOfPubRetailers = 0;
        PublishingRetailer[] publishingRetailers;

        /* We count the number of rows from the file to know how many retailers
         * there are.
         */
        try (BufferedReader br = new BufferedReader
                (new FileReader("../publishing-retailers.in"))){

            String line;
            while ((line = br.readLine()) != null)
                numberOfPubRetailers++;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("File \"publishing-retailers.in\" doesn't exist.");
            return null;
        }

        /* The first row doesn't contain information about publishing
         * retailers, so we ignore it.
         */
        numberOfPubRetailers -= 1;
        publishingRetailers = new PublishingRetailer[numberOfPubRetailers];

        try (BufferedReader br = new BufferedReader
                (new FileReader("../publishing-retailers.in"))){

            // The first line has only formatting information, so we skip it.
            String line = br.readLine();
            int index = 0;
            int ID;
            String name;

            while ((line = br.readLine()) != null){
                String[] tokens = line.split("###");

                ID = Integer.parseInt(tokens[0]);
                name = tokens[1];

                publishingRetailers[index++] = new PublishingRetailer(ID, name);
            }


        } catch (IOException e){
            e.printStackTrace();
            System.out.println("File \"publishing-retailers.in\" doesn't exist.");
            return null;
        }

        return publishingRetailers;
    }

    // Initializes the countries.
    public static Country[] initializeCountry () {
        int numberOfCountries = 0;
        Country[] countries;

        /* We count the number of rows from the file to know how many countries
         * there are.
         */
        try (BufferedReader br = new BufferedReader
                                (new FileReader("../countries.in"))){

            String line;
            while ((line = br.readLine()) != null)
                numberOfCountries++;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("File \"countries.in\" doesn't exist.");
            return null;
        }

        // The first row doesn't contain country information, so we ignore it.
        numberOfCountries -= 1;
        countries = new Country[numberOfCountries];

        try (BufferedReader br = new BufferedReader
                                (new FileReader("../countries.in"))){

            // The first line has only formatting information, so we skip it.
            String line = br.readLine();
            int index = 0;
            int ID;
            String countryCode;

            while ((line = br.readLine()) != null){
                String[] tokens = line.split("###");

                ID = Integer.parseInt(tokens[0]);
                countryCode = tokens[1];

                countries[index++] = new Country(ID, countryCode);
            }


        } catch (IOException e){
            e.printStackTrace();
            System.out.println("File \"countries.in\" doesn't exist.");
            return null;
        }

        return countries;
    }

    // Initializes the retailers and links them to their respective countries.
    public static PublishingRetailer[] initializePubRetCountries () {
        PublishingRetailer[] publishingRetailers =
                            Initialization.initializePubRetailer();
        Country[] countries = Initialization.initializeCountry();

        // If the initialization has already failed, then we stop.
        if (publishingRetailers == null || countries == null)
            return null;

        try (BufferedReader br = new BufferedReader
                (new FileReader("../publishing-retailers-countries.in"))){

            // The first line has only formatting information, so we skip it.
            String line = br.readLine();
            int crtRetailerID;
            int crtCountryID;
            int found;
            PublishingRetailer crtRetailer = null;
            Country crtCountry = null;


            while ((line = br.readLine()) != null){
                String[] tokens = line.split("###");

                crtRetailerID = Integer.parseInt(tokens[0]);
                crtCountryID = Integer.parseInt(tokens[1]);

                found = 0;
                for (int i = 0; i < publishingRetailers.length && found == 0; i++){
                    if (publishingRetailers[i].getID() == crtRetailerID){
                        crtRetailer = publishingRetailers[i];
                        found = 1;
                    }
                }

                found = 0;
                for (int i = 0; i < countries.length && found == 0; i++){
                    if (countries[i].getID() == crtCountryID){
                        crtCountry = countries[i];
                        found = 1;
                    }
                }

                crtRetailer.addCountries(crtCountry);
            }


        } catch (IOException e){
            e.printStackTrace();
            System.out.println
                    ("File \"publishing-retailers-countries.in\" doesn't exist.");
            return null;
        }

        return publishingRetailers;
    }

    /* This is a helper method that receives a vector of objects that can be
     * EditorialGroups, PublishingBrands or PublishingRetailers. In this vector,
     * the method will add to their corresponding list an artifact. If we are
     * talking about a PublishingRetailer, then we can add books, EditorialGroups
     * or PublishingBrands. If we are talking about the other cases, we will add to
     * the list books. This method is used to populate the classes' lists. This
     * method is used to limit the similar code.
     * For this method we use the codes for typeOfObjects:
     * 0 -> PublishingRetailer; 1 -> EditorialGroup; 2 -> PublishingBrand
     * For this method we use the codes for typeOfArtifact:
     * 0 -> Book; 1 -> EditorialGroup; 2 -> PublishingBrand
     */
    private static void addArtifactToObject(String[] tokens,
                                            Object[] objectInWhichWeAdd,
                                            IPublishingArtifact[] artifact,
                                            int typeOfObject, int typeOfArtifact){

        int crtObjectID;
        int crtArtifactID;
        int found;
        Object crtObject = null;
        IPublishingArtifact crtArtifact = null;

        crtObjectID = Integer.parseInt(tokens[0]);
        crtArtifactID = Integer.parseInt(tokens[1]);

        // We search for the object in which we will add the new element.
        found = 0;
        for (int i = 0; i < objectInWhichWeAdd.length && found == 0; i++){
            if (typeOfObject == 0) {
                if (((PublishingRetailer)objectInWhichWeAdd[i]).getID()
                        == crtObjectID) {

                    crtObject = objectInWhichWeAdd[i];
                    found = 1;
                }
            }
            else if (typeOfObject == 1){
                if (((EditorialGroup)objectInWhichWeAdd[i]).getID()
                        == crtObjectID){

                    crtObject = objectInWhichWeAdd[i];
                    found = 1;
                }
            }
            else if (typeOfObject == 2){
                if (((PublishingBrand)objectInWhichWeAdd[i]).getID()
                        == crtObjectID){

                    crtObject = objectInWhichWeAdd[i];
                    found = 1;
                }
            }
        }

        found = 0;
        for (int i = 0; i < artifact.length && found == 0; i++){
            if (typeOfArtifact == 0) {
                if (((Book)artifact[i]).getID() == crtArtifactID) {
                    crtArtifact = artifact[i];
                    found = 1;
                }
            }
            else if (typeOfArtifact == 1){
                if (((EditorialGroup)artifact[i]).getID() == crtArtifactID){
                    crtArtifact = artifact[i];
                    found = 1;
                }
            }
            else{
                if (((PublishingBrand)artifact[i]).getID() == crtArtifactID){
                    crtArtifact = artifact[i];
                    found = 1;
                }
            }
        }

        if (typeOfObject == 0) {
            ((PublishingRetailer) crtObject).addPublishingArtifact(crtArtifact);
        }
        else if (typeOfObject == 1){
            ((EditorialGroup) crtObject).addBooks((Book) crtArtifact);
        }
        else if (typeOfObject == 2){
            ((PublishingBrand)crtObject).addBooks((Book) crtArtifact);
        }
    }

    /* The method uses as parameters the retailers and the books, which need
     * to be initialized beforehand, and add the corresponding books to their
     * retailers.
     */
    public static void linkPubRetailersToBooks
            (PublishingRetailer[] publishingRetailers, Book[] books) {

        try (BufferedReader br = new BufferedReader
                (new FileReader("../publishing-retailers-books.in"))){

            // The first line has only formatting information, so we skip it.
            String line = br.readLine();

            while ((line = br.readLine()) != null){
                String[] tokens = line.split("###");
                addArtifactToObject(tokens, publishingRetailers, books, 0, 0);
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    // The method makes the link between retailers and editorial groups.
    public static void linkPubRetToEdGroups
            (PublishingRetailer[] publishingRetailers, EditorialGroup[] edGroups) {

        try (BufferedReader br = new BufferedReader
                (new FileReader("../publishing-retailers-editorial-groups.in"))){

            // The first line has only formatting information, so we skip it.
            String line = br.readLine();

            while ((line = br.readLine()) != null){
                String[] tokens = line.split("###");
                addArtifactToObject(tokens, publishingRetailers, edGroups, 0, 1);
            }


        } catch (IOException e){
            e.printStackTrace();
        }
    }

    // The method makes the link between retailers and publishing brands.
    public static void linkPubRetToPubBrands
            (PublishingRetailer[] publishingRetailers, PublishingBrand[] pubBrands) {

        try (BufferedReader br = new BufferedReader
                (new FileReader("../publishing-retailers-publishing-brands.in"))){

            // The first line has only formatting information, so we skip it.
            String line = br.readLine();

            while ((line = br.readLine()) != null){
                String[] tokens = line.split("###");
                addArtifactToObject(tokens, publishingRetailers, pubBrands, 0, 2);
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    // The method initializes the retailers and completes their lists.
    public static PublishingRetailer[] initializePubRetComplete(){
        Book[] books = initializeCompleteBooks();
        EditorialGroup[] edGroups = initializeEdGroupComplete(books);
        PublishingBrand[] pubBrands = initializePubBrandComplete(books);
        PublishingRetailer[] publishingRetailers =
                Initialization.initializePubRetCountries();

        // If the initialization has already failed, then we don't go further.
        if (books == null || edGroups == null || pubBrands == null
                || publishingRetailers == null){

            return null;
        }

        Initialization.linkPubRetailersToBooks(publishingRetailers, books);
        Initialization.linkPubRetToEdGroups(publishingRetailers, edGroups);
        Initialization.linkPubRetToPubBrands(publishingRetailers, pubBrands);

        return publishingRetailers;
    }
}
