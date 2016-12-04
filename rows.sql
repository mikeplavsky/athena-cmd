SELECT *
FROM (
    VALUES

    (ARRAY[2, 5], ARRAY['dog', 'cat', 'bird']),
    (ARRAY[7, 8, 9], ARRAY['cow', 'pig'])

) AS x (numbers, animals)

