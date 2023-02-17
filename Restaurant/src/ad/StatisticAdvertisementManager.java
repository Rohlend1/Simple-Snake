package ad;

import java.util.ArrayList;
import java.util.List;

public class StatisticAdvertisementManager {
    private static final StatisticAdvertisementManager ourInstance = new StatisticAdvertisementManager();
    private final AdvertisementStorage storage = AdvertisementStorage.getInstance();

    public static StatisticAdvertisementManager getInstance() {
        return ourInstance;
    }

    private StatisticAdvertisementManager() {
    }

    public List<Advertisement> getVideoSet(boolean isActive) {
        List<Advertisement> result = new ArrayList<>();
        for (Advertisement advertisement : storage.list()) {
            if (!isActive ^ advertisement.isActive()) {
                result.add(advertisement);
            }
        }
        return result;
    }

}
