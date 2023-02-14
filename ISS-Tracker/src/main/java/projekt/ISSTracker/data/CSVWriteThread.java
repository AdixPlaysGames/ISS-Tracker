package projekt.ISSTracker.data;

import de.unknownreality.dataframe.DataFrame;

import java.io.File;

public class CSVWriteThread extends Thread {
    private DataFrame df;
    private File file;

    public CSVWriteThread(String savePath, DataFrame df, int endIndex) {
        this.df = df.selectSubset(0, endIndex);
        this.file = new File(savePath);
    }

    @Override
    public void run() {
        this.df.writeCSV(this.file, ',', true);
    }
}
