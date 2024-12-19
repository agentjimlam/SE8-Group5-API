package sg.edu.ntu.bus_api.exception;

public class BusStopDuplicateException extends RuntimeException{
    public BusStopDuplicateException (Long id, String busStopCode){
        super("Bus stop code already exists: " + busStopCode + ", at existing id: " + id);
    }
}
