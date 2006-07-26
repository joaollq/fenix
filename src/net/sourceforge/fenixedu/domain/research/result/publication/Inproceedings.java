package net.sourceforge.fenixedu.domain.research.result.publication;

import net.sourceforge.fenixedu.domain.Person;
import net.sourceforge.fenixedu.domain.exceptions.DomainException;

/**
 * (conference: The same as Inproceedings.)
 * An article in a conference proceedings.
 * Required fields: author, title, booktitle, year.
 * Optional fields: editor, pages, organization, publisher, address, month, note.
 * 
 * Extra from previous publications: conference, language
 */
public class Inproceedings extends Inproceedings_Base {
    
    public  Inproceedings() {
        super();
    }
    
    //constructor with required fields
    public Inproceedings(Person participator, String title, String bookTitle, Integer year) {
        super();
        if((participator == null) || (title == null) || (bookTitle == null) || (year == null))
            throw new DomainException("error.publication.missingRequiredFields");
        
        setParticipation(participator);
        setTitle(title);
        setBookTitle(bookTitle);
        setYear(year);
    }
    
    //edit with required fields
    public void edit(String title, String bookTitle, Integer year) {
        if((title == null) || (bookTitle == null) || (year == null))
            throw new DomainException("error.publication.missingRequiredFields");
        
        setTitle(title);
        setBookTitle(bookTitle);
        setYear(year);
    }
}
