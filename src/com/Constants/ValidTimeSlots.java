package com.Constants;

import com.Model.*;

public class ValidTimeSlots {
    public static Slot[] data = {
            // monday & wednesdays:
            new Slot(DaySeries.MO, SlotType.BOTH, 8, 0),
            new Slot(DaySeries.MO, SlotType.BOTH, 9, 0),
            new Slot(DaySeries.MO, SlotType.BOTH, 10, 0),
            new Slot(DaySeries.MO, SlotType.BOTH, 11, 0),
            new Slot(DaySeries.MO, SlotType.BOTH, 12, 0),
            new Slot(DaySeries.MO, SlotType.BOTH, 13, 0),
            new Slot(DaySeries.MO, SlotType.BOTH, 14, 0),
            new Slot(DaySeries.MO, SlotType.BOTH, 15, 0),
            new Slot(DaySeries.MO, SlotType.BOTH, 16, 0),
            new Slot(DaySeries.MO, SlotType.BOTH, 17, 0),
            new Slot(DaySeries.MO, SlotType.BOTH, 18, 0),
            new Slot(DaySeries.MO, SlotType.BOTH, 19, 0),
            new Slot(DaySeries.MO, SlotType.BOTH, 20, 0),

            // friday
            new Slot(DaySeries.FR, SlotType.COURSE, 8, 0),
            new Slot(DaySeries.FR, SlotType.COURSE, 9, 0),
            new Slot(DaySeries.FR, SlotType.COURSE, 10, 0),
            new Slot(DaySeries.FR, SlotType.COURSE, 11, 0),
            new Slot(DaySeries.FR, SlotType.COURSE, 12, 0),
            new Slot(DaySeries.FR, SlotType.COURSE, 13, 0),
            new Slot(DaySeries.FR, SlotType.COURSE, 14, 0),
            new Slot(DaySeries.FR, SlotType.COURSE, 15, 0),
            new Slot(DaySeries.FR, SlotType.COURSE, 16, 0),
            new Slot(DaySeries.FR, SlotType.LAB, 8, 0),
            new Slot(DaySeries.FR, SlotType.LAB, 10, 0),
            new Slot(DaySeries.FR, SlotType.LAB, 12, 0),
            new Slot(DaySeries.FR, SlotType.LAB, 14, 0),
            new Slot(DaySeries.FR, SlotType.LAB, 16, 0),
            new Slot(DaySeries.FR, SlotType.LAB, 18, 0),

            // tuesday & thursdays:
            new Slot(DaySeries.TU, SlotType.BOTH, 8, 0),
            new Slot(DaySeries.TU, SlotType.COURSE, 9, 30),
            new Slot(DaySeries.TU, SlotType.BOTH, 11, 0),
            new Slot(DaySeries.TU, SlotType.COURSE, 12, 30),
            new Slot(DaySeries.TU, SlotType.BOTH, 14, 0),
            new Slot(DaySeries.TU, SlotType.COURSE, 15, 30),
            new Slot(DaySeries.TU, SlotType.COURSE, 17, 0),
            new Slot(DaySeries.TU, SlotType.COURSE, 18, 30),
            new Slot(DaySeries.TU, SlotType.LAB, 9, 0),
            new Slot(DaySeries.TU, SlotType.LAB, 10, 0),
            new Slot(DaySeries.TU, SlotType.LAB, 12, 0),
            new Slot(DaySeries.TU, SlotType.LAB, 13, 0),
            new Slot(DaySeries.TU, SlotType.LAB, 15, 0),
            new Slot(DaySeries.TU, SlotType.LAB, 16, 0),
            new Slot(DaySeries.TU, SlotType.LAB, 17, 0),
            new Slot(DaySeries.TU, SlotType.LAB, 18, 0),
            new Slot(DaySeries.TU, SlotType.LAB, 19, 0),
            new Slot(DaySeries.TU, SlotType.LAB, 20, 0),
    };
}
