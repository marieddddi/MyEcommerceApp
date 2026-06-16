package com.formation.myecommerceapp.domain.domain.exception

class ProductNotFoundException(
    productId: Int
) : Exception(
    "Product with id $productId not found"
)