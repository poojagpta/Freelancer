1. Find the first value order by device_events_ts, we can use below hack:

    MAX(STRUCT(device_event_ts, build)).col2 AS build
