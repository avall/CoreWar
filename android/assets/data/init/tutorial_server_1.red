start       spl     1,      <300    ;\
        spl     1,      <150    ;  generate 7 consecutive processes
        mov     -1,     0       ;/

silk    spl     3620,   #0      ;split to new copy
        mov.i   >-1,    }-1     ;copy self to new location