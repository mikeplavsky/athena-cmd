select b from (
    select sequence(1, 10, 1)) as x (n) 
CROSS JOIN UNNEST(n) as res(b)
