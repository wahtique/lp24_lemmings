package sample;

import java.io.Serializable;

/**
 * The class representing the progression of our students toward validating their course.
 * Will be used to build lemmings which will be the physical representation of our students.
 * @author William
 * @since 14/05/2017
 */
public class StudentData implements Serializable
{
    /** The color of the lemmings built from this student should match the nunmber of exams he could attend.
     * This is equivalent to the number of levels he cleared*/
    private int examsAttended;

    /*More fields will / can be added latter according to our needs*/


    public int getExamsAttended()
    {
        return examsAttended;
    }

    public void setExamsAttended(int examsAttended)
    {
        this.examsAttended = examsAttended;
    }

    public StudentData()
    {
        examsAttended = 0;
    }

}
