import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class TestFinal {
    public static void main(String[] args) {
        Administration administration = new Administration();
        List<Book> booksForRetailers;
        List<Language> languagesForRetailers;
        List<Country> countriesForBook;
        List<Book> commonBooks;
        List<Book> allBooks;

        // We verify if the initialization failed or not.
        if (administration.getPublishingRetailers() == null)
            return;

        // We read the tests from the teste.txt file.
        try (BufferedReader br = new BufferedReader(new FileReader("../teste.txt"))) {
            String line;

            /* We test each implemented method in the Administration class with 5
             * examples. If the initialization failed, we will not get to this step,
             * so from this point there will be no NullPointerException.
             */
            for (int i = 0; i < 5; i++) {
                line = br.readLine();
                int index = Integer.parseInt(line);
                booksForRetailers = administration.getBooksForPublishingRetailerID(index);

                System.out.println("Books for retailer " + index);
                for (Book book : booksForRetailers)
                    System.out.println(book.getName());
                System.out.println();
            }

            for (int i = 0; i < 5; i++) {
                line = br.readLine();
                int index = Integer.parseInt(line);
                languagesForRetailers = administration.getLanguagesForPublishingRetailerID(index);

                System.out.println("Languages for retailer " + index);
                for (Language language : languagesForRetailers)
                    System.out.println(language.getCode());
                System.out.println();
            }

            for (int i = 0; i < 5; i++) {
                line = br.readLine();
                int index = Integer.parseInt(line);
                countriesForBook = administration.getCountriesForBookID(index);

                System.out.println("Countries for book " + index);
                for (Country country : countriesForBook)
                    System.out.println(country.getCountryCode());
                System.out.println();
            }

            for (int i = 0; i < 5; i++) {
                String[] tokens;

                line = br.readLine();
                tokens = line.split(" ");
                int index1 = Integer.parseInt(tokens[0]);
                int index2 = Integer.parseInt(tokens[1]);

                System.out.println("Common books for retailer " + index1 +
                                    " and " + index2);
                commonBooks = administration.getCommonBooksForRetailerIDs(index1, index2);
                for (Book book : commonBooks)
                    System.out.println(book.getName());
                System.out.println();
            }

            for (int i = 0; i < 5; i++) {
                String[] tokens;

                line = br.readLine();
                tokens = line.split(" ");
                int index1 = Integer.parseInt(tokens[0]);
                int index2 = Integer.parseInt(tokens[1]);

                System.out.println("All books for retailers " + index1 +
                                    " and " + index2);
                allBooks = administration.getAllBooksForRetailersIDs(index1, index2);
                for (Book book : allBooks)
                    System.out.println(book.getName());
                System.out.println();
            }
        }
        catch (IOException ex){
            System.out.println("The file \"teste.txt\" doesn't exist");
        }
    }
}
