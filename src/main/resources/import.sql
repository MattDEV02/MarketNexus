--SET TIME ZONE 'Europe/Rome';

SELECT TRUNC(c.cart_price)                               AS expected,
       TRUNC(GET_CARTLINEITEMS_PRICE_SUM_FROM_CARTID(1)) AS actual,
       ((TRUNC(c.cart_price) =
         TRUNC(GET_CARTLINEITEMS_PRICE_SUM_FROM_CARTID(1))) OR c.cart_price = 0)
                                                         AS are_equals
FROM MarketNexus.Carts c
WHERE c.id = 1