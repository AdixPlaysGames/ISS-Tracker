package projekt.ISSTracker.issInformation;

import de.unknownreality.dataframe.DataFrame;
import org.apache.commons.lang3.ArrayUtils;
import org.json.JSONObject;
import org.json.JSONArray;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Date;

public class ISS {
	
	//Wartosci stacji kosmicznej o wymaganych przez https parametrach
	private final Double[] lattitude;
	private final Double[] longitude;
	private final Double[] altitude;
	private final Double[] velocity;
	private final Double[] footprint;
	private final Long[] timestamp;
	private final Double[] daynum;
	private final Double[] solar_lat;
	private final Double[] solar_lon;
	private int elemIndex;
	private final int size;
	private int pointer = -1;
	private static final DecimalFormat df = new DecimalFormat("0.00");
	private static final DecimalFormat df2nd = new DecimalFormat("0");


	public ISS(int size) {
		this.size = size;
		this.altitude = new Double[size];
		this.longitude = new Double[size];
		this.lattitude = new Double[size];
		this.velocity = new Double[size];
		this.footprint = new Double[size];
		this.timestamp = new Long[size];
		this.daynum = new Double[size];
		this.solar_lat = new Double[size];
		this.solar_lon = new Double[size];
		this.elemIndex = -1;
	}

	public ISS(DataFrame df) {
		this.size = df.size();
		this.elemIndex = -1;

		this.timestamp = ArrayUtils.toObject(Arrays.stream(df.getIntegerColumn("Timestamp").toArray(new Integer[size])).mapToLong(n -> Long.valueOf(n.longValue())).toArray());
		this.lattitude = df.getDoubleColumn("Lattitude").toArray(new Double[size]);
		this.longitude = df.getDoubleColumn("Longitude").toArray(new Double[size]);
		this.altitude = df.getDoubleColumn("Altitude").toArray(new Double[size]);
		this.velocity = df.getDoubleColumn("Velocity").toArray(new Double[size]);
		this.footprint = df.getDoubleColumn("Footprint").toArray(new Double[size]);
		this.daynum = df.getDoubleColumn("Daynum").toArray(new Double[size]);
		this.solar_lat = df.getDoubleColumn("Solar_lat").toArray(new Double[size]);
		this.solar_lon = df.getDoubleColumn("Solar_lon").toArray(new Double[size]);
	}

	public void displayData() {
		System.out.println("Predkosc: " + this.velocity[this.elemIndex - 1] + "km/h\nData: " +
				new Date() + "\nSzerokosc: " + this.lattitude[elemIndex - 1] +
				"\nDlugosc: " + this.longitude[this.elemIndex - 1] + "\n");
	}

	public void downloadPosition(String url) throws IOException {
		String jsonString = JSONReceiver.readJsonFromUrl(url);
		JSONObject iss;
		if (jsonString.startsWith("[")) {
			JSONArray array = new JSONArray(jsonString);
			iss = array.getJSONObject(0);
		} else {
			iss = new JSONObject(jsonString);
		}
		elemIndex++;
		if (elemIndex == this.size) {
			elemIndex = 0;
		}
		this.lattitude[elemIndex] = iss.getDouble("latitude");
		this.longitude[elemIndex] = iss.getDouble("longitude");
		this.altitude[elemIndex] = iss.getDouble("altitude");
		this.velocity[elemIndex] = iss.getDouble("velocity");
		this.footprint[elemIndex] = iss.getDouble("footprint");
		this.timestamp[elemIndex] = iss.getLong("timestamp");
		this.daynum[elemIndex] = iss.getDouble("daynum");
		this.solar_lat[elemIndex] = iss.getDouble("solar_lat");
		this.solar_lon[elemIndex] = iss.getDouble("solar_lon");
	}

	public Double[] getAltitude() {
		return altitude;
	}

	public Double[] getDaynum() {
		return daynum;
	}

	public Double[] getFootprint() {
		return footprint;
	}

	public Double[] getLattitude() {
		return lattitude;
	}

	public Double[] getLongitude() {
		return longitude;
	}

	public Double[] getVelocity() {
		return velocity;
	}

	public Long[] getTimestamp() {
		return timestamp;
	}

	public Double[] getSolar_lat() {
		return solar_lat;
	}

	public Double[] getSolar_lon() {
		return solar_lon;
	}

	public int getElemIndex() {
		return elemIndex;
	}

	public int getSize() {
		return size;
	}
	public void resetElemIndes() {
		this.elemIndex = -1;
	}

	public double getNewestLattitude() {
		return lattitude[elemIndex];
	}

	public double getNewestLongitude() {
		return longitude[elemIndex];
	}

	public double getNextLattitude() {
		pointer+=1;
		if (pointer >= size) {
			pointer = 0;
		}
		return lattitude[pointer];
	}

	public double getNextLongitude() {
		pointer+=1;
		if (pointer >= size) {
			pointer = 0;
		}
		return longitude[pointer];
	}

	public String getParams(boolean downloaded) {
		String params = new String();
		if (downloaded) {
			params = "Parametry: \nPrędkość: " + df2nd.format(velocity[pointer]) + "km/h" + "\n" +
			"Szerokość: " + df.format(lattitude[pointer]) + "S" + "\n" +
			"Długość: " + df.format(longitude[pointer]) + "E";
		} else {
			params = "Parametry: \nPrędkość: " + df2nd.format(velocity[elemIndex]) + "km/h" + "\n" +
					"Szerokość: " + df.format(lattitude[elemIndex]) + "S" + "\n" +
					"Długość: " + df.format(longitude[elemIndex]) + "E";
		}
		return params;
	}
}

