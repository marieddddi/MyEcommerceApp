package com.formation.myecommerceapp.domain.ui.productlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.formation.myecommerceapp.domain.data.remote.EmptyBodyException
import com.formation.myecommerceapp.domain.data.remote.ErrorResultFromRemoteException
import com.formation.myecommerceapp.domain.data.remote.NetworkException
import com.formation.myecommerceapp.domain.data.remote.dto.toProduct
import com.formation.myecommerceapp.domain.data.remote.service.ProductApiService
import com.formation.myecommerceapp.ui.productlist.state.ProductListState
import com.formation.myecommerceapp.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class ProductListViewModel(
    private val productApiService: ProductApiService,
) : ViewModel() {
    private val _state = MutableStateFlow<Result<ProductListState>>(Result.Loading)
    val state: StateFlow<Result<ProductListState>>
        get() = _state.asStateFlow()

    init {
        loadProductsFromApi()
    }

    private fun loadProductsFromApi() {
        viewModelScope.launch {
            _state.value = Result.Loading // On passe en état de chargement

            try {
                val response = productApiService.getProducts()

                if (!response.isSuccessful) {
                    throw ErrorResultFromRemoteException("L'API a renvoyé une erreur : ${response.code()}")
                }

                // 1. On récupère le body (qui est maintenant un ProductResponse)
                val apiResponse = response.body() ?: throw EmptyBodyException("Le corps de la réponse est vide.")

                // 2. On extrait la liste de produits pour le .map
                val productDtos = apiResponse.products

                // 3. Le reste de ton code fonctionne à l'identique !
                val products = productDtos.map { dto -> dto.toProduct() }

                _state.value = Result.Success(ProductListState(products))

            } catch (e: ErrorResultFromRemoteException) {
                // Gestion de l'erreur renvoyée par le serveur (Le doublon vide a été supprimé ici)
                _state.value = Result.Error(e)
            } catch (e: EmptyBodyException) {
                // Gestion du cas où il n'y a aucun produit / réponse vide
                _state.value = Result.Error(e)
            } catch (e: IOException) {
                // IOException englobe les coupures internet, timeouts, etc.
                val networkException = NetworkException("Impossible de contacter le serveur. Vérifiez votre connexion.", e)
                _state.value = Result.Error(networkException)
            } catch (e: Exception) {
                // Sécurité pour toute autre exception imprévue
                _state.value = Result.Error(e)
            }
        }
    }
}