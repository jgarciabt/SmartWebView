package jgarciabt.smartwebview.bus;

import com.squareup.otto.Bus;

import java.util.Objects;

/**
 * Created by JGarcia on 28/3/15.
 */
public class BusManager {

    private static BusManager instance;
    private Bus bus;

    private BusManager()
    {
        bus = new Bus();
    }

    static public BusManager getInstance()
    {
        if(instance == null)
        {
            instance = new BusManager();
        }
        return instance;
    }

    public void register(Object o)
    {
        bus.register(o);
    }

    public void unregister(Objects o)
    {
        bus.unregister(o);
    }

    public void post(Object o)
    {
        bus.post(o);
    }

}
