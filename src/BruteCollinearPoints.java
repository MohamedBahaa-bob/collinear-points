
import java.util.Arrays;
import java.util.Comparator;
import edu.princeton.cs.algs4.Merge;

public class BruteCollinearPoints {
    private LineSegment[] lines;
    private int count = 0;

    public BruteCollinearPoints(Point[] points) {
        if (points == null || checkForNull(points))
            throw new IllegalArgumentException("invalid points");
        Merge.sort(points);
        if (checkForDuplicates(points))
            throw new IllegalArgumentException("Duplicated points");
        int n = points.length;
        int maxSize = n / 3;
        lines = new LineSegment[maxSize];
        for (int i = 0; i < n; i++) {
            Comparator<Point> c = points[i].slopeOrder();
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    if (c.compare(points[j], points[k]) == 0) {
                        for (int m = k + 1; m < n; m++) {
                            if (c.compare(points[k], points[m]) == 0)
                                lines[count++] = new LineSegment(points[i], points[m]);
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() { // the number of line segments
        return count;
    }

    public LineSegment[] segments() { // the line segments
        LineSegment[] segments=Arrays.copyOf(lines, count);
        return segments;
    }
    private static boolean checkForNull(Point[] p) {
        for (int i = 0; i < p.length - 1; i++)
            if (p[i] == null || p[i+1]==null)
                return true;
        return false;
    }
    
    private static boolean checkForDuplicates(Point[] p) {
        for (int i = 0; i < p.length - 1; i++)
            if (p[i] == null || p[i+1]==null || p[i].compareTo(p[i + 1]) == 0)
                return true;
        return false;
    }

    public static void main(String[] args) {
        Point[] p = new Point[7];
        p[3] = new Point(0, 1);
        p[1] = new Point(2, 3);
        p[5] = new Point(3, 4);
        p[6] = new Point(4, 5);
        p[0] = new Point(3, 2);
        p[2] = new Point(4, 1);
        p[4] = new Point(5, 0);
        Merge.sort(p);
        for (int i = 0; i < p.length; i++)
            System.out.println(p[i].toString());
        BruteCollinearPoints bp = new BruteCollinearPoints(p);
        System.out.println(bp.numberOfSegments());
        LineSegment[] lineS = bp.segments();
        for (int i = 0; i < bp.numberOfSegments(); i++)
            System.out.println(lineS[i].toString());
    }
}
