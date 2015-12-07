package cs4303.p2.util;


import cs4303.p2.game.App;

/**
 * Dashed Line Drawer
 * Code in this class has been adapted from http://www.openprocessing.org/sketch/7013.
 */
public class DashedLineDrawer {
    /**
     * Draw a dashed line with given set of dashes and gap lengths.
     * @param x0 starting x-coordinate of line.
     * @param y0 starting y-coordinate of line.
     * @param x1 ending x-coordinate of line.
     * @param y1 ending y-coordinate of line.
     * @param spacing array giving lengths of dashes and gaps in pixels;
     *  an array with values {5, 3, 9, 4} will draw a line with a
     *  5-pixel dash, 3-pixel gap, 9-pixel dash, and 4-pixel gap.
     *  if the array has an odd number of entries, the values are
     *  recycled, so an array of {5, 3, 2} will draw a line with a
     *  5-pixel dash, 3-pixel gap, 2-pixel dash, 5-pixel gap,
     *  3-pixel dash, and 2-pixel gap, then repeat.
     */
    public static void dashline(float x0, float y0, float x1, float y1, float[] spacing) {
        float distance = App.dist(x0, y0, x1, y1);
        float[] xSpacing = new float[spacing.length];
        float[] ySpacing = new float[spacing.length];
        float drawn = 0.0f;  // amount of distance drawn

        if (distance > 0) {
            int i;
            boolean drawLine = true; // alternate between dashes and gaps

            for (i = 0; i < spacing.length; i++) {
                xSpacing[i] = App.lerp(0, (x1 - x0), spacing[i] / distance);
                ySpacing[i] = App.lerp(0, (y1 - y0), spacing[i] / distance);
            }

            i = 0;
            while (drawn < distance) {
                if (drawLine) {
                    App.app.line(x0, y0, x0 + xSpacing[i], y0 + ySpacing[i]);
                }
                x0 += xSpacing[i];
                y0 += ySpacing[i];
      /* Add distance "drawn" by this line or gap */
                drawn = drawn + App.mag(xSpacing[i], ySpacing[i]);
                i = (i + 1) % spacing.length;  // cycle through array
                drawLine = !drawLine;  // switch between dash and gap
            }
        }
    }

    /**
     * Draw a dashed line with given dash and gap length.
     * @param x0 starting x-coordinate of line.
     * @param y0 starting y-coordinate of line.
     * @param x1 ending x-coordinate of line.
     * @param y1 ending y-coordinate of line.
     * @param dash - length of dashed line in pixels
     * @param gap - space between dashes in pixels
     */
    public static void dashline(float x0, float y0, float x1, float y1, float dash, float gap) {
        float[] spacing = {dash, gap};
        dashline(x0, y0, x1, y1, spacing);
    }
}