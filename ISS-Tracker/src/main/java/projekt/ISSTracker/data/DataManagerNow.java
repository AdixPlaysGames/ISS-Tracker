package projekt.ISSTracker.data;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class DataManagerNow extends DataManager{
    public DataManagerNow(int size) {
        super(size);
    }
    private String createRequestNow() { return "https://api.wheretheiss.at/v1/satellites/25544"; }

    private void addNewDataFromNow() throws IOException {
        this.iss.downloadPosition(createRequestNow());
    }

    @Override
    public void run() {
        while (true) {
            if (this.isTurned) {
                try {
                    this.addNewDataFromNow();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (this.save) {
                    this.saveToCsv();
                }
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
