/**
 * This class represents a library, which hold a collection of books. Patrons can register at the library to
 * be able to check out books, if a copy of the requested book is available.
 */
class Library {

    /**
     * The maximal number of books this library can hold.
     */
    final int libraryMaxBook;

    /**
     * The maximal number of books this library allows a single patron to borrow at the same time.
     */
    final int libraryMaxBorrowed;

    /**
     * The maximal number of registered patrons this library can handle.
     */
    final int libraryMaxPatron;

    /**
     * The library's books registration
     */
    Book[] libraryBooks;

    /**
     * The library's patrons registration, and sum of borrowed books per patron.
     */
    Patron[] libraryPatrons;
    int[] patronBorrowedBooks;

    /**
     * Current book and patron numbers. These are being used as the indices of newly added book/patron
     * (initialized as 0)
     */
    int currentBookNum, currentPatronNum;

    /*----=  Constructors  =-----*/

    /**
     * Creates a new library with the given parameters.
     *
     * @param maxBookCapacity   The maximal number of books this library can hold.
     * @param maxBorrowedBooks  The maximal number of books this library allows a single patron to borrow at
     *                          the same time.
     * @param maxPatronCapacity The maximal number of registered patrons this library can handle.
     */
    Library(int maxBookCapacity, int maxBorrowedBooks, int maxPatronCapacity) {
        libraryMaxBook = maxBookCapacity;
        libraryMaxBorrowed = maxBorrowedBooks;
        libraryMaxPatron = maxPatronCapacity;

        // initializing data members
        libraryBooks = new Book[libraryMaxBook];
        libraryPatrons = new Patron[libraryMaxPatron];
        patronBorrowedBooks = new int[libraryMaxPatron];
        currentBookNum = 0;
        currentPatronNum = 0;

    }

    /*----=  Instance Methods  =-----*/

    /**
     * Adds the given book to this library, if there is a place available, and it isn't already in the
     * library.
     *
     * @param book The book to add to this library.
     * @return a non-negative id number for the book if there was a spot and the book was successfully added,
     * or if the book was already in the library; a negative number otherwise.
     */
    int addBookToLibrary(Book book) {
        int bookId = getBookId(book);
        if (bookId != -1) {
            return bookId;
        } else {
            if (currentBookNum < libraryMaxBook) {
                bookId = currentBookNum;
                libraryBooks[bookId] = book;
                currentBookNum++;
                return bookId;
            }
            return -1;
        }
    }

    /**
     * Returns true if the given number is an id of some book in the library, false otherwise.
     *
     * @param bookId The id to check.
     * @return true if the given number is an id of some book in the library, false otherwise.
     */
    boolean isBookIdValid(int bookId) {
        return (0 <= bookId && bookId < currentBookNum);
    }

    /**
     * Returns the non-negative id number of the given book if he is owned by this library, -1 otherwise.
     *
     * @param book The book for which to find the id number.
     * @return a non-negative id number of the given book if he is owned by this library, -1 otherwise.
     */
    int getBookId(Book book) {
        for (int i = 0; i < currentBookNum; i++) {
            if (libraryBooks[i] == book)
                return i;
        }
        return -1;
    }

    /**
     * Returns true if the book with the given id is available, false otherwise.
     *
     * @param bookId The id number of the book to check.
     * @return true if the book with the given id is available, false otherwise.
     */
    boolean isBookAvailable(int bookId) {
        if (isBookIdValid(bookId))
            return libraryBooks[bookId].getCurrentBorrowerId() == -1;
        return false;
    }

    /**
     * Registers the given Patron to this library, if there is a spot available.
     *
     * @param patron The patron to register to this library.
     * @return a non-negative id number for the patron if there was a spot and the patron was successfully
     * registered, a negative number otherwise.
     */
    int registerPatronToLibrary(Patron patron) {
        int patronId = getPatronId(patron);
        if (patronId != -1) {
            return patronId;
        } else {
            if (currentPatronNum < libraryMaxPatron) {
                patronId = currentPatronNum;
                libraryPatrons[patronId] = patron;
                currentPatronNum++;
                return patronId;
            }
            return -1;
        }
    }

    /**
     * Returns true if the given number is an id of a patron in the library, false otherwise.
     *
     * @param patronId The id to check.
     * @return true if the given number is an id of a patron in the library, false otherwise.
     */
    boolean isPatronIdValid(int patronId) {
        return (0 <= patronId && patronId < currentPatronNum);
    }

    /**
     * Returns the non-negative id number of the given patron if he is registered to this library,
     * -1 otherwise.
     *
     * @param patron The patron for which to find the id number.
     * @return a non-negative id number of the given patron if he is registered to this library, -1 otherwise.
     */
    int getPatronId(Patron patron) {
        for (int i = 0; i < currentPatronNum; i++) {
            if (libraryPatrons[i] == patron)
                return i;
        }
        return -1;
    }

    /**
     * Marks the book with the given id number as borrowed by the patron with the given patron id, if this
     * book is available, the given patron isn't already borrowing the maximal number of books allowed, and
     * if the patron will enjoy this book.
     *
     * @param bookId   The id number of the book to borrow.
     * @param patronId The id number of the patron that will borrow the book.
     * @return true if the book was borrowed successfully, false otherwise.
     */
    boolean borrowBook(int bookId, int patronId) {
        if (!isPatronIdValid(patronId) || !isBookIdValid(bookId))
            return false;
        if (isBookAvailable(bookId) && !borrowedLimitReached(patronId) &&
                patronWillEnjoyBook(patronId, bookId)) {
            libraryBooks[bookId].setBorrowerId(patronId);
            patronBorrowedBooks[patronId]++;
            return true;
        }
        return false;
    }

    /**
     * Returns true if libraryMaxBorrowed was reached by given patron, false otherwise.
     *
     * @param patronId The id number of the patron to check.
     * @return true if the patron can't borrow more books, false otherwise.
     */
    boolean borrowedLimitReached(int patronId) {
        if (!isPatronIdValid(patronId))
            return false;
        return patronBorrowedBooks[patronId] == libraryMaxBorrowed;
    }

    /**
     * return true if patron will enjoy the requested book, false otherwise.
     *
     * @param patronId The id number of the patron that will borrow the book.
     * @param bookId   The id number of the book to borrow.
     * @return true upon enjoyment, false otherwise.
     */
    boolean patronWillEnjoyBook(int patronId, int bookId) {
        if (!isBookIdValid(bookId) || !isPatronIdValid(patronId))
            return false;
        return libraryPatrons[patronId].willEnjoyBook(libraryBooks[bookId]);
    }

    /**
     * Return the given book.
     *
     * @param bookId The id number of the book to return.
     */
    void returnBook(int bookId) {
        if (!isBookIdValid(bookId))
            return;
        int patronId = libraryBooks[bookId].getCurrentBorrowerId();
        libraryBooks[bookId].returnBook();
        patronBorrowedBooks[patronId]--;
    }

    /**
     * Suggest the patron with the given id the book he will enjoy the most, out of all available books he
     * will enjoy, if any such exist.
     *
     * @param patronId The id number of the patron to suggest the book to.
     * @return The available book the patron with the given will enjoy the most. Null if no book is available.
     */
    Book suggestBookToPatron(int patronId) {
        if (!isPatronIdValid(patronId))
            return null;
        Patron patron = libraryPatrons[patronId];
        int maxEnjoyment = 0;
        Book suggestedBook = null;
        for (int i = 0; i < currentBookNum; i++) {
            int curBookEnjoyment = patron.getBookScore(libraryBooks[i]);
            Book book = libraryBooks[i];
            if (patron.willEnjoyBook(book) && maxEnjoyment < curBookEnjoyment && isBookAvailable(i)) {
                maxEnjoyment = curBookEnjoyment;
                suggestedBook = book;
            }
        }
        return suggestedBook;
    }

}

