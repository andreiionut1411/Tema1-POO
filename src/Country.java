public class Country implements Comparable<Country> {
    private int ID;
    private String countryCode;

    public Country(int ID, String countryCode) {
        this.ID = ID;
        this.countryCode = countryCode;
    }

    public int getID() {
        return ID;
    }

    public String getCountryCode() {
        return countryCode;
    }

    /* We implement the compareTo method so that we can sort the countries.
     * We will need to sort them for the getCountriesForBookID method from
     * the Administration class.
     */
    @Override
    public int compareTo(Country o) {
        return this.ID - o.getID();
    }
}
