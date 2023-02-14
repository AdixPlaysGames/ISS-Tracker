package projekt.ISSTracker.data;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class DataManagerDates extends DataManager {

    private Long time;

    public DataManagerDates(int size) {
        super(size);
    }

    public void setDate(Long date) {
        this.mutex.lock();
        this.iss.resetElemIndes();
        this.time = date;
        this.mutex.unlock();
    }

    private String createRequestFromTime(Long time) {
        String request = "https://api.wheretheiss.at/v1/satellites/25544/positions?timestamps=";
        request += Long.toString(time);
        request += "&units=kilometers";
        return request;
    }

    private void addOldDataFromTime() throws IOException {
        this.iss.downloadPosition(createRequestFromTime(this.time));
    }


    @Override
    public void run() {
        while (true) {
            if (isTurned) {
                this.mutex.lock();
                try {
                    this.addOldDataFromTime();
                    this.time += 1;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (this.save) {
                    this.saveToCsv();
                }
                this.mutex.unlock();
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}


