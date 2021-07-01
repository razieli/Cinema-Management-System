package il.ac.haifa.cs.sweng.cms;

import il.ac.haifa.cs.sweng.cms.common.entities.Screening;

import java.util.Comparator;

class SortByDate implements Comparator<Screening> {
    // Used for sorting in ascending order of screening dates
    public int compare(Screening a, Screening b)
    {
        return a.getDate().compareTo(b.getDate());
    }
}
