package projekt.ISSTracker.data;

import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import de.unknownreality.dataframe.DataFrame;
import de.unknownreality.dataframe.column.DoubleColumn;
import de.unknownreality.dataframe.column.LongColumn;
import projekt.ISSTracker.issInformation.ISS;

import java.util.concurrent.locks.ReentrantLock;

public abstract class DataManager extends Thread{
    protected ISS iss;
    protected ReentrantLock mutex = new ReentrantLock();
    private CSVWriteThread writer;
    protected boolean save = false;
    private String savePath;
    protected boolean isTurned = true;

    public DataManager(int size) {
        iss = new ISS(size);
    }

    public void turnOn() {
        this.isTurned = true;
    }

    public void turnOff() {
        this.isTurned = false;
        this.iss.resetElemIndes();
    }

    public void lock() {
        this.mutex.lock();
    }

    public void unlock() {
        this.mutex.unlock();
    }

    public boolean isValue() {
        return this.iss.getElemIndex() != -1;
    }

    public void setSave(String savePath) {
        this.savePath = savePath;
        this.save = true;
    }

    protected void saveToCsv() {
        DataFrame df = DataFrame.create();

        df.addColumn(new LongColumn("Timestamp", this.iss.getTimestamp()));
        df.addColumn(new DoubleColumn("Lattitude", this.iss.getLattitude()));
        df.addColumn(new DoubleColumn("Longitude", this.iss.getLongitude()));
        df.addColumn(new DoubleColumn("Altitude", this.iss.getAltitude()));
        df.addColumn(new DoubleColumn("Velocity", this.iss.getVelocity()));
        df.addColumn(new DoubleColumn("Footprint", this.iss.getFootprint()));
        df.addColumn(new DoubleColumn("Daynum", this.iss.getDaynum()));
        df.addColumn(new DoubleColumn("Solar_lat", this.iss.getSolar_lat()));
        df.addColumn(new DoubleColumn("Solar_lon", this.iss.getSolar_lon()));
        this.save = false;

        this.writer = new CSVWriteThread(this.savePath, df, iss.getElemIndex() + 1);
        this.writer.start();
    }

    public Point getNewestPosition() {
        mutex.lock();
        Point point = new Point(iss.getNewestLongitude(), iss.getNewestLattitude(), SpatialReferences.getWgs84());
        mutex.unlock();
        return point;
    }

    public String getParams() {
        return iss.getParams(false);
    }
}
