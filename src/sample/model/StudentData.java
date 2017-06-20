package sample.model;

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

    public int getExamsAttended()
    {
        return examsAttended;
    }

    public void setExamsAttended(int examsAttended)
    {
        this.examsAttended = examsAttended;
    }

    /**
     * build a new student data which indicate it cleared 1 level by default to avoid some bug
     */
    public StudentData()
    {
        examsAttended = 1;
    }

    @Override
    public String toString()
    {
        return "StudentData{" +
                "examsAttended=" + examsAttended +
                '}';
    }
}
