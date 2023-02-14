package projekt.ISSTracker.data;

import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import de.unknownreality.dataframe.DataFrame;
import projekt.ISSTracker.issInformation.ISS;

import java.io.File;

public class DataCSVReader {
    private ISS iss;

    public boolean readCSV(File file, char sep) {
        try {
            DataFrame df = DataFrame.fromCSV(file, sep, true);
            this.iss = new ISS(df);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    public Point getNextPosition() {
        Point point = new Point(iss.getNextLongitude(), iss.getNextLattitude(), SpatialReferences.getWgs84());
        return point;
    }

    public String getParams() {
        return iss.getParams(true);
    }
}
