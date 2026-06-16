package com.formation.myecommerceapp.domain.di

import com.formation.myecommerceapp.domain.ui.cart.CartViewModel
import com.formation.myecommerceapp.domain.ui.productdetails.ProductDetailsViewModel
import com.formation.myecommerceapp.domain.ui.productlist.ProductListViewModel
import org.koin.dsl.module
import org.koin.plugin.module.dsl.viewModel

val viewModelModule = module {
    viewModel<ProductListViewModel>()
    viewModel<ProductDetailsViewModel>()
    viewModel<CartViewModel>()
}