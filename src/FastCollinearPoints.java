import java.util.Arrays;
import java.util.Comparator;

import edu.princeton.cs.algs4.Merge;

public class FastCollinearPoints {
    private final LineSegment[] lines;
    private int count = 0;
    private Point[] chosen;
    private Point[] copy;

    public FastCollinearPoints(Point[] points) {
        if (points == null || checkForNull(points))
            throw new IllegalArgumentException("invalid points");
        Merge.sort(points);
        if (checkForDuplicates(points))
            throw new IllegalArgumentException("Duplicated points");
        int n = points.length;
        int maxSize = n / 3;
        lines = new LineSegment[maxSize];
        for (int i = 0; i < n; i++) {
            copy = Arrays.copyOf(points, points.length);
            Comparator<Point> c = copy[i].slopeOrder();
            Arrays.sort(copy, c);
            int j = 1, save = 0, size = 0;
            boolean leave = false;
            while (j < n - 1 && !leave) {
                size = 0;
                while (j < n - 1 && copy[j].slopeTo(points[i]) != copy[j + 1].slopeTo(points[i]))
                    j++;
                save = j;
                while (j < n - 1 && copy[j].slopeTo(points[i]) == copy[j + 1].slopeTo(points[i])) {
                    size++;
                    j++;
                    if (size >= 2)
                        leave = true; // error here as it doesn't complete looping for same point.
                }
            }
            if (size >= 2) {
                chosen = new Point[size + 1];
                for (int k = 0; k < size + 1; k++)
                    chosen[k] = copy[save++];
                Merge.sort(chosen);
                if (chosen[0].compareTo(points[i]) > 0)
                    lines[count++] = new LineSegment(points[i], chosen[size]);
            }
        }
    }

    public int numberOfSegments() {
        return count;
    }

    public LineSegment[] segments() {
        LineSegment[] segments = Arrays.copyOf(lines, count);
        return segments;
    }

    private static boolean checkForNull(Point[] p) {
        for (int i = 0; i < p.length - 1; i++)
            if (p[i] == null || p[i + 1] == null)
                return true;
        return false;
    }

    private static boolean checkForDuplicates(Point[] p) {
        for (int i = 0; i < p.length - 1; i++)
            if (p[i] == null || p[i + 1] == null || p[i].compareTo(p[i + 1]) == 0)
                return true;
        return false;
    }

    public static void main(String[] args) {
        Point[] p = new Point[9];
        p[3] = new Point(0, 1);
        p[1] = new Point(2, 3);
        p[5] = new Point(3, 4);
        p[6] = new Point(4, 5);
        p[0] = new Point(3, 2);
        p[2] = new Point(4, 1);
        p[4] = new Point(5, 0);
        p[7] = new Point(1, 4);
        p[8] = new Point(5, 6);
        FastCollinearPoints fp = new FastCollinearPoints(p);
        System.out.println(fp.numberOfSegments());
        LineSegment[] l = fp.segments();
        for (int i = 0; i < fp.numberOfSegments(); i++)
            System.out.println(l[i].toString());
    }
}
