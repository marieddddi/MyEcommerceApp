package com.formation.myecommerceapp.domain.data.remote

class EmptyBodyException(message: String) : Exception(message)
class ErrorResultFromRemoteException(message: String) : Exception(message)
class NetworkException(message: String, cause: Throwable? = null) : Exception(message)
