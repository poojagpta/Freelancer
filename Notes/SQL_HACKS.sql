1. Find the first value order by device_events_ts, we can use below hack:

    MAX(STRUCT(device_event_ts, build)).col2 AS build
               
2. Concat all non-null string
       concat_ws('&', collect_set(IF(concat(test_id,':',bucket)  LIKE '%:%', concat(test_id,':',bucket) , NULL)))
                                     as active_exp_map
                                     
3. Other things                                    
